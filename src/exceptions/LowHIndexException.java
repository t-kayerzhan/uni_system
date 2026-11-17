package exceptions;

public class LowHIndexException extends Exception {
    private int hIndex;

    public LowHIndexException(int hIndex) {
        super("Cannot assign supervisor with h-index " + hIndex + ". Minimum required is 3.");
        this.hIndex = hIndex;
    }

    public int getHIndex() { return hIndex; }
}
