package sim;
import gui.GUISimulator;
import java.awt.Color;

public class Pattes extends Robot
{
    public Pattes(Case position, int vitesse, int reserve)
    {
        super(position, vitesse, Integer.MAX_VALUE, reserve);
        this.type = RobotType.PATTES;
    }

    @Override
    public void setPosition(Case target) {
        if (target.getNature() == NatureTerrain.TERRAIN_LIBRE || target.getNature() == NatureTerrain.HABITAT || target.getNature() == NatureTerrain.FORET) {
            int adjustedSpeed = (target.getNature() == NatureTerrain.FORET) ? this.vitesse / 2 : this.vitesse;
            System.out.println("Robot Pattes moving to target at speed " + adjustedSpeed + " km/h.");
            this.position = target;
        } else {
            System.out.println("Robot Pattes cannot move to this terrain.");
        }
    }

    @Override
    public void remplirReservoir() {
        System.out.println("Robot Pattes refilling...");
        this.volumeReservoir = Integer.MAX_VALUE;
    }

    @Override
    public void deverserEau(int volume) {
        if (this.volumeReservoir > 0 && volume <= this.volumeReservoir) {
            this.volumeReservoir -= volume;
            System.out.println("Robot Pattes pours " + volume + "L of water.");
        } else {
            this.remplirReservoir();
            this.deverserEau(volume);
        }
    }

    @Override
    public void draw(GUISimulator gui, int tailleCase) {
        Case caseRobot = this.getPosition();
        int caseX = caseRobot.getColonne() * tailleCase;
        int caseY = caseRobot.getLigne() * tailleCase;

        gui.addGraphicalElement(new gui.Oval(
                caseX, caseY,
                Color.BLACK,                           // Border color
                Color.CYAN,                             // Fill color
                tailleCase, tailleCase
        ));     }
}