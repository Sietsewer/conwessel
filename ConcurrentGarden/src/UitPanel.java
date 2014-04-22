// S.Verbeek 23-2-2001
import java.awt.*;
import java.applet.*;
import java.util.logging.Level;
import java.util.logging.Logger;

// De UitPanel klasse bestaat uit een label een (draai)hek.
// Klasse Hek is innig verbonden met de UitPanel klasse en is
// daarom als binnenklasse opgenomen.
public class UitPanel extends Panel implements Runnable {

    Label uitLabel;
    Hek hek;
    MyApplet tuinApplet;

    public UitPanel(String nr, MyApplet tuinApplet) {
        this.tuinApplet = tuinApplet;
        uitLabel = new Label("UIT " + nr);
        uitLabel.setBounds(12, 0, 36, 20);
        add(uitLabel);
        hek = new Hek();
        hek.setBounds(0, 24, 40, 40);
        add(hek);

    }

    @Override
    public void run() {
        while (true) {
            if (this.tuinApplet.tuin.getAantalBezoekers() > 0) {
                hek.setOpen();
                this.tuinApplet.tuin.bezoekerVertrekt();
                try {
                    Thread.sleep(200);
                } catch (InterruptedException ex) {
                    Logger.getLogger(UitPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                hek.setDicht();
            }
            try {
                Thread.sleep(this.tuinApplet.tuin.getAantalVrijePlaatsen() * 10);
            } catch (InterruptedException ex) {
                Logger.getLogger(UitPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    class Hek extends Canvas {

        private boolean open = false;

        public Hek() {
            setSize(24, 24);
        }

        public void setOpen() {
            open = true;
            repaint();
        }

        public void setDicht() {
            open = false;
            repaint();
        }

        public void paint(Graphics g) {
            g.setColor(getBackground());
            g.fillRect(0, 0, 40, 40);
            g.setColor(Color.blue);
            if (open) {
                g.fillRect(0, 0, 40, 6);
            } else {
                g.fillRect(0, 0, 6, 40);
            }
        }
    }
}