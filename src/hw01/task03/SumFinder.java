package hw01.task03;

import java.util.HashMap;
import java.util.Scanner;

public class SumFinder {

    private int[] input;
    private HashMap<Long, Tuple> sumToIndexPair = new HashMap<>();

    public SumFinder(int[] input){
        this.input = input;
        fillMap();
    }

    private void fillMap(){
        for ( int i = 0; i < input.length; i++ ){
            for ( int j = 0; j < input.length; j++ ){
                sumToIndexPair.put(0l + input[i] + input[j], new Tuple(i, j));
            }
        }
    }

    private int[] getAddends(int sum){
        for ( int i = 0; i < input.length; i++ ){
            for ( int j = 0; j < input.length; j++){
                Tuple matchingIndexPair = sumToIndexPair.get(0l - input[i] - input[j] + sum);
                if (  matchingIndexPair != null ){
                    return new int[]{i, j, matchingIndexPair.i, matchingIndexPair.j};
                }
            }
        }
        return null;
    }

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        int n = sysIn.nextInt();
        int sum = sysIn.nextInt();
        int[] input = new int[n];
        for ( int i = 0; i < n; i++ ){
            input[i] = sysIn.nextInt();
        }
//        int[] indices = new SumFinder(SequenceGenerator.generateSequence(2000)).getAddends(-388888888);
        int[] indices = new SumFinder(input).getAddends(sum);
        if ( indices == null ){
            System.out.println("NE");
        } else {
            System.out.format("DA%n%d %d %d %d%n", indices[0], indices[1], indices[2], indices[3]);
        }
    }

    private static class Tuple{
        private int i, j;
        private Tuple(int i, int j){
            this.i = i;
            this.j = j;
        }
    }
}
