package oop.project.hotel;
import java.util.ArrayList;
import java.util.List;

public class Hotel {

    private List<CheckIn> checkInList;

    public Hotel(){
        this.checkInList = new ArrayList<>();
    }

    public void addCheckIn(CheckIn checkIn){
        this.checkInList.add(checkIn);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("=== Checkins ===\n");
        for (CheckIn p : checkInList) {
            sb.append(p.toString()).append("\n");
        }
        return sb.toString();
    }

    public void displayCheckIns() {
        if (checkInList.isEmpty()) {
            System.out.println("=== Checkins ===");
            System.out.println("No check-ins at the moment.\n");
        } else {
            System.out.println(this.toString());
        }
    }

}
