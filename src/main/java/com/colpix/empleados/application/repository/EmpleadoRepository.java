package com.colpix.empleados.application.repository;

import com.colpix.empleados.domain.Empleado;
import com.colpix.empleados.domain.EmpleadoDetalle;

import java.util.List;

public interface EmpleadoRepository {
    Empleado crearEmpleado(Empleado empleado);
    Empleado actualizarEmpleado(Empleado empleado);
    EmpleadoDetalle obtenerDetalles(Empleado e);
    List<Empleado> obtenerDetalle();
}
