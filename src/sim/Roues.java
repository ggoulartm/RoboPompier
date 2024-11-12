package sim;

import java.util.ArrayList;
import sim.NatureTerrain;
import graphes.StrategiePrim;


public class Roues extends Robot
{
    public Roues(Case position, int vitesse)
    {
        super(position, vitesse);
    }

    @Override
    public void createShortestPathTo(Case end, Carte carte, Simulateur sim)
    {
        ArrayList<Case> pathSteps = StrategiePrim.findShortestPath(carte, this.getPosition(), end, new NatureTerrain[]{NatureTerrain.EAU});
    }

    @Override
    public String getType()
    {
        return "Roues";
    }
}