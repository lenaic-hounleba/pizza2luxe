package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests JUnit de la classe {@link pizzas.Pizza}.
 *
 * @author diamilatou-assura.diallo
 * @see pizzas.Pizza
 */


package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzas.Pizza;
import pizzas.TypePizza;


/**
 * Tests JUnit de la classe {@link pizzas.Pizza
 * Pizza}.
 *
 * @author diamilatou-assura.diallo
 * @see pizzas.Pizza
 *  @see pizzas.TypePizza
 */
class TestPizza {
  
  /**
   * Une pizza basique : nom, type, prix.
   */
  private Pizza PizzaBasique;
  /**
   * Une pizza complète : nom, type, prix et listeIngredients.
   */
  private Pizza PizzaComplete;
  
  /**
   * Instancie une pizza basique et une complète pour les tests.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @BeforeEach
  void setUp() throws Exception {
    PizzaBasique = new Pizza("4fromages", TypePizza.Vegetarienne);
    PizzaComplete =new Pizza("4fromages", TypePizza.Vegetarienne,8.50);
  }
  
  /**
   * Ne fait rien après les tests : à modifier au besoin.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @AfterEach
  void tearDown() throws Exception {}
  
  /**
   * Vérifie que l'on peut positionner un êge de 25 ans.
   */
  @Test
  void testAge25Basique() {
    infoBasique.setAge(25);
    assertEquals(infoBasique.getAge(), 25);
  }
  
  /**
   * Vérifie qu'on ne peut pas positionner un âge négatif sur une information
   * basique.
   */
  @Test
  void testAgeNegatifBasique() {
    infoBasique.setAge(-20);
    assertTrue(infoBasique.getAge() != -20);
  }
  
  /**
   * Vérifie qu'on ne peut pas positionner un age négatif sur une information
   * complàte : l'âge reste le même qu'avant.
   */
  @Test
  void testAgeNegatifComplet() {
    int age = infoComplete.getAge();
    infoComplete.setAge(-20);
    assertEquals(infoComplete.getAge(), age);
  }
  
  
  /**
   * Vérifie qu'une adresse n'est pas null quand on crée une information
   * personnelle.
   */
  @Test
  void testAdresseNonNull() {
    assertTrue(infoBasique.getAdresse() != null);
    assertTrue(infoComplete.getAdresse() != null);
  }
  
  /**
   * Vérifie qu'on ne peut pas positionner une adresse null sur une information
   * existante.
   */
  @Test
  void testSetterAdresseNull() {
    infoComplete.setAdresse(null);
    assertTrue(infoComplete.getAdresse() != null);
  }
  
  /**
   * Vérifie que les paramètres des constructeurs sont correctement gérés.
   */
  @Test
  void testConstructeur() {
    InformationPersonnelle inf =
        new InformationPersonnelle("Vador", "Dark", null, -30);
    assertEquals(inf.getNom(), "Vador");
    assertEquals(inf.getPrenom(), "Dark");
    assertTrue(inf.getAdresse() != null);
    assertTrue(inf.getAge() >= 0);
  }
  
}
