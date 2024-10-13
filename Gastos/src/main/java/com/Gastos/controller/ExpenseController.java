package com.Gastos.controller;

import com.Gastos.dto.ExpenseDto;
import com.Gastos.dto.SummaryDto;
import com.Gastos.entity.Expense;
import com.Gastos.exception.ExpenseNotFoundException;
import com.Gastos.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/expense")
public class ExpenseController {
    private final ExpenseRepository expenseRepository;

    public ExpenseController(@Autowired ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    // Crear nuevo gasto o ingreso
    @PostMapping
    public Expense createExpense(@RequestBody ExpenseDto expenseDto) {
        Expense expense = new Expense(expenseDto);
        return expenseRepository.save(expense);
    }

    // Buscar gasto o ingreso por ID
    @GetMapping("/{id}")
    public Expense findById(@PathVariable Long id) {
        Optional<Expense> optionalExpense = expenseRepository.findById(id);
        if (optionalExpense.isPresent())
            return optionalExpense.get();
        else throw new ExpenseNotFoundException();
    }

    // Obtener todos los gastos e ingresos
    @GetMapping
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    // Filtrar por categoría
    @GetMapping("/category/{category}")
    List<Expense> findByCategory(@PathVariable String category) {
        return expenseRepository.findByCategory(category);
    }

    // Filtrar por tipo (ingreso o gasto)
    @GetMapping("/type/{type}")
    List<Expense> findByType(@PathVariable String type) {
        return expenseRepository.findByType(type);
    }

    // Obtener resumen total de ingresos y gastos
    @GetMapping("/summary")
    public SummaryDto getSummary() {
        List<Expense> allExpenses = expenseRepository.findAll();

        double totalIncome = allExpenses.stream()
                .filter(expense -> "ingreso".equalsIgnoreCase(expense.getType()))
                .mapToDouble(Expense::getAmount)
                .sum();

        double totalExpense = allExpenses.stream()
                .filter(expense -> "gasto".equalsIgnoreCase(expense.getType()))
                .mapToDouble(Expense::getAmount)
                .sum();

        return new SummaryDto(totalIncome, totalExpense);
    }
}
