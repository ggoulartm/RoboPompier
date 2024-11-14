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

    public Robot(Case position, int vitesse, int waterCapacityMax, int reserveWaterAmount, RobotType type)
    {
        this.position = position;
        this.vitesse = vitesse;
        this.volumeReservoir = reserveWaterAmount;
        this.volumeReservoirMax = waterCapacityMax;
        this.type = type;
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

    public RobotType getType(){
        return this.type;
    }
    abstract public void createShortestPathTo(Case end, Carte carte, Simulateur sim);

    public Case getPosition() 
    { 
        return this.position;
    };

    public void setPosition(Case c) 
    { 
        this.position = c; 
    }
    
    public double getVitesse()
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

    public int setDeversementTemps(int temps) {
        return this.Deversement.temps = temps;
    }

}