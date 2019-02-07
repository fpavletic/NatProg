package codeforces.jan13.task2;

import java.util.Scanner;

public class BuildAContest {

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        int difficultyCount = sysIn.nextInt();
        int problemCount = sysIn.nextInt();
        int[] problems = new int[difficultyCount];
        SegmentTree problemsTree = new SegmentTree(new int[difficultyCount], Math::min, 0);
        int contestCount = 0;
        int difficulty;
        StringBuilder output = new StringBuilder();
        for ( int i = 0; i < problemCount; i++ ){
            difficulty = sysIn.nextInt() - 1;
            problemsTree.update(0, difficulty, ++problems[difficulty]);
            if ( problemsTree.queryRange(0, difficultyCount) > contestCount){
                ++contestCount;
                output.append(1);
            } else {
                output.append(0);
            }
        }
        System.out.println(output);
    }




    private static class SegmentTree{
        private int[] nodes;
        private int[][] ranges;
        private IntBiFunction biFunction;
        private int defaultValue;

        public SegmentTree(int[] leaves, IntBiFunction biFunction, int defaultValue){
            nodes = new int[getTreeSize(leaves)];
            ranges = new int[nodes.length][];
            this.biFunction = biFunction;
            this.defaultValue = defaultValue;
            buildTree(leaves);
        }

        private void buildTree(int[] leaves){
            buildTree(leaves, 0, 0, leaves.length -1);
        }

        private int buildTree(int[] leaves, int index, int start, int end){
            ranges[index] = new int[]{start, end};
            if ( start == end ){
                nodes[index] = leaves[start];
            } else {
                int middle = start + ( end - start ) / 2;
                int newIndex = index * 2 + 1;
                nodes[index] = biFunction.apply(
                        buildTree(leaves, newIndex, start, middle),
                        buildTree(leaves, newIndex + 1, middle + 1, end)
                );
            }
            return nodes[index];
        }

        public int queryRange(int i, int j){
            return queryRange(0, i, j);
        }

        private int queryRange(int index, int i, int j){
            if ( index > ranges.length ){
                return defaultValue;
            }
            if ( i <= ranges[index][0] && j >= ranges[index][1] ) {
                return nodes[index];
            }
            if ( i > ranges[index][1] || j < ranges[index][0]) {
                return defaultValue;
            }
            return biFunction.apply(queryRange(index * 2 + 1, i, j), queryRange(index * 2 + 2, i, j)); //half and half
        }

        private void update(int index, int leafIndex, int newValue){
            if ( leafIndex < ranges[index][0] || leafIndex > ranges[index][1] )
                return;

            if ( ranges[index][0] == ranges[index][1] ){
                nodes[index] = newValue;
                return;
            }

            int middle = ranges[index][0] + (ranges[index][1] - ranges[index][0]) / 2;
            if ( leafIndex <= middle ){
                update(2 * index + 1, leafIndex, newValue);
            } else {
                update(2 * index + 2, leafIndex, newValue);
            }
            nodes[index] = biFunction.apply(nodes[2 * index + 1], nodes[2 * index + 2]);
        }

        private int getTreeSize(int[] leaves){
            return 2 << ( 33 - Integer.numberOfLeadingZeros(leaves.length)) - 1;
        }
    }

    private interface IntBiFunction{
        public int apply(int i, int j);
    }

}
