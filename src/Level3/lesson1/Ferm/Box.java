package Level3.lesson1.Ferm;

import java.util.ArrayList;

public class Box<T extends Fruit> {
    ArrayList<T> box;

    public Box() {
        this.box = new ArrayList<>();
    }

    public float getWeight() {
        if (box.size()>0) {
            return box.size() * box.get(0).weight;
        }
        return 0f;
    }

    public boolean compare(Box b) {
        if (this.getWeight() == b.getWeight()) {
            return true;
        }
        return false;
    }

    public void transferTo(Box<T> b) {
        b.getBox().addAll(this.box);
        this.box.clear();
    }

    public void putFruit(T fruit, int k) {
        if (k < 0) {
            k = 0;
        }
        for (int i = 1; i <= k; i++) {
            this.box.add(fruit);
        }
    }

    private ArrayList<T> getBox() {
        return box;
    }
}
