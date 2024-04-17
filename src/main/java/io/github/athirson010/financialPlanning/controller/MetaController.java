package io.github.athirson010.financialPlanning.controller;

import io.github.athirson010.financialPlanning.domain.model.MetaModel;
import io.github.athirson010.financialPlanning.exception.NaoEncontradoException;
import io.github.athirson010.financialPlanning.service.MetaService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping(value = "/metas", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Meta")
@RestController
@SecurityRequirement(name = "bearerAuth")
public class MetaController {

    private MetaService service;

    public MetaController(MetaService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public MetaModel postCriarMeta(@Valid @RequestBody MetaModel gasto) {
        return service.save(gasto);
    }

   @PutMapping(path = "/{id}")
    public MetaModel putAtualizarMeta(@PathVariable String id, @Valid @RequestBody MetaModel gasto) {
       return (MetaModel) service.update(id, gasto);
    }

    @GetMapping("/{id}")
    public MetaModel getMetaPorId(@PathVariable String id) throws Throwable {
        return (MetaModel) service.findById(id);
    }
    @DeleteMapping("/{id}")
    public void deleteMetaPorId(@PathVariable String id) {
        service.deleteById(id);
    }
}