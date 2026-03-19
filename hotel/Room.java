package oop.project.hotel;

public class Room {

    private String roomNumber;
    private String type;
    private int beds;
    private boolean available;

    public Room(String roomNumber, String type, int beds,boolean available) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.beds = beds;
        this.available = available;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public int getBeds() {
        return beds;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ", " + beds + " beds" + ", available " + available + " )";
    }
}
