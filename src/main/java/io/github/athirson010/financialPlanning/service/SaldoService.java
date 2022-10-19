package io.github.athirson010.financialPlanning.service;

import io.github.athirson010.financialPlanning.domain.model.SaldoModel;
import io.github.athirson010.financialPlanning.repository.SaldoRepository;
import org.springframework.stereotype.Service;


@Service
public class SaldoService extends AbstractService<SaldoModel, SaldoRepository> {

    public SaldoService(SaldoRepository repository) {
        super(SaldoModel.class, repository);
    }


}
