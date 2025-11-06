package com.smartbudget.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "historisation_expenses")
public class HistorisationExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "historisation_id", nullable = false)
    @JsonIgnore // Évite la boucle infinie dans le JSON
    private Historisation historisation;

    @Column(nullable = false)
    private String userName; // NOUVEAU : Pour identifier facilement l'utilisateur

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private BigDecimal percentage;

    @Column(nullable = false)
    private Double calculatedAmount;

    public HistorisationExpense() {
    }

    public HistorisationExpense(Historisation historisation, String userName, String category, BigDecimal percentage,
            Double calculatedAmount) {
        this.historisation = historisation;
        this.userName = userName;
        this.category = category;
        this.percentage = percentage;
        this.calculatedAmount = calculatedAmount;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public Historisation getHistorisation() {
        return historisation;
    }

    public void setHistorisation(Historisation historisation) {
        this.historisation = historisation;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getPercentage() {
        return percentage;
    }

    public void setPercentage(BigDecimal percentage) {
        this.percentage = percentage;
    }

    public Double getCalculatedAmount() {
        return calculatedAmount;
    }

    public void setCalculatedAmount(Double calculatedAmount) {
        this.calculatedAmount = calculatedAmount;
    }
}