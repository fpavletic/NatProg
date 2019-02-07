package hw05.task01;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Labirint {

    private Node root;
    private int longestPathInTree = 0;

    public Labirint(Node root){
        this.root = root;
    }

    public int findLongestPath(){
        findLongestPath(root);
        return longestPathInTree + 2;
    }

    private int findLongestPath(Node current){
        for ( Node child : current.children ){

            int childDeepestSubTree = findLongestPath(child);
            if ( childDeepestSubTree > current.deepestSubTree ){
                current.secondDeepestSubTree = current.deepestSubTree;
                current.deepestSubTree = childDeepestSubTree;
            } else if ( childDeepestSubTree > current.secondDeepestSubTree ){
                current.secondDeepestSubTree = childDeepestSubTree;
            }
        }

        int longestPathInSubtree = current.secondDeepestSubTree + current.deepestSubTree;
        if ( longestPathInSubtree > longestPathInTree ){
            longestPathInTree  = longestPathInSubtree;
        }

        return current.deepestSubTree + 1;
    }

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        Node[] nodes = new Node[sysIn.nextInt()];
        for ( int i = 0; i < nodes.length; i++ ){
            nodes[i] = new Node();
        }
        for ( int i = 1; i < nodes.length; i++ ){
            int i1 = sysIn.nextInt() - 1, i2 = sysIn.nextInt() - 1;
            nodes[Math.min(i1, i2)].children.add(nodes[Math.max(i1, i2)]);
        }
        System.out.println(new Labirint(nodes[0]).findLongestPath());

    }

    private static class Node{

        private static int lastId = 0;

        private int id = ++lastId;
        private int deepestSubTree = 0;
        private int secondDeepestSubTree = 0;
        private List<Node> children = new ArrayList<>();

        @Override
        public String toString(){
            return "Node{" +
                    "id=" + id +
                    '}';
        }
    }

}
