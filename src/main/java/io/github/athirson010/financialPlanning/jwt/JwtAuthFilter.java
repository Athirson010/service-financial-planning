package io.github.athirson010.financialPlanning.jwt;

import io.github.athirson010.financialPlanning.service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UsuarioService usuarioService;

    public JwtAuthFilter(JwtService jwtService, UsuarioService usuarioService) {
        this.jwtService = jwtService;
        this.usuarioService = usuarioService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        String authorization = httpServletRequest.getHeader("Authorization");

        if (validarToken(authorization)) {
            String token = authorization.split(" ")[1];
            String loginUsuario = jwtService.obterLoginUsuario(token);
            UserDetails usuario = usuarioService.loadUserByUsername(loginUsuario);
            UsernamePasswordAuthenticationToken user = new
                    UsernamePasswordAuthenticationToken(usuario, null,
                    usuario.getAuthorities());
            user.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
            SecurityContextHolder.getContext().setAuthentication(user);
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean validarToken(String authorization) {
        if (authorization != null && authorization.startsWith("Bearer")) {
            String token = authorization.split(" ")[1];
            return jwtService.tokenValido(token);
        }
        return false;
    }
}
