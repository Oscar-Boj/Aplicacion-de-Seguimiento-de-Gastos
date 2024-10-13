package com.Gastos.dto;

public class ExpenseDto {
    private String description;
    private double amount;
    private String category;
    private String type; // income or expense

    public ExpenseDto(String description, double amount, String category, String type) {
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
