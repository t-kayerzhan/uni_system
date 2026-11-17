
package academic;

import enums.*;
import users.Student;
import users.Teacher;
import java.io.Serializable;
import java.util.*;

public class CourseSection implements Serializable {
    private Course course;
    private Teacher lectureTeacher;
    private Teacher practiceTeacher;
    private int maxStudents;
    private List<Student> enrolledStudents;
    private Map<Student, Mark> marks;
    private List<Lesson> lessons;
    private Semester semester;
    private int semesterYear;

    public CourseSection(Course course,int maxStudents, Semester semester, int semesterYear ) {
        this.course = course;
        this.enrolledStudents = new ArrayList<>();
        this.marks = new HashMap<>();
        this.lessons = new ArrayList<>();
        this.maxStudents = maxStudents;
        this.semester = semester;
        this.semesterYear = semesterYear;
    }
    
    public boolean isFull() {
        return enrolledStudents.size() >= maxStudents;
    }

    public void assignTeacher(Teacher teacher, LessonType type) {
        if (type == LessonType.LECTURE) this.lectureTeacher = teacher;
        else this.practiceTeacher = teacher;
        teacher.addCourseSection(this);
    }

    public void addStudent(Student student) {
        if (!this.isFull() && !enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    public void removeStudent(Student student) {
        if (enrolledStudents.remove(student)) {
            marks.remove(student);
        }
    }

    public void addLesson(Lesson lesson) { lessons.add(lesson); }

    public Mark getMarkForStudent(Student student) { return marks.get(student); }

    public void setMarkForStudent(Student student, Mark mark) {
        marks.put(student, mark);
        student.addMark(mark);
    }

    public double getAverageGrade() {
        if (marks.isEmpty()) return 0;
        return marks.values().stream().mapToDouble(Mark::getTotal).average().orElse(0);
    }

    public int getPassCount() {
        return (int) marks.values().stream().filter(Mark::isPassed).count();
    }

    public int getFailCount() {
        return (int) marks.values().stream().filter(m -> !m.isPassed()).count();
    }
    
   
    public CourseType getCourseTypeForStudent(Student student) {
        String major = student.getMajor();
        String targetMajor = course.getTargetMajor();
        String studentSchool = student.getSchool();
        String courseSchool = course.getSchool();

        if (targetMajor == null || targetMajor.isBlank()) return CourseType.FREE_ELECTIVE;
        if (targetMajor.equalsIgnoreCase(major)) return CourseType.MAJOR;
        if (studentSchool != null && courseSchool != null
                && studentSchool.equalsIgnoreCase(courseSchool)) return CourseType.MINOR;
        return CourseType.FREE_ELECTIVE;
    }
   

    public Course getCourse() { return course; }
    public Teacher getLectureTeacher() { return lectureTeacher; }
    public Teacher getPracticeTeacher() { return practiceTeacher; }
    public List<Student> getEnrolledStudents() { return enrolledStudents; }
    public List<Lesson> getLessons() { return lessons; }
    public Map<Student, Mark> getMarks() { return marks; }
    public int getMaxStudents() { return maxStudents; }
    public Semester getSemester() { return semester; }
    public int getSemesterYear() { return semesterYear; }
    public String getSemesterLabel() { return semester.getLabel(semesterYear); }
   

    @Override
    public String toString() {
        return "[SECTION] " + course.getTitle()+   " | " + getSemesterLabel()
                + " | Students: " + enrolledStudents.size() + "/" + maxStudents
                + " | Lecture: " + (lectureTeacher != null ? lectureTeacher.getFullName() : "TBD")
                + " | Practice: " + (practiceTeacher != null ? practiceTeacher.getFullName() : "TBD");
    }
}