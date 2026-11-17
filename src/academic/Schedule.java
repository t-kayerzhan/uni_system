package academic;

import users.Student;
import users.Teacher;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Schedule implements Serializable {
    private static int counter = 1;
    private int scheduleId;
    private String semester;
    private String academicYear;
    private List<Lesson> lessons;

    public Schedule(String semester, String academicYear) {
        this.scheduleId = counter++;
        this.semester = semester;
        this.academicYear = academicYear;
        this.lessons = new ArrayList<>();
    }

    public void addLesson(Lesson lesson) {
        if (!lessons.contains(lesson)) {      
            lessons.add(lesson);
        } else {
            System.out.println("Lesson already in schedule.");
        }
    }
    public void removeLesson(Lesson lesson) {
        if (lessons.remove(lesson)) {         
            System.out.println("Lesson removed from schedule.");
        } else {
            System.out.println("Lesson not found in schedule.");
        }
    }

    public List<Lesson> getLessonsForTeacher(Teacher teacher) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson l : lessons)
            if (l.getRoom() != null) result.add(l);
        return result;
    }

    public List<Lesson> getLessonsForStudent(Student student) {
        return new ArrayList<>(lessons);
    }

    public List<Lesson> getLessonsForRoom(Room room) {
        List<Lesson> result = new ArrayList<>();
        for (Lesson l : lessons) {
            if (room.equals(l.getRoom())) {   
                result.add(l);
            }
        }
        return result;
    }

    public String getSemester() { return semester; }
    public String getAcademicYear() { return academicYear; }
    public List<Lesson> getLessons() { return lessons; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("===== SCHEDULE: " + semester + " " + academicYear + " =====\n");
        for (Lesson l : lessons) sb.append(l).append("\n");
        return sb.toString();
    }
}
