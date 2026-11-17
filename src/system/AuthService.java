package system;

import users.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class AuthService {
    private Map<String, User> activeSessions = new HashMap<>();

    public User authenticate(String username, String password, List<User> users) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.login(password)) {
                System.out.println("Login successful: " + user.getFullName());
                return user;
            }
        }
        System.out.println("Login failed for username: " + username);
        return null;
    }

    public String generateSession(User user) {
        String sessionId = UUID.randomUUID().toString();
        activeSessions.put(sessionId, user);
        System.out.println("Session created for: " + user.getFullName());
        return sessionId;
    }

    public void invalidateSession(String sessionId) {
        User user = activeSessions.remove(sessionId);
        if (user != null) System.out.println("Session ended for: " + user.getFullName());
    }

    public User getCurrentUser(String sessionId) {
        return activeSessions.get(sessionId);
    }
    
    public User requireAuth(String sessionId) {
        User u = activeSessions.get(sessionId);
        if (u == null) {
            throw new RuntimeException("User not authenticated");
        }
        return u;
    }

    public boolean isSessionActive(String sessionId) {
        return activeSessions.containsKey(sessionId);
    }

    public String hashPassword(String raw) {
        return Integer.toHexString(raw.hashCode());
    }
}
