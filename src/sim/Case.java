package sim;

import gui.GUISimulator;
import gui.ImageElement;

import java.awt.Color;

public class Case{
    
    private int ligne, colonne;
    private NatureTerrain nature;
    private boolean estBrulee;

    /**
     * Constructor for one Case used to build a sim.Carte.java
     * @param ligne line of position in map
     * @param colonne coloumn of position in map
     * @param nature type of sim.NatureTerrain of the Case
     */
    public Case(int ligne, int colonne, NatureTerrain nature){
        this.ligne = ligne;
        this.colonne = colonne;
        this.nature = nature;
    }
    public int getLigne(){
        return this.ligne;
    }
    public int getColonne(){
        return this.colonne;
    }
    public NatureTerrain getNature(){
        return this.nature;
    }

    @Override
    public String toString()
    {
        return "lig: "+this.ligne+" col: "+this.colonne;
    }

    public void setEstBrulee(boolean brulee) {
        this.estBrulee = brulee;
    }

    public void extinguish() {
        setEstBrulee(false);
    }
    public boolean isBrulee() {
        return this.estBrulee;
    }

    /**
     * Function that draws all the cases in the GUI
     * @param GUISimulator,int the UI where the cases will be drawn and an integer with the case dimensions
     * @return arraylist of water cases
     */
    public void draw(GUISimulator gui, int tailleCase) {
        int coordX = tailleCase + this.getColonne() * tailleCase;
        int coordY = tailleCase + this.getLigne() * tailleCase;
        Color Dark_Green = new Color(20, 100, 30);

        switch (this.getNature()) {
            case EAU:
                gui.addGraphicalElement(new gui.Rectangle(
                        coordX, coordY,
                        Color.BLACK,                           // Border color
                        Color.BLUE,                             // Fill color
                        tailleCase, tailleCase
                ));
                break;
            case FORET:
                if(this.isBrulee()) {
                    gui.addGraphicalElement(new gui.Rectangle(
                            coordX, coordY,
                            Color.BLACK,                           // Border color
                            Color.BLACK,                             // Fill color
                            tailleCase, tailleCase
                    ));
                    return;
                } else {
                    gui.addGraphicalElement(new gui.Rectangle(
                            coordX, coordY,
                            Color.BLACK,                           // Border color
                            Dark_Green,                             // Fill color
                            tailleCase, tailleCase
                    ));
                    gui.addGraphicalElement(new ImageElement(
                            (coordX - tailleCase/2), (coordY - tailleCase/2),
                            "images/forest.png",
                            tailleCase, tailleCase,
                            null
                    ));
                }
                break;
            case ROCHE:
                gui.addGraphicalElement(new gui.Rectangle(
                        coordX, coordY,
                        Color.BLACK,                           // Border color
                        Color.darkGray,                             // Fill color
                        tailleCase, tailleCase
                ));                     break;
            case TERRAIN_LIBRE:
                if(this.isBrulee()) {
                    gui.addGraphicalElement(new gui.Rectangle(
                            coordX, coordY,
                            Color.BLACK,                           // Border color
                            Color.BLACK,                             // Fill color
                            tailleCase, tailleCase
                    ));
                    return;
                }
                else {
                    gui.addGraphicalElement(new gui.Rectangle(
                            coordX, coordY,
                            Color.BLACK,                           // Border color
                            Dark_Green,                             // Fill color
                            tailleCase, tailleCase
                    ));

                }
                break;
            case HABITAT:
                if(this.isBrulee()) {
                    gui.addGraphicalElement(new gui.Rectangle(
                            coordX, coordY,
                            Color.BLACK,                           // Border color
                            Color.BLACK,                             // Fill color
                            tailleCase, tailleCase
                    ));
                    return;
                } else {
                    gui.addGraphicalElement(new gui.Rectangle(
                            coordX, coordY,
                            Color.BLACK,                           // Border color
                            Dark_Green,                             // Fill color
                            tailleCase, tailleCase
                    ));
                    gui.addGraphicalElement(new ImageElement(
                            (coordX - tailleCase/2), (coordY - tailleCase/2),
                            "images/habitat.png",
                            tailleCase, tailleCase,
                            null
                    ));
                }
                break;
        }
    }

    @Override
    public boolean equals(Object o) 
    {
        if( ! (o instanceof Case)) {
                return false ;
        }
        // ou, plus restrictif :
        // if(o.getClass() != this.getClass()) {
        //  return false ;
        //}

        // cast explicite nécessaire, car Downcast :
        Case other = (Case) o;
        return (this.ligne == other.ligne
                && this.colonne == other.colonne
                && this.nature == other.nature);
    }
    
}