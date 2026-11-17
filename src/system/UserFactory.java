package system;

import enums.*;
import users.*;

public class UserFactory {

    public static Student createStudent(String id, String username, String password,
                                         String firstName, String lastName, String email,
                                         String studentId, int year, String major) {
        return new Student(id, username, password, firstName, lastName, email, studentId, year, major);
    }
    
    public static Teacher createTeacher(String id, String username, String password,
            String firstName, String lastName, String email,
            double salary, String department, TeacherPosition position) {
            return new Teacher(id, username, password, firstName, lastName, email, salary, department, position);
}

    public static Manager createManager(String id, String username, String password,
                                         String firstName, String lastName, String email,
                                         double salary, String department, ManagerType type) {
        return new Manager(id, username, password, firstName, lastName, email, salary, department, type);
    }

    public static Admin createAdmin(String id, String username, String password,
                                     String firstName, String lastName, String email,
                                     double salary, String department) {
        return new Admin(id, username, password, firstName, lastName, email, salary, department);
    }

    public static GraduateStudent createGraduate(String id, String username, String password,
                                                   String firstName, String lastName, String email,
                                                   String studentId, int year, String major, GraduateType type) {
        return new GraduateStudent(id, username, password, firstName, lastName, email, studentId, year, major, type);
    }

    public static TechSupportSpecialist createTechSupport(String id, String username, String password,
                                                            String firstName, String lastName, String email,
                                                            double salary, String department) {
        return new TechSupportSpecialist(id, username, password, firstName, lastName, email, salary, department);
    }
}
