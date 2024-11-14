package events;

import sim.Incendie;
import sim.Robot;

public class DebutDeverser extends Evenement{
    private Robot robot;
    private Incendie incendieDeversage;
    private int qteDeverse;

    public DebutDeverser(long date, Robot robot, Incendie incendie) {
        super(date);
        this.robot = robot;
        this.incendieDeversage = incendie;
        if (incendieDeversage.getIntensite() == 0) this.qteDeverse = 0;
        else this.qteDeverse = Math.min(robot.getDeversementVolume(), incendieDeversage.getIntensite());
    }

    public void execute() {
        long dateFinDeverser = dateFinEvenement();
        FinDeverser FinDeverser = new FinDeverser(dateFinDeverser, robot, qteDeverse);
    }

    public long dateFinEvenement() {
        return this.getDate() + robot.setDeversementTemps(qteDeverse);
    }
}
