package graphes;

import sim.Carte;
import sim.Case;
import sim.NatureTerrain;

import java.util.ArrayList;

public class StrategieDijkstra
{

    /**
     * 
     * @param carte
     * @param start
     * @param target
     * @param forbiddenTerrains
     * @param natureCosts Tableau du cout pour une nature de case de la forme {EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT}
     * @return
     */
    public static ArrayList<Case> findShortestPath(Carte carte, Case start, Case target, NatureTerrain[] forbiddenTerrains, double[] natureCosts)
    {
        System.out.println(carte);
        System.out.println(start);
        System.out.println(target);
        StrategieDijkstra strat = new StrategieDijkstra(carte, start, forbiddenTerrains, natureCosts);
        return strat.shortestPath(start, target);
    }

    ArrayList<TreeCase> tree = new ArrayList<TreeCase>();
    Carte carte;
    NatureTerrain[] forbiddenTerrains;
    // EAU, FORET, ROCHE, TERRAIN_LIBRE, HABITAT 
    // POSITIVE_INFINITY when not allowed 
    double[] natureCosts;

    public StrategieDijkstra(Carte carte, Case start, NatureTerrain[] forbiddenTerrains, double[] natureCosts)
    {
        this.carte = carte;
        this.forbiddenTerrains = forbiddenTerrains;
        this.natureCosts = natureCosts;
    }

    private ArrayList<Case> shortestPath(Case s, Case t)
    {
        // As long last as target is not element of tree continue searching
        TreeCase target = new TreeCase(carte, t);
        TreeCase start = new TreeCase(carte, s, 0);
        // System.out.println("target and start successfully initialized");
        int iterations = 0;
        // List of all sommets that were called in a scan
        ArrayList<TreeCase> updatedNeighbours = new ArrayList<TreeCase>();
        updatedNeighbours.add(start);
        while(iterations < this.carte.getNbLignes()*this.carte.getNbColonnes()) //&& iterations < this.carte.getNbLignes()*(this.carte.getNbColonnes()-1)
        {
            iterations++;
            // System.out.println("Start of while - size of tree: "+this.tree.size());
            
            // Find element with lowest length from home that is not yet added to tree
            TreeCase min_v = target;  
            double min_cout = Double.POSITIVE_INFINITY;
            for(TreeCase currentCase : updatedNeighbours)
            {
                if(currentCase.getLengthFromHome() < min_cout && !this.tree.contains(currentCase))
                {
                    min_v = currentCase;
                    min_cout = min_v.getLengthFromHome();
                }
            }

            // System.out.println("min_v at: "+min_v.getPosition());
            // SCAN
            ArrayList<TreeCase> neighbours = min_v.getNeighbours(forbiddenTerrains);
            for(TreeCase neighbour : neighbours)
            {
                // System.out.println("Call SCAN for: "+neighbour.getPosition());
                if (tree.contains(neighbour)) {
                    // System.out.println("Neighbour already in searchtree, jump to next neighbour");
                } else {
                    // System.out.println("Neighbour not in searchtree");
                    if(updatedNeighbours.contains(neighbour))
                    {
                        if(min_v.getLengthFromHome()+min_v.travelCost(this.natureCosts) < neighbour.getLengthFromHome())
                        {
                            neighbour.setPredecesseur(min_v, min_v.getLengthFromHome()+min_v.travelCost(this.natureCosts));
                            // System.out.println("Changed predecesseur of neighbour "+neighbour.getPosition()+" with Predecesseur"+neighbour.getPredecesseur());
                        }
                    }
                    else 
                    {
                        // neighbour not yet in extern neighbours
                        neighbour.setPredecesseur(min_v, min_v.getLengthFromHome()+min_v.travelCost(this.natureCosts));
                        updatedNeighbours.add(neighbour);
                        // System.out.println("Added neighbour "+neighbour.getPosition()+"with Predecesseur"+neighbour.getPredecesseur()+" to updatedNeighbours");
                    }

                }
            }
            // Add v to tree
            this.tree.add(min_v);
        }
        if(this.tree.contains(target))
        {
            target = this.tree.get(this.tree.indexOf(target));
            // System.out.println("Tree contains target");
        }

        // System.out.println("Added target to tree or ran out of iterations");

        return constructPathFromTarget(start, target); 
    }

    private ArrayList<Case> constructPathFromTarget(TreeCase start, TreeCase target)
    {
        System.out.println("Construct path from target");
        // System.out.println("Predecesseur from target: "+target.getPredecesseur());
        ArrayList<TreeCase> path = new ArrayList<TreeCase>();
        path.add(target);
        while(!path.contains(start))
        {
            // System.out.println("Predecesseur of subpath: "+path.get(path.size()-1).getPredecesseur());
            path.add(path.get(path.size()-1).getPredecesseur());
        }

        ArrayList<Case> pathCase = new ArrayList<Case>();
        for (int j = path.size() - 1; j >= 0; j--) 
        {
            pathCase.add(path.get(j).getPosition());
        }
        return pathCase;
    }
}