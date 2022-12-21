package com.api.rest.reactiva.controllers;

import com.api.rest.reactiva.documents.Contacto;
import com.api.rest.reactiva.repository.ContactoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
public class ContactoController {

    @Autowired
    private ContactoRepository contactoRepository;

    @GetMapping("/contactos")
    public Flux<Contacto> listarContactos(){
        return contactoRepository.findAll();
    }

    @GetMapping(value = "/contactos/{id}")
    public Mono<ResponseEntity<Contacto>> obtenerContacto(@PathVariable String id){
        return contactoRepository.findById(id)
                .map(contacto -> new ResponseEntity<>(contacto, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/contactos/byEmail/{email}")
    public Mono<ResponseEntity<Contacto>> obtenerContactoPorEmail(@PathVariable String email){
        return contactoRepository.findFirstByEmail(email)
                .map(contacto -> new ResponseEntity<>(contacto, HttpStatus.OK))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/contactos")
    public Mono<ResponseEntity<Contacto>> guardarContacto(@RequestBody Contacto contacto){
        return contactoRepository.insert(contacto)
                .map(contactoGuardado -> new ResponseEntity<>(contactoGuardado,HttpStatus.ACCEPTED))
                .defaultIfEmpty(new ResponseEntity<>(contacto,HttpStatus.NOT_ACCEPTABLE));
    }


    @PutMapping("/contactos/{id}")
    public Mono<ResponseEntity<Contacto>> actualizarContacto(@RequestBody Contacto contacto,@PathVariable String id){
        return contactoRepository.findById(id)
                .flatMap(contactoActualizado -> {
                    contacto.setId(id);
                    return contactoRepository.save(contacto)
                            .map(contacto1 -> new ResponseEntity<>(contacto1,HttpStatus.ACCEPTED));
                }).defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(value = "/contactos/{id}")
    public Mono<Void> eliminarContacto(@PathVariable String id){
        return contactoRepository.deleteById(id);
    }
}
