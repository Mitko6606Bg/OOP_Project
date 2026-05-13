package oop.project.hotel.Commands;

import oop.project.hotel.Command;
import oop.project.hotel.Controller;

public class OpenCommand implements Command {
    private Controller controller;

    public OpenCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "open";
    }

    @Override
    public String getHelp() {
        return "open [-c] <path> - Opens a rooms file or a check-ins file (-c flag).";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Missing file path.");
            System.out.println("Usage: " + getHelp());
            return;
        }

        if (args[1].equals("-c")) {
            if (args.length < 3) {
                System.out.println("Error: Missing file path for check-ins.");
                System.out.println("Usage: " + getHelp());
                return;
            }
            controller.openCheckinFile(args[2].toLowerCase());
        } else {
            controller.openFile(args[1].toLowerCase());
        }
    }
}