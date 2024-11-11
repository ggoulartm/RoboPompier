package sim;

public class Roues extends Robot
{
    public Roues(Case position, int vitesse)
    {
        super(position, vitesse);
    }

    @Override
    public String getType()
    {
        return "Roues";
    }
}