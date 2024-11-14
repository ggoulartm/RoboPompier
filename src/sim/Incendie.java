package sim;

import gui.GUISimulator;
import java.awt.Color;

public class Incendie {
    private Case position;
    private int intensite;
    static int maxIntensity = 0; 

    public Incendie(Case position, int intensite)
    {
        System.out.println("Creating Incendie");
        System.out.println(position);
        this.position = position;
        this.intensite = intensite;
        maxIntensity = Math.max(maxIntensity, intensite);
    }

    public void reduceIntensite(int quantiteEau)
    {
        this.intensite = Math.max(0, this.intensite-quantiteEau);
    }

    public Case getPosition() {
        return position;
    }

    public int getIntensite() {
        return intensite;
    }

    @Override
    public String toString()
    {
        return "Incendie at " +this.position.toString()+" with intensity: " + this.intensite;
    }

    public void draw (GUISimulator gui, int tailleCase) {
        int coordX = tailleCase + this.position.getColonne() * tailleCase;
        int coordY = tailleCase + this.position.getLigne() * tailleCase;
        float alpha = (float)this.intensite/(float)maxIntensity;
        Color redIntensity = new Color(1, 0, 0, alpha);
            gui.addGraphicalElement(new gui.Rectangle(
                    coordX, coordY,
                    Color.BLACK,                           // Border color
                    redIntensity,                             // Fill color
                    tailleCase, tailleCase
            ));
    }
}
