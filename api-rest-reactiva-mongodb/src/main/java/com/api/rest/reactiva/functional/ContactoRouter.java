package com.api.rest.reactiva.functional;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ContactoRouter {

    @Bean
    public RouterFunction<ServerResponse> routeContacto(ContactoHandler contactoHandler){
        return RouterFunctions
                .route(GET("/functional/contactos/"),contactoHandler::listarContactos)
                .andRoute(GET("/functional/contactos/{id}"),contactoHandler::obtenerContactoPorId)
                .andRoute(GET("/functional/contactos/byEmail/{email}"),contactoHandler::obtenerContactoPorEmail)
                .andRoute(POST("/functional/contactos/"),contactoHandler::insertarContacto)
                .andRoute(PUT("/functional/contactos/{id}"),contactoHandler::actualizarContacto)
                .andRoute(DELETE("/functional/contactos/{id}"),contactoHandler::eliminarContacto);
    }

}
