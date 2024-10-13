package com.Gastos;

import com.Gastos.controller.ExpenseController;
import com.Gastos.dto.ExpenseDto;
import com.Gastos.dto.SummaryDto;
import com.Gastos.entity.Expense;
import com.Gastos.exception.ExpenseNotFoundException;
import com.Gastos.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ExpenseControllerTest {
    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseController expenseController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateExpense() {
        // Crear el DTO con los datos del gasto
        ExpenseDto expenseDto = new ExpenseDto("Salary", 100.0, "Job", "income");

        // Crear la entidad a partir del DTO
        Expense expense = new Expense(expenseDto);

        // Simular el comportamiento del repositorio al guardar un gasto
        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        // Llamar al método del controlador para crear un gasto
        Expense createdExpense = expenseController.createExpense(expenseDto);

        // Verificar los valores devueltos
        assertEquals("Salary", createdExpense.getDescription());
        assertEquals(100.0, createdExpense.getAmount());
        assertEquals("Job", createdExpense.getCategory());
        assertEquals("income", createdExpense.getType());

        // Verificar que el método save del repositorio fue llamado una vez
        verify(expenseRepository, times(1)).save(any(Expense.class));
    }

    @Test
    void testFindById_ExistingExpense() {
        Expense expense = new Expense();
        expense.setId(1L);
        expense.setAmount(100.0);

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));

        Expense foundExpense = expenseController.findById(1L);

        assertNotNull(foundExpense);
        assertEquals(1L, foundExpense.getId());
        assertEquals(100.0, foundExpense.getAmount());
    }

    @Test
    void testFindById_NonExistingExpense() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ExpenseNotFoundException.class, () -> {
            expenseController.findById(1L);
        });
    }

    @Test
    void testGetSummary() {
        Expense income = new Expense();
        income.setAmount(200.0);
        income.setType("ingreso");

        Expense expense = new Expense();
        expense.setAmount(50.0);
        expense.setType("gasto");

        when(expenseRepository.findAll()).thenReturn(Arrays.asList(income, expense));

        SummaryDto summary = expenseController.getSummary();

        assertEquals(200.0, summary.getTotalIncome());
        assertEquals(50.0, summary.getTotalExpense());
    }

    @Test
    void testGetAllExpenses() {
        Expense expense1 = new Expense();
        Expense expense2 = new Expense();

        when(expenseRepository.findAll()).thenReturn(Arrays.asList(expense1, expense2));

        List<Expense> expenses = expenseController.getAllExpenses();

        assertEquals(2, expenses.size());
        verify(expenseRepository, times(1)).findAll();
    }
}
