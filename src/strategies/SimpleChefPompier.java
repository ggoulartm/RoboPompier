package strategies;

import java.util.ArrayList;

import sim.DonneesSimulation;
import sim.Incendie;
import sim.Robot;
import sim.RobotWheels;
import sim.Simulateur;
import sim.Case;
import sim.Pattes;
import sim.RobotCaterpillar;

public class SimpleChefPompier {
 
    private DonneesSimulation simData;
    private Simulateur sim;

    public SimpleChefPompier(DonneesSimulation simData, Simulateur sim)
    {
        System.out.println("Create Chef Pompier");
        this.simData = simData;
        this.sim = sim;
    }

    public void startStrategy(int date)
    {
        ArrayList<Integer> fireArrivalDates = this.sendRobotsToFires(date);
    }

    public void giveInstructionsToDrone(Robot robot)
    {
        if(robot.onBurningFire(this.simData) && robot.getWaterContent()>100)
        {
            System.out.println("Let Robot put out the fire its standing on");
            robot.intervenir(this.sim.getDateSimulation()+30, this.sim);
            robot.registerAskForInstructions(this.sim.getDateSimulation()+30, this, this.sim);
        }
        else if(robot.getWaterContent()<=100 && !robot.nextToWater(this.simData.getCarte()))
        {
            System.out.println("Sending robot to Refill Water");
            this.sendRobotToWater(robot);   
        }
        else if(robot.getWaterContent() <= 100 && robot.nextToWater(this.simData.getCarte()))
        {
            //Let the robot fill with water for 30min
            robot.registerFillReservoir(this.sim.getDateSimulation()+30*60, sim, this);
        }
        else
        {
            System.out.println("Sending Caterpillar to nearest burning fire");
            this.sendRobotToFire(robot);
        }


        // System.out.println(robot+" asked for Instructions");
        // if(robot.getWaterContent() <= robot.getMaxWaterContent()/2)
        // {
        //     System.out.println("Sending robot to Refill Water");
        //     this.sendRobotToWater(robot);
        // }
        // else if(robot.getWaterContent() > robot.getMaxWaterContent()/2)
        // {
        //     boolean alreadyOnFire = false;
        //     for(Incendie inc : this.simData.getIncendies())
        //     {
        //         if(inc.getIntensite()>0 && inc.getPosition().equals(robot.getPosition()))
        //         {
        //             System.out.println("Let Robot put out the fire its standing on");
        //             // Take thirty seconds to dump all water
        //             robot.intervenir(this.sim.getDateSimulation()+30, this.sim);
        //             robot.registerAskForInstructions(this.sim.getDateSimulation()+30, this, this.sim);
        //             alreadyOnFire = true;
        //             break;
        //         }
        //     }
        //     if(!alreadyOnFire)
        //     {
        //         System.out.println("Sending robot to nearest burning fire");
        //         this.sendRobotToFire(robot);
        //     }
        // }
    }

    public void giveInstructionsToCaterpillar(Robot robot)
    {
        if(robot.onBurningFire(this.simData) && robot.getWaterContent()>100)
        {
            System.out.println("Let Robot put out the fire its standing on");
            robot.intervenir(this.sim.getDateSimulation()+8, this.sim);
            robot.registerAskForInstructions(this.sim.getDateSimulation()+8, this, this.sim);
        }
        else if(robot.getWaterContent()<=100 && !robot.nextToWater(this.simData.getCarte()))
        {
            System.out.println("Sending robot to Refill Water");
            this.sendRobotToWater(robot);   
        }
        else if(robot.getWaterContent() <= 100 && robot.nextToWater(this.simData.getCarte()))
        {
            //Let the robot fill with water for 300sek
            robot.registerFillReservoir(this.sim.getDateSimulation()+5*60, sim, this);
        }
        else
        {
            System.out.println("Sending Caterpillar to nearest burning fire");
            this.sendRobotToFire(robot);
        }
    }

    public void giveInstructionsToWheels(Robot robot)
    {
        if(robot.onBurningFire(this.simData) && robot.getWaterContent()>100)
        {
            System.out.println("Let Robot put out the fire its standing on");
            robot.intervenir(this.sim.getDateSimulation()+5, this.sim);
            robot.registerAskForInstructions(this.sim.getDateSimulation()+5, this, this.sim);
        }
        else if(robot.getWaterContent()<=100 && !robot.nextToWater(this.simData.getCarte()))
        {
            System.out.println("Sending robot to Refill Water");
            this.sendRobotToWater(robot);   
        }
        else if(robot.getWaterContent() <= 100 && robot.nextToWater(this.simData.getCarte()))
        {
            //Let the robot fill with water for 300sek
            robot.registerFillReservoir(this.sim.getDateSimulation()+10*60, sim, this);
        }
        else
        {
            System.out.println("Sending Caterpillar to nearest burning fire");
            this.sendRobotToFire(robot);
        }
    }

    public void giveInstructionsToPattes(Robot robot)
    {
        System.out.println("Pattes asks for new Instruction");
        if(robot.onBurningFire(this.simData))
        {
            System.out.println("Let Robot put out the fire its standing on");
            robot.intervenir(this.sim.getDateSimulation()+1, this.sim);
            robot.registerAskForInstructions(this.sim.getDateSimulation()+1, this, this.sim);
        }
        else
        {
            System.out.println("Sending pattes to nearest burning fire");
            this.sendRobotToFire(robot);
        }

    }

    /**
     * Robot can shedule this event to ask for new instructions
     * @param robot
     */
    public void askInstructions(Robot robot)
    {
        switch(robot.getType())
        {
            case DRONE:
                this.giveInstructionsToDrone(robot);
                break;
            case CATERPILLAR:
                this.giveInstructionsToCaterpillar(robot);
                break;
            case PATTES:
                this.giveInstructionsToPattes(robot);
                break;
            case WHEELS:
                this.giveInstructionsToWheels(robot);
                break;
        }
    }

    /**
     * Function that finds the next fire to every robot that is not yet taken
     * @param date start date of the deplacement of the robots
     * @return arraylist of integers with arrival dates of all robots
     */
    public ArrayList<Integer> sendRobotsToFires(int date)
    {
        System.out.println("Send robots to fire");
        ArrayList<Integer> endDates = new ArrayList<Integer>();
        Robot[] myWallis = simData.getRobots();
        ArrayList<Incendie> incendieVisitees = new ArrayList<Incendie>(); 
        for(Robot walli : myWallis)
        {   
            boolean fireFound = false;
            System.out.println("Find next fire for robot: "+walli);
            Incendie incendieProche = this.simData.getIncendies()[0];
            double timeToNextFire = Double.POSITIVE_INFINITY;
            for(Incendie incendie : simData.getIncendies())
            {
                double timeToFire = (double)walli.timeTo(incendie.getPosition(), this.simData.getCarte());
                if(timeToFire == -1)
                    timeToFire = Double.POSITIVE_INFINITY;
                System.out.println("Time to fire: "+timeToFire+" !< "+timeToNextFire+" bool: "+(timeToFire<timeToNextFire)); 
                if((timeToFire < timeToNextFire) && !incendieVisitees.contains(incendie))
                {
                    fireFound = true;
                    System.out.println("Update next fire: "+incendie+" with time to Fire: "+timeToFire+" - timeToNextFire: "+timeToNextFire);
                    incendieProche = incendie;
                    timeToNextFire = timeToFire;
                }
            }
            if(fireFound)
            {
                System.out.println("Fire closest to "+walli+": "+incendieProche);
                incendieVisitees.add(incendieProche);
                int dateOfFireArrival = walli.createShortestPathTo(date, incendieProche.getPosition(), this.simData.getCarte(), this.sim, this);
                // walli.intervenir(dateOfFireArrival+1, this.sim);
                walli.registerAskForInstructions(dateOfFireArrival+1, this, this.sim);
                endDates.add(dateOfFireArrival+1);
            }
            else
            {
                System.out.println("No Fire found to send robot to");
            }
        }
        return endDates;
    }

    /**
     * Sends robot to the nearest fire
     * @param walli robot to send
     */
    public void sendRobotToFire(Robot walli)
    {
        System.out.println("Find next fire for robot: "+walli);
        try{
            Incendie incendieProche = this.simData.getIncendiesBrules().get(0);
            double timeToNextFire = Double.POSITIVE_INFINITY;
            for(Incendie incendie : simData.getIncendiesBrules())
            {
                double timeToFire = (double)walli.timeTo(incendie.getPosition(), this.simData.getCarte());
                System.out.println("Time to fire: "+timeToFire+" !< "+timeToNextFire+" bool: "+(timeToFire<timeToNextFire)); 
                if((timeToFire < timeToNextFire))
                {
                    System.out.println("Update next fire: "+incendie+" with time to Fire: "+timeToFire+" - timeToNextFire: "+timeToNextFire);
                    incendieProche = incendie;
                    timeToNextFire = timeToFire;
                }
            }
            System.out.println("Fire next to "+walli+": "+incendieProche);
            int dateOfFireArrival = walli.createShortestPathTo(this.sim.getDateSimulation(), incendieProche.getPosition(), this.simData.getCarte(), this.sim, this);
            // walli.intervenir(dateOfFireArrival+1, this.sim);
            walli.registerAskForInstructions(dateOfFireArrival+2, this, this.sim);
        }
        catch(IndexOutOfBoundsException e)
        {
            System.out.println("All fires extinguished! - stop simulation");
            //TODO: Stop algorithm?
        }
    }

    /**
     * Find the nearest water Case and set path for robot to go to water and
     * refill its reservoir
     * @param walli robot to send to water
     */
    public void sendRobotToWater(Robot walli)
    {
        double timeToNextWaterNeighbour = Double.POSITIVE_INFINITY;
        try{
            Case nearestWaterNeigbour = this.simData.getCarte().getWaterCases().get(0);

            for(Case water : this.simData.getCarte().getWaterCases())
            {
                for(Case waterNeighbour : this.simData.getCarte().getNonWaterNeighbours(water))
                {
                    System.out.println("Water neighbour: " +waterNeighbour);
                    double timeToWaterNeighbour = (double)walli.timeTo(waterNeighbour, this.simData.getCarte());
                    System.out.println("Time to water: "+timeToWaterNeighbour); 
                    if((timeToWaterNeighbour < timeToNextWaterNeighbour))
                    {
                        System.out.println("Update next waterNeighbour: "+waterNeighbour+" with time to Water: "+timeToWaterNeighbour+" - timeToNextWater: "+timeToNextWaterNeighbour);
                        nearestWaterNeigbour = waterNeighbour;
                        timeToNextWaterNeighbour = timeToWaterNeighbour;
                    }
                }
            }
            System.out.println("Found nearest Water "+nearestWaterNeigbour+" for robot: "+ walli);
            int dateOfWaterArrival = walli.createShortestPathTo(this.sim.getDateSimulation()+1, nearestWaterNeigbour, this.simData.getCarte(), this.sim, this);
            // walli.registerFillReservoir(dateOfWaterArrival+1, this.sim, this);
            walli.registerAskForInstructions(dateOfWaterArrival+1, this, this.sim);
        }
        catch(Exception e)
        {
            System.err.println("ERROR: "+walli+" Problems with finding Water: "+e);
        }
    }

}
