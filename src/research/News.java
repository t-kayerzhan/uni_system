package research;

import enums.NewsTopic;
import users.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class News implements Serializable {
    private static int counter = 1;
    private int newsId;
    private String title;
    private String content;
    private NewsTopic topic;
    private boolean pinned;
    private User author;
    private Date publishedAt;
    private List<String> comments;
    public News(String title, String content, NewsTopic topic, User author) {
        this.newsId = counter++;
        this.title = title;
        this.content = content;
        this.topic = topic;
        this.pinned = (topic == NewsTopic.RESEARCH); 
        this.author = author;
        this.publishedAt = new Date();
        this.comments = new ArrayList<>();
    }

    public void addComment(User author, String text) {
        comments.add("[" + author.getFullName() + "]: " + text);
        System.out.println("Comment added by " + author.getFullName());
    }

    public int getNewsId() { return newsId; }
    public String getTitle() { return title; }
    public String getContent() { return content; }
    public NewsTopic getTopic() { return topic; }
    public boolean isPinned() { return pinned; }
    public void setPinned(boolean flag) { this.pinned = flag; }
    public User getAuthor() { return author; }
    public Date getPublishedAt() { return publishedAt; }
    public List<String> getComments() { return comments; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(" Title: ").append(this.title).append("\n")
          .append("   Content: ").append(this.content).append("\n")
          .append("    Comments:\n");
          
        if (this.comments == null || this.comments.isEmpty()) {
            sb.append("      [No comments yet]\n");
        } else {
            for (Object comment : this.comments) {
                sb.append("      ↳ ").append(comment).append("\n");
            }
        }
        return sb.toString();
    }
}
