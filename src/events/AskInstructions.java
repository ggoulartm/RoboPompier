package events;

import sim.Robot;
import strategies.SimpleChefPompier;

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
        System.out.println("Ask chefPompier for instructions");
        this.chefPompier.askInstructions(this.robot);
    }

}
