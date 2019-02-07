package hw04.task03;

import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

public class Query {

    long[] bit;
    int[] sequence;
    TreeSet<Integer> activeIndices = new TreeSet<>();
    public Query(int[] sequence){
        this.sequence = sequence;
        for ( int i = 1; i < sequence.length; i++ ){
            activeIndices.add(i);
        }
        bit = new long[sequence.length];
        for ( int i = 1; i < sequence.length; i++ ){
            update(i, sequence[i]);
        }
    }

    private void update(int index, long value){
        while ( index < sequence.length ){
            bit[index] += value;
            index += index & (-index);
        }
    }

    private long query(int index){
        long value = 0;
        while ( index > 0 ){
            value += bit[index];
            index -= index & (-index);
        }
        return value;
    }

    public void apply(int[][] queries){
        for ( int[] request : queries ){
            switch ( request[0] ){
                case 0:
                    Iterator<Integer> activeIndicesIterator =
                            activeIndices.tailSet(request[1]).iterator();
                    int index;
                    while ( activeIndicesIterator.hasNext() && (index = activeIndicesIterator.next()) <= request[2] ) {
                        int newValue = squareRootFloor(sequence[index]);
                        update(index, -sequence[index] + newValue);
                        sequence[index] = newValue;
                        if ( newValue == 1 ){
                            activeIndicesIterator.remove();
                        }
                    }
                    break;
                case 1:
                    System.out.println(query(request[2]) - (request[1] == 0 ? 0 : query(request[1] - 1)));
                    break;
            }
        }
    }

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        int[] sequence = new int[sysIn.nextInt() + 1];
        for ( int i = 1; i < sequence.length; i++ ){
            sequence[i] = sysIn.nextInt();
        }
        int[][] queries = new int[sysIn.nextInt()][];
        for ( int i = 0; i < queries.length; i++ ){
            queries[i] = new int[]{sysIn.nextInt(), sysIn.nextInt(), sysIn.nextInt()};
        }

        new Query(sequence).apply(queries);
    }


    private static int squareRootFloor(int num){
        int start = 1, end = num, root=0;
        while (start <= end)
        {
            int mid = (start + end) / 2;
            int pow = mid * mid;

            if (pow == num)
                return mid;

            if (pow < num)
            {
                start = mid + 1;
                root = mid;
            }
            else
                end = mid-1;
        }
        return root;
    }

    private static int pow(int base, int power) {
        int re = 1;
        while (power > 0) {
            if ((power & 1) == 1) {
                re *= base;
            }
            power >>= 1;
            base *= base;
        }
        return re;
    }

}
