package system;

import academic.Course;
import academic.Mark;
import research.Researcher;
import research.ResearchPaper;
import users.Student;
import users.Teacher;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import academic.CourseSection;

public class ReportService {

	public String generateMarkReport(CourseSection section) {
	    StringBuilder sb = new StringBuilder();

	    sb.append("===== MARK REPORT: ")
	      .append(section.getCourse().getTitle())
	      .append(" =====\n");

	    sb.append("Course Code: ")
	      .append(section.getCourse().getCourseCode())
	      .append("\n");

	    sb.append("Total Students: ")
	      .append(section.getEnrolledStudents().size())
	      .append("\n");

	    sb.append("Average Grade: ")
	      .append(String.format("%.2f", section.getAverageGrade()))
	      .append("\n");

	    sb.append("Passed: ")
	      .append(section.getPassCount())
	      .append("\n");

	    sb.append("Failed: ")
	      .append(section.getFailCount())
	      .append("\n");

	    for (Student s : section.getEnrolledStudents()) {
	        Mark m = section.getMarkForStudent(s);

	        if (m != null) {
	            sb.append("  ")
	              .append(s.getFullName())
	              .append(": ")
	              .append(m)
	              .append("\n");
	        }
	    }

	    sb.append("=============================================");

	    System.out.println(sb);

	    return sb.toString();
	}
	
    public String generateTranscript(Student student) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== TRANSCRIPT =====\n");
        sb.append("Student: ").append(student.getFullName()).append("\n");
        sb.append("ID: ").append(student.getStudentId()).append("\n");
        sb.append("Major: ").append(student.getMajor()).append("\n");
        sb.append("Year: ").append(student.getYear()).append("\n");
        sb.append("GPA: ").append(String.format("%.2f", student.getGpa())).append("\n");
        sb.append("Credits: ").append(student.getCreditsEnrolled()).append("\n");
        sb.append("--- Marks ---\n");
        for (Mark m : student.viewMarks()) sb.append("  ").append(m).append("\n");
        sb.append("======================");
        System.out.println(sb);
        return sb.toString();
    }

    public String generateAcademicReport(List<Student> students) {
        StringBuilder sb = new StringBuilder("===== ACADEMIC PERFORMANCE REPORT =====\n");
        sb.append("Total Students: ").append(students.size()).append("\n");
        double total = students.stream().mapToDouble(Student::getGpa).sum();
        double avg = students.isEmpty() ? 0 : total / students.size();
        sb.append("Average GPA: ").append(String.format("%.2f", avg)).append("\n");
        long passing = students.stream().filter(s -> s.getGpa() >= 2.0).count();
        sb.append("Passing Students: ").append(passing).append("\n");
        sb.append("========================================");
        System.out.println(sb);
        return sb.toString();
    }

    public String generateAttendanceReport(CourseSection section) {

        StringBuilder sb = new StringBuilder();

        sb.append("===== ATTENDANCE REPORT: ")
          .append(section.getCourse().getTitle())
          .append(" =====\n");

        if (section.getEnrolledStudents().isEmpty()) {
            sb.append("No students enrolled.\n");
        } else {

            for (Student s : section.getEnrolledStudents()) {

                double pct = s.getAttendancePercent(section);

                sb.append("  ")
                  .append(s.getFullName())
                  .append(": ")
                  .append(String.format("%.0f%%", pct))
                  .append("\n");
            }
        }

        sb.append("========================================");

        System.out.println(sb);

        return sb.toString();
    }

    public String generateResearchReport(List<Researcher> researchers) {
        StringBuilder sb = new StringBuilder("===== RESEARCH REPORT =====\n");
        for (Researcher r : researchers) {
            sb.append("  H-Index: ").append(r.getHIndex())
              .append(" | Papers: ").append(r.getPapers().size()).append("\n");
        }
        sb.append("===========================");
        System.out.println(sb);
        return sb.toString();
    }

    public String generateRecommendationLetter(Teacher teacher, Student student) {
        String letter = "===== RECOMMENDATION LETTER =====\n"
                + "From: " + teacher.getFullName() + " | " + teacher.getPosition() + "\n"
                + "Department: " + teacher.getDepartment() + "\n\n"
                + "I, " + teacher.getFullName() + ", highly recommend " + student.getFullName() + ".\n"
                + "Student ID: " + student.getStudentId() + "\n"
                + "Major: " + student.getMajor() + " | Year: " + student.getYear() + "\n"
                + "GPA: " + String.format("%.2f", student.getGpa()) + "\n\n"
                + "This student has shown exemplary academic performance.\n\n"
                + "Sincerely,\n" + teacher.getFullName()
                + "\n=================================";
        System.out.println(letter);
        return letter;
    }

    public void exportReport(String report, String filename) {
        try (FileWriter fw = new FileWriter(filename)) {
            fw.write(report);
            System.out.println("Report exported to: " + filename);
        } catch (IOException e) {
            System.err.println("Export failed: " + e.getMessage());
        }
    }
    
    public String generateOfficialMessage(String text) {
        String msg = "===== OFFICIAL MESSAGE =====\n" + text + "\n===========================";
        System.out.println(msg);
        return msg;
    }
}
