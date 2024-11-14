package events;

import sim.Robot;

public class FinDeverser extends Evenement{
    private Robot robot;
    private int qteDeverse;

    public FinDeverser(long date,Robot robot, int qteDeverse) {
        super(date);
        this.robot = robot;
        this.qteDeverse = qteDeverse;
    }

    public void execute() {
        robot.deverserEau(this.qteDeverse);
    }
}
