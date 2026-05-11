package oop.project.hotel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CheckIn {

    private String room;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String note;
    private int guests;
    private List<String> activities;

    public CheckIn(String room, LocalDate fromDate, LocalDate toDate, String note, int guests,List<String> activities) {
        this.room = room;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.note = note;
        this.guests = guests;
        this.activities = activities != null ? activities : new ArrayList<>();
    }


    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getGuests() {
        return guests;
    }

    public void setGuests(int guests) {
        this.guests = guests;
    }

    public List<String> getActivities() { return activities; }
    public void setActivities(List<String> activities) { this.activities = activities; }

    @Override
    public String toString() {
        String acts = activities.isEmpty() ? "None" : String.join(", ", activities);
        return "Room: " + room + " | From: " + fromDate + " | To: " + toDate + " | Guests: " + guests + " | Note: " + note + " | Activities: " + acts;
    }


}
