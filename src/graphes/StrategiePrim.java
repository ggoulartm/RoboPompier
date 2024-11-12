// package graphes;

// import sim.Carte;
// import sim.Case;
// import sim.NatureTerrain;

// import java.util.ArrayList;

// public class StrategiePrim
// {

//     public static ArrayList<Case> findShortestPath(Carte carte, Case start, Case target, NatureTerrain[] forbiddenTerrains)
//     {
//         System.out.println(carte);
//         System.out.println(start);
//         System.out.println(target);
//         StrategiePrim strat = new StrategiePrim(carte, start, forbiddenTerrains);
//         System.out.println("Strategy successfully initialized");
//         return strat.shortestPath(start, target);
//     }

//     ArrayList<TreeCase> tree = new ArrayList<TreeCase>();
//     Carte carte;
//     NatureTerrain[] forbiddenTerrains;

//     public StrategiePrim(Carte carte, Case start, NatureTerrain[] forbiddenTerrains)
//     {
//         this.carte = carte;
//         this.forbiddenTerrains = forbiddenTerrains;

//         this.tree.add(new TreeCase(this.carte, start, 0));
//     }

//     private ArrayList<Case> shortestPath(Case s, Case t)
//     {
//         // As long last as target is not element of tree continue searching
//         TreeCase target = new TreeCase(carte, t);
//         TreeCase start = new TreeCase(carte, s, 0);
//         System.out.println("target and start successfully initialized");
//         int iterations = 0;
//         while(!(this.tree.contains(target))) //&& iterations < this.carte.getNbLignes()*(this.carte.getNbColonnes()-1)
//         {
//             iterations++;
//             // System.out.println("Start of while - size of tree: "+this.tree.size());
//             ArrayList<TreeCase> externNeighbours = new ArrayList<TreeCase>();
//             // Get all extern neigbours of current tree
//             for(TreeCase currentCase : this.tree)
//             {
//                 System.out.println("Start at case in Tree: "+currentCase.getPosition());
//                 // Get all neighbours for one Case
//                 ArrayList<TreeCase> neighbours = currentCase.getNeighbours(forbiddenTerrains);
//                 // Add neighbour to extern neighbours if its not yet in the tree
//                 for(TreeCase neighbour : neighbours)
//                 {
//                     System.out.println("Neighbour: "+neighbour.getPosition());
//                     if (tree.contains(neighbour)) {
//                         System.out.println("Neighbour already in searchtree");
//                     } else {
//                         System.out.println("Neighbour not in searchtree");
//                         // check whether extern neighbour can already be reached from another element in tree with less cost
//                         if(externNeighbours.contains(neighbour))
//                         {
//                             if(currentCase.getLengthFromHome()+currentCase.travelCost() < neighbour.getLengthFromHome())
//                             {
//                                 neighbour.setPredecesseur(currentCase, currentCase.getLengthFromHome()+currentCase.travelCost());
//                             }
//                         }
//                         else 
//                         {
//                             // neighbour not yet in extern neighbours
//                             neighbour.setPredecesseur(currentCase, currentCase.getLengthFromHome()+currentCase.travelCost());
//                             externNeighbours.add(neighbour);
//                             System.out.println("Added neighbour "+neighbour.getPosition()+" to extern neighbours");
//                         }
//                     }
//                 }
//                 System.out.println("Done with case in Tree: "+currentCase.getPosition());
//             }
//             // Add the extern neighbour with cheapest pathlength to tree
//             TreeCase cheapestNeighbour = externNeighbours.get(0);
//             for(TreeCase externNeighbour : externNeighbours)
//             {
//                 if(externNeighbour.getLocalShortestPath() < cheapestNeighbour.getLocalShortestPath());
//                 {
//                     cheapestNeighbour = externNeighbour;
//                 }
//             }
//             tree.add(cheapestNeighbour);
//             System.out.println("Cheapest neighbour predecesseur: "+cheapestNeighbour.getPredecesseur());
//             System.out.println("Added "+tree.get(tree.size()-1).getPosition()+" to tree");
//         }
//         if(this.tree.get(this.tree.size()-1).equals(target))
//         {
//             target = this.tree.get(this.tree.size()-1);
//         }

//         System.out.println("Added target to tree or ran out of iterations");

//         return constructPathFromTarget(start, target); 
//     }

//     private ArrayList<Case> constructPathFromTarget(TreeCase start, TreeCase target)
//     {
//         System.out.println("Construct target from path");
//         System.out.println("Predecesseur from target: "+target.getPredecesseur());
//         ArrayList<TreeCase> path = new ArrayList<TreeCase>();
//         path.add(target);
//         while(!path.contains(start))
//         {
//             System.out.println("Predecesseur of subpath: "+path.get(path.size()-1).getPredecesseur());
//             path.add(path.get(path.size()-1).getPredecesseur());
//         }

//         ArrayList<Case> pathCase = new ArrayList<Case>();
//         for (int j = path.size() - 1; j >= 0; j--) 
//         {
//             pathCase.add(path.get(j).getPosition());
//         }
//         return pathCase;
//     }
// }