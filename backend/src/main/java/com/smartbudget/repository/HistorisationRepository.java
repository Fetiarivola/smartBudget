package com.smartbudget.repository;

import com.smartbudget.entity.Historisation;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.List;

@ApplicationScoped
public class HistorisationRepository implements PanacheRepository<Historisation> {

    public List<Historisation> findByName(String name) {
        return list("name", name);
    }

    public Historisation findLatestByName(String name) {
        return find("name = ?1 ORDER BY date DESC", name).firstResult();
    }
}