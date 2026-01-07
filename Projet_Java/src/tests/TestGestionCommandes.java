package tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import pizzas.Client;
import pizzas.Commande;
import pizzas.CommandeException;
import pizzas.EtatCommande;
import pizzas.GestionCommandes;

/**
 * Tests unitaires de la classe {@link pizzas.GestionCommandes}.
 *
 * @author lenaic-love.hounleba
 */
class TestGestionCommandes {
  
  private GestionCommandes gestion;
  private Client client;
  
  @BeforeEach
  void setUp() {
    gestion = new GestionCommandes();
    client =
        new Client("test@gmail.com", "1234", "Test", "Client", "Rue test", 22);
  }
  
  /**
   * Test de la création d'une commande.
   */
  @Test
  void testCreerCommande() {
    Commande cmd = gestion.creerCommande(client);
    
    assertEquals(client, cmd.getClient());
    assertEquals(EtatCommande.creee, cmd.getEtat());
  }
  
  /**
   * Test de l'annulation d'une commande en cours.
   */
  @Test
  void testAnnulerCommande() {
    Commande cmd = gestion.creerCommande(client);
    gestion.annulerCommande(cmd);
    
    assertEquals(0, gestion.getCommandesEnCours(client).size());
  }
  
  /**
   * Vérifie qu'on ne peut pas annuler une commande validée.
   */
  @Test
  void testAnnulationCommandeValidee() {
    Commande cmd = gestion.creerCommande(client);
    cmd.valider();
    
    assertThrows(CommandeException.class, () -> {
      gestion.annulerCommande(cmd);
    });
  }
  
  /**
   * Test de la récupération des commandes en cours d'un client.
   */
  @Test
  void testGetCommandesEnCours() {
    gestion.creerCommande(client);
    gestion.creerCommande(client);
    
    assertEquals(2, gestion.getCommandesEnCours(client).size());
  }
  
  /**
   * Test du traitement des commandes validées.
   */
  @Test
  void testTraiterCommandes() {
    Commande cmd = gestion.creerCommande(client);
    cmd.valider();
    
    gestion.traiterCommandes();
    
    assertEquals(EtatCommande.traitee, cmd.getEtat());
  }
}
