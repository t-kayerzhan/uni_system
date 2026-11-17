package research;

import users.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Startup implements Serializable {
    private static int counter = 1;
    private int startupId;
    private String name;
    private String description;
    private List<User> founders;
    private ResearchProject linkedProject;
    private Date foundedDate;
    private boolean isActive;

    public Startup(String name, String description, ResearchProject linkedProject) {
        this.startupId = counter++;
        this.name = name;
        this.description = description;
        this.linkedProject = linkedProject;
        this.founders = new ArrayList<>();
        this.foundedDate = new Date();
        this.isActive = true;
    }

    public void addFounder(User user) {
        founders.add(user);
    }

    public void removeFounder(User user) { founders.remove(user); }

    public int getStartupId() { return startupId; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public List<User> getFounders() { return founders; }
    public ResearchProject getLinkedProject() { return linkedProject; }
    public Date getFoundedDate() { return foundedDate; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }

    @Override
    public String toString() {
        return "[STARTUP #" + startupId + "] " + name
                + " | Active: " + isActive
                + " | Founders: " + founders.size()
                + " | Project: " + (linkedProject != null ? linkedProject.getTopic() : "None");
    }
}
