package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.domain.model.SaldoModel;
import io.github.athirson010.financialPlanning.repository.GastoRespository;
import io.github.athirson010.financialPlanning.repository.SaldoRepository;

public class GastoService extends AbstractService<GastoModel, GastoRespository> {
    public GastoService(GastoRespository repository) {
        super(GastoModel.class, repository);
    }
}
