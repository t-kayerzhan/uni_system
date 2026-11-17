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


public class ResearchProjectConsole {
public static void researchProjectFlow(Researcher researcher) {
        System.out.println("1. View projects");
        System.out.println("2. Create project");
        System.out.println("3. Join project");
        System.out.println("4. Add paper to project");
        String c = scanner.nextLine();
        if (c.equals("1")) {
            if (sys.getResearchProjects().isEmpty()) { System.out.println("No research projects."); return; }
            sys.getResearchProjects().forEach(System.out::println);
        } else if (c.equals("2")) {
            System.out.print("Topic: "); String topic = scanner.nextLine();
            System.out.print("Description: "); String desc = scanner.nextLine();
            ResearchProject project = new ResearchProject(topic, desc);
            try { project.addParticipant(researcher); } catch (Exception e) { System.out.println(e.getMessage()); }
            researcher.addProject(project);
            sys.addResearchProject(project);
            System.out.println("✓ Research project created.");
        } else if (c.equals("3")) {
            List<ResearchProject> projects = sys.getResearchProjects();
            if (projects.isEmpty()) { System.out.println("No research projects."); return; }
            for (int i = 0; i < projects.size(); i++) System.out.println((i + 1) + ". " + projects.get(i));
            System.out.print("Select project: ");
            try {
                int idx = Integer.parseInt(scanner.nextLine()) - 1;
                if (idx < 0 || idx >= projects.size()) return;
                projects.get(idx).addParticipant(researcher);
                researcher.addProject(projects.get(idx));
            } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
        } else if (c.equals("4")) {
            List<ResearchProject> projects = sys.getResearchProjects();
            List<ResearchPaper> papers = researcher.getPapers();
            if (projects.isEmpty()) { System.out.println("No research projects."); return; }
            if (papers.isEmpty()) { System.out.println("You have no papers."); return; }
            for (int i = 0; i < projects.size(); i++) System.out.println((i + 1) + ". " + projects.get(i));
            System.out.print("Select project: ");
            try {
                int prIdx = Integer.parseInt(scanner.nextLine()) - 1;
                for (int i = 0; i < papers.size(); i++) System.out.println((i + 1) + ". " + papers.get(i));
                System.out.print("Select paper: ");
                int paIdx = Integer.parseInt(scanner.nextLine()) - 1;
                if (prIdx >= 0 && prIdx < projects.size() && paIdx >= 0 && paIdx < papers.size()) {
                    projects.get(prIdx).addPaper(papers.get(paIdx));
                }
            } catch (Exception e) { System.out.println("Error: " + e.getMessage()); }
        }
    }
}
