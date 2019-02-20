package exams.midterm.exam.task01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class MenzaReborn {

    private static final int DEFAULT_WAIT_TIME = 1000000007;

    private Map<Integer, Integer> studentToMinimumWaitingTime;
    private List<Interval> intervals;

    public MenzaReborn(List<Interval> interval){
        this.studentToMinimumWaitingTime = new HashMap<>();
        this.intervals = interval;
        Collections.sort(this.intervals);
    }

    public void calculateMinimumWaitingTimes(Pair[] studentArrivals, int studentCount){
        int i = 0;
        Arrays.sort(studentArrivals, Comparator.comparingInt(sm -> sm.arrivalTime));
        for ( Interval interval : intervals ){
            while ( i < studentArrivals.length ){
                Pair studentArrival = studentArrivals[i];
                if ( studentArrival.arrivalTime > interval.end ){
                    break;
                }
                int waitingTime = interval.start - studentArrival.arrivalTime;
                studentToMinimumWaitingTime.merge(studentArrival.studentId, waitingTime < 0 ? 0 : waitingTime, Integer::min);
                i++;
            }
        }
        for ( int j = 0; j < studentCount; j++ ){
            Integer out = studentToMinimumWaitingTime.get(j);
            System.out.println(out == null ? DEFAULT_WAIT_TIME : out);
        }
    }

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        int studentCount = sysIn.nextInt();
        int intervalCount = sysIn.nextInt();
        List<Interval> intervals = new ArrayList<>();
        for ( int i = 0; i < intervalCount; i++ ){
            intervals.add(new Interval(sysIn.nextInt(), sysIn.nextInt()));
        }
        Pair[] studentArrivals = new Pair[sysIn.nextInt()];
        for ( int i = 0; i < studentArrivals.length; i++){
            studentArrivals[i] = new Pair(sysIn.nextInt(), sysIn.nextInt());
        }
        new MenzaReborn(intervals).calculateMinimumWaitingTimes(studentArrivals, studentCount);
    }

    private static class Interval implements Comparable<Interval>{
        int start, end;

        public Interval(int start, int end){
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Interval o){
            return Integer.compare(start, o.start);
        }
    }

    private static class Pair {
        int studentId, arrivalTime;

        public Pair(int studentId, int arrivalTime){
            this.studentId = studentId;
            this.arrivalTime = arrivalTime;
        }
    }
}
