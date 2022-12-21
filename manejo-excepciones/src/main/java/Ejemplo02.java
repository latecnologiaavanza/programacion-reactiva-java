import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class Ejemplo02 {
    public static void main(String[] args) {

        Flux.just(2,7,10)
                .concatWith(Flux.error(new RuntimeException("Exception ocurred")))
                .concatWith(Mono.just(12))
                .onErrorResume(err -> {
                    System.out.println("Error : " + err);
                    return Mono.just(12);
                })
                .log()
                .subscribe();

    }
}
