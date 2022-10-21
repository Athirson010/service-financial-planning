package io.github.athirson010.financialPlanning.repository;

import io.github.athirson010.financialPlanning.domain.model.MetaModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MetaRepository extends MongoRepository<MetaModel, String> {
}
