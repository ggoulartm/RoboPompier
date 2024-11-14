package sim;

import java.util.ArrayList;

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
    public ArrayList<Case> getNeighbours(Case c)
    {
        ArrayList<Case> neighbours = new ArrayList<Case>();
        if(this.voisinExiste(c, Direction.NORD))
            neighbours.add(this.getVoisin(c, Direction.NORD));
        if(this.voisinExiste(c, Direction.EST))
            neighbours.add(this.getVoisin(c, Direction.EST));
        if(this.voisinExiste(c, Direction.SUD))
            neighbours.add(this.getVoisin(c, Direction.SUD));
        if(this.voisinExiste(c, Direction.SUD))
            neighbours.add(this.getVoisin(c, Direction.SUD));
        return neighbours;
    }
    public boolean voisinExiste(Case src, Direction dir){
        int ligne = src.getLigne();
        int colonne = src.getColonne();
        switch(dir)
        {
            case NORD: 
                return (ligne > 0);
            case EST:
                return (colonne < (this.getNbColonnes()-1));
            case SUD:
                return (ligne < (this.getNbLignes()-1));
            case OUEST:
                return (colonne > 0);
            default: 
                return false;
        }
    }
    public Case getVoisin(Case src, Direction dir){
        switch(dir)
        {
            case NORD:
                return this.chequerboard[src.getLigne()-1][src.getColonne()];
            case EST:
                return this.chequerboard[src.getLigne()][src.getColonne()+1];
            case SUD:
                return this.chequerboard[src.getLigne()+1][src.getColonne()];
            case OUEST:
                return this.chequerboard[src.getLigne()][src.getColonne()-1];
            default:
                return null;
        }
    }
    public int getTaille()
    {
        return this.tailleCases;
    }

    @Override
    public String toString()
    {
        return "Carte avec taille: "+this.tailleCases+" "+this.chequerboard.length+"x"+this.chequerboard[0].length;
    }
}
