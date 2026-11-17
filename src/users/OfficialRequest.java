package users;

import java.io.Serializable;
import java.util.Date;

public class OfficialRequest implements Serializable {
    private static int counter = 1;
    private int requestId;
    private String title;
    private String description;
    private Employee createdBy;
    private String signedBy;
    private Date createdAt;

    public OfficialRequest(String title, String description, Employee createdBy, String signedBy) {
        this.requestId = counter++;
        this.title = title;
        this.description = description;
        this.createdBy = createdBy;
        this.signedBy = signedBy;
        this.createdAt = new Date();
    }

    public int getRequestId() { return requestId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Employee getCreatedBy() { return createdBy; }
    public String getSignedBy() { return signedBy; }
    public Date getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "[OFFICIAL REQUEST #" + requestId + "] " + title
                + "\n  By: " + createdBy.getFullName()
                + " | Signed by: " + signedBy
                + "\n  Description: " + description;
    }
}
