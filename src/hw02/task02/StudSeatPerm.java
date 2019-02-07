package hw02.task02;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class StudSeatPerm {

    /**
     *  hasObstacle[row][column]
     */
    private boolean[][] hasObstacle;
    private int studentCount;
    private int columnCount;

    private Map<State, Integer> stateToPermutationCount = new HashMap<>();

    public StudSeatPerm(boolean[][] hasObstacle, int studentCount, int columnCount){
        this.studentCount = studentCount;
        this.hasObstacle = hasObstacle;
        this.columnCount = columnCount;
    }

    private int getPermutationCount(){
        return permutationStep(new State(
                columnCount -1,
                studentCount,
                true,
                true
        ));
    }

    private int permutationStep(State state){


        if ( state.studentCount == 0 ){
            return 1;
        }

        if ( state.column < 0 ){
            return 0;
        }

        {
            Integer count = stateToPermutationCount.get(state);
            if ( count != null ) {
                return count;
            }
        }

        int count = 0;

        count = permutationStep(new State(
                    state.column - 1,
                    state.studentCount,
                    true,
                    true)
            ) % 1000007;

        if ( isCanSit(state.canSitTop, state.column, 0)){
            count =( count + permutationStep(new State(
                    state.column - 1,
                    state.studentCount - 1,
                    false,
                    true
            ))) % 1000007;
        }

        if ( isCanSit(state.canSitBottom, state.column, 1)){
            count =( count + permutationStep(new State(
                    state.column - 1,
                    state.studentCount - 1,
                    true,
                    false
            ))) % 1000007;
        }

        stateToPermutationCount.put(state, count);
        return count;
    }

    private boolean isCanSit(boolean notHasAdjecent, int i, int j){
        return notHasAdjecent && !hasObstacle[j][i];
    }

    private class State{
        private final int column;
        private final int studentCount;
        private final boolean canSitTop;
        private final boolean canSitBottom;

        public State(int column, int studentCount, boolean canSitTop, boolean canSitBottom){
            this.column = column;
            this.studentCount = studentCount;
            this.canSitTop = canSitTop;
            this.canSitBottom = canSitBottom;
        }

        @Override
        public boolean equals(Object o){
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;
            State state = (State) o;
            return column == state.column &&
                    studentCount == state.studentCount &&
                    canSitTop == state.canSitTop &&
                    canSitBottom == state.canSitBottom;
        }

        @Override
        public int hashCode(){
            return Objects.hash(column, studentCount, canSitTop, canSitBottom);
        }
    }

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        int n = sysIn.nextInt();
        int k = sysIn.nextInt();
        boolean[][] hasObstacle = new boolean[2][n];
        sysIn.nextLine();

        for ( int i = 0, j = 0; i < 2; i++ , j = 0){
            for ( char c : sysIn.nextLine().toCharArray() ){
                hasObstacle [i][j++] = c == '#';
            }
        }

        System.out.println(new StudSeatPerm(hasObstacle, k, n).getPermutationCount());
    }

}
