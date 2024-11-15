package events;

import sim.Robot;
import sim.Simulateur;
import sim.Case;
import sim.NatureTerrain;

import java.util.ArrayList;

public class Remplir extends Evenement{
    private Simulateur sim;
    private Robot robot;

    public Remplir(int date, Simulateur sim, Robot robot)
    {
        super(date);
        this.robot = robot;
        this.sim = sim;
    }

    private boolean waterNextTo(Case pos)
    {
        // System.out.println("    Check for water at "+pos);
        if(pos.getNature() == NatureTerrain.EAU)
            return true;

        ArrayList<Case> neighbours = sim.getDonnees().getCarte().getNeighbours(pos);
        System.out.println("    Found: "+neighbours.isEmpty()+" neighbours");
        for(Case c : neighbours)
        {
            if (c.getNature() == NatureTerrain.EAU)
                return true;
        }
        return false;
    }

    public void execute()
    {
        System.out.println("Execute Remplir");
        System.out.println("Try to refill reservoir");
        // System.out.println("Water next to pos: "+waterNextTo(this.robot.getPosition()));
        if(waterNextTo(this.robot.getPosition()))
        {            
            this.robot.fillReservoir();
            System.out.println(this.robot+" has filled his reservoir!");
        }
        else
        {
            System.err.println("ERROR: You have to find water");
        }
    }

    @Override
    public String toString()
    {
        return "Remplir event at "+this.getDate()+" and robot: "+this.robot;
    }
}
