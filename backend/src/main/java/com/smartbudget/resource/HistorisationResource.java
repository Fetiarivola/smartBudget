package com.smartbudget.resource;

import com.smartbudget.entity.*;
import com.smartbudget.repository.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Path("/api/historisations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HistorisationResource {

    @Inject
    HistorisationRepository historisationRepository;

    @Inject
    SalaryRepository salaryRepository;

    @Inject
    ExpenseRepository expenseRepository;

    @Inject
    HistorisationExpenseRepository historisationExpenseRepository;

    @GET
    @Path("/list")
    public List<Historisation> getAll() {
        return historisationRepository.listAll();
    }

    @GET
    @Path("/by-name/{name}")
    public Historisation getBudgetByName(@PathParam("name") String name) {
        Historisation historisation = historisationRepository.findLatestByName(name);
        if (historisation == null) {
            throw new NotFoundException("Aucun budget trouvé pour: " + name);
        }
        return historisation;
    }

    @GET
    @Path("/expenses/by-user/{userName}")
    public List<HistorisationExpense> getExpensesByUser(@PathParam("userName") String userName) {
        return historisationExpenseRepository.findByUserName(userName);
    }

    @POST
    @Path("/create-budget/{salaryName}")
    @Transactional
    public Historisation createBudgetFromSalary(@PathParam("salaryName") String salaryName) {
        // Trouver le salaire par nom
        Salary salary = salaryRepository.find("name", salaryName).firstResult();
        if (salary == null) {
            throw new NotFoundException("Salaire non trouvé pour le nom: " + salaryName);
        }

        // Récupérer les dépenses SPÉCIFIQUES à cet utilisateur
        List<Expense> userExpenses = expenseRepository.findByUserName(salaryName);
        if (userExpenses.isEmpty()) {
            throw new BadRequestException("Aucune dépense définie pour l'utilisateur: " + salaryName);
        }

        // ===== LOGIQUE SMART UPDATE =====
        // Chercher s'il existe déjà un budget pour cette personne
        Historisation historisation = historisationRepository.findLatestByName(salaryName);

        if (historisation != null) {
            // METTRE À JOUR l'existant
            historisation.setSalaryAmount(salary.getAmount());
            historisation.setDate(LocalDate.now()); // Mettre à jour la date
            historisation.setCurrency(salary.getCurrency());

            // Supprimer les anciennes dépenses pour les recalculer
            historisation.getExpenses().clear();
        } else {
            // CRÉER un nouveau budget
            historisation = new Historisation();
            historisation.setName(salary.getName());
            historisation.setSalaryAmount(salary.getAmount());
            historisation.setDate(LocalDate.now());
            historisation.setCurrency(salary.getCurrency());
        }

        // Calculer les dépenses (même logique pour CREATE et UPDATE)
        List<HistorisationExpense> historisationExpenses = new ArrayList<>();
        double totalExpenses = 0.0;

        for (Expense expense : userExpenses) {
            HistorisationExpense histExpense = new HistorisationExpense();
            histExpense.setHistorisation(historisation);
            histExpense.setUserName(salaryName); // NOUVEAU : Ajouter le nom d'utilisateur
            histExpense.setCategory(expense.getCategory());
            histExpense.setPercentage(expense.getPercentage());

            // Calculer le montant
            double calculatedAmount = (salary.getAmount() * expense.getPercentage().doubleValue()) / 100;
            histExpense.setCalculatedAmount(calculatedAmount);

            historisationExpenses.add(histExpense);
            totalExpenses += calculatedAmount;
        }

        // Calculer le reste
        double remainingAmount = salary.getAmount() - totalExpenses;

        historisation.setExpenses(historisationExpenses);
        historisation.setTotalExpenses(totalExpenses);
        historisation.setRemainingAmount(remainingAmount);

        // Persister (fonctionnera pour CREATE et UPDATE)
        historisationRepository.persist(historisation);

        return historisation;
    }

    @DELETE
    @Path("/delete/{id}")
    @Transactional
    public void deleteHistorisation(@PathParam("id") Long id) {
        Historisation existing = historisationRepository.findById(id);
        if (existing == null) {
            throw new NotFoundException("Historisation avec id " + id + " non trouvée");
        }
        historisationRepository.delete(existing);
    }
}