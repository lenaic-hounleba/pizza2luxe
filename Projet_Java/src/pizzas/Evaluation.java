package pizzas;

/**
 * Classe qui représente une évaluation laissée par un client sur une pizza.
 *
 * <p>Une évaluation contient un client, une pizza, une note entre 0 et 5 et un
 * commentaire optionnel.</p>
 *
 * @author Dorian Fleurquin
 */
public class Evaluation implements java.io.Serializable {
  
  
  /**
   * Identifiant de sérialisation.
   */
  private static final long serialVersionUID = 1L;
  
  /** Client qui laisse l'évaluation. */
  private final InformationPersonnelle client;
  
  /** Pizza évaluée. */
  private final Pizza pizza;
  
  /** Note attribuée (0 à 5). */
  private final int note;
  
  /** Commentaire associé (jamais null). */
  private final String commentaire;
  
  /**
   * Constructeur d'une évaluation.
   *
   * @param client le client qui laisse l'évaluation
   * @param pizza la pizza concernée
   * @param note la note attribuée (0 à 5)
   * @param commentaire le commentaire (peut être null)
   */
  public Evaluation(InformationPersonnelle client, Pizza pizza, int note,
      String commentaire) {
    this.client = client;
    this.pizza = pizza;
    this.note = corrigerNote(note);
    
    if (commentaire == null) {
      this.commentaire = "";
    } else {
      this.commentaire = commentaire;
    }
  }
  
  /**
   * Constructeur d'une évaluation sans commentaire.
   *
   * @param client le client qui laisse l'évaluation
   * @param pizza la pizza concernée
   * @param note la note attribuée (0 à 5)
   */
  public Evaluation(InformationPersonnelle client, Pizza pizza, int note) {
    this(client, pizza, note, "");
  }
  
  /**
   * Retourne le client qui a laissé l'évaluation.
   *
   * @return le client
   */
  public InformationPersonnelle getClient() {
    return client;
  }
  
  /**
   * Retourne la pizza évaluée.
   *
   * @return la pizza
   */
  public Pizza getPizza() {
    return pizza;
  }
  
  /**
   * Retourne la note attribuée.
   *
   * @return la note (0 à 5)
   */
  public int getNote() {
    return note;
  }
  
  /**
   * Retourne le commentaire.
   *
   * @return le commentaire (jamais null)
   */
  public String getCommentaire() {
    return commentaire;
  }
  
  /**
   * Corrige une note pour la ramener dans l'intervalle [0, 5].
   *
   * @param note la note à corriger
   * @return la note corrigée
   */
  private static int corrigerNote(int note) {
    if (note < 0) {
      return 0;
    }
    if (note > 5) {
      return 5;
    }
    return note;
  }
  
  @Override
  public String toString() {
    return "Evaluation par " + client + " de " + pizza + " note:" + note
        + " commentaire:" + commentaire;
  }
}
