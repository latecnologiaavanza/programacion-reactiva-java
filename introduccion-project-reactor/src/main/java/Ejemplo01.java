import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class Ejemplo01 {
    public static void main(String[] args) {

        List<Integer> elementosFromMono = new ArrayList<>();
        List<Integer> elementosFromFlux = new ArrayList<>();

        //Creamos un Mono
        Mono<Integer> mono = Mono.just(121);

        //Creamos un Flux
        Flux<Integer> flux = Flux.just(12,14,9,11,12,20,23,8,5,6,7);

        //Nos suscribimos al mono
        mono.subscribe(elementosFromMono::add);

        //Nos suscribimos al Flux
        flux.subscribe(elementosFromFlux::add);

        System.out.println(elementosFromMono);
        System.out.println(elementosFromFlux);

    }
}
