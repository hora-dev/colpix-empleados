package com.colpix.empleados.infraestructure.configuration;

import com.colpix.empleados.application.repository.EmpleadoRepository;
import com.colpix.empleados.application.service.EmpleadoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    @Bean
    public EmpleadoService empleadoService(EmpleadoRepository empleadoRepository) {
        return new EmpleadoService(empleadoRepository);
    }
}
