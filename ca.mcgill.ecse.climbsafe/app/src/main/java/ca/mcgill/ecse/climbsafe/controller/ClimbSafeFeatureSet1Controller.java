package ca.mcgill.ecse.climbsafe.controller;

import java.sql.Date;

import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Member;

/**
 * ClimbSafeFeatureSet1 is the first feature set for the ClimbSafe system
 * 
 * @author Michael Grieco
 */
public class ClimbSafeFeatureSet1Controller {

  /**
   * Instantiates the ClimbSafe class if necessary and passes in the parameters
   * 
   * @param startDate the start date of the climbing season
   * @param nrWeeks the number of weeks in the climbing season
   * @param priceOfGuidePerWeek the price of a climbing guide per week
   * @throws InvalidInputException if nrWeeks or priceOfGuidePerWeek are negative
   */
  public static void setup(Date startDate, int nrWeeks, int priceOfGuidePerWeek)
      throws InvalidInputException {
    if (nrWeeks < 0) {
      throw new InvalidInputException("The number of climbing weeks must be greater than or equal to zero.");
    }

    if (priceOfGuidePerWeek < 0.0) {
      throw new InvalidInputException("The price of guide per week must be greater than or equal to zero.");
    }

    // setup singleton ClimbSafe object with parameters
    ClimbSafe cs = ClimbSafeApplication.getClimbSafe();
    cs.setStartDate(startDate);
    cs.setNrWeeks(nrWeeks);
    cs.setPriceOfGuidePerWeek(priceOfGuidePerWeek);
  }

  /**
   * Deletes a member from the ClimbSafe system
   * 
   * @param email the email of the member to delete
   */
  public static void deleteMember(String email) {
    ClimbSafe cs = ClimbSafeApplication.getClimbSafe();

    for (Member member : cs.getMembers()) {
      if (member.getEmail() == email) {
        // found member
        member.delete();
        break;
      }
    }
  }

  /**
   * Deletes a guide from the system
   * 
   * @param email the email of the guide to delete
   */
  public static void deleteGuide(String email) {
    ClimbSafe cs = ClimbSafeApplication.getClimbSafe();

    for (Guide guide : cs.getGuides()) {
      if (guide.getEmail() == email) {
        // found guide
        guide.delete();
        break;
      }
    }
  }

  // this method needs to be implemented only by teams with seven team members
  public static void deleteHotel(String name) {}

}
