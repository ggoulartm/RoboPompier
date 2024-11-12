package sim;

import java.util.ArrayList;

import graphes.StrategieDijkstra;
import sim.NatureTerrain;

public class Roues extends Robot
{
    public Roues(Case position, int vitesse)
    {
        super(position, vitesse);
    }

    @Override
    public void createShortestPathTo(Case end, Carte carte, Simulateur sim)
    {
        ArrayList<Case> pathSteps = StrategieDijkstra.findShortestPath(carte, this.getPosition(), end, new NatureTerrain[]{NatureTerrain.EAU}, new double[]{Double.POSITIVE_INFINITY, 10, 10, 10, 10});
    }

    @Override
    public String getType()
    {
        return "Roues";
    }
}