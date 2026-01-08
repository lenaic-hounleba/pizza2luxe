package pizzas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;



/**
 * Classe contenant les données partagées de la pizzeria. Elle centralise
 * l'ensemble des informations utilisées par les services client et pizzaïolo.
 * 
 * 
 * @author Dorian Fleurquin
 */
public class PizzeriaData implements java.io.Serializable {
  
  
  /**
   * Identifiant de sérialisation.
   */
  private static final long serialVersionUID = 1L;
  
  /** Clients inscrits, indexés par leur adresse email. */
  public final Map<String, Client> clientsByEmail;
  
  /** Ingrédients disponibles, indexés par leur nom. */
  public final Map<String, Ingredient> ingredientsByName;
  
  /** Pizzas en vente, indexées par leur nom. */
  public final Map<String, Pizza> pizzasByName;
  
  /** Ingrédients interdits pour chaque type de pizza. */
  public final Map<TypePizza, Set<String>> ingredientsInterditsParType;
  
  /** Ensemble de toutes les commandes créées par les clients. */
  public final List<Commande> commandes;
  
  /** Identifiant utilisé pour la création des commandes. */
  public int nextCommandeId;
  
  /** Évaluations associées à chaque pizza. */
  public final Map<Pizza, Set<Evaluation>> evaluationsParPizza;
  
  /**
   * Constructeur de la classe PizzeriaData. Initialise les différentes
   * structures de données nécessaires au fonctionnement de la pizzeria.
   */
  public PizzeriaData() {
    clientsByEmail = new HashMap<>();
    ingredientsByName = new HashMap<>();
    pizzasByName = new HashMap<>();
    ingredientsInterditsParType = new HashMap<>();
    commandes = new ArrayList<>();
    evaluationsParPizza = new HashMap<>();
    nextCommandeId = 1;
    
    for (TypePizza type : TypePizza.values()) {
      ingredientsInterditsParType.put(type, new HashSet<>());
    }
  }
}
