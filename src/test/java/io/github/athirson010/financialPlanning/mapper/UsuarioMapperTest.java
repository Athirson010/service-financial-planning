package io.github.athirson010.financialPlanning.mapper;

import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import io.github.athirson010.financialPlanning.domain.model.usuario.dto.UsuarioModelDTO;
import io.github.athirson010.financialPlanning.mapper.UsuarioMapper;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class UsuarioMapperTest {

    @Autowired
    private UsuarioMapper usuarioMapper;

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void testToUsuarioModelDTO() {
        UsuarioModel usuarioModel = new UsuarioModel("email@email.com", "user", "senha", false, LocalDate.now());

        UsuarioModelDTO usuarioModelDTO = usuarioMapper.toUsuarioModelDTO(usuarioModel);

        assertEquals(usuarioModel.getNome(), usuarioModelDTO.getNome());
        assertEquals(usuarioModel.getEmail(), usuarioModelDTO.getEmail());
       }

    @Test
    public void testToUsuarioModel() {
        UsuarioModelDTO usuarioModelDTO = new UsuarioModelDTO("user", "email@email.com", "teste", LocalDate.now());
        UsuarioModel usuarioModel = usuarioMapper.toUsuarioModel(usuarioModelDTO);

        assertEquals(usuarioModelDTO.getNome(), usuarioModel.getNome());
        assertEquals(usuarioModelDTO.getEmail(), usuarioModel.getEmail());
    }
}
