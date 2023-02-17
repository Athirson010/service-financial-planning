package io.github.athirson010.financialPlanning.jwt;

import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
@Slf4j
public class JwtService {
    @Value("${security.jwt.expiracao}")
    String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    String chaveAssinatura;

    public String gerarToken(UsuarioModel usuario) {
        long expString = Long.parseLong(expiracao);
        log.info("gerando token...");
        return Jwts
                .builder()
                .setSubject(usuario.getEmail())
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(expString).toInstant()))
                .signWith(SignatureAlgorithm.HS512, chaveAssinatura)
                .compact();
    }

    private Claims obterClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(chaveAssinatura)
                .parseClaimsJws(token)
                .getBody();
    }

    public Boolean tokenValido(String token) {
        try {
            obterClaims(token);
            log.info("token valido");
            return true;
        } catch (ExpiredJwtException e) {
            log.error("token invalido");
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return obterClaims(token).getSubject();
    }
}
