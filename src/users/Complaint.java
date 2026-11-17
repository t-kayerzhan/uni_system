package users;

import enums.ComplaintUrgency;
import java.io.Serializable;
import java.util.Date;

public class Complaint implements Serializable {
    private static int counter = 1;
    private int complaintId;
    private Teacher sender;
    private Student targetStudent;
    private ComplaintUrgency urgency;
    private String description;
    private Date createdAt;
    private boolean resolved;

    public Complaint(Teacher sender, Student targetStudent, ComplaintUrgency urgency, String description) {
        this.complaintId = counter++;
        this.sender = sender;
        this.targetStudent = targetStudent;
        this.urgency = urgency;
        this.description = description;
        this.createdAt = new Date();
        this.resolved = false;
    }

    public ComplaintUrgency getUrgency() { return urgency; }
    public Teacher getSender() { return sender; }
    public Student getTargetStudent() { return targetStudent; }
    public String getDescription() { return description; }
    public boolean isResolved() { return resolved; }
    public void markResolved() {
        this.resolved = true;
        System.out.println("Complaint #" + complaintId + " marked as resolved.");
    }

    @Override
    public String toString() {
        return "[COMPLAINT #" + complaintId + "] "
                + "From: " + sender.getFullName()
                + " | About: " + targetStudent.getFullName()
                + " | Urgency: " + urgency
                + " | Resolved: " + resolved
                + "\n  Description: " + description;
    }
}
