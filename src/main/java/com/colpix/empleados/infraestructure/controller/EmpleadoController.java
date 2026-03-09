package com.colpix.empleados.infraestructure.controller;

import com.colpix.empleados.application.service.EmpleadoService;
import com.colpix.empleados.domain.Empleado;
import com.colpix.empleados.infraestructure.dto.EmpleadoACargoDTO;
import com.colpix.empleados.infraestructure.dto.EmpleadoDTO;
import com.colpix.empleados.infraestructure.dto.EmpleadoDetalleDTO;
import com.colpix.empleados.infraestructure.mapper.EmpleadoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/empleados")
@RequiredArgsConstructor
@Slf4j
public class EmpleadoController {

    private final EmpleadoMapper empleadoMapper;
    private final EmpleadoService empleadoService;

    @PostMapping("/")
    public ResponseEntity<EmpleadoDTO> crearEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
        Empleado empleado = empleadoMapper.toEmpleado(empleadoDTO);
        Empleado createdEmpleado = empleadoService.crearEmpleado(empleado);
        return ResponseEntity.ok().body(empleadoMapper.toEmpleadoDTO(createdEmpleado));
    }

    @PutMapping("/")
    public ResponseEntity<EmpleadoDTO> actualizarEmpleado(@RequestBody EmpleadoDTO empleadoDTO) {
        Empleado empleado = empleadoMapper.toEmpleado(empleadoDTO);
        Empleado empleadoActualizado = empleadoService.actualizarEmpleado(empleado);
        return ResponseEntity.ok().body(empleadoMapper.toEmpleadoDTO(empleadoActualizado));
    }

    @GetMapping("/")
    public ResponseEntity<List<EmpleadoDetalleDTO>> obtenerEmpleados() {
        return ResponseEntity.ok(empleadoMapper.toEmpleadoDetalleDTOList(empleadoService.obtenerDetalles()));

    }

    @GetMapping("/detalle/{id}")
    public ResponseEntity<EmpleadoACargoDTO> obtenerDetalle(@PathVariable Integer id) throws InterruptedException {
        return ResponseEntity.ok(empleadoMapper.toEmpleadosACargoDTO(empleadoService.obtenerDetalle(id)));
    }

}
