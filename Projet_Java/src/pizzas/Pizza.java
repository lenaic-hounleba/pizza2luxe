package pizzas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Set;

// A compléter
public class Pizza {
  String nom;
  TypePizza type;
  List<Ingredient> ingredients;
  double prixVente;
 
/**
 * 
 */
  Pizza(String nom,TypePizza type){
    this.nom=nom;
    this.type=type;
    this.ingredients=new ArrayList<>();
    this.prixVente=0.0;
    
    
  }
  /**
   * Retourne le prix de vente d'une pizza. Si le prix n'a pas été fixé
   * manuellement, retourne le prix minimal basé sur le prix des ingrédients.
   *
   * @param pizza la pizza dont on veut connaitre le prix
   * @return le prix de la pizza ou -1 si la pizza n'était pas valide
   */
  double getPrixPizza(Pizza pizza) {
    return pizza.prixVente;
  }
  
  /**
   * Modifie le prix de vente d'une pizza. Le prix doit être supérieur ou égal
   * au prix minimal de la pizza.
   *
   * @param pizza la pizza dont on change le prix
   * @param prix le nouveau prix
   * @return <code>true</code> si le prix a été modifié car correct ou
   *         <code>false</code> si le prix proposé est inférieur au prix minimal
   *         (dans ce cas, le prix n'a pas été modifié) ou que la pizza n'était
   *         pas valide
   */
  boolean setPrixPizza(Pizza pizza, double prix);
  
  /**
   * Calcule le prix minimal d'une pizza en fonction de ses ingrédients (sans
   * modifier le prix courant de la pizza). Le prix minimal d'une pizza est la
   * somme des prix de ses ingrédients augmentée de 40% et arrondie à la dizaine
   * d'€ supérieure.
   *
   * @param pizza la pizza dont on veut calculer le prix minimal
   * @return le prix minimal de la pizza ou -1 si la pizza n'est pas valide
   */
  double calculerPrixMinimalPizza(Pizza pizza);
  
  /**
   * Retourne l'ensemble des pizzas.
   *
   * @return l'ensemble des pizzas (l'ensemble est vide si aucune pizza n'a
   *         encore été créée)
   */
  Set<Pizza> getPizzas();
  
  /**
   * Renvoie l'ensemble des clients qui ont un compte dans la pizzeria.
   *
   * @return l'ensemble des clients (l'ensemble est vide s'il n'y a aucun
   *         client)
   */
}
