import graphes.StrategieDijkstra;
import gui.GUISimulator;
import io.LecteurDonnees;
import sim.*;
import sim.Robot;

import java.awt.*;
import java.util.*;

public class TestChefPompier {
    private DonneesSimulation simData;
    private GUISimulator gui;
    private Simulateur mySim;
    protected HashMap<Robot,Incendie> Taches;
    public TestChefPompier(String fileName)
    {
        try{
            this.Taches = new HashMap<>();
            this.simData = LecteurDonnees.creeDonnees(fileName);
            //System.out.println(simData);
            this.gui = new GUISimulator(1024, 768, Color.BLACK);
            this.mySim = new Simulateur(gui, simData);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    private void CalculaDistances() {
        this.Taches.clear();
        this.Taches = new HashMap<>();
        for(Robot rob: this.simData.getRobots()){
            if(rob.isMoving()) continue;
            int prix = Integer.MAX_VALUE;
            for(Incendie inc: this.simData.getIncendies()){
                int shortestPathSize = rob.findShortestPathTo(inc.getPosition(),this.simData.getCarte());
                if(shortestPathSize < prix){
                    Taches.put(rob,inc);
                    prix = shortestPathSize;
                }

            }
        }
    }
    private void Boss()
    {
        System.out.println("Boss");
        CalculaDistances();

        System.out.println(this.Taches.keySet());
        System.out.println(this.Taches.values());
        for(Robot rob: this.Taches.keySet()) {
            Incendie inc = Taches.get(rob);
            System.out.println(rob+" fights "+inc);
            rob.createShortestPathToIncendie(0,inc, this.simData.getCarte(),this.mySim);
        }
        /*
        int i =0;
        while(this.simData.getIncendies().length != 0){
            System.out.println("Boss");
                CalculaDistances();

            System.out.println(this.Taches.keySet());
            System.out.println(this.Taches.values());
                for(Robot rob: this.Taches.keySet()) {
                    Incendie inc = Taches.get(rob);
                    System.out.println(rob+" fights "+inc);
                    rob.createShortestPathToIncendie(i,inc, this.simData.getCarte(),this.mySim);
                }

                for(Robot rob: this.simData.getRobots()){
                    if (rob.isEmpty()) {
                        Case[] Water = this.simData.getWater();
                        for (Case c : Water) {
                            rob.createShortestPathTo(i, c, this.simData.getCarte(), this.mySim);
                        }
                    }
                }
                i ++;
        }
         */
        this.mySim.printEvenements();

    }

    public static void main(String[] args)
    {
        TestChefPompier testBoss = new TestChefPompier(args[0]);
        System.out.println("TestChefPompier created");
        testBoss.Boss();
    }
}
