package sim;
import java.util.ArrayList;

import events.AskInstructions;
import events.Intervention;
import events.Remplir;
import gui.GUISimulator;
import strategies.SimpleChefPompier;

// This is an abstract class, so it cannot be instantiated
// It is used as a base class for the Drone and Pattes classes
// The Drone and Pattes classes must implement the getType method
// The Drone and Pattes classes must implement the constructor
// The Drone and Pattes classes might implement the toString method
public abstract class Robot {
    protected Case position;
    private Case InitCase;
    private int initVolume;
    protected int volumeReservoir; //Litres
    protected int volumeReservoirMax; //Litres
    protected int vitesse; //km/h
    protected RobotType type; //Type de robot: Drone, Pattes(Legs), Roues(Wheels), Chenilles(Caterpillar)
    protected int tempsRemplissage; //minutes
    private boolean moving;
    private Case targetCase;

    /**
     * 
     * @param position Case of starting position
     * @param vitesse velocity in km/h
     * @param waterCapacityMax 
     * @param reserveWaterAmount
     * @param type type of Robot
     */
    public Robot(Case position, int vitesse, int waterCapacityMax, int reserveWaterAmount, RobotType type)
    {
        this.position = position;
        this.InitCase = new Case(position.getLigne(), position.getColonne(), position.getNature());
        this.vitesse = vitesse;
        this.volumeReservoir = reserveWaterAmount;
        this.initVolume = volumeReservoir;
        this.volumeReservoirMax = waterCapacityMax;
        this.type = type;
        this.moving = false;
    }

    /**
     * Needs to be implemented by every robot, make sure to only create new path when robot stopped moving indicated
     * by the bool notMoving and to set notMoving to false when a path was created. This function is responsible 
     * that the robots only move as allowed 
     * @param end target Case
     * @param carte map on which the robot operates
     * @param sim reference to the simulateur that receives the deplacer events
     * @return returns duration it takes the robot to move from current position to target
     */
    abstract public int createShortestPathTo(int date, Case end, Carte carte, Simulateur sim, SimpleChefPompier chef);
    /**
     * Calculates time robot needs to go to Case c
     * @param c
     * @param carte
     * @return time in seconds
     */
    abstract public int timeTo(Case c, Carte carte);

    /**
     * Register an intervention (fire extinguish) event to the simulator
     * @param date when the event is supposed to happen
     * @param sim simulator that keeps track of events
     */
    public void intervenir(int date, Simulateur sim)
    {
        Intervention intervention = new Intervention(date, sim, this);
        sim.addEvent(intervention);
    }

    /**
     * Register an AskForInstructions event
     * @param date date when the request shall be posed
     * @param chef ChefPompier that coordinates firefighting
     * @param sim instance of the simulator that keeps track of events
     */
    public void registerAskForInstructions(int date, SimpleChefPompier chef, Simulateur sim)
    {
        System.out.println("Robot is moving: "+this.moving);
        AskInstructions question = new AskInstructions(date, this, chef);
        sim.addEvent(question);
    }

    public void emptyWater()
    {
        this.volumeReservoir = 0;
    }

    public void emptyWater(int amount)
    {
        this.volumeReservoir = this.volumeReservoir-amount;
    }

    /**
     * Checks whether robot is next to Water
     * @param carte
     * @return true if water in one of the neighbours of robot
     */
    public boolean nextToWater(Carte carte)
    {
        // System.out.println("    Check for water at "+pos);
        if(this.position.getNature() == NatureTerrain.EAU)
            return true;

        ArrayList<Case> neighbours = carte.getNeighbours(this.position);
        System.out.println("    Found: "+neighbours.size()+" neighbours");
        for(Case c : neighbours)
        {
            if (c.getNature() == NatureTerrain.EAU)
                return true;
        }
        return false;
    }

    /**
     * Registers a Remplir event with following askForInstructions event to the simulator 
     * @param date Date of start to remplir
     * @param sim Simulator to register the event to
     */
    public void registerFillReservoir(int date, Simulateur sim, SimpleChefPompier chef)
    {
        int dateDeRemplissageComplet = date+this.getTempsRemplissage();
        int dateDeDemandeInstruction = dateDeRemplissageComplet+1;
        Remplir remplir = new Remplir(date+this.getTempsRemplissage(), sim, this);
        sim.addEvent(remplir);
        this.registerAskForInstructions(dateDeDemandeInstruction, chef, sim);
    }
    /**
     * Only to be called by the Simulator
     */
    public void fillReservoir()
    {
        this.volumeReservoir = this.volumeReservoirMax;
    }
    /**
     * return the Case which the Robot is moving towards
     */
    public Case getTargetCase()
    {
        return this.targetCase;
    }   

    public void setTargetCase(Case c)
    {
        this.targetCase = c;
    }

    public boolean isMoving()
    {
        return this.moving;
    }

    public void setMoving(boolean b)
    {
        this.moving = b;
    }

    /**
     * Returns Direction of Case b relative to Case a, Returns NO_DIRECTION if Cases are not adjacent
     * @param a Case of home
     * @param b Case relative to home
     */
    public Direction getDirectionFromCases(Case a, Case b)
    {
        int a_ligne = a.getLigne();
        int a_colonne = a.getColonne();
        int b_ligne = b.getLigne();
        int b_colonne = b.getColonne();

        if(a_ligne == b_ligne)
        {
            if(b_colonne == a_colonne+1) return Direction.EST;
            else if(b_colonne == a_colonne-1) return Direction.OUEST;
            else return Direction.NO_DIRECTION;
        }
        else if(a_colonne == b_colonne)
        {
            if(b_ligne == a_ligne+1) return Direction.SUD;
            else if(b_ligne == a_ligne-1) return Direction.NORD;
            else return Direction.NO_DIRECTION;
        }
        else
        {
            return Direction.NO_DIRECTION;
        }

    }

    /**
     * 
     * @return int of size of water reservoir
     */
    public int getMaxWaterContent()
    {
        return this.volumeReservoirMax;
    }

    public RobotType getType(){
        return this.type;
    }

    public Case getPosition() 
    { 
        return this.position;
    };

    public void setPosition(Case c) 
    { 
        this.position = c;
        if(this.position.equals(this.targetCase))
        {
            this.setMoving(false);
        }
    }
    
    public double getVitesse()
    { 
        return this.vitesse; 
    }
    
    public int getWaterContent()
    {
        return this.volumeReservoir;
    }

    public void deverserEau(int vol) 
    { 
        if (this.volumeReservoir > 0 && vol <= this.volumeReservoir) {
            System.out.println("Je deverse d'eau"); 
            this.volumeReservoir -= vol;
            System.out.println(this.type + " robot pours " + vol + "L of water.");
         }
    }
    
    public void remplirReservoir() 
    { 
        this.volumeReservoir = this.volumeReservoirMax;
        System.out.println(this.type + " robot a rempli son reservoir.");
    }

    @Override
    public String toString()
    {
        return "Robot at: "+this.position.toString()+" with speed: "+this.vitesse;
    }

    public abstract void draw(GUISimulator gui, int tailleCase);

    public boolean onBurningFire(DonneesSimulation simData)
    {
        for(Incendie inc : simData.getIncendies())
        {
            if(inc.getPosition().equals(this.getPosition()) && inc.getIntensite() != 0)
                return true;
        }
        return false;
    }

    /**
     * 
     * @return time in seconds needed to refill reservoir of robot
     */
    public int getTempsRemplissage() {
        switch(this.type)
        {
            case CATERPILLAR:
                return 5*60;
            case DRONE:
                return 30*60;
            case PATTES:
                return -1;
            case WHEELS:
                return 10*60;
        }
        return this.tempsRemplissage;
    }
    
    /**
     * checks whether robot has no water left
     * @return true if reservoir is empty, false if not
     */
    public boolean isEmpty(){
        return this.volumeReservoir == 0;
    }
    /**
     * Reset robot to initial state
     */
    public void Restore(){
        this.position = new Case(this.InitCase.getLigne(), this.InitCase.getColonne(), this.InitCase.getNature());
        this.volumeReservoir = initVolume;
        this.setMoving(false);
    }

}