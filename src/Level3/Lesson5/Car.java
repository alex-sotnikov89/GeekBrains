package Level3.Lesson5;


public class Car extends Transport {
    public Car(String model, FuelStation fuelStation) {
        this.model = model;
        this.tankVolume = 20f;
        this.fuelVolume = 20f;
        this.fuelExpenses = 2.5f;
        this.fuelStation = fuelStation;
    }

}
