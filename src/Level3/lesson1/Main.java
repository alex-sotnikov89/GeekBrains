package Level3.lesson1;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        String[] array1 = {"1","2","3"};
        Integer[] array2 = {1,2,3};

        Elements<String> e1 = new Elements<>();
        Elements<Integer> e2 = new Elements<>();
        e1.changeElements(array1,0,2);

        for (int i = 0; i <array1.length ; i++) {
            System.out.println(array1[i]);
        }
        ArrayList<String> al1 = new ArrayList<>();
        al1 = e1.transformationTo(array1);
        System.out.println(al1.toString());

        e2.changeElements(array2,0,1);
        for (int i = 0; i <array2.length ; i++) {
            System.out.println(array2[i]);
        }

        ArrayList<Integer> al2 = new ArrayList<>();
        al2 = e2.transformationTo(array2);
        System.out.println(al2.toString());

    }
}
