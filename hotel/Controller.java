package oop.project.hotel;
import java.util.List;
import java.util.Scanner;

public class Controller {

    String filePath;
    FileController fileController = new FileController();
    private List<Room> rooms;
    Hotel hotel = new Hotel();
    Scanner scanner;



    public Controller() {
        this.scanner = new Scanner(System.in);
    }


    public void startCommandLine() {

        boolean running = true;

        System.out.println("Welcome to the Hotel Check-In System.\nUse !help to find the available commands.");

        while (running) {

            if (filePath == null) {
                System.out.println("No file opened! Open a file now using: open <filepath> to continue.");
                System.out.print("> ");
                String input = scanner.nextLine().trim();

                String[] parts = input.split(" ");
                openFile(parts[1].toLowerCase());

            }
            else{

                System.out.print("> ");
                String input = scanner.nextLine().trim();

                String[] parts = input.split(" ");
                String command = parts[0].toLowerCase();

                switch (command) {
                    case "checkin":
                        checkIn();
                        break;
                    case "display_checkin":
                        hotel.displayCheckIns();
                        break;
                    case "open":
                        openFile(parts[1].toLowerCase());
                        break;
                    case "save":
                        saveFile();
                        break;
                    case "showr":
                        showRooms();
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


        }
        scanner.close();
    }


    public void openFile(String path){
        filePath = path;
        fileController.readRoomsFile(filePath);
    }

    public void saveFile(){
        fileController.setRooms(rooms);
        fileController.writeToFile();
    }

    public void showRooms(){
        fileController.displayRooms();
    }



    public void checkIn() {
        try {
            rooms = fileController.getRooms();

            System.out.print("Room: ");
            String targetRoom = scanner.nextLine().trim();

            Room roomToCheckin = null;
            for (Room r : rooms) {
                if (r.getRoomNumber().equals(targetRoom)) {
                    roomToCheckin = r;
                    break;
                }
            }

            if (roomToCheckin == null) {
                System.out.println("Error: Room " + targetRoom + " does not exist.");
                return;
            }

            if (!roomToCheckin.isAvailable()) {
                System.out.println("Error: Room " + targetRoom + " is currently occupied.");
                return;
            }

            System.out.print("From (e.g., YYYY-MM-DD): ");
            String fromDate = scanner.nextLine().trim();

            System.out.print("To (e.g., YYYY-MM-DD): ");
            String toDate = scanner.nextLine().trim();

            System.out.print("Note: ");
            String note = scanner.nextLine().trim();

            System.out.print("Guests: ");
            int guests = Integer.parseInt(scanner.nextLine().trim());

            roomToCheckin.setAvailable(false);

            CheckIn checkIn = new CheckIn(targetRoom, fromDate, toDate, note, guests);
            hotel.addCheckIn(checkIn);

            System.out.println("Success! Checked " + guests + " guests into room " + targetRoom + ".");


        } catch (NumberFormatException e) {
            System.out.println("Error: Room and Guests must be numbers. Check-in cancelled.");
        }
    }


    public void help(){
        System.out.println("Hotel help menu.");
        System.out.println("Available commands: ");
        System.out.println("'checkin', - add a new checkin");
        System.out.println("'display_checkin', - displays all current checkins");
        System.out.println("'open', - open <path> - opens file and keeps it in memory");
        System.out.println("'showr', - displays all the rooms from the loaded file.");
        System.out.println("'exit', - exits the program");
    }




}
