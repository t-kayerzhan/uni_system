package system;

import academic.Mark;
import research.ResearchPaper;
import users.Student;
import users.Teacher;
import java.util.Comparator;
import users.User;

public class UniversityComparators {

    
    

    public static final Comparator<Student> BY_GPA_DESC =
        (a, b) -> Double.compare(b.getGpa(), a.getGpa());

    
    public static final Comparator<Student> BY_GPA_ASC =
        (a, b) -> Double.compare(a.getGpa(), b.getGpa());


    public static final Comparator<Student> BY_YEAR =
        Comparator.comparingInt(Student::getYear);

    public static final Comparator<Student> BY_CREDITS =
        Comparator.comparingInt(Student::getCreditsEnrolled);

    
    public static final Comparator<Teacher> BY_RATING_DESC =
        (a, b) -> Double.compare(b.getRating(), a.getRating());

    
    public static final Comparator<Teacher> BY_HINDEX_DESC =
        (a, b) -> Integer.compare(b.getHIndex(), a.getHIndex());

        public static final Comparator<User> BY_NAME =
        	    Comparator.comparing(User::getFullName);

    public static final Comparator<Teacher> BY_POSITION =
        Comparator.comparing(Teacher::getPosition);

     

    public static final Comparator<ResearchPaper> BY_CITATIONS_DESC =
        (a, b) -> Integer.compare(b.getCitations(), a.getCitations());

    public static final Comparator<ResearchPaper> BY_DATE_DESC =
        (a, b) -> b.getPublishDate().compareTo(a.getPublishDate());

    public static final Comparator<ResearchPaper> BY_DATE_ASC =
        (a, b) -> a.getPublishDate().compareTo(b.getPublishDate());

    public static final Comparator<ResearchPaper> BY_PAGES_DESC =
        (a, b) -> Integer.compare(b.getPages(), a.getPages());

    public static final Comparator<ResearchPaper> BY_TITLE =
        Comparator.comparing(ResearchPaper::getTitle);

    

    public static final Comparator<Mark> BY_TOTAL_DESC =
        (a, b) -> Double.compare(b.getTotal(), a.getTotal());

    public static final Comparator<Mark> BY_TOTAL_ASC =
        (a, b) -> Double.compare(a.getTotal(), b.getTotal());
        
       
}