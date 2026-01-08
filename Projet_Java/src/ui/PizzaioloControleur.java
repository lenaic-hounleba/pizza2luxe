package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import pizzas.Commande;
import pizzas.Pizza;
import pizzas.TypePizza;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * Controleur JavaFX de la fenêtre du pizzaïolo.
 *
 * @author Eric Cariou
 */
public class PizzaioloControleur {
  private pizzas.Ingredient selectedIngredient;
  private Pizza selectedPizza;
  
  private final Map<String, Pizza> pizzasByDisplay = new HashMap<>();
  private final Map<String, pizzas.Ingredient> ingredientsByDisplay =
      new HashMap<>();
  
  
  
  private AppContext ctx;
  
  public void setContext(AppContext ctx) {
    this.ctx = ctx;
  }
  
  public void refreshAll() {
    if (choiceBoxTypePizza.getItems().isEmpty()) {
      choiceBoxTypePizza.getItems().setAll("Viande", "Vegetarienne",
          "Regionale");
    }
    if (choiceBoxTypeIngredient.getItems().isEmpty()) {
      choiceBoxTypeIngredient.getItems().setAll("Viande", "Vegetarienne",
          "Regionale");
    }
    
    comboBoxClients.getItems().setAll(ctx.pizzaioloService.ensembleClients()
        .stream().map(Object::toString).toList());
    
  }
  
  // popups
  
  private void info(String msg) {
    javafx.scene.control.Alert a = new javafx.scene.control.Alert(
        javafx.scene.control.Alert.AlertType.INFORMATION);
    a.setHeaderText(null);
    a.setContentText(msg);
    a.showAndWait();
  }
  
  private void error(String msg) {
    javafx.scene.control.Alert a = new javafx.scene.control.Alert(
        javafx.scene.control.Alert.AlertType.ERROR);
    a.setHeaderText(null);
    a.setContentText(msg);
    a.showAndWait();
  }
  
  
  
  @FXML
  private ChoiceBox<String> choiceBoxTypeIngredient;
  
  @FXML
  private ChoiceBox<String> choiceBoxTypePizza;
  
  @FXML
  private ComboBox<String> comboBoxClients;
  
  @FXML
  private TextField entreeBeneficeClient;
  
  @FXML
  private TextField entreeBeneficeCommande;
  
  @FXML
  private TextField entreeBeneficeTotalCommandes;
  
  @FXML
  private TextField entreeBeneficeTotalPizza;
  
  @FXML
  private TextField entreeBeneficeUnitairePizza;
  
  @FXML
  private TextField entreeNbCommandesPizza;
  
  @FXML
  private TextField entreeNbPizzasClient;
  
  @FXML
  private TextField entreeNomIngredient;
  
  @FXML
  private TextField entreeNomPizza;
  
  @FXML
  private TextField entreeNombreTotalCommandes;
  
  @FXML
  private TextField entreePhotoPizza;
  
  @FXML
  private TextField entreePrixIngredient;
  
  @FXML
  private TextField entreePrixMinimalPizza;
  
  @FXML
  private TextField entreePrixVentePizza;
  
  @FXML
  private Label labelListeCommandes;
  
  @FXML
  private Label labelListeIngredients;
  
  @FXML
  private Label labelListePizzas;
  
  @FXML
  private ListView<String> listeCommandes;
  
  @FXML
  private ListView<String> listeIngredients;
  
  @FXML
  private ListView<String> listePizzas;
  
  @FXML
  void actionBoutonAfficherListeTrieePizzas(ActionEvent event) {
    pizzasByDisplay.clear();
    listePizzas.getItems().clear();
    
    var pizzas = ctx.pizzaioloService.classementPizzasParNombreCommandes();
    labelListePizzas.setText("Pizzas triées (" + pizzas.size() + ")");
    
    for (Pizza p : pizzas) {
      String line = p.getNom() + " | " + p.getType() + " | "
          + String.format("%.2f", ctx.pizzaioloService.getPrixPizza(p)) + "€";
      pizzasByDisplay.put(line, p);
      listePizzas.getItems().add(line);
    }
  }
  
  
  
  @FXML
  void actionBoutonAfficherTousIngredients(ActionEvent event) {
    ingredientsByDisplay.clear();
    listeIngredients.getItems().clear();
    
    labelListeIngredients
        .setText("Ingrédients (" + ctx.data.ingredientsByName.size() + ")");
    
    for (pizzas.Ingredient ing : ctx.data.ingredientsByName.values()) {
      String line =
          ing.getNom() + " | " + String.format("%.2f", ing.getPrix()) + "€";
      ingredientsByDisplay.put(line, ing);
      listeIngredients.getItems().add(line);
    }
  }
  
  
  @FXML
  void actionBoutonAfficherToutesPizzas(ActionEvent event) {
    pizzasByDisplay.clear();
    listePizzas.getItems().clear();
    
    Set<Pizza> pizzas = ctx.pizzaioloService.getPizzas();
    labelListePizzas.setText("Toutes les pizzas (" + pizzas.size() + ")");
    
    for (Pizza p : pizzas) {
      String line = p.getNom() + " | " + p.getType() + " | "
          + String.format("%.2f", p.getPrixPizza()) + "€";
      pizzasByDisplay.put(line, p);
      listePizzas.getItems().add(line);
    }
  }
  
  @FXML
  void actionBoutonAjouterIngredientPizza(ActionEvent event) {
    if (selectedPizza == null) {
      error("Sélectionne une pizza.");
      return;
    }
    
    if (selectedIngredient == null) {
      error("Sélectionne un ingrédient.");
      return;
    }
    
    int code = ctx.pizzaioloService.ajouterIngredientPizza(selectedPizza,
        selectedIngredient.getNom());
    
    switch (code) {
      case 0 -> {
        info("Ingrédient ajouté à la pizza.");
        actionListeSelectionPizza(null); // refresh pizza fields
      }
      case -1 -> error("Pizza invalide.");
      case -2 -> error("Ingrédient invalide.");
      case -3 -> error("Ingrédient interdit pour ce type de pizza.");
      default -> error("Erreur inconnue.");
    }
  }
  
  
  @FXML
  void actionBoutonCommandesDejaTraitees(ActionEvent event) {
    var commandes = ctx.pizzaioloService.commandesDejaTraitees();
    
    labelListeCommandes
        .setText("Commandes traitées (" + commandes.size() + ")");
    
    entreeBeneficeTotalCommandes.setText(
        String.format("%.2f", ctx.pizzaioloService.beneficeToutesCommandes()));
  }
  
  
  @FXML
  void actionBoutonCommandesNonTraitees(ActionEvent event) {
    listeCommandes.getItems().clear();
    
    var commandes = ctx.pizzaioloService.commandeNonTraitees();
    labelListeCommandes
        .setText("Commandes non traitées (" + commandes.size() + ")");
    
    for (var c : commandes) {
      listeCommandes.getItems().add(c.toString());
    }
    
    entreeNombreTotalCommandes.setText(String.valueOf(commandes.size()));
  }
  
  
  @FXML
  void actionBoutonCommandesTraiteesClient(ActionEvent event) {
    String clientStr = comboBoxClients.getValue();
    if (clientStr == null) {
      error("Sélectionnez un client.");
      return;
    }
    
    pizzas.InformationPersonnelle client = null;
    for (pizzas.InformationPersonnelle c : ctx.pizzaioloService
        .ensembleClients()) {
      if (c.toString().equals(clientStr)) {
        client = c;
        break;
      }
    }
    
    if (client == null) {
      error("Client invalide.");
      return;
    }
    
    listeCommandes.getItems().clear();
    
    var commandes = ctx.pizzaioloService.commandesTraiteesClient(client);
    
    labelListeCommandes
        .setText("Commandes traitées (" + commandes.size() + ")");
    
    for (pizzas.Commande c : commandes) {
      listeCommandes.getItems().add(c.toString());
    }
  }
  
  
  
  @FXML
  void actionBoutonCreerIngredient(ActionEvent event) {
    String nom = entreeNomIngredient.getText();
    double prix;
    
    try {
      prix = Double.parseDouble(entreePrixIngredient.getText());
    } catch (NumberFormatException e) {
      error("Prix invalide.");
      return;
    }
    
    int code = ctx.pizzaioloService.creerIngredient(nom, prix);
    
    switch (code) {
      case 0 -> {
        info("Ingrédient créé.");
        entreeNomIngredient.clear();
        entreePrixIngredient.clear();
        actionBoutonAfficherTousIngredients(event);
      }
      case -1 -> error("Nom invalide.");
      case -2 -> error("Ingrédient déjà existant.");
      case -3 -> error("Prix invalide (doit être > 0).");
      default -> error("Erreur inconnue.");
    }
  }
  
  
  
  @FXML
  void actionBoutonCreerPizza(ActionEvent event) {
    String nom = entreeNomPizza.getText();
    String typeStr = choiceBoxTypePizza.getValue();
    
    if (nom == null || nom.isBlank()) {
      error("Nom de pizza invalide.");
      return;
    }
    
    if (typeStr == null) {
      error("Choisis un type de pizza.");
      return;
    }
    
    TypePizza type;
    try {
      type = TypePizza.valueOf(typeStr);
    } catch (IllegalArgumentException e) {
      error("Type de pizza invalide.");
      return;
    }
    
    Pizza pizza = ctx.pizzaioloService.creerPizza(nom, type);
    
    if (pizza == null) {
      error("Pizza invalide ou déjà existante.");
      return;
    }
    
    info("Pizza créée : " + pizza.getNom());
    
    entreeNomPizza.clear();
    choiceBoxTypePizza.setValue(null);
    
    actionBoutonAfficherToutesPizzas(event);
  }
  
  
  @FXML
  void actionBoutonInterdireIngredient(ActionEvent event) {
    if (selectedIngredient == null) {
      error("Sélectionnez un ingrédient.");
      return;
    }
    
    String t = choiceBoxTypeIngredient.getValue();
    if (t == null) {
      error("Choisis un type de pizza.");
      return;
    }
    
    boolean ok = ctx.pizzaioloService
        .interdireIngredient(selectedIngredient.getNom(), TypePizza.valueOf(t));
    
    if (ok)
      info("Ingrédient interdit pour ce type.");
    else
      error("Impossible d'interdire l'ingrédient.");
  }
  
  
  @FXML
  void actionBoutonModifierPrixIngredient(ActionEvent event) {
    if (selectedIngredient == null) {
      error("Sélectionnez un ingrédient.");
      return;
    }
    
    double prix;
    try {
      prix = Double.parseDouble(entreePrixIngredient.getText());
    } catch (NumberFormatException e) {
      error("Prix invalide.");
      return;
    }
    
    int code = ctx.pizzaioloService
        .changerPrixIngredient(selectedIngredient.getNom(), prix);
    
    switch (code) {
      case 0 -> {
        info("Prix modifié.");
        actionBoutonAfficherTousIngredients(event);
      }
      case -1 -> error("Nom invalide.");
      case -2 -> error("Prix invalide.");
      case -3 -> error("Ingrédient inexistant.");
    }
  }
  
  
  @FXML
  void actionBoutonModifierPrixPizza(ActionEvent event) {
    if (selectedPizza == null) {
      error("Sélectionnez une pizza.");
      return;
    }
    
    double prix;
    try {
      prix = Double.parseDouble(entreePrixVentePizza.getText());
    } catch (NumberFormatException e) {
      error("Prix invalide.");
      return;
    }
    
    boolean ok = ctx.pizzaioloService.setPrixPizza(selectedPizza, prix);
    
    if (ok) {
      info("Prix modifié.");
      actionListeSelectionPizza(null);
    } else {
      error("Prix inférieur au minimum.");
    }
  }
  
  
  @FXML
  void actionBoutonParcourirPhotoPizza(ActionEvent event) {
    
  }
  
  @FXML
  void actionBoutonSupprimerIngredientPizza(ActionEvent event) {
    if (selectedPizza == null || selectedIngredient == null) {
      error("Sélectionnez une pizza et un ingrédient.");
      return;
    }
    
    int code = ctx.pizzaioloService.retirerIngredientPizza(selectedPizza,
        selectedIngredient.getNom());
    
    switch (code) {
      case 0 -> {
        info("Ingrédient supprimé.");
        actionListeSelectionPizza(null);
      }
      case -1 -> error("Pizza invalide.");
      case -2 -> error("Ingrédient invalide.");
      case -3 -> error("Ingrédient absent de la pizza.");
    }
  }
  
  
  @FXML
  void actionBoutonVerifierValiditeIngredientsPizza(ActionEvent event) {
    if (selectedPizza == null) {
      error("Sélectionne une pizza.");
      return;
    }
    
    Set<String> invalides =
        ctx.pizzaioloService.verifierIngredientsPizza(selectedPizza);
    
    if (invalides == null) {
      error("Pizza invalide.");
    } else if (invalides.isEmpty()) {
      info("Tous les ingrédients sont valides.");
    } else {
      error("Ingrédients interdits : " + String.join(", ", invalides));
    }
  }
  
  
  @FXML
  void actionListeSelectionCommande(MouseEvent event) {
    String key = listeCommandes.getSelectionModel().getSelectedItem();
    if (key == null)
      return;
    
    for (Commande c : ctx.pizzaioloService.commandesDejaTraitees()) {
      if (c.toString().equals(key)) {
        double benef = ctx.pizzaioloService.beneficeCommandes(c);
        entreeBeneficeCommande.setText(String.format("%.2f", benef));
        return;
      }
    }
  }
  
  
  @FXML
  void actionListeSelectionIngredient(MouseEvent event) {
    String key = listeIngredients.getSelectionModel().getSelectedItem();
    if (key == null)
      return;
    
    selectedIngredient = ingredientsByDisplay.get(key);
  }
  
  
  
  @FXML
  void actionListeSelectionPizza(MouseEvent event) {
    String key = listePizzas.getSelectionModel().getSelectedItem();
    if (key == null)
      return;
    
    selectedPizza = pizzasByDisplay.get(key);
    if (selectedPizza == null)
      return;
    
    entreeNomPizza.setText(selectedPizza.getNom());
    choiceBoxTypePizza.setValue(String.valueOf(selectedPizza.getType()));
    
    entreePrixMinimalPizza.setText(String.format("%.2f",
        ctx.pizzaioloService.calculerPrixMinimalPizza(selectedPizza)));
    
    entreePrixVentePizza.setText(String.format("%.2f",
        ctx.pizzaioloService.getPrixPizza(selectedPizza)));
    
    // Stats
    entreeNbCommandesPizza.setText(String
        .valueOf(ctx.pizzaioloService.nombrePizzasCommandees(selectedPizza)));
    
    // Total profit for this pizza
    double benefUnitaire = ctx.pizzaioloService.beneficeParPizza()
        .getOrDefault(selectedPizza, 0.0);
    entreeBeneficeUnitairePizza.setText(String.format("%.2f", benefUnitaire));
    
    double benefTotal = benefUnitaire
        * ctx.pizzaioloService.nombrePizzasCommandees(selectedPizza);
    entreeBeneficeTotalPizza.setText(String.format("%.2f", benefTotal));
  }
  
  
  @FXML
  void actionMenuApropos(ActionEvent event) {
    
  }
  
  @FXML
  void actionMenuSauvegarder(ActionEvent event) throws IOException {
    ctx.save();
    info("Données sauvegardées.");
  }
  
  @FXML
  void actionMenuCharger(ActionEvent event) throws IOException {
    ctx.load();
    refreshAll();
    info("Données chargées.");
  }
  
  @FXML
  void actionMenuQuitter(ActionEvent event) {
    System.exit(0);
  }
  
  @FXML
  void actionSelectionClient(ActionEvent event) {
    var client = comboBoxClients.getValue();
    if (client == null)
      return;
    
    var map = ctx.pizzaioloService.nombrePizzasCommandeesParClient();
    var benef = ctx.pizzaioloService.beneficeParClient();
    
    entreeNbPizzasClient.setText(String.valueOf(map.getOrDefault(client, 0)));
    entreeBeneficeClient
        .setText(String.format("%.2f", benef.getOrDefault(client, 0.0)));
  }
  
  
  
  @FXML
  void initialize() {
    // Mettre ici le code d'initialisation du contenu de la fenêtre
  }
}
