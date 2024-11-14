package sim;
import gui.GUISimulator;
import java.awt.Color;
import java.util.ArrayList;

import events.Deplacer;
import graphes.StrategieDijkstra;

import sim.NatureTerrain;
import sim.Direction;
public class RobotWheels extends Robot {

    // Vitesse par défaut de 80 km/h, mais qui peut être lue dans le fichier. 
    // Capacité de stockage d’eau de 5000L, mais qui peut être lue dans le fichier.
    //Peut déverser de l’eau sur un incendie.
    //Intervention unitaire: déversement de 100L d’eau en 5 secondes.
    //Remplissage du réservoir: 10 minutes pour remplir le réservoir.
    public RobotWheels(Case position, int vitesse, int waterCapacityMax, int reserveWaterAmount) {
        super(position, vitesse,waterCapacityMax,reserveWaterAmount, RobotType.WHEELS);
        if (vitesse == 0) {
            this.vitesse = 80;
        }
        if(waterCapacityMax == 0) {
            this.volumeReservoirMax = 5000;
        }
        this.type = RobotType.WHEELS;
        this.tempsRemplissage = 10;
        this.Deversement = new InterventionUnitaire(100, 5); //Litres/seconde
    }

    private int getVitesseParNature(Case c)
    {
        switch(c.getNature())
        {
            case TERRAIN_LIBRE:
                return vitesse/10;
            case HABITAT:
                return vitesse/10;
            default:
                return 0;
        }
    }

    //Ne peut se déplacer que sur du terrain libre ou habitat.
    @Override
    public void setPosition(Case target) {
        if (target.getNature() == NatureTerrain.TERRAIN_LIBRE || target.getNature() == NatureTerrain.HABITAT) {
            this.position = target;
        } else {
            System.out.println("Wheeled robot cannot move to this terrain.");
        }
        if(target.equals(this.getTargetCase()))
        {
            this.setMoving(false);
        }
    }

    @Override
    public void draw(GUISimulator gui, int tailleCase) {
        Case caseRobot = this.getPosition();
        int caseX = caseRobot.getColonne() * tailleCase;
        int caseY = caseRobot.getLigne() * tailleCase;

        gui.addGraphicalElement(new gui.Oval(
                caseX, caseY,
                Color.BLACK,                                   // Border color
                Color.WHITE,                             // Fill color
                tailleCase, tailleCase
        ));     }

    /**
     * Finds shortest part from current position to Case end and
     * adds Deplacements to the simulator at the corresponding dates
     */
    @Override
    public void createShortestPathTo(int start_date, Case end, Carte carte, Simulateur sim)
    {
        if(!this.isMoving())
        {
            double[] natureCosts = {Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 
                Double.POSITIVE_INFINITY, this.vitesse, this.vitesse};
            ArrayList<Case> shortestPath = StrategieDijkstra.findShortestPath(carte, this.position, end, new NatureTerrain[]{NatureTerrain.EAU, NatureTerrain.FORET, NatureTerrain.ROCHE}, natureCosts);
            for(Case c : shortestPath)
            {
                System.out.println(c);
            }
            
            int previousDate = start_date;
            for(int i = 0; i<shortestPath.size()-1;i++)
            {
                Case currentCase = shortestPath.get(i);
                Case nextCase = shortestPath.get(i+1);
                Direction dir = this.getDirectionFromCases(currentCase, nextCase);
                int currentCaseVitesse = this.getVitesseParNature(currentCase);
                int nextCaseVitesse = this.getVitesseParNature(nextCase);
                int timeToNextCase = currentCaseVitesse+nextCaseVitesse;
                int dateAtNextCase = previousDate + timeToNextCase;
                sim.addEvent(new Deplacer(dateAtNextCase, this, dir, carte));

                previousDate = dateAtNextCase;
            }
            this.setMoving(true);
        }
        else
        {
            System.out.println("Relax - wait until robot has reached its destination to set a new path!");
        }
    }
}
