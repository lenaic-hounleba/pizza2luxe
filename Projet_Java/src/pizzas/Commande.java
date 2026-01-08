package pizzas;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Représente une commande de pizzas passée par un client. Une commande possède
 * un état qui évolue de créée à validée puis traitée.
 * Une commande créée peut être modifiée par le client. Une commande validée ou
 * traitée n'est plus modifiable.
 *
 * @author lenaic-love.hounleba
 */
public class Commande implements java.io.Serializable {
  
  /**
   * Identifiant de sérialisation.
   */
  private static final long serialVersionUID = 1L;
  
  /** Identifiant unique de la commande. */
  private final int id;
  
  /** Client ayant passé la commande. */
  private final Client client;
  
  /** Pizzas commandées avec leurs quantités. */
  private final Map<Pizza, Integer> pizzas;
  
  /** État actuel de la commande. */
  private EtatCommande etat;
  
  /**
   * Construit une nouvelle commande associée à un client.
   *
   * @param id identifiant unique de la commande
   * @param client client ayant passé la commande
   */
  public Commande(int id, Client client) {
    this.id = id;
    this.client = client;
    this.pizzas = new HashMap<>();
    this.etat = EtatCommande.creee;
  }
  
  /**
   * Ajoute une certaine quantité d'une pizza à la commande. Si la pizza est
   * déjà présente, la quantité est cumulée.
   *
   * @param pizza la pizza à ajouter
   * @param nombre la quantité à ajouter (doit être strictement positive)
   * @throws CommandeException si la commande n'est plus modifiable
   */
  public void ajouterPizza(Pizza pizza, int nombre) {
    if (!estModifiable()) {
      throw new CommandeException("La commande n'est plus modifiable.");
    }
    if (pizza == null || nombre <= 0) {
      throw new CommandeException("Pizza ou quantité invalide.");
    }
    
    pizzas.merge(pizza, nombre, Integer::sum);
  }
  
  /**
   * Indique si la commande est encore modifiable.
   *
   * @return true si la commande est en cours de création, false sinon
   */
  public boolean estModifiable() {
    return etat == EtatCommande.creee;
  }
  
  /**
   * Valide la commande. Une commande validée ne peut plus être modifiée.
   *
   * @throws CommandeException si la commande n'est pas en cours de création
   */
  public void valider() {
    if (etat != EtatCommande.creee) {
      throw new CommandeException("La commande ne peut pas être validée.");
    }
    etat = EtatCommande.validee;
  }
  
  /**
   * Marque la commande comme traitée. Cette opération est réalisée par le
   * pizzaïolo.
   *
   * @throws CommandeException si la commande n'est pas validée
   */
  public void traiter() {
    if (etat != EtatCommande.validee) {
      throw new CommandeException(
          "Seules les commandes validées peuvent être traitées.");
    }
    etat = EtatCommande.traitee;
  }
  
  /**
   * Calcule le montant total de la commande à partir du prix de vente des
   * pizzas et de leurs quantités.
   *
   * @return montant total de la commande
   */
  public double calculerMontantTotal() {
    double total = 0.0;
    for (Map.Entry<Pizza, Integer> entry : pizzas.entrySet()) {
      total += entry.getKey().getPrixVente() * entry.getValue();
    }
    return total;
  }
  
  /**
   * Retourne une vue non modifiable des pizzas commandées.
   *
   * @return map pizzas - quantités
   */
  public Map<Pizza, Integer> getPizzas() {
    return Collections.unmodifiableMap(pizzas);
  }
  
  /**
   * Retourne l'identifiant de la commande.
   *
   * @return identifiant
   */
  public int getId() {
    return id;
  }
  
  /**
   * Retourne le client ayant passé la commande.
   *
   * @return le client
   */
  public Client getClient() {
    return client;
  }
  
  /**
   * Retourne l'état actuel de la commande.
   *
   * @return état de la commande
   */
  public EtatCommande getStatut() {
    return etat;
  }
  
  @Override
  public String toString() {
    return "Commande #" + id + " (" + etat + ")";
  }
}
