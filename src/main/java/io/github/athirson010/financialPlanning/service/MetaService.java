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
public class MetaService extends AbstractService<MetaModel, MetaRepository> {

    GastoService gastoService;
    AutenticacaoService autenticacaoService;

    public MetaService(Class<MetaModel> beanClass,
                       MetaRepository repository,
                       GastoService gastoService,
                       AutenticacaoService autenticacaoService) {
        super(beanClass, repository);

        this.gastoService = gastoService;
        this.autenticacaoService = autenticacaoService;
    }

    public MetaService(MetaRepository repository) {
        super(MetaModel.class, repository);
    }

    @Override
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
