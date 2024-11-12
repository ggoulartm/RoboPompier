package sim;
import gui.GUISimulator;

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

    public Robot(Case position, int vitesse, int waterCapacityMax, int reserveWaterAmount)
    {
        this.position = position;
        this.vitesse = vitesse;
        this.volumeReservoir = reserveWaterAmount;
        this.volumeReservoirMax = waterCapacityMax;
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
    }
    
    public double getVitesse(NatureTerrain nature) 
    { 
        return this.vitesse; 
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
}