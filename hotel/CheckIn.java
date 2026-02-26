package oop.project.hotel;

public class CheckIn {

    private int room;
    private String fromDate;
    private String toDate;
    private String note;
    private int guests;

    public CheckIn(int room, String fromDate, String toDate, String note, int guests) {
        this.room = room;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.note = note;
        this.guests = guests;
    }


    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
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
