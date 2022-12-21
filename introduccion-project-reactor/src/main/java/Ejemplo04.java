import reactor.core.publisher.Flux;

public class Ejemplo04 {
    public static void main(String[] args) {

        Flux<String> flux = Flux.fromArray(new String[]{"data1","data2","data3"});
        //flux.subscribe(System.out::println);

        flux.doOnNext(System.out::println).subscribe();
    }
}
