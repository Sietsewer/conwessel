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

    TextField aantalWachtendenVeld;
    Button reserveerKnop;
    Label inLabel;
    Licht licht;
    Tuin tuin;

    public InPanel(String nr, Tuin tuin) {
        this.tuin = tuin;
        // ...
        Label kassaLabel = new Label("KASSA " + nr);
        kassaLabel.setBounds(24, 0, 48, 21);
        add(kassaLabel);
        aantalWachtendenVeld = new TextField();
        aantalWachtendenVeld.setBounds(0, 36, 36, 27);
        add(aantalWachtendenVeld);
        reserveerKnop = new Button();
        reserveerKnop.setLabel("Reserveer");
        reserveerKnop.setBounds(60, 36, 74, 25);
        reserveerKnop.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                synchronized (Tuin.aantalBezoekers) {
                    Tuin.aantalBezoekers++;
                }
            }
        });

        add(reserveerKnop);
        inLabel = new Label("IN " + nr);
        inLabel.setBounds(156, 0, 30, 20);
        add(inLabel);
        licht = new Licht();
        licht.setBounds(156, 36, 24, 24);
        add(licht);
    }

    class Licht extends Canvas {

        private Color kleur = Color.red;

        public Licht() {
            setSize(24, 24);
        }

        public void setGroen() {
            kleur = Color.green;
            repaint();
        }

        public void setRood() {
            kleur = Color.red;
            repaint();
        }

        public void groenPuls() //Zet licht gedurende 3s op groen.
        {
            // ...
        }

        public void paint(Graphics g) {
            g.setColor(kleur);
            g.fillOval(0, 0, 24, 24);
        }
    }

    class ReserveerActionListener /* ... */ {
        // ...
    }
}