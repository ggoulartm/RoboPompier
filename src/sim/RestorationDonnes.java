package sim;

import sim.Incendie;
import sim.Robot;

public class RestorationDonnes{
    int size;
    Incendie[] I;
    Robot[] R;

    public RestorationDonnes(Incendie[] I, Robot[] R) {
        this.I = new Incendie[I.length];
        this.R = new Robot[R.length];
        for(Incendie iT: I){
            this.I[this.size] = new Incendie(iT.getPosition(), iT.getIntensite());
            this.size++;
        }
        this.size = 0;
        for(Robot rT: R){
            this.R[this.size] = rT;
            this.size++;
        }
    }
}