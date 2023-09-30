package Server.managers.DBManagers;

import Common.consoles.Console;
import Common.consoles.ServerConsole;
import Common.models.Chapter;
import Common.models.Coordinates;
import Common.models.MeleeWeapon;
import Common.models.SpaceMarine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.HashSet;

import static Common.models.SpaceMarine.ids;

public class TableCollectionManager {
    private final Connection connection;
    private Console console;

    public TableCollectionManager(Connection connection, Console console) {
        this.connection = connection;
        this.console = console;
    }

    //insert SpaceMarine object
    public void insertSpaceMarine(SpaceMarine spaceMarine, int user_id) {
        PreparedStatement statement;
        try {
            String stm = "INSERT INTO  collection" +
                    " (name,x, y, health, heartCount,loyal, meleeweapon," +
                    "chapter_name, chapter_world, creationDate, user_id) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)";
            statement = connection.prepareStatement(stm);
            statement.setString(1, spaceMarine.getName());
            statement.setLong(2, spaceMarine.getCoordinates().getX());
            statement.setDouble(3, spaceMarine.getCoordinates().getY());
            statement.setFloat(4, spaceMarine.getHealth());
            statement.setLong(5, spaceMarine.getHeartCount());
            statement.setBoolean(6, spaceMarine.isLoyal());
            statement.setString(7, spaceMarine.getMeleeWeapon().toString());
            statement.setString(8, spaceMarine.getChapter().getName());
            statement.setString(9, spaceMarine.getChapter().getWorld());
            statement.setString(10, spaceMarine.getCreationDate().toString());
            statement.setInt(11, user_id);

            statement.execute();
            console.writeStr("The new element was successfully added to the collection");
            System.out.println("Object inserted");


        } catch (Exception e) {
            System.out.println("Object not inserted: " + e);
        }

    }

    //get SpaceMarine object
    //HashSet<SpaceMarine>
    public HashSet<SpaceMarine> readSpaceMarines() {
        PreparedStatement statement;
        ResultSet sp = null;
        int count = 0;
        HashSet<SpaceMarine> spaceMarines = new HashSet<SpaceMarine>();
        try {
            String stm = "SELECT * FROM collection";
            statement = connection.prepareStatement(stm);
            sp = statement.executeQuery();
            while (sp.next()) {
                SpaceMarine spaceMarine = new SpaceMarine();
                spaceMarine.setId(sp.getInt("id"));
                ids.add(sp.getInt("id"));
                spaceMarine.setName(sp.getString("name"));
                spaceMarine.setCoordinates(new Coordinates(sp.getLong("x"), sp.getDouble("y")));
                spaceMarine.setHealth(sp.getFloat("health"));
                spaceMarine.setHeartCount(sp.getLong("heartCount"));
                spaceMarine.setLoyal(sp.getBoolean("loyal"));
                spaceMarine.setMeleeWeapon(MeleeWeapon.valueOf(sp.getString("meleeweapon")));
                spaceMarine.setChapter(new Chapter(sp.getString("chapter_name"), sp.getString("chapter_world")));
                spaceMarine.setCreationDate(ZonedDateTime.parse(sp.getString("creationDate")));
                spaceMarines.add(spaceMarine);
                count += 1;
                System.out.println("SpaceMarine number " + count + " was added to the collection");

            }
            return spaceMarines;


        } catch (Exception e) {
            System.out.println("SpaceMarine wasn't received: " + e);
        }
        return spaceMarines;
    }

    public void clearObjects(int user_id) {
        PreparedStatement statement;
        try {
            String stm = "DELETE FROM collection WHERE user_id = ?";
            statement = connection.prepareStatement(stm);
            statement.setInt(1, user_id);


            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Objects wasn't deleted: " + e);
        }
    }

    public void addIfMax(SpaceMarine spaceMarine, int user_id) {
        PreparedStatement statement;
        double maxHealth = 0;
        try {
            String stm = "SELECT * FROM collection ORDER BY health DESC LIMIT 1";
            statement = connection.prepareStatement(stm);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                maxHealth = resultSet.getDouble("health");
            }
            if (spaceMarine.getHealth() > maxHealth) {
                insertSpaceMarine(spaceMarine, user_id);
            } else {
                console.writeStr("Oops, the health field of the new element is less than the maximum one in the collection");
            }

        } catch (SQLException e) {
            System.out.println("Object wasn't added: " + e);
        }
    }

    public void removeById(int user_id, int id) {
        PreparedStatement statement;
        try {
            String stm = "SELECT id FROM collection WHERE id = ? and user_id = ?";
            statement = connection.prepareStatement(stm);
            statement.setInt(1, id);
            statement.setInt(2, user_id);

            ResultSet set = statement.executeQuery();
            if (set.next()) {
                removeObject(user_id, id);
                console.writeStr("SpaceMarine with id " + id + " successfully deleted");
            } else {
                console.writeStr("SpaceMarine with this id was not found or you do not have access to a collection item with this id");
            }

        } catch (SQLException e) {
            System.out.println("Something went wrong: " + e);
        }
    }

    public void removeObject(int user_id, int id) {
        PreparedStatement statement;
        try {
            String stm = "DELETE  FROM collection WHERE user_id = ? AND id = ?";
            statement = connection.prepareStatement(stm);
            statement.setInt(1, user_id);
            statement.setInt(2, id);

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Objects wasn't deleted: " + e);
        }
    }

    public void removeLower(double health, int user_id) {
        PreparedStatement statement;
        int counter = 0;
        try {
            String stm = "SELECT * FROM collection WHERE user_id = ? AND health < ?";
            statement = connection.prepareStatement(stm);
            statement.setInt(1, user_id);
            statement.setDouble(2, health);
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                removeObject(user_id, set.getInt("id"));
                counter += 1;
            }
            console.writeStr("Items were successfully deleted: " + counter);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public String getClientIds(int user_id) {
        PreparedStatement statement;
        String ids = "";
        try {
            String stm = "SELECT * FROM collection WHERE user_id = ?";
            statement = connection.prepareStatement(stm);
            statement.setInt(1, user_id);

            ResultSet set = statement.executeQuery();
            while (set.next()) {
                ids += String.valueOf(set.getInt("id")) + " ";
            }
            return ids;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateElement(int id, SpaceMarine spaceMarine) {
        PreparedStatement statement;
        System.out.println(id);
        try {
            String stm = "UPDATE collection SET name = ?, x = ?, y = ?, health = ?," +
                    "heartCount = ?, loyal = ?, meleeWeapon = ?, chapter_name = ?," +
                    "chapter_world = ?, creationDate =  ? WHERE id =?";
            statement = connection.prepareStatement(stm);
            statement.setString(1, spaceMarine.getName());
            statement.setLong(2, spaceMarine.getCoordinates().getX());
            statement.setDouble(3, spaceMarine.getCoordinates().getY());
            statement.setDouble(4, spaceMarine.getHealth());
            statement.setLong(5, spaceMarine.getHeartCount());
            statement.setBoolean(6, spaceMarine.isLoyal());
            statement.setString(7, String.valueOf(spaceMarine.getMeleeWeapon()));
            statement.setString(8, spaceMarine.getChapter().getName());
            statement.setString(9, spaceMarine.getChapter().getWorld());
            statement.setString(10, String.valueOf(spaceMarine.getCreationDate()));

            statement.setInt(11, id);

            statement.executeUpdate();
            console.writeStr("Element with id = " + id + " successfully updated");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setConsole(ServerConsole console) {
        this.console = console;
    }
}
