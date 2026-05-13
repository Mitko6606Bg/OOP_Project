package oop.project.hotel.Commands;


import oop.project.hotel.Command;
import oop.project.hotel.Controller;

public class CheckOutCommand implements Command {
    private Controller controller;

    public CheckOutCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "checkout";
    }

    @Override
    public String getHelp() {
        return "checkout <roomNumber> - Checks out a guest and frees the room.";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Missing room number.");
            System.out.println("Usage: " + getHelp());
            return;
        }

        String roomNumber = args[1];
        controller.checkOut(roomNumber);
    }
}