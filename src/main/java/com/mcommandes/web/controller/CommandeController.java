package com.mcommandes.web.controller;

import com.mcommandes.model.Commande;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    @GetMapping
    public List<Commande> getCommandes() {
        return Arrays.asList(
                new Commande(1L, "Commande 1", 250.0),
                new Commande(2L, "Commande 2", 150.0)
        );
    }
}
