package pizzas;

/**
 * classe qui permet de définir un ingrédient en lui attribuant un nom et un prix.
 * 
 * @author diamilatou-assura.diallo
 */
public class Ingredient {
  String nom;
  double prix;
  
  Ingredient(String nom, double prix) {
    this.nom = nom;
    this.prix = prix;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public double getPrix() {
    return prix;
  }

  public void setPrix(double prix) {
    this.prix = prix;
  }
  
}
