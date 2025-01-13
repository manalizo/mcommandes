package com.mcommandes.service;

import com.mcommandes.model.Commande;
import com.mcommandes.repository.CommandeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CommandeService {

    @Autowired
    private CommandeRepository commandeRepository;

    // Create or Update
    public Commande saveCommande(Commande commande) {
        return commandeRepository.save(commande);
    }

    // Get all commandes
    public List<Commande> getAllCommandes() {
        return commandeRepository.findAll();
    }

    // Get a specific commande by ID
    public Optional<Commande> getCommandeById(int id) {
        return commandeRepository.findById(id);
    }

    // Delete a commande
    public void deleteCommande(int id) {
        commandeRepository.deleteById(id);
    }
}
