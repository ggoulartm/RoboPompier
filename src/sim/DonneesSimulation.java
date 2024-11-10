package sim;

public class DonneesSimulation {
    private Incendie[] incendies;
    private Carte carte;
    private Robot[] robots;

    public DonneesSimulation(Carte carte, Incendie[] incs, Robot[] robs)
    {
        this.carte = carte;
        this.incendies = incs;
        this.robots = robs;
        System.out.println("Crée-moi!");
    }

    public Carte getCarte()
    {
        return this.carte;
    }

    @Override
    public String toString()
    {
        String incendieString = "";
        for(int i = 0; i < incendies.length; i++)
        {
            incendieString += "\n"+incendies[i].toString();
        }
        String robotString = "";
        for(int i = 0; i < robots.length; i++)
        {
            robotString += "\n"+robots[i].toString();
        }

        return carte.toString() +"\n" + incendieString + "\n" + robotString;
    }
}