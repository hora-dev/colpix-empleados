package com.colpix.empleados.infraestructure.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EmpleadoDetalleDTO extends EmpleadoDTO{
    private LocalDateTime updatedAt;
}
