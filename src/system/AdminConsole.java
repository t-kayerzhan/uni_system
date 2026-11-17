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


public class AdminConsole {
public static void showAdminMenu() {
    	System.out.println("\n=====" + L("admin.menu") + " =============================");
    	System.out.println("1. " + L("admin.view_users"));
    	System.out.println("2. " + L("admin.create_user"));
    	System.out.println("3. " + L("admin.delete_user"));
    	System.out.println("4. " + L("admin.logs"));
    	System.out.println("5. " + L("menu.change_lang"));
    	System.out.println("6. " + L("admin.papers"));
    	System.out.println("7. " + L("admin.update_user"));
    	System.out.println("0. " + L("menu.logout"));
    	System.out.print(L("menu.choice"));
        String choice = scanner.nextLine();
        Admin admin = (Admin) currentUser;

        switch (choice) {
            case "1":
                for (User u : sys.getUsers()) System.out.println(u);
                break;
            case "2":
                AdminConsole.createUserFlow(admin);
                break;
            case "3":
                System.out.print("Enter user ID to delete: ");
                String Id = scanner.nextLine();
                admin.deleteUser(Id, sys.getUsers());
                break;
            case "4":
                admin.viewLogs(sys.getLogs());
                break;
            case "5":
                changeLanguage();
                break;
            case "6":
                System.out.println("Sort by: 1.Citations  2.Date  3.Pages");
                String ps = scanner.nextLine();
                Comparator<ResearchPaper> comp;
                switch (ps) {
                    case "2": comp = UniversityComparators.BY_DATE_DESC;  break;
                    case "3": comp = UniversityComparators.BY_PAGES_DESC; break;
                    default:  comp = UniversityComparators.BY_CITATIONS_DESC;
                }
                sys.printAllPapers(comp);
                break;
            case "7":
                System.out.print("Enter user ID to update: ");
                String updateId = scanner.nextLine();
                User toUpdate = null;
                for (User u : sys.getUsers())
                    if (u.getId().equals(updateId)) { toUpdate = u; break; }
                if (toUpdate == null) { System.out.println("User not found."); break; }
                System.out.println("1.Change email  2.Change password");
                String upChoice = scanner.nextLine();
                if (upChoice.equals("1")) {
                    System.out.print("New email: ");
                    toUpdate.setEmail(scanner.nextLine());
                    System.out.println(" Email updated.");
                } else if (upChoice.equals("2")) {
                    System.out.print("New password: ");
                    toUpdate.setPassword(scanner.nextLine());
                    System.out.println(" Password updated.");
                }
                break;
            case "0": logout(); break;
        }
    }

public static void createUserFlow(Admin admin) {
        try {
            System.out.println("Create role: 1.Student 2.Graduate 3.Teacher 4.Manager 5.TechSupport 6.Admin");
            String role = scanner.nextLine();
            System.out.print("ID: "); String id = scanner.nextLine();
            System.out.print("Username: "); String username = scanner.nextLine();
            System.out.print("Password: "); String password = scanner.nextLine();
            System.out.print("First name: "); String firstName = scanner.nextLine();
            System.out.print("Last name: "); String lastName = scanner.nextLine();
            System.out.print("Email: "); String email = scanner.nextLine();
            User newUser = null;

            switch (role) {
                case "1": {
                    System.out.print("Student ID: "); String studentId = scanner.nextLine();
                    System.out.print("Year: "); int year = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Major: "); String major = scanner.nextLine();
                    System.out.print("School: "); String school = scanner.nextLine();
                    Student st = UserFactory.createStudent(id, username, password, firstName, lastName, email, studentId, year, major);
                    st.setSchool(school);
                    newUser = st;
                    break;
                }
                case "2": {
                    System.out.print("Student ID: "); String studentId = scanner.nextLine();
                    System.out.print("Year: "); int year = Integer.parseInt(scanner.nextLine().trim());
                    System.out.print("Major: "); String major = scanner.nextLine();
                    System.out.print("School: "); String school = scanner.nextLine();
                    System.out.println("Degree: 1.MASTER 2.PHD");
                    GraduateType gt = scanner.nextLine().equals("2") ? GraduateType.PHD : GraduateType.MASTER;
                    GraduateStudent gs = UserFactory.createGraduate(id, username, password, firstName, lastName, email, studentId, year, major, gt);
                    gs.setSchool(school);
                    newUser = gs;
                    break;
                }
                case "3": {
                    System.out.print("Salary: "); double salary = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Department/School: "); String dep = scanner.nextLine();
                    System.out.println("Position: 1.TUTOR 2.LECTOR 3.SENIOR_LECTURER 4.PROFESSOR");
                    String pos = scanner.nextLine();
                    TeacherPosition position = pos.equals("4") ? TeacherPosition.PROFESSOR : pos.equals("3") ? TeacherPosition.SENIOR_LECTURER : pos.equals("2") ? TeacherPosition.LECTURER : TeacherPosition.TUTOR;
                    newUser = UserFactory.createTeacher(id, username, password, firstName, lastName, email, salary, dep, position);
                    break;
                }
                case "4": {
                    System.out.print("Salary: "); double salary = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Department: "); String dep = scanner.nextLine();
                    System.out.println("Manager type: 1.OR 2.DEPARTMENT 3.DEAN");
                    String mt = scanner.nextLine();
                    ManagerType type = mt.equals("3") ? ManagerType.DEAN_OFFICE : mt.equals("2") ? ManagerType.DEPARTMENT : ManagerType.OR;
                    newUser = UserFactory.createManager(id, username, password, firstName, lastName, email, salary, dep, type);
                    break;
                }
                case "5": {
                    System.out.print("Salary: "); double salary = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Department: "); String dep = scanner.nextLine();
                    newUser = UserFactory.createTechSupport(id, username, password, firstName, lastName, email, salary, dep);
                    break;
                }
                case "6": {
                    System.out.print("Salary: "); double salary = Double.parseDouble(scanner.nextLine().trim());
                    System.out.print("Department: "); String dep = scanner.nextLine();
                    newUser = UserFactory.createAdmin(id, username, password, firstName, lastName, email, salary, dep);
                    break;
                }
                default:
                    System.out.println("Unknown role."); return;
            }
            sys.addUser(newUser);
            admin.createUser(newUser);
            System.out.println(" User created: " + newUser);
        } catch (Exception e) {
            System.out.println("Error while creating user: " + e.getMessage());
        }
    }
}
