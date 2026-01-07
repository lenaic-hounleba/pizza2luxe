




package pizzas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Classe qui gère les traitements liés au pizzaïolo.
 *
 * Cette classe implémente les services définis par l'interface InterPizzaiolo.
 * @author Dorian Fleurquin
 */
public class GestionPizzaiolo implements InterPizzaiolo {

  /** Données partagées de la pizzeria. */
  private final PizzeriaData data;



  /**
   * Constructeur du service pizzaïolo.
   *
   * @param data les données partagées de la pizzeria
   */
  public GestionPizzaiolo(PizzeriaData data) {
    this.data = data;
  }

  /**
   * Vérifie qu'une chaine est valide (non null et non vide).
   *
   * @param s la chaine à tester
   * @return true si la chaine est valide, false sinon
   */
  private boolean chaineValide(String s) {
    return s != null && !s.isEmpty();
  }

  /**
   * Vérifie qu'une pizza est valide (non null et existante dans la pizzeria).
   *
   * @param pizza la pizza
   * @return true si la pizza est valide, false sinon
   */
  private boolean pizzaValide(Pizza pizza) {
    return pizza != null && data.pizzasByName.containsValue(pizza);
  }

  /**
   * Création d'un nouvel ingrédient avec son prix.
   *
   * @param nom le nom de l'ingrédient
   * @param prix le prix de l'ingrédient
   * @return 0 si ok, -1 si nom invalide, -2 si déjà existant, -3 si prix invalide
   */
  @Override
  public int creerIngredient(String nom, double prix) {
    if (!chaineValide(nom)) {
      return -1;
    }
    if (data.ingredientsByName.containsKey(nom)) {
      return -2;
    }
    if (prix <= 0) {
      return -3;
    }

    Ingredient ing = new Ingredient(nom, prix);
    data.ingredientsByName.put(nom, ing);
    return 0;
  }

  /**
   * Change le prix d'un ingrédient déjà existant.
   *
   * @param nom le nom de l'ingrédient
   * @param prix le nouveau prix
   * @return 0 si ok, -1 si nom invalide, -2 si prix invalide, -3 si absent
   */
  @Override
  public int changerPrixIngredient(String nom, double prix) {
    if (!chaineValide(nom)) {
      return -1;
    }
    if (prix <= 0) {
      return -2;
    }

    Ingredient ing = data.ingredientsByName.get(nom);
    if (ing == null) {
      return -3;
    }

    ing.setPrix(prix);
    return 0;
  }

  /**
   * Interdit un ingrédient pour un type de pizza.
   *
   * @param nomIngredient le nom de l'ingrédient
   * @param type le type de pizza
   * @return true si l'interdiction a été ajoutée, false sinon
   */
  @Override
  public boolean interdireIngredient(String nomIngredient, TypePizza type) {
    if (!chaineValide(nomIngredient) || type == null) {
      return false;
    }
    if (!data.ingredientsByName.containsKey(nomIngredient)) {
      return false;
    }

    Set<String> interdits = data.ingredientsInterditsParType.get(type);
    if (interdits == null) {
      interdits = new HashSet<>();
      data.ingredientsInterditsParType.put(type, interdits);
    }

    interdits.add(nomIngredient);
    return true;
  }

  /**
   * Crée une nouvelle pizza.
   *
   * @param nom le nom de la pizza
   * @param type le type de la pizza
   * @return la pizza créée ou null si problème (nom invalide ou doublon)
   */
  @Override
  public Pizza creerPizza(String nom, TypePizza type) {
    if (!chaineValide(nom) || type == null) {
      return null;
    }
    if (data.pizzasByName.containsKey(nom)) {
      return null;
    }

    Pizza pizza = new Pizza(nom, type);
    data.pizzasByName.put(nom, pizza);
    return pizza;
  }

  /**
   * Ajoute un ingrédient à une pizza.
   *
   * @param pizza la pizza à modifier
   * @param nomIngredient le nom de l'ingrédient
   * @return 0 si ok, -1 si pizza invalide, -2 si ingrédient invalide, -3 si interdit
   */
  @Override
  public int ajouterIngredientPizza(Pizza pizza, String nomIngredient) {
    if (!pizzaValide(pizza)) {
      return -1;
    }
    if (!chaineValide(nomIngredient)) {
      return -2;
    }

    Ingredient ing = data.ingredientsByName.get(nomIngredient);
    if (ing == null) {
      return -2;
    }

    Set<String> interdits = data.ingredientsInterditsParType.get(pizza.getType());
    if (interdits != null && interdits.contains(nomIngredient)) {
      return -3;
    }

    pizza.getIngredients().add(ing);
    return 0;
  }

  /**
   * Retire un ingrédient d'une pizza.
   *
   * @param pizza la pizza à modifier
   * @param nomIngredient le nom de l'ingrédient
   * @return 0 si ok, -1 si pizza invalide, -2 si ingrédient invalide, -3 si absent
   */
  @Override
  public int retirerIngredientPizza(Pizza pizza, String nomIngredient) {
    if (!pizzaValide(pizza)) {
      return -1;
    }
    if (!chaineValide(nomIngredient)) {
      return -2;
    }
    if (!data.ingredientsByName.containsKey(nomIngredient)) {
      return -2;
    }

    Ingredient aRetirer = null;
    for (Ingredient ing : pizza.getIngredients()) {
      if (ing != null && ing.getNom().equals(nomIngredient)) {
        aRetirer = ing;
        break;
      }
    }

    if (aRetirer == null) {
      return -3;
    }

    pizza.getIngredients().remove(aRetirer);
    return 0;
  }

  /**
   * Vérifie si une pizza contient des ingrédients interdits pour son type.
   *
   * @param pizza la pizza à vérifier
   * @return l'ensemble des noms d'ingrédients interdits, vide si aucun, ou null si pizza invalide
   */
  @Override
  public Set<String> verifierIngredientsPizza(Pizza pizza) {
    if (!pizzaValide(pizza)) {
      return null;
    }

    Set<String> result = new HashSet<>();
    Set<String> interdits = data.ingredientsInterditsParType.get(pizza.getType());
    if (interdits == null || interdits.isEmpty()) {
      return result;
    }

    for (Ingredient ing : pizza.getIngredients()) {
      if (ing != null && interdits.contains(ing.getNom())) {
        result.add(ing.getNom());
      }
    }

    return result;
  }

  /**
   * Ajoute une photo à une pizza.
   *
   * @param pizza la pizza concernée
   * @param file le fichier image
   * @return true si la photo a été ajoutée, false sinon
   * @throws IOException en cas d'erreur de lecture
   */
  @Override
  public boolean ajouterPhoto(Pizza pizza, String file) throws IOException {
    if (!pizzaValide(pizza)) {
      return false;
    }
    return pizza.ajouterPhoto(pizza, file);
  }

  /**
   * Retourne le prix de vente d'une pizza.
   *
   * @param pizza la pizza
   * @return le prix ou -1 si la pizza n'est pas valide
   */
  @Override
  public double getPrixPizza(Pizza pizza) {
    if (!pizzaValide(pizza)) {
      return -1;
    }
    return pizza.getPrixPizza(pizza);
  }

  /**
   * Modifie le prix de vente d'une pizza.
   *
   * @param pizza la pizza
   * @param prix le nouveau prix
   * @return true si le prix a été modifié, false sinon
   */
  @Override
  public boolean setPrixPizza(Pizza pizza, double prix) {
    if (!pizzaValide(pizza)) {
      return false;
    }
    return pizza.setPrixPizza(pizza, prix);
  }

  /**
   * Calcule le prix minimal d'une pizza.
   *
   * @param pizza la pizza
   * @return le prix minimal ou -1 si la pizza n'est pas valide
   */
  @Override
  public double calculerPrixMinimalPizza(Pizza pizza) {
    if (!pizzaValide(pizza)) {
      return -1;
    }
    return pizza.calculerPrixMinimalPizza(pizza);
  }

  /**
   * Retourne l'ensemble des pizzas en vente.
   *
   * @return l'ensemble des pizzas
   */
  @Override
  public Set<Pizza> getPizzas() {
    return new HashSet<>(data.pizzasByName.values());
  }

  /**
   * Renvoie l'ensemble des clients qui ont un compte dans la pizzeria.
   *
   * @return l'ensemble des informations personnelles des clients
   */
  @Override
  public Set<InformationPersonnelle> ensembleClients() {
    Set<InformationPersonnelle> result = new HashSet<>();
    for (Client c : data.clientsByEmail.values()) {
      if (c != null) {
        result.add(c.getInformationPersonnelle());
      }
    }
    return result;
  }

  /**
   * Retourne l'ensemble des commandes traitées, de la plus ancienne à la plus récente.
   *
   * @return la liste des commandes traitées
   */
  @Override
  public List<Commande> commandesDejaTraitees() {
    List<Commande> result = new ArrayList<>();
    for (Commande cmd : data.commandes) {
      if (cmd.getStatut() == EtatCommande.traitee) {
        result.add(cmd);
      }
    }
    return result;
  }

  /**
   * Retourne l'ensemble des commandes des clients non encore traitées.
   * Une fois que ces commandes ont été lues en appelant cette méthode,
   * on les considère comme traitées.
   *
   * @return l'ensemble ordonné des commandes à traiter
   */
  @Override
  public List<Commande> commandeNonTraitees() {
    List<Commande> result = new ArrayList<>();

    for (Commande cmd : data.commandes) {
      if (cmd.getStatut() == EtatCommande.validee) {
        result.add(cmd);
      }
    }

    for (Commande cmd : result) {
      cmd.traiter();
    }

    return result;
  }


  /**
   * Retourne les commandes déjà traitées d'un client.
   *
   * @param client le client dont on veut les commandes
   * @return la liste des commandes du client, vide si aucune, ou null si client invalide
   */
  @Override
  public List<Commande> commandesTraiteesClient(InformationPersonnelle client) {
    if (client == null) {
      return null;
    }

    List<Commande> result = new ArrayList<>();
    for (Commande cmd : data.commandes) {
      if (cmd.getStatut() == EtatCommande.traitee
              && cmd.getClient() != null
              && cmd.getClient().getInformationPersonnelle().equals(client)) {
        result.add(cmd);
      }
    }

    return result;
  }

  /**
   * Calcule le bénéfice unitaire pour chaque pizza en vente.
   *
   * @return une map pizza -> bénéfice unitaire
   */
  @Override
  public Map<Pizza, Double> beneficeParPizza() {
    Map<Pizza, Double> result = new HashMap<>();

    for (Pizza p : data.pizzasByName.values()) {
      double vente = p.getPrixPizza(p);
      double min = p.calculerPrixMinimalPizza(p);
      double benef = vente - min;
      if (benef < 0) {
        benef = 0;
      }
      result.put(p, benef);
    }

    return result;
  }

  /**
   * Calcule le bénéfice d'une commande.
   *
   * @param commande la commande
   * @return le bénéfice ou -1 si la commande n'est pas valide
   */
  @Override
  public double beneficeCommandes(Commande commande) {
    if (commande == null || !data.commandes.contains(commande)) {
      return -1;
    }

    double total = 0.0;
    for (Map.Entry<Pizza, Integer> e : commande.getPizzas().entrySet()) {
      Pizza p = e.getKey();
      int qte = e.getValue();

      double vente = p.getPrixPizza(p);
      double min = p.calculerPrixMinimalPizza(p);
      double benefUnitaire = vente - min;
      if (benefUnitaire < 0) {
        benefUnitaire = 0;
      }

      total += benefUnitaire * qte;
    }

    return total;
  }

  /**
   * Calcule le bénéfice global pour toutes les commandes déjà traitées.
   *
   * @return le bénéfice global
   */
  @Override
  public double beneficeToutesCommandes() {
    double total = 0.0;

    for (Commande cmd : data.commandes) {
      if (cmd.getStatut() == EtatCommande.traitee) {
        total += beneficeCommandes(cmd);
      }
    }

    return total;
  }

  /**
   * Calcule le nombre total de pizzas commandées par client (sur les commandes traitées).
   *
   * @return une map client -> nombre total de pizzas commandées
   */
  @Override
  public Map<InformationPersonnelle, Integer> nombrePizzasCommandeesParClient() {
    Map<InformationPersonnelle, Integer> result = new HashMap<>();

    for (Commande cmd : data.commandes) {
      if (cmd.getStatut() != EtatCommande.traitee) {
        continue;
      }

      InformationPersonnelle ip = cmd.getClient().getInformationPersonnelle();
      int totalCmd = 0;

      for (int qte : cmd.getPizzas().values()) {
        totalCmd += qte;
      }

      Integer actuel = result.get(ip);
      if (actuel == null) {
        result.put(ip, totalCmd);
      } else {
        result.put(ip, actuel + totalCmd);
      }
    }

    return result;
  }

  /**
   * Calcule le bénéfice généré par client (sur les commandes traitées).
   *
   * @return une map client -> bénéfice total
   */
  @Override
  public Map<InformationPersonnelle, Double> beneficeParClient() {
    Map<InformationPersonnelle, Double> result = new HashMap<>();

    for (Commande cmd : data.commandes) {
      if (cmd.getStatut() != EtatCommande.traitee) {
        continue;
      }

      InformationPersonnelle ip = cmd.getClient().getInformationPersonnelle();
      double benef = beneficeCommandes(cmd);

      Double actuel = result.get(ip);
      if (actuel == null) {
        result.put(ip, benef);
      } else {
        result.put(ip, actuel + benef);
      }
    }

    return result;
  }

  /**
   * Pour une pizza, retourne le nombre de fois où elle a été commandée
   * (sur les commandes traitées).
   *
   * @param pizza la pizza
   * @return le nombre de fois ou -1 si la pizza n'est pas valide
   */
  @Override
  public int nombrePizzasCommandees(Pizza pizza) {
    if (!pizzaValide(pizza)) {
      return -1;
    }

    int total = 0;
    for (Commande cmd : data.commandes) {
      if (cmd.getStatut() != EtatCommande.traitee) {
        continue;
      }

      Integer qte = cmd.getPizzas().get(pizza);
      if (qte != null) {
        total += qte;
      }
    }

    return total;
  }

  /**
   * Retourne la liste des pizzas triées par nombre de commandes (décroissant).
   *
   * @return la liste des pizzas, vide si aucune pizza n'est en vente
   */
  @Override
  public List<Pizza> classementPizzasParNombreCommandes() {
    List<Pizza> pizzas = new ArrayList<>(data.pizzasByName.values());

    Collections.sort(pizzas, new Comparator<Pizza>() {
      @Override
      public int compare(Pizza p1, Pizza p2) {
        int n1 = nombrePizzasCommandees(p1);
        int n2 = nombrePizzasCommandees(p2);
        return Integer.compare(n2, n1);
      }
    });

    return pizzas;
  }
}
