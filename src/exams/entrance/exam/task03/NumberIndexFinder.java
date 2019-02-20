package exams.entrance.exam.task03;


import java.util.Iterator;
import java.util.Scanner;

public class NumberIndexFinder {

    private int findNumberIndex(int number){

        short digitCount = getDigitCount(number);
        int currentNumber = getStartingNumber(digitCount);
        Iterator<Integer> sequenceGenerator = new SequenceGenerator(currentNumber % 10, digitCount);
        int index = 1;
        while ( number != currentNumber ){
            currentNumber = sequenceGenerator.next() + (currentNumber % (int)Math.pow(10, digitCount -1 )) * 10;
            index++;
        }
        return index;
    }

    private short getDigitCount(int number){
        return (short)String.valueOf(number).length();
    }

    private int getStartingNumber(int numberDigitCount){
        switch ( numberDigitCount ){
            case 1:
                return 1;
            case 2:
                return 12;
            case 3:
                return 123;
            case 4:
                return 1234;
            case 5:
                return 12345;
            case 6:
                return 123456;
        }
        return -1; //this should never happen
    }

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        String numberAsString = sysIn.nextLine();
        sysIn.close();
        System.out.println(new NumberIndexFinder().findNumberIndex(Integer.parseInt(numberAsString)));
    }

    private class SequenceGenerator implements Iterator<Integer> {

        private final short maxDigitCount;

        private int[] lastNumberArray;
        private int lastNumber;
        private short remainingDigits;

        public SequenceGenerator(int lastNumber, short maxDigitCount){
            this.lastNumber = lastNumber;
            this.maxDigitCount = maxDigitCount;
            remainingDigits = 0;
        }

        @Override
        public boolean hasNext(){
            return true;
        }

        @Override
        public Integer next(){
            if ( remainingDigits == 0 ) {
                remainingDigits = getDigitCount(++lastNumber);
                lastNumberArray = toArray(lastNumber, remainingDigits);
            }
            return lastNumberArray[getDigitCount(lastNumber) - remainingDigits--];
        }

        private int[] toArray(int number, short indexOffset){
            int[] array = new int[maxDigitCount];
            int index = 0;
            while ( number != 0 ){
                array[indexOffset - ++index] = number % 10;
                number /= 10;
            }
            return array;
        }
    }
}
