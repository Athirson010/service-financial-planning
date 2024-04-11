package io.github.athirson010.financialPlanning.exception;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GerenciadorException {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Erro handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Erro errors = new Erro();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            errors.getErrosEncontrados().add(error.getDefaultMessage());
        });
        return errors;
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleValidationToken(ExpiredJwtException e) {
        return "token expirado" + e;
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleValidationToken(MalformedJwtException e) {
        return "token expirado" + e;
    }

}
