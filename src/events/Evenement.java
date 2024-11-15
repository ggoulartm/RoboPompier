package events;

/**
 * Evenement is an abstract class that represents an event in the simulation
 */
public abstract class Evenement {
    private int date;
    private Evenement next;
    public Evenement(int date)
    {
        this.date = date;
        this.next = null;
    }
    public int getDate()
    {
        return this.date;
    }
    public Evenement getNext() {
        return next;
    }
    public void setNext(Evenement e) {
        this.next = e;
    }
    abstract public void execute();
}