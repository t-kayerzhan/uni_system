package users;

import academic.Course;
import academic.CourseSection;
import enums.LessonType;
import enums.ManagerType;
import research.News;
import java.util.*;


public class Manager extends Employee {
    private ManagerType type;

    public Manager(String id, String username, String password,
                   String firstName, String lastName, String email,
                   double salary, String department, ManagerType type) {
        super(id, username, password, firstName, lastName, email, salary, department);
        this.type = type;
    }

    public void assignTeacher(CourseSection section, Teacher teacher, LessonType lessonType) {
        section.assignTeacher(teacher, lessonType);
        System.out.println("Assigned " + teacher.getFullName() + " to "
                + section.getCourse().getTitle() + " [" + lessonType + "]");
    }

    public void addCourseForRegistration(Course course, String major) {
        course.setTargetMajor(major);
        System.out.println("Course " + course.getTitle() + " added for Major: " + major);
    }

    public void approveRegistration(Student student, Course course) {
        System.out.println("Registration APPROVED: " + student.getFullName() + " -> " + course.getTitle());
    }

    public void rejectRegistration(Student student, Course course, String reason) {
        System.out.println("Registration REJECTED: " + student.getFullName() + " -> " + course.getTitle() + " | Reason: " + reason);
    }

   

    public String createAcademicReport(List<Student> students) {
        StringBuilder sb = new StringBuilder("===== ACADEMIC REPORT =====\n");
        sb.append("Total Students: ").append(students.size()).append("\n");
        double totalGpa = 0;
        for (Student s : students) totalGpa += s.getGpa();
        sb.append("Average GPA: ").append(students.isEmpty() ? 0 : String.format("%.2f", totalGpa / students.size())).append("\n");
        sb.append("===========================");
        System.out.println(sb);
        return sb.toString();
    }

    public void publishNews(News news) {
        if (news.getTopic() == enums.NewsTopic.RESEARCH) news.setPinned(true);
        System.out.println("News published: " + news.getTitle() + (news.isPinned() ? " [PINNED]" : ""));
    }

    public List<Student> viewStudentsSortedByGPA(List<Student> students) {
        List<Student> sorted = new ArrayList<>(students);
        Collections.sort(sorted);
        System.out.println("=== Students sorted by GPA ===");
        for (Student s : sorted) System.out.println(s.getFullName() + " - GPA: " + s.getGpa());
        return sorted;
    }

    public List<Student> viewStudentsSortedByName(List<Student> students) {
        List<Student> sorted = new ArrayList<>(students);
        sorted.sort(Comparator.comparing(User::getFullName));
        System.out.println("=== Students sorted by Name ===");
        for (Student s : sorted) System.out.println(s);
        return sorted;
    }

    public List<Teacher> viewTeachersSortedByRating(List<Teacher> teachers) {
        List<Teacher> sorted = new ArrayList<>(teachers);
        sorted.sort((a, b) -> Double.compare(b.getRating(), a.getRating()));
        System.out.println("=== Teachers sorted by Rating ===");
        for (Teacher t : sorted) System.out.println(t.getFullName() + " - Rating: " + t.getRating());
        return sorted;
    }

    public ManagerType getType() { return type; }

    @Override
    public String toString() {
        return super.toString() + " | Manager Type: " + type;
    }
}
