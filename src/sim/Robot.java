package sim;

public abstract class Robot {
    private Case position;
    private int vitesse;

    public Robot(Case position, int vitesse)
    {
        this.position = position;
        this.vitesse = vitesse;
    }

    public Case getPosition() { 
        System.out.println("Mon position est n'importe ou "); 
        return this.position;
        };

    public void setPosition(Case c) { System.out.println("Mon nouveau position est quoi?"); }
    
    public double getVitesse(NatureTerrain nature) 
    { 
        System.out.println("Je roule à 100km/h ");
        return 100; 
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
