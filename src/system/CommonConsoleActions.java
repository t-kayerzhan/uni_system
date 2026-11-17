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


public class CommonConsoleActions {
public static String L(String key) {
        Language lang = currentUser != null ? currentUser.getLanguage() : Language.EN;
        return Localization.get(key, lang);
    }

public static void sendMessageFlow() {
        System.out.print("Recipient username: ");
        String username = scanner.nextLine();
        User recipient = null;
        for (User u : sys.getUsers())
            if (u.getUsername().equals(username)) { recipient = u; break; }
        if (recipient == null) { System.out.println("User not found."); return; }
        System.out.print("Message: ");
        currentUser.sendMessage(recipient, scanner.nextLine());
    }

public static void changeLanguage() {
        System.out.println("1.KZ  2.RU  3.EN");
        String c = scanner.nextLine();
        currentUser.changeLanguage(c.equals("1") ? Language.KZ : c.equals("2") ? Language.RU : Language.EN);
    }

public static void logout() {
        auth.invalidateSession(sessionId);
        sys.logAction(currentUser.getId(), "Logged out: " + currentUser.getFullName());
        DataStorage.getInstance().saveAll(sys);
        System.out.println("Logged out. Goodbye, " + currentUser.getFullName() + "!");
        currentUser = null;
        sessionId = null;
    }

public static void addPaperFlow(Researcher researcher) {
    System.out.print("Title: ");
    String title = scanner.nextLine();
    System.out.print("Authors (comma separated): ");
    List<String> authors = Arrays.asList(scanner.nextLine().split(","));
    System.out.print("Pages: ");
    int pages = Integer.parseInt(scanner.nextLine().trim());
    System.out.print("DOI: ");
    String doi = scanner.nextLine();
    System.out.print("Citations: ");
    int citations = Integer.parseInt(scanner.nextLine().trim());
    
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
    
    ResearchPaper paper = new ResearchPaper(title, authors, "", pages, new Date(), doi);
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

public static void viewMessagesFlow() {
        List<Message> messages = currentUser.viewMessages();
        if (messages.isEmpty()) {
            System.out.println("No messages.");
            return;
        }
        for (int i = 0; i < messages.size(); i++) {
            System.out.println((i + 1) + ". " + messages.get(i));
        }
        System.out.print("Enter message number to mark as read (0 to skip): ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            if (idx >= 0 && idx < messages.size()) {
                messages.get(idx).markAsRead();
                System.out.println("✓ Marked as read.");
            }
        } catch (Exception e) {}
    }

public static void startupFlow(Researcher researcher) {
    System.out.println("1. Create startup");
    System.out.println("2. View startups");
    System.out.println("3. Add founder to startup");
    System.out.println("4. Close startup");
    String c = scanner.nextLine();
    
    if (c.equals("1")) {

        System.out.print("Startup name: ");
        String name = scanner.nextLine().trim();

        if (name.isEmpty()) {
            System.out.println("Startup name cannot be empty.");
            return;
        }

        System.out.print("Description: ");
        String desc = scanner.nextLine().trim();

        ResearchProject linkedProject = null;

        System.out.print("Link to a research project? (y/n): ");
        String ans = scanner.nextLine();

        if (ans.equalsIgnoreCase("y")) {

            List<ResearchProject> projects =
                    researcher.getProjects();

            if (projects.isEmpty()) {

                System.out.println("You have no research projects.");

            } else {

                System.out.println("Select project:");

                for (int i = 0; i < projects.size(); i++) {

                    System.out.println(
                            (i + 1)
                            + ". "
                            + projects.get(i).getTopic()
                            + " | Papers: "
                            + projects.get(i).getPublishedPapers().size()
                    );
                }

                try {

                    int idx =
                            Integer.parseInt(scanner.nextLine()) - 1;

                    if (idx >= 0 && idx < projects.size()) {

                        linkedProject = projects.get(idx);

                    } else {

                        System.out.println("Invalid project.");
                        return;
                    }

                } catch (Exception e) {

                    System.out.println("Invalid input.");
                    return;
                }
            }
        }

        Startup startup =
                new Startup(name, desc, linkedProject);

        startup.addFounder((User) researcher);

        sys.addStartup(startup);

        System.out.println(" Startup created: " + startup);
    
        
    } else if (c.equals("2")) {
        if (sys.getStartups().isEmpty()) { System.out.println("No startups."); return; }
        sys.getStartups().forEach(System.out::println);
        
    } else if (c.equals("3")) {
        if (sys.getStartups().isEmpty()) { System.out.println("No startups."); return; }
        sys.getStartups().forEach(System.out::println);
        System.out.print("Select startup number: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            System.out.print("Enter username of new founder: ");
            String username = scanner.nextLine();
            for (User u : sys.getUsers()) {
                if (u.getUsername().equals(username)) {
                    sys.getStartups().get(idx).addFounder(u);
                    return;
                }
            }
            System.out.println("User not found.");
        } catch (Exception e) { System.out.println("Error."); }
        
    } else if (c.equals("4")) {
        if (sys.getStartups().isEmpty()) { System.out.println("No startups."); return; }
        sys.getStartups().forEach(System.out::println);
        System.out.print("Select startup number to close: ");
        try {
            int idx = Integer.parseInt(scanner.nextLine()) - 1;
            sys.getStartups().get(idx).setActive(false);
            System.out.println(" Startup closed.");
        } catch (Exception e) { System.out.println("Error."); }
    }
}
}
