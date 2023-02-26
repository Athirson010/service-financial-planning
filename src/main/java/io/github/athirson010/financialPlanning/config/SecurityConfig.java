package io.github.athirson010.financialPlanning.config;

import io.github.athirson010.financialPlanning.jwt.JwtAuthFilter;
import io.github.athirson010.financialPlanning.jwt.JwtService;
import io.github.athirson010.financialPlanning.jwt.NoPopupBasicAuthenticationEntryPoint;
import io.github.athirson010.financialPlanning.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UsuarioService usuarioService;
    @Autowired
    JwtService jwtService;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public OncePerRequestFilter jwtFilter() {
        return new JwtAuthFilter(jwtService, usuarioService);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(usuarioService)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http

                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/swagger-ui/**").permitAll()
                .antMatchers("/v3/api-docs/**").permitAll()
                .antMatchers(HttpMethod.POST, "/usuarios/autenticar").permitAll()
                .antMatchers(HttpMethod.POST, "/usuarios").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/health").permitAll()
                .and()
                .csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic()
                .authenticationEntryPoint(new NoPopupBasicAuthenticationEntryPoint())
                .and()
                .addFilterBefore(jwtFilter(), UsernamePasswordAuthenticationFilter.class);
        ;
    }
}
