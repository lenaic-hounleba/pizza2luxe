package pizzas;

/**
 * Exception levée quand il y a un problème avec le compte d'un utilisateur ou
 * sa connexion.
 *
 * @author Eric Cariou
 */
public class NonConnecteException extends Exception {
  
  /**
   * Identifiant de sérialisation.
   */
  private static final long serialVersionUID = -2876441299971092712L;
  
  /**
   * Crée une exception indiquant qu'aucun utilisateur n'est connecté.
   */
  public NonConnecteException() {
    super("Aucun utilisateur n'est actuellement connecté.");
  }
  
  /**
   * Crée une exception avec un message personnalisé.
   *
   * @param message message décrivant le problème
   */
  public NonConnecteException(String message) {
    super(message);
  }
  
}
