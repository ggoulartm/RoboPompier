import gui.GUISimulator;
import io.LecteurDonnees;
import sim.DonneesSimulation;
import sim.Simulateur;
import strategies.SimpleChefPompier;
import gui.GUISimulator;
import java.awt.Color;

public class TestChefPompier {
    private Simulateur mySim;
    private DonneesSimulation simData = null;
    private GUISimulator gui;

    private SimpleChefPompier chefPomp;

    public TestChefPompier(String fileName)
    {
        try{
            this.simData = LecteurDonnees.creeDonnees(fileName);
            System.out.println(simData);
            this.gui = new GUISimulator(800, 600, Color.BLACK);
            this.mySim = new Simulateur(gui, simData);
            this.chefPomp = this.mySim.getChefPomp();
        }
        catch (Exception e)
        {
            System.out.println(e);
        }
    }
    
    public SimpleChefPompier getChefPomp()
    {
        return this.chefPomp;
    }

    public static void main(String[] args)
    {
        TestChefPompier pompTester = new TestChefPompier(args[0]);
        pompTester.getChefPomp().startStrategy(0);
    }
}
