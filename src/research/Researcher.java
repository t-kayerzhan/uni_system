package research;

import java.util.Comparator;
import java.util.List;
import enums.NewsTopic;
public interface Researcher {
    int getHIndex();
    void printPapers(Comparator<ResearchPaper> comparator);
    List<ResearchPaper> getPapers();
    List<ResearchProject> getProjects();
    void addPaper(ResearchPaper paper);
    void addProject(ResearchProject project);
    void addPaperWithTopic(ResearchPaper paper, NewsTopic topic);
}
