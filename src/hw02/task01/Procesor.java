package hw02.task01;

import java.util.*;

public class Procesor {

    private int[] processors;

    public Procesor(int[] processors){
        this.processors = processors;
    }

    private int optimizeProcessorCount(){
        return getProcessorState(0, processors.length).processorCount;
    }

    private ProcOptState getProcessorState(int start, int end){
        Map<Integer, ProcOptState> lastProcessorToState = new HashMap<>();

        for ( int i = start; i < end; i++ ){
            boolean isAdded = false;
            for ( ProcOptState state : new ArrayList<>(lastProcessorToState.values()) ){
                if ( canAdd ( state, processors[i])){
                    isAdded = true;
                    lastProcessorToState.merge(processors[i],
                            new ProcOptState(!state.isLastOdd, processors[i], state.processorCount + 1),
                            ( s1, s2 ) -> s1.processorCount > s2.processorCount ? s1 : s2);
                }
            }
            if ( !isAdded ){
                lastProcessorToState.put(processors[i], new ProcOptState(isOdd(processors[i]), processors[i], 1));
            }
        }
        return lastProcessorToState.values().stream().max(Comparator.comparingInt(s -> s.processorCount)).get();
    }

    private boolean isOdd(int num){
        return (num & 1) != 0;
    }

    private boolean canAdd(ProcOptState state, int processor){
        return ( state.isLastOdd ^ isOdd(processor) ) &&
                ( state.lastProcessorSize < processor );
    }

    public static void main(String[] args){

        Scanner sysIn = new Scanner(System.in);
        int processorCount = sysIn.nextInt();
        int[] processors = new int[processorCount];
        for ( int i = 0; i < processorCount; i++ ){
            processors[i] = sysIn.nextInt();
        }

        System.out.println(new Procesor(processors).optimizeProcessorCount());

    }

    private class ProcOptState {
        private boolean isLastOdd;
        private int lastProcessorSize;
        private int processorCount;

        public ProcOptState(boolean isLastOdd, int lastProcessorSize, int processorCount){
            this.isLastOdd = isLastOdd;
            this.lastProcessorSize = lastProcessorSize;
            this.processorCount = processorCount;
        }

        @Override
        public boolean equals(Object o){
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;
            ProcOptState that = (ProcOptState) o;
            return lastProcessorSize == that.lastProcessorSize;
        }

        @Override
        public int hashCode(){
            return Objects.hash(lastProcessorSize);
        }

        @Override
        public String toString(){
            return "ProcOptState{" +
                    "lastProcessorSize=" + lastProcessorSize +
                    ", processorCount=" + processorCount +
                    '}';
        }
    }
}
