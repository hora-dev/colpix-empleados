package com.colpix.empleados.infraestructure.adapter;

import com.colpix.empleados.application.repository.EmpleadoRepository;
import com.colpix.empleados.domain.Empleado;
import com.colpix.empleados.domain.EmpleadoDetalle;
import com.colpix.empleados.infraestructure.entity.EmpleadoEntity;
import com.colpix.empleados.infraestructure.mapper.EmpleadoMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Repository
@RequiredArgsConstructor
@Slf4j
public class EmpleadoRepositoryImpl implements EmpleadoRepository {
    private final EmpleadoCrudRepository empleadoCrudRepository;
    private final EmpleadoMapper empleadoMapper;


    @Override
    public Empleado crearEmpleado(Empleado empleado) {
        EmpleadoEntity empleadoEntity = empleadoMapper.toEmpleadoEntity(empleado);
        empleadoEntity.setUpdatedAt(LocalDateTime.now());
        if(empleado.getSupervisorId() != null) {
            EmpleadoEntity supervisorExistente = empleadoCrudRepository.findById(empleado.getSupervisorId())
                    .orElseThrow(() -> new RuntimeException("El supervisor no existe"));
            empleadoEntity.setSupervisor(supervisorExistente);
        } else {
            empleadoEntity.setSupervisor(null);
        }
        empleadoCrudRepository.save(empleadoEntity);
        return empleadoMapper.toEmpleado(empleadoEntity);
    }

    @Override
    public Empleado actualizarEmpleado(Empleado empleado) {
        EmpleadoEntity empleadoBD = empleadoCrudRepository.findById(empleado.getId()).orElse(null);
        if(!Objects.isNull(empleadoBD)) {
            EmpleadoEntity empleadoEntityAActualizar = empleadoMapper.toEmpleadoEntity(empleado);
            empleadoBD.setEmail(empleadoEntityAActualizar.getEmail());
            empleadoBD.setNombre(empleadoEntityAActualizar.getNombre());

            if (empleado.getSupervisorId() != null) {
                EmpleadoEntity supervisorFromDB = empleadoCrudRepository.findById(empleado.getSupervisorId())
                        .orElseThrow(() -> new EntityNotFoundException("Supervisor no encontrado"));
                empleadoBD.setSupervisor(supervisorFromDB);
            } else {
                empleadoBD.setSupervisor(null);
            }

            empleadoBD.setUpdatedAt(LocalDateTime.now());
            return empleadoMapper.toEmpleado(empleadoCrudRepository.save(empleadoBD));
        }
        throw new EntityNotFoundException("Empleado con id no encontrado " + empleado.getId());
    }

    private EmpleadoEntity buscarDetallesEmpleado(Integer idEmpleado) {
        Optional<EmpleadoEntity> empleadoFromDB = empleadoCrudRepository.findById(idEmpleado);
        if(!empleadoFromDB.isPresent()) {
            throw new EntityNotFoundException("No se encontro el empleado:" + idEmpleado);
        }
        return empleadoFromDB.get();
    }

    private Long buscarPersonasACargo(Integer idEmpleado) {

        Long cantDirectaACargo = empleadoCrudRepository.countBySupervisor_Id(idEmpleado);
        if(cantDirectaACargo.intValue() == 0) {
            return cantDirectaACargo;
        }

        List<EmpleadoEntity> empleadosACargo = empleadoCrudRepository.findBySupervisor_Id(idEmpleado);

        for (EmpleadoEntity empleado: empleadosACargo) {
            cantDirectaACargo = cantDirectaACargo + buscarPersonasACargo(empleado.getId());
        }
        return cantDirectaACargo;
    }

    @Override
    public EmpleadoDetalle obtenerDetalles(Integer idEmpleado) throws InterruptedException {

        AtomicReference<EmpleadoEntity> empleadoEntity = new AtomicReference<>();
        AtomicReference<Throwable> error = new AtomicReference<>();
        Thread t1 = Thread.ofVirtual().start(() -> {
            log.info("Buscando detalles del empleado");
            try {
                empleadoEntity.set(buscarDetallesEmpleado(idEmpleado));
            } catch(EntityNotFoundException e) {
                error.set(e);
            }

        });

        AtomicReference<Long> cantidadPersonasACargo = new AtomicReference<>();
        Thread t2 = Thread.ofVirtual().start(() -> {
            log.info("Buscando cantidad de personas a cargo");
            cantidadPersonasACargo.set(buscarPersonasACargo(idEmpleado));
        });

        t1.join();
        t2.join();

        if(!Objects.isNull(error.get())) {
            throw new EntityNotFoundException(error.get().getMessage());
        }

        log.info("Fin");
        return EmpleadoDetalle.builder()
                .id(!Objects.isNull(empleadoEntity.get()) ? empleadoEntity.get().getId() : null)
                .nombre(!Objects.isNull(empleadoEntity.get()) ? empleadoEntity.get().getNombre() : null)
                .email(!Objects.isNull(empleadoEntity.get()) ? empleadoEntity.get().getEmail() : null)
                .updatedAt(!Objects.isNull(empleadoEntity.get()) ? empleadoEntity.get().getUpdatedAt(): null)
                .supervisorId(!Objects.isNull(empleadoEntity.get()) && !Objects.isNull(empleadoEntity.get().getSupervisor()) ? empleadoEntity.get().getSupervisor().getId() : null)
                .cantidadEmpleadosACargo(!Objects.isNull(cantidadPersonasACargo.get()) ? cantidadPersonasACargo.get().intValue() : null).build();
    }

    @Override
    public List<Empleado> obtenerDetalle() {
        return empleadoMapper.toEmpleadoList(empleadoCrudRepository.findAll());
    }
}
