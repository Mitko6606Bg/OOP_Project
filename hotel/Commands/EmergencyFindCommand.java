package oop.project.hotel.Commands;

import oop.project.hotel.Command;
import oop.project.hotel.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EmergencyFindCommand implements Command {
    private Controller controller;

    public EmergencyFindCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "!find";
    }

    @Override
    public String getHelp() {
        return "!find <beds> <from> <to> - Emergency VIP search. Tries to free a room by relocating up to 2 reservations.";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 4) {
            System.out.println("Error: Missing parameters.");
            System.out.println("Usage: " + getHelp());
            return;
        }

        try {
            int beds = Integer.parseInt(args[1]);
            LocalDate fromDate = LocalDate.parse(args[2]);
            LocalDate toDate = LocalDate.parse(args[3]);

            controller.emergencyRoomSearch(fromDate, toDate, beds);

        } catch (NumberFormatException e) {
            System.out.println("Error: The number of beds must be a whole number.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Please strictly use YYYY-MM-DD.");
        }
    }
}