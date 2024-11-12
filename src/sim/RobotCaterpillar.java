package sim;
import gui.GUISimulator;
import java.awt.Color;

public class RobotCaterpillar extends Robot {

    public RobotCaterpillar(Case position, int vitesse, int waterCapacityMax, int reserveWaterAmount) {
        super(position,vitesse,waterCapacityMax,reserveWaterAmount);  
        this.type = RobotType.CATERPILLAR;
    }

    @Override
    public void setPosition(Case target) {
        if (target.getNature() == NatureTerrain.TERRAIN_LIBRE || target.getNature() == NatureTerrain.HABITAT || target.getNature() == NatureTerrain.FORET) {
            int adjustedSpeed = (target.getNature() == NatureTerrain.FORET) ? this.vitesse / 2 : this.vitesse;
            System.out.println("Caterpillar robot moving to target at speed " + adjustedSpeed + " km/h.");
            this.position = target;
        } else {
            System.out.println("Caterpillar robot cannot move to this terrain.");
        }
    }

    @Override
    public void deverserEau(int volume) {
        if (this.volumeReservoir > 0 && volume <= this.volumeReservoir) {
            this.volumeReservoir -= volume;
            System.out.println("Caterpillar robot pours " + volume + "L of water.");
        }
    }

    @Override
    public void remplirReservoir() {
        System.out.println("Caterpillar robot refilling...");
        this.volumeReservoir = this.volumeReservoirMax;
    }

    @Override
    public void draw(GUISimulator gui, int tailleCase) {
        Case caseRobot = this.getPosition();
        int caseX = caseRobot.getColonne() * tailleCase;
        int caseY = caseRobot.getLigne() * tailleCase;

        gui.addGraphicalElement(new gui.Oval(
                caseX, caseY,
                Color.BLACK,                           // Border color
                Color.ORANGE,                             // Fill color
                tailleCase, tailleCase
        ));
    }
}
