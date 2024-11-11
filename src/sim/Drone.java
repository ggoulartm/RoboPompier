package sim;

public class Drone extends Robot
{

    public Drone(Case position, int vitesse, int max, int reserve)
    {
        super(position, vitesse, max, reserve);
    }

    @Override
    public String getType()
    {
        return "Drone";
    }
}