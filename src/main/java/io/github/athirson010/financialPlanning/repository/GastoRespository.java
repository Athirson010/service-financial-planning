package io.github.athirson010.financialPlanning.repository;

import io.github.athirson010.financialPlanning.domain.model.GastoModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastoRespository extends MongoRepository<GastoModel, String> {
}
