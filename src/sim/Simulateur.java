package sim;


import java.lang.reflect.Array;
import java.util.*;
import java.util.Queue;
import java.util.LinkedList;
import strategies.SimpleChefPompier;
import events.*;
import gui.GUISimulator;
import gui.Simulable;

/**
 * Simulateur: l'utilisation d'une file de priorité (PriorityQueue) est particulièrement adaptée pour gérer les événements à exécuter lors de la simulation.
 * Gestion efficace des événements par date : Dans une simulation, chaque événement est programmé pour se produire à un instant précis(représenté par dateSimulation). 
 * Utilisant dates.peek() ou dates.poll(): Ces méthodes permettent respectivement de consulter ou de retirer l'événement ayant la date la plus proche sans parcourir l'ensemble des événements.
 * Insertion flexible des événements : Les événements peuvent être ajoutés dans n'importe quel ordre à la file de priorité. 
 * La structure se charge de les positionner correctement en fonction de leur date. 
 * Cela simplifie le code et évite d'avoir à trier manuellement les événements après chaque insertion.
 */
public class Simulateur implements Simulable {
    private GUISimulator gui;           // Reference to the graphical interface
    private DonneesSimulation data; // Contains the map, robots, and fires
    private int dateSimulation;
    // Evenement Management, outer arraylist collects arraylist of events to be executed at certain date 
    private PriorityQueue<Evenement> dates;
    protected SimpleChefPompier chefPomp;

    public Simulateur(GUISimulator gui, DonneesSimulation dataNew) {
        this.gui = gui;
        this.data = dataNew;
        this.dateSimulation = 0;
        this.dates = new PriorityQueue<>(this::compare);
        gui.setSimulable(this);            // Associating to the GUI

        this.chefPomp = new SimpleChefPompier(dataNew, this);
        // Initial setup: display the map, robots, and fires
        draw();
        this.chefPomp.startStrategy(0);
    }

    public SimpleChefPompier getChefPomp()
    {
        return this.chefPomp;
    }

    /**
     * Get the data of the simulation
     * @return
     */
    public DonneesSimulation getDonnees() {
        return this.data;
    }

    /**
     * Compare two events by their date - used in the priority queue definition
     * @param e1
     * @param e2
     * @return
     */
    public int compare(Evenement e1, Evenement e2)
    {
        return e1.getDate() - e2.getDate();
    }

    /**
     * Add an event to the queue of events
     * @param e Represents the event to be added (deplacement, extinction, remplissage)
     */
    public void addEvent(Evenement e)
    {
            System.out.println("Dates length: "+dates.size());
            System.out.println("Date of Event: "+e.getDate());
            dates.add(e);
            System.out.println("Added Event to que of events"+e);
    }

    /**
     * Increment the date of the simulation
     */
    private void incrementeDate()
    {
        this.dateSimulation++;
    }

    /**
     * Get the date of the simulation
     * @return
     */
    public int getDateSimulation() {
        return dateSimulation;
    }

    /**
     * Get the fire at a certain position
     * @param pos  The position of the fire
     * @return Incendie The fire at the position
     */
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
        System.out.println("It's the "+this.dateSimulation+" second");

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
        System.out.println("PriorityQueue\n"+this.dates);
        this.dates = new PriorityQueue<>(this::compare);
        this.chefPomp = new SimpleChefPompier(this.data, this);
        System.out.println("PriorityQueue\n"+this.dates);
        draw();
        this.chefPomp.startStrategy(0);
        System.out.println("PriorityQueue\n"+this.dates);
    }

    /**
     * Draw the map, robots, and fires
     */
    private void draw() {
        gui.reset();
        Carte carte = data.getCarte();

        int tailleCase;
        if (carte.getNbColonnes() == 8) tailleCase = 600 / (carte.getNbColonnes()) - 10;
        else tailleCase = 50;

        // Draw cases
        for (int x = 0; x < carte.getNbLignes(); x++) {
            for (int y = 0; y < carte.getNbColonnes(); y++) {
                Case cases_xy = carte.getCase(x, y);
                cases_xy.draw(gui, tailleCase);
            }
        }

        // Draw fires
        Incendie[] incendies = getDonnees().getIncendies();
        for (Incendie incendie : incendies) {
            incendie.draw(gui, tailleCase);
        }

        // Draw robots
        Robot[] robots = data.getRobots();
        for (Robot robot : robots) {
            robot.draw(gui, tailleCase);
        }
    }

    /**
     * Print the simulator events in the console (for debugging purposes)
     */
    public void printEvenements()
    {
        System.out.println("Simulator Events: "+this.dates.size());
        //System.out.println("Priority Queue: "+this.dates);
        
    }
}

