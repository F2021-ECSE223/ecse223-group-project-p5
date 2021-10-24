package ca.mcgill.ecse.climbsafe.controller;

import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;

/**
 * the controller to register guide and updateguide
 * 
 * @author frankpan
 *
 */
public class ClimbSafeFeatureSet3Controller {
  private static ClimbSafe climbsafe = ClimbSafeApplication.getClimbSafe();

  /**
   * the method to register guide
   * 
   * @author Yida Pan
   * @param email
   * @param password
   * @param name
   * @param emergencyContact
   * @throws InvalidInputException
   */
  public static void registerGuide(String email, String password, String name,
      String emergencyContact) throws InvalidInputException {
    try {
      climbsafe.addGuide(email, password, name, emergencyContact);
    } catch (RuntimeException e) {
      throw new InvalidInputException(e.getMessage());
    }
  }

  /**
   * the method to update guide
   * 
   * @param email
   * @param newPassword
   * @param newName
   * @param newEmergencyContact
   * @throws InvalidInputException
   */
  public static void updateGuide(String email, String newPassword, String newName,
      String newEmergencyContact) throws InvalidInputException {
    Guide guide = findGuide(email);
    if (guide != null) {
      guide.setName(newName);
      guide.setPassword(newPassword);
    }
  }

  /**
   * the helper method to find the guide with its email as input
   * 
   * @author Yida Pan
   * @param email
   * @return
   */
  private static Guide findGuide(String email) {
    Guide foundGuide = null;
    for (var guide : climbsafe.getGuides()) {
      if (guide.getEmail() == email) {
        foundGuide = guide;
        break;
      }
    }
    return foundGuide;
  }

}
