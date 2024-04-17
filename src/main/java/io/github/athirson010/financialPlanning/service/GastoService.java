package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.repository.GastoRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GastoService extends AbstractService<GastoModel, GastoRespository> {
    public GastoService(Class<GastoModel> beanClass, GastoRespository repository) {
        super(beanClass, repository);
    }

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private AutenticacaoService autenticacaoService;
    private List<Criteria> criterias = new ArrayList<>();
    @Override
    public GastoModel save(GastoModel model) {
        model.setUsuario(autenticacaoService.buscarUsuarioLogado());
        return super.save(model);
    }
    public List<GastoModel> buscarGastoMensal(LocalDate data) {
        criterias.add(new Criteria("usuario").is(autenticacaoService.buscarUsuarioLogado()));
        criterias.add(new Criteria("data").gte(data.withDayOfMonth(1)));
        criterias.add(new Criteria("data").lte(data.withDayOfMonth(31)));

        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[]{})));

        return mongoTemplate.find(query, GastoModel.class);
    }
}
