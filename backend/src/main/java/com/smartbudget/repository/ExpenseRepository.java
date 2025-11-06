package com.smartbudget.repository;

import com.smartbudget.entity.Expense;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class ExpenseRepository implements PanacheRepository<Expense> {

    // Trouver les dépenses d'un utilisateur spécifique
    public List<Expense> findByUserName(String userName) {
        return find("userName = ?1", userName).list();
    }

    // Trouver les dépenses par défaut (sans utilisateur spécifique)
    public List<Expense> findDefaultExpenses() {
        return find("userName is null").list();
    }
}
