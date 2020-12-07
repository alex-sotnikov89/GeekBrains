package Level3.Lesson5;

public abstract class Transport implements Runnable {
    protected FuelStation fuelStation;
    protected String model;
    protected float tankVolume;
    protected float fuelVolume;
    protected float fuelExpenses;

    public float getFuelVolume() {
        return fuelVolume;
    }

    public float getTankVolume() {
        return tankVolume;
    }

    public String getModel() {
        return model;
    }

    public void setFuelVolume(float fuelVolume) {
        this.fuelVolume = fuelVolume;
    }

    public void drive() {
        try {
            do {
                while (fuelVolume - fuelExpenses > 0) {
                    System.out.println(String.format(
                            "[%s] is going to the road.", this.getModel())
                    );
                    Thread.sleep(300);
                    fuelVolume -= fuelExpenses;
                }
                fuelStation.refuel(this);
            } while (fuelStation.getGasPoolInfo());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        drive();
    }
}
