package sim;
import gui.GUISimulator;
import java.awt.Color;

import java.util.ArrayList;

import events.Deplacer;
import graphes.StrategieDijkstra;
import sim.NatureTerrain;

public class Pattes extends Robot
{
    NatureTerrain[] forbiddenTerrains = {NatureTerrain.EAU};
    //Vitesse de base de 30 km/h, réduite à 10 km/h sur du rocher. 
    //Intervention unitaire : 10 litres en 1 sec.
    //Utilise de la poudre. Réservoir considéré infini à l’échelle de la si- mulation. Ne se remplit jamais.
    public Pattes(Case position, int vitesse, int reserve)
    {
        super(position, vitesse, Integer.MAX_VALUE, reserve, RobotType.PATTES);
        this.type = RobotType.PATTES;
        this.Deversement = new InterventionUnitaire(10, 1);
    }

    public int getVitesseParNature(Case c)
    {
        switch(c.getNature())
        {
            case ROCHE:
                return Math.min(10, this.vitesse);
            case EAU:
                return -1;
            default:
                return this.vitesse;
        }
    }

    //Ne peut pas se rendre sur de l’eau.
    @Override
    public void createShortestPathTo(Case end, Carte carte, Simulateur sim)
    {
        if(!this.isMoving())
        {
            double[] natureCosts = {Double.POSITIVE_INFINITY, this.vitesse, 
                ((double)this.vitesse)/2, this.vitesse, this.vitesse};
            ArrayList<Case> shortestPath = StrategieDijkstra.findShortestPath(carte, this.position, end, forbiddenTerrains, natureCosts);
            for(Case c : shortestPath)
            {
                System.out.println(c);
            }
            
            int previousDate = sim.getDateSimulation();
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
        if(target == this.getTargetCase())
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
                Color.BLACK,                           // Border color
                Color.CYAN,                             // Fill color
                tailleCase, tailleCase
        ));     
    }
}