package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import pizzas.PizzeriaData;

/**
 * Implémentation de la sauvegarde des données de la pizzeria via la
 * sérialisation Java.
 *
 * <p>Cette classe sauvegarde et recharge l'objet PizzeriaData dans un fichier à la
 * fermeture et à l'ouverture de la fenetre respectivement.</p>
 *
 * @author lenaic-love.hounleba
 */
public class SauvegardePizzeria implements InterSauvegarde {
  
  /** Données de la pizzeria à sauvegarder. */
  private PizzeriaData data;
  
  /**
   * Construit un gestionnaire de sauvegarde.
   *
   * @param data les données de la pizzeria
   */
  public SauvegardePizzeria(PizzeriaData data) {
    this.data = data;
  }
  
  /**
   * Sauvegarde toutes les données de la pizzeria dans le fichier.
   *
   * @param nomFichier le nom du fichier de sauvegarde
   * @throws IOException en cas de problème d'écriture
   */
  @Override
  public void sauvegarderDonnees(String nomFichier) throws IOException {
    try (ObjectOutputStream oos =
        new ObjectOutputStream(new FileOutputStream(nomFichier))) {
      
      oos.writeObject(data);
    }
  }
  
  /**
   * Charge les données de la pizzeria depuis le fichier.
   *
   * @param nomFichier le nom du fichier de sauvegarde
   * @throws IOException en cas de problème de lecture
   */
  @Override
  public void chargerDonnees(String nomFichier) throws IOException {
    try (ObjectInputStream ois =
        new ObjectInputStream(new FileInputStream(nomFichier))) {
      
      data = (PizzeriaData) ois.readObject();
      
    } catch (ClassNotFoundException e) {
      throw new IOException("Erreur lors du chargement des données.", e);
    }
  }
  
  /**
   * Retourne les données actuellement chargées.
   *
   * @return les données de la pizzeria
   */
  public PizzeriaData getData() {
    return data;
  }
}
