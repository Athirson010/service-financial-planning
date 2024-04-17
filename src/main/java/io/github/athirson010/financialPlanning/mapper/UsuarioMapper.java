package io.github.athirson010.financialPlanning.mapper;


import io.github.athirson010.financialPlanning.domain.dto.autenticacao.UsuarioTokenDTO;
import io.github.athirson010.financialPlanning.domain.dto.usuario.UsuarioCriacaoDTO;
import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;

public class UsuarioMapper {

    public static UsuarioModel of(UsuarioCriacaoDTO usuarioCriacaoDto) {
        UsuarioModel usuario = new UsuarioModel();

        usuario.setEmail(usuarioCriacaoDto.getEmail());
        usuario.setNome(usuarioCriacaoDto.getNome());
        usuario.setSenha(usuarioCriacaoDto.getSenha());

        return usuario;
    }

    public static UsuarioTokenDTO of(UsuarioModel usuario, String token) {
        UsuarioTokenDTO usuarioTokenDto = new UsuarioTokenDTO();

        usuarioTokenDto.setUserId(Long.valueOf(usuario.getId()));
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setToken(token);

        return usuarioTokenDto;
    }
}
