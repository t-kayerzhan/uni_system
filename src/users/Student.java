
package users;

import academic.Course;
import academic.CourseSection;
import academic.Mark;
import enums.OrganizationRole;
import exceptions.CourseAlreadyRegisteredException;
import exceptions.MaxCreditsExceededException;
import exceptions.MaxFailsExceededException;
import java.util.*;
import enums.*;

public class Student extends User implements Comparable<Student> {
    private String studentId;
    private int year;
    private double gpa;
    private String major;
    private String school;
    private int creditsEnrolled;
    private int failCount;
    private List<CourseSection> registeredSections;
    private Map<StudentOrganization, OrganizationRole> organizations;

    public Student(String id, String username, String password,
                   String firstName, String lastName, String email,
                   String studentId, int year, String major) {
        super(id, username, password, firstName, lastName, email);
        this.studentId = studentId;
        this.year = year;
        this.major = major;
        this.school = "General";
        this.gpa = 0.0;
        this.creditsEnrolled = 0;
        this.failCount = 0;
        this.registeredSections = new ArrayList<>();
        this.organizations = new HashMap<>();
    }
    
    public int getCreditsForSemester(Semester semester, int year) {
        return registeredSections.stream()
            .filter(s -> s.getSemester() == semester && s.getSemesterYear() == year)
            .mapToInt(s -> s.getCourse().getCredits())
            .sum();
    }

    public void registerForCourse(CourseSection section)
            throws MaxCreditsExceededException, MaxFailsExceededException, CourseAlreadyRegisteredException {
        if (registeredSections.contains(section)) {
            throw new CourseAlreadyRegisteredException(section.getCourse().getCourseCode());
        }
        int credits = section.getCourse().getCredits();
        int currentSemesterCredits = getCreditsForSemester(section.getSemester(), section.getSemesterYear());
        if (currentSemesterCredits + credits > 21) {
            throw new MaxCreditsExceededException(currentSemesterCredits + credits, 21);
        }
        int fails = countFailsForCourse(section.getCourse());
        if (fails >= 3) {
            throw new MaxFailsExceededException(fails);
        }
        registeredSections.add(section);
        section.addStudent(this);
        creditsEnrolled += credits;
    }


    public void dropCourse(CourseSection section) {
        if (registeredSections.remove(section)) {
            section.removeStudent(this);
            creditsEnrolled -= section.getCourse().getCredits();
        }
    }

    
    public List<Mark> viewMarks() {
        List<Mark> result = new ArrayList<>();
        for (CourseSection s : registeredSections) {
            Mark m = s.getMarkForStudent(this);
            if (m != null) result.add(m);
        }
        return result;
    }

    public Mark getMarkForCourse(Course course) {
        for (CourseSection s : registeredSections) {
            if (s.getCourse().equals(course)) return s.getMarkForStudent(this);
        }
        return null;
    }

    
    public void addMark(Mark mark) {
        if (!mark.isPassed()) failCount++;
        recalculateGPA();
    }

    private int countFailsForCourse(Course course) {
        int count = 0;
        for (CourseSection s : registeredSections) {
            if (s.getCourse().equals(course)) {
                Mark m = s.getMarkForStudent(this);
                if (m != null && !m.isPassed()) count++;
            }
        }
        return count;
    }

    public void recalculateGPA() {
        List<Mark> marks = viewMarks();
        if (marks.isEmpty()) { gpa = 0; return; }
        gpa = marks.stream().mapToDouble(Mark::getGradePoints).average().orElse(0);
    }

    public void rateTeacher(Teacher teacher, int value) {
        if (value < 1 || value > 5) { System.out.println("Rating must be 1-5."); return; }
        teacher.addRating(value);
        System.out.println("Rated " + teacher.getFullName() + ": " + value + "/5");
    }

    public boolean joinOrganization(StudentOrganization org, OrganizationRole role) {
        if (organizations.containsKey(org)) {
            System.out.println("You are already a member of: " + org.getName()
                    + " | Role: " + organizations.get(org));
            return false;
        }

        organizations.put(org, role);
        org.addMember(this, role);
        return true;
    }
    
    public boolean leaveOrganization(StudentOrganization org) {
        if (!organizations.containsKey(org)) return false;

        organizations.remove(org);
        org.removeMember(this);
        return true;
    }

    public double getAttendancePercent(CourseSection section) {
        int total = 0, present = 0;
        for (academic.Lesson lesson : section.getLessons()) {
            Map<Student, Boolean> att = lesson.getAttendanceList();
            if (att.containsKey(this)) {
                total++;
                if (att.get(this)) present++;
            }
        }
        return total == 0 ? 0 : (present * 100.0 / total);
    }

    @Override
    public int compareTo(Student other) { return Double.compare(other.gpa, this.gpa); }

    public String getStudentId() { return studentId; }
    public int getYear() { return year; }
    public double getGpa() { return gpa; }
    public String getMajor() { return major; }
    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }
    public int getFailCount() { return failCount; }
    public List<CourseSection> getRegisteredSections() { return registeredSections; }
    public int getCreditsEnrolled() {
        return registeredSections.stream()
            .mapToInt(s -> s.getCourse().getCredits())
            .sum();
    }

    
    public List<Course> getRegisteredCourses() {
        List<Course> courses = new ArrayList<>();
        for (CourseSection s : registeredSections) courses.add(s.getCourse());
        return courses;
    }
    
  
    public void printCoursesWithType() {
        for (CourseSection s : registeredSections) {
            System.out.println(s.getCourse().getTitle() 
                + " | Type: " + s.getCourseTypeForStudent(this));
        }
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;
        return this.studentId.equals(((Student) obj).studentId);
    }

    @Override
    public int hashCode() { return Objects.hash(studentId); }

    @Override
    public String toString() {
        return super.toString() + " | ID: " + studentId + " | Year: " + year
                + " | Major: " + major + " | School: " + school + " | GPA: " + String.format("%.2f", gpa)
                + " | Credits: " + creditsEnrolled;
    }
}