package com.colpix.empleados.infraestructure.adapter;

import com.colpix.empleados.infraestructure.entity.EmpleadoEntity;
import org.springframework.data.repository.CrudRepository;

public interface EmpleadoCrudRepository extends CrudRepository<EmpleadoEntity, Integer> {
}
