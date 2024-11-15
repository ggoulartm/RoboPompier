package sim;
import gui.GUISimulator;
import gui.ImageElement;
import java.util.ArrayList;

import events.Deplacer;
import graphes.StrategieDijkstra;
import sim.NatureTerrain;
import strategies.SimpleChefPompier;

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

    public double getVitesseParNature(Case c)
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
    public int createShortestPathTo(int start_date, Case end, Carte carte, Simulateur sim, SimpleChefPompier chef)
    {
        System.out.println("Creating path for Pattes");
        int endDate = 0;
        if(!this.isMoving() && !this.getPosition().equals(end))
        {
            this.setTargetCase(end);
            double tailleCase = sim.getDonnees().getCarte().getTaille();
            double[] natureCosts = {Double.POSITIVE_INFINITY, 
                (double)tailleCase/(double)this.vitesse, 
                (double)tailleCase/Math.min((double)this.vitesse, 10), 
                (double)tailleCase/(double)this.vitesse, 
                (double)tailleCase/(double)this.vitesse};
            ArrayList<Case> shortestPath = StrategieDijkstra.findShortestPath(carte, this.position, end, this.forbiddenTerrains, natureCosts);
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
                int currentCaseTemps = (int)(tailleCase/(2*(double)this.getVitesseParNature(currentCase)));
                int nextCaseTemps = (int)(tailleCase/(2*(double)this.getVitesseParNature(nextCase)));
                int timeToNextCase = Math.max(1, currentCaseTemps+nextCaseTemps);
                int dateAtNextCase = previousDate + timeToNextCase;
                sim.addEvent(new Deplacer(dateAtNextCase, this, dir, carte));
                endDate = dateAtNextCase;
                previousDate = dateAtNextCase;
            }
            this.setMoving(true);
        }
        else
        {
            if(this.isMoving())
                System.out.println("Relax - wait until robot has reached its destination to set a new path!");
            else
            {
                System.out.println("Robot already in target position");
                this.registerAskForInstructions(start_date+1, chef, sim);
            }
        }
        return endDate;
    }

    public int timeTo(Case c, Carte carte)
    {
        double tailleCase = (double)carte.getTaille();
        double[] natureCosts = {Double.POSITIVE_INFINITY, 
            (double)tailleCase/(double)this.vitesse, 
            (double)tailleCase/Math.min((double)this.vitesse, 10), 
            (double)tailleCase/(double)this.vitesse, 
            (double)tailleCase/(double)this.vitesse};
        ArrayList<Case> shortestPath = StrategieDijkstra.findShortestPath(carte, this.position, c, this.forbiddenTerrains, natureCosts);
        if(shortestPath.isEmpty())
            return -1;
        int previousDate = 0;
        int endDate = 0;
        for(int i = 0; i<shortestPath.size()-1;i++)
        {
            Case currentCase = shortestPath.get(i);
            Case nextCase = shortestPath.get(i+1);
            Direction dir = this.getDirectionFromCases(currentCase, nextCase);
            int currentCaseTemps = (int)(tailleCase/(2*(double)this.getVitesseParNature(currentCase)));
            int nextCaseTemps = (int)(tailleCase/(2*(double)this.getVitesseParNature(nextCase)));
            int timeToNextCase = Math.max(1, currentCaseTemps+nextCaseTemps);
            int dateAtNextCase = previousDate + timeToNextCase;
            endDate = dateAtNextCase;
            previousDate = dateAtNextCase;
        }
        return endDate;
    }

    //Ne peut pas se rendre sur de l’eau.
    @Override
    public void setPosition(Case target) {
        if (target.getNature() == NatureTerrain.TERRAIN_LIBRE || target.getNature() == NatureTerrain.HABITAT 
        || target.getNature() == NatureTerrain.FORET || target.getNature() == NatureTerrain.ROCHE) {
            int adjustedSpeed = (target.getNature() == NatureTerrain.FORET) ? this.vitesse / 2 : this.vitesse;
            System.out.println("Robot Pattes moving to target "+target+" at speed " + adjustedSpeed + " km/h.");
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
        int caseX = tailleCase/2 + caseRobot.getColonne() * tailleCase;
        int caseY = tailleCase/2 + caseRobot.getLigne() * tailleCase;

        gui.addGraphicalElement(new ImageElement(caseX, caseY,
                "images/robot-pattes.png",
                tailleCase, tailleCase,
                null
        ));     
    }
}