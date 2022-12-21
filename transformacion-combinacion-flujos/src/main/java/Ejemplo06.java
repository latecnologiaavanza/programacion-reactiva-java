import reactor.core.publisher.Flux;

public class Ejemplo06 {
    public static void main(String[] args) {

        Flux<String> flux1 = Flux.just("A", "B", "C");
        Flux<String> flux2 = Flux.just("D", "E", "F");

        flux1.zipWith(flux2,(first,second) -> first + second).subscribe(System.out::println);
    }
}
