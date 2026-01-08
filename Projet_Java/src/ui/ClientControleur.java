package ui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import pizzas.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.List;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;



/**
 * Controleur JavaFX de la fenêtre du client.
 *
 * @author Eric Cariou
 */
public class ClientControleur {
  
  private final Map<String, Pizza> pizzasByDisplay = new HashMap<>();
  private Pizza selectedPizza;
  private final Map<String, Commande> commandesByDisplay = new HashMap<>();
  private Commande commandeEnCours;
  private Commande selectedCommande;
  private final Map<String, Evaluation> evaluationsByDisplay = new HashMap<>();
  
  
  
  private AppContext ctx;
  
  public void setContext(AppContext ctx) {
    this.ctx = ctx;
  }
  
  public void refreshAll() {
    // minimal for now: just initialize ChoiceBoxes
    if (choiceBoxNoteEvaluation.getItems().isEmpty()) {
      choiceBoxNoteEvaluation.getItems().setAll(0, 1, 2, 3, 4, 5);
    }
    if (choiceBoxFiltreType.getItems().isEmpty()) {
      choiceBoxFiltreType.getItems().setAll("Viande", "Vegetarienne",
          "Regionale");
    }
  }
  
  private void refreshCommandes(List<Commande> commandes, String label) {
    commandesByDisplay.clear();
    listeCommandes.getItems().clear();
    labelListeCommandes.setText(label + " (" + commandes.size() + ")");
    
    for (Commande c : commandes) {
      String line = "Commande #" + c.getId() + " | " + c.getStatut() + " | "
          + String.format("%.2f", c.calculerMontantTotal()) + "€";
      
      commandesByDisplay.put(line, c);
      listeCommandes.getItems().add(line);
    }
  }
  
  private void info(String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
  
  private void error(String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Erreur");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
  
  
  
  @FXML
  private ChoiceBox<String> choiceBoxFiltreType;
  
  @FXML
  private TextField entreeAdresseClient;
  
  @FXML
  private TextField entreeAgeClient;
  
  @FXML
  private TextField entreeAuteurEvaluation;
  
  @FXML
  private TextField entreeEmailClient;
  
  @FXML
  private TextField entreeEvaluationMoyenneEvaluations;
  
  @FXML
  private TextField entreeFiltreContientIngredient;
  
  @FXML
  private TextField entreeFiltrePrixMax;
  
  @FXML
  private TextField entreeMotDePasseClient;
  
  @FXML
  private TextField entreeNomClient;
  
  @FXML
  private TextField entreeNomPizza;
  
  @FXML
  private TextField entreeNomPizzaEvaluee;
  
  @FXML
  private TextField entreeNoteMoyennePizza;
  
  @FXML
  private TextField entreePrenomClient;
  
  @FXML
  private TextField entreePrixPizza;
  
  @FXML
  private TextField entreeTypePizza;
  
  @FXML
  private Label labelListeCommandes;
  
  @FXML
  private Label labelListePizzas;
  
  @FXML
  private ListView<String> listeCommandes;
  
  @FXML
  private ListView<String> listeEvaluations;
  
  @FXML
  private ListView<String> listeIngredients;
  
  @FXML
  private ChoiceBox<Integer> choiceBoxNoteEvaluation;
  
  @FXML
  private ListView<String> listePizzas;
  
  @FXML
  private StackPane panePhotoPizza;
  
  @FXML
  private TextArea texteCommentaireEvaluation;
  
  @FXML
  void actionBoutonAfficherCommandesEnCours(ActionEvent event) {
    try {
      refreshCommandes(ctx.clientService.getCommandesEncours(),
          "Commandes en cours");
    } catch (NonConnecteException e) {
      error(e.getMessage());
    }
  }
  
  
  @FXML
  void actionBoutonAfficherCommandesTtraitees(ActionEvent event) {
    try {
      refreshCommandes(ctx.clientService.getCommandePassees(),
          "Commandes passées");
    } catch (NonConnecteException e) {
      error(e.getMessage());
    }
  }
  
  
  @FXML
  void actionBoutonAfficherEvaluationPizzas(ActionEvent event) {
    if (selectedPizza == null) {
      error("Sélectionne une pizza.");
      return;
    }
    
    listeEvaluations.getItems().clear();
    entreeNomPizzaEvaluee.setText(selectedPizza.getNom());
    
    Set<Evaluation> evals =
        ctx.clientService.getEvaluationsPizza(selectedPizza);
    
    double avg = ctx.clientService.getNoteMoyenne(selectedPizza);
    if (avg >= 0) {
      entreeEvaluationMoyenneEvaluations.setText(String.format("%.2f", avg));
    } else {
      entreeEvaluationMoyenneEvaluations.setText("");
    }
    
    evaluationsByDisplay.clear();
    
    for (Evaluation e : evals) {
      String line = e.getClient().getPrenom() + " " + e.getClient().getNom()
          + " : " + e.getNote() + "/5";
      
      evaluationsByDisplay.put(line, e);
      listeEvaluations.getItems().add(line);
    }
    
    
    
  }
  
  
  @FXML
  void actionBoutonAfficherToutesPizzas(ActionEvent event) {
    pizzasByDisplay.clear();
    listePizzas.getItems().clear();
    
    Set<Pizza> pizzas = ctx.clientService.getPizzas();
    labelListePizzas.setText("Toutes les pizzas (" + pizzas.size() + ")");
    
    for (Pizza p : pizzas) {
      String line = p.getNom() + " | " + p.getType() + " | "
          + String.format("%.2f", p.getPrixPizza()) + "€";
      
      pizzasByDisplay.put(line, p);
      listePizzas.getItems().add(line);
    }
  }
  
  private void afficherPizzasFiltrees() {
    pizzasByDisplay.clear();
    listePizzas.getItems().clear();
    
    Set<Pizza> pizzas = ctx.clientService.selectionPizzaFiltres();
    
    labelListePizzas.setText("Pizzas filtrées (" + pizzas.size() + ")");
    
    for (Pizza p : pizzas) {
      String line = p.getNom() + " | " + p.getType() + " | "
          + String.format("%.2f", p.getPrixPizza()) + "€";
      
      pizzasByDisplay.put(line, p);
      listePizzas.getItems().add(line);
    }
  }
  
  
  
  @FXML
  void actionBoutonAjouterMonEvaluation(ActionEvent event) {
    if (selectedPizza == null) {
      error("Sélectionne une pizza.");
      return;
    }
    
    Integer note = choiceBoxNoteEvaluation.getValue();
    if (note == null) {
      error("Choisis une note.");
      return;
    }
    
    try {
      boolean ok = ctx.clientService.ajouterEvaluation(selectedPizza, note,
          texteCommentaireEvaluation.getText());
      
      if (ok) {
        info("Évaluation ajoutée.");
        actionBoutonAfficherEvaluationPizzas(event);
      } else {
        error("Évaluation refusée.");
      }
      
    } catch (NonConnecteException | CommandeException e) {
      error(e.getMessage());
    }
  }
  
  
  @FXML
  void actionBoutonAjouterPizzaSelectionneeCommande(ActionEvent event) {
    if (selectedPizza == null) {
      error("Sélectionne une pizza.");
      return;
    }
    if (commandeEnCours == null) {
      error("Aucune commande en cours.");
      return;
    }
    
    try {
      ctx.clientService.ajouterPizza(selectedPizza, 1, commandeEnCours);
      info("Pizza ajoutée à la commande.");
      actionBoutonAfficherCommandesEnCours(event);
    } catch (NonConnecteException | CommandeException e) {
      error(e.getMessage());
    }
  }
  
  
  @FXML
  void actionBoutonAppliquerFiltreContientngredient(ActionEvent event) {
    String ing = entreeFiltreContientIngredient.getText();
    if (ing.isEmpty())
      return;
    
    ctx.clientService.ajouterFiltre(ing);
    afficherPizzasFiltrees();
  }
  
  
  @FXML
  void actionBoutonAppliquerFiltrePrixMax(ActionEvent event) {
    try {
      double prix = Double.parseDouble(entreeFiltrePrixMax.getText());
      
      ctx.clientService.ajouterFiltre(prix);
      afficherPizzasFiltrees();
      
    } catch (NumberFormatException e) {
      error("Prix invalide.");
    }
  }
  
  
  @FXML
  void actionBoutonAppliquerFiltreType(ActionEvent event) {
    String t = choiceBoxFiltreType.getValue();
    if (t == null)
      return;
    
    ctx.clientService.ajouterFiltre(TypePizza.valueOf(t));
    afficherPizzasFiltrees();
  }
  
  
  @FXML
  void actionBoutonConnexion(ActionEvent event) {
    String email = entreeEmailClient.getText();
    String mdp = entreeMotDePasseClient.getText();
    
    if (email.isEmpty() || mdp.isEmpty()) {
      error("Email ou mot de passe manquant.");
      return;
    }
    
    boolean ok = ctx.clientService.connexion(email, mdp);
    if (ok) {
      info("Connexion réussie.");
      commandeEnCours = null;
      actionBoutonAfficherToutesPizzas(event);
    } else {
      error("Email ou mot de passe incorrect.");
    }
  }
  
  
  @FXML
  void actionBoutonCreerNouvelleCommande(ActionEvent event) {
    try {
      commandeEnCours = ctx.clientService.debuterCommande();
      info("Nouvelle commande créée (id " + commandeEnCours.getId() + ").");
      actionBoutonAfficherCommandesEnCours(event);
    } catch (NonConnecteException e) {
      error(e.getMessage());
    }
  }
  
  
  @FXML
  void actionBoutonDeconnexion(ActionEvent event) {
    try {
      ctx.clientService.deconnexion();
      commandeEnCours = null;
      selectedPizza = null;
      selectedCommande = null;
      listeCommandes.getItems().clear();
      info("Déconnecté.");
    } catch (NonConnecteException e) {
      error(e.getMessage());
    }
  }
  
  
  @FXML
  void actionBoutonInscription(ActionEvent event) {
    try {
      String nom = entreeNomClient.getText();
      String prenom = entreePrenomClient.getText();
      String adresse = entreeAdresseClient.getText();
      int age = Integer.parseInt(entreeAgeClient.getText());
      String email = entreeEmailClient.getText();
      String mdp = entreeMotDePasseClient.getText();
      
      InformationPersonnelle infoPers =
          new InformationPersonnelle(nom, prenom, adresse, age);
      
      int code = ctx.clientService.inscription(email, mdp, infoPers);
      
      switch (code) {
        case 0 -> info("Inscription réussie.");
        case -1 -> error("Email déjà utilisé.");
        case -2 -> error("Email ou mot de passe invalide.");
        case -3 -> error("Informations personnelles invalides.");
        case -4 -> error("Email mal formé.");
      }
      
    } catch (NumberFormatException e) {
      error("Âge invalide.");
    }
  }
  
  
  @FXML
  void actionBoutonReinitialiserFiltre(ActionEvent event) {
    ctx.clientService.supprimerFiltres();
    actionBoutonAfficherToutesPizzas(event);
  }
  
  
  @FXML
  void actionBoutonValiderCommandeEnCours(ActionEvent event) {
    if (commandeEnCours == null) {
      error("Aucune commande en cours.");
      return;
    }
    
    try {
      ctx.clientService.validerCommande(commandeEnCours);
      info("Commande validée.");
      commandeEnCours = null;
      actionBoutonAfficherCommandesTtraitees(event);
    } catch (NonConnecteException | CommandeException e) {
      error(e.getMessage());
    }
  }
  
  
  @FXML
  void actionSelectionCommnade(MouseEvent event) {
    String key = listeCommandes.getSelectionModel().getSelectedItem();
    if (key != null) {
      selectedCommande = commandesByDisplay.get(key);
    }
  }
  
  
  @FXML
  void actionSelectionEvaluation(MouseEvent event) {
    String key = listeEvaluations.getSelectionModel().getSelectedItem();
    if (key == null)
      return;
    
    Evaluation e = evaluationsByDisplay.get(key);
    if (e == null)
      return;
    
    entreeAuteurEvaluation
        .setText(e.getClient().getPrenom() + " " + e.getClient().getNom());
    texteCommentaireEvaluation.setText(e.getCommentaire());
    choiceBoxNoteEvaluation.setValue(e.getNote());
  }
  
  
  @FXML
  void actionSelectionPizza(MouseEvent event) {
    String key = listePizzas.getSelectionModel().getSelectedItem();
    if (key == null)
      return;
    
    selectedPizza = pizzasByDisplay.get(key);
    if (selectedPizza == null)
      return;
    
    entreeNomPizza.setText(selectedPizza.getNom());
    entreePrixPizza
        .setText(String.format("%.2f", selectedPizza.getPrixPizza()));
    entreeTypePizza.setText(String.valueOf(selectedPizza.getType()));
    
    // ingredients
    listeIngredients.getItems().clear();
    for (Ingredient ing : selectedPizza.getIngredients()) {
      if (ing != null)
        listeIngredients.getItems().add(ing.getNom());
    }
    
    // average note
    double avg = ctx.clientService.getNoteMoyenne(selectedPizza);
    if (avg >= 0) {
      entreeNoteMoyennePizza.setText(String.format("%.2f", avg));
    } else {
      entreeNoteMoyennePizza.setText("");
    }
  }
  
  
  @FXML
  void initialize() {
    // Mettre ici le code d'initialisation du contenu de la fenêtre
  }
  
}
