import sim.Simulateur;
import sim.DonneesSimulation;
import sim.PrintEvent;

import io.LecteurDonnees;

import gui.GUISimulator;
import java.awt.Color;

public class TestSimulateur
{
    public static void main(String[] args)
    {
        PrintEvent myPrint = new PrintEvent(0, "FUN");
        PrintEvent my2Print = new PrintEvent(1, "MORE FUN");
        try{
            DonneesSimulation simData = LecteurDonnees.creeDonnees(args[0]);
            System.out.println(simData);
            GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
            Simulateur mySim = new Simulateur(gui, simData);
            mySim.addEvent(myPrint);
            mySim.addEvent(my2Print);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
}