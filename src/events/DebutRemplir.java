package events;

import sim.Robot;
import sim.Simulateur;

public class DebutRemplir extends Evenement {
    Robot robot;

    public DebutRemplir(long date, Simulateur sim, Robot robot) {
        super(date, sim);
        this.robot = robot;
    }

    @Override
    public void execute() {
        long dateFinRemplissage = dateFinEvenement();
        FinRemplir remplissageFin = new FinRemplir(dateFinRemplissage, this.getSim(), robot);
        this.getSim().addEvent(remplissageFin);
    }

    public long dateFinEvenement() {
        return this.getDate() + robot.getTempsRemplissage();
    }
}