package com.Gastos.service;

import com.Gastos.exception.ResourceNotFoundException;
import com.Gastos.model.Gastos;
import com.Gastos.repository.GastosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GastosService {
    @Autowired
    private GastosRepository expenseRepository;

    public Gastos saveExpense(Gastos expense) {
        return expenseRepository.save(expense);
    }

    public Gastos getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id " + id));
    }

    public List<Gastos> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Gastos updateExpense(Long id, Gastos expenseDetails) {
        Gastos expense = getExpenseById(id);
        expense.setDescription(expenseDetails.getDescription());
        expense.setAmount(expenseDetails.getAmount());
        expense.setCategory(expenseDetails.getCategory());
        expense.setDate(expenseDetails.getDate());
        return expenseRepository.save(expense);
    }

    public void deleteExpense(Long id) {
        Gastos expense = getExpenseById(id);
        expenseRepository.delete(expense);
    }
}
