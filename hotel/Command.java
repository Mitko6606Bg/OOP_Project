package oop.project.hotel;

public interface Command {

    String getName();
    String getHelp();
    void execute(String[] args);

}
