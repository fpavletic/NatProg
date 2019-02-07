package hw03.task02;

import java.util.Scanner;
import java.util.Stack;

public class Bruno {

    private int[] vertexToGroupHead;
    private int[] size;
    private int[][] edges;
    private int[] buildOrder;

    public Bruno(int vertexCount, int[][] edges, int[] buildOrder){
        vertexToGroupHead = new int[vertexCount];
        size = new int[vertexCount];
        this.edges = edges;
        this.buildOrder = buildOrder;
    }

    public void printGroupCounts(){
        for ( int i = 0; i < vertexToGroupHead.length; i++ ){
            vertexToGroupHead[i] = i;
            size[i] = 1;
        }

        Stack<Integer> groupCountAtStep = new Stack<>();

        for ( int groupCount = vertexToGroupHead.length, i = 0; i < buildOrder.length; i++ ){
            groupCountAtStep.push(groupCount);
            int[] edge = edges[buildOrder[i]];
            int head1 = find(edge[0]);
            int head2 = find(edge[1]);
            if ( head1 != head2 ){
                union(head1, head2);
                groupCount--;
            }
        }

        while ( !groupCountAtStep.empty() )
            System.out.println(groupCountAtStep.pop());
    }

    private void union(int v1, int v2){
        if (size[v1] < size[v2]) {
            vertexToGroupHead[v1] = vertexToGroupHead[v2];
            size[v2] += size[v1];
        } else {
            vertexToGroupHead[v2] = vertexToGroupHead[v1];
            size[v1] += size[v2];
        }
    }

    private int find(int v){
        while ( vertexToGroupHead[v] != v){
            vertexToGroupHead[v] = vertexToGroupHead[vertexToGroupHead[v]];
            v = vertexToGroupHead[v];
        }
        return v;
    }

    public static void main(String[] args){

        Scanner sysIn = new Scanner(System.in);
        int vertexCount = sysIn.nextInt();
        int edgeCount = sysIn.nextInt();
        int[][] edges = new int[edgeCount][];
        for ( int i = 0; i < edgeCount; i++ ){
            edges[i] = new int[]{sysIn.nextInt() -1, sysIn.nextInt() -1};
        }
        int[] buildOrder = new int[edgeCount];
        for ( int i = edgeCount - 1; i >= 0; i-- ){
            buildOrder[i] = sysIn.nextInt() -1;
        }

        new Bruno(vertexCount, edges, buildOrder).printGroupCounts();

    }

}
