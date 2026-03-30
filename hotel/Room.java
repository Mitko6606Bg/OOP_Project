package oop.project.hotel;

import java.time.LocalDate;

public class Room {

    private String roomNumber;
    private String type;
    private int beds;
    private boolean availableNow;
    private LocalDate startDate;
    private LocalDate endDate;

    public Room(String roomNumber, String type, int beds,boolean availableNow) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.beds = beds;
        this.availableNow = availableNow;
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
        return availableNow;
    }

    public void setAvailable(boolean available) {
        this.availableNow = available;
    }

    @Override
    public String toString() {
        return "Room " + roomNumber + " (" + type + ", " + beds + " beds" + ", available now " + availableNow + " )";
    }
}
