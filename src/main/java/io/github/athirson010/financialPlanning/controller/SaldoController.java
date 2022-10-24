package io.github.athirson010.financialPlanning.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.athirson010.financialPlanning.controller.security.RestSecurity;
import io.github.athirson010.financialPlanning.domain.model.SaldoModel;
import io.github.athirson010.financialPlanning.exception.NaoEncontradoException;
import io.github.athirson010.financialPlanning.service.SaldoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.time.LocalDate;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping(value = "/saldo", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Saldo")
@RestController
public class SaldoController extends RestSecurity {
    @Autowired
    private SaldoService service;

    @PostMapping(path = "/")
    @ResponseStatus(CREATED)
    public SaldoModel postCriarSaldo(@Valid @RequestBody SaldoModel saldo) {
        return service.save(saldo);
    }

    @PutMapping(path = "/{id}")
    public SaldoModel putAtualizarSaldo(@PathVariable String id, @Valid @RequestBody SaldoModel saldo) {
        return service.update(id, saldo);
    }

    @GetMapping("/{id}")
    public SaldoModel getSaldoPorId(@PathVariable String id) {
        return service.findById(id).orElseThrow(() -> new NaoEncontradoException("Saldo"));
    }

    @DeleteMapping("/{id}")
    public void deleteSaldoPorId(@PathVariable String id) {
        service.deleteById(id);
    }

    @GetMapping("consultar-saldo/{data}")
    public Double getSaltoMensal(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data ){ return service.buscarSaldoMensal(data);}


}
