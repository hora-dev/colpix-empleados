package com.colpix.empleados.infraestructure.adapter;

import com.colpix.empleados.application.repository.EmpleadoRepository;
import com.colpix.empleados.domain.Empleado;
import com.colpix.empleados.domain.EmpleadoDetalle;
import com.colpix.empleados.infraestructure.entity.EmpleadoEntity;
import com.colpix.empleados.infraestructure.mapper.EmpleadoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

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
        EmpleadoEntity empleadoBD = empleadoCrudRepository.findById(empleado.getId()).orElse(null);
        if(!Objects.isNull(empleadoBD)) {
            EmpleadoEntity empleadoEntityActualizado = empleadoMapper.toEmpleadoEntity(empleado);
            empleadoBD.setEmail(empleadoEntityActualizado.getEmail());
            empleadoBD.setNombre(empleadoEntityActualizado.getNombre());
            empleadoBD.setSupervisor(empleadoEntityActualizado.getSupervisor());
            empleadoBD.setUpdatedAt(LocalDateTime.now());
            return empleadoMapper.toEmpleado(empleadoCrudRepository.save(empleadoBD));
        }
        throw new IllegalStateException("Empleado con id no encontrado " + empleado.getId());
    }

    @Override
    public EmpleadoDetalle obtenerDetalles(Empleado e) {
        return null;
    }

    @Override
    public List<Empleado> obtenerDetalle() {
        return empleadoMapper.toEmpleadoList(empleadoCrudRepository.findAll());
    }
}
