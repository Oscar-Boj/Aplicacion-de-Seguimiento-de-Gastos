package com.Gastos.repository;

import com.Gastos.model.Income;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IncomeRepository extends MongoRepository<Income, String> {
}
