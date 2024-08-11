package com.Gastos.controller;

import com.Gastos.model.Gastos;
import com.Gastos.service.GastosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gastos")
public class GastosController {
    @Autowired
    private GastosService expenseService;

    @PostMapping
    public Gastos createExpense(@RequestBody Gastos expense) {
        return expenseService.saveExpense(expense);
    }

    @GetMapping("/{id}")
    public Gastos getExpenseById(@PathVariable Long id) {
        return expenseService.getExpenseById(id);
    }

    @PutMapping("/{id}")
    public Gastos updateExpense(@PathVariable Long id, @RequestBody Gastos expenseDetails) {
        return expenseService.updateExpense(id, expenseDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable Long id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public List<Gastos> getAllExpenses() {
        return expenseService.getAllExpenses();
    }
}
