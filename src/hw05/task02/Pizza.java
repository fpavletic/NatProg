package hw05.task02;

import java.util.*;

public class Pizza {

    private static final int LEVEL = 18;

    private Node[] nodes;
    private int[][] parents;

    public Pizza(Node[] nodes, int rootId){
        this.nodes = nodes;
        parents = new int[nodes.length][LEVEL];
        dfs(nodes[rootId], null);
        precomputeParents();
    }

    private final void dfs (Node current, Node previous){
        if (previous == null){
            current.depth = 1;
        } else {
            current.depth = previous.depth + 1;
            parents[current.id][0] = previous.id;
        }
        for ( Node child : current.children){
            dfs(child, current);
        }
    }

    private final void precomputeParents(){
        for ( int i = 1; i < LEVEL; i++ ){
            for ( int nodeId = 1; nodeId < nodes.length; nodeId++ ){
                if ( parents[nodeId][i - 1] != 0 ){
                    parents[nodeId][i] = parents[parents[nodeId][i-1]][i-1];
                }
            }
        }
    }

    private int getLca(int nodeId1, int nodeId2){
        if ( nodes[nodeId1].depth > nodes[nodeId2].depth ){
            nodeId1 ^= nodeId2;
            nodeId2 ^= nodeId1;
            nodeId1 ^= nodeId2;
        }

        int dif = nodes[nodeId2].depth - nodes[nodeId1].depth;

        for ( int i = 0; i < LEVEL; i++ ){
            if ((( dif >> i ) & 1) != 0){
                nodeId2 = parents[nodeId2][i];
            }
        }

        if ( nodeId1 == nodeId2 ){
            return nodeId1;
        }

        for ( int i = LEVEL - 1; i >= 0; i-- ){
            if ( parents[nodeId1][i] != parents[nodeId2][i]){
                nodeId1 = parents[nodeId1][i];
                nodeId2 = parents[nodeId2][i];
            }
        }
        return parents[nodeId1][0];
    }

    public int findMaxTip(int nodeId1, int nodeId2){
        Integer lca = getLca(nodeId1, nodeId2);
        return Math.max(
            findMaxTipBetweenChildAndAncestor(nodeId1, lca),
            findMaxTipBetweenChildAndAncestor(nodeId2, lca)
        );
    }

    private int findMaxTipBetweenChildAndAncestor(int child, int ancestor){
        int maxTip = Integer.MIN_VALUE;
        for ( int  i = nodes[child].depth; i >= nodes[ancestor].depth; i-- ){
            maxTip = maxTip > nodes[child].tip ? maxTip : nodes[child].tip;
            child = nodes[child].parentId;
        }
        return maxTip;
    }

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);

        Node[] nodes = new Node[sysIn.nextInt() + 1];
        int queryCount = sysIn.nextInt();

        for ( int i = 1; i < nodes.length; i++ ) {
            nodes[i] = new Node(sysIn.nextInt(), sysIn.nextInt());
        }

        int rootId = -1;
        for ( int i = 1; i < nodes.length; i++ ){
            Node parent = nodes[nodes[i].parentId];
            if ( parent.id == nodes[i].id){
                rootId = i;
            } else {
                parent.children.add(nodes[i]);
            }
        }

        Pizza pizza = new Pizza(nodes, rootId);

        for ( int i = 0; i < queryCount; i++ ){
            System.out.println(pizza.findMaxTip(sysIn.nextInt(), sysIn.nextInt()));
        }

    }

    private static class Node{
        private static int lastId = 0;
        private int id;
        private int parentId;
        private List<Node> children = new ArrayList<>();
        private int depth;
        private int tip;

        public Node(int parentId, int tip){
            id = ++lastId;
            this.parentId = parentId;
            this.tip = tip;
        }
    }
}
