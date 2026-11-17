package users;

import academic.TechSupportRequest;
import enums.RequestStatus;
import java.util.ArrayList;
import java.util.List;

public class TechSupportSpecialist extends Employee {
    private List<TechSupportRequest> assignedRequests;

    public TechSupportSpecialist(String id, String username, String password,
                                  String firstName, String lastName, String email,
                                  double salary, String department) {
        super(id, username, password, firstName, lastName, email, salary, department);
        this.assignedRequests = new ArrayList<>();
    }

    public List<TechSupportRequest> viewRequests() {
        return assignedRequests;
    }

    public List<TechSupportRequest> viewNewRequests() {
        List<TechSupportRequest> newOnes = new ArrayList<>();
        for (TechSupportRequest r : assignedRequests) {
            if (r.getStatus() == RequestStatus.NEW) {
                r.setStatus(RequestStatus.VIEWED); 
                newOnes.add(r);
            }
        }
        return newOnes;
    }

    public void acceptRequest(TechSupportRequest req) {
        req.setStatus(RequestStatus.ACCEPTED);
        req.setAssignedTo(this);
        System.out.println("Request ACCEPTED: " + req.getTitle());
    }

    public void rejectRequest(TechSupportRequest req, String reason) {
        req.setStatus(RequestStatus.REJECTED);
        req.setRejectionReason(reason);
        System.out.println("Request REJECTED: " + req.getTitle() + " | Reason: " + reason);
    }

    public void markDone(TechSupportRequest req) {
        req.setStatus(RequestStatus.DONE);
        System.out.println("Request DONE: " + req.getTitle());
    }

    public void addRequest(TechSupportRequest req) {
        req.setStatus(RequestStatus.NEW);
        assignedRequests.add(req);
    }

    @Override
    public String toString() {
        return super.toString() + " | Role: TECH SUPPORT";
    }
}
