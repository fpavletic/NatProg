package hw03.task01old;

import java.util.*;

class Mirko{

    private long[] cityIdToDistance;
    private Map<Integer, List<CityDistance>> cityIdToRoads;
    private int sourceCityId;

    private Queue<CityDistance> queue = new PriorityQueue<>(Comparator.comparingLong(c -> c.distance));
    private Set<Integer> visited = new HashSet<>();


    public Mirko(Map<Integer, List<CityDistance>> cityIdToRoads, int sourceCityId){
        cityIdToDistance = new long[cityIdToRoads.size()];
        this.cityIdToRoads = cityIdToRoads;
        this.sourceCityId = sourceCityId;
        push(new CityDistance(sourceCityId, 0l));
    }

    public void printDistances(int[] targetIds){

        for ( int targetId : targetIds  ){
            while (cityIdToDistance[targetId] == 0 && targetId != sourceCityId) {

                CityDistance current = pop();
                int currentId = current.city;

                if ( cityIdToDistance[currentId] == 0 ) {
                    cityIdToDistance[currentId] = current.distance;
                }

                Iterator<CityDistance> iter = cityIdToRoads.get(current.city).iterator();
                while ( iter.hasNext()){
                    CityDistance cd = iter.next();
                    if ( !push(new CityDistance(cd, current.distance)) ){
                        iter.remove(); //this saves us a shit ton of iterations in pops when dealing with highly interconnected graphs
                    }
                }
            }
            System.out.println(cityIdToDistance[targetId]);
        }

    }

    public boolean push(CityDistance cd){
        if ( visited.contains(cd.city) ){
            return false;
        }
        queue.add(cd);
        return true;
    }

    public CityDistance pop(){
        CityDistance tmp;
        while ( visited.contains((tmp = queue.poll()).city));
        visited.add(tmp.city);
        return tmp;
    }

    private static class CityDistance{
        private Integer city;
        private Long distance;

        public CityDistance(CityDistance other, Long distance){
            this(other.city, other.distance + distance);
        }

        public CityDistance(Integer city, Long distance){
            this.city = city;
            this.distance = distance;
        }

        @Override
        public boolean equals(Object o){
            if ( this == o ) return true;
            if ( o == null || getClass() != o.getClass() ) return false;
            CityDistance that = (CityDistance) o;
            return Objects.equals(city, that.city);
        }

        @Override
        public int hashCode(){
            return Objects.hash(city);
        }
    }



    public static void main(String[] args) {
        Scanner sysIn = new Scanner(System.in);

        Map<Integer, List<CityDistance>> cityIdToRoads = new HashMap<>();
        for ( int i = sysIn.nextInt() - 1; i > -1; i-- ){
            cityIdToRoads.put(i, new LinkedList<>());
        }

        //Create roads
        for ( int i = sysIn.nextInt(); i > 0 ; i-- ){
            Integer cityId1 = sysIn.nextInt() -1;
            Integer cityId2 = sysIn.nextInt() -1;
            Long distance = sysIn.nextLong();
            cityIdToRoads.get(cityId1).add(new CityDistance(cityId2, distance));
            cityIdToRoads.get(cityId2).add(new CityDistance(cityId1, distance));
        }

        //Create target list
        int[] targetCities = new int[sysIn.nextInt()];
        for ( int i = 0; i < targetCities.length; i++ ){
            targetCities[i] = sysIn.nextInt() -1;
        }

    }
}
