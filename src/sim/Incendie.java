package sim;

import gui.GUISimulator;
import gui.ImageElement;
import gui.Text;

import java.awt.Color;

public class Incendie {
    private Case position;
    private int intensite;
    static int maxIntensity = 0; 
    private int InitIntensite;

    public Incendie(Case position, int intensite)
    {
        System.out.println("Creating Incendie");
        System.out.println(position);
        this.position = position;
        this.InitIntensite = intensite;
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

    public void Restore() {
        this.intensite = InitIntensite;
    }

    @Override
    public String toString()
    {
        return "Incendie at " +this.position.toString()+" with intensity: " + this.intensite;
    }

    public void draw (GUISimulator gui, int tailleCase) {
        int coordX = tailleCase + this.position.getColonne() * tailleCase;
        int coordY = tailleCase + this.position.getLigne() * tailleCase;
        Color Dark_Green = new Color(20, 100, 30);
        /* float alpha = (float)this.intensite/(float)maxIntensity;
        Color redIntensity = new Color(1, 0, 0, alpha);
            gui.addGraphicalElement(new gui.Rectangle(
                    coordX, coordY,
                    Color.BLACK,                           // Border color
                    redIntensity,                             // Fill color
                    tailleCase, tailleCase
            ));*/
        if (this.position.getNature() == NatureTerrain.FORET){
            gui.addGraphicalElement(new gui.Rectangle(
                    coordX, coordY,
                    Color.BLACK,                           // Border color
                    Dark_Green,                             // Fill color
                    tailleCase, tailleCase
            ));
            gui.addGraphicalElement(new ImageElement(
                    (coordX - tailleCase/2), (coordY - tailleCase/2),
                    "images/forest-fire.png",
                    tailleCase, tailleCase,
                    null
            ));
            gui.addGraphicalElement(new Text((coordX), (coordY+tailleCase/4), Color.WHITE, Integer.toString(this.getIntensite())));

        }
        else if (this.position.getNature() == NatureTerrain.HABITAT){
            gui.addGraphicalElement(new gui.Rectangle(
                    coordX, coordY,
                    Color.BLACK,                           // Border color
                    Dark_Green,                             // Fill color
                    tailleCase, tailleCase
            ));
            gui.addGraphicalElement(new ImageElement(
                    (coordX - tailleCase/2), (coordY - tailleCase/2),
                    "images/habitat-fire.png",
                    tailleCase, tailleCase,
                    null
            ));
            gui.addGraphicalElement(new Text((coordX), (coordY+tailleCase/4), Color.BLACK, Integer.toString(this.getIntensite())));

        }
        else {
        gui.addGraphicalElement(new ImageElement(
                (coordX - tailleCase/2), (coordY - tailleCase/2),
                "images/fire.png",
                tailleCase, tailleCase,
                null
        ));
        gui.addGraphicalElement(new Text((coordX), (coordY+tailleCase/4), Color.WHITE, Integer.toString(this.getIntensite())));
        }
    }
}
