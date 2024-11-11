package sim;

public class RobotWheels extends Robot {

    public RobotWheels(Case position, int vitesse, int waterCapacityMax, int reserveWaterAmount) {
        super(position, vitesse,waterCapacityMax,reserveWaterAmount);
        this.kind = RobotKind.WHEELS;  
    }

    @Override
    public void setPosition(Case target) {
        if (target.getNature() == NatureTerrain.TERRAIN_LIBRE || target.getNature() == NatureTerrain.HABITAT) {
            this.position = target;
        } else {
            System.out.println("Wheeled robot cannot move to this terrain.");
        }
    }

    @Override
    public void deverserEau(int volume) {
        if (this.volumeReservoir>0 && volume <= this.volumeReservoir) {
            this.volumeReservoir -= volume;
            System.out.println("Wheeled robot pours " + volume + "L of water.");
        }
    }

    @Override
    public void remplirReservoir() {
        System.out.println("Wheeled robot refilling...");
        this.volumeReservoir = this.volumeReservoirMax;
    }
}
