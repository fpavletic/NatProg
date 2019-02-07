package codeforces.jan20.task2;

import java.util.Scanner;

public class ZuhairStrings {
    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        int n = sysIn.nextInt();
        int k = sysIn.nextInt();
        sysIn.nextLine();
        char[] chars = sysIn.nextLine().toCharArray();
        int[] counts = new int[26];
        int maxCount = 0;
        char lastChar = '\0';
        char sequenceLen = 0;
        for ( int i = 0; i < n; i++ ){
            if ( lastChar == chars[i] ){
                ++sequenceLen;
            } else {
                lastChar = chars[i];
                sequenceLen = 1;
            }
            if ( sequenceLen == k ){
                if ( ++counts[lastChar - 'a'] > maxCount ){
                    maxCount = counts[lastChar - 'a'];
                }
                sequenceLen = 0;
            }
        }
        System.out.println(maxCount);
    }
}