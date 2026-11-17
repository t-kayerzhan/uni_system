package research;

import exceptions.NotAResearcherException;
import java.util.Objects; 

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ResearchProject implements Serializable {
    private static int counter = 1;
    private int projectId;
    private String topic;
    private String description;
    private List<Researcher> participants;
    private List<ResearchPaper> publishedPapers;
    private Date startDate;
    private Date endDate;
    private boolean isActive;

    public ResearchProject(String topic, String description) {
        this.projectId = counter++;
        this.topic = topic;
        this.description = description;
        this.participants = new ArrayList<>();
        this.publishedPapers = new ArrayList<>();
        this.startDate = new Date();
        this.isActive = true;
    }

    public void addParticipant(Object person) throws NotAResearcherException {
        if (!(person instanceof Researcher)) {
            throw new NotAResearcherException(person.toString());
        }
        Researcher researcher = (Researcher) person;
        if (!participants.contains(researcher)) {
            participants.add(researcher);
        } else {
        }
    }

    public void removeParticipant(Researcher researcher) {
        participants.remove(researcher);
    }

    public void addPaper(ResearchPaper paper) {
        if (!publishedPapers.contains(paper)) {
            publishedPapers.add(paper);
        } else {
        }
    }

    public List<Researcher> getParticipants() { return participants; }
    public List<ResearchPaper> getPublishedPapers() { return publishedPapers; }
    public int getProjectId() { return projectId; }
    public String getTopic() { return topic; }
    public String getDescription() { return description; }
    public boolean isActive() { return isActive; }
    public void setActive(boolean active) { this.isActive = active; }
    public Date getStartDate() { return startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }

    @Override
    public String toString() {
        return "[PROJECT #" + projectId + "] " + topic
                + " | Active: " + isActive
                + " | Participants: " + participants.size()
                + " | Papers: " + publishedPapers.size();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ResearchProject)) return false;
        ResearchProject other = (ResearchProject) obj;
        return this.projectId == other.projectId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId);
    }
}
