/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Sietse
 */
import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Label;

public class MyApplet extends Applet implements Runnable {

    private Label aantalLabel;
    private Label aantalVrijLabel;
    public Tuin tuin;

    /**
     * Initialization method that will be called after the applet is loaded into
     * the browser.
     */
    @Override
    public void paint(Graphics g) {
        this.repaint();
    }

    @Override
    public void init() {
        this.tuin = new Tuin(this);
        setLayout(null);
        setSize(466, 191);
        setBackground(new Color(0, 200, 0));  //groene tuin

        InPanel in1Panel = new InPanel("1", this);
        in1Panel.setLayout(null);
        in1Panel.setBounds(24, 12, 182, 60);
        add(in1Panel);

        InPanel in2Panel = new InPanel("2", this);
        in2Panel.setLayout(null);
        in2Panel.setBounds(24, 96, 181, 62);
        add(in2Panel);

        Label label1 = new Label("AANTAL BEZOEKERS:", Label.CENTER);
        label1.setBounds(240, 48, 156, 24);
        add(label1);
        aantalLabel = new Label("0", Label.CENTER);
        aantalLabel.setBounds(300, 72, 36, 22);
        add(aantalLabel);

        Label label2 = new Label("AANTAL VRIJE PLAATSEN:");
        label2.setBounds(240, 108, 156, 24);
        add(label2);
        aantalVrijLabel = new Label("" + this.tuin.MAX_AANTAL_BEZOEKERS, Label.CENTER);
        aantalVrijLabel.setBounds(300, 132, 36, 22);
        add(aantalVrijLabel);

        UitPanel uit1Panel = new UitPanel("1", this);
        uit1Panel.setLayout(null);
        uit1Panel.setBounds(408, 12, 48, 64);
        add(uit1Panel);
        Thread u1 = new Thread(uit1Panel);
        u1.start();

        UitPanel uit2Panel = new UitPanel("2", this);
        uit2Panel.setLayout(null);
        uit2Panel.setBounds(408, 96, 48, 64);
        add(uit2Panel);
        Thread u2 = new Thread(uit2Panel);
        u2.start();

        Thread update = new UpdateInterface(this);
        update.start();
    }
    // TODO overwrite start(), stop() and destroy() methods

    @Override
    public void run() {
        this.start();
    }

    public class UpdateInterface extends Thread {

        MyApplet applet;

        public UpdateInterface(MyApplet _applet) {
            applet = _applet;
        }

        @Override
        public void run() {
            while (this.isAlive()) {
                applet.aantalLabel.setText(applet.tuin.getAantalBezoekers() + "");
                aantalVrijLabel.setText(applet.tuin.getAantalVrijePlaatsen() + "");

                applet.repaint();
                try {
                    Thread.sleep(200);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
