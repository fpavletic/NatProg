package hw01.task02;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Prasina {

    private static final int LINEAR_INTERVAL_SIZE = 32;
    private int magicDustCount;
    private List<Tuple> ingredients;

    public Prasina(List<Tuple> ingredients, int magicDustCount){
        this.magicDustCount = magicDustCount;
        this.ingredients = ingredients;
    }

    private long getMaxCakeCount(long low, long high){
        if ( high - low < LINEAR_INTERVAL_SIZE ){
            return getMaxCakeCountIterate(low, high);
        } else {
            long mid = (low + high) / 2;
            return isCanBake(mid) ? getMaxCakeCount(mid + 1, high) : getMaxCakeCount(low, mid - 1);
        }
    }

    private long getMaxCakeCountIterate(long low, long high){
        for ( long i = low; i <= high; i++ ){
            if ( !isCanBake(i) ){
                return i - 1;
            }
        }
        return high;
    }

    private boolean isCanBake(long cakeCount){
        return magicDustCount >= ingredients.stream().mapToLong(t -> {
            long mul = t.i * cakeCount;
            return t.j < mul ? mul - t.j : 0;
        } ).sum();
    }

    public static void main(String[] args){
        Scanner sysIn = new Scanner(System.in);
        int ingredientCount = sysIn.nextInt();
        int magicDustCount = sysIn.nextInt();

        List<Tuple> ingredients = new ArrayList<>();
        for ( int i = 0; i < ingredientCount; i++ ){
            ingredients.add(new Tuple(sysIn.nextInt(), 0));
        }
        for ( int i = 0; i < ingredientCount; i++ ){
            ingredients.get(i).j = sysIn.nextInt();
        }

        Tuple tmp = ingredients.stream().min(Comparator.comparingDouble(o -> o.j / o.i)).get();
        double max = (magicDustCount + tmp.j) / (double)tmp.i;

//        Tuple tmp = ingredients.stream().max((o1, o2) -> Double.compare(o1.j / o1.i, o2.j / o1.i)).get();
//        double max = tmp.j / tmp.i + magicDustCount / tmp.i;

        System.out.println(
                new Prasina(ingredients, magicDustCount)
                        .getMaxCakeCount(0, (long)Math.floor(max)));
    }

    private static class Tuple{
        private int i, j;
        private Tuple(int i, int j){
            this.i = i;
            this.j = j;
        }
    }
}
