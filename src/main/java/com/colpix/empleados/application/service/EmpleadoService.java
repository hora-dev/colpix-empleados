package com.colpix.empleados.application.service;

import com.colpix.empleados.application.repository.EmpleadoRepository;
import com.colpix.empleados.domain.Empleado;

public class EmpleadoService {
    private final EmpleadoRepository empleadoRepository;

    public EmpleadoService(EmpleadoRepository empleadoRepository) {
        this.empleadoRepository = empleadoRepository;
    }

    public Empleado crearEmpleado(Empleado empleado) {
        return empleadoRepository.crearEmpleado(empleado);
    }
}
