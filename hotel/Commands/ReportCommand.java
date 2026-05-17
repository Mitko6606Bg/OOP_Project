package oop.project.hotel.Commands;

import oop.project.hotel.Command;
import oop.project.hotel.Controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ReportCommand implements Command {
    private Controller controller;

    public ReportCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "report";
    }

    @Override
    public String getHelp() {
        return "report <from> <to> - Displays a report on the rooms used and the number of days in a given period.";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 3) {
            System.out.println("Error: Missing parameters.");
            System.out.println("Usage: " + getHelp());
            return;
        }

        try {
            LocalDate fromDate = LocalDate.parse(args[1]);
            LocalDate toDate = LocalDate.parse(args[2]);

            controller.reportRoomUsage(fromDate, toDate);

        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Use YYYY-MM-DD.");
        }
    }
}