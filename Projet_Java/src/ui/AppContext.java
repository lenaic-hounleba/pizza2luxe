package ui;

import java.io.IOException;
import io.SauvegardePizzeria;

import pizzas.GestionClient;
import pizzas.GestionPizzaiolo;
import pizzas.PizzeriaData;


/**
 * Classe représentant le contexte global de l'application.
 * <p>
 * Elle centralise l'accès aux données de la pizzeria ainsi qu'aux services
 * métiers associés (gestion des clients et des pizzaiolos). Elle est également
 * responsable de la sauvegarde et du chargement des données à partir d'un
 * fichier unique.
 * </p>
 *
 * <p>
 * Cette classe est conçue pour être utilisée comme point d'entrée partagé entre
 * les différentes parties de l'interface utilisateur.
 * </p>
 * 
 * 
 * @author dorianfleurquin
 */

public final class AppContext {
  
  private static final String SAVE_FILE = "pizzeria.dat";
  
  public PizzeriaData data;
  public GestionClient clientService;
  public GestionPizzaiolo pizzaioloService;
  private final SauvegardePizzeria sauvegarde;
  
  public AppContext() {
    this.data = new PizzeriaData();
    this.clientService = new GestionClient(data);
    this.pizzaioloService = new GestionPizzaiolo(data);
    this.sauvegarde = new SauvegardePizzeria(data);
  }
  
  /** Sauvegarde les données de l'interface dans le fichier unique */
  public void save() throws IOException {
    sauvegarde.sauvegarderDonnees(SAVE_FILE);
  }
  
  /** Recharge depuis le fichier unique */
  public void load() throws IOException {
    sauvegarde.chargerDonnees(SAVE_FILE);
    
   
    this.data = sauvegarde.getData();
    this.clientService = new GestionClient(data);
    this.pizzaioloService = new GestionPizzaiolo(data);
  }
}
