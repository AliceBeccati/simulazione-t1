package a06.e2;

import java.util.Map;

public interface Logics {
    
    String init(Pair<Integer,Integer> pair);
    boolean quit();
    Map<Pair<Integer,Integer>,Integer> advance();
}
