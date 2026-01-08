package ui;

import java.io.IOException;

import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Classe exécutable qui lance l'interface graphique de l'application.
 *
 * @author Eric Cariou
 */
public final class MainInterface extends Application {
  
  
  private AppContext ctx;
  private ClientControleur clientController;
  private PizzaioloControleur pizzaioloController;
  
  
  /**
   * Affiche la fenêtre du client pour commander les pizzas.
   */
  public void startFenetreClient() {
    try {
      URL url = getClass().getResource("client.fxml");
      FXMLLoader loader = new FXMLLoader(url);
      VBox root = loader.load();
      
      // injecte (partage) le ctx avec le controlleur
      clientController = loader.getController();
      clientController.setContext(ctx);
      clientController.refreshAll();
      
      Scene scene = new Scene(root, 1210, 620);
      
      Stage stage = new Stage();
      stage.setResizable(true);
      stage.setTitle("Commandes de pizzas");
      stage.setScene(scene);
      stage.show();
      
    } catch (IOException e) {
      System.err.println("Erreur au chargement de la fenêtre du client : " + e);
    }
  }
  
  /**
   * Affiche la fenêtre de gestion des pizzas pour le pizzaïolo.
   *
   * @param primaryStage le paramètre passé par JavaFX pour la fenêtre
   *        principale
   */
  public void startFenetrePizzaiolo(Stage primaryStage) {
    try {
      URL url = getClass().getResource("pizzaiolo.fxml");
      FXMLLoader loader = new FXMLLoader(url);
      VBox root = loader.load();
      
      pizzaioloController = loader.getController();
      pizzaioloController.setContext(ctx);
      pizzaioloController.refreshAll();
      
      Scene scene = new Scene(root, 985, 630);
      
      primaryStage.setScene(scene);
      primaryStage.setResizable(true);
      primaryStage.setTitle("Gestion des pizzas");
      primaryStage.show();
      
    } catch (IOException e) {
      System.err
          .println("Erreur au chargement de la fenêtre du pizzaïolo : " + e);
    }
  }
  
  
  @Override
  public void start(Stage primaryStage) {
    
    // Rajouter ici du code si besoin
    ctx = new AppContext(); // init du context data
    
    // Lancement des 2 fenêtres de l'application
    this.startFenetrePizzaiolo(primaryStage);
    this.startFenetreClient();
    
    // Rajouter ici du code si besoin
  }
  
  /**
   * Méthode principale qui lance l'application en affichant les deux fenêtres.
   *
   * @param args non utilisés ici
   */
  public static void main(String[] args) {
    launch(args);
  }
}
