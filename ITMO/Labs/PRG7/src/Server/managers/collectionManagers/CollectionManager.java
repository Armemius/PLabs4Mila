package Server.managers.collectionManagers;

import Common.consoles.Console;
import Common.consoles.ServerConsole;
import Common.models.SpaceMarine;
import Server.managers.DBManagers.TableCollectionManager;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static Server.managers.collectionManagers.CommandsManager.history;

public class CollectionManager {
    private ServerConsole console;
    private final LocalDateTime creationDate;
    private static HashSet<SpaceMarine> fileCollection;
    private static final ReentrantReadWriteLock lock = new ReentrantReadWriteLock(true);

    public CollectionManager() {

        fileCollection = new HashSet<>();
        creationDate = LocalDateTime.now();
    }

    public CollectionManager(HashSet<SpaceMarine> collection, ServerConsole console) {

        this();
        fileCollection = collection;
        this.console = console;

    }

    public void setConsole(Console console) {
        this.console = (ServerConsole) console;
    }


    /**
     * Collection setter
     *
     * @param spaceMarines The space marines
     */
    public HashSet<SpaceMarine> setCollection(HashSet<SpaceMarine> spaceMarines) {
        lock.writeLock().lock();
        fileCollection = spaceMarines;
//        for (SpaceMarine sp : fileCollection){
//            System.out.println(sp);
//        }
        lock.writeLock().unlock();
        return fileCollection;
    }

    /**
     * Updating an element with the specified id
     *
     * @param id The id
     * @param spaceMarine The space marine
     */
    public void updateCollectionElement(int id, SpaceMarine spaceMarine) {
        lock.readLock().lock();
        for (SpaceMarine sm : fileCollection) {
            if (sm.getId() == id) {
                sm.updateSpaceMarine(spaceMarine);
                console.writeStr(spaceMarine.getName() + " with id = " + id + " has been updated");
            }
        }
        lock.readLock().unlock();
    }

    /**
     * Collection getter
     */
    public HashSet<SpaceMarine> getCollection() {
        return fileCollection;
    }

    /**
     * Collection elements printer
     */
    public void printCollection() {
        lock.readLock().lock();
        if (!(fileCollection.size() > 0)) {
            console.writeStr("Collection is empty");
        }
        for (SpaceMarine spaceMarine : fileCollection) {
            // System.out.println(spaceMarine.toString());
            console.writeStr(spaceMarine.toString());
        }
        lock.readLock().unlock();
    }


    /**
     * List of last eleven commands getter
     */
    public void getHistory() {
        lock.readLock().lock();
        List<String> tmp = history;
        if (tmp.size() == 0) {
            console.writeStr("History is empty");
        } else {
            for (String command : tmp) {
                console.writeStr(command);
            }
        }
        lock.readLock().unlock();
    }

    /**
     * Find and print collection element with minimum coordinates value
     */
    public void findMinElementByCoordinates() {
        lock.readLock().lock();
        SpaceMarine minCoordinatesSpaceMarine = Collections.min(fileCollection, new SpaceMarine.SpaceMarineCoordinatesComparator());
        console.writeStr(minCoordinatesSpaceMarine.toString());
        lock.readLock().unlock();
    }

    /**
     * Sort and print the lis of collection elements health values
     */
    public void printSortedHealthFields() {
        lock.readLock().lock();
        ArrayList<SpaceMarine> tmp = new ArrayList<>(fileCollection);
        tmp.sort(new SpaceMarine.SpaceMarineHealthComparator());
        for (int i = tmp.size() - 1; i > -1; i--) {
            console.writeStr(String.valueOf(tmp.get(i).getHealth()));
        }
        lock.readLock().unlock();
    }

    public void refreshMarines(TableCollectionManager tableCollectionManager) {
        lock.writeLock().lock();
        setCollection(tableCollectionManager.readSpaceMarines());
        lock.writeLock().unlock();
    }

//    /**
//     * Add new element to the collection if new element health value is more than max one in the collection
//     * @param spaceMarine
//     */
//
//    public void addNewElementIfMax(SpaceMarine spaceMarine) {
//        if (spaceMarine.getHealth() > Collections.max(fileCollection, new SpaceMarine.SpaceMarineHealthComparator()).getHealth()) {
//            fileCollection.add(spaceMarine);
//            console.writeStr("The new element was successfully added to the collection");
//        } else {
//            console.writeStr("Oops, the health field of the new element is less than the maximum one in the collection");
//        }
//    }

    /**
     * Deletes collection elements with health values less than the entered item
     *
     * @param spaceMarine
     */
    public void removeLowerHealth(SpaceMarine spaceMarine) {
        lock.writeLock().lock();
        int counter = 0;
        Iterator<SpaceMarine> iterator = fileCollection.iterator();
        while (iterator.hasNext()) {
            SpaceMarine sm = iterator.next();
            if (spaceMarine.getHealth() > sm.getHealth()) {
                iterator.remove();
                counter += 1;
            }
        }
        console.writeStr("Items were successfully deleted: " + counter);
        lock.writeLock().unlock();
    }

    /**
     * Displaying information about the collection
     */
    public void getInfo() {
        lock.readLock().lock();
        console.writeStr("Collection type: " + fileCollection.getClass().getName());
        console.writeStr("Creation date: " + creationDate);
        console.writeStr("Collection size: " + fileCollection.size());
        lock.readLock().unlock();
    }

    public ServerConsole getConsole() {
        return console;
    }
}
