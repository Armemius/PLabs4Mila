package Common.models;

import Common.exceptions.WrongArgumentsException;

import java.io.Serializable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chapter implements Serializable {
    public Chapter(String name, String world) {
        this.name = name; //not null && not empty
        this.world = world; //not null, can be empty
    }

    private String name;
    private final String world;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        try {
            if (name == null || name.equals("")) {
                throw new WrongArgumentsException("Name can't be null or empty string");
            }
            this.name = name;
        } catch (WrongArgumentsException ignore) {}
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        try {
            if (name == null) {
                throw new WrongArgumentsException("World can't be null");
            }
            this.name = name;
        } catch (WrongArgumentsException e) {

        }
    }

    /**
     * Data matcher from input string
     *
     * @param string
     * @return
     */
    public static String matchText(String string) {
        Matcher matcher = Pattern.compile("'([^']*)'").matcher(string);
        String text = null;
        while (matcher.find()) {
            text = matcher.group(1);
        }
        return text;
    }

    public boolean argsCheckChapter() {
        return name != null && name != "\\s*" && world != null;
    }


    @Override
    public String toString() {
        return "Chapter{" +
                "name='" + name + '\'' +
                ", world='" + world + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Chapter chapter = (Chapter) o;
        return Objects.equals(name, chapter.name) && Objects.equals(world, chapter.world);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, world);
    }
}

