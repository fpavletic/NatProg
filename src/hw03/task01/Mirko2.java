package hw03.task01;

import java.util.Scanner;

public class Mirko2 {

    long[] distances;

    private Mirko2(int vertexCount, Tuple[] edges, int start){
        distances = calculateDistances(vertexCount, edges, start);
    }

    private long[] calculateDistances(int vertexCount, Tuple[] edges, int start){
        long[] distances = new long[vertexCount];
        for ( int i = 0; i < vertexCount; i++ ){
            distances[i] = -1;
        }

        distances[start] = 0;
        boolean hasChanged = true;
        for ( int i = 0; hasChanged && i < vertexCount; i++ ){
            hasChanged = false;
            for ( Tuple edge : edges ){
                if ( distances[edge.b] == -1 || distances[edge.b] > distances[edge.a] + edge.w) {
                    distances[edge.b] = distances[edge.a] + edge.w;
                    hasChanged = true;
                }
                if ( distances[edge.a] == -1 || distances[edge.a] > distances[edge.b] + edge.w){
                    distances[edge.a] = distances[edge.b] + edge.w;
                    hasChanged = true;
                }
            }
        }
        return distances;
    }

    private void printDistances(int[] vertices ){
        for ( int vertex : vertices){
            System.out.println(distances[vertex]);
        }
    }

    public static void main(String[] args) {
        Scanner sysIn = new Scanner(System.in);

        int vertexCount = sysIn.nextInt();

        int edgeCount = sysIn.nextInt();
        Tuple[] edges = new Tuple[edgeCount];
        //Create roads
        for ( int i = 0; i < edgeCount; i++ ){
            edges[i] = new Tuple(sysIn.nextInt() -1, sysIn.nextInt() -1, sysIn.nextInt());
        }

        //Create target list
        int[] targetCities = new int[sysIn.nextInt()];
        for ( int i = 0; i < targetCities.length; i++ ){
            targetCities[i] = sysIn.nextInt() -1;
        }

        new Mirko2(vertexCount, edges, 0).printDistances(targetCities);
    }

    private static class Tuple{
        private int a, b, w;

        public Tuple(int a, int b, int w){
            this.a = a;
            this.b = b;
            this.w = w;
        }
    }
}
