package oop.project.hotel;
import java.util.Scanner;

public class Controller {

    Hotel hotel = new Hotel();
    Scanner scanner = new Scanner(System.in);


    public Controller() {
        this.scanner = new Scanner(System.in);
    }


    public void startCommandLine() {
        System.out.println("Welcome to the Hotel Check-In System.");
        System.out.println("Available commands: 'checkin', 'exit'");

        boolean running = true;

        while (running) {
            System.out.print("> "); // The CMD prompt symbol
            String input = scanner.nextLine().trim();

            String[] parts = input.split(" ");
            String command = parts[0].toLowerCase();

            switch (command) {
                case "checkin":
                    checkIn();
                    break;
                case "displayIns":
                    hotel.displayCheckIns();
                    break;
                case "!help":
                    help();
                    break;
                case "exit":
                    System.out.println("Exiting system...");
                    running = false;
                    break;

                default:
                    System.out.println("Unknown command.");
            }
        }
        scanner.close();
    }




    public void checkIn(){

        try {
            System.out.print("Room: ");
            int room = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("From (e.g., YYYY-MM-DD): ");
            String fromDate = scanner.nextLine().trim();

            System.out.print("To (e.g., YYYY-MM-DD): ");
            String toDate = scanner.nextLine().trim();

            System.out.print("Note: ");
            String note = scanner.nextLine().trim();

            System.out.print("Guests: ");
            int guests = Integer.parseInt(scanner.nextLine().trim());

            CheckIn checkIn = new CheckIn(room, fromDate, toDate, note, guests);
            hotel.addCheckIn(checkIn);

            System.out.println("Success! Checked " + guests + " guests into room " + room + ".");

        } catch (NumberFormatException e) {
            System.out.println("Error: Room and Guests must be numbers. Check-in cancelled.");
        }

    }








    public void help(){
        System.out.println("Hotel help menu.");
        System.out.println("Available commands: ");
        System.out.println("'checkin', - add a new checkin");
        System.out.println("'displayIns', - displays all current checkins");
        System.out.println("'exit', - exits the program");
    }




}
