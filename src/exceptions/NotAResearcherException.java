package exceptions;

public class NotAResearcherException extends Exception {
    private String userName;

    public NotAResearcherException(String userName) {
        super("User '" + userName + "' is not a Researcher and cannot join a ResearchProject.");
        this.userName = userName;
    }

    public String getUserName() { return userName; }
}
