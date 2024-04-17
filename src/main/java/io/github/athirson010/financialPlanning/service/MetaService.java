package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.domain.model.MetaModel;
import io.github.athirson010.financialPlanning.repository.MetaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class MetaService extends AbstractService {
    private GastoService gastoService;
    private AutenticacaoService autenticacaoService;
    private MetaRepository repository;

    public MetaService(GastoService gastoService,
                       AutenticacaoService autenticacaoService,
                       MetaRepository repository) {
        super(MetaModel.class, repository);


        this.gastoService = gastoService;
        this.autenticacaoService = autenticacaoService;
        this.repository = repository;
    }


    public MetaModel save(MetaModel model) {
        model.setUsuario(autenticacaoService.buscarUsuarioLogado());
        super.save(model);
        criarGastosMensaisParaMeta(model);
        return model;
    }

    private void criarGastosMensaisParaMeta(MetaModel model) {
        buscarDatasPagamentoMensais(model).forEach(pagamento -> {
            gastoService.save(new GastoModel(model.getNome(), model.getTipo(), pagamento, model.getUsuario(),
                    (model.getValorBruto().divide(BigDecimal.valueOf(model.getParcelas()))), model));
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
