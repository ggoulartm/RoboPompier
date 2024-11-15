package sim;


import java.lang.reflect.Array;
import java.util.*;
import java.util.Queue;
import java.util.LinkedList;

import events.*;
import gui.GUISimulator;
import gui.Simulable;

public class Simulateur implements Simulable {
    private GUISimulator gui;           // Reference to the graphical interface
    private DonneesSimulation data; // Contains the map, robots, and fires
    private int dateSimulation;
    // Evenement Management, outer arraylist collects arraylist of events to be executed at certain date 
    private PriorityQueue<Evenement> dates;

    public Simulateur(GUISimulator gui, DonneesSimulation dataNew) {
        this.gui = gui;
        this.data = dataNew;
        this.dateSimulation = 0;
        this.dates = new PriorityQueue<>(this::compare);
        gui.setSimulable(this);            // Associating to the GUI

        // Initial setup: display the map, robots, and fires
        draw();
    }

    public DonneesSimulation getDonnees() {
        return this.data;
    }

    public int compare(Evenement e1, Evenement e2)
    {
        return e1.getDate() - e2.getDate();
    }

    public void addEvent(Evenement e)
    {
            System.out.println("Dates length: "+dates.size());
            System.out.println("Date of Event: "+e.getDate());
            dates.add(e);
            System.out.println("Added Event to que of events"+e);
    }
    private void incrementeDate()
    {
        this.dateSimulation++;
    }
    public int getDateSimulation() {
        return dateSimulation;
    }
    public Incendie getIncendie(Case pos)
    {
        Incendie[] incs = this.data.getIncendies();
        for(Incendie inc : incs)
        {
            if(inc.getPosition().equals(pos))
            {
                return inc;
            }
        }
        return null;
    }

    // This method is called when the "Next" button is pressed
    @Override
    public void next() {
        System.out.println("It's the "+this.dateSimulation+" day");

        try{
            while(dates.peek().getDate() == this.dateSimulation)
            {
                dates.poll().execute();
            }
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Nothing to do on date "+this.dateSimulation);
        }
        catch(NullPointerException e){
            System.out.println("Nothing to do");
        }

        this.incrementeDate();
        draw();
    }

    // This method is called when the "Restart" button is pressed
    @Override
    public void restart() {
        // Reset the simulation to its initial state
        System.out.println("Simulation restarted.");
        this.dateSimulation = 0;
        this.data.Restore();
        this.dates = new PriorityQueue<>(this::compare);
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

    public void printEvenements()
    {
        System.out.println("Simulator Events: "+this.dates.size());
        //System.out.println("Priority Queue: "+this.dates);
        
    }
}

