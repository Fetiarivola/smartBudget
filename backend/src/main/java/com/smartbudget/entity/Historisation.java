package com.smartbudget.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "historisations")
public class Historisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double salaryAmount;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String currency;

    @OneToMany(mappedBy = "historisation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<HistorisationExpense> expenses;

    @Column(nullable = false)
    private Double totalExpenses;

    @Column(nullable = false)
    private Double remainingAmount;

    public Historisation() {
    }

    public Historisation(String name, Double salaryAmount, LocalDate date, String currency) {
        this.name = name;
        this.salaryAmount = salaryAmount;
        this.date = date;
        this.currency = currency;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalaryAmount() {
        return salaryAmount;
    }

    public void setSalaryAmount(Double salaryAmount) {
        this.salaryAmount = salaryAmount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public List<HistorisationExpense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<HistorisationExpense> expenses) {
        this.expenses = expenses;
    }

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Double getRemainingAmount() {
        return remainingAmount;
    }

    public void setRemainingAmount(Double remainingAmount) {
        this.remainingAmount = remainingAmount;
    }
}