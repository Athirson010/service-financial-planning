package io.github.athirson010.financialPlanning.mapper;

import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import io.github.athirson010.financialPlanning.domain.model.usuario.dto.UsuarioModelDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioMapper {

    @Autowired
    ModelMapper modelMapper;

    public UsuarioModelDTO toUsuarioModelDTO(UsuarioModel usuarioModel) {
        return modelMapper.map(usuarioModel, UsuarioModelDTO.class);
    }

    public UsuarioModel toUsuarioModel(UsuarioModelDTO dto) {
        return modelMapper.map(dto, UsuarioModel.class);
    }
}
