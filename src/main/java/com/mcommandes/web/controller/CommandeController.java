package com.mcommandes.web.controller;

import com.mcommandes.configurations.ApplicationPropertiesConfiguration;
import com.mcommandes.dao.CommandeDao;
import com.mcommandes.model.Commande;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
public class CommandeController implements HealthIndicator {
    @Autowired
    CommandeDao commandeDao;

    @Autowired
    ApplicationPropertiesConfiguration appProperties;

    private final List<Commande> commandes = new ArrayList<>();

    @Value("${mes-config-ms.commandes-last:10}")
    private int commandesLast;

    @GetMapping
    public List<Commande> getCommandes() {
        LocalDate cutoffDate = LocalDate.now().minusDays(commandesLast);
        return commandes.stream()
                .filter(commande -> commande.getDate().isAfter(cutoffDate))
                .collect(Collectors.toList());
    }

    @PostMapping
    public Commande createCommande(@RequestBody Commande commande) {
        commande.setId(UUID.randomUUID().toString());
        commandes.add(commande);
        return commande;
    }

    @PutMapping("/{id}")
    public Commande updateCommande(@PathVariable String id, @RequestBody Commande updatedCommande) {
        return commandes.stream()
                .filter(commande -> commande.getId().equals(id))
                .findFirst()
                .map(commande -> {
                    commande.setDescription(updatedCommande.getDescription());
                    commande.setQuantite(updatedCommande.getQuantite());
                    commande.setDate(updatedCommande.getDate());
                    commande.setMontant(updatedCommande.getMontant());
                    return commande;
                })
                .orElseThrow(() -> new NoSuchElementException("Commande not found"));
    }

    @DeleteMapping("/{id}")
    public void deleteCommande(@PathVariable String id) {
        commandes.removeIf(commande -> commande.getId().equals(id));
    }

    @Override
    public Health health() {
        return null;
    }
}
/*
@Bean
public HealthIndicator commandesHealthIndicator() {
    return () -> {
        boolean hasCommandes = !commandes.isEmpty();
        return hasCommandes ? Health.up().build() : Health.down().withDetail("reason", "No commandes available").build();
    };
}*/
/*
@Override
    public Health health() {
        System.out.println("****** Actuator : CommandeController health() ");
        List<Commande> products = commandeDao.findAll();

        if (products.isEmpty()) {
            return Health.down().build();
        }

        return Health.up().build();
    }
}*/
