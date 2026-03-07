package com.colpix.empleados.application.repository;

import com.colpix.empleados.domain.Empleado;
import com.colpix.empleados.domain.EmpleadoDetalle;

public interface EmpleadoRepository {
    Empleado crearEmpleado(Empleado empleado);
    Empleado actualizarEmpleado(Empleado empleado);
    EmpleadoDetalle obtenerDetalles(Empleado e);
}
