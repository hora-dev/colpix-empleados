package com.colpix.empleados.infraestructure.adapter;

import com.colpix.empleados.application.repository.EmpleadoRepository;
import com.colpix.empleados.domain.Empleado;
import com.colpix.empleados.domain.EmpleadoDetalle;
import com.colpix.empleados.infraestructure.entity.EmpleadoEntity;
import com.colpix.empleados.infraestructure.mapper.EmpleadoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@RequiredArgsConstructor
public class EmpleadoRepositoryImpl implements EmpleadoRepository {
    private final EmpleadoCrudRepository empleadoCrudRepository;
    private final EmpleadoMapper empleadoMapper;


    @Override
    public Empleado crearEmpleado(Empleado empleado) {
        EmpleadoEntity empleadoEntity = empleadoMapper.toEmpleadoEntity(empleado);
        empleadoEntity.setUpdatedAt(LocalDateTime.now());
        empleadoCrudRepository.save(empleadoEntity);
        return empleadoMapper.toEmpleado(empleadoEntity);
    }

    @Override
    public Empleado actualizarEmpleado(Empleado empleado) {
        return null;
    }

    @Override
    public EmpleadoDetalle obtenerDetalles(Empleado e) {
        return null;
    }
}
