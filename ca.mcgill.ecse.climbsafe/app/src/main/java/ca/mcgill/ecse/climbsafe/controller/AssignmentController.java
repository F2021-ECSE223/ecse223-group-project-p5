package ca.mcgill.ecse.climbsafe.controller;


import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.model.User;

public class AssignmentController {
  
 
/**
 * 
 * @param email
 * @throws InvalidInputException
 */
  public static void cancelTrip(String email) throws InvalidInputException {
    
    validate_member(email);
    var user= User.getWithEmail(email);
    var member  = (Member) user;
  
  throw new InvalidInputException("");
  
  }
/**
 * 
 * @param email
 * @throws InvalidInputException
 */
  public static void finishTrip(String email) throws InvalidInputException {
    
  throw new InvalidInputException("");
  
  }
  
 /**
  * 
  * @param week
  * @throws InvalidInputException
  */
  
  public static void startTripsForWeek(int week) throws InvalidInputException {
    
  throw new InvalidInputException("");
  
  }
  
 /**
  * 
  * @param email
  * @param code
  * @throws InvalidInputException
  */
  
  public static void confirmPayment(String email, String code) throws InvalidInputException {
    
    validate_member(email);
    var user= User.getWithEmail(email);
    var member  = (Member) user;
    
    //update state
    member.getAssignment().doAssign();
       
   //------------------------------------------------------------------------------------------- 
    
    if (code == null || code.equals("") || code.equals(" ")) {
      throw new InvalidInputException("Invalid authorization code");
    }
   
    //update state
    member.Pay(code);
  
  }
  /**
   * 
   * @param email
   * @throws InvalidInputException
   */
  public static void toggleBan(String email) throws InvalidInputException {
    
    validate_member(email);
    var user= User.getWithEmail(email);
    var member  = (Member) user;
    
  //update state
    member.toggleBan();
   
  }
  /**
   * Helper method for validating input email
   * 
   * @param discount
   * @throws InvalidInputException
   */
  private static void validate_member(String email) throws InvalidInputException {
   
    var user= User.getWithEmail(email);
    if (user == null) {
      throw new InvalidInputException("Member with email address "+email+" does not exist");
    } else if (!(user instanceof Member)) {
      throw new InvalidInputException("Member with email address "+email+" does not exist");
    }
   
  }

  
  
  
}
