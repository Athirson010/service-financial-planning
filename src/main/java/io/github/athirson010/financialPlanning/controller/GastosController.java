package io.github.athirson010.financialPlanning.controller;

import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.service.GastoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RequestMapping(value = "/gastos", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Gasto")
@RestController
@SecurityRequirement(name = "bearerAuth")
public class GastosController {

    private GastoService service;

    public GastosController(GastoService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public GastoModel postCriarGasto(@Valid @RequestBody GastoModel gasto) {
        return (GastoModel) service.save(gasto);
    }

    @PutMapping(path = "/{id}")
    public GastoModel putAtualizarGasto(@PathVariable String id, @Valid @RequestBody GastoModel gasto) {
        return (GastoModel) service.update(id, gasto);
    }

    @GetMapping("/{id}")
    public GastoModel getGastoPorId(@PathVariable String id) {
        return (GastoModel) service.findById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteGastoPorId(@PathVariable String id) {
        service.deleteById(id);
    }


    @GetMapping("consultar-gasto-mensal/{data}")
    public List<GastoModel> getGastosMensal(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return service.buscarGastoMensal(data);
    }
}
