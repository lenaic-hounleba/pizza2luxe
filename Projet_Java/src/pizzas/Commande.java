package pizzas;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe qui permet de créer une commande à partir des pizzas existantes et
 * permet de gérer également l'état des commandes.
 *
 * @author Lenaïc Love HOUNLEBA
 */
public class Commande {
  private String pizzas;
  private double montantTotal;
  private String adresseLivraison;
  private List<String> etat = new ArrayList<>();
  
  
  
  public Commande(String pizzas, double montantTotal, String adresseLivraison,
      List<String> etat) {
    this.pizzas = pizzas;
    this.montantTotal = montantTotal;
    this.adresseLivraison = adresseLivraison;
    this.etat = etat;
  }
  
  
  public String getPizzas() {
    return pizzas;
  }
  
  
  public void setPizzas(String pizzas) {
    this.pizzas = pizzas;
  }
  
  
  public double getmontantTotal() {
    return montantTotal;
  }
  
  
  public void setmontantTotal(double montantTotal) {
    this.montantTotal = montantTotal;
  }
  
  
  public void setadresseLivraison(String adresseLivraison) {
    this.adresseLivraison = adresseLivraison;
  }
  
  public String getadresseLivraison() {
    return adresseLivraison;
  }
  
  
  public List<String> getetat() {
    return etat;
  }
  
  
  public void setetat(List<String> etat) {
    this.etat = etat;
  }
  
  
  
}
