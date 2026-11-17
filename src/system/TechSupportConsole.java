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


public class TechSupportConsole {
public static void showTechMenu() {
    	System.out.println("\n====" + L("tech.menu") + " =============================");
    	System.out.println("1. " + L("tech.view"));
    	System.out.println("2. " + L("tech.accept"));
    	System.out.println("3. " + L("tech.reject"));
    	System.out.println("4. " + L("tech.done"));
    	System.out.println("9. " + L("menu.change_lang"));
    	System.out.println("0. " + L("menu.logout"));
    	System.out.print(L("menu.choice"));
        String choice = scanner.nextLine();
        TechSupportSpecialist tech = (TechSupportSpecialist) currentUser;

        switch (choice) {
            case "1":
                List<TechSupportRequest> newR = tech.viewNewRequests();
                if (newR.isEmpty()) System.out.println("No new requests.");
                else newR.forEach(System.out::println);
                break;
            case "2":
                List<TechSupportRequest> all = tech.viewRequests();
                if (all.isEmpty()) { System.out.println("No requests."); break; }
                for (int i = 0; i < all.size(); i++)
                    System.out.println((i + 1) + ". " + all.get(i));
                System.out.print("Select request number: ");
                try {
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx >= 0 && idx < all.size()) tech.acceptRequest(all.get(idx));
                } catch (Exception e) {}
                break;
            case "3":
                List<TechSupportRequest> all2 = tech.viewRequests();
                if (all2.isEmpty()) { System.out.println("No requests."); break; }
                for (int i = 0; i < all2.size(); i++)
                    System.out.println((i + 1) + ". " + all2.get(i));
                System.out.print("Select request number: ");
                try {
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx >= 0 && idx < all2.size()) {
                        System.out.print("Rejection reason: ");
                        tech.rejectRequest(all2.get(idx), scanner.nextLine());
                    }
                } catch (Exception e) {}
                break;
            case "4":
                List<TechSupportRequest> all3 = tech.viewRequests();
                if (all3.isEmpty()) { System.out.println("No requests."); break; }
                for (int i = 0; i < all3.size(); i++)
                    System.out.println((i + 1) + ". " + all3.get(i));
                System.out.print("Select request number: ");
                try {
                    int idx = Integer.parseInt(scanner.nextLine()) - 1;
                    if (idx >= 0 && idx < all3.size()) tech.markDone(all3.get(idx));
                } catch (Exception e) {}
                break;
            case "9": changeLanguage(); break;
            case "0": logout(); break;
        }
    }
}
