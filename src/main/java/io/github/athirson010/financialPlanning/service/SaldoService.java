package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.domain.model.SaldoModel;
import io.github.athirson010.financialPlanning.repository.SaldoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;


@Service
public class SaldoService extends AbstractService<SaldoModel, SaldoRepository> {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    GastoService gastoService;
    Double saldoMensal = 0.0;
    private List<Criteria> criterias = new ArrayList<>();

    public SaldoService(SaldoRepository repository) {
        super(SaldoModel.class, repository);
    }

    @Override
    public SaldoModel save(SaldoModel model) {
        model.setUsuario(usuarioService.buscarUsuarioLogado());
        return super.save(model);
    }

    public List<SaldoModel> buscarExtratoMensal(LocalDate data) {

        YearMonth month = YearMonth.from(data);
        int ultimoDiadoMes = month.atEndOfMonth().getDayOfMonth();

        criterias.add(new Criteria("usuario").is(usuarioService.buscarUsuarioLogado()));
        criterias.add(new Criteria("data").gte(data.withDayOfMonth(1)));
        criterias.add(new Criteria("data").lte(data.withDayOfMonth(ultimoDiadoMes)));

        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[]{})));

        return mongoTemplate.find(query, SaldoModel.class);
    }

    public Double buscarSaldoMensal(LocalDate data) {
        YearMonth month = YearMonth.from(data);
        int ultimoDiadoMes = month.atEndOfMonth().getDayOfMonth();

        criterias.add(new Criteria("usuario").is(usuarioService.buscarUsuarioLogado()));
        criterias.add(new Criteria("data").gte(data.withDayOfMonth(1)));
        criterias.add(new Criteria("data").lte(data.withDayOfMonth(ultimoDiadoMes)));

        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[]{})));

        mongoTemplate.find(query, SaldoModel.class).forEach(saldo -> {
            saldoMensal = saldoMensal + saldo.getValor();
        });

        mongoTemplate.find(query, GastoModel.class).forEach(gasto -> {
            saldoMensal = saldoMensal - gasto.getValor();
        });

        return saldoMensal;
    }

}
