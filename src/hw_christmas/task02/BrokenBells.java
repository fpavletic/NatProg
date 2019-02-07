package hw_christmas.task02;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class BrokenBells {

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        /**
         * [0] -> input/output; [1] -> processors;
         */
        Processor[][] processors = new Processor[2][];
        processors[0] = new Processor[sysIn.nextInt()];
        processors[1] = new Processor[sysIn.nextInt()];
        long iterations = sysIn.nextLong();
        int mod = sysIn.nextInt();

        int inputCount = sysIn.nextInt();
        int outputCount = sysIn.nextInt();

        buildProcessors(sysIn, inputCount, processors[1]);
        buildProcessors(sysIn, outputCount, processors[0]);

        for ( int i = 0; i < processors[0].length; i++ ){
            processors[0][i].value = sysIn.nextInt();
        }

        List<long[]> outputs = new LinkedList<>();

        int oldOutputIndex;
        for ( int i = 0; i < iterations; i++ ){
            for ( int j = 0; j < processors[1].length; j++ ){
                processors[1][j].calculateNewValue(processors[0], mod);
            }
            long [] output = new long[processors[0].length];
            for ( int j = 0; j < processors[0].length; j++ ){
                output[j] = processors[0][j].calculateNewValue(processors[1], mod);
            }

            oldOutputIndex = 0;
            Iterator<long[]> oldOutputIter = outputs.iterator();
            while ( oldOutputIter.hasNext() ){
                if ( Arrays.equals(output, oldOutputIter.next())){
                    iterations = i + ( iterations - oldOutputIndex ) % ( i - oldOutputIndex );
                    break;
                }
                oldOutputIndex++;
            }
            outputs.add(output);
        }
        Arrays.stream(processors[0]).forEach(System.out::println);
    }

    private static void buildProcessors(Scanner sysIn, int linkCount, Processor[] processors){
        int inputIndex, inputWeight, processorIndex;
        for ( int i = 0; i < linkCount; i++ ){
            inputIndex = sysIn.nextInt() - 1;
            processorIndex = sysIn.nextInt() - 1;
            inputWeight = sysIn.nextInt();
            if ( processors[processorIndex] == null ){
                processors[processorIndex] = new Processor();
            }
            processors[processorIndex].inputIndices.add(inputIndex);
            processors[processorIndex].inputWeights.add(inputWeight);
        }
    }

    private static class Processor {
        private long value = 0;
        private List<Integer> inputIndices = new ArrayList<>();
        private List<Integer> inputWeights = new ArrayList<>();

        public long calculateNewValue(Processor[] processor, int mod){
            value = 0;
            for ( int i = 0; i < inputIndices.size(); i++ ){
                value = ( value + processor[inputIndices.get(i)].value * inputWeights.get(i)) % mod;
            }
            return value;
        }
        public String toString(){
            return "" + value;
        }
    }
}