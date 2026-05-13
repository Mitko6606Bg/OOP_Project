package oop.project.hotel.Commands;

import oop.project.hotel.Command;
import oop.project.hotel.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class AvailabilityCommand implements Command {
    private Controller controller;

    public AvailabilityCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "availability";
    }

    @Override
    public String getHelp() {
        return "availability [YYYY-MM-DD] - Shows available rooms for a specific date (defaults to today).";
    }

    @Override
    public void execute(String[] args) {
        LocalDate targetDate;

        if (args.length > 1) {
            try {
                targetDate = LocalDate.parse(args[1]);
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format. Please use YYYY-MM-DD.");
                return;
            }
        } else {
            targetDate = LocalDate.now();
        }

        controller.roomAvailability(targetDate);
    }
}