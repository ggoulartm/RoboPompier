import gui.GUISimulator;
import gui.Simulable;

import java.awt.Color;

public class Simulateur implements Simulable {
    private GUISimulator gui;           // Reference to the graphical interface
    private DonneesSimulation data;     // Contains the map, robots, and fires

    public Simulateur(GUISimulator gui, DonneesSimulation data) {
        this.gui = gui;
        this.data = data;
        gui.setSimulable(this);		    // Associating to the GUI

        // Initial setup: display the map, robots, and fires
        displaySimulation();
    }

    public DonneesSimulation getDonnees() {
        return this.data;
    }

    // This method is called when the "Next" button is pressed
    @Override
    public void next() {
        // For now, just print something
        System.out.println("Next step in the simulation...");
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

                // Set colors based on the type of terrain
                Color caseColor = getColorForCase(currentCase.getNature());

                // Draw each cell (case) as a rectangle
                gui.addGraphicalElement(new gui.Rectangle(
                        j * data.getCarte().getTailleCases(),   // X position
                        i * data.getCarte().getTailleCases(),   // Y position
                        Color.BLACK,                           // Border color
                        caseColor,                             // Fill color
                        data.getCarte().getTailleCases()        // Size of the square
                ));
            }
        }

        // Drawing the robots
        // TO DO

        // Drawing the fires
        // TO DO

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
