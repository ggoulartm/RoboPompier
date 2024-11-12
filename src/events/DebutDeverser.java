package events;

import sim.Incendie;
import sim.Robot;
import sim.Simulateur;

public class DebutDeverser extends Evenement{
    private Robot robot;
    private Incendie incendieDeversage;
    private int qteDeverse;

    public DebutDeverser(long date, Simulateur sim, Robot robot, Incendie incendie) {
        super(date, sim);
        this.robot = robot;
        this.incendieDeversage = incendie;
        if (incendieDeversage.getIntensite() == 0) this.qteDeverse = 0;
        else this.qteDeverse = Math.min(robot.getDeversementVolume(), incendieDeversage.getIntensite());
    }

    public void execute() {
        long dateFinDeverser = dateFinEvenement();
        FinDeverser FinDeverser = new FinDeverser(dateFinDeverser, this.getSim(), robot, qteDeverse);
        this.getSim().addEvent(FinDeverser);
    }

    public long dateFinEvenement() {
        return this.getDate() + robot.setDeversementTemps(qteDeverse);
    }
}
