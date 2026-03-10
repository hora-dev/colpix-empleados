package com.colpix.empleados.infraestructure.errors;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalHandler {

    @ExceptionHandler(InterruptedException.class)
    public void interruptedException(InterruptedException interruptedException) {
        log.error("Interrupted Exception {}", interruptedException.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<String> empleadoNoEncontrado(EntityNotFoundException entityNotFoundException) {
        log.error("Empleado no encontrado: {}", entityNotFoundException.getMessage());
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> usuarioYaExiste(IllegalArgumentException illegalArgumentException) {
        log.error("Usuario ya existe: {}", illegalArgumentException.getMessage());
        return ResponseEntity.badRequest().body(illegalArgumentException.getMessage());
    }
}
