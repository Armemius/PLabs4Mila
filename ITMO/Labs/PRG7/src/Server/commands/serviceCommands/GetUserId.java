package Server.commands.serviceCommands;

import Common.commands.Command;
import Common.consoles.Console;
import Server.managers.DBManagers.UsersManager;

public class GetUserId extends Command {

    private Console console;
    private final UsersManager usersManager;

    public GetUserId(Console console, UsersManager usersManager) {
        super("get_user_id", "user id getter", console);
        this.usersManager = usersManager;
    }

    @Override
    public void execute(String[] strings) {
        usersManager.getUserId(strings[0]);
    }
}
