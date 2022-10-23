package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.dto.enums.Tipos;
import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.domain.model.MetaModel;
import io.github.athirson010.financialPlanning.repository.MetaRepository;
import org.bson.io.BsonOutput;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Meta;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MetaService extends AbstractService<MetaModel, MetaRepository>{

    @Autowired
    private GastoService gastoService;

    public MetaService(MetaRepository repository) {
        super(MetaModel.class, repository);
    }

    @Override
    public MetaModel save(MetaModel model) {
        save(model);
        criarGastosMensaisParaMeta(model);
        return model;
    }

    private void criarGastosMensaisParaMeta(MetaModel model) {
        buscarDatasPagamentoMensais(model).forEach(pagamento -> {
            GastoModel gasto = new GastoModel(model.getNome(),model.getTipo(), pagamento, model.getUsuarioModel(), (model.getValorBruto() / model.getParcelas()), model);
            gastoService.save(gasto);
        });
    }

    private List<LocalDate> buscarDatasPagamentoMensais(MetaModel model) {
        List<LocalDate> datasPagamento = new ArrayList<>();
        for (int i = 0; i < model.getParcelas(); i++) {
            datasPagamento.add(model.getInicioPagamento().plusMonths(i));
        }
        return datasPagamento;
    }


}
