package com.colpix.empleados.infraestructure.dto;

import lombok.Data;

@Data
public class UsuarioDTO {
    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
}
