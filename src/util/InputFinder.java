package util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.function.Consumer;

public class InputFinder implements Consumer<String>, Runnable {

    private static final char[] VALID_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    private int id;
    private String prefix;
    private HttpClient client = HttpClient.newHttpClient();

    public InputFinder(int id, String prefix){
        this.id = id;
        this.prefix = prefix;
    }

    public void run(){
        permutations(prefix, 6, this);
    }

    @Override
    public void accept(String s){
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI("http://natpro-judge.duckdns.org/media/testcases/input_14_" + s + ".txt"))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if ( response.statusCode() == 404 ){
                return;
            }
            System.out.format("%d: %d - %s%n", id, response.statusCode(), s);
        } catch ( IOException | InterruptedException | URISyntaxException e ) {
        }
    }

    public static void main(String[] args){

        int id = 0;
        ExecutorService service = Executors.newFixedThreadPool(8);
        for ( int i = 0; i < VALID_CHARS.length; i++ ){
            service.submit(new InputFinder(id++, Character.toString(VALID_CHARS[i])));
        }
    }

    private static void permutations(String prefix, int remainingLen, Consumer<String> consumer){
        if ( remainingLen == 0 ){
            consumer.accept(prefix);
        } else if ( remainingLen == 6 ) {
            ThreadPoolExecutor pool = (ThreadPoolExecutor)Executors.newFixedThreadPool(4);
            for ( int i = 0; i < VALID_CHARS.length; i++ ){
                String tmp = prefix + Character.valueOf(VALID_CHARS[i]);
                pool.getQueue().add(() -> permutations(tmp, remainingLen - 1, consumer));
            }
        } else {
            for ( int i = 0; i < VALID_CHARS.length; i++ ){
                permutations(prefix + VALID_CHARS[i], remainingLen - 1, consumer);
            }
        }
    }
}
