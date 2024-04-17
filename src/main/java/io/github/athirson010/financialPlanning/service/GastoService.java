package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.repository.GastoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GastoService {
    private MongoTemplate mongoTemplate;
    private GastoRespository gastoRespository;
    private UsuarioService usuarioService;
    private AutenticacaoService autenticacaoService;

    public GastoService(MongoTemplate mongoTemplate,
                        GastoRespository gastoRespository,
                        UsuarioService usuarioService,
                        AutenticacaoService autenticacaoService) {
        this.mongoTemplate = mongoTemplate;
        this.gastoRespository = gastoRespository;
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
