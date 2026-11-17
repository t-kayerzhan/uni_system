
package users;

import academic.Course;
import academic.CourseSection;
import academic.Lesson;
import academic.Mark;
import enums.ComplaintUrgency;
import enums.TeacherPosition;
import research.ResearchPaper;
import research.ResearchProject;
import research.Researcher;
import system.UniversitySystem;
import enums.NewsTopic;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Teacher extends Employee implements Researcher {
    private TeacherPosition position;
    private double rating;
    private int ratingCount;
    private List<CourseSection> courseSections;
    private List<ResearchPaper> papers;
    private List<ResearchProject> projects;
   

    public Teacher(String id, String username, String password,
                   String firstName, String lastName, String email,
                   double salary, String department, TeacherPosition position) {
        super(id, username, password, firstName, lastName, email, salary, department);
        this.position = position;
        this.rating = 0.0;
        this.ratingCount = 0;
        this.courseSections = new ArrayList<>();
        this.papers = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    public void putMark(Student student, CourseSection section, Mark mark) {

        if (!this.equals(section.getLectureTeacher())) {
            System.out.println(" Only lecture teacher can put marks.");
            return;
        }

        section.setMarkForStudent(student, mark);

        System.out.println("Mark set for " + student.getFullName()
                + " in " + section.getCourse().getTitle());
    }

    
    

    public void markAttendance(Student student, Lesson lesson, boolean present) {
        lesson.markAttendance(student, present);
        System.out.println("Attendance marked for " + student.getFullName()
                + ": " + (present ? "Present" : "Absent"));
    }

    public void addCourseSection(CourseSection section) {
        if (!courseSections.contains(section)) {
            courseSections.add(section);
        }
    }
    public List<CourseSection> getCourseSections() { return courseSections; }

   
    public List<academic.Course> getCourses() {
        List<academic.Course> courses = new ArrayList<>();
        for (CourseSection s : courseSections) courses.add(s.getCourse());
        return courses;
    }

    public void addRating(int value) {
        rating = (rating * ratingCount + value) / (++ratingCount);
    }

    public TeacherPosition getPosition() { return position; }
    public void setPosition(TeacherPosition pos) { this.position = pos; }
    public double getRating() { return rating; }
    
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
            UniversitySystem.getInstance().generateResearchAnnouncementNews(paper, this);
        }
    }

    @Override
    public void addProject(ResearchProject project) {
        if (!projects.contains(project)) {
            projects.add(project);
        }
    }
    
    
    

    @Override
    public String toString() {
        return super.toString() + " | Position: " + position
                + " | Rating: " + String.format("%.1f", rating)+ " | H-Index: " + getHIndex();
    } 
    public Complaint sendComplaint(Student s,
            ComplaintUrgency urgency,
            String desc) {

            Complaint c = new Complaint(this, s, urgency, desc);
            UniversitySystem.getInstance().addComplaint(c);

System.out.println("Complaint sent about "
+ s.getFullName()
+ " [" + urgency + "]");

return c;
}
    @Override
    public void addPaperWithTopic(ResearchPaper paper, NewsTopic topic) {
        if (!papers.contains(paper)) {
            papers.add(paper);
            UniversitySystem.getInstance().generateResearchAnnouncementNewsWithTopic(paper, this, topic);
        }
    }
    
   

}