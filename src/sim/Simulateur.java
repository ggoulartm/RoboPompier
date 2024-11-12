package sim;

import java.util.ArrayList;

import events.Evenement;
import gui.GUISimulator;
import gui.Simulable;

public class Simulateur implements Simulable {
    private GUISimulator gui;           // Reference to the graphical interface
    private DonneesSimulation data;     // Contains the map, robots, and fires
    private long dateSimulation;
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
        this.events.add(e);
    }
    private void incrementeDate()
    {
        this.dateSimulation++;
    }
    private boolean simulationTerminee()
    {
        return (this.dateSimulation >= events.size());
    }

    // This method is called when the "Next" button is pressed
    @Override
    public void next() {
        // For now, just print something
        System.out.println("Next step in the simulation...");
        try{
            this.events.get((int)this.dateSimulation).execute();
            this.incrementeDate();
        }
        catch(IndexOutOfBoundsException e)
        {
            System.out.println(e);
        }
    }

    // This method is called when the "Restart" button is pressed
    @Override
    public void restart() {
        // Reset the simulation to its initial state
        System.out.println("Simulation restarted.");
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

