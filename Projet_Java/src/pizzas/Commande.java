package pizzas;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Représente une commande passée par un client avec des quantités pour chaque
 * type de pizza. Une commande suit trois états : cree, validee ou traitee.
 * 
 * @author lenaic-love.hounleba
 */
public class Commande {
  
  /** Identifiant unique de la commande. */
  private final int id;
  
  
  /** Client ayant passé la commande. */
  private final Client client;
  
  
  /**
   * Pizzas commandées et leur quantité.
   */
  private final Map<Pizza, Integer> pizzas;
  
  
  /** Statut actuel de la commande. */
  private EtatCommande statut;
  
  
  /** Date de création de la commande. */
  private final LocalDateTime dateCreationCmd;
  
  
  /** Date de validation de la commande. */
  private LocalDateTime dateValidationCmd;
  
  
  /** Date de livraison/préparation par le pizzaiolo. */
  private LocalDateTime dateLivraison;
  
  
  /**
   * Constructeur d'une commande.
   *
   * @param id identifiant unique
   * @param client client ayant passé la commande
   */
  public Commande(int id, Client client) {
    super();
    this.id = id;
    this.client = client;
    this.pizzas = new HashMap<>();
    this.statut = EtatCommande.cree;
    this.dateCreationCmd = LocalDateTime.now();
    this.dateValidationCmd = null;
    this.dateLivraison = null;
  }
  
  
  // ----------------------------------------------------------------------
  // MÉTHODES
  // ----------------------------------------------------------------------
  
  /**
   * Ajoute une pizza avec une quantité donnée.
   *
   * @param pizza la pizza à ajouter
   * @param quantite quantité désirée (>= 1)
   */
  public void ajouterPizza(Pizza pizza, int quantite) {
    if (!estModifiable()) {
      throw new CommandeException("La commande n'est plus modifiable.");
    }
    if (quantite <= 0) {
      throw new IllegalArgumentException("La quantité doit être >= 1.");
    }
    
    pizzas.merge(pizza, quantite, Integer::sum);
  }
  
  /**
   * Modifie la quantité d'une pizza dans la commande.
   *
   * @param pizza la pizza concernée
   * @param quantite nouvelle quantité (>= 0)
   */
  public void changerQuantite(Pizza pizza, int quantite) {
    if (!estModifiable()) {
      throw new CommandeException("La commande n'est plus modifiable.");
    }
    if (quantite < 0) {
      throw new IllegalArgumentException("Quantité négative interdite.");
    }
    
    if (quantite == 0) {
      pizzas.remove(pizza);
    } else {
      pizzas.put(pizza, quantite);
    }
  }
  
  /**
   * Vérifie si la commande est encore modifiable (statut cree).
   *
   * @return true si modifiable
   */
  public boolean estModifiable() {
    return statut == EtatCommande.cree;
  }
  
  /**
   * Valide la commande. Elle ne peut plus être modifiée.
   */
  public void valider() {
    if (statut != EtatCommande.cree) {
      throw new CommandeException("La commande ne peut pas être validée.");
    }
    statut = EtatCommande.validee;
    dateValidationCmd = LocalDateTime.now();
  }
  
  /**
   * Annule une commande encore en création.
   */
  public void annuler() {
    if (statut != EtatCommande.cree) {
      throw new CommandeException("Impossible d'annuler une commande validée.");
    }
    pizzas.clear();
    statut = EtatCommande.traitee; // devient un état "fini"
  }
  
  /**
   * Marque la commande comme traitée/livrée.
   */
  public void traiter() {
    if (statut != EtatCommande.validee) {
      throw new CommandeException("Seules les commandes validées peuvent être traitées.");
    }
    statut = EtatCommande.traitee;
    dateLivraison = LocalDateTime.now();
  }
  
  /**
   * Calcule le montant total de la commande : somme(prixPizza × quantité).
   *
   * @return le prix total
   */
  public double getMontantTotal() {
    double total = 0;
    for (Map.Entry<Pizza, Integer> entry : pizzas.entrySet()) {
      Pizza pizza = entry.getKey();
      int qte = entry.getValue();
      double prix = getPrixPizza(pizza);
      total += prix * qte;
    }
    return total;
  }
  
  // ----------------------------------------------------------------------
  // GETTERS
  // ----------------------------------------------------------------------
  
  public int getId() {
    return id;
  }
  
  public Client getClient() {
    return client;
  }
  
  /**
   * Renvoie une vue non modifiable de la map des pizzas.
   */
  public Map<Pizza, Integer> getPizzas() {
    return Collections.unmodifiableMap(pizzas);
  }
  
  public EtatCommande getStatut() {
    return statut;
  }
  
  public LocalDateTime getDateCreationCmd() {
    return dateCreationCmd;
  }
  
  public LocalDateTime getDateValidationCmd() {
    return dateValidationCmd;
  }
  
  public LocalDateTime getDateLivraison() {
    return dateLivraison;
  }
  
  @Override
  public String toString() {
    return "Commande #" + id + " - " + statut + " - total: " + getMontantTotal() + "€";
  }
}
