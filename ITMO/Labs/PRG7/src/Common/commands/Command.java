package Common.commands;

import Common.consoles.Console;

public abstract class Command {
    private final String name;
    private final String description;
    protected Console console;


    protected Command(String name, String description, Console console) {
        this.name = name;
        this.description = description;
        this.console = console;
    }


    public String getName() {
        return name;
    }

    /**
     * Abstract method for command execution
     *
     * @param strings Array of string values to execute
     */
    public abstract void execute(String[] strings);

    public String toString() {
        return name + ": " + description;
    }
}
