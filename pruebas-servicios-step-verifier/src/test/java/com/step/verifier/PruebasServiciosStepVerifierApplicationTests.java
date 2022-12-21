package com.step.verifier;

import com.step.verifier.servicios.ServicioSencillo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

@SpringBootTest
class PruebasServiciosStepVerifierApplicationTests {

	@Autowired
	ServicioSencillo servicioSencillo;

	@Test
	void testMono(){
		Mono<String> uno = servicioSencillo.buscarUno();
		StepVerifier.create(uno).expectNext("hola").verifyComplete();
	}

	@Test
	void testVarios(){
		Flux<String> varios = servicioSencillo.buscarTodos();
		StepVerifier.create(varios).expectNext("hola").expectNext("que").expectNext("tal").expectNext("estas").verifyComplete();
	}

	@Test
	void testVariosLenta(){
		Flux<String> varios = servicioSencillo.buscarTodosLento();
		StepVerifier.create(varios)
				.expectNext("hola")
				.thenAwait(Duration.ofSeconds(1))
				.expectNext("que")
				.thenAwait(Duration.ofSeconds(1))
				.expectNext("tal")
				.thenAwait(Duration.ofSeconds(1))
				.expectNext("estas")
				.thenAwait(Duration.ofSeconds(1)).verifyComplete();
	}
}
