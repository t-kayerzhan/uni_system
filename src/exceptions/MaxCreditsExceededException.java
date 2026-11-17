package exceptions;

public class MaxCreditsExceededException extends Exception {
    private int currentCredits;

    public MaxCreditsExceededException(int current, int limit) {
        super("Cannot register: credit limit exceeded. Current: " + current + ", Limit: " + limit);
        this.currentCredits = current;
    }

    public int getCurrentCredits() { return currentCredits; }
}
