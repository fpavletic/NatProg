package hw09.task1;

import java.util.Scanner;

//Task: Find the longest palindrome in the provided string
//This solution implements the Manacher algorithm, you can read more about it here:
//https://articles.leetcode.com/longest-palindromic-substring-part-ii/
public class PaleSamNaSvijetu2 {

    private static String padString(String s){
        return "." + s.replaceAll("", "#") + ",";
    }

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        sysIn.nextLine();
        char[] input = padString(sysIn.nextLine()).toCharArray();
        int[] indexToPalindromeLen = new int[input.length];

        int centre = 0, rightEdge = 0, greatestPalindromeLen = 0;

        for ( int i = 1; i < input.length - 1; i++ ) {
            int j = centre + centre - i;
            indexToPalindromeLen[i] = rightEdge > i ? Math.min(rightEdge - i, indexToPalindromeLen[j]) : 0;

            while (input[i + 1 + indexToPalindromeLen[i]] == input[i - 1 - indexToPalindromeLen[i]]){
                indexToPalindromeLen[i]++;
            }

            if ( i + indexToPalindromeLen[i] > rightEdge ){
                centre = i;
                rightEdge = i + indexToPalindromeLen[i];
            }

            if ( indexToPalindromeLen[i] > greatestPalindromeLen ){
                greatestPalindromeLen = indexToPalindromeLen[i];
            }
        }

        System.out.println(greatestPalindromeLen);
    }

}
