package com.api.rest.reactiva.webflux;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public class HelloHandler {

    public Mono<ServerResponse> mostrarMensajeMono(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(
                  Mono.just("Bienvenido a mi canal de YouTube"),String.class
                );
    }
    public Mono<ServerResponse> mostrarMensajeFlux(ServerRequest serverRequest){
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_STREAM_JSON)
                .body(
                        Flux.just("Bienvenido","a","mi","canal")
                                .delayElements(Duration.ofSeconds(1)),String.class
                );
    }

}
