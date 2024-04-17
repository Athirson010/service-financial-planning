package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.domain.model.MetaModel;
import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import io.github.athirson010.financialPlanning.repository.GastoRespository;
import io.github.athirson010.financialPlanning.repository.MetaRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;

import static io.github.athirson010.financialPlanning.domain.dto.enums.Tipos.DIVERSAO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MetaServiceTest {
    @Mock
    MetaRepository metaRepository;

    @Mock
    GastoRespository gastoRespository;

    @MockBean
    GastoService gastoService;

    @MockBean
    AutenticacaoService autenticacaoService;

    @InjectMocks
    MetaService metaService;

    private static MetaModel metaModel;
    private static UsuarioModel usuarioModel;
    private static GastoModel gastoModel;

    @BeforeAll
    static void beforeAll() {
        usuarioModel = new UsuarioModel("teste@teste.com", "teste", "12345678", LocalDate.now());
        metaModel = new MetaModel("Viagem", "Viagem", BigDecimal.valueOf(1000.0), DIVERSAO, 10, LocalDate.now(), usuarioModel);
        gastoModel = new GastoModel("teste", DIVERSAO, LocalDate.now(), usuarioModel, BigDecimal.valueOf(10.0), null);
    }

    @Test
    void deve_salvar_uma_meta() {
        when(gastoService.save(Mockito.any(GastoModel.class))).thenReturn(gastoModel);
        when(autenticacaoService.buscarUsuarioLogado()).thenReturn(usuarioModel);

        MetaModel metaSalva = metaService.save(metaModel);

        verify(gastoService, times(10)).save(Mockito.any(GastoModel.class));
        assertThat(metaSalva).isNotNull();
        assertThat(metaSalva.getNome()).isEqualTo("Viagem");
        assertThat(metaSalva.getTipo()).isEqualTo(DIVERSAO);
        assertThat(metaSalva.getValorBruto()).isEqualTo(1000.0);
        assertThat(metaSalva.getParcelas()).isEqualTo(10);
    }

    @Test
    void deve_criar_10_gastos_para_uma_meta_com_parcelas_iguais_a_10() {
        when(gastoService.save(Mockito.any(GastoModel.class))).thenReturn(gastoModel);
        metaService.save(metaModel);
        verify(gastoService, times(10)).save(Mockito.any(GastoModel.class));
    }
}