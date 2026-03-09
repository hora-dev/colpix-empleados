package com.colpix.empleados.repository;

import com.colpix.empleados.domain.Empleado;
import com.colpix.empleados.domain.EmpleadoDetalle;
import com.colpix.empleados.infraestructure.adapter.EmpleadoCrudRepository;
import com.colpix.empleados.infraestructure.adapter.EmpleadoRepositoryImpl;
import com.colpix.empleados.infraestructure.entity.EmpleadoEntity;
import com.colpix.empleados.infraestructure.mapper.EmpleadoMapper;
import jakarta.persistence.EntityNotFoundException;
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
                .supervisorId(null).build();

        EmpleadoEntity entidad = EmpleadoEntity.builder()
                .id(0)
                .nombre("Juan")
                .email("email@email.com")
                .updatedAt(LocalDateTime.now())
                .supervisor(null).build();

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
                .supervisorId(null).build();

        EmpleadoEntity entidadBD = EmpleadoEntity.builder()
                .id(1)
                .nombre("Juan")
                .email("email@email.com")
                .supervisor(null).build();

        EmpleadoEntity entidadNueva = EmpleadoEntity.builder()
                .id(1)
                .nombre("Juan")
                .email("juan@email.com")
                .supervisor(null).build();

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
                .supervisorId(null).build();

        when(empleadoCrudRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
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

    @Test
    void obtenerDetalles_ok() throws InterruptedException {

        Integer idEmpleado = 1;

        EmpleadoEntity supervisor = new EmpleadoEntity();
        supervisor.setId(99);

        EmpleadoEntity empleado = new EmpleadoEntity();
        empleado.setId(idEmpleado);
        empleado.setNombre("Juan");
        empleado.setEmail("juan@mail.com");
        empleado.setUpdatedAt(LocalDateTime.now());
        empleado.setSupervisor(supervisor);

        when(empleadoCrudRepository.findById(idEmpleado))
                .thenReturn(Optional.of(empleado));

        when(empleadoCrudRepository.countBySupervisor_Id(idEmpleado))
                .thenReturn(0L);

        EmpleadoDetalle resultado = empleadoRepository.obtenerDetalles(idEmpleado);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        assertEquals("juan@mail.com", resultado.getEmail());
        assertEquals(99, resultado.getSupervisorId());
        assertEquals(0, resultado.getCantidadEmpleadosACargo());
    }

    @Test
    void obtenerDetalles_empleadoNoExiste() {

        Integer idEmpleado = 1;

        when(empleadoCrudRepository.findById(idEmpleado))
                .thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            empleadoRepository.obtenerDetalles(idEmpleado);
        });
    }

    @Test
    void obtenerDetalles_conEmpleadosACargo() throws InterruptedException {

        Integer jefeId = 1;

        EmpleadoEntity jefe = new EmpleadoEntity();
        jefe.setId(jefeId);
        jefe.setNombre("Horacio");
        jefe.setEmail("horacio@mail.com");

        EmpleadoEntity emp1 = new EmpleadoEntity();
        emp1.setId(2);

        EmpleadoEntity emp2 = new EmpleadoEntity();
        emp2.setId(3);

        when(empleadoCrudRepository.findById(jefeId))
                .thenReturn(Optional.of(jefe));

        when(empleadoCrudRepository.countBySupervisor_Id(jefeId))
                .thenReturn(2L);

        when(empleadoCrudRepository.findBySupervisor_Id(jefeId))
                .thenReturn(List.of(emp1, emp2));

        when(empleadoCrudRepository.countBySupervisor_Id(2))
                .thenReturn(0L);

        when(empleadoCrudRepository.countBySupervisor_Id(3))
                .thenReturn(0L);

        EmpleadoDetalle resultado = empleadoRepository.obtenerDetalles(jefeId);

        assertEquals(2, resultado.getCantidadEmpleadosACargo());
    }
}