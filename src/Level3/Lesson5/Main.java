package Level3.Lesson5;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        FuelStation fuelStation = new FuelStation();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        executorService.submit(new Car("LADA",fuelStation));
        executorService.submit(new Car("Kamaz",fuelStation));
        executorService.submit(new Truck("Belaz",fuelStation));
        executorService.submit(new Car("BMW",fuelStation));
        executorService.submit(new Bus("Ikarus",fuelStation));
        executorService.submit(new Car("Buhanka",fuelStation));
        executorService.shutdown();
    }
}
