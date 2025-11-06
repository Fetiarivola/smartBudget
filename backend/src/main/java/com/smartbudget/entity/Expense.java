package com.smartbudget.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "expenses")
public class Expense {

     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     private Long id;

     @Column(nullable = false)
     private String userName;

     @Column(nullable = false)
     private String category;

     @Column(nullable = false)
     private BigDecimal percentage;

     public Expense() {
     }

     public Expense(String userName, String category, BigDecimal percentage) {
          this.userName = userName;
          this.category = category;
          this.percentage = percentage;
     }

     // --- Getters et Setters ---
     public Long getId() {
          return id;
     }

     public String getCategory() {
          return category;
     }

     public String getUserName() {
          return userName;
     }

     public void setUserName(String userName) {
          this.userName = userName;
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
}
