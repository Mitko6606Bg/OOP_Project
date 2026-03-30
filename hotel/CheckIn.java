package oop.project.hotel;

import java.time.LocalDate;

public class CheckIn {

    private String room;
    private LocalDate fromDate;
    private LocalDate toDate;
    private String note;
    private int guests;

    public CheckIn(String room, LocalDate fromDate, LocalDate toDate, String note, int guests) {
        this.room = room;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.note = note;
        this.guests = guests;
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

    @Override
    public String toString() {
        return "Room: " + room + " | From: " + fromDate + " | To: " + toDate + " | Guests: " + guests + " | Note: " + note;
    }


}
