package com.Gastos.service;

import com.Gastos.exception.ResourceNotFoundException;
import com.Gastos.model.Gastos;
import com.Gastos.repository.GastosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GastosServiceTest {

    @InjectMocks
    private GastosService gastosService;

    @Mock
    private GastosRepository gastosRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveExpense() {
        Gastos expense = new Gastos();
        expense.setId(1L);

        when(gastosRepository.save(any(Gastos.class))).thenReturn(expense);

        Gastos savedExpense = gastosService.saveExpense(expense);

        assertEquals(expense, savedExpense);
        verify(gastosRepository, times(1)).save(expense);
    }

    @Test
    void testGetExpenseById() {
        Gastos expense = new Gastos();
        expense.setId(1L);

        when(gastosRepository.findById(1L)).thenReturn(Optional.of(expense));

        Gastos foundExpense = gastosService.getExpenseById(1L);

        assertEquals(expense, foundExpense);
        verify(gastosRepository, times(1)).findById(1L);
    }

    @Test
    void testGetExpenseByIdNotFound() {
        when(gastosRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            gastosService.getExpenseById(1L);
        });

        assertEquals("Expense not found with id 1", thrown.getMessage());
        verify(gastosRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllExpenses() {
        Gastos expense1 = new Gastos();
        expense1.setId(1L);
        Gastos expense2 = new Gastos();
        expense2.setId(2L);

        List<Gastos> expenses = Arrays.asList(expense1, expense2);

        when(gastosRepository.findAll()).thenReturn(expenses);

        List<Gastos> foundExpenses = gastosService.getAllExpenses();

        assertEquals(expenses, foundExpenses);
        verify(gastosRepository, times(1)).findAll();
    }

    @Test
    void testUpdateExpense() {
        Gastos existingExpense = new Gastos();
        existingExpense.setId(1L);
        existingExpense.setDescription("Old description");

        Gastos updatedExpenseDetails = new Gastos();
        updatedExpenseDetails.setDescription("New description");
        updatedExpenseDetails.setAmount(500.0);
        updatedExpenseDetails.setCategory("Food");
        updatedExpenseDetails.setDate("2024-08-01");

        when(gastosRepository.findById(1L)).thenReturn(Optional.of(existingExpense));
        when(gastosRepository.save(any(Gastos.class))).thenReturn(existingExpense);

        Gastos updatedExpense = gastosService.updateExpense(1L, updatedExpenseDetails);

        assertEquals("New description", updatedExpense.getDescription());
        assertEquals(500.0, updatedExpense.getAmount());
        assertEquals("Food", updatedExpense.getCategory());
        assertEquals("2024-08-01", updatedExpense.getDate());
        verify(gastosRepository, times(1)).findById(1L);
        verify(gastosRepository, times(1)).save(existingExpense);
    }

    @Test
    void testDeleteExpense() {
        Gastos expense = new Gastos();
        expense.setId(1L);

        when(gastosRepository.findById(1L)).thenReturn(Optional.of(expense));
        doNothing().when(gastosRepository).delete(expense);

        gastosService.deleteExpense(1L);

        verify(gastosRepository, times(1)).findById(1L);
        verify(gastosRepository, times(1)).delete(expense);
    }

    @Test
    void testDeleteExpenseNotFound() {
        when(gastosRepository.findById(1L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> {
            gastosService.deleteExpense(1L);
        });

        assertEquals("Expense not found with id 1", thrown.getMessage());
        verify(gastosRepository, times(1)).findById(1L);
    }
}

