package com.colpix.empleados.infraestructure.mapper;

import com.colpix.empleados.domain.Empleado;
import com.colpix.empleados.infraestructure.dto.EmpleadoDTO;
import com.colpix.empleados.infraestructure.dto.EmpleadoDetalleDTO;
import com.colpix.empleados.infraestructure.entity.EmpleadoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmpleadoMapper {

    @Mapping(source = "supervisorId", target= "supervisor.id")
    EmpleadoEntity toEmpleadoEntity(Empleado empleado);

    @Mapping(source = "supervisorId", target= "supervisor.id")
    EmpleadoEntity toEmpleadoEntity(EmpleadoDTO empleadoDTO);

    Empleado toEmpleado(EmpleadoDTO empleadoDTO);

    @Mapping(source = "supervisor.id", target = "supervisorId")
    Empleado toEmpleado(EmpleadoEntity empleadoEntity);

    List<Empleado> toEmpleadoList(Iterable<EmpleadoEntity> empleadoEntity);

    EmpleadoDetalleDTO toEmpleadoDetalleDTO(Empleado empleado);

    EmpleadoDTO toEmpleadoDTO(Empleado empleado);


    List<EmpleadoDetalleDTO> toEmpleadoDetalleDTOList(List<Empleado> empleados);
}
