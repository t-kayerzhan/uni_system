package system;

import academic.Course;
import research.ResearchPaper;
import users.Student;
import users.User;
import java.util.ArrayList;
import java.util.List;

public class SearchService {

    public List<Student> searchStudentsByName(String name, List<User> users) {
        List<Student> result = new ArrayList<>();
        String lower = name.toLowerCase();
        for (User u : users) {
            if (u instanceof Student && u.getFullName().toLowerCase().contains(lower)) {
                result.add((Student) u);
            }
        }
        System.out.println("Found " + result.size() + " students matching: " + name);
        return result;
    }

    public List<Student> searchStudentsByMajor(String major, List<User> users) {
        List<Student> result = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Student && ((Student)u).getMajor().equalsIgnoreCase(major)) {
                result.add((Student) u);
            }
        }
        return result;
    }

    public List<Student> searchStudentsByGPA(double min, double max, List<User> users) {
        List<Student> result = new ArrayList<>();
        for (User u : users) {
            if (u instanceof Student) {
                double gpa = ((Student)u).getGpa();
                if (gpa >= min && gpa <= max) result.add((Student) u);
            }
        }
        return result;
    }

    public List<Course> searchCoursesByTitle(String title, List<Course> courses) {
        List<Course> result = new ArrayList<>();
        String lower = title.toLowerCase();
        for (Course c : courses) {
            if (c.getTitle().toLowerCase().contains(lower)) result.add(c);
        }
        System.out.println("Found " + result.size() + " courses matching: " + title);
        return result;
    }

    public List<Course> searchCoursesByCode(String code, List<Course> courses) {
        List<Course> result = new ArrayList<>();
        for (Course c : courses) {
            if (c.getCourseCode().equalsIgnoreCase(code)) result.add(c);
        }
        return result;
    }

    public List<ResearchPaper> searchPapersByAuthor(String author, List<ResearchPaper> papers) {
        List<ResearchPaper> result = new ArrayList<>();
        String lower = author.toLowerCase();
        for (ResearchPaper p : papers) {
            for (String a : p.getAuthors()) {
                if (a.toLowerCase().contains(lower)) { result.add(p); break; }
            }
        }
        return result;
    }

    public List<ResearchPaper> searchPapersByKeyword(String keyword, List<ResearchPaper> papers) {
        List<ResearchPaper> result = new ArrayList<>();
        String lower = keyword.toLowerCase();
        for (ResearchPaper p : papers) {
            if (p.getTitle().toLowerCase().contains(lower)) result.add(p);
            else if (p.getKeywords() != null) {
                for (String k : p.getKeywords()) {
                    if (k.toLowerCase().contains(lower)) { result.add(p); break; }
                }
            }
        }
        return result;
    }

    public List<User> searchUsersByEmail(String email, List<User> users) {
        List<User> result = new ArrayList<>();
        for (User u : users) {
            if (u.getEmail().toLowerCase().contains(email.toLowerCase())) result.add(u);
        }
        return result;
    }
}
