package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.dto.enums.Tipos;
import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.domain.model.SaldoModel;
import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import io.github.athirson010.financialPlanning.repository.SaldoRepository;
import io.github.athirson010.financialPlanning.repository.UsuarioRepository;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static io.github.athirson010.financialPlanning.domain.dto.enums.Tipos.CONTAS_MENSAIS;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SaldoServiceTest {
    @Mock
    SaldoRepository saldoRepository;

    @InjectMocks
    SaldoService saldoService;

    @MockBean
    UsuarioService usuarioService;

    @Mock
    MongoTemplate mongoTemplate;

    private static UsuarioModel usuarioModel;

    @BeforeAll
    static void beforeAll() {
        usuarioModel = new UsuarioModel("teste@teste.com", "teste", "12345678", false, LocalDate.now());
    }

    @Test
    void deve_salvar_um_saldo() {
        SaldoModel saldoModel = new SaldoModel("Salario", 100.0, usuarioModel, LocalDate.now());
        when(saldoRepository.save(any(SaldoModel.class))).thenReturn(saldoModel);
        when(usuarioService.buscarUsuarioLogado()).thenReturn(usuarioModel);
        SaldoModel saldoSalvo = saldoService.save(saldoModel);

        assertNotNull(saldoSalvo);
        assertEquals(100.0, saldoSalvo.getValor());
    }


        @Test
        void buscarExtratoMensal_ReturnsSaldoModels() {
            // Arrange
            LocalDate data = LocalDate.now();
            List<SaldoModel> expectedSaldoModels = Arrays.asList(
                    new SaldoModel("Salario", 10000.00, usuarioModel, LocalDate.now()),
                    new SaldoModel("Externo", 100.00, usuarioModel, LocalDate.now())
            );

            Mockito.when(mongoTemplate.find(Mockito.any(), Mockito.eq(SaldoModel.class)))
                    .thenReturn(expectedSaldoModels);

            // Act
            List<SaldoModel> actualSaldoModels = saldoService.buscarExtratoMensal(data);

            // Assert
            assertEquals(expectedSaldoModels, actualSaldoModels);
        }

        @Test
        void buscarSaldoMensal_ReturnsSaldo() {
            // Arrange
            LocalDate data = LocalDate.now();
            List<SaldoModel> saldoModels = Arrays.asList(
                    new SaldoModel("Salario", 100.0, usuarioModel, LocalDate.now()),
                    new SaldoModel("Externo", 200.0, usuarioModel, LocalDate.now())
            );
            List<GastoModel> gastoModels = Arrays.asList(
                    new GastoModel("Espelho", CONTAS_MENSAIS, LocalDate.now(), usuarioModel, 50.0, null),
                    new GastoModel("Espelho", CONTAS_MENSAIS, LocalDate.now(), usuarioModel, 75.0, null)
            );
            double expectedSaldo = 175.0;

            Mockito.when(mongoTemplate.find(Mockito.any(), Mockito.eq(SaldoModel.class)))
                    .thenReturn(saldoModels);
            Mockito.when(mongoTemplate.find(Mockito.any(), Mockito.eq(GastoModel.class)))
                    .thenReturn(gastoModels);

            // Act
            double actualSaldo = saldoService.buscarSaldoMensal(data);

            // Assert
            assertEquals(expectedSaldo, actualSaldo);
        }



}
