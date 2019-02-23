package hw01.task01;

import java.util.Scanner;

public class Palindromi {

    public static void main(String[] args){
        System.out.println(new Scanner(System.in).useDelimiter("\\$").next().split("\n", 2)[1].codePoints().sorted().collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append));
    }
}

