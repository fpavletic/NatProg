package hw_christmas.task04;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class DestructiveDevelopment {

    public static void main(String[] args){

        Scanner sysIn = new Scanner(System.in);
        int lightCount = Integer.parseInt(sysIn.nextLine());
        Node[] lightsA = buildTree(sysIn, lightCount), lightsB = buildTree(sysIn, lightCount);
        buildAncestors(lightsA[0], lightCount);
        buildAncestors(lightsB[0], lightCount);
        StringBuilder sb = new StringBuilder(lightCount * 2);
        for ( int i = 0; i < lightCount; i++ ){
            int sum = 0;
            for ( int j = 0; j < lightsA[i].ancestors.length; j++ ){
                sum += Long.bitCount(lightsA[i].ancestors[j] & lightsB[i].ancestors[j]);
            }
            sb.append(sum);
            sb.append(' ');
        }
        System.out.println(sb.toString().trim());
    }

    private static Node[] buildTree(Scanner sysIn, int treeSize){
        Node[] tree = new Node[treeSize];
        for ( int i = 0; i < tree.length; i++ ){
            tree[i] = new Node();
        }
        for ( int i = 1; i < tree.length; i++ ){
            int light1 = sysIn.nextInt() - 1, light2 = sysIn.nextInt() - 1;
            tree[light1].children.add(tree[light2]);
            tree[light2].children.add(tree[light1]);
        }

        Node.lastId = -1;
        return tree;
    }

    private static void buildAncestors(Node current, int nodeCount){
        current.ancestors = new long[nodeCount / 64 + 1];
        for ( Node child : current.children ){
            buildAncestors(child, current);
        }
    }

    private static void buildAncestors(Node current, Node previous){
        current.ancestors = Arrays.copyOf(previous.ancestors, previous.ancestors.length);
        int index = previous.id / 64;
        current.ancestors[index] |= 1 << (previous.id - index * 64);
        for ( Node child : current.children ){
            if ( child == previous ) continue;
            buildAncestors(child, current);
        }
    }

    // Sugestija
//    private static void buildAncestors(Node current, Node previous){
//        current.ancestors = Arrays.copyOf(previous.ancestors, previous.ancestors.length);
//        int index = previous.id / 64;
//        current.ancestors[index] |= 1 << (previous.id - index * 64);
//        for ( Node child : current.children ){
//            if ( child == previous ) continue;
//            buildAncestors(child, current);
//        }
//        current.ancestors[index] &= ~(1 << (previous.id - index * 64));
//    }

    private static class Node{
        private static int lastId = -1;
        private long[] ancestors;
        private int id;
        private List<Node> children = new LinkedList<>();

        public Node(){
            id = ++lastId;
        }
    }

}
