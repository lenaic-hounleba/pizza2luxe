package pizzas;

// A compléter

// TODO: this class needs:

// -the client name getters
// -the pizza name getters

/**
 * classe qui crée une Evaluation.
 *
 * @author Dorian Fleurquin
 */



public class Evaluation {
  
  

  
  
  
  // we'll use getters to get those attributes
  
  private final GestionClient client; // le client qui laisse l'evaluation
  private final Pizza pizza; // la pizza (duh)
  private int note; // note spans from 0-5
  private String commentaire; // le commentaire laissé par le client avec la
  
  
  
  /**
   * Constructeur d'une évaluation.
   *
   * @param client le client qui laisse l'évaluation
   * @param pizza la pizza concernée
   * @param note la note attribuée (0 à 5)
   * @param commentaire commentaire facultatif (peut être null)
   */
  public Evaluation(GestionClient client, Pizza pizza, int note, String commentaire) {
    
    // we could add error handling here
    
    
    this.client = client;
    this.pizza = pizza;
    setNote(note); // this cleans up a potential out of bounds value
    
    // check null sur le commentaire
    
    if (commentaire != null) {
      this.commentaire = commentaire;
    } else {
      this.commentaire = "";
    }
    
    
    
  }
  
  
  /**
   * Constructeur surchargé d'une évaluation sans commentaire.
   *
   * @param client le client qui laisse l'évaluation
   * @param pizza la pizza concernée
   * @param note la note attribuée (0 à 5)
   */
  public Evaluation(GestionClient client, Pizza pizza, int note) {
    this(client, pizza, note, ""); // appele le constructeur original et insert
                          
  }
  
  /**
   * set la note de l'eval.
   * 
   */
  
  // make sure note is in range
  // correcting the value by getting it to nearest valid rating
  
  
  public void setNote(int note) {
    
 
    
    
    if (note < 0) {
      this.note = 0;
    } else if (note > 5) {
      this.note = 5;
      
    } else {
      this.note = note;
    }
    
  }
  
  public GestionClient getClient() {
    return client;
  }
  
  public Pizza getPizza() {
    return pizza;
  }
  
  public int getNote() {
    return note;
  }
  
  public String getCommentaire() {
    return commentaire;
  }
  
  
  // to string output
  
  
  /**
   * methode qui affiche l'evaluation en tant que string
   * 
   */
  
  
  // make sure note is in range
  // correcting the value by getting it to nearest valid rating
  
  public String toString() {
    
    // we'll need the client name getters for this one
    // when those are done TODO: replace the values in [] brackets
    
    return "Evaluation par [client] de " + pizza + " note:" + note
        + " commentaire:" + commentaire;
    
    
    
  }
  
  
  
}
