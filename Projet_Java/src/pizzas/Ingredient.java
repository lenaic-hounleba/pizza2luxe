package pizzas;

/**
 * classe qui permet de définir un ingrédient en lui attribuant un nom et un
 * prix.
 *
 * @author diamilatou-assura.diallo
 */
public class Ingredient implements java.io.Serializable {
  
  
  /**
   * Identifiant de sérialisation.
   */
  private static final long serialVersionUID = 1L;
  
  /** Nom de l'ingrédient. */
  String nom;
  
  /** Prix de l'ingrédient. */
  double prix;
  
  /**
   * Construit un nouvel ingrédient avec un nom et un prix.
   *
   * @param nom le nom de l'ingrédient
   * @param prix le prix de l'ingrédient
   */
  public Ingredient(String nom, double prix) {
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
