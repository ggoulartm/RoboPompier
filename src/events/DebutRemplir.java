package events;

import sim.Robot;

public class DebutRemplir extends Evenement {
    Robot robot;

    public DebutRemplir(long date, Robot robot) {
        super(date);
        this.robot = robot;
    }

    @Override
    public void execute() {
        long dateFinRemplissage = dateFinEvenement();
        FinRemplir remplissageFin = new FinRemplir(dateFinRemplissage, robot);
    }

    public long dateFinEvenement() {
        return this.getDate() + robot.getTempsRemplissage();
    }
}