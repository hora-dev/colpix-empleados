package com.colpix.empleados.infraestructure.mapper;

import com.colpix.empleados.domain.Usuario;
import com.colpix.empleados.infraestructure.dto.UsuarioDTO;
import com.colpix.empleados.infraestructure.entity.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toUsuario(UsuarioEntity usuarioEntity);

    @Mapping(target = "id", ignore = true)
    UsuarioEntity toUsuarioEntity(Usuario usuario);
    Usuario toUsuarioFromDTO(UsuarioDTO usuarioDTO);
    UsuarioDTO toUsuarioDTO(Usuario usuario);
}
