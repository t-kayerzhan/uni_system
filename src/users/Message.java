package users;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private static int counter = 1;
    private int messageId;
    private User from;
    private User to;
    private String subject;
    private String text;
    private Date sentAt;
    private boolean isRead;

    public Message(User from, User to, String subject, String text) {
        this.messageId = counter++;
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.sentAt = new Date();
        this.isRead = false;
    }

    public void markAsRead() { this.isRead = true; }
    public String getText() { return text; }
    public User getFrom() { return from; }
    public User getTo() { return to; }
    public String getSubject() { return subject; }
    public Date getSentAt() { return sentAt; }
    public boolean isRead() { return isRead; }

    @Override
    public String toString() {
        return "[MSG #" + messageId + "] From: " + from.getFullName()
                + " | Subject: " + subject
                + " | Read: " + isRead
                + "\n  " + text;
    }
}
