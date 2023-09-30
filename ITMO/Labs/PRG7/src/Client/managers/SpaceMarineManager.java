package Client.managers;

import Common.consoles.Console;
import Common.exceptions.EndInputException;
import Common.exceptions.StopInputException;
import Common.models.Chapter;
import Common.models.Coordinates;
import Common.models.MeleeWeapon;
import Common.models.SpaceMarine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SpaceMarineManager {
    private final Console console;
    private final String END_INPUT = "end";

    public SpaceMarineManager(Console console) {
        this.console = console;

    }

    public Long getLong(String helpText, int conditionCheck) throws StopInputException, EndInputException {
        String strX;
        Long x;
        console.writeStr(helpText);
        while (console.isNextStr()) {
            strX = console.getNextStr();
            if (strX.equals(END_INPUT)) {
                throw new EndInputException();
            }
            if (TypeCheckingManager.isLong(strX)) {
                x = Long.parseLong(strX);
                if ((x > 0 && x <= 3) || (conditionCheck == 1)) return x;
            }
            console.writeStr(helpText);

        }
        throw new StopInputException();
    }

    /**
     * String to Float parser
     *
     * @param helpText
     * @return
     * @throws StopInputException
     * @throws EndInputException
     */

    public Float getFloat(String helpText) throws StopInputException, EndInputException {
        String strX;
        Float x;
        console.writeStr(helpText);
        while (console.isNextStr()) {
            strX = console.getNextStr();
            if (strX.equals(END_INPUT)) {
                throw new EndInputException();
            }
            if (TypeCheckingManager.isFloat(strX)) {
                if (strX.contains(String.valueOf(","))) {
                    strX = strX.replace(",", ".");
                }
                x = Float.parseFloat(strX);
                if (x > 0) return x;
            }
            console.writeStr(helpText);

        }
        throw new StopInputException();
    }

    /**
     * String to Double parser
     *
     * @param helpText
     * @return
     * @throws StopInputException
     * @throws EndInputException
     */
    public Double getDouble(String helpText) throws StopInputException, EndInputException {
        String strX;
        Double x;
        console.writeStr(helpText);
        while (console.isNextStr()) {
            strX = console.getNextStr();
            if (strX.equals(END_INPUT)) {
                throw new EndInputException();
            }
            if (TypeCheckingManager.isDouble(strX)) {
                if (strX.contains(String.valueOf(","))) {
                    strX = strX.replace(",", ".");
                }
                x = Double.parseDouble(strX);
                if (x != null && x <= 47.0) return x;
            }
            console.writeStr(helpText);
        }
        throw new StopInputException();
    }

    /**
     * String getter
     *
     * @param helpText
     * @param conditionCheck - if 1 - value can't be null  and empty, if 0 - string can be empty
     * @return
     * @throws EndInputException
     * @throws StopInputException
     */
    public String getString(String helpText, int conditionCheck) throws EndInputException, StopInputException {
        String strX;
        console.writeStr(helpText);
        while (console.isNextStr()) {
            strX = console.getNextStr();
            if (strX.equals(END_INPUT)) {
                throw new EndInputException();
            }
            if (!strX.isEmpty() && conditionCheck == 0 && (strX.equals("\"\"") || strX != null || strX.equals("\'\'"))) {
                if (strX.equals("\"\"") || strX.equals("\'\'")) return "";
                return strX;
            }
            if (strX != null && !strX.isEmpty() && !strX.equals("\"\"") && !strX.equals("\'\'") && conditionCheck == 1) {
                return strX;
            }
            console.writeStr(helpText);
        }
        throw new StopInputException();
    }

    /**
     * String to MeleeWeapon parser (enum)
     *
     * @return
     * @throws EndInputException
     * @throws StopInputException
     */
    public MeleeWeapon getMeleeweapon() throws EndInputException, StopInputException {
        String strX;
        MeleeWeapon x;
        Integer tmp;
        List<String> strMeleeWeapon = new ArrayList<>(Arrays.asList("CHAIN_SWORD", "POWER_SWORD", "LIGHTING_CLAW", "POWER_BLADE"));
        String helpText = "Enter the melee weapon value from the list below or enter the number";
        console.writeStr(helpText);
        int counter = 0;
        for (MeleeWeapon meleeWeapon : MeleeWeapon.values()) {
            counter += 1;
            console.writeStr(counter + "-" + meleeWeapon.toString());
        }
        while (console.isNextStr()) {
            strX = console.getNextStr();
            if (strX.equals(END_INPUT)) {
                throw new EndInputException();
            }
            if (TypeCheckingManager.isMeleeWeapon(strX)) {
                x = Enum.valueOf(MeleeWeapon.class, strX);
                return x;
            }
            if (TypeCheckingManager.isInteger(strX)) {
                tmp = Integer.parseInt(strX);
                if (!(tmp >= 1 && tmp <= 4)) {
                    console.writeStr("Index out of range: enter a number from 1 to 4");
                } else {
                    x = Enum.valueOf(MeleeWeapon.class, strMeleeWeapon.get(tmp - 1));
                    return x;
                }
            }
            console.writeStr(helpText);
            for (MeleeWeapon meleeWeapon : MeleeWeapon.values()) {
                console.writeStr(meleeWeapon.toString());
            }
        }
        throw new StopInputException();
    }

    /**
     * String to Boolean parser
     *
     * @return
     * @throws EndInputException
     * @throws StopInputException
     */
    public boolean getLoyal() throws EndInputException, StopInputException {
        String helpText = "Enter a loyal configuration (true or false)";
        console.writeStr(helpText);
        String str;
        Boolean x;
        while (console.isNextStr()) {
            str = console.getNextStr();
            if (str.equals(END_INPUT)) {
                throw new EndInputException();
            }
            if (TypeCheckingManager.isBoolean(str)) {
                x = Boolean.parseBoolean(str);
                return x;
            }
            console.writeStr(helpText);
        }
        throw new StopInputException();
    }

    /**
     * New input SpaceMarine getter
     *
     * @return
     * @throws StopInputException
     * @throws EndInputException
     */

    public SpaceMarine getSpaceMarine() throws StopInputException, EndInputException/*, WrongArgumentsException*/ {
        try {
            //Name can't be null and empty - so, that the cause of "1" state mark
            String name = getString("enter the name of the spaceship (cannot be an empty string or null): ", 1);
            console.writeStr("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            console.writeStr("~       ~       ~        ~    Enter the coordinates     ~        ~       ~      ~");
            console.writeStr("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            // x field doesn't have any has no restrictions other than those imposed by the data type (1)
            long x = getLong("Enter the X coordinate (integer number)", 1);
            console.writeStr("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            double y = getDouble("Enter the Y coordinate (real number <= 47, enter a fractional part separated by a dot or a comma): ");
            console.writeStr("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            Coordinates coordinates = new Coordinates(x, y);
            float health = getFloat("Enter the health value (real number, enter a fraction part separated by a dot or a comma):");
            console.writeStr("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            //heartCount field has a limited range, so parser need a state marker for correct parsing (0)
            Long heartCount = getLong("Enter the count of hearts (integer number in the range [1, 3]): ", 0);
            console.writeStr("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            console.writeStr("~       ~       ~        ~     Enter  the  Chapter      ~        ~       ~      ~");
            console.writeStr("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            //Name can't be null and empty - so, that the cause of "1" state mark
            String chapterName = getString("Enter the name of character (cannot be an empty string or null): ", 1);
            console.writeStr("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            //World field can be empty, so parser need information about this - that's why "0" state mark
            String chapterWorld = getString("Enter the world (write \"\" for an empty field ):", 0);
            console.writeStr("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            Chapter chapter = new Chapter(chapterName, chapterWorld);
            MeleeWeapon meleeWeapon = getMeleeweapon();
            console.writeStr("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            Boolean loyal = getLoyal();
            console.writeStr("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~" +
                    "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            SpaceMarine spaceMarine = new SpaceMarine(name, coordinates, health, heartCount, loyal, meleeWeapon, chapter);
            return spaceMarine;
        } catch (StopInputException | EndInputException e) {
            return null;
        }
    }
}

