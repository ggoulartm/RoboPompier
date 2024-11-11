package sim;

public class Drone extends Robot
{

    public Drone(Case position, int vitesse)
    {
        super(position, vitesse);
    }

    @Override
    public String getType()
    {
        return "Drone";
    }
}