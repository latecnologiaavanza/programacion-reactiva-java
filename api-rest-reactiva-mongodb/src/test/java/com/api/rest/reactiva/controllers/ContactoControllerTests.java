package com.api.rest.reactiva.controllers;

import com.api.rest.reactiva.documents.Contacto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@SpringBootTest
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContactoControllerTests {

    @Autowired
    private WebTestClient webTestClient;

    private Contacto contactoGuardado;

    @Test
    @Order(0)
    public void testGuardarContacto(){
        Flux<Contacto> contactoFlux = webTestClient.post()
                .uri("/api/v1/contactos")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new Contacto("webtest","w@gmail.com","992922")))
                .exchange()
                .expectStatus().isAccepted()
                .returnResult(Contacto.class).getResponseBody()
                .log();

        contactoFlux.next().subscribe(contacto -> {
            this.contactoGuardado = contacto;
        });

        Assertions.assertNotNull(contactoGuardado);
    }

    @Test
    @Order(1)
    public void testObtenerContactoPorEmail(){
        Flux<Contacto> contactoFlux = webTestClient.get()
                .uri("/api/v1/contactos/byEmail/{email}","w@gmail.com")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Contacto.class).getResponseBody()
                .log();

        StepVerifier.create(contactoFlux)
                .expectSubscription()
                .expectNextMatches(contacto -> contacto.getEmail().equals("w@gmail.com"))
                .verifyComplete();
    }

    @Test
    @Order(2)
    public void testActualizarContacto(){
        Flux<Contacto> contactoFlux = webTestClient.put()
                .uri("/api/v1/contactos/{id}",contactoGuardado.getId())
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(new Contacto(contactoGuardado.getId(),"WebTestClient","wtc@gmail.com","11111")))
                .exchange()
                .returnResult(Contacto.class).getResponseBody()
                .log();

        StepVerifier.create(contactoFlux)
                .expectSubscription()
                .expectNextMatches(contacto -> contacto.getEmail().equals("wtc@gmail.com"))
                .verifyComplete();
    }

    @Test
    @Order(2)
    public void testListarContactos(){
        Flux<Contacto> contactosFlux = webTestClient.get()
                .uri("/api/v1/contactos")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(Contacto.class).getResponseBody()
                .log();

        StepVerifier.create(contactosFlux)
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @Order(3)
    public void testEliminarContacto(){
        Flux<Void> flux = webTestClient.delete()
                .uri("/api/v1/contactos/{id}",contactoGuardado.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .returnResult(Void.class).getResponseBody();

        StepVerifier.create(flux)
                .expectSubscription()
                .verifyComplete();
    }
}
