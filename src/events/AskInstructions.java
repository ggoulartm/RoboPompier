package events;

import sim.Robot;
import strategies.SimpleChefPompier;

/**
 * AskInstructions is an event that asks the SimpleChefPompier for further Instructions
 */
public class AskInstructions extends Evenement{
    
    private Robot robot;
    private SimpleChefPompier chefPompier;

    public AskInstructions(int date, Robot robot, SimpleChefPompier chef)
    {
        super(date);
        this.robot = robot;
        this.chefPompier = chef;
    }

    public void execute()
    {
        System.out.println("ASKINSTRUCTION of: "+this.robot);
        this.chefPompier.askInstructions(this.robot);
    }

}
