package com.colpix.empleados.infraestructure;

import com.colpix.empleados.application.service.EmpleadoService;
import com.colpix.empleados.domain.Empleado;
import com.colpix.empleados.infraestructure.dto.EmpleadoDTO;
import com.colpix.empleados.infraestructure.mapper.EmpleadoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
@Slf4j
public class EmpleadoController {

    private final EmpleadoMapper empleadoMapper;
    private final EmpleadoService empleadoService;

    @PostMapping("/")
    public ResponseEntity<Empleado> crearEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
        Empleado empleado = empleadoMapper.toEmpleado(empleadoDTO);
        Empleado createdEmpleado = empleadoService.crearEmpleado(empleado);
        return ResponseEntity.ok().body(createdEmpleado);
    }

    @PutMapping("/")
    public ResponseEntity<Empleado> actualizarEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
        Empleado empleado = empleadoMapper.toEmpleado(empleadoDTO);
        Empleado empleadoActualizado = empleadoService.actualizarEmpleado(empleado);
        return ResponseEntity.ok().body(empleadoActualizado);
    }
}
