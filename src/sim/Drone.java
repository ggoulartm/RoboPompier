package sim;
import gui.GUISimulator;
import gui.ImageElement;

import java.util.ArrayList;

import events.Deplacer;
import graphes.StrategieDijkstra;

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
        this.natureCosts = new double[]{this.vitesse, this.vitesse,
                this.vitesse, this.vitesse, this.vitesse};
        this.forbiddenTerrains = new NatureTerrain[]{};
    }

    public int findShortestPathTo(Case end, Carte carte){
        ArrayList<Case> shortestPath = StrategieDijkstra.findShortestPath(carte, this.position, end, forbiddenTerrains, this.natureCosts);
        return shortestPath.size();
    }

    //Peut se déplacer sur toutes les cases, quelle que soit leur nature, à vitesse constante.
    @Override
    public void createShortestPathTo(int start_date, Case end, Carte carte, Simulateur sim)
    {
        if(!this.isMoving())
        {
            this.setTargetCase(end);
            System.out.println("Move Drone");

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
            //this.remplir(previousDate,sim);
        }
        else
        {
            System.out.println("Relax - wait until robot has reached its destination to set a new path!");
        }
    }

    public void createShortestPathToIncendie(int start_date, Incendie end, Carte carte, Simulateur sim){
        if(!this.isMoving())
        {
            ArrayList<Case> shortestPath = StrategieDijkstra.findShortestPath(carte, this.position, end.getPosition(), forbiddenTerrains, this.natureCosts);
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
            extinguishFire(end,previousDate, sim);
        }
        else
        {
            System.out.println("Relax - wait until robot is full of water to set a path to the fire!");
        }
    }

    public void extinguishFire(Incendie end, int previousDate,Simulateur sim){
        int interventionNumber = this.volumeReservoir/this.Deversement.volume;
        int interventionNumber_Fire = end.getIntensite()/this.Deversement.volume;
        if(interventionNumber > interventionNumber_Fire) {
            interventionNumber = interventionNumber_Fire;
        }
        int interventions = 0;
        while(interventionNumber > interventions)
        {
            interventions++;
            this.intervenir(previousDate+interventions*this.getDeversementTemps(),sim);
        }
        end.getPosition().extinguish();
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

    @Override
    public String toString()
    {
        return "Robot Drone at: "+this.position.toString()+" with speed: "+this.vitesse;
    }

}


