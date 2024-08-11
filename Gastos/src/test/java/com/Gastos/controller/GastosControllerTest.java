package com.Gastos.controller;

import com.Gastos.model.Gastos;
import com.Gastos.service.GastosService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GastosControllerTest {

    @InjectMocks
    private GastosController gastosController;

    @Mock
    private GastosService gastosService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateExpense() {
        Gastos expense = new Gastos();
        expense.setId(1L);

        when(gastosService.saveExpense(any(Gastos.class))).thenReturn(expense);

        Gastos createdExpense = gastosController.createExpense(expense);

        assertEquals(expense, createdExpense);
        verify(gastosService, times(1)).saveExpense(expense);
    }

    @Test
    void testGetExpenseById() {
        Gastos expense = new Gastos();
        expense.setId(1L);

        when(gastosService.getExpenseById(1L)).thenReturn(expense);

        Gastos foundExpense = gastosController.getExpenseById(1L);

        assertEquals(expense, foundExpense);
        verify(gastosService, times(1)).getExpenseById(1L);
    }

    @Test
    void testUpdateExpense() {
        Gastos expense = new Gastos();
        expense.setId(1L);
        Gastos updatedExpenseDetails = new Gastos();
        updatedExpenseDetails.setAmount(500.0);

        when(gastosService.updateExpense(1L, updatedExpenseDetails)).thenReturn(updatedExpenseDetails);

        Gastos updatedExpense = gastosController.updateExpense(1L, updatedExpenseDetails);

        assertEquals(updatedExpenseDetails.getAmount(), updatedExpense.getAmount());
        verify(gastosService, times(1)).updateExpense(1L, updatedExpenseDetails);
    }

    @Test
    void testDeleteExpense() {
        doNothing().when(gastosService).deleteExpense(1L);

        ResponseEntity<Void> response = gastosController.deleteExpense(1L);

        assertEquals(ResponseEntity.noContent().build(), response);
        verify(gastosService, times(1)).deleteExpense(1L);
    }

    @Test
    void testGetAllExpenses() {
        Gastos expense1 = new Gastos();
        expense1.setId(1L);
        Gastos expense2 = new Gastos();
        expense2.setId(2L);

        List<Gastos> expenses = Arrays.asList(expense1, expense2);

        when(gastosService.getAllExpenses()).thenReturn(expenses);

        List<Gastos> foundExpenses = gastosController.getAllExpenses();

        assertEquals(expenses, foundExpenses);
        verify(gastosService, times(1)).getAllExpenses();
    }
}

