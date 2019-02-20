package exams.midterm.exam.task02;

import java.util.Scanner;

public class Fontane {

    long[] bit;

    public Fontane(int fountainCount){
        bit = new long[fountainCount + 1];
    }
    //BIT!

    public static void main(String[] args){

        Scanner sysIn = new Scanner(System.in);
        int fountainCount = sysIn.nextInt();
        Request[] requests = new Request[sysIn.nextInt()];
        sysIn.nextLine();
        for ( int i = 0; i < requests.length; i++ ){
            String[] line = sysIn.nextLine().split(" ");
            requests[i] = new Request(line[0].charAt(0) != '!', Integer.parseInt(line[1]) + 1, Integer.parseInt(line[2]) + 1);
        }

        new Fontane(fountainCount).applyRequests(requests);

    }

    private void applyRequests(Request[] requests){

        for ( Request request : requests ){
            if ( request.isQuery ){
                System.out.println(query(request.y - 1) - query(request.x - 1));
            } else {
                update(request.x, request.y);
            }
        }

    }

    private void update(int index, long value){

//        long diff = value - ( bit[index] - bit[index - (index &(-index))]) - 1;
        long diff = value - ( query(index) - query(index - 1)) - 1;
        while ( index < bit.length ){
            bit[index] = bit[index] + diff;
            index += index & (-index);
        }
    }

    private long query(int index){
        long sum = 0;
        while ( index > 0 ){
            sum = sum + bit[index];
            index -= index &(-index);
        }
        return sum;
    }

    private static class Request {
        private boolean isQuery;
        private int x, y;

        public Request(boolean isQuery, int x, int y){
            this.isQuery = isQuery;
            this.x = x;
            this.y = y;
        }
    }
}
