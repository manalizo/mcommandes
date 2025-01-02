package com.mcommandes.web.controller;

import com.mcommandes.dao.ClientDTO;
import com.mcommandes.dao.ProduitDTO;
import com.mcommandes.feign.ClientsClient;
import com.mcommandes.feign.ProduitsClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    private final ClientsClient clientsClient;
    private final ProduitsClient produitsClient;

    public CommandeController(ClientsClient clientsClient, ProduitsClient produitsClient) {
        this.clientsClient = clientsClient;
        this.produitsClient = produitsClient;
    }

    @GetMapping("/details")
    public Map<String, Object> getCommandeDetails() {
        List<ClientDTO> clients = clientsClient.getClients();
        List<ProduitDTO> produits = produitsClient.getProduits();
        return Map.of("clients", clients, "produits", produits);
    }
}
