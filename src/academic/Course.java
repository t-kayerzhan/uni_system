
package academic;

import enums.CourseType;
import java.io.Serializable;
import java.util.*;

public class Course implements Serializable {
    private static int counter = 1;
    private int courseId;
    private String courseCode;
    private String title;
    private String description;
    private int credits;
   
  
    private String targetMajor;
    private String school;
    
    public Course(String courseCode, String title, String description,
                  int credits) {
        this.courseId = counter++;
        this.courseCode = courseCode;
        this.title = title;
        this.description = description;
        this.credits = credits;
        this.school = "General";
    }

    

    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public int getCredits() { return credits; }
    public String getTargetMajor() { return targetMajor; }
    public void setTargetMajor(String major) { this.targetMajor = major; }
    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Course)) return false;
        return this.courseCode.equals(((Course) obj).courseCode);
    }

    @Override
    public int hashCode() { return Objects.hash(courseCode); }

    @Override
    public String toString() {
        return "[COURSE] " + courseCode + " - " + title
                + " | Credits: " + credits
                + " | School: " + school
                + " | Target major: " + (targetMajor != null ? targetMajor : "General");
    }
}