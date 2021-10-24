package ca.mcgill.ecse.climbsafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet1Controller;
import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class P10StepDefinitions {

  private ClimbSafe climbSafe;
  private String error = "";

  /**
   * @author Andrew Geday, Andrei Cosmovici
   */
  @Given("a ClimbSafe system exists with defaults values {string} as start date, {string} for number of weeks, and {string} for price of guide per week. \\(p10)")
  public void a_climb_safe_system_exists_with_defaults_values_as_start_date_for_number_of_weeks_and_for_price_of_guide_per_week_p10(
      String date, String weeks, String price) {
    error = "";

    climbSafe = ClimbSafeApplication.getClimbSafe();
    climbSafe.setStartDate(Date.valueOf(date));
    climbSafe.setNrWeeks(Integer.parseInt(weeks));
    climbSafe.setPriceOfGuidePerWeek(Integer.parseInt(price));
  }

  /**
   * @author Gwynette Labitoria, Jerry Hou-Liu
   */
  @When("the admin attempts to setup the NMC program information with the start date {string}, number of climbing weeks {string}, and price of guide per week {string} \\(p10)")
  public void the_admin_attempts_to_setup_the_nmc_program_information_with_the_start_date_number_of_climbing_weeks_and_price_of_guide_per_week_p10(
      String date, String weeks, String price) {
    Date d = Date.valueOf(date);
    int nrWeeks = Integer.parseInt(weeks);
    int p = Integer.parseInt(price);
    try {
      ClimbSafeFeatureSet1Controller.setup(d, nrWeeks, p);
    } catch (InvalidInputException e) {
      error += e.getMessage();
    }

  }

  /**
   * @author Eric Xia, Gwynette Labitoria
   */
  @Then("the NMC program information shall be successfully setup with the start date {string}, number of climbing weeks {string}, and price of guide per week {string} \\(p10)")
  public void the_nmc_program_information_shall_be_successfully_setup_with_the_start_date_number_of_climbing_weeks_and_price_of_guide_per_week_p10(
      String date, String weeks, String price) {
    assertEquals(date, String.valueOf(climbSafe.getStartDate()));
    assertEquals(weeks, String.valueOf(climbSafe.getNrWeeks()));
    assertEquals(price, String.valueOf(climbSafe.getPriceOfGuidePerWeek()));

  }

  /**
   * @author Andrew Geday, Jerry Hou-Liu
   */
  @When("the admin attempts to setup the NMC program inforamtion with the start date {string}, number of climbing weeks {string}, and price of guide per week {string} \\(p10)")
  public void the_admin_attempts_to_setup_the_nmc_program_inforamtion_with_the_start_date_number_of_climbing_weeks_and_price_of_guide_per_week_p10(
      String date, String weeks, String price) {

    try {
      Date d = Date.valueOf(date);
      int nrWeeks = Integer.parseInt(weeks);
      int p = Integer.parseInt(price);
      ClimbSafeFeatureSet1Controller.setup(d, nrWeeks, p);
    } catch (InvalidInputException e1) {
      error += e1.getMessage();
    } catch (IllegalArgumentException e2) {
      error = "Invalid date";
    }

  }

  /**
   * @author Ethan Cheng, Andrei Cosmovici
   */
  @Then("the following {string} shall be raised. \\(p10)")
  public void the_following_shall_be_raised_p10(String expError) {
    assertTrue(error.contains(expError));
  }
}
