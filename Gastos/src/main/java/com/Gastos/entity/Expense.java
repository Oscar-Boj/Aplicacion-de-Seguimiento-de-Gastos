package com.Gastos.entity;

import com.Gastos.dto.ExpenseDto;
import jakarta.persistence.*;

@Entity
@Table(name = "expenses")
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private double amount;
    private String category;
    private String type; // income or expense

    public Expense(Long id, String description, double amount, String category, String type) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.type = type;
    }

    public Expense() {
    }

    public Expense(ExpenseDto expenseDto) {
        this.description = expenseDto.getDescription();
        this.amount = expenseDto.getAmount();
        this.category = expenseDto.getCategory();
        this.type = expenseDto.getType();
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public String getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setType(String type) {
        this.type = type;
    }
}
