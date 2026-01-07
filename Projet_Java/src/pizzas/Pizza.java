
package pizzas;
  
import java.util.HashSet;
import java.util.Set;




/**
* classe qui permet de définir une pizza.
*
* @author diamilatou-assura.diallo
*/

public class Pizza {
   
  private String nom;
  private TypePizza type;
  private Set<Ingredient> ingredients;
  private double prixVente;
  private String photo;
    
    
  /**
  * constructeur qui permet d'instancier une pizza à partir d'un nom et d'un
  * type passés en paramètres.
  *
  * @nom nom de la pizza
  * @type un type parmi les 3 qui existent dans la classe TypePizza:
  *       viande,vegetarienne ou regionale
  */
  
  public Pizza(String nom, TypePizza type) {
    this.nom = nom;
    this.type = type;
    this.ingredients = new HashSet<>();
    this.prixVente = 0.0;
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
    this.ingredients = ingredients;
  }


  public double getPrixVente() {
    return prixVente;
  }


  public void setPrixVente(double prixVente) {
    this.prixVente = prixVente;
  }


  public String getPhoto() {
    return photo;
  }


  public void setPhoto(String photo) {
    this.photo = photo;
  }
    
   

    
    
}



  
  

