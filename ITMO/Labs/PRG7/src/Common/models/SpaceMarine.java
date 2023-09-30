package Common.models;

import Common.exceptions.WrongArgumentsException;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class SpaceMarine implements Serializable {
    public SpaceMarine() {}

    public static Set<Integer> ids = new TreeSet<>();

    public SpaceMarine(String name, Coordinates coordinates, float health, Long heartCount, boolean loyal, MeleeWeapon meleeWeapon, Chapter chapter) {
        //auto-generation
        creationDate = java.time.ZonedDateTime.now();
        //auto-generation
        id = 1;
        //can't be null and empty
        this.name = name;
        //y - can't be null (so, it's double)
        this.coordinates = coordinates;
        // > 0
        this.health = health;
        // can't be null 0 < x <= 3
        this.heartCount = heartCount;
        this.loyal = loyal;
        //can't be null
        this.meleeWeapon = meleeWeapon;
        //can't be null
        this.chapter = chapter;
    }


    private java.time.ZonedDateTime creationDate;

    private int id;

    private String name;
    private Coordinates coordinates;

    private float health;

    private Long heartCount;

    private boolean loyal;

    private MeleeWeapon meleeWeapon;

    private Chapter chapter;

    public java.time.ZonedDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(java.time.ZonedDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public int getId() {
        return id;
    }

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

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public Long getHeartCount() {
        return heartCount;
    }

    public void setHeartCount(Long heartCount) {
        this.heartCount = heartCount;
    }

    public boolean isLoyal() {
        return loyal;
    }

    public void setLoyal(boolean loyal) {
        this.loyal = loyal;
    }

    public MeleeWeapon getMeleeWeapon() {
        return meleeWeapon;
    }

    public void setMeleeWeapon(MeleeWeapon meleeWeapon) {
        this.meleeWeapon = meleeWeapon;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public boolean argsCheckSpaceMarine() {
        return coordinates.argsCheckCoordinates()
                && chapter.argsCheckChapter()
                && name != null && !name.equals("\\s*") && creationDate != null
                && health > 0 && heartCount != null && heartCount > 0
                && heartCount <= 3 && meleeWeapon != null;
    }

    /**
     * Updater for collection elements
     *
     * @param spaceMarine The space marine
     */
    public void updateSpaceMarine(SpaceMarine spaceMarine) {
        setName(spaceMarine.getName());
        setCoordinates(spaceMarine.getCoordinates());
        setHealth(spaceMarine.getHealth());
        setHeartCount(spaceMarine.getHeartCount());
        setLoyal(spaceMarine.isLoyal());
        setChapter(spaceMarine.getChapter());
        setMeleeWeapon(spaceMarine.getMeleeWeapon());
        setCreationDate(spaceMarine.getCreationDate());
    }

    public static class SpaceMarineCoordinatesComparator implements Comparator<SpaceMarine> {
        @Override
        public int compare(SpaceMarine sm1, SpaceMarine sm2) {
            return new Coordinates.CooordiantesComparator().compare(sm1.getCoordinates(), sm2.getCoordinates());
        }
    }

    public static class SpaceMarineHealthComparator implements Comparator<SpaceMarine> {
        @Override
        public int compare(SpaceMarine sm1, SpaceMarine sm2) {
            return Float.compare(sm1.getHealth(), sm2.getHealth());
        }
    }


    @Override
    public String toString() {
        return "SpaceMarine{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", health=" + health +
                ", heartCount=" + heartCount +
                ", loyal=" + loyal +
                ", meleeWeapon=" + meleeWeapon +
                ", chapter=" + chapter +
                "creationDate=" + creationDate +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SpaceMarine that = (SpaceMarine) o;
        return id == that.id && Float.compare(that.health, health) == 0 && loyal == that.loyal && Objects.equals(creationDate, that.creationDate) && Objects.equals(name, that.name) && Objects.equals(coordinates, that.coordinates) && Objects.equals(heartCount, that.heartCount) && meleeWeapon == that.meleeWeapon && Objects.equals(chapter, that.chapter);
    }

    @Override
    public int hashCode() {
        return Objects.hash(creationDate, id, name, coordinates, health, heartCount, loyal, meleeWeapon, chapter);
    }
}
