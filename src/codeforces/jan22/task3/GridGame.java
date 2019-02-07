package codeforces.jan22.task3;

import java.util.Scanner;

public class GridGame {

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
//        char[] sequence = sysIn.nextLine().toCharArray();
        char[] sequence = "01010101010101".toCharArray();
        int vert = 4; int hor = 3;

        for ( int i = 0; i < sequence.length; i++ ){
            if ( sequence[i] == '1' ){
                System.out.println( "1 " + (hor += hor == 3 ?  -2 : 2));
            } else {
                System.out.println( "2 " + (vert += vert == 4 ? -3 : 1));
            }
        }
    }
}
