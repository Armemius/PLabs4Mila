package Server.Commands;

import Common.Commands.Command;
import Common.Consoles.Console;
import Common.Exceptions.WrongArgumentsException;

public class Help extends Command {
    private Command[] commands;

    public Help (Command[] commands, Console console){
        super("help", "list of available commands and their descriptions ", console);
        this.commands = commands;
    }

    @Override
    public void execute(String[] strings) {
        try{
            if (strings.length != 0){
                throw new WrongArgumentsException();
            }for (Command command : commands){
                if(!command.getName().equals("updateClientIds")) {
                    console.writeStr("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                            "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    console.writeStr(command.toString());
                }
            }
        }catch (WrongArgumentsException e){
            console.writeStr(e.toString()+": help command doesn't need additional arguments");
        }
    }

}
