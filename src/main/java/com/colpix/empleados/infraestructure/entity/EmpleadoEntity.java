package com.colpix.empleados.infraestructure.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "empleados")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EmpleadoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String email;
    private LocalDateTime updatedAt;


    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    private EmpleadoEntity supervisor;
}
