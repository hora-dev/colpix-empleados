package com.colpix.empleados.repository;

import com.colpix.empleados.domain.Empleado;
import com.colpix.empleados.infraestructure.adapter.EmpleadoCrudRepository;
import com.colpix.empleados.infraestructure.adapter.EmpleadoRepositoryImpl;
import com.colpix.empleados.infraestructure.entity.EmpleadoEntity;
import com.colpix.empleados.infraestructure.mapper.EmpleadoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmpleadoRepositoryImplTest {

    @Mock
    private EmpleadoCrudRepository empleadoCrudRepository;

    @Mock
    private EmpleadoMapper empleadoMapper;

    @InjectMocks
    private EmpleadoRepositoryImpl empleadoRepository;

    @Test
    void crearEmpleado_DebeTransformarYGuardar() {
        Empleado empleadoDominio = Empleado.builder()
                .id(0)
                .nombre("Juan")
                .email("email@email.com")
                .updatedAt(LocalDateTime.now())
                .supervisorId(0).build();

        EmpleadoEntity entidad = EmpleadoEntity.builder()
                .id(0)
                .nombre("Juan")
                .email("email@email.com")
                .updatedAt(LocalDateTime.now())
                .supervisor(new EmpleadoEntity()).build();

        when(empleadoMapper.toEmpleadoEntity(empleadoDominio)).thenReturn(entidad);
        when(empleadoCrudRepository.save(entidad)).thenReturn(entidad);
        when(empleadoMapper.toEmpleado(entidad)).thenReturn(empleadoDominio);

        Empleado resultado = empleadoRepository.crearEmpleado(empleadoDominio);

        assertNotNull(resultado);
        verify(empleadoCrudRepository, times(1)).save(entidad);
        verify(empleadoMapper).toEmpleadoEntity(empleadoDominio);
    }
}