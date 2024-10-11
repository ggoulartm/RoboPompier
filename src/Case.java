public class Case{
    int ligne, colonne;
    NatureTerrain nature;
    Case(){
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