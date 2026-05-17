package oop.project.hotel;
import oop.project.hotel.Commands.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.format.DateTimeParseException;

public class Controller {

    String filePath;
    String checkinFilePath;
    FileController fileController = new FileController();
    private Map<String, Command> commands;
    private List<Room> rooms;
    private List<CheckIn> checkins;
    Scanner scanner;

    private void registerCommands() {

        commands.put("open", new OpenCommand(this));
        commands.put("save", new SaveCommand(this));
        commands.put("save as", new SaveAsCommand(this));
        commands.put("close", new CloseCommand(this));
        commands.put("showr", new ShowRoomsCommand(this));
        commands.put("dsp_checkins", new DisplayCheckinsCommand(this));
        commands.put("checkin", new CheckInCommand(this));
        commands.put("checkout", new CheckOutCommand(this));
        commands.put("availability", new AvailabilityCommand(this));
        commands.put("find", new FindRoomCommand(this));
        commands.put("unavailable", new MakeUnavailableCommand(this));
        commands.put("!find", new EmergencyFindCommand(this));
        commands.put("activity_guests", new ActivityGuestsCommand(this));
        commands.put("room_program", new RoomProgramCommand(this));
        commands.put("report", new ReportCommand(this));
    }


    public Controller() {
        this.scanner = new Scanner(System.in);
        this.rooms = new ArrayList<>();
        this.checkins = new ArrayList<>();

        this.commands = new HashMap<>();
        registerCommands();
    }



    public void startCommandLine() {

        boolean running = true;

        System.out.println("Welcome to the Hotel Check-In System.\nUse !help to find the available commands.");

        while (running) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();

            if (input.isEmpty()) {
                continue;
            }

            String[] parts = input.split(" ");
            String commandName = parts[0].toLowerCase();

            if (commandName.equals("save") && parts.length > 1 && parts[1].equalsIgnoreCase("as")) {
                commandName = "save as";
            }

            if (filePath == null && !commandName.equals("open") && !commandName.equals("exit") && !commandName.equals("!help")) {
                System.out.println("No rooms file opened! Open a file now using: open <filepath> to continue.");
                continue;
            }

            if (commandName.equals("exit")) {
                System.out.println("Exiting system...");
                running = false;
                continue;
            } else if (commandName.equals("!help")) {
                help();
                continue;
            }

            Command command = commands.get(commandName);

            if (command != null) {
                command.execute(parts);
            } else {
                System.out.println("Unknown command: " + commandName);
            }
        }

        scanner.close();
    }


    public void openFile(String path){

        if(fileController.readRoomsFile(path)){
            filePath = path;
            this.rooms = fileController.getRooms();
        }
    }

    public void openCheckinFile(String path){
        checkinFilePath = path;
        fileController.readCheckInsFile(checkinFilePath, this.rooms);
        this.checkins = fileController.getCheckIns();
    }

    public boolean checkIfCheckinFileIsOpened(){
       if (checkinFilePath != null){
           return false;
       }
       else{
           return true;
       }
    }


    public void saveFiles() {
        boolean savedAnything = false;

        if (filePath != null) {
            fileController.setRooms(rooms);
            fileController.writeToFile();
            System.out.println("Successfully saved rooms file.");
            savedAnything = true;
        }

        if (checkinFilePath != null) {
            fileController.setCheckIns(checkins);
            fileController.writeCheckInsToFile();
            System.out.println("Successfully saved checkins file.");
            savedAnything = true;
        }

        if (!savedAnything) {
            System.out.println("No files are currently open to save.");
        }
    }

    public void saveAs(boolean isCheckinFile, String newPath) {

        newPath = newPath.replace("\"", "");

        if (isCheckinFile) {
            this.checkinFilePath = newPath;
            fileController.setCheckInsFilePath(newPath);
            fileController.setCheckIns(checkins);
            fileController.writeCheckInsToFile();
            System.out.println("Successfully saved checkins to " + newPath);
        } else {
            this.filePath = newPath;
            fileController.setFilePath(newPath);
            fileController.setRooms(rooms);
            fileController.writeToFile();
            System.out.println("Successfully saved rooms to " + newPath);
        }
    }


    public void closeFiles() {
        boolean anythingOpen = false;

        if (this.checkinFilePath != null) {
            anythingOpen = true;
            System.out.print("Do you want to close the checkins file? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("y")) {
                this.checkins.clear();
                fileController.setCheckIns(new ArrayList<>());
                this.checkinFilePath = null;
                System.out.println("Successfully closed checkins file.");
            } else {
                System.out.println("Checkins file remains open.");
            }
        }

        if (this.filePath != null) {
            anythingOpen = true;
            System.out.print("Do you want to close the rooms file? (y/n): ");
            String choice = scanner.nextLine().trim().toLowerCase();

            if (choice.equals("y")) {
                this.rooms.clear();
                fileController.setRooms(new ArrayList<>());
                this.filePath = null;
                System.out.println("Successfully closed rooms file.");
            } else {
                System.out.println("Rooms file remains open.");
            }
        }

        if (!anythingOpen) {
            System.out.println("No files are currently open to close.");
        }
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

            if (guests > roomToCheckin.getBeds()) {
                System.out.println("Error: Room " + targetRoom + " has only " + roomToCheckin.getBeds() + " beds");
                return;
            }


            System.out.println("Available activities: SPA, Pool, Gym, Movie (or press Enter to skip)");
            System.out.print("Activities (comma separated): ");
            String activitiesInput = scanner.nextLine().trim();
            List<String> activities = new ArrayList<>();
            if (!activitiesInput.isEmpty()) {
                String[] acts = activitiesInput.split(",");
                for (String act : acts) {
                    activities.add(act.trim());
                }
            }


            roomToCheckin.setAvailable(false);

            CheckIn checkIn = new CheckIn(roomToCheckin, fromDate, toDate, note, guests,activities);

            checkins.add(checkIn);
            fileController.setCheckIns(checkins);


            System.out.println("Success! Checked " + guests + " guests into room " + targetRoom + ".");


        } catch (NumberFormatException e) {
            System.out.println("Error: Room and Guests must be numbers. Check-in cancelled.");
        }
    }

    public void checkOut(String roomNumber) {
        checkins = fileController.getCheckIns();
        CheckIn toRemove = null;

        for (CheckIn checkin : checkins) {
            if (checkin.getRoom().getRoomNumber().equals(roomNumber)) {
                toRemove = checkin;
                break;
            }
        }

        if (toRemove != null) {
            toRemove.getRoom().setAvailable(true);
            toRemove.getRoom().setNote("");
            System.out.println("Successfully checkout from room: " + roomNumber);
        } else {
            System.out.println("No active check-in found for room " + roomNumber);
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
                if (checkin.getRoom() == room) {
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
                if (checkin.getRoom() == room) {
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

    private Room findAlternativeRoom(CheckIn conflict, Room originalRoom, List<Room> reservedAlternatives) {
        for (Room altRoom : rooms) {
            if (altRoom == originalRoom) continue;

            if (altRoom.getBeds() < conflict.getGuests()) continue;
            if (reservedAlternatives.contains(altRoom)) continue;

            boolean isAvailable = true;
            for (CheckIn c : checkins) {
                if (c.getRoom() == altRoom) {
                    boolean isOverlapping = conflict.getFromDate().isBefore(c.getToDate()) &&
                            conflict.getToDate().isAfter(c.getFromDate());
                    if (isOverlapping) {
                        isAvailable = false;
                        break;
                    }
                }
            }
            if (isAvailable) return altRoom;
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
                if (checkin.getRoom() == targetRoom) {
                    boolean isOverlapping = fromDate.isBefore(checkin.getToDate()) &&
                            toDate.isAfter(checkin.getFromDate());
                    if (isOverlapping) {
                        overlappingCheckIns.add(checkin);
                    }
                }
            }

            if (!overlappingCheckIns.isEmpty() && overlappingCheckIns.size() <= 2) {

                boolean canRelocateAll = true;
                List<String> relocationPlan = new ArrayList<>();
                List<Room> reservedAlternatives = new ArrayList<>();

                for (CheckIn conflict : overlappingCheckIns) {
                    Room alternative = findAlternativeRoom(conflict, targetRoom, reservedAlternatives);

                    if (alternative == null) {
                        canRelocateAll = false;
                        break;
                    } else {
                        reservedAlternatives.add(alternative);
                        relocationPlan.add("Move reservation from room " + conflict.getRoom().getRoomNumber() +
                                " -> to ROOM " + alternative.getRoomNumber());                    }
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

        CheckIn unavailable = new CheckIn(roomToUnavailable, fromDate, toDate, note, 0,List.of());

        checkins.add(unavailable);
        fileController.setCheckIns(checkins);
        //fileController.writeCheckInsToFile();
        fileController.setRooms(rooms);

        System.out.println("   Room " + roomNumber + " is set to unavailable!");
        System.out.println("   Dates: " + fromDate + " to " + toDate);
        System.out.println("   Note: " + note);
    }

    public void showRoomProgram(String roomNumber) {
        checkins = fileController.getCheckIns();
        boolean found = false;

        System.out.println("\n--- Program for Room " + roomNumber + " ---");
        for (CheckIn checkIn : checkins) {
            if (checkIn.getRoom().getRoomNumber().equals(roomNumber)) {
                found = true;
                if (checkIn.getActivities().isEmpty()) {
                    System.out.println("No activities scheduled for this room.");
                } else {
                    for (String activity : checkIn.getActivities()) {
                        System.out.println("- " + activity);
                    }
                }
            }
        }

        if (!found) {
            System.out.println("Room not found or not currently checked in.");
        }
        System.out.println("-------------------------------\n");
    }

    public void showActivityGuests(String targetActivity) {
        checkins = fileController.getCheckIns();
        boolean found = false;

        System.out.println("\n--- Guests signed up for: " + targetActivity + " ---");
        for (CheckIn checkIn : checkins) {
            for (String activity : checkIn.getActivities()) {
                if (activity.equalsIgnoreCase(targetActivity.trim())) {
                    System.out.println("Room: " + checkIn.getRoom().getRoomNumber() + " (Guests: " + checkIn.getGuests() + ")");                    found = true;
                    break;
                }
            }
        }

        if (!found) {
            System.out.println("No guests are signed up for this activity.");
        }
        System.out.println("----------------------------------------\n");
    }

    public void reportRoomUsage(LocalDate reportFrom, LocalDate reportTo) {
        if (reportFrom.isAfter(reportTo)) {
            System.out.println("Error: The start date must be before the end.");
            return;
        }

        Map<String, Long> roomUsage = new HashMap<>();

        for (CheckIn checkIn : checkins) {

            LocalDate start = checkIn.getFromDate().isAfter(reportFrom) ? checkIn.getFromDate() : reportFrom;

            LocalDate end = checkIn.getToDate().isBefore(reportTo) ? checkIn.getToDate() : reportTo;

            if (start.isBefore(end)) {
                long daysUsed = ChronoUnit.DAYS.between(start, end);
                String roomNum = checkIn.getRoom().getRoomNumber();

                roomUsage.put(roomNum, roomUsage.getOrDefault(roomNum, 0L) + daysUsed);
            }
        }

        System.out.println("\n--- Room Report (" + reportFrom + " to " + reportTo + ") ---");
        if (roomUsage.isEmpty()) {
            System.out.println("No rooms used in period.");
        } else {
            for (Map.Entry<String, Long> entry : roomUsage.entrySet()) {
                System.out.println("Room " + entry.getKey() + " is used " + entry.getValue() + " days.");
            }
        }
        System.out.println("--------------------------------------------------\n");
    }


    public void help() {
        System.out.println("\n--- Hotel Help Menu ---");

        System.out.println("File Operations:");
        System.out.println("'open <path>', - loads a rooms file");
        System.out.println("'open -c <path>', - loads a checkins file");
        System.out.println("'save', - saves all current data to their respective files");
        System.out.println("'save as <path>', - saves rooms to a new file path");
        System.out.println("'save as -c <path>', - saves checkins to a new file path");
        System.out.println("'close', - closes current files and clears memory");

        System.out.println("\nDisplay Information:");
        System.out.println("'showr', - displays all loaded rooms");
        System.out.println("'dsp_checkins', - displays all current checkins");

        System.out.println("\nReservations & Search:");
        System.out.println("'checkin', - starts the checkin process");
        System.out.println("'checkout <roomNumber>', - checks out a guest and frees the room");
        System.out.println("'availability [YYYY-MM-DD]', - shows free rooms (default is today)");
        System.out.println("'report <from> <to> - Displays a report on the rooms used and the number of days in a given period.");
        System.out.println("'find <beds> <from> <to>', - finds available rooms for specific dates");
        System.out.println("'!find <beds> <from> <to>', - emergency VIP room search (relocates guests)");
        System.out.println("'unavailable <room> <from> <to> <note>', - manually blocks a room");

        System.out.println("\nActivities:");
        System.out.println("'room_program <roomNumber>', - shows activities for a specific room");
        System.out.println("'activity_guests <activity>', - shows all guests signed up for an activity");

        System.out.println("\nSystem:");
        System.out.println("'!help', - displays this menu");
        System.out.println("'exit', - saves all data and exits the program");
        System.out.println("-----------------------\n");
    }




}
