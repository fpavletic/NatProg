package hw06.task01;

import java.util.Scanner;

public class Pero {

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        long a = sysIn.nextInt();
        long b = sysIn.nextInt();
        long p = sysIn.nextInt();
        for ( int i = 0; true; i++ ){
            if ( (b + i * p) % a == 0 ){
                System.out.println((b + i * p) / a);
                return;
            }
        }
    }

}
