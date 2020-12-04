package Level3.Lesson5;

public class Bus extends Transport {
    public Bus(String model, FuelStation fuelStation) {
        this.model = model;
        this.tankVolume = 40f;
        this.fuelVolume = 40f;
        this.fuelExpenses = 7.5f;
        this.fuelStation = fuelStation;
    }

}
