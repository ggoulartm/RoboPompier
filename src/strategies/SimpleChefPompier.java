package strategies;

import java.util.ArrayList;

import sim.DonneesSimulation;
import sim.Incendie;
import sim.Robot;
import sim.Simulateur;

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
            System.out.println("Find next fire for robot: "+walli);
            Incendie incendieProche = this.simData.getIncendies()[0];
            double timeToNextFire = Double.POSITIVE_INFINITY;
            for(Incendie incendie : simData.getIncendies())
            {
                double timeToFire = (double)walli.timeTo(incendie.getPosition(), this.simData.getCarte());
                System.out.println("Time to fire: "+timeToFire+" !< "+timeToNextFire+" bool: "+(timeToFire<timeToNextFire)); 
                if((timeToFire < timeToNextFire) && !incendieVisitees.contains(incendie))
                {
                    System.out.println("Update next fire: "+incendie+" with time to Fire: "+timeToFire+" - timeToNextFire: "+timeToNextFire);
                    incendieProche = incendie;
                    timeToNextFire = timeToFire;
                }
            }
            System.out.println("Fire next to "+walli+": "+incendieProche);
            incendieVisitees.add(incendieProche);
            int dateOfFireArrival = walli.createShortestPathTo(date, incendieProche.getPosition(), this.simData.getCarte(), this.sim);
            walli.intervenir(dateOfFireArrival+1, this.sim);
            endDates.add(dateOfFireArrival+1);
        }
        return endDates;
    }

}
