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
   * @param info client info form informationsPersonneles
   */
  
  
  public Client(String email, String password, InformationPersonnelle info) {
    

    this.email = email;
    this.password = password;
    this.info = info;
    
    
    
    
   
   
    
    
  }
  
  
  
  
  
}
