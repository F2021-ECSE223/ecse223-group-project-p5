/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package ca.mcgill.ecse.climbsafe.application;

import ca.mcgill.ecse.climbsafe.javafx.ClimbSafeView;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.persistence.ClimbSafePersistence;
import javafx.application.Application;

public class ClimbSafeApplication {
  private static ClimbSafe climbSafe;

  /**
   * Entry point of the program
   * 
   * @param args
   * @author Jimmy Sheng
   */
  public static void main(String[] args) {
    // TODO: for testing, use demo data file
    ClimbSafePersistence.setFilename("ClimbSafeDemo.data");
    // start UI
    Application.launch(ClimbSafeView.class, args);
  }

  public static ClimbSafe getClimbSafe() {
    if (climbSafe == null) {
      // load model from persistence
      climbSafe = ClimbSafePersistence.load();
    }
    return climbSafe;
  }
}
