package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.domain.model.SaldoModel;
import io.github.athirson010.financialPlanning.repository.SaldoRepository;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;


@Service
public class SaldoService extends AbstractService<SaldoModel, SaldoRepository> {

    public SaldoService(SaldoRepository repository) {
        super(SaldoModel.class, repository);
    }
    AutenticacaoService autenticacaoService;
    GastoService gastoService;
    BigDecimal saldoMensal;
    private final List<Criteria> criterias = new ArrayList<>();

    @Override
    public SaldoModel save(SaldoModel model) {
        model.setUsuario(autenticacaoService.buscarUsuarioLogado());
        return super.save(model);
    }

    public List<SaldoModel> buscarExtratoMensal(LocalDate data) {
        int ultimoDiadoMes = buscarUltimoDiaDoMes(data);

        criterias.add(new Criteria("usuario").is(autenticacaoService.buscarUsuarioLogado()));
        criterias.add(new Criteria("data").gte(data.withDayOfMonth(1)));
        criterias.add(new Criteria("data").lte(data.withDayOfMonth(ultimoDiadoMes)));

        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[]{})));

        return mongoTemplate.find(query, SaldoModel.class);
    }

    public BigDecimal buscarSaldoMensal(LocalDate data) {

        int ultimoDiadoMes = buscarUltimoDiaDoMes(data);

        criterias.add(new Criteria("usuario").is(autenticacaoService.buscarUsuarioLogado()));
        criterias.add(new Criteria("data").gte(data.withDayOfMonth(1)));
        criterias.add(new Criteria("data").lte(data.withDayOfMonth(ultimoDiadoMes)));

        Query query = new Query();
        query.addCriteria(new Criteria().andOperator(criterias.toArray(new Criteria[]{})));

        mongoTemplate.find(query, SaldoModel.class).forEach(saldo -> {
            saldoMensal = saldoMensal.add(saldo.getValor());
        });

        mongoTemplate.find(query, GastoModel.class).forEach(gasto -> {
            saldoMensal = saldoMensal.subtract(gasto.getValor());
        });

        return saldoMensal;
    }

    int buscarUltimoDiaDoMes(LocalDate data) {
        YearMonth month = YearMonth.from(data);
        return month.atEndOfMonth().getDayOfMonth();
    }

}
