public class Case{
    
    private int ligne, colonne;
    private NatureTerrain nature;
    Case(int ligne, int colonne){
        this.ligne = ligne;
        this.colonne = colonne;
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
}

enum Direction{
    NORD,SUD,EST,OUEST;
}

enum NatureTerrain{
    EAU,FORET,ROCHE,TERRAIN_LIBRE,HABITAT;
}