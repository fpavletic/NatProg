package hw07.task1;

import java.util.Scanner;

/**
 * Max match
 */
public class Olovke {

    private static int inCount;
    private static int outCount;

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        inCount = sysIn.nextInt() + 1;
        outCount = sysIn.nextInt();
        sysIn.nextLine();
        char[][] graph = new char[inCount][];
        for ( int i = 1; i < inCount; i++ ){
            graph[i] = sysIn.nextLine().toCharArray();
        }
        System.out.println(getMaxMatch(graph));

    }

    private static boolean findOut (char[][] graph, int in, boolean[] visited, int[] outToMatch){
        for ( int out = 0; out < outCount; out++ ){
            if ( graph[in][out] == 'S' && !visited[out] ){
                visited[out] = true;
                if (outToMatch[out] == 0 || findOut(graph, outToMatch[out], visited, outToMatch)){
                    outToMatch[out] = in;
                    return true;
                }
            }
        }
        return false;
    }

    private static int getMaxMatch(char[][] graph){
        int[] outToMatch = new int[outCount];
        int matchCount = 0;
        for ( int in = 1; in < inCount; in++ ){
            boolean[] visited = new boolean[outCount];
            if ( findOut(graph, in, visited, outToMatch) ){
                matchCount++;
            }
        }
        return matchCount;
    }
}
