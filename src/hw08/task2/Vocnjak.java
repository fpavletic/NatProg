package hw08.task2;

import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.function.BiFunction;

//Task: Find the number of tagged ( sprayed ), overtagged ( destroyed ) and untagged ( unsprayed ) elements
//Solution is implemented using the line sweep algorithm
public class Vocnjak {

    private static final BiFunction<MutableInteger, MutableInteger, MutableInteger> MERGER = (i1, i2) -> i1.add(i2.value);

    public static void main(String[] args){

        TreeMap<Long, MutableInteger> eventPositionToValue = new TreeMap<>();
        Scanner sysIn = new Scanner(System.in);
        long x, r, n = sysIn.nextLong();
        int b = sysIn.nextInt(), k = sysIn.nextInt();

        MutableInteger v = new MutableInteger();
        for ( int i = 0; i < b; i++ ){
            x = sysIn.nextLong() - 1;
            r = sysIn.nextLong();
            if ( v == eventPositionToValue.merge(x - r, v.set(1), MERGER)){
                v = new MutableInteger();
            }
            if ( v == eventPositionToValue.merge(x, v.set(-1), MERGER)){
                v = new MutableInteger();
            }
            if ( v == eventPositionToValue.merge(x + 1, v.set(1), MERGER)){
                v = new MutableInteger();
            }
            if ( v == eventPositionToValue.merge(x + r + 1, v.set(-1), MERGER)){
                v = new MutableInteger();
            }
        }

        long sprayed = 0, destroyed = 0, last = 0;
        int currentSprayCount = 0;
        for ( Map.Entry<Long, MutableInteger> entry : eventPositionToValue.entrySet()){
            long position = entry.getKey();
            position = position >= n ? n : position;
            if ( position > 0L ) {
                if ( currentSprayCount > k ) {
                    destroyed += position - last;
                } else if ( currentSprayCount > 0l ) {
                    sprayed += position - last ;
                }
                last = position;
            }
            currentSprayCount += entry.getValue().value;
        }
        System.out.println(sprayed);
        System.out.println(destroyed);
        System.out.println(n - sprayed - destroyed);
    }

    private static class MutableInteger {
        int value;

        public MutableInteger add(int value){
            this.value += value;
            return this;
        }

        public MutableInteger set(int value){
            this.value = value;
            return this;
        }
    }

}
