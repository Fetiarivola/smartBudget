package com.smartbudget.repository;

import com.smartbudget.entity.HistorisationExpense;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class HistorisationExpenseRepository implements PanacheRepository<HistorisationExpense> {

    // Trouver toutes les dépenses d'un utilisateur spécifique
    public List<HistorisationExpense> findByUserName(String userName) {
        return find("userName = ?1", userName).list();
    }

    // Trouver les dépenses d'un utilisateur pour une historisation spécifique
    public List<HistorisationExpense> findByUserNameAndHistorisationId(String userName, Long historisationId) {
        return find("userName = ?1 AND historisation.id = ?2", userName, historisationId).list();
    }
}