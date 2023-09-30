package Client.managers;

import Common.models.MeleeWeapon;

import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;

public class TypeCheckingManager {
    public TypeCheckingManager() {}

    public static boolean isMeleeWeapon(String string) {
        try {
            Enum.valueOf(MeleeWeapon.class, string);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }

    public static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isDouble(String string) {
        try {
            Double.parseDouble(string.replace(",", "."));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isLong(String string) {
        try {
            Long.parseLong(string);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isFloat(String string) {
        try {
            Float.parseFloat(string.replace(",", "."));
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }

    public static boolean isBoolean(String string) {
        return string.equals("true") || string.equals("false");
    }

    public static boolean isZoneDateTime(String string) {
        try {
            ZonedDateTime.parse(string); // Kinda sus
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
