package academic;
import users.Student;
import java.io.Serializable;
public class CourseRegistrationRequest implements Serializable {
    private Student student;
    private CourseSection section;
    private String status = "PENDING";
    
    public CourseRegistrationRequest(Student student, CourseSection section) {
        this.student = student;
        this.section = section;
    }
    public Student getStudent() { return student; }
    public CourseSection getSection() { return section; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    @Override
    public String toString() {
        return "[REQUEST] " + student.getFullName() + " → " + section.getCourse().getTitle() + " | " + status;
    }
}