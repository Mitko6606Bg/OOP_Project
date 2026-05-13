package oop.project.hotel.Commands;

import oop.project.hotel.Command;
import oop.project.hotel.Controller;

public class ActivityGuestsCommand implements Command {
    private Controller controller;

    public ActivityGuestsCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public String getName() {
        return "activity_guests";
    }

    @Override
    public String getHelp() {
        return "activity_guests <activity> - Shows all guests/rooms signed up for a specific activity.";
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2) {
            System.out.println("Error: Missing activity name.");
            System.out.println("Usage: " + getHelp());
            return;
        }

        StringBuilder activityName = new StringBuilder();
        for (int i = 1; i < args.length; i++) {
            activityName.append(args[i]).append(" ");
        }

        controller.showActivityGuests(activityName.toString().trim());
    }
}