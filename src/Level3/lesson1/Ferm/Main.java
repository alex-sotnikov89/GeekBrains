package Level3.lesson1.Ferm;

public class Main {
    public static void main(String[] args) {
        Box<Apple> box1_Apple = new Box<>();
        Box<Apple> box2_Apple = new Box<>();
        Box<Orange> box3_Orange = new Box<>();
        box1_Apple.putFruit(new Apple(),3);
        box2_Apple.putFruit(new Apple(),3);
        box3_Orange.putFruit(new Orange(),2);

        System.out.println(box1_Apple.box.size());

        System.out.println(box1_Apple.getWeight());

        System.out.println(box2_Apple.compare(box1_Apple));
        System.out.println(box2_Apple.compare(box3_Orange));

        box1_Apple.transferTo(box2_Apple);
        System.out.println(box2_Apple.getWeight());
    }
}
