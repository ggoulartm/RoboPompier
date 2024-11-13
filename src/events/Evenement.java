package events;

import sim.Simulateur;

public abstract class Evenement {
    private long date;
    private Evenement next;
    private Simulateur simulateur;

    public Evenement(long date, Simulateur sim)
    {
        this.date = date;
        this.simulateur = sim;
        this.next = null;
    }
    public long getDate()
    {
        return this.date;
    }
    public Evenement getNext() {
        return next;
    }
    public Simulateur getSim() {
        return simulateur;
    }
    public void setNext(Evenement e) {
        next = e;
    }
    abstract public void execute();
}