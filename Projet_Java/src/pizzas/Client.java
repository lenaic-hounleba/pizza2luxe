package pizzas;


/**
 * class handles client info.
 *
 * @author Dorian Fleurquin
 */

public class Client {
  
  InformationPersonnelle info;
  String email;
  String password;
  
  
  
  /**
   * Constructeur d'un client.
   *
   * @param email client email
   * @param password client password
   * @param nom nom client
   * @param prenom prenom client
   * @param adresse adresse client
   * @param age age client
   */
  
  
  public Client(String email, String password, String nom, String prenom,
      String adresse, int age) {
    
    
    this.email = email;
    this.password = password;
    this.info = setClientInfo(nom, prenom, adresse, age);
    
    
    
  }
  
  
  // gotta guard the age to make sure it's >= 10
  //TODO
  
  
  /**
   * method handles info aquisition to build info object.
   * @param email client email
   * @param password client password
   * @param nom nom client
   * @param prenom prenom client
   * @param adresse adresse client
   * @param age age client
   */
  
  
  
  private InformationPersonnelle setClientInfo(String nom, String prenom,
      String adresse, int age) {
    
    InformationPersonnelle info = new InformationPersonnelle(nom, prenom, adresse, age);
    
    return info;
  }
  
  
  
}
