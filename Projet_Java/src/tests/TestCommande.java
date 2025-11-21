package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzas.Client;
import pizzas.Commande;
import pizzas.CommandeException;
import pizzas.Pizza;
import pizzas.TypePizza;

/**
 * Tests JUnit de la classe {@link pizzas.Commande Commande}.
 *
 * Teste l'ajout de pizzas, le calcul du total et les exceptions dans les cas où
 * la commande est clôturée.
 * 
 * @author lenaic-love.hounleba
 */
class TestCommande {
  
  private Commande commande;
  private Pizza pizza1;
  private Pizza pizza2;
  private Pizza pizza3;
  private Client client1 = new Client("paulkun@gmail.com", "123456", "Paul", "Kun", "Rue de Georges", 16);
  
  /**
   * Initialise une commande et quelques pizzas de test.
   *
   * @throws Exception jamais levée ici
   */
  @BeforeEach
  void setUp() throws Exception {
    
    commande = new Commande(150, client1); // numéro quelconque
    
    pizza1 = new Pizza("Regina", TypePizza.Viande);
    pizza2 = new Pizza("Margarita", TypePizza.Vegetarienne);
    pizza3 = new Pizza("Olda", TypePizza.Regionale);
    
    // On force les prix vu que Pizza actuelle ne marche pas correctement
    pizza1.prixVente = 12.0;
    pizza2.prixVente = 10.0;
  }
  
  /**
   * Test : ajout d'une seule pizza.
   *
   * @throws Exception ne doit pas être levée
   */
  @Test
  void testAjoutPizzaSimple() throws Exception {
    commande.ajouterPizza(pizza1, 2); // 2 Regina
    assertEquals(1, commande.getPizzas().size());
  }
  
  /**
   * Test du calcul correct du total.
   *
   * @throws Exception ne doit pas être levée
   */
  @Test
  void testCalculTotal() throws Exception {
    commande.ajouterPizza(pizza1, 2); // 2 × 12 = 24
    commande.ajouterPizza(pizza2, 1); // 1 × 10 = 10
    
    double total = commande.getMontantTotal();
    assertEquals(34.0, total);
  }
  
  /**
   * Vérifie qu'ajouter une pizza avec une quantité <= 0 déclenche bien une
   * exception CommandeException.
   */
  @Test
  void testQuantiteInvalide() {
    assertThrows(CommandeException.class, () -> {
      commande.ajouterPizza(pizza1, -1);
    });
  }
  
  /**
   * Vérifie qu'on ne peut plus modifier la commande après l'avoir clôturée.
   *
   * @throws Exception ne doit pas être levée (avant la clôture)
   */
  @Test
  void testModificationApresCloture() throws Exception {
    commande.ajouterPizza(pizza1, 1);
    commande.annuler();
    
    assertThrows(CommandeException.class, () -> {
      commande.ajouterPizza(pizza2, 1);
    });
  }
  
  /**
   * Vérifie que l'ajout cumulé de pizzas du même type fonctionne : on ajoute
   * deux fois la même pizza, les quantités se cumulent.
   */
  @Test
  void testCumulQuantites() throws Exception {
    commande.ajouterPizza(pizza1, 1);
    commande.ajouterPizza(pizza1, 3);
    
    // Quantité totale = 4
    assertEquals(4, commande.getPizzas().get(pizza1).intValue());
  }
}
