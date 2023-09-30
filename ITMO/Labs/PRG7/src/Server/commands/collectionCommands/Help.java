package Server.commands.collectionCommands;

import Common.commands.Command;
import Common.consoles.Console;
import Common.exceptions.WrongArgumentsException;

public class Help extends Command {
    private final Command[] commands;

    public Help(Command[] commands, Console console) {
        super("help", "list of available commands and their descriptions ", console);
        this.commands = commands;
    }

    @Override
    public void execute(String[] strings) {
        try {
            if (strings.length != 1) {
                throw new WrongArgumentsException();
            }
            for (Command command : commands) {
                if (!command.getName().equals("updateClientIds") && !command.getName().equals("add_new_user")
                        && !command.getName().equals("check_user") && !command.getName().equals("check_user_pass")
                        && !command.getName().equals("get_user_id")) {
                    console.writeStr("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    console.writeStr(command.toString());
                }


            }
            console.writeStr("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            console.writeStr("execute_script: execute commands from a file");
        } catch (WrongArgumentsException e) {
            console.writeStr(e + ": help command doesn't need additional arguments");
        }
    }

}
