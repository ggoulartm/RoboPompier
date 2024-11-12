package sim;
import gui.GUISimulator;
import java.awt.Color;

public class RobotCaterpillar extends Robot {

    //Vitesse par défaut de 60 km/h, mais qui peut être lue dans le fichier (sans dépasser 80 km/h)
    //Réservoir de 2000 litres. Intervention unitaire : 100 litres en 8 sec.
    //Remplissage complet en 5 minutes. Se remplit à côté d’une case contenant de l’eau.
    public RobotCaterpillar(Case position, int vitesse, int waterCapacityMax, int reserveWaterAmount) {
        super(position,vitesse,waterCapacityMax,reserveWaterAmount);  
        if(vitesse == 0)
        {
            this.vitesse = 60;
        } else if (vitesse > 80) {
            this.vitesse = 80;
        }
        if(waterCapacityMax == 0)
        {
            this.volumeReservoirMax = 2000;
        }
        this.type = RobotType.CATERPILLAR;
        this.tempsRemplissage = 5;
        this.Deversement = new InterventionUnitaire(100, 8); //Litres/seconde
    }

    //La vitesse est diminuée de 50% en forêt.
    //Ne peut pas se rendre sur de l’eau ou du rocher.
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
