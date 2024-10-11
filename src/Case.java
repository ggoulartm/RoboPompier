public class Case extends Carte implements Robot, Incendie{
    int ligne, colonne;
    NatureTerrain nature;
    Case(){
        super(0,0);
        this.colonne = 0;
        this.ligne = 0;
    }
    public int getLigne(){
        return 0;
    }
    public int getColonne(){
        return 0;
    }
    public NatureTerrain getNature(){
        return NatureTerrain.EAU;
    }

    //implements Robot interface
    public Case getPosition(){
        return null;
    }
    public void setPosition(Case c){
        return;
    }
    public double getVitesse(NatureTerrain nature){
        return 0;
    }
    public void deverserEau(int vol){
        return;
    }
    public void remplirReservoir(){
        return;
    }
}

enum Direction{
    NORD,SUD,EST,OUEST;
}

enum NatureTerrain{
    EAU,FORET,ROCHE,TERRAIN_LIBRE,HABITAT;
}