
import java.util.ArrayList;

// S.Verbeek 23-2-2001
// De Tuin klasse bevat alle elementen die rechtstreeks met de tuin
// hebben te maken (lokaliteitseis). Een aantal methoden is gesynchroniseerd
// omdat ze vanuit diverse threads aangeroepen worden.
// Voor het reserveren is een aparte BusyFlag gebruikt. Dit is nodig om
// een grotere lock scope te verkrijgen zonder dat andere threads geblokkeerd
// raken. Zie soortgelijk voorbeeld in Oaks (p62).
public class Tuin {

    static int MAX_AANTAL_BEZOEKERS = 100;
    static Integer aantalBezoekers = 0;
    private static ArrayList<Group> waiting;

    // Attributen
    // ...
    public Tuin() {
        // ...
    }
    // Methoden (zie klassediagram in lesmateriaal)
    // ...

    public static void init() {
        waiting = new ArrayList<>();
    }

    synchronized public static void addGroup(Group g) {
        synchronized (waiting) {
            waiting.add(g);
        }
    }

    synchronized public static boolean nextCanEnter() {
        synchronized (waiting) {
            if(waiting.size() > 0){
                Group n = waiting.get(0);
                return n.size >= (MAX_AANTAL_BEZOEKERS - aantalBezoekers);
            }
            return true;
        }
    }

    synchronized public static void nextGroup() {
        if (nextCanEnter()) {
            synchronized (waiting) {
                synchronized (aantalBezoekers) {
                    aantalBezoekers += waiting.remove(0).size;
                }
            }
        }
    }

    synchronized public static Group getNext() {
        if (waiting.size() > 0) {
            return waiting.get(0);
        } else {
            return new Group(0, 0);
        }
    }
}