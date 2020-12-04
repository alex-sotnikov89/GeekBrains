package Level3.Lesson5;

public class GasPool {
    private static float fuelReserve = 200f;

    public float request(float amount){
        if ((fuelReserve-amount)<0){
            float result = fuelReserve;
            fuelReserve=0;
            return result;
        }
        fuelReserve -=amount;
        return  amount;
    }

    public boolean getInfo() {
        if (fuelReserve<=0){
            System.out.println("Fuelstation is empty!");
            return false;
        }
        return true;
    }
}
