package oop.project.hotel.Commands;

import oop.project.hotel.Command;
import oop.project.hotel.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class MakeUnavailableCommand implements Command {
    private Controller controller;

    public MakeUnavailableCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "unavailable";
    }

    @Override
    public String getHelp() {
        return "unavailable <room> <from> <to> <note> - Manually blocks a room for specific dates.";
    }

    @Override
    public void execute(String[] args) {

        if (args.length < 5) {
            System.out.println("Error: Missing parameters.");
            System.out.println("Usage: " + getHelp());
            return;
        }

        try {
            String room = args[1];
            LocalDate fromDate = LocalDate.parse(args[2]);
            LocalDate toDate = LocalDate.parse(args[3]);


            StringBuilder note = new StringBuilder();
            for (int i = 4; i < args.length; i++) {
                note.append(args[i]).append(" ");
            }

            controller.makeRoomUnavailable(room, fromDate, toDate, note.toString().trim());

        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Please strictly use YYYY-MM-DD.");
        }
    }
}