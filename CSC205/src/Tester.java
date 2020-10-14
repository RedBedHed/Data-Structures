
import util.*;

public class Tester {

    public static void main(String[] args){

        ArrayDeque<Integer> l = new ArrayDeque<>();
        ArrayDeque<String> s = new ArrayDeque<>();
        for(int i = 0; i < 20; i++) {l.insert(i); l.insertOnFront(i);}
        for(int i = 0; i < 20; i++) {s.insert(i+""); s.insertOnFront(i+"");}
        System.out.println(l.equals(s));
        System.out.println(l);

    }
}
