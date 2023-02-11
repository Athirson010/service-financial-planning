package io.github.athirson010.financialPlanning.mapper;

import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import io.github.athirson010.financialPlanning.domain.model.usuario.dto.UsuarioModelDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
class UsuarioMapperTest {
    @Mock
    private ModelMapper modelMapper;

    @MockBean
    UsuarioMapper mapper;

    private UsuarioModelDTO usuarioModelDTO;
    private UsuarioModel usuarioModel;

    @BeforeEach
    public void setUp() {
        usuarioModelDTO = UsuarioModelDTO.builder()
                .id("1")
                .email("email@email.com")
                .dataNascimento(LocalDate.now())
                .nome("teste")
                .build();
        usuarioModel = new UsuarioModel("email@example.com", "teste", "12345678", false, LocalDate.now());
    }

    @Test
    public void testToUsuarioModelDTO() {
        UsuarioModelDTO result = mapper.toUsuarioModelDTO(usuarioModel);
        assertEquals(usuarioModelDTO, result);
    }

}