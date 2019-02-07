package hw04.task02;
import java.util.Scanner;

public class IncreasingSubsequences {

    private static final long MODULUS_VALUE = 5000000l;

    int[] sequence;
    long[][] bits = new long[51][100000+10];

    public IncreasingSubsequences(int[] sequence){
        this.sequence = sequence;
    }

    public long countSubSequencesOfLen(int subsequenceLength){
        long count = 0;
        for ( int i = 1; i < sequence.length; i++){
            for ( int j = 1; j <= subsequenceLength; j++ ){
                long prefix =  j == 1 ? 1 : query(sequence[i] - 1, j - 1);
                update(sequence[i], j, prefix);
                if ( j == subsequenceLength ){
                    count = ( count + prefix ) % MODULUS_VALUE;
                }
            }
        }
        return count;
    }

    private void update(int index, int i, long value){
        while ( index <= 100005 ){
            bits[i][index] = (bits[i][index] + value) % MODULUS_VALUE;
            index += index & (-index);
        }
    }

    private long query(int index, int i){
        long sum = 0;
        while ( index > 0 ){
            sum = (sum + bits[i][index]) % MODULUS_VALUE;
            index -= index &(-index);
        }
        return sum;
    }

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        int[] sequence = new int[sysIn.nextInt() + 1];
        int subsequenceLength = sysIn.nextInt();

        for ( int i = 1; i < sequence.length; i++ ){
            sequence[i] = sysIn.nextInt() + 1;
        }

        System.out.println(new IncreasingSubsequences(sequence).countSubSequencesOfLen(subsequenceLength));
    }
}