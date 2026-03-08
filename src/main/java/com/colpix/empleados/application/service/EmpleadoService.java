package com.colpix.empleados.application.service;

import com.colpix.empleados.application.repository.EmpleadoRepository;
import com.colpix.empleados.domain.Empleado;

import java.util.List;

public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado crearEmpleado(Empleado empleado) {
        return empleadoRepository.crearEmpleado(empleado);
    }

    public Empleado actualizarEmpleado(Empleado empleado) {
        return empleadoRepository.actualizarEmpleado(empleado);
    }

    public List<Empleado> obtenerDetalles() {
        return empleadoRepository.obtenerDetalle();
    }
}
