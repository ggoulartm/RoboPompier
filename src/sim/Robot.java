package sim;
import events.AskInstructions;
import events.Evenement;
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
    protected int volumeReservoir; //Litres
    protected int volumeReservoirMax; //Litres
    protected int vitesse; //km/h
    protected RobotType type; //Type de robot: Drone, Pattes(Legs), Roues(Wheels), Chenilles(Caterpillar)
    protected InterventionUnitaire Deversement; //Litres/seconde
    protected int tempsRemplissage; //minutes
    private boolean moving;
    private Case targetCase;

    public Robot(Case position, int vitesse, int waterCapacityMax, int reserveWaterAmount, RobotType type)
    {
        this.position = position;
        this.vitesse = vitesse;
        this.volumeReservoir = reserveWaterAmount;
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
    abstract public int timeTo(Case c, Carte carte);

    public void intervenir(int date, Simulateur sim)
    {
        Intervention intervention = new Intervention(date, this.volumeReservoir, sim, this);
        sim.addEvent(intervention);
    }

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
    /**
     * Registers a Remplir event to the simulator
     * @param date Date of the attempt to remplir
     * @param sim Simulator to register the event to
     */
    public void registerFillReservoir(int date, Simulateur sim)
    {
        Remplir remplir = new Remplir(date, sim, this);
        sim.addEvent(remplir);
        this.volumeReservoir = this.volumeReservoirMax;
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
        else
        {
            System.err.println("Relax - Robot still moving, wait until its target is reached and set new path");
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

    public class InterventionUnitaire {
        public int volume;
        public int temps;
        public InterventionUnitaire(int volume, int temps) {
            this.volume = volume;
            this.temps = temps;
        }
    }

    public void InterventionUnitaire() {
        System.out.println("Intervention unitaire: " + this.Deversement.volume + "L in " + this.Deversement.temps + "s.");
        deverserEau(this.Deversement.volume); //Litres/seconde
        //Attendre this.Deversement.temps secondes
    }

    public int getTempsRemplissage() {
        return this.tempsRemplissage;
    }
    
    public int getDeversementVolume() {
        return this.Deversement.volume;
    }

    public int getDeversementTemps() {
        return this.Deversement.temps;
    }

    public int setDeversementTemps(int temps) {
        return this.Deversement.temps = temps;
    }

}