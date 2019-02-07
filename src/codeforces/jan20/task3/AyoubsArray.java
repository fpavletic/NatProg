package codeforces.jan20.task3;

import java.util.Scanner;

public class AyoubsArray {

    private static long[][] dp;
    private static boolean[][] dpSet;

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        int n = sysIn.nextInt();
        dp = new long[n + 1][3];
        dpSet = new boolean[n + 1][3];
        int l = sysIn.nextInt();
        int r = sysIn.nextInt();
        int rMod = r % 3;
        int lMod = l % 3;
        int base = ( ( r - rMod ) - ( l + 3 - lMod ) ) / 3;
        int[] remainderCounts = new int[]{base, base, base};
        switch ( rMod ){
            case 2:
                remainderCounts[2]++;
            case 1:
                remainderCounts[1]++;
            case 0:
                remainderCounts[0]++;
        }
        switch ( lMod ){
            case 0:
                remainderCounts[0]++;
            case 1:
                remainderCounts[1]++;
            case 2:
                remainderCounts[2]++;
        }
        System.out.println(recursion(remainderCounts, n, 0));
    }

    private static long recursion(int[] remainderCounts, int numLeft, int remainder){
        if ( numLeft == 0 ){
            return remainder == 0 ? 1 : 0;
        }

        if ( dpSet[numLeft][remainder] ){
            return dp[numLeft][remainder];
        }

        long sum = 0;
        for ( int i = 0; i < 3; i++ ){
            sum = (sum + remainderCounts[i] * recursion(remainderCounts, numLeft - 1, (i + remainder) % 3 )) % 1000000007;
        }
        dp[numLeft][remainder] = sum;
        dpSet[numLeft][remainder] = true;
        return sum;
    }

}
