package codeforces.jan13.task3;

import java.util.Scanner;

public class OpticalIllusion {

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        int n = sysIn.nextInt();
        int r = sysIn.nextInt();
        double vectorLen = new DoublePair(1, 0).subtract(new DoublePair(Math.cos(2 * Math.PI/n), Math.sin(2 * Math.PI/n))).getLen();
        double R = r * vectorLen / ( 2 - vectorLen );
        System.out.println(R);
    }

    private static class DoublePair {
        private double x, y;

        public DoublePair(double x, double y){
            this.x = x;
            this.y = y;
        }

        public DoublePair subtract(DoublePair other){
            return new DoublePair(x - other.x, y - other.y);
        }

        public double getLen(){
            return Math.sqrt(x * x + y * y);
        }
    }

}
