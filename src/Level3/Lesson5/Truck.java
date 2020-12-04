package Level3.Lesson5;

public class Truck extends Transport {
    public Truck(String model, FuelStation fuelStation) {
        this.model = model;
        this.tankVolume = 60f;
        this.fuelVolume = 60f;
        this.fuelExpenses = 15f;
        this.fuelStation = fuelStation;
    }

}
