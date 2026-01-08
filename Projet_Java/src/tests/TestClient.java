package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pizzas.Client;
import pizzas.Commande;
import pizzas.GestionClient;
import pizzas.InformationPersonnelle;
import pizzas.NonConnecteException;
import pizzas.Pizza;
import pizzas.PizzeriaData;
import pizzas.TypePizza;

/**
 * Tests JUnit client : {@link pizzas.Client} et {@link pizzas.GestionClient}.
 *
 * @author Dorian Fleurquin
 * @see pizzas.Client
 * @see pizzas.GestionClient
 */
class TestClient {
  
  /**
   * Données partagées de la pizzeria.
   */
  private PizzeriaData data;
  
  /**
   * Service côté client.
   */
  private GestionClient service;
  
  /**
   * Informations personnelles de base.
   */
  private InformationPersonnelle info;
  
  /**
   * Client de base.
   */
  private Client client;
  
  /**
   * Instancie les objets nécessaires aux tests.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @BeforeEach
  void setUp() throws Exception {
    data = new PizzeriaData();
    service = new GestionClient(data);
    info = new InformationPersonnelle("Doe", "John", "Paris", 20);
    client = new Client("john@doe.com", "pw", "Doe", "John", "Paris", 20);
  }
  
  /**
   * Ne fait rien après les tests : à modifier au besoin.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @AfterEach
  void tearDown() throws Exception {}
  
  /**
   * Vérifie que l'email du client est bien stocké.
   */
  @Test
  void testClientGetEmail() {
    assertEquals("john@doe.com", client.getEmail());
  }
  
  /**
   * Vérifie que les informations personnelles ne sont pas null.
   */
  @Test
  void testClientInfoNonNull() {
    assertNotNull(client.getInformationPersonnelle());
  }
  
  /**
   * Vérifie que la vérification du mot de passe fonctionne.
   */
  @Test
  void testClientVerifierMotDePasse() {
    assertTrue(client.verifierMotDePasse("pw"));
    assertFalse(client.verifierMotDePasse("wrong"));
  }
  
  /**
   * Vérifie que l'inscription fonctionne.
   */
  @Test
  void testInscriptionOk() {
    int code = service.inscription("john@doe.com", "pw", info);
    assertEquals(0, code);
    assertTrue(data.clientsByEmail.containsKey("john@doe.com"));
  }
  
  /**
   * Vérifie qu'on ne peut pas inscrire deux fois le même email.
   */
  @Test
  void testInscriptionEmailDejaUtilise() {
    assertEquals(0, service.inscription("john@doe.com", "pw", info));
    assertEquals(-1, service.inscription("john@doe.com", "pw2", info));
  }
  
  /**
   * Vérifie que l'inscription échoue si email ou mot de passe est vide.
   */
  @Test
  void testInscriptionEmailOuMdpVide() {
    assertEquals(-2, service.inscription("", "pw", info));
    assertEquals(-2, service.inscription("john@doe.com", "", info));
    assertEquals(-2, service.inscription(null, "pw", info));
    assertEquals(-2, service.inscription("john@doe.com", null, info));
  }
  
  /**
   * Vérifie que l'inscription échoue si les infos personnelles sont null.
   */
  @Test
  void testInscriptionInfoNull() {
    assertEquals(-3, service.inscription("john@doe.com", "pw", null));
  }
  
  /**
   * Vérifie que l'inscription échoue si l'email n'est pas bien formé.
   */
  @Test
  void testInscriptionEmailMalForme() {
    assertEquals(-4, service.inscription("john-doe.com", "pw", info));
    assertEquals(-4, service.inscription("john@doe", "pw", info));
  }
  
  /**
   * Vérifie qu'on peut se connecter avec les bons identifiants.
   */
  @Test
  void testConnexionOk() {
    service.inscription("john@doe.com", "pw", info);
    assertTrue(service.connexion("john@doe.com", "pw"));
  }
  
  /**
   * Vérifie que la connexion échoue si le mot de passe est incorrect.
   */
  @Test
  void testConnexionMdpIncorrect() {
    service.inscription("john@doe.com", "pw", info);
    assertFalse(service.connexion("john@doe.com", "wrong"));
  }
  
  /**
   * Vérifie que debuterCommande lève une exception si aucun client n'est
   * connecté.
   */
  @Test
  void testDebuterCommandeSansConnexion() {
    assertThrows(NonConnecteException.class, () -> {
      service.debuterCommande();
    });
  }
  
  /**
   * Vérifie qu'on peut créer une commande après connexion.
   *
   * @throws Exception ne peut pas être levée ici
   */
  @Test
  void testDebuterCommandeOk() throws Exception {
    service.inscription("john@doe.com", "pw", info);
    assertTrue(service.connexion("john@doe.com", "pw"));
    
    Commande cmd = service.debuterCommande();
    assertNotNull(cmd);
    assertEquals(1, cmd.getId());
    assertEquals(1, service.getCommandesEncours().size());
  }
  
  /**
   * Vérifie que getPizzas retourne bien l'ensemble des pizzas en vente.
   */
  @Test
  void testGetPizzas() {
    Pizza p = new Pizza("Reine", TypePizza.Viande);
    data.pizzasByName.put("Reine", p);
    
    Set<Pizza> pizzas = service.getPizzas();
    assertEquals(1, pizzas.size());
    assertTrue(pizzas.contains(p));
  }
}
