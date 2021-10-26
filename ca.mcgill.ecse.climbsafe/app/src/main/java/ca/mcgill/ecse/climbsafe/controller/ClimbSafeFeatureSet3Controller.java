package ca.mcgill.ecse.climbsafe.controller;

import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.User;

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
   * 
   */
  

  public static void registerGuide(String email, String password, String name,
      String emergencyContact) throws InvalidInputException {
   
         checkemail(email,name);
         checkpassword(password);
         checkemergencyContact(emergencyContact);
         checkname(name);
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
   // checkemail(email,newName);
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
   * the helper method to find the guide with its email as input
   * 
   * @author Yida Pan
   * @param email
   * @return
   * @throws InvalidInputException 
   */
  private static  void checkemail(String email,String name) throws InvalidInputException  {
    if(email.equals("")) {
      throw new InvalidInputException("Email cannot be empty");
     }
      else if(email.equals("admin@nmc.nt")) {
        throw new InvalidInputException("Email cannot be admin@nmc.nt");
      }
        else if(email.contains(" ")) {
          throw new InvalidInputException("Email must not contain any spaces");
        }
        else {
          
          checklinkeduser(email);
          checkvalidemail(email,name);
        }
  }
  
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
  private static void checkname(String name) throws InvalidInputException {
    if((name.equals(""))||(name==null)){
      throw new InvalidInputException("Name cannot be empty");
    }
  }
  private static void checkpassword(String password) throws InvalidInputException {
    if((password.equals(""))||(password==null)){
      throw new InvalidInputException("Password cannot be empty");
  }
}
  private static void checkemergencyContact(String emergencyContact) throws InvalidInputException {
    if((emergencyContact.equals(""))||(emergencyContact==null)){
      throw new InvalidInputException("Emergency contact cannot be empty");
  }
}
  private static void checklinkeduser(String email) throws InvalidInputException {
    var user=User.getWithEmail(email);
    if(user!=null) {
      if(user.getClass().equals(Guide.class)) {
        throw new InvalidInputException("Email already linked to a guide account");
      }
        else {
        throw new InvalidInputException("Email already linked to a member account");    
      }
    }
  }
  private static void checkvalidemail(String email,String name) throws InvalidInputException {
    int firstindex=email.indexOf("@");
    StringBuilder n=new StringBuilder();
    for(int i=firstindex;i<=firstindex+9;i++) {
      try{n.append(email.charAt(i));
    }
      catch(RuntimeException e ){
        throw new InvalidInputException("Invalid email");
      }
    }
    String EMAIL=n.toString();
    System.out.println(EMAIL);
    if(!EMAIL.equals("@email.com")) {
    throw new InvalidInputException("Invalid email");
    }
      
    int namelength=name.length();
    StringBuilder m=new StringBuilder();
    for(int j=0;j<=namelength-1;j++) {
      try{m.append(email.charAt(j));
    }
      catch(RuntimeException e){
        throw new InvalidInputException("Invalid email");
      } 
    }
    String NAME=m.toString();
    if (!NAME.equalsIgnoreCase(name)) {
      throw new InvalidInputException("Invalid email");
    
  }
  }
  }
  




