package com.colpix.empleados.domain;

import lombok.Data;

@Data
public class Usuario {
    private Integer id;
    private String username;
    private String password;
    private Boolean enabled;
}
