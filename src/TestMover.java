import io.LecteurDonnees;

import sim.NatureTerrain;
import sim.RobotWheels;
import sim.Case;
import sim.Direction;
import sim.DonneesSimulation;
import sim.Simulateur;
import sim.Robot;

import graphes.StrategieDijkstra;

import gui.GUISimulator;
import java.awt.Color;
import java.util.ArrayList;

import events.Deplacer;

public class TestMover
{
    private DonneesSimulation simData;
    private GUISimulator gui;
    private Simulateur mySim;

    public TestMover(String fileName)
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

    public void provokeMapExit()
    {
        Robot walli = this.simData.getRobots()[0];
        for(int i = 0; i<4; i++)
        {
            this.mySim.addEvent(new Deplacer(this.mySim.getDateSimulation()+i, walli, Direction.NORD, this.simData.getCarte()));
        }
    }

    public void extinguishFire()
    {
        Robot walli = this.simData.getRobots()[1];
        this.mySim.addEvent(new Deplacer(this.mySim.getDateSimulation()+1, walli, Direction.NORD, this.simData.getCarte()));
        walli.intervenir(this.mySim.getDateSimulation()+2, this.mySim);
        this.mySim.addEvent(new Deplacer(this.mySim.getDateSimulation()+3, walli, Direction.OUEST, this.simData.getCarte()));
        this.mySim.addEvent(new Deplacer(this.mySim.getDateSimulation()+4, walli, Direction.OUEST, this.simData.getCarte()));
        walli.registerFillReservoir(this.mySim.getDateSimulation()+5, this.mySim);
        this.mySim.addEvent(new Deplacer(this.mySim.getDateSimulation()+6, walli, Direction.EST, this.simData.getCarte()));
        this.mySim.addEvent(new Deplacer(this.mySim.getDateSimulation()+7, walli, Direction.EST, this.simData.getCarte()));
        walli.intervenir(this.mySim.getDateSimulation()+8, this.mySim);
        walli.registerFillReservoir(this.mySim.getDateSimulation()+8, this.mySim);
    }

    public static void main(String[] args)
    {
        TestMover unmovedMover = new TestMover(args[0]);
        System.out.println("Testpathfinder created");
        unmovedMover.provokeMapExit();
        unmovedMover.extinguishFire();
    }
}