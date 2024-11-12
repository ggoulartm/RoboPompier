package sim;

import java.util.ArrayList;

import graphes.StrategieDijkstra;
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
        ArrayList<Case> pathSteps = StrategieDijkstra.findShortestPath(carte, this.getPosition(), end, new NatureTerrain[]{}, new double[]{10,10,10,10,10});
    }

    @Override
    public String getType()
    {
        return "Drone";
    }
}