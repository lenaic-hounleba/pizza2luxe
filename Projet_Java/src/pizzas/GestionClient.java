package pizzas;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Classe qui gère les traitements liés au client.
 * @author Dorian Fleurquin
 * Cette classe implémente les services définis par l'interface InterClient.
 */
public class GestionClient implements InterClient {

  /** Données partagées de la pizzeria. */
  private final PizzeriaData data;

  /** Client actuellement connecté. */
  private Client clientConnecte;

  /** Filtre sur le type de pizza. */
  private TypePizza filtreType;

  /** Filtre sur les ingrédients. */
  private Set<String> filtreIngredients;

  /** Filtre sur le prix maximum. */
  private Double filtrePrixMax;

  /**
   * Constructeur du service client.
   *
   * @param data les données partagées de la pizzeria
   */
  public GestionClient(PizzeriaData data) {
    this.data = data;
    this.clientConnecte = null;
    this.filtreType = null;
    this.filtreIngredients = null;
    this.filtrePrixMax = null;
  }

  @Override
  /**
   * Inscription d'un nouveau client.
   *
   * L'adresse email doit être unique. L'inscription échoue si l'email ou le
   * mot de passe est vide, si les informations personnelles sont invalides
   * ou si l'email n'est pas correctement formé.
   *
   * @param email l'adresse email du client
   * @param mdp le mot de passe du client
   * @param info les informations personnelles du client
   * @return 0 si l'inscription s'est bien déroulée, -1 si l'email est déjà
   *         utilisé, -2 si l'email ou le mot de passe est vide, -3 si les
   *         informations personnelles sont invalides, -4 si l'email n'est
   *         pas bien formé
   */
  public int inscription(String email, String mdp,
                         InformationPersonnelle info) {

    if (email == null || email.isEmpty()
            || mdp == null || mdp.isEmpty()) {
      return -2;
    }

    if (!email.contains("@") || !email.contains(".")) {
      return -4;
    }

    if (info == null) {
      return -3;
    }

    if (data.clientsByEmail.containsKey(email)) {
      return -1;
    }

    Client client = new Client(
            email,
            mdp,
            info.getNom(),
            info.getPrenom(),
            info.getAdresse(),
            info.getAge());

    data.clientsByEmail.put(email, client);

    return 0;
  }


  /**
   * Connexion d'un client.
   *
   * Le couple email/mot de passe doit correspondre à un client inscrit.
   * Une fois connecté, le client peut passer des commandes.
   *
   * @param email l'adresse email du client
   * @param mdp le mot de passe du client
   * @return true si la connexion s'est bien déroulée, false sinon
   */
  @Override
  public boolean connexion(String email, String mdp) {

    if (email == null || mdp == null) {
      return false;
    }

    Client client = data.clientsByEmail.get(email);
    if (client == null) {
      return false;
    }

    if (!client.verifierMotDePasse(mdp)) {
      return false;
    }

    clientConnecte = client;
    return true;
  }


/**
 * Déconnecte le client actuellement connecté.
 *
 * @throws NonConnecteException si aucun client n'est connecté
 */
  @Override
  public void deconnexion() throws NonConnecteException {

    if (clientConnecte == null) {
      throw new NonConnecteException();
    }

    clientConnecte = null;
  }


  /**
   * Crée une nouvelle commande pour le client actuellement connecté.
   *
   * @return la commande qui vient d'être créée
   * @throws NonConnecteException si aucun client n'est connecté
   */
  @Override
  public Commande debuterCommande() throws NonConnecteException {

    if (clientConnecte == null) {
      throw new NonConnecteException();
    }

    Commande cmd = new Commande(
            data.nextCommandeId,
            clientConnecte);

    data.nextCommandeId++;
    data.commandes.add(cmd);

    return cmd;
  }


  /**
   * Ajoute une certaine quantité d'une pizza à une commande.
   *
   * @param pizza la pizza que l'on commande
   * @param nombre le nombre de pizzas à ajouter
   * @param cmd la commande en cours
   * @throws NonConnecteException si aucun client n'est connecté
   * @throws CommandeException en cas de problème avec la commande
   */
  @Override
  public void ajouterPizza(Pizza pizza, int nombre, Commande cmd)
          throws NonConnecteException, CommandeException {

    if (clientConnecte == null) {
      throw new NonConnecteException();
    }

    if (cmd == null) {
      throw new CommandeException("Commande invalide.");
    }

    if (cmd.getClient() != clientConnecte) {
      throw new CommandeException(
              "La commande n'appartient pas au client connecté.");
    }

    if (!data.commandes.contains(cmd)) {
      throw new CommandeException("Commande inconnue.");
    }

    if (!data.pizzasByName.containsValue(pizza)) {
      throw new CommandeException("Pizza invalide.");
    }

    cmd.ajouterPizza(pizza, nombre);
  }


  /**
   * Valide une commande en cours.
   *
   * Une fois validée, la commande ne peut plus être modifiée.
   *
   * @param cmd la commande à valider
   * @throws NonConnecteException si aucun client n'est connecté
   * @throws CommandeException en cas de problème avec la commande
   */
  @Override
  public void validerCommande(Commande cmd)
          throws NonConnecteException, CommandeException {

    if (clientConnecte == null) {
      throw new NonConnecteException();
    }

    if (cmd == null) {
      throw new CommandeException("Commande invalide.");
    }

    if (cmd.getClient() != clientConnecte) {
      throw new CommandeException(
              "La commande n'appartient pas au client connecté.");
    }

    if (!data.commandes.contains(cmd)) {
      throw new CommandeException("Commande inconnue.");
    }

    if (!cmd.estModifiable()) {
      throw new CommandeException(
              "La commande n'est pas modifiable.");
    }


    cmd.valider();
  }


  /**
   * Annule une commande en cours.
   *
   * Une commande annulée n'existe plus.
   *
   * @param cmd la commande à annuler
   * @throws NonConnecteException si aucun client n'est connecté
   * @throws CommandeException en cas de problème avec la commande
   */
  @Override
  public void annulerCommande(Commande cmd)
          throws NonConnecteException, CommandeException {

    if (clientConnecte == null) {
      throw new NonConnecteException();
    }

    if (cmd == null) {
      throw new CommandeException("Commande invalide.");
    }

    if (cmd.getClient() != clientConnecte) {
      throw new CommandeException(
              "La commande n'appartient pas au client connecté.");
    }

    if (!data.commandes.contains(cmd)) {
      throw new CommandeException("Commande inconnue.");
    }

    if (!cmd.estModifiable()) {
      throw new CommandeException(
              "Impossible d'annuler une commande validée.");
    }

    data.commandes.remove(cmd);
  }


  /**
   * Renvoie la liste des commandes en cours du client connecté.
   *
   * Les commandes retournées sont celles en cours de création, ordonnées
   * par date de création (de la plus ancienne à la plus récente).
   *
   * @return la liste des commandes en cours
   * @throws NonConnecteException si aucun client n'est connecté
   */
  @Override
  public List<Commande> getCommandesEncours()
          throws NonConnecteException {

    if (clientConnecte == null) {
      throw new NonConnecteException();
    }

    List<Commande> result = new ArrayList<>();

    for (Commande cmd : data.commandes) {
      if (cmd.getClient() == clientConnecte
              && cmd.getStatut() == EtatCommande.creee) {
        result.add(cmd);
      }
    }

    return result;
  }


  /**
   * Renvoie la liste des commandes passées du client connecté.
   *
   * Les commandes retournées sont celles qui ont été validées ou traitées,
   * ordonnées par date de création.
   *
   * @return la liste des commandes passées
   * @throws NonConnecteException si aucun client n'est connecté
   */
  @Override
  public List<Commande> getCommandePassees()
          throws NonConnecteException {

    if (clientConnecte == null) {
      throw new NonConnecteException();
    }

    List<Commande> result = new ArrayList<>();

    for (Commande cmd : data.commandes) {
      if (cmd.getClient() == clientConnecte
              && cmd.getStatut() != EtatCommande.creee) {
        result.add(cmd);
      }
    }

    return result;
  }


  /**
   * Renvoie l'ensemble des pizzas en vente.
   *
   * @return l'ensemble des pizzas disponibles
   */
  @Override
  public Set<Pizza> getPizzas() {
    return new HashSet<>(data.pizzasByName.values());
  }


  /**
   * Ajoute un filtre sur le type de pizza.
   *
   * @param type le type de pizza à conserver
   */
  @Override
  public void ajouterFiltre(TypePizza type) {
    if (type != null) {
      filtreType = type;
    }
  }


  /**
   * Ajoute un filtre sur les ingrédients.
   *
   * Les pizzas conservées doivent contenir tous les ingrédients fournis.
   * Les ingrédients invalides sont ignorés.
   *
   * @param ingredients les noms des ingrédients à filtrer
   */
  @Override
  public void ajouterFiltre(String... ingredients) {

    if (ingredients == null) {
      return;
    }

    if (filtreIngredients == null) {
      filtreIngredients = new HashSet<>();
    }

    for (String ing : ingredients) {
      if (ing != null && !ing.isEmpty()) {
        filtreIngredients.add(ing.toLowerCase());
      }
    }
  }


  /**
   * Ajoute un filtre sur le prix maximum des pizzas.
   *
   * @param prixMaximum le prix maximum
   */
  @Override
  public void ajouterFiltre(double prixMaximum) {
    if (prixMaximum > 0) {
      filtrePrixMax = prixMaximum;
    }
  }


  /**
   * Sélectionne les pizzas qui valident tous les filtres définis.
   *
   * @return l'ensemble des pizzas correspondant aux filtres
   */
  @Override
  public Set<Pizza> selectionPizzaFiltres() {

    Set<Pizza> result = new HashSet<>();

    for (Pizza pizza : data.pizzasByName.values()) {

      // Filtre sur le type
      if (filtreType != null && pizza.getType() != filtreType) {
        continue;
      }

      // Filtre sur le prix
      if (filtrePrixMax != null
              && pizza.getPrixPizza(pizza) > filtrePrixMax) {
        continue;
      }

      // Filtre sur les ingrédients
      if (filtreIngredients != null) {
        boolean ok = true;

        for (String ing : filtreIngredients) {
          boolean found = false;

          for (Ingredient i : pizza.getIngredients()) {
            if (i.getNom().equalsIgnoreCase(ing)) {
              found = true;
              break;
            }
          }

          if (!found) {
            ok = false;
            break;
          }
        }

        if (!ok) {
          continue;
        }
      }

      result.add(pizza);
    }

    return result;
  }


  /**
   * Supprime tous les filtres définis.
   */
  @Override
  public void supprimerFiltres() {
    filtreType = null;
    filtreIngredients = null;
    filtrePrixMax = null;
  }


  /**
   * Retourne l'ensemble des évaluations d'une pizza.
   *
   * @param pizza la pizza dont on veut les évaluations
   * @return l'ensemble des évaluations ou null si la pizza n'est pas valide
   */
  @Override
  public Set<Evaluation> getEvaluationsPizza(Pizza pizza) {

    if (pizza == null
            || !data.pizzasByName.containsValue(pizza)) {
      return null;
    }

    Set<Evaluation> evals = data.evaluationsParPizza.get(pizza);
    if (evals == null) {
      return new HashSet<>();
    }

    return new HashSet<>(evals);
  }


  /**
   * Retourne la note moyenne des évaluations d'une pizza.
   *
   * @param pizza la pizza dont on veut la note moyenne
   * @return la note moyenne, -1 si aucune évaluation, -2 si la pizza est invalide
   */
  @Override
  public double getNoteMoyenne(Pizza pizza) {

    if (pizza == null
            || !data.pizzasByName.containsValue(pizza)) {
      return -2;
    }

    Set<Evaluation> evals = data.evaluationsParPizza.get(pizza);
    if (evals == null || evals.isEmpty()) {
      return -1;
    }

    int somme = 0;
    for (Evaluation e : evals) {
      somme += e.getNote();
    }

    return (double) somme / evals.size();
  }


  /**
   * Ajoute une évaluation à une pizza de la part du client connecté.
   *
   * @param pizza la pizza évaluée
   * @param note la note attribuée (0 à 5)
   * @param commentaire le commentaire associé
   * @return true si l'évaluation a été ajoutée, false sinon
   * @throws NonConnecteException si aucun client n'est connecté
   * @throws CommandeException si le client n'a jamais commandé cette pizza
   */
  @Override
  public boolean ajouterEvaluation(Pizza pizza, int note,
                                   String commentaire)
          throws NonConnecteException, CommandeException {

    if (clientConnecte == null) {
      throw new NonConnecteException();
    }

    if (pizza == null
            || !data.pizzasByName.containsValue(pizza)) {
      return false;
    }

    if (note < 0 || note > 5) {
      return false;
    }

    // Vérifie que le client a commandé la pizza dans une commande traitée
    boolean aCommande = false;

    for (Commande cmd : data.commandes) {
      if (cmd.getClient() == clientConnecte
              && cmd.getStatut() == EtatCommande.traitee
              && cmd.getPizzas().containsKey(pizza)) {
        aCommande = true;
        break;
      }
    }

    if (!aCommande) {
      throw new CommandeException(
              "Le client n'a jamais commandé cette pizza.");
    }

    Set<Evaluation> evals =
            data.evaluationsParPizza.get(pizza);

    if (evals == null) {
      evals = new HashSet<>();
      data.evaluationsParPizza.put(pizza, evals);
    }

    // Vérifie si le client a déjà évalué cette pizza
    for (Evaluation e : evals) {
      if (e.getClient()
              .getInformationPersonnelle()
              .equals(clientConnecte.getInformationPersonnelle())) {
        return false;
      }
    }

    Evaluation eval = new Evaluation(
            clientConnecte.getInformationPersonnelle(),
            pizza,
            note,
            commentaire);

    evals.add(eval);
    return true;
  }

}

    
    
   
   
    
    




  
  
  
  
  
  
  

