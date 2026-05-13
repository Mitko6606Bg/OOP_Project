package oop.project.hotel.Commands;

import oop.project.hotel.Command;
import oop.project.hotel.Controller;

public class SaveAsCommand implements Command {
    private Controller controller;

    public SaveAsCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() { return "save"; }

    @Override
    public String getHelp() {
        return "save as [-c] <path> - Saves rooms or checkins (-c) to a new file.";
    }

    @Override
    public void execute(String[] args) {

        if (args.length < 3 || !args[1].equalsIgnoreCase("as")) {
            System.out.println("Error: Invalid syntax. " + getHelp());
            return;
        }

        boolean isCheckin = args[2].equals("-c");
        String path = isCheckin ? (args.length > 3 ? args[3] : null) : args[2];

        if (path == null) {
            System.out.println("Error: Missing file path.");
            return;
        }

        controller.saveAs(isCheckin, path);
    }
}