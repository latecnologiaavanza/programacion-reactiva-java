package com.ejemplo.reactivo;

import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductoRepository {

    private static List<Producto> lista = new ArrayList<>();
    private static List<Producto> lista2 = new ArrayList<>();

    static{
        lista.add(new Producto(1,"ordenador",200));
        lista.add(new Producto(2,"tablet",300));
        lista.add(new Producto(3,"auricular",200));

        lista2.add(new Producto(4,"movil",200));
        lista2.add(new Producto(5,"teclado",30));
        lista2.add(new Producto(6,"raton",20));
    }

    public Flux<Producto> buscarTodos(){
        return Flux.fromIterable(lista).delayElements(Duration.ofSeconds(3));
    }

    public Flux<Producto> buscarOtros(){
        return Flux.fromIterable(lista2).delayElements(Duration.ofSeconds(3));
    }
}
