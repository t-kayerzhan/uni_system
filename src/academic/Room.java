package academic;
import enums.RoomType;
import java.util.Objects;
import java.io.Serializable;

public class Room implements Serializable {
    private static int counter = 1;
    private int roomId;
    private String roomNumber;
    private String building;
    private int floor;
    private int capacity;
    private RoomType type;
    private boolean isAvailable;
    private String bookedFor;
    private int bookedCount;

    public Room(String roomNumber, String building, int floor, int capacity, RoomType type) {
        this.roomId = counter++;
        this.roomNumber = roomNumber;
        this.building = building;
        this.floor = floor;
        this.capacity = capacity;
        this.type = type;
        this.isAvailable = true;
    }

    public boolean book(String purpose, int studentCount) {
        if (!isAvailable) return false;
        if (studentCount > capacity) return false;
        isAvailable = false;
        bookedFor = purpose;
        bookedCount = studentCount;
        return true;
    }

    public void release() {
        isAvailable = true;
        bookedFor = null;
        bookedCount = 0;
    }

    public String getRoomNumber() { return roomNumber; }
    public String getBuilding() { return building; }
    public int getFloor() { return floor; }
    public int getCapacity() { return capacity; }
    public RoomType getType() { return type; }
    public boolean isAvailable() { return isAvailable; }
    public String getBookedFor() { return bookedFor; }
    public int getBookedCount() { return bookedCount; }

    @Override
    public String toString() {
        return "[ROOM] " + roomNumber + " | " + building + " Floor " + floor
                + " | Capacity: " + capacity + " | Type: " + type
                + " | " + (isAvailable ? "Available" : "Booked for: " + bookedFor);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Room)) return false;
        return this.roomNumber.equals(((Room) obj).roomNumber);
    }

    @Override
    public int hashCode() { return Objects.hash(roomNumber); }
}