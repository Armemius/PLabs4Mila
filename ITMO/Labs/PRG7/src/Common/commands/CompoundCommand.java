package Common.commands;

import Common.consoles.Console;
import Common.models.SpaceMarine;

public abstract class CompoundCommand extends Command{
    private  String name;
    private String description;
    protected CompoundCommand(String name, String description, Console console){
        super(name, description, console);
    }
    public abstract void setSpaceMarine(SpaceMarine spaceMarine);

}
