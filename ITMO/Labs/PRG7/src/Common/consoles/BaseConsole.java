package Common.consoles;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class BaseConsole implements Console {
    private final Scanner scanner = new Scanner(System.in);

    @Override
    public boolean isNextStr() {
        return true;
    }

    @Override
    public String getNextStr() {
        try {
            return scanner.nextLine().strip();
        } catch (NoSuchElementException e) {
            System.out.println("You interrupted the input stream");
            System.exit(0);
        }
        return null;
    }

    @Override
    public void writeStr(String text) {
        System.out.println(text);
    }

    //заглушка*
    @Override
    public String getText() {
        return "";
    }

    public void close() {
        scanner.close();
    }
}
