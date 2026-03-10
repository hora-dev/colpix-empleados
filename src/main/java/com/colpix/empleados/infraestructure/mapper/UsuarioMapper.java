package com.colpix.empleados.infraestructure.mapper;

import com.colpix.empleados.domain.Usuario;
import com.colpix.empleados.infraestructure.entity.UsuarioEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toUsuario(UsuarioEntity usuarioEntity);
    UsuarioEntity toUsuarioEntity(Usuario usuario);
}
