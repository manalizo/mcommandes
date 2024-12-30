package com.mcommandes.web.controller;



import com.mcommandes.dao.CommandeDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CommandeController {

    @GetMapping("/commandes")
    public List<CommandeDTO> getAllCommandes() {
        // Vous pouvez remplacer par une logique réelle (par exemple, accéder à une base de données)
        return List.of(
                new CommandeDTO(1, "Commande 1", "Détails de la commande 1"),
                new CommandeDTO(2, "Commande 2", "Détails de la commande 2")
        );
    }
}
