
import java.applet.Applet;
import java.util.logging.Level;
import java.util.logging.Logger;

// S.Verbeek 23-2-2001
// De Tuin klasse bevat alle elementen die rechtstreeks met de tuin
// hebben te maken (lokaliteitseis). Een aantal methoden is gesynchroniseerd
// omdat ze vanuit diverse threads aangeroepen worden.
// Voor het reserveren is een aparte BusyFlag gebruikt. Dit is nodig om
// een grotere lock scope te verkrijgen zonder dat andere threads geblokkeerd
// raken. Zie soortgelijk voorbeeld in Oaks (p62).
public class Tuin {

    int MAX_AANTAL_BEZOEKERS = 100;
    private int aantalBezoekers = 0;
    public BusyFlag reseveerFlag;
    Applet tuinApplet;

    public Tuin(Applet tuinApplet) {
        this.tuinApplet = tuinApplet;
        this.reseveerFlag = new BusyFlag();
    }

    public int getAantalBezoekers() {
        return aantalBezoekers;
    }

    public int getAantalVrijePlaatsen() {
        return MAX_AANTAL_BEZOEKERS - aantalBezoekers;
    }

    public boolean reserveer(int aantal) {
        reserveerLock();
        Boolean b = true;
        while (b) {
            if (aantal < MAX_AANTAL_BEZOEKERS && aantal < (MAX_AANTAL_BEZOEKERS - aantalBezoekers)) {
                aantalBezoekers += aantal;
                b = false;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(Tuin.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        reserveerUnlock();
        return true;
    }

    public synchronized boolean bezoekerVertrekt() {
        if(aantalBezoekers > 0){
        aantalBezoekers--;
        }return false;
    }

    public void pasLabelsAan() {
    }

    public void reserveerLock() {
        this.reseveerFlag.getBusyFlag();
    }

    public void reserveerUnlock() {
        this.reseveerFlag.freeBusyFlag();
    }

    public Thread getReserveerFlagOwner() {
        return this.reseveerFlag.getBusyFlagOwner();
    }
}