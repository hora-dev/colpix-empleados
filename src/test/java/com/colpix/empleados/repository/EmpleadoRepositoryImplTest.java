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
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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

    @Test
    void actualizarEmpleado() {

        Empleado empleadoDominio = Empleado.builder()
                .id(1)
                .nombre("Juan")
                .email("juan@email.com")
                .supervisorId(0).build();

        EmpleadoEntity entidadBD = EmpleadoEntity.builder()
                .id(1)
                .nombre("Juan")
                .email("email@email.com")
                .supervisor(new EmpleadoEntity()).build();

        EmpleadoEntity entidadNueva = EmpleadoEntity.builder()
                .id(1)
                .nombre("Juan")
                .email("juan@email.com")
                .supervisor(new EmpleadoEntity()).build();

        when(empleadoMapper.toEmpleadoEntity(empleadoDominio)).thenReturn(entidadNueva);
        when(empleadoMapper.toEmpleado(entidadNueva)).thenReturn(empleadoDominio);
        when(empleadoCrudRepository.findById(1)).thenReturn(Optional.of(entidadBD));
        when(empleadoCrudRepository.save(any(EmpleadoEntity.class))).thenReturn(entidadNueva);

        Empleado resultado = empleadoRepository.actualizarEmpleado(empleadoDominio);

        assertNotNull(resultado);
        assertEquals(resultado.getEmail(), empleadoDominio.getEmail());
        verify(empleadoCrudRepository, times(1)).save(entidadBD);
        verify(empleadoMapper).toEmpleadoEntity(empleadoDominio);
    }

    @Test
    void empleadoAActualizarNoEncontrado() {

        Empleado empleadoDominio = Empleado.builder()
                .id(1)
                .nombre("Juan")
                .email("juan@email.com")
                .supervisorId(0).build();

        when(empleadoCrudRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(IllegalStateException.class, () -> {
            empleadoRepository.actualizarEmpleado(empleadoDominio);
        });
    }

    @Test
    void devuelveDetalles() {

        EmpleadoEntity empleadoEntidad1 = EmpleadoEntity.builder()
                .id(1)
                .nombre("Juan")
                .email("juan@email.com")
                .build();

        EmpleadoEntity empleadoEntidad2 = EmpleadoEntity.builder()
                .id(2)
                .nombre("Pedro")
                .email("pedro@email.com")
              .build();

        EmpleadoEntity empleadoEntidad3 = EmpleadoEntity.builder()
                .id(3)
                .nombre("Simon")
                .email("simon@email.com")
               .build();

        Empleado empleadoDominio11 = Empleado.builder()
                .id(1)
                .nombre("Juan")
                .email("juan@email.com")
                .build();

        Empleado empleadoDominio22 = Empleado.builder()
                .id(2)
                .nombre("Pedro")
                .email("pedro@email.com")
                .build();

        Empleado empleadoDominio33 = Empleado.builder()
                .id(3)
                .nombre("Simon")
                .email("simon@email.com")
                .build();


        when(empleadoCrudRepository.findAll()).thenReturn(List.of(empleadoEntidad1, empleadoEntidad2, empleadoEntidad3));
        when(empleadoMapper.toEmpleadoList(empleadoCrudRepository.findAll())).thenReturn(List.of(empleadoDominio11, empleadoDominio22, empleadoDominio33));

        List<Empleado> empleadoList = empleadoRepository.obtenerDetalle();

        assertNotNull(empleadoList);
        assertEquals(3, empleadoList.size());
    }
}