package academic;

import enums.RequestStatus;
import users.TechSupportSpecialist;
import users.User;
import java.io.Serializable;
import java.util.Date;

public class TechSupportRequest implements Serializable {
    private static int counter = 1;
    private int requestId;
    private String title;
    private String description;
    private User requestedBy;
    private TechSupportSpecialist assignedTo;
    private RequestStatus status;
    private Date createdAt;
    private Date updatedAt;
    private String rejectionReason;

    public TechSupportRequest(String title, String description, User requestedBy) {
        this.requestId = counter++;
        this.title = title;
        this.description = description;
        this.requestedBy = requestedBy;
        this.status = RequestStatus.NEW;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }

    public int getRequestId() { return requestId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public RequestStatus getStatus() { return status; }
    public void setStatus(RequestStatus status) {
        this.status = status;
        this.updatedAt = new Date();
    }
    public User getRequestedBy() { return requestedBy; }
    public TechSupportSpecialist getAssignedTo() { return assignedTo; }
    public void setAssignedTo(TechSupportSpecialist spec) { this.assignedTo = spec; }
    public String getRejectionReason() { return rejectionReason; }
    public void setRejectionReason(String reason) { this.rejectionReason = reason; }

    @Override
    public String toString() {
        return "[REQUEST #" + requestId + "] " + title
                + " | Status: " + status
                + " | By: " + requestedBy.getFullName()
                + (rejectionReason != null ? " | Reason: " + rejectionReason : "");
    }
}
