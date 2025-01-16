package com.mcommandes.dao;


import java.time.LocalDate;

class CommandeResponse {
    private int id;
    private String description;
    private int quantite;
    private LocalDate date;
    private double montant;
    private int productid;
    private String email;

    public CommandeResponse(int id, String description, int quantite, LocalDate date, double montant, int productid, String email) {
        this.id = id;
        this.description = description;
        this.quantite = quantite;
        this.date = date;
        this.montant = montant;
        this.productid = productid;
        this.email = email;
    }

    // Getters et Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }

    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }

    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public int getProductid() { return productid; }
    public void setProductid(int productid) { this.productid = productid; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
