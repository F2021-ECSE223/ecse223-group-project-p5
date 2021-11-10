package ca.mcgill.ecse.climbsafe.controller;


import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.Assignment;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Hotel;
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

    switch(member.getBanStatus()) {
      case Banned:
        throw new InvalidInputException("Cannot cancel the trip due to a ban");
      default:
        break;
    }
    switch(member.getAssignment().getAssignmentStatus()) {
      case Finished:
        throw new InvalidInputException("Cannot cancel a trip which has finished");
      default:
        break;
    }
    //cancel assignment
    member.getAssignment().cancel();
  
  }
/**
 * 
 * @param email
 * @throws InvalidInputException
 */
  public static void finishTrip(String email) throws InvalidInputException {
   
    validate_member(email);
    var user= User.getWithEmail(email);
    var member  = (Member) user;
    
    switch(member.getBanStatus()) {
      case Banned:
        throw new InvalidInputException("Cannot finish the trip due to a ban");
      default:
        break;
    }
    
    switch(member.getAssignment().getAssignmentStatus()) {
      case Assigned:
        throw new InvalidInputException("Cannot finish a trip which has not started");
       
      case Paid:
        throw new InvalidInputException("Cannot finish a trip which has not started");
      case Cancelled:
        throw new InvalidInputException("Cannot finish a trip which has been cancelled");
      default:
        break;
              
    }
    
    //at valid state-> do transition
    member.getAssignment().finish();
      
  }
  
 /**
  * 
  * @param week
  * @throws InvalidInputException
  */
  
  public static void startTripsForWeek(int week) throws InvalidInputException {
    
   // reference to ClimbSafe
    ClimbSafe cs = ClimbSafeApplication.getClimbSafe();
    
    for (Assignment a : cs.getAssignments()) {
      if (a.getStartWeek() == 4) {
        switch(a.getMember().getBanStatus()) {
          case Banned:
            throw new InvalidInputException("Cannot start the trip due to a ban");
          default:
            break;
        }
        switch(a.getAssignmentStatus()) {
          case Cancelled:
            throw new InvalidInputException("Cannot start a trip which has been cancelled");
          case Finished:
            throw new InvalidInputException("Cannot start a trip which has finished");
          default:
            break;
        }
        //valid status
        a.start();
         
      
      }
    }
 
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
    int startWeek = member.getAssignment().getStartWeek();
    int endWeek = member.getAssignment().getEndWeek();
    Guide guide = member.getAssignment().getGuide();
    Hotel hotel = member.getAssignment().getHotel();
    
    //assign assignment
    member.getAssignment().assign(startWeek, endWeek, guide, hotel);
   
    if (code == null || code.equals("") || code.equals(" ")) {
      throw new InvalidInputException("Invalid authorization code");
    }
    
    switch(member.getBanStatus()) {
      case Banned:
        throw new InvalidInputException("Cannot pay for the trip due to a ban");
      default:
        break;
    }
   
    switch(member.getAssignment().getAssignmentStatus()) {
      case Started:
        throw new InvalidInputException("Trip has already been paid for");
       
      case Paid:
        throw new InvalidInputException("Trip has already been paid for");
      
      case Cancelled:
        throw new InvalidInputException("Cannot pay for a trip which has been cancelled");
      
      case Finished:
        throw new InvalidInputException("Cannot pay for a trip which has finished");
      default:
        break;
              
    }
    
    //update state
    member.getAssignment().pay(code);
  
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
    member.ban();
   
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
