package events;

import sim.Robot;
import sim.Direction;
import sim.Carte;

/**
 * Deplacer is an event that moves a robot in a specific direction
 */
public class Deplacer extends Evenement{
    private Robot robot;
    private Direction dir;
    private Carte carte;

    public Deplacer(int date, Robot robot, Direction dir, Carte carte) {
        super(date);
        this.robot=robot;
        this.dir = dir;
        this.carte = carte;
    }

    @Override
    public void execute()
    {
        System.out.println("Deplacer vers+ "+this.dir);
        int ligne = this.robot.getPosition().getLigne();
        int colonne = this.robot.getPosition().getColonne();
        switch(this.dir)
        {
            case NORD:
                if(ligne != 0)
                    this.robot.setPosition(this.carte.getCase(ligne-1, colonne));
                else
                    System.err.println("ERROR: R2D2 cries because he fell of the map!");
                break;
            case EST:
                if(colonne != this.carte.getNbColonnes()-1)
                    this.robot.setPosition(this.carte.getCase(ligne, colonne+1));
                else
                    System.err.println("ERROR: R2D2 cries because he fell of the map!");
                break;
            case SUD:
                if(ligne!=this.carte.getNbLignes()-1)
                    this.robot.setPosition(this.carte.getCase(ligne+1, colonne));
                else
                    System.err.println("ERROR: R2D2 cries because he fell of the map!");
                break;
            case OUEST:
                if(colonne != 0)
                    this.robot.setPosition(this.carte.getCase(ligne, colonne-1));
                else
                    System.err.println("ERROR: R2D2 cries because he fell of the map!");
                break;
            default:
                System.out.println("No direction specified");
        }
    }

    @Override 
    public String toString()
    {
        return "Deplacement de robot: "+this.robot+" vers: "+this.dir+" on day: "+this.getDate();
    }

}
