import sim.Simulateur;
import sim.DonneesSimulation;

import io.LecteurDonnees;

import gui.GUISimulator;
import java.awt.Color;

public class TestSimulateur
{
    public static void main(String[] args)
    {
        try{
            System.out.println("Trying creating simData");
            DonneesSimulation simData = LecteurDonnees.creeDonnees(args[0]);
            System.out.println("Created simData");
            System.out.println(simData);
            GUISimulator gui = new GUISimulator(800, 600, Color.BLACK);
            Simulateur mySim = new Simulateur(gui, simData);
        }
        catch (Exception e)
        {
            System.out.println(e);
        }

    }
}