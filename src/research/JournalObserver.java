package research;

public interface JournalObserver {
    void onNewPaperPublished(ResearchPaper paper, Journal journal);
}
