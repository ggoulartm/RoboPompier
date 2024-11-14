package sim;
import gui.GUISimulator;
import gui.ImageElement;
import java.util.ArrayList;

import events.Deplacer;
import graphes.StrategieDijkstra;


public class Pattes extends Robot
{
    //Vitesse de base de 30 km/h, réduite à 10 km/h sur du rocher. 
    //Intervention unitaire : 10 litres en 1 sec.
    //Utilise de la poudre. Réservoir considéré infini à l’échelle de la si- mulation. Ne se remplit jamais.
    public Pattes(Case position, int vitesse, int reserve)
    {
        super(position, vitesse, Integer.MAX_VALUE, reserve, RobotType.PATTES);
        this.type = RobotType.PATTES;
        this.Deversement = new InterventionUnitaire(10, 1);
        this.natureCosts = new double[]{Double.POSITIVE_INFINITY, (double) this.vitesse, ((double) this.vitesse) / 2, (double) this.vitesse, (double) this.vitesse};

        this.forbiddenTerrains = new NatureTerrain[]{NatureTerrain.EAU};
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
    public void createShortestPathTo(int start_date, Case end, Carte carte, Simulateur sim)
    {
        if(!this.isMoving())
        {
            ArrayList<Case> shortestPath = StrategieDijkstra.findShortestPath(carte, this.position, end, forbiddenTerrains, this.natureCosts);
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

    public int findShortestPathTo(Case end, Carte carte){
        ArrayList<Case> shortestPath = StrategieDijkstra.findShortestPath(carte, this.position, end, forbiddenTerrains, this.natureCosts);
        return shortestPath.size();
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
                    int currentCaseVitesse = this.getVitesseParNature(currentCase);
                    int nextCaseVitesse = this.getVitesseParNature(nextCase);
                    int timeToNextCase = currentCaseVitesse+nextCaseVitesse;
                    int dateAtNextCase = previousDate + timeToNextCase;
                    sim.addEvent(new Deplacer(dateAtNextCase, this, dir, carte));

                    previousDate = dateAtNextCase;
                }
                this.setMoving(true);
                this.extinguishFire(end,previousDate,sim);
            }
            else
            {
                System.out.println("Relax - wait until robot is full of water to set a path to the fire!");
            }
    }

    private void extinguishFire(Incendie end, int previousDate, Simulateur sim) {
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
    public void deverserEau(int vol){
        if (this.volumeReservoir > 0 && vol <= this.volumeReservoir) {
            System.out.println("Je deverse d'eau");
            this.volumeReservoir -= vol;
            System.out.println(this.type + " robot pours " + vol + "L of water.");
        }
        this.remplirReservoir();
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

    @Override
    public String toString()
    {
        return "Robot Pattes at: "+this.position.toString()+" with speed: "+this.vitesse;
    }

}