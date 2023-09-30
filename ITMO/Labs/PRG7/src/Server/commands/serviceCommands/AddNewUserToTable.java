package Server.commands.serviceCommands;

import Common.commands.Command;
import Common.consoles.Console;
import Server.managers.DBManagers.UsersManager;

public class AddNewUserToTable extends Command {

    private Console console;
    private final UsersManager usersManager;
    public AddNewUserToTable(Console console, UsersManager usersManager){
        super ("add_new_user", "add new user to the table", console);
        this.usersManager = usersManager;

    }

    @Override
    public void execute(String[] strings){
        //System.out.println(strings[0]);
        usersManager.addNewUser(strings[0], strings[1]);
    }
}
