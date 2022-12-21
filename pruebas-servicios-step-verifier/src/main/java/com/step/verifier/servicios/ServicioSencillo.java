package com.step.verifier.servicios;

import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class ServicioSencillo {

    public Mono<String> buscarUno(){
        return Mono.just("hola");
    }

    public Flux<String> buscarTodos(){
        return Flux.just("hola","que","tal","estas");
    }

    public Flux<String> buscarTodosLento(){
        return Flux.just("hola","que","tal","estas").delaySequence(Duration.ofSeconds(10));
    }
}
