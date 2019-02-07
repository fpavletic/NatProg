package hw06.task02;

import java.util.Scanner;

public class Nimbo {

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        int chipCount = sysIn.nextInt();
        long nimSum = 0;
        for ( int i = 0; i < chipCount; i++ ){
            nimSum ^= sysIn.nextLong();
        }
        System.out.println(nimSum == 0 ? "PERO" : "BRANKO");
    }
}
