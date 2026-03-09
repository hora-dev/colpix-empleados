package com.colpix.empleados.domain;

import lombok.*;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmpleadoDetalle extends Empleado {
    private Integer cantidadEmpleadosACargo;
}
