package a06.e1;

import java.util.List;
import java.util.function.UnaryOperator;

public class FluentParserFactoryImpl implements FluentParserFactory{

    @Override
    public FluentParser<Integer> naturals() {
        return new FluentParser<>() {
            private int expected = 0;
            @Override
            public FluentParser<Integer> accept(Integer value) {
                if (value != this.expected){
                    throw new IllegalStateException();
                }
                this.expected++;
                return this;
            }
            
        };
    }

    @Override
    public FluentParser<List<Integer>> incrementalNaturalLists() {
        return new FluentParser<List<Integer>>(){
            private int sizeExpected = 0;
            private FluentParser<Integer> parserN = naturals();

            @Override
            public FluentParser<List<Integer>> accept(List<Integer> value) {
                try {
                    if(value.size() != sizeExpected){
                        throw new IllegalStateException();
                    }
                    value.forEach(v -> parserN.accept(v));
                } catch (IllegalStateException e) {
                    throw e;
                }
                sizeExpected++;
                parserN = naturals();
                return this;
            }
            
        };
    }
    
    @Override
    public FluentParser<Integer> repetitiveIncrementalNaturals() {
        return new FluentParser<Integer>(){
            private int count = 1;
            private int countNaturals = 0;
            private FluentParser<Integer> parserN = naturals();

            @Override
            public FluentParser<Integer> accept(Integer value) {
                try{
                    parserN.accept(value);
                }
                catch(IllegalStateException e){
                    throw e;
                }
                countNaturals++;
                if(countNaturals == count){
                    countNaturals=0;
                    parserN = naturals();
                    count++;
                }
                return this;
            }
            
        };
    }

    @Override
    public FluentParser<String> repetitiveIncrementalStrings(String s) {
        return new FluentParser<String>(){
            private final FluentParser<Integer> parserIncN = repetitiveIncrementalNaturals();

            @Override
            public FluentParser<String> accept(String value) {
                try {
                    parserIncN.accept(value.length()-1);
                } catch (IllegalStateException e) {
                    throw e;
                }
                return this;
            }

        };
    }
/* 
    @Override
    public FluentParser<Pair<Integer, List<String>>> incrementalPairs(int i0, UnaryOperator<Integer> op, String s) {
        return new FluentParser<Pair<Integer, List<String>>>(){
            private int i = 0;
            private FluentParser<Integer> parserNX = naturals();
            private FluentParser<Integer> parserNY = naturals();

            @Override
            public FluentParser<Pair<Integer, List<String>>> accept(Pair<Integer, List<String>> value) {
                try {
                    parserNX.accept(value.getX());
                    parserNY.accept(value.getY().size());
                } catch (IllegalStateException e) {
                    if (i >= i0 && i){

                    }
                }
                return this;
            }

        };*/

    @Override
    public FluentParser<Pair<Integer, List<String>>> incrementalPairs(int i0, UnaryOperator<Integer> op, String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    
}
