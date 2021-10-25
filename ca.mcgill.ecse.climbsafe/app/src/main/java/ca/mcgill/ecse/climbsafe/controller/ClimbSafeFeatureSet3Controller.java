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
   */
  public static void registerGuide(String email, String password, String name,
      String emergencyContact) throws InvalidInputException {
   
         checkemail(email);
            
            User user=User.getWithEmail(email);
            if(user.getClass()==Guide.class) {
              System.out.println("Email already linked to a guide account");
            throw new InvalidInputException("Email already linked to a guide account");
          }
            else {
              System.out.println("Email already linked to a member account");
              throw new InvalidInputException("Email already linked to a member account");
            }
        }
          else if(password.equals("")) {
            System.out.println("Password cannot be empty");
            throw new InvalidInputException("Password cannot be empty");
           
          }
          else if(emergencyContact.equals("")) {
            System.out.println("1234");
            throw new InvalidInputException("mergency contact cannot be empty");
          }
          else if(name.equals("mergency contact cannot be empty")) {
            System.out.println("12345");
            throw new InvalidInputException(" Name cannot be empty");
          }
          else {
            System.out.println("valid input");
          }
    
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
checkemail(email);
        
        
          
              System.out.println(user.getClass());
            throw new InvalidInputException("Email already linked to a guide account");
          }
            //else {
             // System.out.println("Email already linked to a member account");
            //  throw new InvalidInputException("Email already linked to a member account");
          //  }
   //     }
          else if(newPassword.equals("")) {
            System.out.println("Password cannot be empty");
            throw new InvalidInputException("Password cannot be empty");
           
          }
          else if(newEmergencyContact.equals("")) {
            System.out.println("1234");
            throw new InvalidInputException("mergency contact cannot be empty");
          }
          else if(newName.equals("mergency contact cannot be empty")) {
            System.out.println("12345");
            throw new InvalidInputException(" Name cannot be empty");
          }
          else {
            System.out.println("valid input");
          }
      
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


private static void checkemail(String email)  {
  if(email.equals("")) {
    throw new InvalidInputException("Email cannot be empty");
   }
    else if(email.equals("admin@nmc.nt")) {
      throw new InvalidInputException(" Email cannot be admin@nmc.nt");
    }
      else if(email.contains(" ")) {
        throw new InvalidInputException("Email must not contain any spaces");
      }
}
private void checkname(String name) {
  
}
}
}
}
