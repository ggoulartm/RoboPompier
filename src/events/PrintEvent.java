package events;

public class PrintEvent extends Evenement
{
    String message;
    public PrintEvent(long date, String msg)
    {
        super(date);
        this.message = msg;
    }
    @Override
    public void execute()
    {
        System.out.println("PrintEvent with date: "+this.getDate()+" MSG: "+this.message);
    }
}
