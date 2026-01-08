package tests;

import pizzas.Ingredient;
import pizzas.Pizza;
import pizzas.TypePizza;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests JUnit de la classe {@link pizzas.Pizza}.
 *
 * Ces tests vérifient :
 * <ul>
 * <li>La création d'une pizza</li>
 * <li>La gestion des ingrédients</li>
 * <li>La gestion du prix</li>
 * <li>La modification des attributs</li>
 * <li>L'ajout d'une photo</li>
 * </ul>
 *
 * @author diamilatou-assura.diallo
 */
public class TestPizza {
  
  /** Pizza testée */
  private Pizza pizza;
  
  /** Ingrédients de test */
  private Ingredient fromage;
  private Ingredient tomate;
  
  /**
   * Initialisation des objets avant chaque test.
   */
  @BeforeEach
  void setUp() {
    pizza = new Pizza("Margherita", TypePizza.Vegetarienne);
    fromage = new Ingredient("Fromage", 2.0);
    tomate = new Ingredient("Tomate", 1.0);
  }
  
  /**
   * Vérifie la création correcte d'une pizza.
   */
  @Test
  void testCreationPizza() {
    assertEquals("Margherita", pizza.getNom());
    assertEquals(TypePizza.Vegetarienne, pizza.getType());
    assertNotNull(pizza.getIngredients());
    assertEquals(0, pizza.getIngredients().size());
    assertEquals(0.0, pizza.getPrixPizza());
  }
  
  /**
   * Vérifie l'ajout d'un ingrédient à une pizza.
   */
  @Test
  void testAjouterIngredient() {
    pizza.getIngredients().add(fromage);
    
    assertEquals(1, pizza.getIngredients().size());
    assertTrue(pizza.getIngredients().contains(fromage));
  }
  
  /**
   * Vérifie qu'un ingrédient ne peut pas être ajouté en double
   */
  @Test
  void testPasDeDoublonIngredient() {
    pizza.getIngredients().add(fromage);
    pizza.getIngredients().add(fromage);
    
    assertEquals(1, pizza.getIngredients().size());
  }
  
  /**
   * Vérifie la modification du prix de vente.
   */
  @Test
  void testSetPrixPizza() {
    pizza.getIngredients().add(fromage);
    pizza.getIngredients().add(tomate);
    
    double prixMinimal = pizza.calculerPrixMinimalPizza();
    assertTrue(pizza.setPrixPizza(prixMinimal + 5));
    
    assertEquals(prixMinimal + 5, pizza.getPrixPizza());
  }
  
  /**
   * Vérifie la modification du nom de la pizza.
   */
  @Test
  void testChangerNom() {
    pizza.setNom("Reine");
    assertEquals("Reine", pizza.getNom());
  }
  
  /**
   * Vérifie la modification du type de la pizza
   */
  @Test
  void testChangerType() {
    pizza.setType(TypePizza.Viande);
    assertEquals(TypePizza.Viande, pizza.getType());
  }
  
  /**
   * Vérifie l'ajout d'une photo à la pizza.
   */
  @Test
  void testAjouterPhoto() throws Exception {
    boolean ok = pizza.ajouterPhoto("margherita.jpg");
    assertTrue(ok);
  }
}
