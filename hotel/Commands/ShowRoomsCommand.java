package oop.project.hotel.Commands;

import oop.project.hotel.Command;
import oop.project.hotel.Controller;

public class ShowRoomsCommand implements Command {
    private Controller controller;

    public ShowRoomsCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "showr";
    }

    @Override
    public String getHelp() {
        return "showr - Displays all the rooms from the loaded file.";
    }

    @Override
    public void execute(String[] args) {
        controller.showRooms();
    }
}