package pizzas;

/**
 * Classe représentant un client de la pizzeria.
 *
 * Un client est défini par une adresse email, un mot de passe et des
 * informations personnelles.
 *
 * @author Dorian Fleurquin
 */
public class Client {

  /** Informations personnelles du client. */
  private final InformationPersonnelle info;

  /** Adresse email du client. */
  private final String email;

  /** Mot de passe du client. */
  private final String password;

  /**
   * Constructeur d'un client.
   *
   * @param email adresse email du client
   * @param password mot de passe du client
   * @param nom nom du client
   * @param prenom prénom du client
   * @param adresse adresse du client
   * @param age âge du client
   */
  public Client(String email, String password, String nom, String prenom,
                String adresse, int age) {
    this.email = email;
    this.password = password;
    this.info = new InformationPersonnelle(nom, prenom, adresse, age);
  }

  /**
   * Retourne l'adresse email du client.
   *
   * @return l'adresse email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Retourne les informations personnelles du client.
   *
   * @return les informations personnelles
   */
  public InformationPersonnelle getInformationPersonnelle() {
    return info;
  }

  /**
   * Vérifie si le mot de passe correspond à celui du client.
   *
   * @param mdp le mot de passe à vérifier
   * @return true si le mot de passe est correct, false sinon
   */
  public boolean verifierMotDePasse(String mdp) {
    return password != null && password.equals(mdp);
  }
}
