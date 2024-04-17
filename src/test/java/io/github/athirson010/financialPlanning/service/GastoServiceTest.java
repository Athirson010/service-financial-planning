package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import io.github.athirson010.financialPlanning.repository.GastoRespository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static io.github.athirson010.financialPlanning.domain.dto.enums.Tipos.DIVERSAO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class GastoServiceTest {
    @Mock
    private GastoRespository gastoRespository;
    @Mock
    private MongoTemplate template;
    @Mock
    private AutenticacaoService autenticacaoService;
    @InjectMocks
    private GastoService gastoService;
    private static UsuarioModel usuarioModel;
    private static GastoModel gastoModel;

    @BeforeAll
    static void beforeAll() {
        usuarioModel = new UsuarioModel("teste@teste.com", "teste", "12345678", LocalDate.now());
        gastoModel = new GastoModel("teste", DIVERSAO, LocalDate.now(), usuarioModel, BigDecimal.valueOf(10), null);
    }

//    @Test
//    public void testSave() {
//        gastoModel.setUsuario(null);
//        when(autenticacaoService.buscarUsuarioLogado()).thenReturn(usuarioModel);
//        when(gastoRespository.save(gastoModel)).thenReturn(gastoModel);
//
//        GastoModel result = (GastoModel) gastoService.save(gastoModel);
//
//        assertEquals(result, gastoModel);
//        assertEquals(result.getUsuario(), usuarioModel);
//    }

    @Test
    public void testBuscarGastoMensal() {

        List<GastoModel> gastoModels = new ArrayList<>();
        gastoModels.add(gastoModel);

        when(autenticacaoService.buscarUsuarioLogado()).thenReturn(usuarioModel);
        when(template.find(any(Query.class), any(Class.class))).thenReturn(gastoModels);

        List<GastoModel> result = gastoService.buscarGastoMensal(LocalDate.parse("2018-12-27"));

        assertEquals(result, gastoModels);
        assertEquals(result.size(), 1);
    }
}
