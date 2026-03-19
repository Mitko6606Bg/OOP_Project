package oop.project.hotel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.IOException;

public class FileController {

    private String filePath;
    private List<Room> rooms;

    public FileController() {
        this.rooms = new ArrayList<>();
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

    public void addRoom(Room room) {
        rooms.add(room);
    }




}
