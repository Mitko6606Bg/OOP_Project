package oop.project.hotel.Commands;

import oop.project.hotel.Command;
import oop.project.hotel.Controller;

public class SaveCommand implements Command {
    private Controller controller;

    public SaveCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public String getHelp() {
        return "save - Saves the current state of rooms and check-ins to their respective files.";
    }

    @Override
    public void execute(String[] args) {
        controller.saveFiles();
    }
}