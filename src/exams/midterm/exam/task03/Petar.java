package exams.midterm.exam.task03;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

public class Petar {
    private PriorityQueue<Point> actives;
    private List<Point> backups;
    private boolean isUsedAxe = false;
    private Set<Point> visited = new HashSet<>();
    private char[][] matrix;
    private int goalX, goalY;

    public Petar(char[][] matrix, int startX, int startY, int goalX, int goalY){
        actives = new PriorityQueue<>((p1, p2) -> p1.cost + p1.heuristic > p2.cost + p2.heuristic ? 1 : -1);
        backups = new LinkedList<>();
        actives.add(new Point(startX, startY, 0, getHeuristic(startX, startY, goalX, goalY)));
        this.matrix = matrix;
        this.goalX = goalX;
        this.goalY = goalY;
    }

    private String getSteps(){

        while ( !actives.isEmpty() || !backups.isEmpty()){
            if ( actives.isEmpty() ){
                actives.addAll(backups);
                backups.clear();
                isUsedAxe = true;
            }
            Point active = actives.poll();
            if ( active.x == goalX && active.y == goalY){
                return String.format("%s%n%d", isUsedAxe ? "Moguce" : "Lagano", active.cost);
            }
            visited.add(active);
            expandActive(active);
        }
        return "Nije moguce";

    }

    private void expandActive(Point active){
        int cost = active.cost + 1;
        for ( int i = 0; i < 4; i++ ){
            int x = active.x + (i == 0 || i == 2 ? i - 1 : 0);
            if ( x < 0 || x >= matrix.length ) continue;
            int y = active.y + (i == 1 || i == 3 ? i - 2 : 0);
            if ( y < 0 || y >= matrix.length ) continue;
            Point newPoint = new Point(x, y, cost, getHeuristic(x, y, goalX, goalY));
            if ( visited.contains(newPoint) || matrix[newPoint.y][newPoint.x] == '#' )
                continue;
            if ( isUsedAxe || matrix[newPoint.y][newPoint.x] == '.'){
                actives.add(newPoint);
            } else {
                backups.add(newPoint);
            }
        }
    }

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        int matrixSize = sysIn.nextInt();
        char[][] matrix = new char[matrixSize][];
        int startY = sysIn.nextInt() - 1, startX = sysIn.nextInt() - 1;
        int goalY = sysIn.nextInt() - 1, goalX = sysIn.nextInt() - 1;
        sysIn.nextLine();
        for ( int i = 0; i < matrixSize; i++ ){
            matrix[i] = sysIn.nextLine().toCharArray();
        }
        System.out.println(new Petar(matrix, startX, startY, goalX, goalY).getSteps());
    }

    private static int getHeuristic(int startX, int startY, int goalX, int goalY){
        return (goalX > startX ? goalX - startX : startX - goalX)
                + (goalY > startY ? goalY - startY : startY - goalY);
    }

    private class Point {
        private int x, y;
        private final int cost;
        private final int heuristic;

        @Override
        public boolean equals(Object o){
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;
            Point point = (Point) o;
            return x == point.x &&
                    y == point.y;
        }

        @Override
        public int hashCode(){
            return Objects.hash(x, y);
        }

        public Point(int x, int y, int cost, int heuristic){
            this.x = x;
            this.y = y;
            this.cost = cost;
            this.heuristic = heuristic;
        }
    }
}
