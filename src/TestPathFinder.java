import io.LecteurDonnees;

import sim.NatureTerrain;
import sim.Case;
import sim.DonneesSimulation;
import sim.Simulateur;

import graphes.StrategiePrim;
import graphes.StrategieDijkstra;

import gui.GUISimulator;
import java.awt.Color;
import java.util.ArrayList;

public class TestPathFinder
{
    public static void main(String[] args)
    {
        try{
            DonneesSimulation simData = LecteurDonnees.creeDonnees(args[0]);
            System.out.println(simData);
            GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
            Simulateur mySim = new Simulateur(gui, simData);
            System.out.println("Searching for shortest path from 0,0 to 4,6");
            ArrayList<Case> shortestPath_noRestrictions = StrategieDijkstra.findShortestPath(simData.getCarte(), 
                                    simData.getCarte().getCase(0,0), simData.getCarte().getCase(4,3),
                                    new NatureTerrain[0]);
            for(Case c : shortestPath_noRestrictions)
            {
                System.out.println(c);
            }
            ArrayList<Case> shortestPath_noWater = StrategieDijkstra.findShortestPath(simData.getCarte(), 
                                    simData.getCarte().getCase(0,0), simData.getCarte().getCase(4,3),
                                    new NatureTerrain[]{NatureTerrain.EAU});
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}