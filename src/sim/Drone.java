package sim;
import gui.GUISimulator;
import java.awt.Color;


public class Drone extends Robot
{

    public Drone(Case position, int vitesse, int max, int reserve)
    {
        super(position, vitesse, max, reserve);
        this.type = RobotType.DRONE;
    }

    @Override
    public void draw(GUISimulator gui, int tailleCase) {
        Case caseRobot = this.getPosition();
        int caseX = caseRobot.getColonne() * tailleCase;
        int caseY = caseRobot.getLigne() * tailleCase;

        gui.addGraphicalElement(new gui.Oval(
                caseX, caseY,
                Color.BLACK,                           // Border color
                Color.PINK,                             // Fill color
                tailleCase, tailleCase
        ));
    }
}


