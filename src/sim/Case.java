package sim;

import java.awt.*;
import gui.GUISimulator;
import java.awt.Color;

public class Case{
    
    private int ligne, colonne;
    private NatureTerrain nature;
    private Incendie incendie;
    private boolean estBrulee;
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

    public Incendie getIncendie() {
        return incendie;
    }

    public void setEstBrulee(boolean brulee) {
        this.estBrulee = brulee;
    }

    public boolean isBrulee() {
        return this.estBrulee;
    }

    public void dessineCase(GUISimulator gui, int tailleCase) {
        int coordX = this.getColonne() * tailleCase;
        int coordY = this.getLigne() * tailleCase;

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
                            Color.GREEN,                             // Fill color
                            tailleCase, tailleCase
                    ));
                }
                break;
            case ROCHE:
                gui.addGraphicalElement(new gui.Rectangle(
                        coordX, coordY,
                        Color.BLACK,                           // Border color
                        Color.GRAY,                             // Fill color
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
                } else {
                    gui.addGraphicalElement(new gui.Rectangle(
                            coordX, coordY,
                            Color.BLACK,                           // Border color
                            Color.LIGHT_GRAY,                             // Fill color
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
                            Color.YELLOW,                             // Fill color
                            tailleCase, tailleCase
                    ));
                }
                break;
            default:
                return;
        }
        if (this.getIncendie() != null) {
            gui.addGraphicalElement(new gui.Rectangle(
                    coordX, coordY,
                    Color.BLACK,                           // Border color
                    Color.RED,                             // Fill color
                    tailleCase, tailleCase
            ));
        }
    }
}