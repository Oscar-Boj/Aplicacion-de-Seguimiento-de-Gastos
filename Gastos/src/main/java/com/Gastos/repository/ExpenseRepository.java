package com.Gastos.repository;

import com.Gastos.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findByCategory(String category);

    List<Expense> findByType(String type);
}
