package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pizzas.Client;
import pizzas.Commande;
import pizzas.CommandeException;
import pizzas.Pizza;
import pizzas.TypePizza;

/**
 * Tests unitaires de la classe {@link pizzas.Commande}.
 *
 * @author lenaic-love.hounleba
 */
class TestCommande {
  
  private Commande commande;
  private Pizza pizza1;
  private Pizza pizza2;
  private Client client;
  
  @BeforeEach
  void setUp() {
    client =
        new Client("paul@gmail.com", "1234", "Paul", "Kun", "Rue de Brest", 20);
    
    commande = new Commande(1, client);
    
    pizza1 = new Pizza("Regina", TypePizza.Viande);
    pizza2 = new Pizza("Margarita", TypePizza.Vegetarienne);
    
    pizza1.setPrixVente(12.0);
    pizza2.setPrixVente(10.0);
  }
  
  /**
   * Test de l'ajout simple d'une pizza.
   */
  @Test
  void testAjoutPizza() {
    commande.ajouterPizza(pizza1);
    assertEquals(1, commande.getPizzas().size());
  }
  
  /**
   * Test du cumul des quantités pour une même pizza.
   */
  @Test
  void testCumulQuantite() {
    commande.ajouterPizza(pizza1);
    commande.ajouterPizza(pizza1);
    
    assertEquals(2, commande.getPizzas().get(pizza1));
  }
  
  /**
   * Test du calcul du montant total.
   */
  @Test
  void testCalculMontant() {
    commande.ajouterPizza(pizza1); // 12
    commande.ajouterPizza(pizza2); // 10
    
    assertEquals(22.0, commande.calculerMontantTotal());
  }
  
  /**
   * Vérifie qu'une commande validée ne peut plus être modifiée.
   */
  @Test
  void testAjoutApresValidation() {
    commande.valider();
    
    assertThrows(CommandeException.class, () -> {
      commande.ajouterPizza(pizza1);
    });
  }
}
