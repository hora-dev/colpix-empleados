package com.colpix.empleados.infraestructure.dto;

import lombok.Data;

@Data
public class EmpleadoDTO {
    private Integer id;
    private String nombre;
    private String email;
    private Integer supervisorId;
}
