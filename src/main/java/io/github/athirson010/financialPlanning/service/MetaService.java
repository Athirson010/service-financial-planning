package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.MetaModel;
import io.github.athirson010.financialPlanning.repository.MetaRepository;

public class MetaService extends AbstractService<MetaModel, MetaRepository>{

    public MetaService(MetaRepository repository) {
        super(MetaModel.class, repository);
    }


}
