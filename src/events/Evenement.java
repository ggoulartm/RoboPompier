package events;

public abstract class Evenement {
    private long date;
    private Evenement next;
    public Evenement(long date)
    {
        this.date = date;
        this.next = null;
    }
    public long getDate()
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