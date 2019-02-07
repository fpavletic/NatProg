package hw_christmas.task01;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ArnoldsArrangement {

    public static void main(String[] args){

        Scanner sysIn = new Scanner(System.in);
        sysIn.nextLine();
        Node[] lights = sysIn.nextLine().chars().mapToObj(Node::new).toArray(Node[]::new);
        for ( int i = 1; i < lights.length; i++ ){
            int light1 = sysIn.nextInt() - 1, light2 = sysIn.nextInt() - 1;
            lights[light1].children.add(lights[light2]);
            lights[light2].children.add(lights[light1]);
        }

//        System.out.println(getPunchCount(false, lights[0], null));
        System.out.println(getPunchCountIterative(lights[0]));
    }

    private static int getPunchCountIterative(Node root){
        Deque<Node> stack = new LinkedList<>(){{push(root);}};
        int punchCount = 0;
        while ( !stack.isEmpty() ){
            Node node = stack.pop();
            boolean doPunch = !(node.isLightOn ^ node.isFlipped);
            if ( doPunch ) {
                punchCount++;
            }
            for ( Node child : node.children ){
                if ( child == node.parent ) continue;
                child.isFlipped = doPunch ^ node.isFlipped;
                child.parent = node;
                stack.push(child);
            }
        }
        return punchCount;
    }

    private static class Node{
        private Node parent;
        private boolean isFlipped;
        private boolean isLightOn;
        private List<Node> children = new LinkedList<>();
        public Node(int isOn){
            this.isLightOn = isOn == 49;
        }
    }

}
