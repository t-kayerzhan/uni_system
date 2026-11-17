package users;

import enums.GraduateType;
import exceptions.LowHIndexException;
import research.ResearchPaper;
import research.ResearchProject;
import research.Researcher;
import java.util.*;
import system.UniversitySystem;
import enums.NewsTopic;
public class GraduateStudent extends Student implements Researcher {
    private GraduateType degreeType;
    private Researcher supervisor;
    private List<ResearchPaper> diplomaPapers;
    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;

    public GraduateStudent(String id, String username, String password,
                           String firstName, String lastName, String email,
                           String studentId, int year, String major, GraduateType degreeType) {
        super(id, username, password, firstName, lastName, email, studentId, year, major);
        this.degreeType = degreeType;
        this.diplomaPapers = new ArrayList<>();
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    public void setSupervisor(Researcher researcher) throws LowHIndexException {
        if (researcher.getHIndex() < 3) {
            throw new LowHIndexException(researcher.getHIndex());
        }
        this.supervisor = researcher;
        System.out.println("Supervisor set: h-index = " + researcher.getHIndex());
    }

    public Researcher getSupervisor() { return supervisor; }
    public GraduateType getDegreeType() { return degreeType; }

    public void addDiplomaPaper(ResearchPaper paper) {
        if (!diplomaPapers.contains(paper)) {
            diplomaPapers.add(paper);
            if (!papers.contains(paper)) papers.add(paper);
            System.out.println("Diploma paper added: " + paper.getTitle());
        } else {
            System.out.println("Diploma paper already added.");
        }
    }

    public List<ResearchPaper> getDiplomaPapers() { return diplomaPapers; }

    @Override
    public int getHIndex() {
        List<Integer> citations = new ArrayList<>();
        for (ResearchPaper p : papers) citations.add(p.getCitations());
        citations.sort(Comparator.reverseOrder());
        int h = 0;
        for (int i = 0; i < citations.size(); i++) {
            if (citations.get(i) >= i + 1) h = i + 1;
            else break;
        }
        return h;
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> c) {
        List<ResearchPaper> sorted = new ArrayList<>(papers);
        sorted.sort(c);
        System.out.println("=== Papers by " + getFullName() + " ===");
        for (ResearchPaper p : sorted) System.out.println(p);
    }

    @Override
    public List<ResearchPaper> getPapers() { return papers; }

    @Override
    public List<ResearchProject> getProjects() { return projects; }

    @Override
    public void addPaper(ResearchPaper paper) {
        if (!papers.contains(paper)) {
            papers.add(paper);
            diplomaPapers.add(paper); 
            UniversitySystem.getInstance().generateResearchAnnouncementNews(paper, this);
        }
    }

    @Override
    public void addProject(ResearchProject project) {
        if (!projects.contains(project)) {
            projects.add(project);
        }
    }
    
    public void setSupervisor(Teacher teacher) throws LowHIndexException {
        if (teacher.getHIndex() < 3) {
            throw new LowHIndexException(teacher.getHIndex());
        }

        this.supervisor = teacher;
        System.out.println("Supervisor set: h-index = " + teacher.getHIndex());
    }
    

    @Override
    public String toString() {
        return super.toString() + " | Degree: " + degreeType
                + " | Supervisor: " + (supervisor != null ? "Assigned" : "None")
                + " | H-Index: " + getHIndex();
    }
    
    @Override
    public void addPaperWithTopic(ResearchPaper paper, NewsTopic topic) {
        if (!papers.contains(paper)) {
            papers.add(paper);
            diplomaPapers.add(paper);
            UniversitySystem.getInstance().generateResearchAnnouncementNewsWithTopic(paper, this, topic);
        }
    }
}
