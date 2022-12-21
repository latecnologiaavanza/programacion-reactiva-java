import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;

public class Ejemplo01 {
    public static void main(String[] args) {

        Flux<String> ciudades = Flux.fromIterable(
                new ArrayList<>(Arrays.asList("New York", "London", "Paris", "Toronto", "Rome"))
        );
        ciudades.log().subscribe();

    }
}
