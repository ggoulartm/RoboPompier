public interface Robot {
    public Case getPosition();
    public void setPosition(Case c);
    public double getVitesse(NatureTerrain nature);
    public void deverserEau(int vol);
    public void remplirReservoir();
}
