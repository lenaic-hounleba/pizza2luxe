package pizzas;



/**
 * Exception levée lorsqu'il y a un problème avec la commande d'un client.
 * 
 * <p>
 * Cette exception est utilisée pour signaler des situations telles que :
 * <ul>
 * <li>ajout de pizzas à une commande déjà validée</li>
 * <li>modification de quantités dans une commande non modifiable</li>
 * <li>Validation ou annulation incorrecte d'une commande</li>
 * </ul>
 * </p>
 *
 * @author lenaic-love.hounleba
 */

public class CommandeException extends RuntimeException {
  
  /**
   * Identifiant de sérialisation.
   */
  private static final long serialVersionUID = -2876441299971092712L;
  
  /**
   * Crée une nouvelle exception avec cr message par défaut.
   */
  public CommandeException() {
    super("Problème rencontré avec la commande.");
  }
  
  /**
   * Crée une nouvelle exception avec le message spécifié.
   *
   * @param message le message qui décrit le problème
   */
  public CommandeException(String message) {
    super(message);
  }
  
  
  /**
   * Crée une nouvelle exception avec le message et la cause spécifiés.
   *
   * @param message le message décrivant le problème
   * @param cause l'exception originale ayant causé cette exception
   */
  public CommandeException(String message, Throwable cause) {
    super(message, cause);
  }
  
  
  /**
   * crée une nouvelle exception avec la cause spécifiée.
   *
   * @param cause l'exception originale ayant causé cette exception
   */
  public CommandeException(Throwable cause) {
    super(cause);
  }
}
