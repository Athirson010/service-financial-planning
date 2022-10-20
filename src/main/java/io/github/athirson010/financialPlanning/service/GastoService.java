package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import io.github.athirson010.financialPlanning.repository.GastoRespository;

public class GastoService extends AbstractService<GastoModel, GastoRespository> {
    public GastoService(GastoRespository repository) {
        super(GastoModel.class, repository);
    }
}
