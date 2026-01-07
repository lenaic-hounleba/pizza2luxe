package pizzas;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Classe de gestion des commandes de l'application. Centralise la création,
 * l'annulation, la validation et le traitement des commandes des clients.
 *
 * @author lenaic-love.hounleba
 */
public class GestionCommandes {
  
  /** Liste de toutes les commandes de l'application. */
  private final List<Commande> commandes;
  
  /** Compteur pour générer des identifiants uniques. */
  private int compteurId;
  
  /**
   * Construit un gestionnaire de commandes vide.
   */
  public GestionCommandes() {
    commandes = new ArrayList<>();
    compteurId = 1;
  }
  
  /**
   * Crée une nouvelle commande pour un client.
   *
   * @param client client connecté
   * @return la commande créée
   */
  public Commande creerCommande(Client client) {
    Commande commande = new Commande(compteurId++, client);
    commandes.add(commande);
    return commande;
  }
  
  /**
   * Annule une commande en cours de création.
   *
   * @param commande commande à annuler
   * @throws CommandeException si la commande n'est pas en cours
   */
  public void annulerCommande(Commande commande) {
    if (commande.getEtat() != EtatCommande.creee) {
      throw new CommandeException(
          "Seules les commandes en cours peuvent être annulées.");
    }
    commandes.remove(commande);
  }
  
  /**
   * Retourne les commandes en cours d'un client.
   *
   * @param client client concerné
   * @return liste des commandes en cours
   */
  public List<Commande> getCommandesEnCours(Client client) {
    return commandes.stream().filter(c -> c.getClient().equals(client))
        .filter(c -> c.getEtat() == EtatCommande.creee)
        .collect(Collectors.toList());
  }
  
  /**
   * Retourne les commandes traitées d'un client.
   *
   * @param client client concerné
   * @return liste des commandes traitées
   */
  public List<Commande> getCommandesTraitees(Client client) {
    return commandes.stream().filter(c -> c.getClient().equals(client))
        .filter(c -> c.getEtat() == EtatCommande.traitee)
        .collect(Collectors.toList());
  }
  
  /**
   * Retourne toutes les commandes validées mais non encore traitées.
   *
   * @return commandes à traiter
   */
  public List<Commande> getCommandesAtraiter() {
    return commandes.stream().filter(c -> c.getEtat() == EtatCommande.validee)
        .collect(Collectors.toList());
  }
  
  /**
   * Marque toutes les commandes validées comme traitées.
   */
  public void traiterCommandes() {
    for (Commande c : getCommandesAtraiter()) {
      c.traiter();
    }
  }
  
  /**
   * Calcule le montant total de toutes les commandes traitées.
   *
   * @return bénéfice total
   */
  public double getMontantTotalCommandesTraitees() {
    return commandes.stream().filter(c -> c.getEtat() == EtatCommande.traitee)
        .mapToDouble(Commande::calculerMontantTotal).sum();
  }
}
