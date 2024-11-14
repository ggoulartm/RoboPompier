package sim;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Queue;
import java.util.LinkedList;

import events.*;
import gui.GUISimulator;
import gui.Simulable;

public class Simulateur implements Simulable {
    private GUISimulator gui;           // Reference to the graphical interface
    private DonneesSimulation data;     // Contains the map, robots, and fires
    private int dateSimulation;
    private Evenement premierEvent, lastEvent;
    // Evenement Management, outer arraylist collects arraylist of events to be executed at certain date 
    ArrayList<ArrayList<Evenement>> dates = new ArrayList<ArrayList<Evenement>>();
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
        if(e.getDate() > dates.size()-1)
        {
            // System.out.println("Starting with "+dates.size()+"days");
            int datesToAdd = (e.getDate()+1)-dates.size();
            // Add every day that's missing between date of Event and current list of dates
            for(int i = 0; i < datesToAdd; i++)
            {
                dates.add(new ArrayList<Evenement>());
            }
            // System.out.println("Dates length: "+dates.size());
            // System.out.println("Date of Event: "+e.getDate());
            dates.get(e.getDate()).add(e);
        }
        else
        {
            // System.out.println("Dates length: "+dates.size());
            // System.out.println("Date of Event: "+e.getDate());
            dates.get(e.getDate()).add(e);
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
    public int getDateSimulation() {
        return dateSimulation;
    }
    public Evenement getPremierEvent() {
        return premierEvent;
    }
    public void setPremierEvent(Evenement premierEvent) {
        this.premierEvent = premierEvent;
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
            for(Evenement e : dates.get(this.dateSimulation))
            {
                e.execute();
            }
        }
        catch(IndexOutOfBoundsException e){
            System.out.println("Nothing to do on date "+this.dateSimulation);
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

    public void printEvenements()
    {
        System.out.println("Simulator Events:");
        System.out.println(this.dates.size());
        for(ArrayList<Evenement> date : this.dates)
        {
            for(Evenement e : date)
            {
                System.out.println(e);
            }
        }
        
    }
}

