package com.kaoutar.ServicePraticien.model;


public class Praticien {
    private Long id;
    private String nom;
    private String prenom;
    private String specialite;
    private String email;
    private String telephone;

    // Constructeur par défaut
    public Praticien() {
    }

    // Constructeur avec paramètres
    public Praticien(Long id, String nom, String prenom, String specialite, String email, String telephone) {
        this.id = id;
        this.nom = nom;
        this.prenom = prenom;
        this.specialite = specialite;
       this.email = email;
       this.telephone = telephone;
   }
    // Getters et Setters
   public Long getId() {
       return id;
   }
    public void setId(Long id) {
       this.id = id;
   }
    public String getNom() {
       return nom;
   }
    public void setNom(String nom) {
       this.nom = nom;
   }
    public String getPrenom() {
       return prenom;
   }
    public void setPrenom(String prenom) {
       this.prenom = prenom;
   }
    public String getSpecialite() {
       return specialite;
   }
    public void setSpecialite(String specialite) {
       this.specialite = specialite;
   }
    public String getEmail() {
       return email;
   }
    public void setEmail(String email) {
       this.email = email;
   }
    public String getTelephone() {
       return telephone;
   }
    public void setTelephone(String telephone) {
       this.telephone = telephone;
   }
    @Override
   public String toString() {
       return "Praticien{" +
               "id=" + id +
               ", nom='" + nom + '\'' +
               ", prenom='" + prenom + '\'' +
               ", specialite='" + specialite + '\'' +
               ", email='" + email + '\'' +
               ", telephone='" + telephone + '\'' +
               '}';
   }
}
