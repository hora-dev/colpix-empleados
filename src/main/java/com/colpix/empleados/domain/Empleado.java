package com.colpix.empleados.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Empleado {
    private Integer id;
    private String nombre;
    private String email;
    private LocalDateTime updatedAt;
    private Integer supervisorId;
}
