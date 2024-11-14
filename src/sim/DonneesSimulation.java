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

    public void Restore() {
        RestoreIncendie();
        RestoreRobots();
    }

    protected void RestoreRobots(){
        for(Robot r : this.robots){
            r.Restore();
        }
    }

    protected void RestoreIncendie(){
        for(Incendie It1 : this.incendies){
            It1.Restore();
        }
    }

    public Carte getCarte()
    {
        return this.carte;
    }

    public Robot[] getRobots()
    {
        return this.robots;
    }

    public Incendie[] getIncendies()
    {
        return this.incendies;
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