package hw01.task03;

import java.util.Random;

public class SequenceGenerator {

    public static int[] generateSequence ( int n ){
        int[] input = new int[n];
        Random rand = new Random();
        for ( int i = 0; i < n; i++ ){
            input[i] = rand.nextInt( 200000000 ) - 100000000;
        }
        return input;
    }
}
