package com.smartbudget.repository;

import com.smartbudget.entity.Salary;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class SalaryRepository implements PanacheRepository<Salary> {

}
