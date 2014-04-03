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

    private void addGroup() {
        Tuin.addGroup(new Group(11, this.hashCode()));
    }

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
                    addGroup();
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
        Thread u = new Update(this);
        u.start();
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

    class Update extends Thread {

        InPanel inPanel;

        public Update(InPanel inPanel) {
            this.inPanel = inPanel;
        }

        @Override
        public void run() {
            while (isAlive()) {
                System.out.println(Tuin.getNext().size +"\n"+Tuin.getNext().kassa+"\n"+inPanel.hashCode()+"\n"+(Tuin.nextCanEnter() ? "True" : "False"));
                if (((Tuin.getNext().kassa == inPanel.hashCode()) && Tuin.nextCanEnter()) || Tuin.getNext() == null) {
                    inPanel.licht.setRood();
                } else {
                    inPanel.licht.setGroen();
                }
                try {
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}