package hw08.task1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Stack;
import java.util.TreeMap;

//IMPORTANT: The idea behind this solution is correct, HOWEVER I messed something up while
//implementing it so it does NOT currently work.

//Task: Find the circumference of the convex shape containing all of the provided points
//Solution implements the Graham scan algorithm, you can read more about it here:
//https://www.geeksforgeeks.org/convex-hull-set-2-graham-scan/
public class Gusari {

    public static void main(String[] args) throws FileNotFoundException{

        System.setIn(new FileInputStream("./src/hw08/task1/input.txt"));
        TreeMap<Double, Pair> polarAngleToPoint = new TreeMap<>();
        Scanner sysIn = new Scanner(System.in);
        Pair[] points = new Pair[sysIn.nextInt()];
        Pair startingPoint = null;
        for ( int i = points.length - 1; i >= 0; i-- ){
            points[i] = new Pair(sysIn.nextInt(), 0);
        }

        for ( int i = points.length -1; i >= 0; i-- ){
            points[i].y = sysIn.nextInt();
            if ( startingPoint == null || startingPoint.compareTo(points[i]) > 0){
                startingPoint = points[i];
            }
        }

        double polarAngle;
        for ( Pair point : points ){
            if ( point == startingPoint ) continue;
            polarAngle = startingPoint.getPolarAngle(point);
            polarAngleToPoint.merge(polarAngle < 0 ? polarAngle + Math.PI : polarAngle, point, (p1, p2) -> p1.x - p2.x < 0 ? p2 : p1);
        }

        Stack<Pair> acceptedPoints = new Stack<>();
        acceptedPoints.push(startingPoint);
        Iterator<Pair> pointsIterator = polarAngleToPoint.values().iterator();
        Pair previous = startingPoint, current;

        for ( int i = 0; i < 2; i++ ){
            current = pointsIterator.next();
            current.previous = previous;
            acceptedPoints.push(current);
            previous = current;
        }

        while ( pointsIterator.hasNext() ){
            current = pointsIterator.next();
            while ( previous.previous != null && !isCcw(previous.previous, previous, current)){
                acceptedPoints.pop();
                previous = acceptedPoints.peek();
            }
            current.previous = previous;
            acceptedPoints.push(current);
            previous = acceptedPoints.peek();
        }

        double circumference = 0;
        Pair last = acceptedPoints.peek();
        while ( !acceptedPoints.empty() ){
            current = acceptedPoints.pop();
            circumference += current.getDistance(current.previous == null ? last : current.previous);
        }
        System.out.format("%.0f", Math.floor(circumference));
    }

    private static boolean isCcw(Pair p, Pair q, Pair r){
        return 0 > (q.y - p.y) * (r.x - q.x) -
                (q.x - p.x) * (r.y - q.y);
    }

    private static class Pair implements Comparable<Pair>{
        int x, y;
        Pair previous;

        public Pair(int x, int y){
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Pair o){
            return y == o.y ? x - o.x : y - o.y;
        }

        double getPolarAngle(Pair o){
            return Math.atan((o.y - y) * 1d / (o.x - x));
        }

        double getDistance(Pair o){
            return Math.sqrt((x - o.x) * ( x - o.x) + (y - o.y) * (y - o.y));
        }
    }
}
