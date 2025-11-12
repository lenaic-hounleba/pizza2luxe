package pizzas;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet de créer une commande à partir des pizzas existantes et
 * permet de gérer également l'état des commandes.
 *
 * @author Lenaïc Love HOUNLEBA
 */
public class Commande {
  private LocalDateTime dateCommande;
  private double montantTotal;
  private String adresseLivraison;
  private List<Pizza> pizzas;
  private int quantite;
  private StatutCommande statut;
  private Client client;
  
  
  
  public Commande(String adresseLivraison, List<Pizza> pizzas, int quantite) {
    this.dateCommande = LocalDateTime.now();
    this.montantTotal = calculerMontantTotal();
    this.adresseLivraison = adresseLivraison;
    this.pizzas = pizzas;
    this.quantite = quantite;
    this.statut = StatutCommande.cree;
    this.client = Client.getNom();
  }
  
  
  
  public LocalDateTime getDateCommande() {
    return dateCommande;
  }
  
  
  
  public void setDateCommande(LocalDateTime dateCommande) {
    this.dateCommande = dateCommande;
  }
  
  
  
  public double getMontantTotal() {
    return montantTotal;
  }
  
  
  
  public void setMontantTotal(double montantTotal) {
    this.montantTotal = montantTotal;
  }
  
  
  
  public String getAdresseLivraison() {
    return adresseLivraison;
  }
  
  
  
  public void setAdresseLivraison(String adresseLivraison) {
    this.adresseLivraison = adresseLivraison;
  }
  
  
  
  
  public Pizza getPizza() {
    return pizzas;
  }
  
  
  
  public void setPizza(Pizza pizzas) {
    this.pizzas = pizzas;
  }
  
  
  
  public int getQuantite() {
    return quantite;
  }
  
  
  
  public void setQuantite(int quantite) {
    this.quantite = quantite;
  }
  
  
  
  public StatutCommande getStatut() {
    return statut;
  }
  
  
  
  public void setStatut(StatutCommande statut) {
    this.statut = statut;
  }
  
  
  
  public double getPrixUnitaire() {
    return prixUnitaire;
  }
  
  
  
  public void setPrixUnitaire(double prixUnitaire) {
    this.prixUnitaire = prixUnitaire;
  }
  
  
  
  public Client getClient() {
    return client;
  }
  
  
  
  public void setClient(Client client) {
    this.client = client;
  }
  
  public double montantTotal(double prixUnitaire, int quantite) {
    double montant total = 
  }
  

  
  
}
