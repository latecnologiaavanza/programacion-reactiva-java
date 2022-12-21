package com.ejemplo.reactivo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    public Flux<Producto> buscarTodos(){
        Flux<Producto> flux1 = productoRepository.buscarTodos();
        Flux<Producto> flux2 = productoRepository.buscarOtros();
        return Flux.merge(flux1,flux2);
    }

}
