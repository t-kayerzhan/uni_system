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


public class MenuRouter {
public static void showUserMenu() {
        if      (currentUser instanceof Admin)                AdminConsole.showAdminMenu();
        else if (currentUser instanceof Teacher)              TeacherConsole.showTeacherMenu();
        else if (currentUser instanceof GraduateStudent)      GraduateStudentConsole.showGradStudentMenu();
        else if (currentUser instanceof Student)              StudentConsole.showStudentMenu();
        else if (currentUser instanceof Manager)              ManagerConsole.showManagerMenu();
        else if (currentUser instanceof TechSupportSpecialist) TechSupportConsole.showTechMenu();
    }
}
