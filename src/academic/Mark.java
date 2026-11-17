package academic;

import users.Student;
import java.util.Objects;
import java.io.Serializable;

public class Mark implements Serializable, Comparable<Mark> {
    private Student student;
    private Course course;
    private double attestation1;
    private double attestation2;
    private double finalExam;

    public Mark(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.attestation1 = 0;
        this.attestation2 = 0;
        this.finalExam = 0;
    }

    public void setAttestation1(double val) { this.attestation1 = val; }
    public void setAttestation2(double val) { this.attestation2 = val; }
    public void setFinalExam(double val) { this.finalExam = val; }
    public double getAttestation1() { return attestation1; }
    public double getAttestation2() { return attestation2; }
    public double getFinalExam() { return finalExam; }
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }

    public double getTotal() {
        return attestation1  + attestation2  + finalExam ;
    }

    public String getLetterGrade() {

        double total = getTotal();

        if (total >= 95) return "A";
        if (total >= 90) return "A-";

        if (total >= 85) return "B+";
        if (total >= 80) return "B";
        if (total >= 75) return "B-";

        if (total >= 70) return "C+";
        if (total >= 65) return "C";
        if (total >= 60) return "C-";

        if (total >= 55) return "D+";
        if (total >= 50) return "D";

        return "F";
    }

    public double getGradePoints() {

        switch (getLetterGrade()) {

            case "A":  return 4.0;
            case "A-": return 3.67;

            case "B+": return 3.33;
            case "B":  return 3.0;
            case "B-": return 2.67;

            case "C+": return 2.33;
            case "C":  return 2.0;
            case "C-": return 1.67;

            case "D+": return 1.33;
            case "D":  return 1.0;

            default:   return 0.0;
        }
    }

    public boolean isPassed() { return getTotal() >= 50; }

    @Override
    public int compareTo(Mark other) {
        return Double.compare(other.getTotal(), this.getTotal());
    }

    @Override
    public String toString() {
        return "[MARK] " + course.getTitle()
                + " | A1: " + attestation1 + " A2: " + attestation2 + " Final: " + finalExam
                + " | Total: " + String.format("%.1f", getTotal())
                + " | Grade: " + getLetterGrade()
                + " | Passed: " + isPassed();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Mark)) return false;
        Mark other = (Mark) obj;
        return this.student.equals(other.student)
            && this.course.equals(other.course);
    }

    @Override
    public int hashCode() {
        return Objects.hash(student, course);
    }
}
