import reactor.core.publisher.Flux;

public class Ejemplo04 {
    public static void main(String[] args) {

        Flux<String> firstFlux = Flux.fromArray(new String[]{"a", "b", "c"});
        Flux<String> secondFlux = Flux.fromArray(new String[]{"d", "e", "f"});

        Flux<String> fluxConcat = Flux.concat(firstFlux,secondFlux);

        fluxConcat.subscribe(element -> System.out.println(element + " "));
    }
}
