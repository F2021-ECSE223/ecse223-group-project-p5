package ca.mcgill.ecse.climbsafe.controller;


import java.util.List;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.Assignment;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Hotel;
import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.model.User;
import ca.mcgill.ecse.climbsafe.model.Assignment.AssignmentStatus;
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
          /*
           * If the member already has an assignment, we subtract the weeks of their trip from the
           * available weeks of the guide.
           */
          leftweeks -= temp.getNrWeeks();
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
    var member = validate_member(email);

    try {
      // cancel assignment
      member.getAssignment().cancel();
      // persistence save
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
    var member = validate_member(email);

    try {
      // finish trip
      member.getAssignment().finish();
      // persistence save
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
        // do not want to stop execution if one trip cannot start
        try {
          a.start();
        } catch (RuntimeException e) {
          throw new InvalidInputException(e.getMessage());
        }
      }
    }
  }

  /**
   * start trips for a target week and return
   * 
   * @param week the target week
   * @return array containing: [ number of trips started, number of trips failed, detailed report ]
   * @throws InvalidInputException if invalid week number
   */
  public static String[] startTripsForWeekReturnDetails(int week) throws InvalidInputException {
    // reference to ClimbSafe
    ClimbSafe cs = ClimbSafeApplication.getClimbSafe();

    if (!(week > 0 && week <= cs.getNrWeeks())) {
      throw new InvalidInputException(
          "The week must be greater than zero and less than or equal to the number of climbing weeks in the climbing season");
    }

    String ret[] = new String[3];

    int noTripsStarted = 0;
    int noTripsFailed = 0;
    int noBans = 0;
    String details = "";

    for (Assignment a : cs.getAssignments()) {
      if (a.getStartWeek() == week) {
        String result = "";

        try {
          a.start();
          if (a.getAssignmentStatus().equals(AssignmentStatus.Assigned)) {
            // member was banned because they have not paid
            noTripsFailed++;
            noBans++;
            result = "Banned member";
          } else {
            noTripsStarted++;
            result = "Successfully started trip";
          }
        } catch (RuntimeException e) {
          noTripsFailed++;
          result = e.getMessage();
        }

        details += String.format("%s: %s\n", a.getMember().getEmail(), result);
      }
    }

    ret[0] = String.valueOf(noTripsStarted);
    ret[1] = String.format("%d (%d new bans)", noTripsFailed, noBans);
    ret[2] = details;

    return ret;
  }

  /**
   * 
   * @param email
   * @param code
   * @throws InvalidInputException
   */

  public static void confirmPayment(String email, String code) throws InvalidInputException {
    if (code == null || code.isEmpty()) {
      throw new InvalidInputException("Invalid authorization code");
    }

    var member = validate_member(email);

    // update state
    int startWeek = member.getAssignment().getStartWeek();
    int endWeek = member.getAssignment().getEndWeek();
    Guide guide = member.getAssignment().getGuide();
    Hotel hotel = member.getAssignment().getHotel();

    // assign assignment
    member.getAssignment().assign(startWeek, endWeek, guide, hotel);

    try {
      // update state
      member.getAssignment().pay(code);
      // persistence save
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
    var member = validate_member(email);

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
  private static Member validate_member(String email) throws InvalidInputException {

    var user = User.getWithEmail(email);
    if (user == null) {
      throw new InvalidInputException("Member with email address " + email + " does not exist");
    } else if (!(user instanceof Member)) {
      throw new InvalidInputException("Member with email address " + email + " does not exist");
    }

    return (Member) user;
  }

}
