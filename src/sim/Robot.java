package sim;

// This is an abstract class, so it cannot be instantiated
// It is used as a base class for the Drone and Pattes classes
// The Drone and Pattes classes must implement the getType method
// The Drone and Pattes classes must implement the constructor
// The Drone and Pattes classes might implement the toString method
public abstract class Robot {
    protected Case position;
    protected int volumeReservoir;
    protected int volumeReservoirMax;
    protected int vitesse;

    public Robot(Case position, int vitesse, int waterCapacityMax, int reserveWaterAmount)
    {
        this.position = position;
        this.vitesse = vitesse;
        this.volumeReservoir = reserveWaterAmount;
        this.volumeReservoirMax = waterCapacityMax;
    }

    abstract public String getType();

    public Case getPosition() 
    { 
        return this.position;
    };

    public void setPosition(Case c) 
    { 
        this.position = c; 
    }
    
    public double getVitesse(NatureTerrain nature) 
    { 
        return this.vitesse; 
    }
    
    public void deverserEau(int vol) 
    { 
        System.out.println("Je deverse d'eau"); 
        this.volumeReservoir -= vol;
    }
    
    public void remplirReservoir() 
    { 
        System.out.println("Remplir Reservoir"); 
        this.volumeReservoir = this.volumeReservoirMax;
    }

    @Override
    public String toString()
    {
        return "Robot at: "+this.position.toString()+" with speed: "+this.vitesse;
    }
}
