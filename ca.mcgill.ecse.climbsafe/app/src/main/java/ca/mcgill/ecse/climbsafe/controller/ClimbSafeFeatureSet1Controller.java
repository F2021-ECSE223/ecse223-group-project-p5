package ca.mcgill.ecse.climbsafe.controller;

import java.sql.Date;

import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.model.User;

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
    // constraint ensuring positive number of weeks
    if (nrWeeks < 0) {
      throw new InvalidInputException("The number of climbing weeks must be greater than or equal to zero.");
    }

    // constraint ensuring positive guide price
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
    User targetUser = Member.getWithEmail(email);
    /*
     * After checking the found User is not null, we must verify targetUser is indeed a Member because
     * Member.getWithEmail calls the superclass method, User.getWithEmail which iterates through all
     * users, including the Admin and Guides.
     */
    if (targetUser != null && targetUser instanceof Member) {
      targetUser.delete();
    }
  }

  /**
   * Deletes a guide from the system
   * 
   * @param email the email of the guide to delete
   */
  public static void deleteGuide(String email) {
    User targetUser = Guide.getWithEmail(email);
    /*
     * After checking the found User is not null, we must verify targetUser is indeed a Guide because
     * Guide.getWithEmail calls the superclass method, User.getWithEmail which iterates through all
     * users, including the Admin and Members.
     */
    if (targetUser != null && targetUser instanceof Guide) {
      targetUser.delete();
    }
  }

  // this method needs to be implemented only by teams with seven team members
  public static void deleteHotel(String name) {}

}
