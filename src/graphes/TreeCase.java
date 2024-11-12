package graphes;

import java.util.ArrayList;

import sim.NatureTerrain;
import sim.Case;
import sim.Carte;

public class TreeCase
{
    private Carte carte;
    private Case position;
    private TreeCase predecesseur;
    private double lengthFromHome;
    private double localShortestPath;
    
    public TreeCase(Carte carte, Case position)
    {
        this.carte = carte;
        this.position = position;
        this.lengthFromHome = Double.POSITIVE_INFINITY;
    }

    public TreeCase(Carte carte, Case position, int lengthFromHome)
    {
        this.carte = carte;
        this.position = position;
        this.lengthFromHome = lengthFromHome;
    }

    public ArrayList<TreeCase> getNeighbours(NatureTerrain[] forbiddenTerrains)
    {
        ArrayList<TreeCase> neighbours = new ArrayList<TreeCase>();
        if(forbiddenTerrains.length == 0)
        {
            // Go around the Case and try to add neighbour if it exists
            try{
                neighbours.add(new TreeCase(carte, carte.getCase(this.position.getLigne(), this.position.getColonne()+1)));
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                System.out.println("No east neighbour");
            }
            try{
                neighbours.add(new TreeCase(carte, carte.getCase(this.position.getLigne()+1, this.position.getColonne())));
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                System.out.println("No south neighbour");
            }
            try{
                neighbours.add(new TreeCase(carte, carte.getCase(this.position.getLigne(), this.position.getColonne()-1)));
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                System.out.println("No west neighbour");
            }
            try{
                neighbours.add(new TreeCase(carte, carte.getCase(this.position.getLigne()-1, this.position.getColonne())));       
            }
            catch(ArrayIndexOutOfBoundsException e)
            {
                System.out.println("No north neighbour");
            }
        }
        else
        {
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("----------------------------------------------------------------------------");
            System.out.println("----------------------------------------------------------------------------");
        }
        return neighbours;
    }

    public void setPredecesseur(TreeCase pred, double travelCost)
    {
        this.predecesseur = pred;
        this.lengthFromHome = travelCost;
    }

    public TreeCase getPredecesseur()
    {
        return this.predecesseur;
    }

    public Case getPosition()
    {
        return this.position;
    }

    public double getLengthFromHome()
    {
        return this.lengthFromHome;
    }

    public double getLocalShortestPath()
    {
        return this.localShortestPath;
    }

    public double travelCost()
    {
        System.out.println("----------------Implement me-------------------------");
        NatureTerrain nature = this.position.getNature();
        if(nature == NatureTerrain.EAU) 
        {
            return 50;
        }
        return 10;
    }

    @Override
    public boolean equals(Object o) {
        if( ! (o instanceof TreeCase)) {
                return false ;
        }
        // ou, plus restrictif :
        // if(o.getClass() != this.getClass()) {
        //  return false ;
        //}

        // cast explicite nécessaire, car Downcast :
        TreeCase other = (TreeCase) o;
        return (this.position.equals(other.position));
    }

    @Override 
    public String toString()
    {
        return "TreeCase with position: "+this.position;
    }
}