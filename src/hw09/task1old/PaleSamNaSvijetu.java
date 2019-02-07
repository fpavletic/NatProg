package hw09.task1old;

import java.util.Scanner;

//Task: Find the longest palindrome in the provided string
//This solution uses dynamic programming and is correct, but takes up O(n^2) memory which is more than the emulator allows
public class PaleSamNaSvijetu {

    private static int dp[][];

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        int strlen = sysIn.nextInt();
        dp = new int[strlen][strlen];
        sysIn.nextLine();
        System.out.println(getLongestPalindrome(sysIn.nextLine().toCharArray(), 0, strlen - 1));
    }

    private static int getLongestPalindrome(char[] string, int i1, int i2){
        if ( i1>i2 )return 0;
        if ( i1==i2 ) return 1;
        if ( string[i1] == string[i2]) {
            if ( dp[i1 + 1][i2 - 1] == 0){
                dp[i1 + 1][i2 - 1] = getLongestPalindrome(string, i1 + 1, i2 - 1);
            }
            return 2 + dp[i1 + 1][i2 - 1];
        }
        if ( dp[i1 + 1][i2] == 0){
            dp[i1 + 1][i2] = getLongestPalindrome(string, i1 + 1, i2);
        }
        if ( dp[i1][i2 - 1] == 0){
            dp[i1][i2 - 1] = getLongestPalindrome(string, i1, i2 - 1);
        }
        return Math.max(dp[i1 + 1][i2], dp[i1][i2 - 1]);
    }
}
