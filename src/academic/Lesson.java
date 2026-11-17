package academic;

import enums.LessonType;
import java.util.Objects;

import users.Student;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Lesson implements Serializable {
    private static int counter = 1;
    private int lessonId;
    private String topic;
    private Date date;
    private String startTime;
    private String endTime;
    private LessonType type;
    private Room room;
    private Map<Student, Boolean> attendance;

    public Lesson(String topic, Date date, String startTime, String endTime, LessonType type,Room room) {
        this.lessonId = counter++;
        this.topic = topic;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.attendance = new HashMap<>();
        this.room = room;
    }

    public void markAttendance(Student student, boolean present) {
        attendance.put(student, present);
    }

    public double getAttendancePercent() {
        if (attendance.isEmpty()) return 0;
        long present = attendance.values().stream().filter(v -> v).count();
        return (present * 100.0) / attendance.size();
    }

    public int getPresentCount() {
        return (int) attendance.values().stream().filter(v -> v).count();
    }

    public int getLessonId() { return lessonId; }
    public String getTopic() { return topic; }
    public Date getDate() { return date; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public LessonType getType() { return type; }
    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }
    public Map<Student, Boolean> getAttendanceList() { return attendance; }

    @Override
    public String toString() {
        return "[LESSON #" + lessonId + "] " + topic
                + " | " + type + " | " + startTime + "-" + endTime
                + " | Room: " + (room != null ? room.getRoomNumber() : "TBD")
                + " | Attendance: " + String.format("%.0f%%", getAttendancePercent());
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Lesson)) return false;
        Lesson other = (Lesson) obj;
        return this.lessonId == other.lessonId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId);
    }
}
