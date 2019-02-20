package exams.endterm.exam.task04;

import java.util.Scanner;

//MAX MATCH
public class Izvidaci {
    static int[][] scoutToPosition;
    static int[][] tentToPosition;
    static int scoutCount;
    static int tentCount;
    public static void main(String[] args){

        Scanner sysIn = new Scanner(System.in);
        int m = sysIn.nextInt(), n = sysIn.nextInt();
        scoutToPosition = new int[scoutCount = sysIn.nextInt() + 1][2];
        tentToPosition = new int[tentCount = sysIn.nextInt()][2];
        boolean[][] graph = new boolean[scoutCount][tentCount];
        int k = sysIn.nextInt(), scountIndex = 1, tentIndex = 0;
        sysIn.nextLine();
        for ( int i = 0; i < m; i++ ){
            char[] line = sysIn.nextLine().toCharArray();
            for ( int j = 0; j < n; j++ ){
                switch ( line[j] ){
                    case 'I':
                        scoutToPosition[scountIndex][0] = i;
                        scoutToPosition[scountIndex++][1] = j;
                        break;
                    case 'S':
                        tentToPosition[tentIndex][0] = i;
                        tentToPosition[tentIndex++][1] = j;
                        break;
                }
            }
        }
        for ( int i = 1; i < scoutCount; i++ ){
            for ( int j = 0; j < tentCount; j++){
                graph[i][j] = canReach(i, j, k);
            }
        }

        System.out.println(scoutCount - getMaxMatch(graph) - 1);
    }

    private static boolean findOut (boolean[][] graph, int in, boolean[] visited, int[] outToMatch){
        for ( int out = 0; out < tentCount; out++ ){
            if ( graph[in][out] && !visited[out] ){
                visited[out] = true;
                if (outToMatch[out] == 0 || findOut(graph, outToMatch[out], visited, outToMatch)){
                    outToMatch[out] = in;
                    return true;
                }
            }
        }
        return false;
    }

    private static int getMaxMatch(boolean[][] graph){
        int[] outToMatch = new int[tentCount];
        int matchCount = 0;
        for ( int in = 1; in < scoutCount; in++ ){
            boolean[] visited = new boolean[tentCount];
            if ( findOut(graph, in, visited, outToMatch) ){
                matchCount++;
            }
        }
        return matchCount;
    }

    private static boolean canReach(int i, int j, int k){
        return k > Math.max(Math.abs(scoutToPosition[i][0] - tentToPosition[j][0]),
                Math.abs(scoutToPosition[i][1] - tentToPosition[j][1]));
    }
}
