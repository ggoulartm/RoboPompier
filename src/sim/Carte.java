package sim;

public class Carte {
    private int nbLignes;
    private int nbColonnes;
    private int tailleCases;
    private Case[][] chequerboard;
    
    public Carte(int nbLignes, int nbColonnes, int tailleCases, Case[][] chequerboard){
        this.nbLignes = nbLignes;
        this.nbColonnes = nbColonnes;
        this.tailleCases = tailleCases;
        this.chequerboard = chequerboard;
    }
    public int getNbLignes(){
        return this.nbLignes;
    }
    public int getNbColonnes(){
        return this.nbColonnes;
    }
    public int getTailleCases(){
        return this.tailleCases;
    }
    /**
     * TODO: handle case that case is not available
     * @param lig
     * @param col
     * @return
     */
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
