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

/** Extracted console logic from Main. */
public class ManagerConsole {
public static void showManagerMenu() {
    	System.out.println("\n==== " + L("manager.menu") + " =============================");
    	System.out.println("1. " + L("manager.assign"));
    	System.out.println("2. " + L("manager.students"));
    	System.out.println("3. " + L("manager.report"));
    	System.out.println("4. " + L("manager.news"));
    	System.out.println("5. " + L("manager.courses"));
    	System.out.println("6. " + L("manager.approve"));
        System.out.println("7. View complaints");
        System.out.println("8. Resolve complaint");
        System.out.println("9. Official messages");
        System.out.println("10. Book room");
        System.out.println("11. Top researcher by school");
        System.out.println("12. " + L("menu.change_lang"));
    	System.out.println("0. " + L("menu.logout"));
    	System.out.print(L("menu.choice"));
        String choice = scanner.nextLine();
        Manager manager = (Manager) currentUser;

        List<Student> students = new ArrayList<>();
        List<Teacher> teachers = new ArrayList<>();
        for (User u : sys.getUsers()) {
            if (u instanceof Student && !(u instanceof GraduateStudent)) students.add((Student) u);
            if (u instanceof Teacher) teachers.add((Teacher) u);
        }

        switch (choice) {
        case "1":
            if (sys.getSections().isEmpty() || teachers.isEmpty()) {
                System.out.println("No sections or teachers.");
                break;
            }
            System.out.println("Select section:");
            for (int i = 0; i < sys.getSections().size(); i++)
                System.out.println((i + 1) + ". " + sys.getSections().get(i).getCourse().getTitle());
            try {
                int sIdx = Integer.parseInt(scanner.nextLine()) - 1;
                if (sIdx < 0 || sIdx >= sys.getSections().size()) break;
                
                System.out.println("Select teacher:");
                for (int i = 0; i < teachers.size(); i++)
                    System.out.println((i + 1) + ". " + teachers.get(i).getFullName());
                int tIdx = Integer.parseInt(scanner.nextLine()) - 1;
                if (tIdx < 0 || tIdx >= teachers.size()) break;
                
                System.out.println("Lesson type: 1.LECTURE  2.PRACTICE");
                String lt = scanner.nextLine();
                LessonType type = lt.equals("2") ? LessonType.PRACTICE : LessonType.LECTURE;
                
                manager.assignTeacher(sys.getSections().get(sIdx), teachers.get(tIdx), type);
            } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
            break;
        case "2":
            System.out.println("\nWho do you want to view?");
            System.out.println("1. Students sorted by GPA");
            System.out.println("2. Teachers");
            System.out.print("Choice: ");
            String subChoice = scanner.nextLine();

            if (subChoice.equals("1")) {
                System.out.println("\n===== STUDENTS SORTED BY GPA =====");
                
               
                List<users.Student> sortedStudents = sys.getUsers().stream()
                    .filter(u -> u instanceof users.Student)
                    .map(u -> (users.Student) u)
                    .sorted((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa())) 
                    .collect(Collectors.toList());

                
                if (sortedStudents.isEmpty()) {
                    System.out.println("No students registered in the system.");
                } else {
                    int idx = 1;
                    
                    for (users.Student s : sortedStudents) {
                        System.out.printf("%d. %s | GPA: %.2f | ID: %s\n", 
                            idx++, 
                            s.getFullName(), 
                            s.getGpa(), 
                            s.getId()
                        );
                    }
                }
            }else if (subChoice.equals("2")) {
               
                System.out.println("\nHow do you want to sort teachers?");
                System.out.println("1. By Student Rating (высокий рейтинг в начале)");
                System.out.println("2. By H-Index (научная цитируемость в начале)");
                System.out.print("Choice: ");
                String sortChoice = scanner.nextLine();

                List<users.Teacher> sortedTeachers;

                if (sortChoice.equals("1")) {
                    System.out.println("\n===== TEACHERS SORTED BY STUDENT RATING =====");
                    sortedTeachers = sys.getTeachersSortedByRating();
                } else if (sortChoice.equals("2")) {
                    System.out.println("\n===== TEACHERS SORTED BY H-INDEX =====");
                    sortedTeachers = sys.getTeachersSortedByHIndex();
                } else {
                    System.out.println("Invalid sorting choice. Showing unsorted.");
                    sortedTeachers = sys.getTeachersSortedByRating(); 
                }
                
                
                if (sortedTeachers.isEmpty()) {
                    System.out.println("No teachers registered in the system.");
                } else {
                    int idx = 1;
                    for (users.Teacher t : sortedTeachers) {
                        System.out.printf("%d. %s | Rating: %.2f/5.0 | H-Index: %d | Papers: %d\n", 
                            idx++, 
                            t.getFullName(), 
                            t.getRating(), 
                            t.getHIndex(),
                            t.getPapers().size()
                        );
                    }
                }
            } else {
                System.out.println("Invalid sub-choice.");
            }
            break;
            case "3":
                manager.createAcademicReport(students);
                break;
            case "4":
                System.out.print("News title: ");
                String newsTitle = scanner.nextLine();
                System.out.print("News content: ");
                String newsContent = scanner.nextLine();
                System.out.println("Topic: 1.GENERAL  2.ACADEMIC  3.RESEARCH");
                String topicChoice = scanner.nextLine();
                NewsTopic topic;
                switch (topicChoice) {
                    case "2": topic = NewsTopic.ACADEMIC; break;
                    case "3": topic = NewsTopic.RESEARCH; break;
                    default:  topic = NewsTopic.GENERAL;
                }
                News news = new News(newsTitle, newsContent, topic, currentUser);
                manager.publishNews(news);
                sys.addNews(news);
                break;
            case "5":
                sys.getCourses().forEach(System.out::println);
                break;
            case "6":
                List<CourseRegistrationRequest> reqs = sys.getPendingRegistrations();
                if (reqs.isEmpty()) { System.out.println("No pending registrations."); break; }
                for (int i = 0; i < reqs.size(); i++)
                    System.out.println((i + 1) + ". " + reqs.get(i));
                System.out.print("Select number (0 to cancel): ");
                try {
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx < 0) break;
                    CourseRegistrationRequest req = reqs.get(idx);
                    System.out.println("1.Approve  2.Reject");
                    String d = scanner.nextLine();
                    if (d.equals("1")) {
                        try {
                            req.getStudent().registerForCourse(req.getSection());
                            manager.approveRegistration(req.getStudent(), req.getSection().getCourse());
                            System.out.println(" Approved.");
                        } catch (CourseAlreadyRegisteredException e) {
                            System.out.println("Already enrolled, approving anyway.");
                            manager.approveRegistration(req.getStudent(), req.getSection().getCourse());
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        reqs.remove(idx);
                    }
                     else if (d.equals("2")) {
                        System.out.print("Reason: ");
                        manager.rejectRegistration(req.getStudent(), req.getSection().getCourse(), scanner.nextLine());
                        reqs.remove(idx);
                    }
                } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
                break;
            case "7":
                ManagerConsole.viewComplaintsFlow();
                break;
            case "8":
                ManagerConsole.resolveComplaintFlow();
                break;
            case "9":
                ManagerConsole.officialMessagesFlow(manager);
                break;
            case "10":
                ManagerConsole.bookRoomFlow();
                break;
            case "11":
                ManagerConsole.topResearcherBySchoolFlow();
                break;
            case "12": changeLanguage(); break;
            case "0": logout(); break;
        }
    }

public static void viewComplaintsFlow() {
        List<Complaint> complaints = sys.getComplaints();
        if (complaints.isEmpty()) { System.out.println("No complaints."); return; }
        for (int i = 0; i < complaints.size(); i++) {
            System.out.println((i + 1) + ". " + complaints.get(i));
        }
    }

public static void resolveComplaintFlow() {
        List<Complaint> complaints = sys.getComplaints();
        if (complaints.isEmpty()) { System.out.println("No complaints."); return; }
        for (int i = 0; i < complaints.size(); i++) {
            System.out.println((i + 1) + ". " + complaints.get(i));
        }
        System.out.print("Select complaint to resolve: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx >= 0 && idx < complaints.size()) complaints.get(idx).markResolved();
        } catch (Exception e) { System.out.println("Error."); }
    }

public static void officialMessagesFlow(Manager manager) {
        System.out.println("1. View official messages");
        System.out.println("2. Create official message");
        String c = scanner.nextLine();
        if (c.equals("1")) {
            if (sys.getOfficialRequests().isEmpty()) { System.out.println("No official messages."); return; }
            sys.getOfficialRequests().forEach(System.out::println);
        } else if (c.equals("2")) {
            System.out.print("Title: "); String title = scanner.nextLine();
            System.out.print("Description: "); String desc = scanner.nextLine();
            System.out.print("Signed by: "); String signedBy = scanner.nextLine();
            OfficialRequest req = new OfficialRequest(title, desc, manager, signedBy);
            sys.addOfficialRequest(req);
            System.out.println(" Official message created.");
        }
    }

public static void bookRoomFlow() {
        List<Room> rooms = sys.getRooms();
        if (rooms.isEmpty()) { System.out.println("No rooms."); return; }
        for (int i = 0; i < rooms.size(); i++) System.out.println((i + 1) + ". " + rooms.get(i));
        System.out.print("Select room: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx < 0 || idx >= rooms.size()) return;
            System.out.print("Purpose: "); String purpose = scanner.nextLine();
            System.out.print("Student count: "); int count = Integer.parseInt(scanner.nextLine().trim());
            rooms.get(idx).book(purpose, count);
        } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
    }

public static void topResearcherBySchoolFlow() {
        System.out.print("School/department name: ");
        String school = scanner.nextLine();
        Researcher top = sys.getTopCitedResearcherBySchool(school);
        if (top == null) { System.out.println("No researcher found for this school."); return; }
        User u = (User) top;
        System.out.println("Top researcher: " + u.getFullName()
                + " | H-index: " + top.getHIndex()
                + " | Total citations: " + sys.getTotalCitations(top));
    }
}
