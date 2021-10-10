package ca.mcgill.ecse.climbsafe.controller;

import java.sql.Date;

public class ClimbSafeFeatureSet1Controller {

  public static void setup(Date startDate, int nrWeeks, int priceOfGuidePerWeek)
      throws InvalidInputException {}

  public static void deleteMember(String email) {}

  public static void deleteGuide(String email) {}

  // this method needs to be implemented only by teams with seven team members
  public static void deleteHotel(String name) {}

}
