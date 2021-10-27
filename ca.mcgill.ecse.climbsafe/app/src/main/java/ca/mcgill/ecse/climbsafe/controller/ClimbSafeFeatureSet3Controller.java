package ca.mcgill.ecse.climbsafe.controller;

import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.User;

/**
 * controller to register guide and update guide
 * 
 * @author Yida Pan
 *
 */
public class ClimbSafeFeatureSet3Controller {
  // reference to ClimbSafe
  private static ClimbSafe climbsafe = ClimbSafeApplication.getClimbSafe();

  /**
   * the method to register guide and check invalid input
   * 
   * @author Yida Pan
   * @param email
   * @param password
   * @param name
   * @param emergencyContact
   * @throws InvalidInputException
   * 
   */
  public static void registerGuide(String email, String password, String name,
      // check invalid input and trow exceptions
      String emergencyContact) throws InvalidInputException {
    checkemail(email, name);
    checkpassword(password);
    checkemergencyContact(emergencyContact);
    checkname(name);
    // add the guide to the climsafe
    try {
      climbsafe.addGuide(email, password, name, emergencyContact);
    } catch (RuntimeException e) {
      throw new InvalidInputException(e.getMessage());
    }
  }

  /**
   * the method to update guide and check invalid input
   * 
   * @param email
   * @param newPassword
   * @param newName
   * @param newEmergencyContact
   * @throws InvalidInputException
   */
  public static void updateGuide(String email, String newPassword, String newName,
      String newEmergencyContact) throws InvalidInputException {
    // check invalid input and throw exceptions
    checkpassword(newPassword);
    checkemergencyContact(newEmergencyContact);
    checkname(newName);
    Guide guide = findGuide(email);
    if (guide != null) {
      guide.setName(newName);
      guide.setPassword(newPassword);
      guide.setEmergencyContact(newEmergencyContact);
    }
  }


  /**
   * The helper method to check if the email is in corret format
   * 
   * @param email
   * @param name
   * @throws InvalidInputException
   */
  private static void checkemail(String email, String name) throws InvalidInputException {
    if (email.equals("")) {
      throw new InvalidInputException("Email cannot be empty");
    } else if (email.equals("admin@nmc.nt")) {
      throw new InvalidInputException("Email cannot be admin@nmc.nt");
    } else if (email.contains(" ")) {
      throw new InvalidInputException("Email must not contain any spaces");
    } else {
      checklinkeduser(email);
      checkvalidemail(email, name);
    }
  }

  /**
   * the helper method to find the guide with its email as input
   * 
   * @author Yida Pan
   * @param email
   * @return
   * @throws InvalidInputException
   */
  private static Guide findGuide(String email) {
    Guide foundGuide = null;
    for (var guide : climbsafe.getGuides()) {
      if (guide.getEmail().equals(email)) {
        foundGuide = guide;
        break;
      }
    }
    return foundGuide;
  }

  /**
   * The helper method to check if the name is exmpty
   * 
   * @param name
   * @throws InvalidInputException if the name is empty
   */
  private static void checkname(String name) throws InvalidInputException {
    if ((name.equals("")) || (name == null)) {
      throw new InvalidInputException("Name cannot be empty");
    }
  }

  /**
   * The helper method to check if the password is empty
   * 
   * @param password
   * @throws InvalidInputException if the password is empty
   */

  private static void checkpassword(String password) throws InvalidInputException {
    if ((password.equals("")) || (password == null)) {
      throw new InvalidInputException("Password cannot be empty");
    }
  }

  /**
   * The helper method to check if the emergencycontact is empty
   * 
   * @param emergencyContact
   * @throws InvalidInputException if the emergencycontact is empty
   */
  private static void checkemergencyContact(String emergencyContact) throws InvalidInputException {
    if ((emergencyContact.equals("")) || (emergencyContact == null)) {
      throw new InvalidInputException("Emergency contact cannot be empty");
    }
  }

  /**
   * The helper method to check if the email is linked to existing guide or member
   * 
   * @param email
   * @throws InvalidInputException if the email is linked to existing guide or member
   */
  private static void checklinkeduser(String email) throws InvalidInputException {
    var user = User.getWithEmail(email);
    if (user != null) {
      if (user.getClass().equals(Guide.class)) {
        throw new InvalidInputException("Email already linked to a guide account");
      } else {
        throw new InvalidInputException("Email already linked to a member account");
      }
    }
  }

  /**
   * The helper method to check if the email is in correct format
   * 
   * @param email
   * @param name
   * @throws InvalidInputException
   */
  private static void checkvalidemail(String email, String name) throws InvalidInputException {
    // check if email has "@email.com" and throw exception if it does not have one
    int firstindex = email.indexOf("@");
    StringBuilder n = new StringBuilder();
    for (int i = firstindex; i <= firstindex + 9; i++) {
      try {
        n.append(email.charAt(i));
        // check if there is index out of bound exception
      } catch (RuntimeException e) {
        throw new InvalidInputException("Invalid email");
      }
    }
    // throw exception
    String EMAIL = n.toString();
    System.out.println(EMAIL);
    if (!EMAIL.equals("@email.com")) {
      throw new InvalidInputException("Invalid email");
    }
    // check if the email has proper name in the start
    int namelength = name.length();
    StringBuilder m = new StringBuilder();
    for (int j = 0; j <= namelength - 1; j++) {
      try {
        m.append(email.charAt(j));
      } catch (RuntimeException e) {
        throw new InvalidInputException("Invalid email");
      }
    }
    String NAME = m.toString();
    if (!NAME.equalsIgnoreCase(name)) {
      throw new InvalidInputException("Invalid email");
    }
  }
}


