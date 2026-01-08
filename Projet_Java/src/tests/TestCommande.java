package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pizzas.Client;
import pizzas.Commande;
import pizzas.CommandeException;
import pizzas.EtatCommande;
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
   * Test de l'ajout d'une pizza avec une quantité.
   */
  @Test
  void testAjoutPizza() {
    commande.ajouterPizza(pizza1, 2);
    
    assertEquals(1, commande.getPizzas().size());
    assertEquals(2, commande.getPizzas().get(pizza1));
  }
  
  /**
   * Test du cumul des quantités pour une même pizza.
   */
  @Test
  void testCumulQuantites() {
    commande.ajouterPizza(pizza1, 1);
    commande.ajouterPizza(pizza1, 3);
    
    assertEquals(4, commande.getPizzas().get(pizza1));
  }
  
  /**
   * Test du calcul correct du montant total.
   */
  @Test
  void testCalculMontantTotal() {
    commande.ajouterPizza(pizza1, 1); // 12
    commande.ajouterPizza(pizza2, 2); // 2 × 10 = 20
    
    assertEquals(32.0, commande.calculerMontantTotal());
  }
  
  /**
   * Vérifie que la commande est modifiable lorsqu'elle est créée.
   */
  @Test
  void testCommandeModifiable() {
    assertTrue(commande.estModifiable());
  }
  
  /**
   * Vérifie qu'on ne peut plus ajouter de pizza après validation.
   */
  @Test
  void testAjoutApresValidation() {
    commande.valider();
    
    assertThrows(CommandeException.class, () -> {
      commande.ajouterPizza(pizza1, 1);
    });
  }
  
  /**
   * Vérifie qu'une commande validée passe bien à l'état traitée.
   */
  @Test
  void testTraitementCommande() {
    commande.ajouterPizza(pizza1, 1);
    commande.valider();
    commande.traiter();
    
    assertEquals(EtatCommande.traitee, commande.getStatut());
  }
  
  /**
   * Vérifie qu'une quantité invalide déclenche une exception.
   */
  @Test
  void testQuantiteInvalide() {
    assertThrows(CommandeException.class, () -> {
      commande.ajouterPizza(pizza1, 0);
    });
  }
}
