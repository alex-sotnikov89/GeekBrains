package Level3.Lesson5;


import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GasPool {
    private final ReentrantReadWriteLock lock=new ReentrantReadWriteLock();
    private float fuelReserve = 200f;

    public float request(float amount){
        lock.writeLock().lock();
        try {
            if ((fuelReserve-amount)<0){
                float result = fuelReserve;
                fuelReserve=0;
                return result;
            }
            fuelReserve -=amount;
            System.out.println("Fuel left: "+fuelReserve);
            return  amount;
        }finally {
            lock.writeLock().unlock();
        }

    }

    public boolean getInfo() {
        lock.readLock().lock();
        try {
            if (fuelReserve<=0){
                System.out.println("Fuelstation is empty!");
                return false;
            }
            return true;
        }finally {
            lock.readLock().unlock();
        }

    }
}
