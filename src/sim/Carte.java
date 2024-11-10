package sim;

public class Carte {
    
    private int tailleCases;
    private Case[][] chequerboard;
    
    public Carte(int nbLignes, int nbColonnes, Case[][] chequerboard){
        this.tailleCases = nbColonnes*nbLignes;
        this.chequerboard = chequerboard;
    }
    public int getNbLignes(){
        return 0;
    }
    public int getNbColonnes(){
        return 0;
    }
    public int getTailleCases(){
        return this.tailleCases;
    }
    public Case getCase(int lig, int col){
        return chequerboard[lig][col];
    }
    public boolean voisinExiste(Case src, Direction dir){
        return false;
    }
    public Case getVoisin(Case src, Direction dir){
        return null;
    }

    @Override
    public String toString()
    {
        return "Carte avec taille: "+this.tailleCases+" "+this.chequerboard.length+"x"+this.chequerboard[0].length;
    }
}
