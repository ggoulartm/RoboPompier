package sim;
import gui.GUISimulator;
import strategies.SimpleChefPompier;

import gui.ImageElement;
import java.util.ArrayList;

import events.Deplacer;
import graphes.StrategieDijkstra;

public class RobotCaterpillar extends Robot {
    private NatureTerrain[] forbiddenTerrains = {NatureTerrain.EAU, NatureTerrain.ROCHE};

    //Vitesse par défaut de 60 km/h, mais qui peut être lue dans le fichier (sans dépasser 80 km/h)
    //Réservoir de 2000 litres. Intervention unitaire : 100 litres en 8 sec.
    //Remplissage complet en 5 minutes. Se remplit à côté d’une case contenant de l’eau.
    public RobotCaterpillar(Case position, int vitesse, int waterCapacityMax, int reserveWaterAmount) {
        super(position,vitesse,waterCapacityMax,reserveWaterAmount, RobotType.CATERPILLAR);  
        if(vitesse == 0)
        {
            this.vitesse = 60;
        } else if (vitesse > 80) {
            this.vitesse = 80;
        }
        if(waterCapacityMax == 0)
        {
            this.volumeReservoirMax = 2000;
        }
        this.type = RobotType.CATERPILLAR;
        this.tempsRemplissage = 5;
        this.Deversement = new InterventionUnitaire(100, 8); //Litres/seconde
    }

    //La vitesse est diminuée de 50% en forêt.
    //Ne peut pas se rendre sur de l’eau ou du rocher.
    @Override
    public void setPosition(Case target) {
        if (target.getNature() == NatureTerrain.TERRAIN_LIBRE || target.getNature() == NatureTerrain.HABITAT || target.getNature() == NatureTerrain.FORET) {
            int adjustedSpeed = (target.getNature() == NatureTerrain.FORET) ? this.vitesse / 2 : this.vitesse;
            System.out.println("Caterpillar robot moving to target at speed " + adjustedSpeed + " km/h.");
            this.position = target;
        } else {
            System.out.println("Caterpillar robot cannot move to this terrain.");
        }
        if(target.equals(this.getTargetCase()))
        {
            this.setMoving(false);
            System.out.println();
        }
    }

    @Override
    public void draw(GUISimulator gui, int tailleCase) {
        Case caseRobot = this.getPosition();
        int caseX = tailleCase/2 + caseRobot.getColonne() * tailleCase;
        int caseY = tailleCase/2 + caseRobot.getLigne() * tailleCase;
        gui.addGraphicalElement(new ImageElement(
                caseX, caseY,
                "images/robot-chenilles.png",
                tailleCase, tailleCase,
                null
        ));
    }

    public double getVitesseParNature(Case c)
    {
        switch(c.getNature())
        {
            case EAU:
                return -1;
            case FORET:
                return (double)this.vitesse/2.0;
            case ROCHE:
                return -1;
            case TERRAIN_LIBRE:
                return (double)this.vitesse;
            case HABITAT:
                return (double)this.vitesse;
            default:
                return -1;
        }

    }

    public int createShortestPathTo(int start_date, Case end, Carte carte, Simulateur sim, SimpleChefPompier chef)
    {
        int endDate = -1;
        if(!this.isMoving())
        {
            this.setTargetCase(end);
            double tailleCase = carte.getTaille();
            double[] natureCosts = {Double.POSITIVE_INFINITY, 
                (double)tailleCase/((double)this.vitesse/2.0), 
                Double.POSITIVE_INFINITY, 
                (double)tailleCase/this.vitesse, 
                (double)tailleCase/this.vitesse};
            ArrayList<Case> shortestPath = StrategieDijkstra.findShortestPath(carte, this.position, end, new NatureTerrain[]{NatureTerrain.EAU, NatureTerrain.ROCHE}, natureCosts);
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

    @Override
    public int timeTo(Case c, Carte carte)
    {
        double tailleCase = carte.getTaille();
        double[] natureCosts = {Double.POSITIVE_INFINITY, 
            (double)tailleCase/((double)this.vitesse/2.0), 
            Double.POSITIVE_INFINITY, 
            (double)tailleCase/this.vitesse, 
            (double)tailleCase/this.vitesse};
        ArrayList<Case> shortestPath = StrategieDijkstra.findShortestPath(carte, this.position, c, new NatureTerrain[]{NatureTerrain.EAU, NatureTerrain.ROCHE}, natureCosts);
        
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

}
