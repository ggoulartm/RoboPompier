package sim;

import java.util.ArrayList;

import gui.GUISimulator;
import gui.Simulable;

import java.awt.Color;

public class Simulateur implements Simulable {
    private GUISimulator gui;           // Reference to the graphical interface
    private DonneesSimulation data;     // Contains the map, robots, and fires
    private long dateSimulation;

    ArrayList<Evenement> events = new ArrayList<Evenement>();

    public Simulateur(GUISimulator gui, DonneesSimulation data) {
        this.gui = gui;
        this.data = data;
        this.dateSimulation = 0;
        gui.setSimulable(this);		    // Associating to the GUI

        // Initial setup: display the map, robots, and fires
        displaySimulation();
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
        displaySimulation();
    }

    private void displaySimulation() {
        // Clear the previous drawings on the GUI
        gui.reset();

        /* SKELETON TO THE METHOD (DRAWING STUFF) */
        // Drawing the map (cases)
        for (int i = 0; i < data.getCarte().getNbLignes(); i++) {
            for (int j = 0; j < data.getCarte().getNbColonnes(); j++) {
                Case currentCase = data.getCarte().getCase(i, j);
                System.out.println("Drawing carte");
                // Set colors based on the type of terrain
                Color caseColor = getColorForCase(currentCase.getNature());

                // Draw each cell (case) as a rectangle
                gui.addGraphicalElement(new gui.Rectangle(
                        j * data.getCarte().getTailleCases() + data.getCarte().getTailleCases()/2,   // X position
                        i * data.getCarte().getTailleCases() + data.getCarte().getTailleCases()/2,   // Y position
                        Color.BLACK,                           // Border color
                        caseColor,                             // Fill color
                        data.getCarte().getTailleCases()        // Size of the square
                ));
            }
        }

        // Drawing the robots
        // TO DO
        for(int i = 0; i < data.getRobots().length; i++)
        {
            switch (data.getRobots()[i].getType())
            {
                case "Drone":
                    this.drawDrone();
                    break;
                case "Roues":
                    this.drawRoues();
                    break;
                case "Pattes":
                    this.drawPattes();
                    break;
            }
        }

        // Drawing the fires
        // TO DO
        for(int i = 0; i<data.getIncendies().length; i++)
        {
            // this.drawIncendie(date.getIncendies[i]);
        }


    }

    private void drawDrone()
    {
        System.out.println("Draw Drone");
    }

    private void drawRoues()
    {
        System.out.println("Draw Roues");
    }

    private void drawPattes()
    {
        System.out.println("Draw Pattes");
    }

    private void drawIncendie()
    {
        System.out.println("Draw Incendie");
    }

    // Helper method to get the color based on the terrain type
    private Color getColorForCase(NatureTerrain terrain) {
        switch (terrain) {
            case EAU:
                return Color.BLUE;
            case FORET:
                return Color.GREEN;
            case ROCHE:
                return Color.GRAY;
            case TERRAIN_LIBRE:
                return Color.LIGHT_GRAY; // ?
            case HABITAT:
                return Color.YELLOW; // ?
            default:
                return Color.WHITE;
        }
    }
}
