package com.smartbudget.resource;

import com.smartbudget.entity.Expense;
import com.smartbudget.repository.ExpenseRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/expenses")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExpenseResource {

    @Inject
    ExpenseRepository expenseRepository;

    @GET
    @Path("/list")
    public List<Expense> getAll() {
        return expenseRepository.listAll();
    }

    @POST
    @Path("/add/{userName}")
    @Transactional
    public Expense addExpenseForUser(@PathParam("userName") String userName, Expense expense) {
        expense.setUserName(userName);
        expenseRepository.persist(expense);
        return expense;
    }

    @GET
    @Path("/by-user/{userName}")
    public List<Expense> getExpensesByUser(@PathParam("userName") String userName) {
        return expenseRepository.findByUserName(userName);
    }

    @PUT
    @Path("/update/{id}")
    @Transactional
    public Expense updateExpense(@PathParam("id") Long id, Expense updatedExpense) {
        Expense existing = expenseRepository.findById(id);
        if (existing == null) {
            throw new NotFoundException("Expense with id " + id + " not found");
        }

        existing.setUserName(updatedExpense.getUserName());
        existing.setCategory(updatedExpense.getCategory());
        existing.setPercentage(updatedExpense.getPercentage());

        expenseRepository.persist(existing);
        return existing;
    }

    @DELETE
    @Path("/delete/{id}")
    @Transactional
    public void deleteExpense(@PathParam("id") Long id) {
        Expense existing = expenseRepository.findById(id);
        if (existing == null) {
            throw new NotFoundException("Expense with id " + id + " not found");
        }
        expenseRepository.delete(existing);
    }
}
