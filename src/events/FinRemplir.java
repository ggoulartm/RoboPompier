package events;

import sim.Robot;
import sim.Simulateur;

public class FinRemplir extends Evenement{
    private Robot robot;

    public FinRemplir(long date, Simulateur sim, Robot robot) {
        super(date, sim);
        this.robot = robot;
    }

    @Override
    public void execute() {
        this.robot.remplirReservoir();
    }
}
