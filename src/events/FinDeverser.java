package events;

import sim.Robot;
import sim.Simulateur;

public class FinDeverser extends Evenement{
    private Robot robot;
    private int qteDeverse;

    public FinDeverser(long date, Simulateur sim, Robot robot, int qteDeverse) {
        super(date, sim);
        this.robot = robot;
        this.qteDeverse = qteDeverse;
    }

    public void execute() {
        robot.deverserEau(this.qteDeverse);
    }
}
