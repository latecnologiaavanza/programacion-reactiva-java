import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class EjemplosTests {

    @Test
    public void testFlux(){
        Flux<Integer> fluxToTest = Flux.just(1,2,3,4,5);

        //Creamos la prueba
        StepVerifier.create(fluxToTest)
                .expectNext(1)
                .expectNext(2)
                .expectNext(3)
                .expectNext(4)
                .expectNext(5)
                .expectComplete()
                .verify();
    }

    @Test
    public void testFluxString(){
        Flux<String> fluxToTest = Flux.just("Jessica", "John", "Tomas", "Melissa", "Steve", "Megan", "Monica","Henry")
                .filter(nombre -> nombre.length() <= 5)
                .map(String::toUpperCase);

        StepVerifier.create(fluxToTest)
                .expectNext("JOHN")
                .expectNext("TOMAS")
                .expectNextMatches(nombre -> nombre.startsWith("ST"))
                .expectNext("MEGAN")
                .expectNext("HENRY")
                .expectComplete()
                .verify();
    }
}
