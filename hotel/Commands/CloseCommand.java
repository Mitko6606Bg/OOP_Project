package oop.project.hotel.Commands;

import oop.project.hotel.Command;
import oop.project.hotel.Controller;

public class CloseCommand implements Command {
    private Controller controller;

    public CloseCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "close";
    }

    @Override
    public String getHelp() {
        return "close - Closes the currently opened files and clears the memory.";
    }

    @Override
    public void execute(String[] args) {
        controller.closeFiles();
    }
}
