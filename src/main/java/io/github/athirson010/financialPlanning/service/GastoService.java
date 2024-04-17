package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.repository.GastoRespository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GastoService extends AbstractService {
    private GastoRespository respository;
    private UsuarioService usuarioService;
    private AutenticacaoService autenticacaoService;

    public GastoService(GastoRespository respository,
                        UsuarioService usuarioService,
                        AutenticacaoService autenticacaoService) {
        super(GastoModel.class, respository);
        this.repository = respository;
        this.usuarioService = usuarioService;
        this.autenticacaoService = autenticacaoService;
    }

    private List<Criteria> criterias = new ArrayList<>();

    public List<GastoModel> buscarGastoMensal(LocalDate data) {
        criterias.add(new Criteria("usuario").is(autenticacaoService.buscarUsuarioLogado()));
        criterias.add(new Criteria("data").gte(data.withDayOfMonth(1)));
        criterias.add(new Criteria("data").lte(data.withDayOfMonth(31)));

        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[]{})));

        return mongoTemplate.find(query, GastoModel.class);
    }
}
