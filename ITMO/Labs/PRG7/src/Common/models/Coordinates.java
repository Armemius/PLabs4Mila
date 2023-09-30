package Common.models;

import Common.exceptions.WrongArgumentsException;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinates implements Serializable {
    public Coordinates() {}

    public Coordinates(long x, double y) {
        this.x = x;
        this.y = y; //can't be null, <= 47
    }

    private Long x;

    private Double y;

    public long getX() {
        return x;
    }

    /**
     * Long X matcher from input string (for file collection downloading)
     *
     * @param string Input value
     * @return The matcher value
     */
    public static String matchX(String string) {
        Matcher matcher = Pattern.compile("\\d+").matcher(string);
        String x = "";
        while (matcher.find()) {
            x = String.valueOf(matcher.group());
        }
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    /**
     * Double Y matcher from string (for file collection downloading)
     *
     * @param string Input value
     * @return Output value
     */
    public static String matchY(String string) {
        Matcher matcher = Pattern.compile("\\d+\\.\\d+").matcher(string);
        String y = "";
        while (matcher.find()) {
            y = String.valueOf(matcher.group());
        }
        return y;
    }

    public void setY(double y) {
        try {
            if (y > 47) {
                throw new WrongArgumentsException("Y coordinate can't be more than 47!");
            }
            this.y = y;
        } catch (WrongArgumentsException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean argsCheckCoordinates() {
        return x != null && y != null && y <= 47;
    }

    public static class CooordiantesComparator implements Comparator<Coordinates> {
        @Override
        public int compare(Coordinates coordinates1, Coordinates coordinates2) {
            int xComparison = Long.compare(coordinates1.getX(), coordinates2.getX());
            if (xComparison != 0) {
                return xComparison;
            }
            return Double.compare(coordinates1.getY(), coordinates2.getY());
        }
    }

    @Override
    public String toString() {
        return "Coordinates{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinates that = (Coordinates) o;
        return Objects.equals(x, that.x) && Double.compare(that.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
