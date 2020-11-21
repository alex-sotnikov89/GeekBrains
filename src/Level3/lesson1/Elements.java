package Level3.lesson1;

import java.util.ArrayList;
import java.util.Collections;

public class Elements<E>{
    public void changeElements(E[] array, int index1, int index2){
        E element;
        element = array[index1];
        array[index1] = array[index2];
        array[index2] = element;
    }

    public ArrayList<E> transformationTo(E[] array){
        ArrayList<E> arrayList = new ArrayList<>();
        Collections.addAll(arrayList , array);
        return arrayList;
    }
}
