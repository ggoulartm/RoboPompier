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

    private boolean isAllowed(NatureTerrain nature, NatureTerrain[] forbiddenTerrains)
    {
        if(forbiddenTerrains.length == 0) return true;
        for(int i=0;i<forbiddenTerrains.length;i++)
        {
            if(nature == forbiddenTerrains[i]) return false;
        }
        return true;
    }

    public ArrayList<TreeCase> getNeighbours(NatureTerrain[] forbiddenTerrains)
    {
        ArrayList<TreeCase> neighbours = new ArrayList<TreeCase>();
        // Go around the Case and try to add neighbour if it exists
        try{
            Case toAdd =  carte.getCase(this.position.getLigne(), this.position.getColonne()+1);
            if(this.isAllowed(toAdd.getNature(), forbiddenTerrains))
                neighbours.add(new TreeCase(carte, toAdd));
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            // System.out.println("No east neighbour");
        }
        try{
            Case toAdd =  carte.getCase(this.position.getLigne()+1, this.position.getColonne());
            if(this.isAllowed(toAdd.getNature(), forbiddenTerrains))
                neighbours.add(new TreeCase(carte, toAdd));
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            // System.out.println("No south neighbour");
        }
        try{
            Case toAdd =  carte.getCase(this.position.getLigne(), this.position.getColonne()-1);
            if(this.isAllowed(toAdd.getNature(), forbiddenTerrains))
                neighbours.add(new TreeCase(carte, toAdd));
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            // System.out.println("No west neighbour");
        }
        try{
            Case toAdd =  carte.getCase(this.position.getLigne()-1, this.position.getColonne());
            if(this.isAllowed(toAdd.getNature(), forbiddenTerrains))
                neighbours.add(new TreeCase(carte, toAdd));       
        }
        catch(ArrayIndexOutOfBoundsException e)
        {
            // System.out.println("No north neighbour");
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

    public double travelCost(double[] natureCosts)
    {
        switch(this.position.getNature())
        {
            case EAU:
                return natureCosts[0];
            case FORET:
                return natureCosts[1];
            case ROCHE:
                return natureCosts[2];
            case TERRAIN_LIBRE:
                return natureCosts[3];
            case HABITAT:
                return natureCosts[4];
            
        }
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