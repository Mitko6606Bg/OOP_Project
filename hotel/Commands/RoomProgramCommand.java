package oop.project.hotel.Commands;

import oop.project.hotel.Command;
import oop.project.hotel.Controller;

public class RoomProgramCommand implements Command {
    private Controller controller;

    public RoomProgramCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "room_program";
    }

    @Override
    public String getHelp() {
        return "room_program <roomNumber> - Shows the planned activities for a specific room.";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Missing room number.");
            System.out.println("Usage: " + getHelp());
            return;
        }

        String roomNumber = args[1];
        controller.showRoomProgram(roomNumber);
    }
}