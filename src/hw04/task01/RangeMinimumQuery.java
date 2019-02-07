package hw04.task01;

import java.util.Scanner;

public class RangeMinimumQuery {

    SegmentTree tree;

    public RangeMinimumQuery(int[] leaves){
        tree = new SegmentTree(leaves, (i, j) -> Integer.min(i, j), Integer.MAX_VALUE);
    }

    private void printMinimums(int[][] intervals){
        for ( int[] interval : intervals){
            System.out.println(tree.queryRange(interval[0], interval[1]));
        }
    }

    public static void main(String[] args){

        Scanner sysIn = new Scanner(System.in);
        int[] leaves = new int[sysIn.nextInt()];
        for ( int i = 0; i < leaves.length; i++ ){
            leaves[i] = sysIn.nextInt();
        }

        int[][] intervals = new int[sysIn.nextInt()][];
        for ( int i = 0; i < intervals.length; i++ ){
            intervals[i] = new int[]{sysIn.nextInt(), sysIn.nextInt()};
        }

        new RangeMinimumQuery(leaves).printMinimums(intervals);
    }

    private class SegmentTree{
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

        private int getTreeSize(int[] leaves){
            return 2 << ( 33 - Integer.numberOfLeadingZeros(leaves.length)) - 1;
        }
    }

    private interface IntBiFunction{
        public int apply(int i, int j);
    }

}
