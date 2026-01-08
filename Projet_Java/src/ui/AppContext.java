package ui;

import io.SauvegardePizzeria;
import pizzas.GestionClient;
import pizzas.GestionPizzaiolo;
import pizzas.PizzeriaData;

import java.io.IOException;

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
  
  /** Sauvegarde dans le fichier unique */
  public void save() throws IOException {
    sauvegarde.sauvegarderDonnees(SAVE_FILE);
  }
  
  /** Recharge depuis le fichier unique */
  public void load() throws IOException {
    sauvegarde.chargerDonnees(SAVE_FILE);
    
    // IMPORTANT: data has changed we must reset the service classes
    this.data = sauvegarde.getData();
    this.clientService = new GestionClient(data);
    this.pizzaioloService = new GestionPizzaiolo(data);
  }
}
