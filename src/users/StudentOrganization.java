package users;

import enums.OrganizationRole;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class StudentOrganization implements Serializable {
    private static int counter = 1;
    private int orgId;
    private String name;
    private String description;
    private Map<Student, OrganizationRole> members;

    public StudentOrganization(String name, String description) {
        this.orgId = counter++;
        this.name = name;
        this.description = description;
        this.members = new HashMap<>();
    }

    public void addMember(Student student, OrganizationRole role) {
        members.put(student, role);
    }

    public void removeMember(Student student) {
        members.remove(student);
    }

    public void promoteToHead(Student student) {
        if (members.containsKey(student)) {
            members.put(student, OrganizationRole.HEAD);
        }
    }

    public Student getHead() {
        for (Map.Entry<Student, OrganizationRole> entry : members.entrySet()) {
            if (entry.getValue() == OrganizationRole.HEAD) return entry.getKey();
        }
        return null;
    }

    public Map<Student, OrganizationRole> getMembers() { return members; }
    public int getMemberCount() { return members.size(); }
    public String getName() { return name; }
    public String getDescription() { return description; }

    @Override
    public String toString() {
        return "[ORG] " + name + " | Members: " + members.size()
                + " | Head: " + (getHead() != null ? getHead().getFullName() : "None");
    }
}
