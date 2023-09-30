package Server.commands.serviceCommands;

import Common.commands.Command;
import Common.consoles.Console;
import Server.managers.DBManagers.UsersManager;

public class CheckUserPass extends Command {
    private Console console;
    private final UsersManager usersManager;

    public CheckUserPass(Console console, UsersManager usersManager) {
        super("check_user_pass", "check available user password", console);
        this.usersManager = usersManager;
    }

    @Override
    public void execute(String[] strings) {
        usersManager.isPasswordCorrect(strings[0], strings[1]);
    }
}
