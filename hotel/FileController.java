package oop.project.hotel;
import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

public class FileController {

    private String filePath;
    private List<Room> rooms;

    private String checkInsFilePath;
    private List<CheckIn> checkIns;

    public FileController() {
        this.rooms = new ArrayList<>();
        this.checkIns = new ArrayList<>();
    }

    public void readRoomsFile(String path) {
        filePath = path;
        rooms.clear();
        File file = new File(filePath);

        if (!file.exists()) {
            try {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                return;
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            List<String> currentRoomData = new ArrayList<>();

            while (true) {
                line = reader.readLine();


                if (line == null || line.trim().isEmpty()) {

                    if (!currentRoomData.isEmpty()) {
                        String roomNumber = currentRoomData.get(0);
                        String type = currentRoomData.get(1);
                        int beds = Integer.parseInt(currentRoomData.get(2));
                        boolean available = Boolean.parseBoolean(currentRoomData.get(3));
                        rooms.add(new Room(roomNumber, type, beds,available));

                        currentRoomData.clear();
                    }


                    if (line == null) {
                        break;
                    }

                } else {
                    currentRoomData.add(line.trim());
                }
            }
            System.out.println("File successfully opened!");

        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void writeToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < rooms.size(); i++) {
                Room room = rooms.get(i);

                writer.write(room.getRoomNumber());
                writer.newLine();
                writer.write(room.getType());
                writer.newLine();
                writer.write(String.valueOf(room.getBeds()));
                writer.newLine();
                writer.write(String.valueOf(room.isAvailable()));
                writer.newLine();

                if (i < rooms.size() - 1) {
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void displayRooms() {
        if (rooms.isEmpty()) {
            System.out.println("No rooms found !");
            return;
        }

        System.out.println("\n--- List of Rooms ---");
        for (Room room : rooms) {
            System.out.println(room.toString());
        }
        System.out.println("---------------------\n");
    }

    public List<Room> getRooms() {
        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }

    public void readCheckInsFile(String path) {
        checkInsFilePath = path;
        checkIns.clear();
        File file = new File(checkInsFilePath);

        if (!file.exists()) {
            try {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
                System.out.println("File successfully created!");
                return;
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            List<String> currentCheckInData = new ArrayList<>();

            while (true) {
                line = reader.readLine();

                if (line == null || line.trim().isEmpty()) {
                    if (!currentCheckInData.isEmpty()) {
                        String room = currentCheckInData.get(0);
                        LocalDate fromDate = LocalDate.parse(currentCheckInData.get(1));
                        LocalDate toDate = LocalDate.parse(currentCheckInData.get(2));
                        String note = currentCheckInData.get(3);
                        int guests = Integer.parseInt(currentCheckInData.get(4));

                        checkIns.add(new CheckIn(room, fromDate, toDate, note, guests));
                        currentCheckInData.clear();
                    }

                    if (line == null) {
                        break;
                    }
                } else {
                    currentCheckInData.add(line.trim());
                }
            }
            System.out.println("Checkins file successfully opened!");

        } catch (Exception e) {
            System.err.println("Error reading check-ins file.");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void addCheckInToFile(CheckIn checkIn) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(checkInsFilePath, true))) {

            writer.write(checkIn.getRoom());
            writer.newLine();
            writer.write(checkIn.getFromDate().toString());
            writer.newLine();
            writer.write(checkIn.getToDate().toString());
            writer.newLine();
            writer.write(checkIn.getNote() != null && !checkIn.getNote().isEmpty() ? checkIn.getNote() : "No note");
            writer.newLine();
            writer.write(String.valueOf(checkIn.getGuests()));
            writer.newLine();

            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void writeCheckInsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(checkInsFilePath))) {
            for (int i = 0; i < checkIns.size(); i++) {
                CheckIn checkIn = checkIns.get(i);

                writer.write(checkIn.getRoom());
                writer.newLine();
                writer.write(checkIn.getFromDate().toString());
                writer.newLine();
                writer.write(checkIn.getToDate().toString());
                writer.newLine();
                writer.write(checkIn.getNote() != null && !checkIn.getNote().isEmpty() ? checkIn.getNote() : "No note");
                writer.newLine();
                writer.write(String.valueOf(checkIn.getGuests()));
                writer.newLine();

                if (i < checkIns.size() - 1) {
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }


    public void displayCheckIns() {
        if (checkIns.isEmpty()) {
            System.out.println("No check-ins found!");
            return;
        }

        System.out.println("\n--- List of Check-Ins ---");
        for (CheckIn checkIn : checkIns) {
            System.out.println(checkIn.toString());
        }
        System.out.println("-------------------------\n");
    }

    public List<CheckIn> getCheckIns() {
        return checkIns;
    }

    public void setCheckIns(List<CheckIn> checkIns) {
        this.checkIns = checkIns;
    }

    public void addCheckIn(CheckIn checkIn) {
        checkIns.add(checkIn);
    }




}
