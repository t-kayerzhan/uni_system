package system;

import academic.CourseSection;
import users.User;
import java.util.Scanner;


public final class AppContext {
    private AppContext() {}

    public static Scanner scanner = new Scanner(System.in);
    public static UniversitySystem sys = UniversitySystem.getInstance();
    public static AuthService auth = new AuthService();
    public static ReportService reports = new ReportService();
    public static String sessionId = null;
    public static User currentUser = null;
    public static CourseSection oopSection = null;
    public static CourseSection dbSection = null;
}
