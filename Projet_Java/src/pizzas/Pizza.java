package pizzas;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


// A compléter

/**
 * classe qui crée une pizza et permet de faire les opérations sur elle:
 * calculer le prix.
 *
 * @author diamilatou-assura.diallo
 */

public class Pizza {
  private String nom;
  private TypePizza type;
  private List<Ingredient> ingredients;
  private double prixVente;
  
  /**
   * constructeur qui permet d'instancier une pizza à partir d'un nom et d'un
   * type passés en paramètres.
   *
   * @nom nom de la pizza
   * @type un type parmi les 3 qui existent dans la classe TypePizza:
   *       viande,vegetarienne ou regionale
   */
  Pizza(String nom, TypePizza type) {
    this.nom = nom;
    this.type = type;
    this.ingredients = new ArrayList<>();
    this.prixVente = 0.0;
    
    
  }
  
  /**
   * Retourne le prix de vente d'une pizza. Si le prix n'a pas été fixé
   * manuellement, retourne le prix minimal basé sur le prix des ingrédients.
   *
   * @param pizza la pizza dont on veut connaitre le prix
   * @return le prix de la pizza ou -1 si la pizza n'était pas valide
   */
  public double getPrixPizza(Pizza pizza) {
    double prix = pizza.prixVente;
    if (pizza.prixVente == 0.0) {
      prix = calculerPrixMinimalPizza(pizza);
    }
    return prix;
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
  public boolean setPrixPizza(Pizza pizza, double prix) {
    double prixMin = calculerPrixMinimalPizza(pizza);
    if (prixMin < prix || pizza == null || !(pizza instanceof Pizza)) {
      
      return false;
    }
    pizza.prixVente = prix;
    
    return true;
  }
  
  /**
   * Calcule le prix minimal d'une pizza en fonction de ses ingrédients (sans
   * modifier le prix courant de la pizza). Le prix minimal d'une pizza est la
   * somme des prix de ses ingrédients augmentée de 40% et arrondie à la dizaine
   * d'€ supérieure.
   *
   * @param pizza la pizza dont on veut calculer le prix minimal
   * @return le prix minimal de la pizza ou -1 si la pizza n'est pas valide
   */
  public double calculerPrixMinimalPizza(Pizza pizza) {
    if (pizza == null || !(pizza instanceof Pizza)) {
      return -1;
    }
    double prixmin = 0.0;
    double somme = 0.0;
    double sommetaux = 0.0;
    for (int i = 0; i < this.ingredients.size(); i++) {
      somme += this.ingredients.get(i).getPrix();
      sommetaux = somme + somme * 40 / 100;
      prixmin = Math.ceil(sommetaux * 10) / 10.0;
      
    }
    return prixmin;
  }
  
  /**
   * Ajoute une photo à une pizza. Remplace la photo précédente si une photo
   * était déjà associée à la pizza.
   *
   * @param pizza la pizza à laquelle on veut ajouter une photo
   * @param file le nom du fichier qui contient l'image
   * @return <code>true</code> si la photo a été ajoutée, <code>false</code> en
   *         cas de problème (pizza invalide, fichier qui ne contient pas une
   *         image...)
   * @throws IOException en cas d'erreur de lecture sur le fichier
   */
  public boolean ajouterPhoto(Pizza pizza, String file) throws IOException {
    
    return true;
  }
  
  
}


