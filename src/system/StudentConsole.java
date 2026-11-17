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


public class StudentConsole {
public static void showStudentMenu() {
    	    System.out.println("\n==== " + L("student.menu") + " =============================");
    	    System.out.println("1. " + L("student.view_courses"));
    	    System.out.println("2. " + L("student.register"));
    	    System.out.println("3. " + L("student.drop"));
    	    System.out.println("4. " + L("student.marks"));
    	    System.out.println("5. " + L("student.transcript"));
    	    System.out.println("6. " + L("student.attendance"));
    	    System.out.println("7. " + L("student.rate_teacher"));
    	    System.out.println("8. " + L("student.news"));
    	    System.out.println("9. " + L("menu.change_lang"));
    	    System.out.println("10. " + L("student.orgs"));
    	    System.out.println("11. " + L("student.journal"));
    	    System.out.println("12. " + L("menu.messages"));
    	    System.out.println("13. " + L("student.message"));
    	    System.out.println("14. " + L("student.tech_request"));
    	    System.out.println("15. " + L("student.my_requests"));
    	    System.out.println("16. " + L("student.teacher_info"));
    	    System.out.println("17. " + L("student.schedule"));
    	    System.out.println("18. View official messages");
    	    System.out.println("0. " + L("menu.logout"));
    	    System.out.print(L("menu.choice"));
    	   
        String choice = scanner.nextLine();
        Student student = (Student) currentUser;

        switch (choice) {
        case "1":
            for (CourseSection s : sys.getSections()) {
                System.out.println(s + " | Type: " + s.getCourseTypeForStudent(student));
            }
            break;
            case "2":
                StudentConsole.registerCourseFlow(student);
                break;
            case "3":
                StudentConsole.dropCourseFlow(student);
                break;
            case "4":
                List<Mark> marks = student.viewMarks();
                if (marks.isEmpty()) System.out.println("No marks yet.");
                else marks.forEach(System.out::println);
                break;
            case "5":
                reports.generateTranscript(student);
                break;
            case "6":
                if (student.getRegisteredSections().isEmpty()) {
                    System.out.println("Not registered in any course.");
                } else {
                    for (CourseSection s : student.getRegisteredSections())
                        System.out.println(s.getCourse().getTitle() + ": "
                                + String.format("%.0f%%", student.getAttendancePercent(s)));
                }
                break;
            case "7":
                StudentConsole.rateTeacherFlow(student);
                break;
            case "8":
                List<News> newsList = sys.getNewsSortedByPriority();
                if (newsList.isEmpty()) { System.out.println("No news."); break; }
                for (int i = 0; i < newsList.size(); i++)
                    System.out.println((i + 1) + ". " + newsList.get(i));
                System.out.print("Enter news number to comment (0 to skip): ");
                try {
                    int nIdx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (nIdx >= 0 && nIdx < newsList.size()) {
                        System.out.print("Comment: ");
                        newsList.get(nIdx).addComment(student, scanner.nextLine());
                    }
                } catch (Exception e) {}
                break;
            case "9": changeLanguage(); break;
            case "10":
                List<users.StudentOrganization> orgs = sys.getOrganizations();
                if (orgs.isEmpty()) { System.out.println("No organizations."); break; }
                for (int i = 0; i < orgs.size(); i++)
                    System.out.println((i + 1) + ". " + orgs.get(i));
                System.out.print("1.Join  2.Leave  0.Back: ");
                String orgChoice = scanner.nextLine();
                if (orgChoice.equals("0")) break;
                System.out.print("Select org number: ");
                try {
                    int oIdx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (oIdx < 0 || oIdx >= orgs.size()) break;
                    users.StudentOrganization org = orgs.get(oIdx);
                    if (orgChoice.equals("1")) {
                    	boolean success = student.joinOrganization(org, OrganizationRole.MEMBER);

                    	if (success) {
                    	    System.out.println(" Вы вступили в: " + org.getName());
                    	}
                    } else if (orgChoice.equals("2")) {
                        student.leaveOrganization(org);
                        System.out.println(" Вы вышли из: " + org.getName());
                    }
                } catch (Exception e) { System.out.println("Error."); }
                break;
                
            case "11":
                List<research.Journal> journals = sys.getJournals();
                if (journals.isEmpty()) { System.out.println("No journals."); break; }
                for (int i = 0; i < journals.size(); i++)
                    System.out.println((i + 1) + ". " + journals.get(i));
                System.out.print("1.Subscribe  2.Unsubscribe  0.Back: ");
                String jChoice = scanner.nextLine();
                if (jChoice.equals("0")) break;
                System.out.print("Select journal number: ");
                try {
                    int jIdx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (jIdx < 0 || jIdx >= journals.size()) break;
                    research.Journal journal = journals.get(jIdx);
                    if (jChoice.equals("1")) {
                        journal.subscribe(student);
                        System.out.println(" Подписались на: " + journal.getName());
                    } else if (jChoice.equals("2")) {
                        journal.unsubscribe(student);
                        System.out.println(" Отписались от: " + journal.getName());
                    }
                } catch (Exception e) { System.out.println("Error."); }
                break;
            case "12":
                viewMessagesFlow();
                break;
            case "13":
                sendMessageFlow();
                break;
            case "14":
                sendTechRequestFlowForUser();
                break;
            case "15":
                viewMyRequestsFlow();
                break;
            case "16":
                for (CourseSection s : sys.getSections()) {
                    System.out.println("Course: " + s.getCourse().getTitle());
                    System.out.println("  Lecture: " + (s.getLectureTeacher() != null ? s.getLectureTeacher().getFullName() + " | Rating: " + String.format("%.1f", s.getLectureTeacher().getRating()) : "TBD"));
                    System.out.println("  Practice: " + (s.getPracticeTeacher() != null ? s.getPracticeTeacher().getFullName() + " | Rating: " + String.format("%.1f", s.getPracticeTeacher().getRating()) : "TBD"));
                }
                break;
            case "17":
                if (student.getRegisteredSections().isEmpty()) {
                    System.out.println("No sections registered."); break;
                }
                for (CourseSection s : student.getRegisteredSections()) {
                    System.out.println(s.getCourse().getTitle() + ":");
                    for (Lesson l : s.getLessons())
                        System.out.println("  " + l);
                }
                break;
            case "18":
                if (sys.getOfficialRequests().isEmpty()) { 
                    System.out.println("No official messages."); break; 
                }
                sys.getOfficialRequests().forEach(System.out::println);
                break;
            case "0": logout(); break;
        }
    }
static void registerCourseFlow(Student student) {
    List<CourseSection> sections = sys.getSections();
    if (sections.isEmpty()) { System.out.println("No sections available."); return; }
    System.out.println("Available sections:");
    for (int i = 0; i < sections.size(); i++)
        System.out.println((i + 1) + ". " + sections.get(i));
    try {
        int idx = Integer.parseInt(scanner.nextLine()) - 1;
        CourseSection selected = sections.get(idx);
        
       
        if (student.getRegisteredSections().contains(selected)) {
            throw new CourseAlreadyRegisteredException(selected.getCourse().getCourseCode());
        }
        int credits = selected.getCourse().getCredits();
        int current = student.getCreditsForSemester(selected.getSemester(), selected.getSemesterYear());
        if (current + credits > 21) {
            throw new MaxCreditsExceededException(current + credits, 21);
        }
        
       
        academic.CourseRegistrationRequest req = new academic.CourseRegistrationRequest(student, selected);
        sys.addRegistrationRequest(req);
        System.out.println(" Registration request sent to manager for approval.");
        
    } catch (MaxCreditsExceededException | CourseAlreadyRegisteredException e) {
        System.out.println("No " + e.getMessage());
    } catch (Exception e) { 
        System.out.println("Error: " + e.getMessage()); 
    }
}

public static void dropCourseFlow(Student student) {
        List<CourseSection> sections = student.getRegisteredSections();
        if (sections.isEmpty()) { System.out.println("Not registered in any course."); return; }
        for (int i = 0; i < sections.size(); i++)
            System.out.println((i + 1) + ". " + sections.get(i).getCourse().getTitle());
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            CourseSection dropped = sections.get(idx);
            student.dropCourse(dropped);
            System.out.println(" Вы отписались от: " + dropped.getCourse().getTitle());
        } catch (Exception e) { System.out.println("Error."); }
    }

public static void rateTeacherFlow(Student student) {
        List<Teacher> teachers = new ArrayList<>();
        for (User u : sys.getUsers()) if (u instanceof Teacher) teachers.add((Teacher) u);
        if (teachers.isEmpty()) { System.out.println("No teachers."); return; }
        for (int i = 0; i < teachers.size(); i++)
            System.out.println((i + 1) + ". " + teachers.get(i).getFullName());
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.print("Rating (1-5): ");
            student.rateTeacher(teachers.get(idx), Integer.parseInt(scanner.nextLine()));
        } catch (Exception e) { System.out.println("Error."); }
    }

public static void sendTechRequestFlowForUser() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Description: ");
        String desc = scanner.nextLine();
        
        TechSupportRequest req = new TechSupportRequest(title, desc, currentUser);
        for (User u : sys.getUsers()) {
            if (u instanceof TechSupportSpecialist) {
                ((TechSupportSpecialist) u).addRequest(req);
                System.out.println(" Request sent to tech support.");
                return;
            }
        }
        System.out.println("No tech support specialist found.");
    }

public static void viewMyRequestsFlow() {
        boolean found = false;
        for (User u : sys.getUsers()) {
            if (u instanceof TechSupportSpecialist) {
                for (TechSupportRequest r : ((TechSupportSpecialist) u).viewRequests()) {
                    if (r.getRequestedBy().equals(currentUser)) {
                        System.out.println(r);
                        found = true;
                    }
                }
            }
        }
        if (!found) System.out.println("No requests found.");
    }
}
