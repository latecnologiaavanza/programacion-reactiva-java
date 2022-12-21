import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

public class EjemplosTests {

    @Test
    public void testTransformMap(){
        List<String> listaNombres = Arrays.asList("google","abc","fb","stackoverflow");
        Flux<String> nombresFlux = Flux.fromIterable(listaNombres)
                .filter(nombre -> nombre.length() > 5)
                .map(nombre -> nombre.toUpperCase())
                .log();

        StepVerifier.create(nombresFlux)
                .expectNext("GOOGLE","STACKOVERFLOW")
                .verifyComplete();
    }

    @Test
    public void testTransformUsingFlatMap(){
        List<String> listaNombres = Arrays.asList("google","abc","fb","stackoverflow");
        Flux<String> nombresFlux = Flux.fromIterable(listaNombres)
                .filter(nombre -> nombre.length() > 5)
                .flatMap(nombre -> {
                    return Mono.just(nombre.toUpperCase());
                })
                .log();

        StepVerifier.create(nombresFlux)
                .expectNext("GOOGLE","STACKOVERFLOW")
                .verifyComplete();
    }

    @Test
    public void testCombinarFlujosUsandoMerge(){
        Flux<String> flux1 = Flux.just("Blenders", "Old", "Johnnie");
        Flux<String> flux2 = Flux.just("Pride", "Monk", "Walker");

        Flux<String> fluxMerge = Flux.merge(flux1,flux2).log();

        StepVerifier.create(fluxMerge)
                .expectSubscription()
                .expectNext("Blenders", "Old", "Johnnie", "Pride", "Monk", "Walker")
                .verifyComplete();
    }

    @Test
    public void testCombinarFlujosUsandoMergeConDemora(){
        Flux<String> flux1 = Flux.just("Blenders", "Old", "Johnnie")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("Pride", "Monk", "Walker")
                .delayElements(Duration.ofSeconds(1));

        Flux<String> fluxMerge = Flux.merge(flux1,flux2).log();

        StepVerifier.create(fluxMerge)
                .expectSubscription()
                .expectNextCount(6)
                .verifyComplete();
    }

    @Test
    public void testCombinarFlujosConDemoraConOperadorConcat(){
        Flux<String> flux1 = Flux.just("Blenders", "Old", "Johnnie")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("Pride", "Monk", "Walker")
                .delayElements(Duration.ofSeconds(1));

        Flux<String> fluxConcat = Flux.concat(flux1,flux2).log();

        StepVerifier.create(fluxConcat)
                .expectSubscription()
                .expectNext("Blenders", "Old", "Johnnie", "Pride", "Monk", "Walker")
                .verifyComplete();
    }

    @Test
    public void testCombinarFlujosConDemoraConOperadorZip(){
        Flux<String> flux1 = Flux.just("Blenders", "Old", "Johnnie")
                .delayElements(Duration.ofSeconds(1));
        Flux<String> flux2 = Flux.just("Pride", "Monk", "Walker")
                .delayElements(Duration.ofSeconds(1));

        Flux<String> fluxZip = Flux.zip(flux1,flux2,(f1,f2) -> {
            return f1.concat(" ").concat(f2);
        }).log();

        StepVerifier.create(fluxZip)
                .expectNext("Blenders Pride", "Old Monk", "Johnnie Walker")
                .verifyComplete();
    }
}
