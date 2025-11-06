package com.smartbudget.resource;

import java.time.LocalDate;

import com.smartbudget.entity.Salary;
import com.smartbudget.repository.SalaryRepository;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.util.List;

@Path("/api/salaries")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SalaryResource {

    @Inject
    SalaryRepository salaryRepository;

    @GET
    @Path("/list")
    public List<Salary> getAll() {
        return salaryRepository.listAll();
    }

    @POST
    @Path("/add")
    @Transactional
    public Salary addSalary(Salary salary) {
        if (salary.getDate() == null)
            salary.setDate(LocalDate.now());
        if (salary.getCurrency() == null)
            salary.setCurrency("MGA");
        salaryRepository.persist(salary);
        return salary;
    }

    @PUT
    @Path("/update/{id}")
    @Transactional
    public Salary updateSalary(@PathParam("id") Long id, Salary updatedSalary) {
        Salary existingSalary = salaryRepository.findById(id);
        if (existingSalary == null) {
            throw new NotFoundException("Salary with id " + id + " not found");
        }
        existingSalary.setName(updatedSalary.getName());
        existingSalary.setAmount(updatedSalary.getAmount());
        existingSalary.setDate(updatedSalary.getDate());
        existingSalary.setCurrency(updatedSalary.getCurrency());
        salaryRepository.persist(existingSalary);
        return existingSalary;
    }

    @DELETE
    @Path("/delete/{id}")
    @Transactional
    public void deleteSalary(@PathParam("id") Long id) {
        Salary existingSalary = salaryRepository.findById(id);
        if (existingSalary == null) {
            throw new NotFoundException("Salary with id " + id + " not found");
        }
        salaryRepository.delete(existingSalary);
    }

}
