public abstract class Robot {
    private Case position;

    public Robot(Case position)
    {
        this.position = position;
    }

    public Case getPosition() { System.out.println("Mon position est n'importe ou ") };
    public void setPosition(Case c) { System.out.println("Mon nouveau position est quoi?"); }
    public double getVitesse(NatureTerrain nature) { Syste.out.println("Je rpule à 100km/h "); }
    public void deverserEau(int vol) { System.out.println("Je deverse d'eau"); }
    public void remplirReservoir() { System.out.println("Remplir Reservoir"); }
}
