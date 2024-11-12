package events;

import sim.Robot;

public class FinRemplir extends Evenement{
    Robot robot;

    public FinRemplir(long date, Robot robot) {
        super(date);
        this.robot = robot;
    }

    @Override
    public void execute() {
        this.robot.remplirReservoir();
    }
}
