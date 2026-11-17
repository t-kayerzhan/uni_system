package enums;

public enum Semester {
	  SPRING, FALL;

    public String getLabel(int year) {
        return this.name().charAt(0) + this.name().substring(1).toLowerCase() + year;
        // SPRING2026, FALL2025
    }
}
