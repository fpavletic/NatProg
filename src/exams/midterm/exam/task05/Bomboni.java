package exams.midterm.exam.task05;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Bomboni {

    static Map<Integer, Map<Integer, Long>> indexToMissingCandyToResult;
    static int[] sackContents;
    static int minCandyPerGroup;

    public static void main(String[] args){

        Scanner sysIn = new Scanner(System.in);
        int sackCount = sysIn.nextInt();
        minCandyPerGroup = sysIn.nextInt() + 1;
        sackContents = new int[sackCount];
        for ( int i = 0; i < sackCount; i++ ){
            sackContents[i] = sysIn.nextInt();
        }
        indexToMissingCandyToResult = new HashMap<>();
        System.out.println(recursion(sackCount - 1, minCandyPerGroup));
    }

    private static long recursion(int index, int missingCandy){

        if ( index == 0 ){
            if ( missingCandy == 0 && sackContents[index] >= minCandyPerGroup){
                return 2;
            }
            return missingCandy - sackContents[index] < 1 ? 1 : 0;
        }

        missingCandy = missingCandy < 1 ? 0 : missingCandy;

        Map<Integer, Long> missingCandyToResult = indexToMissingCandyToResult.get(index);
        if ( missingCandyToResult == null ){
            missingCandyToResult = new HashMap<>();
            indexToMissingCandyToResult.put(index, missingCandyToResult);
        }
        Long result = missingCandyToResult.get(missingCandy);

        if ( result == null ) {
            if ( missingCandy < 1 ) {
                result = ( recursion(index - 1, 0) +
                        recursion(index - 1, minCandyPerGroup - sackContents[index])) % 1000000007;
            } else {
                result = recursion(index - 1, missingCandy - sackContents[index]) % 1000000007;
            }
            indexToMissingCandyToResult.get(index).put(missingCandy, result);
        }

        return result;
    }

}
