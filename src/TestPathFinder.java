import io.LecteurDonnees;

import sim.*;

import graphes.StrategieDijkstra;

import gui.GUISimulator;
import java.awt.Color;
import java.util.ArrayList;

public class TestPathFinder
{
    private DonneesSimulation simData;
    private GUISimulator gui;
    private Simulateur mySim;

    public TestPathFinder(String fileName)
    {
        try{
            this.simData = LecteurDonnees.creeDonnees(fileName);
            System.out.println(simData);
            this.gui = new GUISimulator(800, 600, Color.BLACK);
            this.mySim = new Simulateur(gui, simData);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }

    private void testStrategieDijkstra()
    {
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
    }

    private void testRobotShortestPathCreation()
    {
        for(Robot rob : this.simData.getRobots())
        {
            System.out.println(rob.createShortestPathTo(1, this.simData.getCarte().getCase(0,0), this.simData.getCarte(), this.mySim));
        }
        this.mySim.printEvenements();
    }

    private void testPattesShortestPathCreation()
    {
        this.simData.getRobots()[5].createShortestPathTo(1, this.simData.getCarte().getCase(0, 0), this.simData.getCarte(), this.mySim);
    }

    public static void main(String[] args)
    {
        TestPathFinder testPath = new TestPathFinder(args[0]);
        System.out.println("Testpathfinder created");
        testPath.testRobotShortestPathCreation();
    }
}