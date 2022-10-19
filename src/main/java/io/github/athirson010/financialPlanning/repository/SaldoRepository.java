package io.github.athirson010.financialPlanning.repository;

import io.github.athirson010.financialPlanning.domain.model.SaldoModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SaldoRepository extends MongoRepository<SaldoModel, String> {
}
