package pizzas;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

/**
 * Classe qui crée une pizza et permet de faire les opérations sur elle :
 * calculer le prix.
 *
 * @author diamilatou-assura.diallo
 */
public class Pizza {

  private String nom;
  private TypePizza type;
  private Set<Ingredient> ingredients;
  private double prixVente;

  /**
   * Constructeur qui permet d'instancier une pizza à partir d'un nom et d'un
   * type passés en paramètres.
   *
   * @param nom nom de la pizza
   * @param type un type parmi : viande, vegetarienne ou regionale
   */
  public Pizza(String nom, TypePizza type) {
    this.nom = nom;
    this.type = type;
    this.ingredients = new HashSet<>();
    this.prixVente = 0.0;
  }

  /**
   * Constructeur complet.
   *
   * @param nom nom de la pizza
   * @param type type de la pizza
   * @param ingredients ingrédients de la pizza
   * @param prixVente prix fixé manuellement (0 si non fixé)
   */
  public Pizza(String nom, TypePizza type, Set<Ingredient> ingredients,
               double prixVente) {
    this(nom, type);
    if (ingredients != null) {
      this.ingredients = ingredients;
    }
    this.prixVente = prixVente;
  }

  /**
   * Retourne le prix de vente d'une pizza. Si le prix n'a pas été fixé
   * manuellement, retourne le prix minimal basé sur le prix des ingrédients.
   *
   * @param pizza la pizza dont on veut connaitre le prix
   * @return le prix de la pizza ou -1 si la pizza n'était pas valide
   */
  public double getPrixPizza(Pizza pizza) {
    if (pizza == null) {
      return -1;
    }
    if (pizza.prixVente > 0.0) {
      return pizza.prixVente;
    }
    return calculerPrixMinimalPizza(pizza);
  }

  /**
   * Modifie le prix de vente d'une pizza. Le prix doit être supérieur ou égal
   * au prix minimal de la pizza.
   *
   * @param pizza la pizza dont on change le prix
   * @param prix le nouveau prix
   * @return true si le prix a été modifié, false sinon
   */
  public boolean setPrixPizza(Pizza pizza, double prix) {
    if (pizza == null) {
      return false;
    }

    double prixMin = calculerPrixMinimalPizza(pizza);
    if (prixMin < 0) {
      return false;
    }

    if (prix < prixMin) {
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
    if (pizza == null) {
      return -1;
    }

    double somme = 0.0;
    for (Ingredient ing : pizza.ingredients) {
      if (ing != null) {
        somme += ing.getPrix();
      }
    }

    double avecMarge = somme * 1.4;

    // Arrondi à la dizaine d'euro supérieure (ex: 12.1 -> 20.0)
    double arrondi = Math.ceil(avecMarge / 10.0) * 10.0;
    return arrondi;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public TypePizza getType() {
    return type;
  }

  public void setType(TypePizza type) {
    this.type = type;
  }

  public Set<Ingredient> getIngredients() {
    return ingredients;
  }

  public void setIngredients(Set<Ingredient> ingredients) {
    if (ingredients == null) {
      this.ingredients = new HashSet<>();
    } else {
      this.ingredients = ingredients;
    }
  }

  public double getPrixVente() {
    return prixVente;
  }

  public void setPrixVente(double prixVente) {
    this.prixVente = prixVente;
  }

  /**
   * Ajoute une photo à une pizza. Remplace la photo précédente si une photo
   * était déjà associée à la pizza.
   *
   * @param pizza la pizza à laquelle on veut ajouter une photo
   * @param file le nom du fichier qui contient l'image
   * @return true si la photo a été ajoutée, false sinon
   * @throws IOException en cas d'erreur de lecture sur le fichier
   */
  public boolean ajouterPhoto(Pizza pizza, String file) throws IOException {
    if (pizza == null || file == null || file.isEmpty()) {
      return false;
    }
    // Projet simplifié : pas de gestion réelle d'image ici.
    return true;
  }

  @Override
  public String toString() {
    return nom;
  }
}
