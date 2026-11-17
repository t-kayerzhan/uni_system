package research;

import enums.CitationFormat;
import java.util.Objects;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ResearchPaper implements Serializable, Comparable<ResearchPaper> {
    private static int counter = 1;
    private int paperId;
    private String title;
    private List<String> authors;
    private String journal;
    private int pages;
    private Date publishDate;
    private String doi;
    private int citations;
    private List<String> keywords;

    public ResearchPaper(String title, List<String> authors, String journal,
                         int pages, Date publishDate, String doi) {
        this.paperId = counter++;
        this.title = title;
        this.authors = authors;
        this.journal = journal;
        this.pages = pages;
        this.publishDate = publishDate;
        this.doi = doi;
        this.citations = 0;
    }

    public String getCitation(CitationFormat format) {
        if (format == CitationFormat.PLAIN_TEXT) {
            return String.join(", ", authors) + ". \"" + title + ".\" "
                    + journal + " (" + publishDate.getYear() + 1900 + "). DOI: " + doi;
        } else { 
            return "@article{" + authors.get(0).split(" ")[0].toLowerCase() + (publishDate.getYear() + 1900) + ",\n"
                    + "  title={" + title + "},\n"
                    + "  author={" + String.join(" and ", authors) + "},\n"
                    + "  journal={" + journal + "},\n"
                    + "  year={" + (publishDate.getYear() + 1900) + "},\n"
                    + "  pages={1-" + pages + "},\n"
                    + "  doi={" + doi + "}\n"
                    + "}";
        }
    }

    public void incrementCitations() { citations++; }

    public int getPaperId() { return paperId; }
    public String getTitle() { return title; }
    public List<String> getAuthors() { return authors; }
    public String getJournal() { return journal; }
    public int getPages() { return pages; }
    public Date getPublishDate() { return publishDate; }
    public String getDoi() { return doi; }
    public int getCitations() { return citations; }
    public void setCitations(int c) { this.citations = c; }
    public List<String> getKeywords() { return keywords; }
    public void setKeywords(List<String> keywords) { this.keywords = keywords; }

    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations); 
    }

    @Override
    public String toString() {
        return "[PAPER #" + paperId + "] \"" + title + "\""
                + " | Journal: " + journal
                + " | Citations: " + citations
                + " | Pages: " + pages
                + " | DOI: " + doi;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ResearchPaper)) return false;
        ResearchPaper other = (ResearchPaper) obj;
        return this.doi.equals(other.doi);
    }

    @Override
    public int hashCode() {
        return Objects.hash(doi);
    }
}
