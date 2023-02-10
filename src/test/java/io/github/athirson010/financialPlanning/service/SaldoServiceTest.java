package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.SaldoModel;
import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import io.github.athirson010.financialPlanning.repository.SaldoRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class SaldoServiceTest {

    @InjectMocks
    private SaldoService saldoService;

    @Mock
    private SaldoRepository saldoRepository;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private GastoService gastoService;

    private static UsuarioModel usuarioModel;

    @BeforeAll
    static void beforeAll() {
        usuarioModel = new UsuarioModel("teste@teste.com", "teste", "12345678", false, LocalDate.now());
    }


    @Test
    void deve_salvar_um_saldo() {
        SaldoModel saldoModel = new SaldoModel("Salario", 100.0, usuarioModel, LocalDate.now());
        when(saldoRepository.save(any(SaldoModel.class))).thenReturn(saldoModel);

        SaldoModel saldoSalvo = saldoService.save(saldoModel);

        assertNotNull(saldoSalvo);
        assertEquals(100.0, saldoSalvo.getValor());
    }
}
