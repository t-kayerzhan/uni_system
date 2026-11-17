package system;

import academic.*;
import enums.*;
import exceptions.*;
import research.*;
import users.*;

import java.util.*;
import java.time.*;
import java.util.stream.*;
import static system.AppContext.*;
import static system.CommonConsoleActions.*;

public class TeacherConsole {
public static void showTeacherMenu() {
    	System.out.println("\n==== " + L("teacher.menu") + " =============================");
    	System.out.println("1. " + L("teacher.courses"));
    	System.out.println("2. " + L("teacher.put_mark"));
    	System.out.println("3. " + L("teacher.attendance"));
    	System.out.println("4. " + L("teacher.complaint"));
    	System.out.println("5. " + L("teacher.report"));
    	System.out.println("6. " + L("teacher.letter"));
    	System.out.println("7. " + L("teacher.hindex"));
    	System.out.println("8. " + L("teacher.papers"));
    	System.out.println("9. " + L("teacher.message"));
    	System.out.println("10. " + L("menu.change_lang"));
    	System.out.println("11. " + L("teacher.news"));
    	System.out.println("12. " + L("teacher.publish_paper"));
    	System.out.println("13. " + L("teacher.add_paper"));
    	System.out.println("14. " + L("menu.messages"));
    	System.out.println("15. " + L("teacher.startup"));
        System.out.println("16. Research projects");
    	System.out.println("0. " + L("menu.logout"));
    	System.out.print(L("menu.choice"));
        String choice = scanner.nextLine();
        Teacher teacher = (Teacher) currentUser;

        switch (choice) {
            case "1":
                if (teacher.getCourseSections().isEmpty()) { System.out.println("No courses."); break; }
                for (CourseSection s : teacher.getCourseSections()) System.out.println(s);
                break;
            case "2":
                TeacherConsole.putMarkFlow(teacher);
                break;
            case "3":
                TeacherConsole.markAttendanceFlow(teacher);
                break;
            case "4":
                TeacherConsole.sendComplaintFlow(teacher);
                break;
            case "5":
                List<CourseSection> sections = teacher.getCourseSections();

                if (sections.isEmpty()) {
                    System.out.println("No courses.");
                    break;
                }

                System.out.println("Select a discipline for the mark report:");

                for (int i = 0; i < sections.size(); i++) {
                    System.out.println((i + 1) + ". " + sections.get(i).getCourse().getTitle());
                }

                System.out.print("Enter number: ");

                try {
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;

                    if (idx < 0 || idx >= sections.size()) {
                        System.out.println("Invalid selection.");
                        break;
                    }

                    reports.generateMarkReport(sections.get(idx));
                } catch (Exception e) {
                    System.out.println("Invalid input.");
                }
                break;
            case "6":
                TeacherConsole.generateLetterFlow(teacher);
                break;
            case "7":
                if (teacher instanceof Teacher)
                    System.out.println("H-Index: " + ((Teacher) teacher).getHIndex());
                else System.out.println("Not a researcher.");
                break;
            case "8":
                if (teacher instanceof Teacher) {
                    System.out.println("Sort by: 1.Citations  2.Date  3.Pages");
                    String sc = scanner.nextLine();
                    Teacher tr = (Teacher) teacher;
                    switch (sc) {
                        case "2": tr.printPapers(UniversityComparators.BY_DATE_DESC);     break;
                        case "3": tr.printPapers(UniversityComparators.BY_PAGES_DESC);    break;
                        default:  tr.printPapers(UniversityComparators.BY_CITATIONS_DESC);
                    }
                } else System.out.println("Not a researcher.");
                break;
            case "9":
                sendMessageFlow();
                break;
            case "10": changeLanguage(); break;

            case "11":
                List<News> tnews = sys.getNewsSortedByPriority();
                for (int i = 0; i < tnews.size(); i++)
                    System.out.println((i + 1) + ". " + tnews.get(i));
                System.out.print("Comment on # (0 to skip): ");
                try {
                    int nIdx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (nIdx >= 0 && nIdx < tnews.size()) {
                        System.out.print("Comment: ");
                        tnews.get(nIdx).addComment(teacher, scanner.nextLine());
                    }
                } catch (Exception e) {}
                break;
            case "12":
                List<research.Journal> journals = sys.getJournals();
                if (journals.isEmpty()) { System.out.println("No journals."); break; }
                for (int i = 0; i < journals.size(); i++)
                    System.out.println((i + 1) + ". " + journals.get(i));
                System.out.print("Select journal: ");
                try {
                    int jIdx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (jIdx < 0 || jIdx >= journals.size()) break;
                    research.Journal j = journals.get(jIdx);
                    List<ResearchPaper> papers = teacher.getPapers();
                    if (papers.isEmpty()) { System.out.println("You have no papers."); break; }
                    System.out.println("Select paper to publish:");
                    for (int i = 0; i < papers.size(); i++)
                        System.out.println((i + 1) + ". " + papers.get(i).getTitle());
                    int pIdx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (pIdx < 0 || pIdx >= papers.size()) break;
                    ResearchPaper pub = papers.get(pIdx);
                    j.publishPaper(pub);
                    System.out.println(" Статья опубликована в журнале: " + pub.getTitle());
                } catch (Exception e) { System.out.println("Error."); }
                break;
            case "13":
                addPaperFlow(teacher);
                break;
            case "14":
                viewMessagesFlow();
                break;
            case "15":
                startupFlow(teacher);
                break;
            case "16":
                ResearchProjectConsole.researchProjectFlow(teacher);
                break;
            case "0": logout(); break;
        }
    }

public static void putMarkFlow(Teacher teacher) {
        List<CourseSection> sections = teacher.getCourseSections();
        if (sections.isEmpty()) { System.out.println("No courses."); return; }
        System.out.println("Select section:");
        for (int i = 0; i < sections.size(); i++)
            System.out.println((i + 1) + ". " + sections.get(i).getCourse().getTitle());
        int sIdx;
        try { sIdx = Integer.parseInt(scanner.nextLine()) - 1; }
        catch (NumberFormatException e) { System.out.println("Invalid."); return; }
        if (sIdx < 0 || sIdx >= sections.size()) { System.out.println("Invalid index."); return; }

        CourseSection section = sections.get(sIdx);
        List<Student> enrolled = section.getEnrolledStudents();
        if (enrolled.isEmpty()) { System.out.println("No students in this course."); return; }

        System.out.println("Select student:");
        for (int i = 0; i < enrolled.size(); i++)
            System.out.println((i + 1) + ". " + enrolled.get(i).getFullName());
        int stIdx;
        try { stIdx = Integer.parseInt(scanner.nextLine()) - 1; }
        catch (NumberFormatException e) { System.out.println("Invalid."); return; }
        if (stIdx < 0 || stIdx >= enrolled.size()) { System.out.println("Invalid index."); return; }

        Student student = enrolled.get(stIdx);
        Mark mark = new Mark(student, section.getCourse());
        try {
            System.out.print("Attestation 1 (0-30): ");
            mark.setAttestation1(Double.parseDouble(scanner.nextLine()));
            System.out.print("Attestation 2 (0-30): ");
            mark.setAttestation2(Double.parseDouble(scanner.nextLine()));
            System.out.print("Final exam (0-40): ");
            mark.setFinalExam(Double.parseDouble(scanner.nextLine()));
            teacher.putMark(student, section, mark);
        } catch (NumberFormatException e) { System.out.println("Invalid input."); }
    }

public static void markAttendanceFlow(Teacher teacher) {
        List<CourseSection> sections = teacher.getCourseSections();
        if (sections.isEmpty()) { System.out.println("No courses."); return; }
        System.out.println("Select section:");
        for (int i = 0; i < sections.size(); i++)
            System.out.println((i + 1) + ". " + sections.get(i).getCourse().getTitle());
        try {
            int sIdx = Integer.parseInt(scanner.nextLine()) - 1;
            if (sIdx < 0 || sIdx >= sections.size()) return;
            CourseSection section = sections.get(sIdx);
            List<Lesson> lessons = section.getLessons();
            if (lessons.isEmpty()) { System.out.println("No lessons."); return; }
            System.out.println("Select lesson:");
            for (int i = 0; i < lessons.size(); i++)
                System.out.println((i + 1) + ". " + lessons.get(i).getTopic());
            int lIdx = Integer.parseInt(scanner.nextLine()) - 1;
            if (lIdx < 0 || lIdx >= lessons.size()) return;
            Lesson lesson = lessons.get(lIdx);
            List<Student> enrolled = section.getEnrolledStudents();
            if (enrolled.isEmpty()) { System.out.println("No students."); return; }
            for (Student s : enrolled) {
                System.out.print("Is " + s.getFullName() + " present? (y/n): ");
                teacher.markAttendance(s, lesson, scanner.nextLine().equalsIgnoreCase("y"));
            }
        } catch (Exception e) { System.out.println("Error."); }
    }

public static void sendComplaintFlow(Teacher teacher) {
        List<Student> students = new ArrayList<>();
        for (User u : sys.getUsers()) if (u instanceof Student) students.add((Student) u);
        if (students.isEmpty()) { System.out.println("No students."); return; }
        System.out.println("Select student (1-" + students.size() + "):");
        for (int i = 0; i < students.size(); i++)
            System.out.println((i + 1) + ". " + students.get(i).getFullName());
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.println("Urgency: 1.LOW  2.MEDIUM  3.HIGH");
            int u = Integer.parseInt(scanner.nextLine());
            ComplaintUrgency urgency = u == 1 ? ComplaintUrgency.LOW
                                     : u == 2 ? ComplaintUrgency.MEDIUM : ComplaintUrgency.HIGH;
            System.out.print("Description: ");
            String desc = scanner.nextLine();

            Complaint c =
                    teacher.sendComplaint(
                            students.get(idx),
                            urgency,
                            desc
                    );

            sys.addComplaint(c);
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

public static void generateLetterFlow(Teacher teacher) {
        List<Student> students = new ArrayList<>();
        for (User u : sys.getUsers()) if (u instanceof Student) students.add((Student) u);
        if (students.isEmpty()) { System.out.println("No students."); return; }
        System.out.println("Select student:");
        for (int i = 0; i < students.size(); i++)
            System.out.println((i + 1) + ". " + students.get(i).getFullName());
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            reports.generateRecommendationLetter(teacher, students.get(idx));
        } catch (Exception e) { System.out.println("Error."); }
    }
static void addPaperFlow(Researcher researcher) {
    System.out.print("Title: ");
    String title = scanner.nextLine();
    System.out.print("Authors (comma separated): ");
    List<String> authors = Arrays.asList(scanner.nextLine().split(","));
    System.out.print("Pages: ");
    int pages = Integer.parseInt(scanner.nextLine().trim());
    System.out.print("DOI: ");
    String doi = scanner.nextLine();

    System.out.print("Enter citations count: ");
    String input = scanner.nextLine().trim();

    int citations;
    try {
        if (input.isEmpty()) {
            System.out.println("Citations cannot be empty. Cancelling...");
            return; 
        }
        citations = Integer.parseInt(input);
    } catch (NumberFormatException e) {
        System.out.println("Error: Citations must be a number! Returning to menu...");
        return; 
    }

    
    System.out.println("News topic: 1.RESEARCH  2.ACADEMIC  3.EVENTS  4.GENERAL  5.STARTUP");
    String topicChoice = scanner.nextLine().trim();
    NewsTopic topic;
    switch (topicChoice) {
        case "2": topic = NewsTopic.ACADEMIC; break;
        case "3": topic = NewsTopic.EVENTS;   break;
        case "4": topic = NewsTopic.GENERAL;  break;
        case "5": topic = NewsTopic.STARTUP;  break;
        default:  topic = NewsTopic.RESEARCH;
    }
    
    ResearchPaper paper = new ResearchPaper(title, authors,"", pages, new Date(), doi);
    paper.setCitations(citations);
    
    for (User u : sys.getUsers()) {
        if (u instanceof Researcher) {
            for (String authorName : authors) {
                if (u.getFullName().trim().equalsIgnoreCase(authorName.trim())) {
                    ((Researcher) u).addPaperWithTopic(paper, topic);
                }
            }
        }
    }
    
    System.out.println(" Paper added to all matching authors!");
}
}
