import reactor.core.publisher.Flux;

public class Ejemplo07 {
    public static void main(String[] args) {

        Flux<Integer> flux1 = Flux.just(1,2,3,4,5);
        Flux<Integer> flux2 = Flux.just(4,5,6);

        Flux.zip(flux1,flux2,(first,second) -> first * second).subscribe(System.out::println);
    }
}
