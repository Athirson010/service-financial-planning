package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import io.github.athirson010.financialPlanning.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class UsuarioServiceTest {
    @Mock
    private UsuarioRepository repository;

    @Mock
    private PasswordEncoder encoder;

    @InjectMocks
    private UsuarioService usuarioService;

    @Test
    void criarUsuario_UsuarioJaCadastrado_DeveLancarException() {
        // Arrange
        UsuarioModel usuario = new UsuarioModel();
        usuario.setEmail("email@email.com");
        when(repository.findByEmail(any())).thenReturn(Optional.of(usuario));

        // Act & Assert
        assertThrows(ResponseStatusException.class, () -> usuarioService.criarUsuario(usuario));
    }

    @Test
    void criarUsuario_UsuarioNaoCadastrado_DeveSalvarUsuario() {
        // Arrange
        UsuarioModel usuario = new UsuarioModel();
        usuario.setEmail("email@email.com");
        when(repository.findByEmail(any())).thenReturn(Optional.empty());
        when(encoder.encode(any())).thenReturn("senhaCriptografada");
        // Act
        usuarioService.criarUsuario(usuario);

        // Assert
        verify(repository).save(usuario);
    }

}