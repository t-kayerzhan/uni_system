package users;

import enums.Language;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class User implements Serializable, research.JournalObserver {
    private String id;
    private String username;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String email;
    private Language language;
    private List<Message> inbox;

    public User(String id, String username, String password, String firstName, String lastName, String email) {
        this.id = id;
        this.username = username;
        this.passwordHash = hashPassword(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.language = Language.EN;
        this.inbox = new ArrayList<>();
    }

    private String hashPassword(String password) {
        return Integer.toHexString(password.hashCode());
    }

    public boolean login(String password) {
        return this.passwordHash.equals(hashPassword(password));
    }

    public void logout() {
        System.out.println(getFullName() + " logged out.");
    }

    public void changeLanguage(Language lang) {
        this.language = lang;
        System.out.println("Language changed to: " + lang);
    }

    public Message sendMessage(User to, String text) {
        Message msg = new Message(this, to, "Message from " + getFullName(), text);
        to.inbox.add(msg);
        System.out.println("Message sent to " + to.getFullName());
        return msg;
    }

    public List<Message> viewMessages() {
        return inbox;
    }

    public List<Message> getUnreadMessages() {
        List<Message> unread = new ArrayList<>();
        for (Message m : inbox) {
            if (!m.isRead()) unread.add(m);
        }
        return unread;
    }

    public String getFullName() { return firstName + " " + lastName; }
    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Language getLanguage() { return language; }
    public String getPasswordHash() { return passwordHash; }
    public void setPassword(String newPassword) { this.passwordHash = hashPassword(newPassword); }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof User)) return false;
        User other = (User) obj;
        return this.id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "[" + getClass().getSimpleName() + "] " + getFullName() + " (username: " + username + ", email: " + email + ")";
    }

    
    @Override
    public void onNewPaperPublished(research.ResearchPaper paper, research.Journal journal) {
        System.out.println("[NOTIFICATION] " + getFullName() + ": New paper in " + journal.getName() + " - " + paper.getTitle());
        Message notification = new Message(this, this,
            " New paper in " + journal.getName(),
            paper.getTitle() + " | DOI: " + paper.getDoi());
        inbox.add(notification);
    }
}
