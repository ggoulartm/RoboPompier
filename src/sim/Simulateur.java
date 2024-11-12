package sim;

import java.util.ArrayList;

import events.Evenement;
import gui.GUISimulator;
import gui.Simulable;

public class Simulateur implements Simulable {
    private GUISimulator gui;           // Reference to the graphical interface
    private DonneesSimulation data;     // Contains the map, robots, and fires
    private long dateSimulation;
    private Evenement premierEvent;
    ArrayList<Evenement> events = new ArrayList<Evenement>();

    public Simulateur(GUISimulator gui, DonneesSimulation data) {
        this.gui = gui;
        this.data = data;
        this.dateSimulation = 0;
        gui.setSimulable(this);            // Associating to the GUI

        // Initial setup: display the map, robots, and fires
        draw();
    }

    public DonneesSimulation getDonnees() {
        return this.data;
    }

    public void addEvent(Evenement e)
    {
        if (premierEvent == null) {
            premierEvent = e;
        } else if (premierEvent.getDate() >= e.getDate()) {
            e.setNext(premierEvent);
            premierEvent = e;
        } else if (premierEvent.getNext() == null) {
            premierEvent.setNext(e);
        } else {
            Evenement prec = premierEvent;
            Evenement courant = premierEvent.getNext();
            while (courant != null && courant.getDate() < e.getDate()) {
                prec = courant;
                courant = courant.getNext();
            }
            e.setNext(courant);
            prec.setNext(e);
        }
    }
    private void incrementeDate()
    {
        this.dateSimulation++;
    }
    private boolean simulationTerminee()
    {
        return (premierEvent == null);
    }
    public long getDateSimulation() {
        return dateSimulation;
    }
    public Evenement getPremierEvent() {
        return premierEvent;
    }
    public void setPremierEvent(Evenement premierEvent) {
        this.premierEvent = premierEvent;
    }

    // This method is called when the "Next" button is pressed
    @Override
    public void next() {
        draw();
        System.out.println("Next step in the simulation...");

        if (simulationTerminee()) return;
        Evenement premierEvent = getPremierEvent();
        while (premierEvent != null && getDateSimulation() >= premierEvent.getDate()) {
            premierEvent.execute();
            premierEvent = premierEvent.getNext();
        }
        setPremierEvent(premierEvent);

        if (data.getCarte().getTailleCases() != 10000) {
            for (int i = 0; i < 5; i++) incrementeDate();
        } else {
            for (int i = 0; i < 80; i++) incrementeDate();
        }
    }

    // This method is called when the "Restart" button is pressed
    @Override
    public void restart() {
        // Reset the simulation to its initial state
        System.out.println("Simulation restarted.");
        this.dateSimulation = 0;
        this.premierEvent = null;
        draw();
    }

    private void draw() {
        gui.reset();
        Carte carte = data.getCarte();

        int tailleCase = 0;
        if (carte.getNbColonnes() == 8) tailleCase = carte.getTailleCases() / 100;
        else if (carte.getNbColonnes() == 20) tailleCase = 45;
        else tailleCase = 20;

        // Draw cases
        for (int x = 0; x < carte.getNbLignes(); x++) {
            for (int y = 0; y < carte.getNbColonnes(); y++) {
                Case cases_xy = carte.getCase(x, y);
                cases_xy.draw(gui, tailleCase);
            }
        }

        // Draw robots
        Robot[] robots = data.getRobots();
        for (Robot robot : robots) {
            robot.draw(gui, tailleCase);
        }

        // Draw fires
        Incendie[] incendies = getDonnees().getIncendies();
        for (Incendie incendie : incendies) {
            incendie.draw(gui, tailleCase);
        }
    }
}

