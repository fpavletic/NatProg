package exams.endterm.exam.task05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class SGTM {
    public static void main(String[] args){

        Scanner sysIn = new Scanner(System.in);
        int n = sysIn.nextInt();
        List<Event> events = new ArrayList<>( n * 2);

        for ( int i = 0; i < n; i++ ){
            events.add(new Event(sysIn.nextFloat(), true));
            events.add(new Event(sysIn.nextFloat(), false));
        }

        Collections.sort(events);

        int currentSprayCount = 0;
        int maxSprayCount = 0;
        for ( Event event : events){
            currentSprayCount += event.value ? 1 : -1;
            if (maxSprayCount < currentSprayCount){
                maxSprayCount = currentSprayCount;
            }
        }

        System.out.println(maxSprayCount);
    }

    private static class Event implements Comparable<Event>{
        private float guid;
        private boolean value;

        public Event(float guid, boolean value){
            this.guid = guid;
            this.value = value;
        }

        @Override
        public int compareTo(Event o){
            return Float.compare(guid, o.guid);
        }
    }

}
