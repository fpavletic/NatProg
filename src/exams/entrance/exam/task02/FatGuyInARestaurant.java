package exams.entrance.exam.task02;

import java.util.Iterator;
import java.util.Scanner;

public class FatGuyInARestaurant {

    private long availableMoney;
    private long price;
    private ItemPriceIterator itemIterator;

    public FatGuyInARestaurant(int itemCount, int last, int a, int b, int d, long availableMoney){
        this.availableMoney = availableMoney;
        price = last;
        itemIterator = new ItemPriceIterator(itemCount, a, b, d, last);
    }

    public String findStartingIndex(){
        IntQueue itemsInOrder = new IntQueue(){{add((int)price);}};
        while (itemIterator.hasNext()){

            Integer item = itemIterator.next();
            price += item;
            itemsInOrder.add(item);

            while ( price > availableMoney) {
                price -= itemsInOrder.remove();
            }

            if ( price == availableMoney ){
                return String.format("DA%n%d%n%d", itemIterator.index - itemsInOrder.size() + 1, itemsInOrder.size());
            }

        }
        return "NE";
    }

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        System.out.print(new FatGuyInARestaurant( sysIn.nextInt(), sysIn.nextInt(), sysIn.nextInt(), sysIn.nextInt(), sysIn.nextInt(), sysIn.nextLong()).findStartingIndex());
    }

    public class ItemPriceIterator implements Iterator<Integer> {

        private int a, b, d, itemCount;
        private int index = 1;
        private int last;

        public ItemPriceIterator(int itemCount, int a, int b, int d, int last){
            this.a = a;
            this.b = b;
            this.d = d;
            this.itemCount = itemCount;
            this.last = last;
        }

        @Override
        public boolean hasNext(){
            return index < itemCount;
        }

        @Override
        public Integer next(){
            last = (int)(( (long)a * last + b ) % d + 1);
            index++;
            return last;
        }
    }
    public class IntQueue{

        private Node head, tail;
        private int size = 0;

        public void add( int value ){
            Node newNode = new Node(value);
            size++;

            if ( head != null ){
                head.lastNode = newNode;
            }
            head = newNode;

            if ( tail == null ){
                tail = head;
            }
        }

        public int remove(){
            size--;
            int value = tail.value;
            tail = tail.lastNode;
            return value;
        }

        public int size(){
            return size;
        }

        private class Node{
            private Node lastNode;
            private int value;

            public Node(int value){
                this.lastNode = null;
                this.value = value;
            }
        }

    }
}
