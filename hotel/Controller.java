package oop.project.hotel;
import java.time.LocalDate;
import java.util.*;
import java.time.format.DateTimeParseException;

public class Controller {

    String filePath;
    String checkinFilePath;
    FileController fileController = new FileController();
    private List<Room> rooms;
    private List<CheckIn> checkins;
    Scanner scanner;



    public Controller() {
        this.scanner = new Scanner(System.in);
        this.rooms = new ArrayList<>();
        this.checkins = new ArrayList<>();
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
                    case "checkout":
                        if (parts.length > 1) {
                            checkOut(parts[1]);
                        } else {
                            System.out.println("Invalid command. No room number ?");
                        }
                        break;
                    case "display_checkin":
                        if(checkinFilePath == null){
                            System.out.println("No checkins file opened! Open a file now using: open -c <filepath> to continue.");
                            break;
                        }
                        else{
                            showCheckins();
                        }
                        break;
                    case "open":
                        String attribute = parts[1];
                        if(attribute.equals("-c")){
                            openCheckinFile(parts[2].toLowerCase());
                        }
                        else{
                            openFile(parts[1].toLowerCase());
                        }
                        break;
                    case "save":
                        saveFile();
                        break;
                    case "showr":
                        showRooms();
                        break;
                    case "availability":
                        java.time.LocalDate date;
                        if (parts.length > 1) {
                            date = LocalDate.parse(parts[1]);
                        } else {
                            date = LocalDate.now();
                        }
                        roomAvailability(date);
                        break;
                    case "find":
                        LocalDate fromDate;
                        LocalDate toDate;
                        if (parts.length > 3) {
                            fromDate = LocalDate.parse(parts[2]);
                            toDate = LocalDate.parse(parts[3]);
                            findRoomsWithBeds(fromDate,toDate,Integer.parseInt(parts[1]));
                        } else {
                            System.out.println("Invalid command. Wrong syntax ?");
                        }
                        break;
                    case "!find":
                        LocalDate fromDateV;
                        LocalDate toDateV;
                        if (parts.length > 3) {
                            fromDateV = LocalDate.parse(parts[2]);
                            toDateV = LocalDate.parse(parts[3]);
                            emergencyRoomSearch(fromDateV,toDateV,Integer.parseInt(parts[1]));
                        } else {
                            System.out.println("Invalid command. Wrong syntax ?");
                        }

                        break;
                    case "unavailable":
                        if(!checkIfCheckinFileIsOpened()){
                            LocalDate fromDateU;
                            LocalDate toDateU;
                            if (parts.length > 3) {
                                fromDateU = LocalDate.parse(parts[2]);
                                toDateU = LocalDate.parse(parts[3]);
                                makeRoomUnavailable(parts[1],fromDateU,toDateU,parts[4]);
                            } else {
                                System.out.println("Invalid command. Wrong syntax ?");
                            }
                        }
                        else{
                            System.out.println("Open a Checkin file using: open -c <filepath> more in !help");
                            break;
                        }
                        break;
                    case "!help":
                        help();
                        break;
                    case "exit":
                        System.out.println("Exiting system...");
                        saveFile();
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

        if(fileController.readRoomsFile(path)){
            filePath = path;
        }
    }

    public void openCheckinFile(String path){
        checkinFilePath = path;
        fileController.readCheckInsFile(checkinFilePath);

    }

    public boolean checkIfCheckinFileIsOpened(){
       if (checkinFilePath != null){
           return false;
       }
       else{
           return true;
       }
    }

    public void saveFile(){
        fileController.setRooms(rooms);
        fileController.writeToFile();
    }

    public void showRooms(){
        fileController.displayRooms();
    }

    public void showCheckins(){
        fileController.displayCheckIns();
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

            LocalDate fromDate = null;
            while (fromDate == null) {
                System.out.print("From (e.g., YYYY-MM-DD): ");
                try {
                    fromDate = LocalDate.parse(scanner.nextLine().trim());
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format! Please strictly use YYYY-MM-DD.");
                }
            }

            LocalDate toDate = null;
            while (toDate == null) {
                System.out.print("To (e.g., YYYY-MM-DD): ");
                try {
                    toDate = LocalDate.parse(scanner.nextLine().trim());

                    if (!toDate.isAfter(fromDate)) {
                        System.out.println("Error: 'To' date must be after 'From' date.");
                        toDate = null;
                    }
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format! Please strictly use YYYY-MM-DD.");
                }
            }

            System.out.print("Note: ");
            String note = scanner.nextLine().trim();

            System.out.print("Guests: ");
            int guests = Integer.parseInt(scanner.nextLine().trim());

            roomToCheckin.setAvailable(false);

            CheckIn checkIn = new CheckIn(targetRoom, fromDate, toDate, note, guests);

            fileController.addCheckInToFile(checkIn);
            checkins.add(checkIn);
            fileController.setCheckIns(checkins);


            System.out.println("Success! Checked " + guests + " guests into room " + targetRoom + ".");


        } catch (NumberFormatException e) {
            System.out.println("Error: Room and Guests must be numbers. Check-in cancelled.");
        }
    }

    public void checkOut(String roomNumber){
        rooms = fileController.getRooms();
        checkins = fileController.getCheckIns();

        for (Room room : rooms){
            boolean found = false;

            if(room.getRoomNumber().equals(roomNumber)){
                room.setAvailable(true);
                for (CheckIn checkin : checkins){
                    if(checkin.getRoom().equals(roomNumber)){
                        checkins.remove(checkin);
                        fileController.setCheckIns(checkins);
                        fileController.writeCheckInsToFile();
                        found = true;
                    }
                }
            }
            if (found) {
                System.out.println("Successfully checkout from room: " + roomNumber);
                break;
            }
        }

    }

    public void roomAvailability(LocalDate targetDate) {
        checkins = fileController.getCheckIns();
        rooms = fileController.getRooms();
        System.out.println("\n--- Rooms Available on " + targetDate + " ---");
        boolean atLeastOne = false;

        for (Room room : rooms) {
            boolean isAvailable = true;

            for (CheckIn checkin : checkins) {
                if (Objects.equals(room.getRoomNumber(), checkin.getRoom())) {

                    boolean isOverlapping = !targetDate.isBefore(checkin.getFromDate()) &&
                            !targetDate.isAfter(checkin.getToDate());

                    if (isOverlapping) {
                        isAvailable = false;
                        break;
                    }
                }
            }

            if (isAvailable) {
                System.out.println(room.toString());
                atLeastOne = true;
            }
        }




        if (!atLeastOne) {
            System.out.println("No rooms are available on this date.");
        }
        System.out.println("----------------------------------------\n");
    }

    public void findRoomsWithBeds(LocalDate fromDate, LocalDate toDate, int requiredBeds) {
        checkins = fileController.getCheckIns();
        rooms = fileController.getRooms();

        System.out.println("\n--- Rooms Available from " + fromDate + " to " + toDate + " (" + requiredBeds + "+ beds) ---");

        List<Room> availableRooms = new ArrayList<>();

        for (Room room : rooms) {
            if (room.getBeds() < requiredBeds) {
                continue;
            }

            boolean isAvailable = true;

            for (CheckIn checkin : checkins) {
                if (Objects.equals(room.getRoomNumber(), checkin.getRoom())) {

                    boolean isOverlapping = fromDate.isBefore(checkin.getToDate()) &&
                            toDate.isAfter(checkin.getFromDate());

                    if (isOverlapping) {
                        isAvailable = false;
                        break;
                    }
                }
            }

            if (isAvailable) {
                availableRooms.add(room);
            }
        }

        availableRooms.sort(Comparator.comparingInt(Room::getBeds));

        if (availableRooms.isEmpty()) {
            System.out.println("No rooms are available matching those dates and bed requirements.");
        } else {
            for (Room room : availableRooms) {
                System.out.println(room.toString());
            }
        }
        System.out.println("----------------------------------------\n");
    }

    private Room findAlternativeRoom(CheckIn conflict, String originalRoomNum, List<Room> reservedAlternatives) {
        for (Room altRoom : rooms) {
            if (altRoom.getRoomNumber().equals(originalRoomNum)) continue;

            if (altRoom.getBeds() < conflict.getGuests()) continue;

            if (reservedAlternatives.contains(altRoom)) continue;

            boolean isAvailable = true;
            for (CheckIn c : checkins) {
                if (c.getRoom().equals(altRoom.getRoomNumber())) {
                    boolean isOverlapping = conflict.getFromDate().isBefore(c.getToDate()) &&
                            conflict.getToDate().isAfter(c.getFromDate());
                    if (isOverlapping) {
                        isAvailable = false;
                        break;
                    }
                }
            }

            if (isAvailable) {
                return altRoom;
            }
        }
        return null;
    }

    public void emergencyRoomSearch(LocalDate fromDate, LocalDate toDate, int requiredBeds) {
        checkins = fileController.getCheckIns();
        rooms = fileController.getRooms();

        System.out.println("\n--- Emergency VIP Room Search (" + fromDate + " to " + toDate + ", " + requiredBeds + "+ beds) ---");
        boolean solutionFound = false;

        for (Room targetRoom : rooms) {
            if (targetRoom.getBeds() < requiredBeds) continue;

            List<CheckIn> overlappingCheckIns = new ArrayList<>();


            for (CheckIn checkin : checkins) {
                if (Objects.equals(targetRoom.getRoomNumber(), checkin.getRoom())) {
                    boolean isOverlapping = fromDate.isBefore(checkin.getToDate()) &&
                            toDate.isAfter(checkin.getFromDate());
                    if (isOverlapping) {
                        overlappingCheckIns.add(checkin);
                    }
                }
            }

            if (overlappingCheckIns.size() > 0 && overlappingCheckIns.size() <= 2) {

                boolean canRelocateAll = true;
                List<String> relocationPlan = new ArrayList<>();
                List<Room> reservedAlternatives = new ArrayList<>();

                for (CheckIn conflict : overlappingCheckIns) {
                    Room alternative = findAlternativeRoom(conflict, targetRoom.getRoomNumber(), reservedAlternatives);

                    if (alternative == null) {
                        canRelocateAll = false;
                        break;
                    } else {
                        reservedAlternatives.add(alternative);
                        relocationPlan.add("Move reservation from room " + conflict.getRoom() + " (Dates: " + conflict.getFromDate() + " to " + conflict.getToDate() + ") -> to ROOM " + alternative.getRoomNumber());
                    }
                }

                if (canRelocateAll) {
                    System.out.println("Accommodate the VIP guest in room " + targetRoom.getRoomNumber());
                    System.out.println("To make this happen, the following changes:");
                    for (String step : relocationPlan) {
                        System.out.println("   - " + step);
                    }
                    System.out.println("----------------------------------------");
                    solutionFound = true;
                    break;
                }
            }
        }

        if (!solutionFound) {
            System.out.println("It is not possible to free up a room by relocating a maximum of 2 reservations.");
        }
        System.out.println();
    }

    public void makeRoomUnavailable(String roomNumber, LocalDate fromDate, LocalDate toDate, String note) {
        checkins = fileController.getCheckIns();
        rooms = fileController.getRooms();

        Room roomToUnavailable= null;
        for (Room r : rooms) {
            if (r.getRoomNumber().equals(roomNumber)) {
                roomToUnavailable = r;
                break;
            }
        }

        if (roomToUnavailable == null) {
            System.out.println("Error: Room " + roomNumber + " does not exist.");
            return;
        }

        roomToUnavailable.setNote(note);
        roomToUnavailable.setAvailable(false);

        CheckIn unavailable = new CheckIn(roomNumber, fromDate, toDate, note, 0);

        checkins.add(unavailable);
        fileController.setCheckIns(checkins);
        fileController.writeCheckInsToFile();
        fileController.setRooms(rooms);

        System.out.println("   Room " + roomNumber + " is set to unavailable!");
        System.out.println("   Dates: " + fromDate + " to " + toDate);
        System.out.println("   Note: " + note);
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
