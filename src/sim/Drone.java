package sim;
import gui.GUISimulator;
import gui.ImageElement;

import java.util.ArrayList;

import events.Deplacer;
import graphes.StrategieDijkstra;

public class Drone extends Robot
{
    NatureTerrain[] forbiddenTerrains = {};
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
    public void createShortestPathTo(int start_date, Case end, Carte carte, Simulateur sim)
    {
        if(!this.isMoving())
        {
            this.setTargetCase(end);
            System.out.println("Move Drone");
            double[] natureCosts = {this.vitesse, this.vitesse, 
            this.vitesse, this.vitesse, this.vitesse};
            ArrayList<Case> shortestPath = StrategieDijkstra.findShortestPath(carte, this.position, end, forbiddenTerrains, natureCosts);
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
                int currentCaseVitesse = this.vitesse/10;
                int nextCaseVitesse = this.vitesse/10;
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

    @Override
    public void draw(GUISimulator gui, int tailleCase) {
        Case caseRobot = this.getPosition();
        int caseX = tailleCase/2 + caseRobot.getColonne() * tailleCase;
        int caseY = tailleCase/2 + caseRobot.getLigne() * tailleCase;

        gui.addGraphicalElement(new ImageElement(
                caseX, caseY,
                "images/robot-drone.png",
                tailleCase, tailleCase,
                null
        ));
    }
}


