public class Carte {
    int tailleCases;
    Carte(int nbLignes, int nbColonnes){
        this.tailleCases = nbColonnes*nbLignes;
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
        return null;
    }
    public boolean voisinExiste(Case src, Direction dir){
        return false;
    }
    public Case getVoisin(Case src, Direction dir){
        return null;
    }
}
