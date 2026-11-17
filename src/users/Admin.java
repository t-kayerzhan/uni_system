package users;

import java.util.List;

public class Admin extends Employee {

    public Admin(String id, String username, String password,
                 String firstName, String lastName, String email,
                 double salary, String department) {
        super(id, username, password, firstName, lastName, email, salary, department);
    }

    public void createUser(User user) {
        System.out.println("User created: " + user.getFullName());
    }

    public void updateUser(User user, String newEmail) {
        user.setEmail(newEmail);
        System.out.println("User updated: " + user.getFullName());
    }

    public void deleteUser(String id, List<User> users) {
        boolean removed = users.removeIf(u -> u.getId().equals(id));

        if (removed) {
            System.out.println("User " + id + " deleted.");
        } else {
            System.out.println("User not found.");
        }
    }
    public void resetPassword(User user, String newPassword) {
        user.setPassword(newPassword);
        System.out.println("Password reset for: " + user.getFullName());
    }

    public void viewLogs(List<String> logs) {
        System.out.println("===== SYSTEM LOGS =====");
        for (String log : logs) System.out.println(log);
    }

    public void exportLogs(List<String> logs, String filename) {
        System.out.println("Logs exported to: " + filename + " (" + logs.size() + " entries)");
    }

    @Override
    public String toString() {
        return super.toString() + " | Role: ADMIN";
    }
}
