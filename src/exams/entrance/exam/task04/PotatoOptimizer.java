package exams.entrance.exam.task04;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class PotatoOptimizer {

    private boolean[][] field;
    private int width;
    private int height;

    public PotatoOptimizer(boolean[][] field, int height, int width){
        this.field = field;
        this.width = width;
        this.height = height;
    }

    private int getArea(){

        int greatestArea = Integer.MIN_VALUE;
        for ( int j = 0; j < height; j++ ){
            for ( int i = 0; i < width; i++){
                if ( !field[j][i] ){
                    continue;
                }
                int area = findLargestArea(j, i);
                if ( area > greatestArea ){
                    greatestArea = area;
                }
            }
        }
        return greatestArea;
    }

    private int findLargestArea(int y, int x){
        int currentBest = Integer.MIN_VALUE;
        int n = width, m = height;
        for ( int j = y; j < m; j++ ){
            for ( int i = x; i < n; i++){
                if ( !field[j][i]){
                    n = i;
                    int current = (i - x) * (j - y + 1);
                    if ( current > currentBest ){
                        currentBest = current;
                    }
                    break;
                }
                if ( i == n - 1 ){
                    int current = (i - x + 1) * (j - y + 1);
                    if ( current > currentBest ){
                        currentBest = current;
                    }
                }
            }
        }
        return currentBest;
    }

    public static void main(String[] args) throws FileNotFoundException{
        Scanner sysIn = new Scanner(System.in);
        String[] size = sysIn.nextLine().split(" ");
        int n = Integer.parseInt(size[1]);
        int m = Integer.parseInt(size[0]);
        boolean[][] field = new boolean[m][n];
        int j = 0;
        while ( sysIn.hasNext() ){
            char[] line = sysIn.nextLine().toCharArray();
            for ( int i = n -1; i >= 0; i-- ){
                field[j][i] = line[i] == '.';
            }
            j++;
        }
        System.out.println(new PotatoOptimizer(field, m, n).getArea());
    }

}
