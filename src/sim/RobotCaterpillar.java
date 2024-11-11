package sim;

public class RobotCaterpillar extends Robot {

    public RobotCaterpillar(Case position, int vitesse, int waterCapacityMax, int reserveWaterAmount) {
        super(position,vitesse,waterCapacityMax,reserveWaterAmount);  
    }

    public String getType() {
        return "RobotCaterpillar";
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
}
