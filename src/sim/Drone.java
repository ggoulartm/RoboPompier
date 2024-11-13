package sim;
import gui.GUISimulator;
import java.awt.Color;


import java.util.ArrayList;

import graphes.StrategieDijkstra;
import sim.NatureTerrain;


public class Drone extends Robot
{

    //Vitesse par défault de 100 km/h, mais peut être lue dans le fichier de données (sans dépasser 150 km/h)
    //Réservoir de 10000litres. Remplissage complet en 30 minutes. Se remplit sur une case contenant de l’eau.
    //Intervention unitaire: vide la totalité du réservoir en 30 se- condes.
    public Drone(Case position, int vitesse, int max, int reserve)
    {
        super(position, vitesse, max, reserve, RobotType.DRONE);
        if(vitesse == 0)
        {
            this.vitesse = 100;
        } else if (vitesse > 150) {
            this.vitesse = 150;
        }
        if(max == 0)
        {
            this.volumeReservoirMax = 10000;
        }
        this.type = RobotType.DRONE;
        this.tempsRemplissage = 30;
        this.Deversement = new InterventionUnitaire(10000, 30); //Litres/seconde
    }

    //Peut se déplacer sur toutes les cases, quelle que soit leur nature, à vitesse constante.
    @Override
    public void createShortestPathTo(Case end, Carte carte, Simulateur sim)
    {
        ArrayList<Case> pathSteps = StrategieDijkstra.findShortestPath(carte, this.getPosition(), end, new NatureTerrain[]{}, new double[]{10,10,10,10,10});
    }

    @Override
    public void draw(GUISimulator gui, int tailleCase) {
        Case caseRobot = this.getPosition();
        int caseX = caseRobot.getColonne() * tailleCase;
        int caseY = caseRobot.getLigne() * tailleCase;

        gui.addGraphicalElement(new gui.Oval(
                caseX, caseY,
                Color.BLACK,                           // Border color
                Color.PINK,                             // Fill color
                tailleCase, tailleCase
        ));
    }
}


