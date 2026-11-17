package research;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class Journal implements Serializable {
    private static int counter = 1;
    private int journalId;
    private String name;
    private String issn;
    private String description;
    private List<ResearchPaper> papers;
    private List<JournalObserver> subscribers;

    public Journal(String name, String issn, String description) {
        this.journalId = counter++;
        this.name = name;
        this.issn = issn;
        this.description = description;
        this.papers = new ArrayList<>();
        this.subscribers = new ArrayList<>();
    }

    public void subscribe(JournalObserver observer) {
        if (!subscribers.contains(observer)) {
            subscribers.add(observer);
        }
    }

    public void unsubscribe(JournalObserver observer) {
        subscribers.remove(observer);
    }

    public void publishPaper(ResearchPaper paper) {
        if (!papers.contains(paper)) {
            papers.add(paper);
            notifySubscribers(paper);
        }
    }

    public void notifySubscribers(ResearchPaper paper) {
        for (JournalObserver observer : subscribers) {
            observer.onNewPaperPublished(paper, this);
        }
    }

    public int getJournalId() { return journalId; }
    public String getName() { return name; }
    public String getIssn() { return issn; }
    public List<ResearchPaper> getPapers() { return papers; }
    public List<JournalObserver> getSubscribers() { return subscribers; }

    @Override
    public String toString() {
        return "[JOURNAL] " + name + " | ISSN: " + issn
                + " | Papers: " + papers.size()
                + " | Subscribers: " + subscribers.size();
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Journal)) return false;
        Journal other = (Journal) obj;
        return this.issn.equals(other.issn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(issn);
    }
}
