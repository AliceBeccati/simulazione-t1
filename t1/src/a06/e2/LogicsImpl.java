package a06.e2;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

public class LogicsImpl implements Logics{

    private final Map<Pair<Integer,Integer>,Integer> positions;
    int size;
    int quit;

    public LogicsImpl(int size){
        positions = new LinkedHashMap<>();
        this.size = size;
    }

    @Override
    public String init(Pair<Integer,Integer> pair) {
        Random random = new Random();
        int val = random.nextInt(2)+1;
        positions.put(pair,val);
        return String.valueOf(val); 
    }

    //returns the entry set list of the specified index column
    private List<Entry<Pair<Integer,Integer>,Integer>> colls(int index){
        return positions.entrySet().stream().filter(p -> p.getKey().getY() == index).toList();
    }

    @Override
    public boolean quit() {
        return quit == size;
    }
    
    @Override
    public Map<Pair<Integer,Integer>,Integer> advance() { 
        quit = 0;
        for(int j = 0; j < size; j++){
            List<Entry<Pair<Integer,Integer>,Integer>> col = colls(j);
            Pair<Integer,Integer> p = sum(col);
            if(p != null){
                positions.put(p,-1);
                collapse(col);
            }
            else{
                quit++;
            }
        }
        return positions;
    }

    //return the position of the element to delete and add to the one below
    private Pair<Integer,Integer> sum(List<Entry<Pair<Integer,Integer>,Integer>> col){
        for(int i = col.size()-1; i > 0; i--){
            int valCurr = col.get(i).getValue();
            int valNext = col.get(i-1).getValue();
            if(valCurr == valNext && (valCurr != -1)){
                col.get(i).setValue(valCurr+valNext);
                return col.get(i-1).getKey();
            }
        }
        return null;
    }

    private void collapse(List<Entry<Pair<Integer,Integer>,Integer>> col){
        for(int i = col.size()-1; i > 0; i--){
            int valCurr = col.get(i).getValue();
            int valNext = col.get(i-1).getValue();
            if(valCurr == -1){
                int tmp = valCurr;
                col.get(i).setValue(valNext);
                col.get(i-1).setValue(tmp);
            }
        }
    }
    
}
