package com.colpix.empleados.infraestructure.controller;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class SaludoController {

    @QueryMapping
    public String saludo() {
        return "Hola desde GraphQL";
    }

}
