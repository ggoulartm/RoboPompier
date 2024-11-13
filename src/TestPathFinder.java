import io.LecteurDonnees;

import sim.NatureTerrain;
import sim.RobotWheels;
import sim.Case;
import sim.DonneesSimulation;
import sim.Simulateur;

import graphes.StrategieDijkstra;

import gui.GUISimulator;
import java.awt.Color;
import java.awt.Robot;
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
            ArrayList<Case> shortestPath_noWater = StrategieDijkstra.findShortestPath(simData.getCarte(), 
                                    simData.getCarte().getCase(0,0), simData.getCarte().getCase(5,3),
                                    new NatureTerrain[]{NatureTerrain.EAU, NatureTerrain.ROCHE, NatureTerrain.FORET},
                                    new double[]{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY, 10, 10});
            System.out.println("Found Path:");
            for(Case c : shortestPath_noWater)
            {
                System.out.println(c);
            }

            RobotWheels walli = new RobotWheels(simData.getCarte().getCase(0,0), 50, 1000, 1000);
            walli.createShortestPathTo(simData.getCarte().getCase(5, 3), simData.getCarte(), mySim);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}