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


public class GraduateStudentConsole {
public static void showGradStudentMenu() {
        System.out.println("\n==== " + L("grad.menu") + " =============================");
        System.out.println("1. " + L("grad.student_options"));
        System.out.println("2. " + L("grad.supervisor"));
        System.out.println("3. " + L("grad.hindex"));
        System.out.println("4. " + L("grad.diploma"));
        System.out.println("5. " + L("grad.add_paper"));
        System.out.println("6. " + L("menu.change_lang"));
        System.out.println("7. Research projects");
        System.out.println("0. " + L("menu.logout"));
        System.out.print(L("menu.choice"));
        String choice = scanner.nextLine();
        GraduateStudent grad = (GraduateStudent) currentUser;

        switch (choice) {
            case "1": StudentConsole.showStudentMenu(); break;
            case "2": GraduateStudentConsole.setSupervisorFlow(grad); break;
            case "3": System.out.println("H-Index: " + grad.getHIndex()); break;
            case "4":
                List<ResearchPaper> dp = grad.getDiplomaPapers();
                if (dp.isEmpty()) System.out.println("No diploma papers.");
                else dp.forEach(System.out::println);
                break;
            case "5":
                addPaperFlow(grad);
                break;
            case "6": changeLanguage(); break;
            case "7": ResearchProjectConsole.researchProjectFlow(grad); break;
            case "0": logout(); break;
        }
    }

public static void setSupervisorFlow(GraduateStudent grad) {
        List<Teacher> teachers = new ArrayList<>();
        for (User u : sys.getUsers()) if (u instanceof Teacher) teachers.add((Teacher) u);
        if (teachers.isEmpty()) { System.out.println("No teachers."); return; }
        System.out.println("Select supervisor:");
        for (int i = 0; i < teachers.size(); i++) {
            String hIdx = (teachers.get(i) instanceof Teacher)
                    ? " (h-index: " + ((Teacher) teachers.get(i)).getHIndex() + ")"
                    : "";
            System.out.println((i + 1) + ". " + teachers.get(i).getFullName() + hIdx);
        }
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            grad.setSupervisor(teachers.get(idx));
        } catch (LowHIndexException e) {
            System.out.println("No " + e.getMessage());
        } catch (Exception e) { System.out.println("Error."); }
    }
}
