package io.github.athirson010.financialPlanning.controller;

import io.github.athirson010.financialPlanning.controller.security.RestSecurity;
import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.exception.NaoEncontradoException;
import io.github.athirson010.financialPlanning.service.GastoService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping(value = "/gastos", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Gasto")
@RestController
public class GastosController extends RestSecurity {
    @Autowired
    private GastoService service;

    @PostMapping(path = "/")
    @ResponseStatus(CREATED)
    public GastoModel postCriarGasto(@Valid @RequestBody GastoModel gasto) {
        return service.save(gasto);
    }

    @PutMapping(path = "/{id}")
    public GastoModel putAtualizarGasto(@PathVariable String id, @Valid @RequestBody GastoModel gasto) {
        return service.update(id, gasto);
    }

    @GetMapping("/{id}")
    public GastoModel getGastoPorId(@PathVariable String id) {
        return service.findById(id).orElseThrow(() -> new NaoEncontradoException("Gasto"));
    }

    @DeleteMapping("/{id}")
    public void deleteGastoPorId(@PathVariable String id) {
        service.deleteById(id);
    }
}
