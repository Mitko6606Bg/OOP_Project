package oop.project.hotel.Commands;

import oop.project.hotel.Command;
import oop.project.hotel.Controller;

public class DisplayCheckinsCommand implements Command {
    private Controller controller;

    public DisplayCheckinsCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "display_checkin";
    }

    @Override
    public String getHelp() {
        return "display_checkin - Displays all current check-ins.";
    }

    @Override
    public void execute(String[] args) {
        if (controller.checkIfCheckinFileIsOpened()) {
            System.out.println("No checkins file opened! Open a file now using: open -c <filepath> to continue.");
        } else {
            controller.showCheckins();
        }
    }
}