// S.Verbeek 23-2-2001
import java.awt.*;
import java.awt.event.*;
import java.applet.*;

// De InPanel bestaat uit een tekstveld, een reserveerknop,
// een (verkeers)licht en enkele labels. De klasse Licht
// hoort bij het InPanel en is daarom als binnenklasse opgenomen.
// Aan de reserveerknop is een ActionListener verbonden.
public class InPanel extends Panel {
    // ...

    MyApplet tuinApplet;
    TextField aantalWachtendenVeld;
    Button reserveerKnop;
    Label inLabel;
    Licht licht;

    public InPanel(String nr, MyApplet tuinApplet) {
        this.tuinApplet = tuinApplet;
        // ...
        Label kassaLabel = new Label("KASSA " + nr);
        kassaLabel.setBounds(24, 0, 48, 21);
        add(kassaLabel);

        aantalWachtendenVeld = new TextField();
        aantalWachtendenVeld.setBounds(0, 36, 36, 27);
        aantalWachtendenVeld.setText("1");
        add(aantalWachtendenVeld);

        reserveerKnop = new Button();
        reserveerKnop.setLabel("Reserveer");
        reserveerKnop.setBounds(60, 36, 74, 25);
        reserveerKnop.addActionListener(new ReserveerActionListener(this));

        add(reserveerKnop);
        inLabel = new Label("IN " + nr);
        inLabel.setBounds(156, 0, 30, 20);
        add(inLabel);
        licht = new Licht();
        licht.setBounds(156, 36, 24, 24);
        add(licht);
        licht.setGroen();
    }

    class Licht extends Canvas {

        private Color kleur = Color.red;

        public Licht() {
            setSize(24, 24);
        }

        public void setGroen() {
            synchronized (kleur) {
                kleur = Color.green;
            }
            repaint();
        }

        public void setRood() {
            synchronized (kleur) {
                kleur = Color.red;
            }
            repaint();
        }

        public void paint(Graphics g) {
            g.setColor(kleur);
            g.fillOval(0, 0, 24, 24);
        }
    }

    class ReserveerActionListener implements Runnable, ActionListener{

        private Thread t = null;
        private MyApplet applet;
        private InPanel panel;
        
        public ReserveerActionListener(InPanel panel){
            this.applet = panel.tuinApplet;
            this.panel = panel;
            
        }

        @Override
        public void run() {
            panel.licht.setRood();
            applet.tuin.reserveer(Integer.parseInt(panel.aantalWachtendenVeld.getText()));
            panel.licht.setGroen();
        }

        @Override
        public void actionPerformed(ActionEvent ae) {
            new Thread(this).start();
        }
    }
}