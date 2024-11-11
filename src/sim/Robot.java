package sim;

public abstract class Robot {
    private Case position;
    private int vitesse;

    public Robot(Case position, int vitesse)
    {
        this.position = position;
        this.vitesse = vitesse;
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
    }
    
    public void remplirReservoir() 
    { 
        System.out.println("Remplir Reservoir"); 
    }

    @Override
    public String toString()
    {
        return "Robot at: "+this.position.toString()+" with speed: "+this.vitesse;
    }
}
