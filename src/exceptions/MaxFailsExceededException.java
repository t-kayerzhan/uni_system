package exceptions;

public class MaxFailsExceededException extends Exception {
    private int failCount;

    public MaxFailsExceededException(int failCount) {
        super("Cannot register: course failed " + failCount + " times. Maximum allowed is 3.");
        this.failCount = failCount;
    }

    public int getFailCount() { return failCount; }
}
