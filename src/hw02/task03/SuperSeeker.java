package hw02.task03;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class SuperSeeker {

    private static final int[][] digitToTransitions = new int[][]{
            new int[]{2, 3, 4, 5, 6, 7, 8, 9},
            new int[]{3, 4, 5 ,6 ,7 ,8 ,9},
            new int[]{0, 4, 5, 6 ,7 ,8 ,9},
            new int[]{0 ,1, 5, 6, 7 ,8 ,9},
            new int[]{0 ,1 ,2, 6, 7, 8 ,9},
            new int[]{0 ,1 ,2 ,3, 7, 8, 9},
            new int[]{0 ,1 ,2 ,3 ,4, 8, 9},
            new int[]{0, 1 ,2 ,3 ,4 ,5, 9},
            new int[]{0, 1, 2 ,3 ,4 ,5 ,6},
            new int[]{0, 1, 2 ,3 ,4 ,5 ,6, 7},
            new int[]{10, 1, 2 ,3 ,4 ,5 ,6, 7, 8, 9} //leading zero state
    };

    private int minLen, maxLen;
    private char[] min, max;
    private Map<State, Integer> stateToCount = new HashMap<>();

    public SuperSeeker(char[] min, char[] max){
        this.minLen = min.length;
        this.maxLen = max.length;
        this.min = min;
        this.max = max;
    }

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        char[] min = sysIn.next().toCharArray();
        char[] max = sysIn.next().toCharArray();
        System.out.println(new SuperSeeker(min, max).seek());
    }

    private int seek(){
        int max = getPerms(new State(10, maxLen, false), this.max);
        stateToCount.clear();
        int min = getPerms(new State(10, minLen, false), this.min);
        return max > min ? max - min : max - min + 10000;
    }

    public int getPerms(State state, char[] num){

        int comparison = state.lesser ? -1 : // if we already know our number is lesser than the provided one
                state.digitsLeft == num.length ? 0 : // if we've just started to build our number
                        Integer.compare(state.digit == 10 ? 0 : state.digit, num[num.length - state.digitsLeft - 1] - 48);

        //It is of utmost importance that the following two ifs are in the order they are in!
        if ( comparison > 0 ){
            return 0;
        }

        if ( state.digitsLeft == 0 ){
            return 1;
        }

        if ( stateToCount.containsKey(state)){
            return stateToCount.get(state);
        }

        int ans = Arrays.stream(digitToTransitions[state.digit])
                .map(i -> getPerms(new State(i, state.digitsLeft - 1, comparison < 0), num))
                .sum() % 10000;
        stateToCount.put(state, ans);
        return ans;
    }

    private class State{
        final private int digit;
        final private int digitsLeft;
        final private boolean lesser;

        public State(int digit, int digitsLeft, boolean lesser){
            this.digit = digit;
            this.digitsLeft = digitsLeft;
            this.lesser = lesser;
        }

        @Override
        public boolean equals(Object o){
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;
            State state = (State) o;
            return digit == state.digit &&
                    digitsLeft == state.digitsLeft &&
                    lesser == state.lesser;
        }

        @Override
        public int hashCode(){
            return Objects.hash(digit, digitsLeft, lesser);
        }
    }

}
