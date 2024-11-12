package sim;

import graphes.StrategiePrim;
import java.util.ArrayList;
import sim.NatureTerrain;


public class Drone extends Robot
{

    public Drone(Case position, int vitesse)
    {
        super(position, vitesse);
    }

    @Override
    public void createShortestPathTo(Case end, Carte carte, Simulateur sim)
    {
        ArrayList<Case> pathSteps = StrategiePrim.findShortestPath(carte, this.getPosition(), end, new NatureTerrain[]{});
    }

    @Override
    public String getType()
    {
        return "Drone";
    }
}