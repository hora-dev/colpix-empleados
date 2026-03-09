package com.colpix.empleados.infraestructure.adapter;

import com.colpix.empleados.infraestructure.entity.EmpleadoEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmpleadoCrudRepository extends CrudRepository<EmpleadoEntity, Integer> {
    Long countBySupervisor_Id(Integer id);
    List<EmpleadoEntity> findBySupervisor_Id(Integer supervisorId);
}
