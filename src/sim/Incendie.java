package sim;

public class Incendie {
    private Case position;
    private int intensite;

    public Incendie(Case position, int intensite)
    {
        System.out.println("Creating Incendie");
        System.out.println(position);
        this.position = position;
        this.intensite = intensite;
    }

    @Override
    public String toString()
    {
        return "Incendie at " +this.position.toString()+" with intensity: " + this.intensite;
    }
}
