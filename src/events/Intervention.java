package events;

import sim.Incendie;
import sim.Robot;
import sim.Simulateur;

/**
 * Class to create a fire extinguish event
 * To make sure that a robot only extinquishes a fire its sitting on
 * try to get the fire from the position the robot sits on when executing
 */
public class Intervention extends Evenement{
    private int quantiteEau;
    private Simulateur sim;
    private Robot robot;

    public Intervention(int date, int quantiteEau, Simulateur sim, Robot robot)
    {
        super(date);
        this.quantiteEau = quantiteEau;
        this.sim = sim;
        this.robot = robot;
    }

    public void execute()
    {
        Incendie incendie = this.sim.getIncendie(this.robot.getPosition());
        try{
            System.out.println("Fire intensity before intervention: "+incendie.getIntensite());
            incendie.reduceIntensite(quantiteEau);
            System.out.println("Fire intensity after intervention: "+incendie.getIntensite());
            this.robot.emptyWater();
        }
        catch (NullPointerException e)
        {
            System.err.println("ERROR: Which Fire are you trying to put out?");
        }
    }
}
