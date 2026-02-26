package oop.project.hotel;
import java.util.Scanner;

public class Controller {

    Hotel hotel = new Hotel();
    Scanner scanner = new Scanner(System.in);


    public void displayMenu() {

        String choice = "0";

        while (!(choice.equals("4"))) {
            System.out.println("1. Check In");
            System.out.println("2. Display Checkins");
            System.out.println("3. ----");
            System.out.println("4. Exit");
            System.out.print(">> ");
            if (scanner.hasNextInt()) {
                choice = scanner.nextLine();

                if (choice.equals("1")) {
                    checkIn();
                }
                if (choice.equals("2")) {
                    hotel.displayCheckIns();
                }
                if (choice.equals("3")) {

                }
            }
            else {
                System.out.println("Wrong input!");
                choice = scanner.nextLine();

            }

        }


        System.out.println("Bye!");
        scanner.close();
    }


    public void checkIn(){

        int room;
        String fromDate;
        String toDate;
        String note;
        int guests;

        System.out.print("Room: ");
        room = Integer.parseInt(scanner.nextLine());
        System.out.print("From: ");
        fromDate = scanner.nextLine();
        System.out.print("To: ");
        toDate = scanner.nextLine();
        System.out.print("Note: ");
        note = scanner.nextLine();
        System.out.print("Guests: ");
        guests = scanner.nextInt();

        CheckIn checkIn = new CheckIn(room,fromDate,toDate,note,guests);
        hotel.addCheckIn(checkIn);

    }


}
