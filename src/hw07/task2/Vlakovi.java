package hw07.task2;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Vlakovi {

    private static long[][] cityToBranch;
    private static int[] cityToParent;
    private static int[] cityToDepth;
    private static IntStack[] cityToChildren;

    public static void main(String[] args){

        Scanner sysIn = new Scanner(System.in);
        int cityCount = sysIn.nextInt();
        cityToBranch = new long[cityCount][];
        cityToParent = new int[cityCount];
        cityToDepth = new int[cityCount];
        cityToChildren = new IntStack[cityCount];

        for ( int i = 1, j; i < cityCount; i++ ){
            j = sysIn.nextInt() - 1;
            if (cityToChildren[j] == null){
                cityToChildren[j] = new IntStack();
            }
            cityToChildren[j].add(i);
            cityToParent[i] = j;
        }

        //build tree
        dfs(new Bitset(64), 0, 1);

        //queries
        for ( int i = sysIn.nextInt(); i > 0; i-- ) {
            int s = sysIn.nextInt() -1, e = sysIn.nextInt() -1;
            System.out.println( branchContains(s, e) ? "DA" : "NE");
        }
    }

    private static void dfs(Bitset citiesInBranch, int city, int depth){
        citiesInBranch.pushBit(city);

        cityToDepth[city] = depth;
        if ( depth % 20 == 0 ){
            cityToBranch[city] = Arrays.copyOf(citiesInBranch.words, citiesInBranch.getWordCount());
        }

        while (cityToChildren[city] != null && cityToChildren[city].size() > 0){
            dfs(citiesInBranch, cityToChildren[city].remove(), depth+1);
        }
        citiesInBranch.popBit();
    }

    private static boolean branchContains(int s, int e){

        do {
            if ( s < e ) return false;
            if ( s == e ) return true;
            s = cityToParent[s];
        } while ( cityToBranch[s] == null );
        return getBit(e, cityToBranch[s]);
    }

    private static boolean getBit(int bit, long[] words){
        int wordIndex = bit / 64;
        if ( wordIndex >= words.length ) return false;
        int bitIndex = bit - wordIndex * 64;
        return (words[wordIndex] & (1l << bitIndex)) != 0;
    }

    private static class Bitset {
        private long[] words;
        private Stack<Index> indexQueue = new Stack<>();

        public Bitset(int bitCapacity){
            words = new long[bitCapacity / 64];
        }

        private void pushBit ( int bit){
            int wordIndex = bit / 64;
            if ( wordIndex >= words.length ){
                words = Arrays.copyOf(words, wordIndex +1);
            }
            int bitIndex = bit - wordIndex * 64;
            indexQueue.push(new Index(wordIndex, bitIndex));
            words[wordIndex] |= (1l << bitIndex);
        }

        private void popBit (){
            Index index = indexQueue.pop();
            words[index.wordIndex] &= ~(1l << index.bitIndex);
        }

        private int getWordCount(){
            return words.length;
        }
    }

    private static class Index {
        int wordIndex;
        int bitIndex;

        public Index(int wordIndex, int bitIndex){
            this.wordIndex = wordIndex;
            this.bitIndex = bitIndex;
        }
    }

    private static class IntStack {

        private StackNode tail;
        private int size = 0;

        public void add( int value ){
            StackNode newNode = new StackNode(value, tail);
            tail = newNode;
            size++;
        }

        public int remove(){
            int value = tail.value;
            tail = tail.lastNode;
            size--;
            return value;
        }

        public int size(){
            return size;
        }

        private static class StackNode {
            private StackNode lastNode;
            private int value;

            public StackNode(int value, StackNode lastNode){
                this.lastNode = lastNode;
                this.value = value;
            }
        }

    }
}
