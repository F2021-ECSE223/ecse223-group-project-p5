package ca.mcgill.ecse.climbsafe.controller;


import java.util.List;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.Assignment;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Hotel;
import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.model.User;
import ca.mcgill.ecse.climbsafe.persistence.ClimbSafePersistence;

public class AssignmentController {

  /**
   * Initialization of the assignment This method initialize the assignment in the system and it
   * follows the same procedure stated in the document
   * 
   * @author Yida Pan
   * @param void
   * @throws Exception
   * 
   */
  public static void initiateAssignment() throws Exception {


    // Get Climbsafe
    ClimbSafe climbsafe = ClimbSafeApplication.getClimbSafe();
    int numberofweeks = climbsafe.getNrWeeks();
    // Get members and guides
    List<Member> memberlist = climbsafe.getMembers();
    List<Guide> guidelist = climbsafe.getGuides();

    for (int j = 0; j <= guidelist.size() - 1; j++) {


      Guide currentguide = guidelist.get(j);
      int leftweeks = numberofweeks;
      // Go throgh each member to assign the guide
      for (int i = 0; i <= memberlist.size() - 1; i++) {

        Member temp = memberlist.get(i);

        // go to the next member if member already has assginment
        if (temp.hasAssignment() == true) {
          continue;
        }
        // the member does not have assignment
        else {
          // member does not require guide
          if (temp.isGuideRequired() == false) {
            // create a new assignment
            int endweek = temp.getNrWeeks();
            Assignment tempassignment = new Assignment(1, endweek, temp, climbsafe);
            tempassignment.assign(1, endweek, null, null);
            climbsafe.addAssignment(tempassignment);
            
            // persistence save
            try {
              ClimbSafePersistence.save(climbsafe);
            } catch (RuntimeException e) {
              throw new Exception(e.getMessage());
            }

          }
          // the member requries a guide
          else {
            // the member can be assigned to the current guide
            if (leftweeks >= temp.getNrWeeks()) {
              // there is no member assigned to the current guide
              if (leftweeks == numberofweeks) {

                int endweek = temp.getNrWeeks();
                Assignment tempassignment = new Assignment(1, endweek, temp, climbsafe);
                tempassignment.assign(1, endweek, currentguide, null);
                climbsafe.addAssignment(tempassignment);
                leftweeks -= endweek;
                
                // persistence save
                try {
                  ClimbSafePersistence.save(climbsafe);
                } catch (RuntimeException e) {
                  throw new Exception(e.getMessage());
                }

              }
              // there is some members assigned to the current guide already,and the member can
              // still be assigned to the current guide
              else if (leftweeks != numberofweeks && leftweeks >= temp.getNrWeeks()) {

                int startweek = numberofweeks - leftweeks + 1;
                int endweek = startweek + temp.getNrWeeks() - 1;
                Assignment tempassignment = new Assignment(startweek, endweek, temp, climbsafe);
                tempassignment.assign(startweek, endweek, currentguide, null);
                climbsafe.addAssignment(tempassignment);
                leftweeks -= temp.getNrWeeks();
                
                // persistence save
                try {
                  ClimbSafePersistence.save(climbsafe);
                } catch (RuntimeException e) {
                  throw new Exception(e.getMessage());
                }

              }
            }
            // the member cannot be assgined to the current guide
            else {

              continue;
            }
            continue;


          }
          continue;
        }

      }
      continue;
    }
    if (climbsafe.getAssignments().size() != memberlist.size()) {
      throw new Exception("Assignments could not be completed for all members");
    }
  }


  /**
   * 
   * @param email
   * @throws InvalidInputException
   */
  public static void cancelTrip(String email) throws InvalidInputException {

    validate_member(email);
    var user = User.getWithEmail(email);
    var member = (Member) user;

    switch (member.getBanStatus()) {
      case Banned:
        throw new InvalidInputException("Cannot cancel the trip due to a ban");
      default:
        break;
    }
    switch (member.getAssignment().getAssignmentStatus()) {
      case Finished:
        throw new InvalidInputException("Cannot cancel a trip which has finished");
      default:
        break;
    }
    // cancel assignment
    member.getAssignment().cancel();
    
    // persistence save
    try {
      ClimbSafePersistence.save();
    } catch (RuntimeException e) {
      throw new InvalidInputException(e.getMessage());
    }

  }

  /**
   * 
   * @param email
   * @throws InvalidInputException
   */
  public static void finishTrip(String email) throws InvalidInputException {

    validate_member(email);
    var user = User.getWithEmail(email);
    var member = (Member) user;

    switch (member.getBanStatus()) {
      case Banned:
        throw new InvalidInputException("Cannot finish the trip due to a ban");
      default:
        break;
    }

    switch (member.getAssignment().getAssignmentStatus()) {
      case Assigned:
        throw new InvalidInputException("Cannot finish a trip which has not started");

      case Paid:
        throw new InvalidInputException("Cannot finish a trip which has not started");
      case Cancelled:
        throw new InvalidInputException("Cannot finish a trip which has been cancelled");
      default:
        break;

    }

    // at valid state-> do transition
    member.getAssignment().finish();
    
    // persistence save
    try {
      ClimbSafePersistence.save();
    } catch (RuntimeException e) {
      throw new InvalidInputException(e.getMessage());
    }

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
      if (a.getStartWeek() == week) {
        switch (a.getMember().getBanStatus()) {
          case Banned:
            throw new InvalidInputException("Cannot start the trip due to a ban");
          default:
            break;
        }
        switch (a.getAssignmentStatus()) {
          case Cancelled:
            throw new InvalidInputException("Cannot start a trip which has been cancelled");
          case Finished:
            throw new InvalidInputException("Cannot start a trip which has finished");
          default:
            break;
        }
        // valid status
        a.start();
        
        // persistence save
        try {
          ClimbSafePersistence.save();
        } catch (RuntimeException e) {
          throw new InvalidInputException(e.getMessage());
        }

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
    var user = User.getWithEmail(email);
    var member = (Member) user;

    // update state
    int startWeek = member.getAssignment().getStartWeek();
    int endWeek = member.getAssignment().getEndWeek();
    Guide guide = member.getAssignment().getGuide();
    Hotel hotel = member.getAssignment().getHotel();

    // assign assignment
    member.getAssignment().assign(startWeek, endWeek, guide, hotel);
    
    // persistence save
    try {
      ClimbSafePersistence.save();
    } catch (RuntimeException e) {
      throw new InvalidInputException(e.getMessage());
    }

    if (code == null || code.equals("") || code.equals(" ")) {
      throw new InvalidInputException("Invalid authorization code");
    }

    switch (member.getBanStatus()) {
      case Banned:
        throw new InvalidInputException("Cannot pay for the trip due to a ban");
      default:
        break;
    }

    switch (member.getAssignment().getAssignmentStatus()) {
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

    // update state
    member.getAssignment().pay(code);
    
    // persistence save
    try {
      ClimbSafePersistence.save();
    } catch (RuntimeException e) {
      throw new InvalidInputException(e.getMessage());
    }

  }

  /**
   * 
   * @param email
   * @throws InvalidInputException
   */
  public static void toggleBan(String email) throws InvalidInputException {

    validate_member(email);
    var user = User.getWithEmail(email);
    var member = (Member) user;

    // update state
    member.ban();
    
    // persistence save
    try {
      ClimbSafePersistence.save();
    } catch (RuntimeException e) {
      throw new InvalidInputException(e.getMessage());
    }

  }

  /**
   * Helper method for validating input email
   * 
   * @param discount
   * @throws InvalidInputException
   */
  private static void validate_member(String email) throws InvalidInputException {

    var user = User.getWithEmail(email);
    if (user == null) {
      throw new InvalidInputException("Member with email address " + email + " does not exist");
    } else if (!(user instanceof Member)) {
      throw new InvalidInputException("Member with email address " + email + " does not exist");
    }

  }



}
