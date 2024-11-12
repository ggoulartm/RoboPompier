package events;

import sim.Carte;
import sim.Direction;
import sim.Robot;
import sim.Simulateur;

public class DebutDeplacer extends Evenement{

    private Robot robot;
    private Direction dir;
    private Carte carte;

    public DebutDeplacer(long date, Simulateur sim, Robot robot, Direction dir, Carte carte) {
        super(date, sim);
        this.robot = robot;
        this.dir = dir;
        this.carte = carte;
    }

    @Override
    public void execute() {
        long dateFinDeplacement = dateFinEvenement();
        FinDeplacer finDeplacement = new FinDeplacer(dateFinDeplacement, getSim(), robot, dir, carte);
        this.getSim().addEvent(finDeplacement);
    }

    public long dateFinEvenement() {
        return this.getDate(); //+ robot.tempsVoyage(dir);
    }
}
