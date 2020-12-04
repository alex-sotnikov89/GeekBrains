package Level3.Lesson5;

import Level3.Lesson5.GasPool;
import Level3.Lesson5.Transport;

import java.util.concurrent.Semaphore;

public class FuelStation {
    private GasPool gasPool;
    private Semaphore semaphore;

    public FuelStation() {
        this.semaphore = new Semaphore(3);
        this.gasPool = new GasPool();
    }

    public boolean getGasPoolInfo() {
        return gasPool.getInfo();
    }

    public void refuel(Transport transport) throws InterruptedException {
        System.out.println(String.format(
                "[%s] is going to fuelstation", transport.getModel())
        );
        if (gasPool.getInfo()) {
            semaphore.acquire();
            float fuel;
            fuel = gasPool.request(transport.getTankVolume() - transport.getFuelVolume());
            System.out.println(String.format("[%s] is refueling.", transport.getModel()));
            Thread.sleep(5000);
            System.out.println(String.format("[%s] filled to %s.", transport.getModel(), fuel));
            transport.setFuelVolume(fuel);
            semaphore.release();
        }
    }
}
