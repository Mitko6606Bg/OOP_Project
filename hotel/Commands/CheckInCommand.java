package oop.project.hotel.Commands;

import oop.project.hotel.Command;
import oop.project.hotel.Controller;

public class CheckInCommand implements Command {
    private Controller controller;

    public CheckInCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() { return "checkin"; }

    @Override
    public String getHelp() { return "checkin - Start the checkin process."; }

    @Override
    public void execute(String[] args) {
        if (controller.checkIfCheckinFileIsOpened()) {
            System.out.println("No checkins file opened! Open a file now using: open -c <filepath> to continue.");
        } else {
            controller.checkIn();
        }
    }
}