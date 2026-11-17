package exceptions;

public class CourseAlreadyRegisteredException extends Exception {
    private String courseCode;

    public CourseAlreadyRegisteredException(String courseCode) {
        super("Student is already registered for course: " + courseCode);
        this.courseCode = courseCode;
    }

    public String getCourseCode() { return courseCode; }
}
