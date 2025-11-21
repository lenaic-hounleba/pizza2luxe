package pizzas;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Représente une commande passée par un client. Une commande dispose d'un
 * identifiant unique, un client, un ensemble de pizzas avec leurs quantités, le
 * statut de la commande, les dates importantes et le montant total.
 *
 * <p>
 * Une commande suit trois états :
 * </p>
 * <ul>
 * <li>creeee : la commande est en cours de création et peut être modifiée</li>
 * <li>validee : la commande a été validée et ne peut plus être modifiée</li>
 * <li>traitee : la commande a été traitée et livrée</li>
 * </ul>
 *
 * @author lenaic-love.hounleba
 */

public class Commande {
  
  /** Identifiant unique de la commande. */
  private final int id;
  
  /** Client ayant passé la commande. */
  private final Client client;
  
  /** Pizzas commandées avec leur quantité. */
  private final Map<Pizza, Integer> pizzas;
  
  /** Statut actuel de la commande. */
  private EtatCommande statut;
  
  /** Date de création de la commande. */
  private final LocalDateTime dateCreationCmd;
  
  /** Date de validation de la commande. */
  private LocalDateTime dateValidationCmd;
  
  /** Date de traitement/livraison. */
  private LocalDateTime dateLivraison;
  
  /** Montant total de la commande. */
  private double montantTotal;
  
  /**
   * Constructeur d'une commande.
   *
   * @param id identifiant unique de la commande
   * @param client client ayant passé la commande
   */
  public Commande(int id, Client client) {
    this.id = id;
    this.client = client;
    this.pizzas = new HashMap<>();
    this.statut = EtatCommande.creee;
    this.dateCreationCmd = LocalDateTime.now();
    this.dateValidationCmd = null;
    this.dateLivraison = null;
    this.montantTotal = 0.0;
  }
  


  // MÉTHODES -------------------------------------------------------------
  
  /**
   * Ajoute une pizza à la commande avec une quantité donnée. Si la pizza existe
   * déjà, la quantité est cumulée.
   *
   * @param pizza la pizza à ajouter
   * @param quantite quantité désirée (>= 1)
   * @throws CommandeException si la commande n'est plus modifiable
   * @throws IllegalArgumentException si la quantité est <= 0
   */
  public void ajouterPizza(Pizza pizza, int quantite) {
    if (!estModifiable()) {
      throw new CommandeException("La commande n'est plus modifiable.");
    }
    if (quantite <= 0) {
      throw new IllegalArgumentException("La quantité doit être >= 1.");
    }
    
    pizzas.merge(pizza, quantite, Integer::sum);
    recalculerMontantTotal();
  }
  
  /**
   * Change la quantité d'une pizza déjà présente dans la commande.
   *
   * @param pizza la pizza concernée
   * @param quantite nouvelle quantité (>= 0)
   * @throws CommandeException si la commande n'est plus modifiable
   * @throws IllegalArgumentException si la quantité est négative
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
    recalculerMontantTotal();
  }
  
  
  /**
   * Vérifie si la commande est encore modifiable.
   *
   * @return true si le statut est 'creee', false sinon
   */
  public boolean estModifiable() {
    return statut == EtatCommande.creee;
  }
  
  
  /**
   * Valide la commande. La commande ne peut plus être modifiée après
   * validation.
   *
   * @throws CommandeException si la commande n'était pas en création
   */
  public void valider() {
    if (statut != EtatCommande.creee) {
      throw new CommandeException("La commande ne peut pas être validée.");
    }
    statut = EtatCommande.validee;
    dateValidationCmd = LocalDateTime.now();
  }
  
  
  /**
   * Annule une commande en cours de création.
   *
   * @throws CommandeException si la commande a déjà été validée
   */
  public void annuler() {
    if (statut != EtatCommande.creee) {
      throw new CommandeException("Impossible d'annuler une commande validée.");
    }
    pizzas.clear();
    montantTotal = 0.0;
    statut = EtatCommande.traitee;
  }
  
  /**
   * Marque la commande comme traitée/livrée.
   *
   * @throws CommandeException si la commande n'est pas validée
   */
  public void traiter() {
    if (statut != EtatCommande.validee) {
      throw new CommandeException(
          "Seules les commandes validées peuvent être traitées.");
    }
    statut = EtatCommande.traitee;
    dateLivraison = LocalDateTime.now();
  }
  
  /**
   * Recalcule le montant total de la commande. Il est appelé à chaque
   * modification de pizza/quantité.
   */
  private void recalculerMontantTotal() {
    double total = 0;
    for (Map.Entry<Pizza, Integer> entry : pizzas.entrySet()) {
      Pizza pizza = entry.getKey();
      int qte = entry.getValue();
      total += pizza.getPrixPizza(pizza) * qte;
    }
    montantTotal = total;
  }
  
  /**
   * Retourne le montant total de la commande.
   *
   * @return le montant total en euros
   */
  public double getMontantTotal() {
    return montantTotal;
  }
  

  // GETTERS ----------------------------------------------------------------
  
  public int getId() {
    return id;
  }
  
  public Client getClient() {
    return client;
  }
  
  
  /**
   * Retourne une vue non modifiable des pizzas commandées.
   *
   * @return map pizzas-quantités
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
    return "Commande #" + id + " - " + statut + " - total: " + montantTotal
        + "€";
  }
}
