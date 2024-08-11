package com.Gastos.service;

import com.Gastos.exception.ResourceNotFoundException;
import com.Gastos.model.Income;
import com.Gastos.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {
    @Autowired
    private IncomeRepository incomeRepository;

    public Income saveIncome(Income income) {
        return incomeRepository.save(income);
    }

    public Income getIncomeById(String id) {
        return incomeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Income not found with id " + id));
    }

    public List<Income> getAllIncomes() {
        return incomeRepository.findAll();
    }

    public Income updateIncome(String id, Income incomeDetails) {
        Income income = getIncomeById(id);
        income.setSource(incomeDetails.getSource());
        income.setAmount(incomeDetails.getAmount());
        income.setDate(incomeDetails.getDate());
        return incomeRepository.save(income);
    }

    public void deleteIncome(String id) {
        Income income = getIncomeById(id);
        incomeRepository.delete(income);
    }
}
