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

    /**
     * 
     * @param date at which the intervention shall take place
     * @param sim
     * @param robot
     */
    public Intervention(int date, Simulateur sim, Robot robot)
    {
        super(date);
        this.sim = sim;
        this.robot = robot;
    }

    public void execute()
    {
        System.out.println("INTERVENTION of: "+this.robot);
        Incendie incendie = this.sim.getIncendie(this.robot.getPosition());
        try{
            System.out.println("Fire intensity before intervention: "+incendie.getIntensite());
            System.out.println("Fire intensity after intervention: "+incendie.getIntensite());
            switch(this.robot.getType())
            {
                case DRONE:
                    incendie.reduceIntensite(this.robot.getWaterContent());
                    this.robot.emptyWater();
                    break;
                case CATERPILLAR:
                    incendie.reduceIntensite(100);
                    this.robot.emptyWater(100);
                    break;
                case WHEELS:
                    incendie.reduceIntensite(100);
                    this.robot.emptyWater(100);
                    break;
                case PATTES:
                    incendie.reduceIntensite(10);
                    break;
            }
        }
        catch (NullPointerException e)
        {
            System.err.println("ERROR: Which Fire are you trying to put out?");
        }
    }
}
