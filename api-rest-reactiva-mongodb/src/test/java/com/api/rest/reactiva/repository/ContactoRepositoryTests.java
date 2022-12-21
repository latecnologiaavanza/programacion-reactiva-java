package com.api.rest.reactiva.repository;

import com.api.rest.reactiva.documents.Contacto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.ReactiveMongoOperations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ContactoRepositoryTests {

    @Autowired
    private ContactoRepository contactoRepository;

    @Autowired
    private ReactiveMongoOperations mongoOperations;

    @BeforeAll
    public void insertarDatos(){
        Contacto contacto1 = new Contacto();
        contacto1.setNombre("Test1");
        contacto1.setEmail("c1@gmail.com");
        contacto1.setTelefono("133222");

        Contacto contacto2 = new Contacto();
        contacto2.setNombre("Test2");
        contacto2.setEmail("c2@gmail.com");
        contacto2.setTelefono("233222");

        Contacto contacto3 = new Contacto();
        contacto3.setNombre("Test3");
        contacto3.setEmail("c3@gmail.com");
        contacto3.setTelefono("333222");

        //Guardamos los contactos
        StepVerifier.create(contactoRepository.insert(contacto1).log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier.create(contactoRepository.save(contacto2).log())
                .expectSubscription()
                .expectNextCount(1)
                .verifyComplete();

        StepVerifier.create(contactoRepository.save(contacto3).log())
                .expectSubscription()
                .expectNextMatches(contacto -> (contacto.getId() != null))
                .verifyComplete();
    }

    @Test
    @Order(1)
    public void testListarContactos(){
        StepVerifier.create(contactoRepository.findAll().log())
                .expectSubscription()
                .expectNextCount(3)
                .verifyComplete();
    }

    @Test
    @Order(2)
    public void testBuscarPorEmail(){
        StepVerifier.create(contactoRepository.findFirstByEmail("c1@gmail.com").log())
                .expectSubscription()
                .expectNextMatches(contacto -> contacto.getEmail().equals("c1@gmail.com"))
                .verifyComplete();
    }

    @Test
    @Order(3)
    public void testActualizarContacto(){
        Mono<Contacto> contactoActualizado = contactoRepository.findFirstByEmail("c1@gmail.com")
                .map(contacto -> {
                    contacto.setTelefono("111111111");
                    return contacto;
                }).flatMap(contacto -> {
                    return contactoRepository.save(contacto);
                });

        StepVerifier.create(contactoActualizado.log())
                .expectSubscription()
                .expectNextMatches(contacto -> (contacto.getTelefono().equals("111111111")))
                .verifyComplete();
    }

    @Test
    @Order(4)
    public void testEliminarContactoPorId(){
        Mono<Void> contactoEliminado = contactoRepository.findFirstByEmail("c2@gmail.com")
                .flatMap(contacto -> {
                    return contactoRepository.deleteById(contacto.getId());
                }).log();

        StepVerifier.create(contactoEliminado)
                .expectSubscription()
                .verifyComplete();
    }

    @Test
    public void testEliminarContacto(){
        Mono<Void> contactoEliminado = contactoRepository.findFirstByEmail("c3@gmail.com")
                .flatMap(contacto -> contactoRepository.delete(contacto));

        StepVerifier.create(contactoEliminado)
                .expectSubscription()
                .verifyComplete();
    }

    @AfterAll
    public void limpiarDatos(){
        Mono<Void> elementosEliminados = contactoRepository.deleteAll();
        StepVerifier.create(elementosEliminados.log())
                .expectSubscription()
                .verifyComplete();
    }
}
