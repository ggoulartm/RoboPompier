import sim.Simulateur;
import sim.Direction;
import sim.DonneesSimulation;
import events.Action;
import events.Deplacer;
import io.LecteurDonnees;

import gui.GUISimulator;
import java.awt.Color;

public class TestEvenement {
    private DonneesSimulation simData;
    private Simulateur mySim;
    private GUISimulator gui;

    public TestEvenement(String fileName)
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
    public static void main(String[] args)
    {
        TestEvenement eveTester = new TestEvenement(args[0]);
        eveTester.setDeplacementHorsBornes();
        eveTester.mettreDeplacementConcurrants();
    }

    private void setDeplacementHorsBornes()
    {
        mySim.addEvent(new Deplacer(mySim.getDateSimulation(), simData.getRobots()[0], Direction.EST, simData.getCarte()));
        mySim.addEvent(new Deplacer(mySim.getDateSimulation()+1, simData.getRobots()[0], Direction.SUD, simData.getCarte()));
        mySim.addEvent(new Deplacer(mySim.getDateSimulation()+2, simData.getRobots()[0], Direction.NORD, simData.getCarte()));
        mySim.addEvent(new Deplacer(mySim.getDateSimulation()+3, simData.getRobots()[0], Direction.OUEST, simData.getCarte()));
        mySim.addEvent(new Deplacer(mySim.getDateSimulation()+4, simData.getRobots()[0], Direction.OUEST, simData.getCarte()));
        mySim.addEvent(new Deplacer(mySim.getDateSimulation()+5, simData.getRobots()[0], Direction.OUEST, simData.getCarte()));
        mySim.addEvent(new Deplacer(mySim.getDateSimulation()+6, simData.getRobots()[0], Direction.OUEST, simData.getCarte()));
        mySim.addEvent(new Deplacer(mySim.getDateSimulation()+7, simData.getRobots()[0], Direction.OUEST, simData.getCarte()));
        mySim.addEvent(new Deplacer(mySim.getDateSimulation()+8, simData.getRobots()[0], Direction.OUEST, simData.getCarte()));
        mySim.addEvent(new Deplacer(mySim.getDateSimulation()+9, simData.getRobots()[0], Direction.OUEST, simData.getCarte()));
        this.mySim.printEvenements();
    }

    private void mettreDeplacementConcurrants()
    {
        mySim.addEvent(new Deplacer(mySim.getDateSimulation()+10, simData.getRobots()[0], Direction.EST, simData.getCarte()));
        mySim.addEvent(new Deplacer(mySim.getDateSimulation()+10, simData.getRobots()[1], Direction.NORD, simData.getCarte()));
        mySim.addEvent(new Deplacer(mySim.getDateSimulation()+10, simData.getRobots()[2], Direction.OUEST, simData.getCarte()));
    }
}