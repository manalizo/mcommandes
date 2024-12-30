package com.mcommandes.dao;



public class CommandeDTO {
    private int id;
    private String nom;
    private String details;

    // Constructeurs
    public CommandeDTO() {}

    public CommandeDTO(int id, String nom, String details) {
        this.id = id;
        this.nom = nom;
        this.details = details;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }
}
