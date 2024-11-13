package sim;
import gui.GUISimulator;
import java.awt.Color;

import java.util.ArrayList;

import graphes.StrategieDijkstra;
import sim.NatureTerrain;

public class Pattes extends Robot
{
    //Vitesse de base de 30 km/h, réduite à 10 km/h sur du rocher. 
    //Intervention unitaire : 10 litres en 1 sec.
    //Utilise de la poudre. Réservoir considéré infini à l’échelle de la si- mulation. Ne se remplit jamais.
    public Pattes(Case position, int vitesse, int reserve)
    {
        super(position, vitesse, Integer.MAX_VALUE, reserve);
        this.type = RobotType.PATTES;
        this.Deversement = new InterventionUnitaire(10, 1);
    }
    //Ne peut pas se rendre sur de l’eau.
    @Override
    public void createShortestPathTo(Case end, Carte carte, Simulateur sim)
    {
        ArrayList<Case> pathSteps = StrategieDijkstra.findShortestPath(carte, this.getPosition(), end, new NatureTerrain[]{NatureTerrain.EAU}, new double[]{Double.POSITIVE_INFINITY, 10, 10, 10, 10});
    }

    //Ne peut pas se rendre sur de l’eau.
    @Override
    public void setPosition(Case target) {
        if (target.getNature() == NatureTerrain.TERRAIN_LIBRE || target.getNature() == NatureTerrain.HABITAT 
        || target.getNature() == NatureTerrain.FORET) {
            int adjustedSpeed = (target.getNature() == NatureTerrain.FORET) ? this.vitesse / 2 : this.vitesse;
            System.out.println("Robot Pattes moving to target at speed " + adjustedSpeed + " km/h.");
            this.position = target;
        } else {
            System.out.println("Robot Pattes cannot move to this terrain.");
        }
    }


    @Override
    public void draw(GUISimulator gui, int tailleCase) {
        Case caseRobot = this.getPosition();
        int caseX = caseRobot.getColonne() * tailleCase;
        int caseY = caseRobot.getLigne() * tailleCase;

        gui.addGraphicalElement(new gui.Oval(
                caseX, caseY,
                Color.BLACK,                           // Border color
                Color.CYAN,                             // Fill color
                tailleCase, tailleCase
        ));     
    }
    
    @Override
    public String getType()
    {
        return "Pattes";
    }
}