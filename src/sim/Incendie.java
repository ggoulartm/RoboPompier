package sim;

import gui.GUISimulator;
import java.awt.Color;

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
        int coordX = this.position.getColonne() * tailleCase;
        int coordY = this.position.getLigne() * tailleCase;
            gui.addGraphicalElement(new gui.Rectangle(
                    coordX, coordY,
                    Color.BLACK,                           // Border color
                    Color.RED,                             // Fill color
                    tailleCase, tailleCase
            ));
    }
}
