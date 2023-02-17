package io.github.athirson010.financialPlanning.jwt;

import io.github.athirson010.financialPlanning.domain.model.usuario.UsuarioModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.Date;

@Service
public class JwtService {
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Value("${security.jwt.expiracao}")
    String expiracao;

    @Value("${security.jwt.chave-assinatura}")
    String chaveAssinatura;

    public String gerarToken(UsuarioModel usuario) {
        long expString = Long.parseLong(expiracao);
        logger.info("gerando token...");
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
            logger.info("token valido");
            return true;
        } catch (ExpiredJwtException e) {
            logger.error("token invalido");
            return false;
        }
    }

    public String obterLoginUsuario(String token) throws ExpiredJwtException {
        return obterClaims(token).getSubject();
    }
}
