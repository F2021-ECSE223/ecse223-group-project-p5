package ca.mcgill.ecse.climbsafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.Assignment;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Hotel;
import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Implementation of Step Definitions for Iteration 3
 * 
 * @author Harrison Wang, Jimmy Sheng, Michael Grieco, Yida Pan, Annie Kang, Sibo Huang
 *
 */
public class AssignmentFeatureStepDefinitions {

  private ClimbSafe climbSafe;
  private String errorMessage;

  /**
   * Instantiates <code>ClimbSafe</code> instance for use with Gherkin Scenario.
   * 
   * @param dataTable Data provided in the Cucumber Feature file.
   * @author Harrison Wang
   */
  @Given("the following ClimbSafe system exists:")
  public void the_following_climb_safe_system_exists(io.cucumber.datatable.DataTable dataTable) {
    Map<String, String> climbSafeDataFromCucumber = dataTable.asMaps().get(0);

    Date startDate = Date.valueOf(climbSafeDataFromCucumber.get("startDate"));
    int nrWeeks = Integer.valueOf(climbSafeDataFromCucumber.get("nrWeeks"));
    int priceOfGuidePerWeek = Integer.valueOf(climbSafeDataFromCucumber.get("priceOfGuidePerWeek"));

    climbSafe = ClimbSafeApplication.getClimbSafe();
    climbSafe.setStartDate(startDate);
    climbSafe.setNrWeeks(nrWeeks);
    climbSafe.setPriceOfGuidePerWeek(priceOfGuidePerWeek);

    errorMessage = null;
  }

  /**
   * Adds equipment to the <code>ClimbSafe</code> instance for use with Gherkin Scenario. Copied
   * from P5StepDefinitions.java, originally written by Annie Kang.
   * 
   * @param dataTable Data provided in the Cucumber Feature file.
   * @author Harrison Wang
   */
  @Given("the following pieces of equipment exist in the system:")
  public void the_following_pieces_of_equipment_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> cucumberData = dataTable.asMaps();

    for (Map<String, String> row : cucumberData) {
      climbSafe.addEquipment(row.get("name"), Integer.parseInt(row.get("weight")),
          Integer.parseInt(row.get("pricePerWeek")));
    }
  }

  /**
   * Adds equipment bundles to the <code>ClimbSafe</code> instance for use with Gherkin Scenario.
   * Copied from P5StepDefinitions.java, originally written by Sibo Huang.
   * 
   * @param dataTable Data provided in the Cucumber Feature file.
   * @author Harrison Wang
   */
  @Given("the following equipment bundles exist in the system:")
  public void the_following_equipment_bundles_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> cucumberData = dataTable.asMaps();

    for (var equipmentBundle : cucumberData) {
      int discount = Integer.valueOf(equipmentBundle.get("discount"));
      String items = equipmentBundle.get("items");
      String quantities = equipmentBundle.get("quantities");
      String[] equipmentItems = items.split(",");
      String[] equipmentQuantities = quantities.split(",");

      var newBundle = new EquipmentBundle(equipmentBundle.get("name"), discount, climbSafe);

      for (int i = 0; i < equipmentItems.length; i++) {
        newBundle.addBundleItem(Integer.valueOf(equipmentQuantities[i]), climbSafe,
            (Equipment) BookableItem.getWithName(equipmentItems[i]));
      }
    }
  }

  /**
   * Adds guides to the <code>ClimbSafe</code> instance for use with Gherkin Scenario. Copied from
   * P5StepDefinitions.java, originally written by Yida Pan.
   * 
   * @param dataTable Data provided in the Cucumber Feature file.
   * @author Harrison Wang
   */
  @Given("the following guides exist in the system:")
  public void the_following_guides_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      String emergencycontact = row.get("emergencyContact");
      climbSafe.addGuide(email, password, name, emergencycontact);
    }
  }

  /**
   * Adds members to the <code>ClimbSafe</code> instance for use with Gherkin Scenario. Copied from
   * P5StepDefinitions.java, originally written by AJimmy Sheng.
   * 
   * @param dataTable Data provided in the Cucumber Feature file.
   * @author Harrison Wang
   */
  @Given("the following members exist in the system:")
  public void the_following_members_exist_in_the_system(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();

    for (var row : rows) {
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      String emergencyContact = row.get("emergencyContact");
      int nrWeeks = Integer.parseInt(row.get("nrWeeks"));
      boolean guideRequired = Boolean.parseBoolean(row.get("guideRequired"));
      boolean hotelRequired = Boolean.parseBoolean(row.get("hotelRequired"));

      var member = climbSafe.addMember(email, password, name, emergencyContact, nrWeeks,
          guideRequired, hotelRequired);

      String bookedItemsList = row.get("bookedItems");
      String bookedQtyList = row.get("bookedItemQuantities");
      String[] itemNames = bookedItemsList != null ? bookedItemsList.split(",") : new String[] {};
      String[] itemQuantities = bookedQtyList != null ? bookedQtyList.split(",") : new String[] {};

      for (int i = 0; i < itemNames.length; i++) {
        var bookedItem = BookableItem.getWithName(itemNames[i]);
        int qty = Integer.parseInt(itemQuantities[i]);
        climbSafe.addBookedItem(qty, member, bookedItem);
      }
    }
  }

  @When("the administrator attempts to initiate the assignment process")
  public void the_administrator_attempts_to_initiate_the_assignment_process() {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  /**
   * Checks that all assignments expected by the Gherkin Scenario are present in the system.
   * 
   * @param dataTable Data provided in the Cucumber Feature file.
   * @author Harrison Wang
   */
  @Then("the following assignments shall exist in the system:")
  public void the_following_assignments_shall_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> expectedAssignments = dataTable.asMaps();
    List<Assignment> assignments = climbSafe.getAssignments();

    assertEquals(expectedAssignments.size(), assignments.size());

    for (int i = 0; i < assignments.size(); i++) {
      var assignment = assignments.get(i);
      var expectedAssignment = expectedAssignments.get(i);
      assertEquals(expectedAssignment.get("memberEmail"), assignment.getMember().getEmail());
      assertEquals(expectedAssignment.get("guideEmail"),
          assignment.hasGuide() ? assignment.getGuide().getEmail() : null);
      assertEquals(Integer.valueOf(expectedAssignment.get("startWeek")), assignment.getStartWeek());
      assertEquals(Integer.valueOf(expectedAssignment.get("endWeek")), assignment.getEndWeek());
    }
  }

  @Then("the assignment for {string} shall be marked as {string}")
  public void the_assignment_for_shall_be_marked_as(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  /**
   * Checks the number of assignments in the system.
   * 
   * @param expectedNumberOfAssignments The number of assignments that should be in the system.
   * @author Harrison Wang
   */
  @Then("the number of assignments in the system shall be {string}")
  public void the_number_of_assignments_in_the_system_shall_be(String expectedNumberOfAssignments) {
    assertEquals(Integer.valueOf(expectedNumberOfAssignments), climbSafe.numberOfAssignments());
  }

  /**
   * Checks the error raised by the system.
   * 
   * @param expectedError The error that should be raised by the system.
   * @author Harrison Wang
   */
  @Then("the system shall raise the error {string}")
  public void the_system_shall_raise_the_error(String expectedError) {
    assertEquals(expectedError, errorMessage);
  }

  /**
   * Instantiates <code>Assignment</code> instances for use with Gherkin Scenario.
   * 
   * @param dataTable Data provided in the Cucumber Feature file.
   * @author Harrison Wang
   */
  @Given("the following assignments exist in the system:")
  public void the_following_assignments_exist_in_the_system(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> cucumberData = dataTable.asMaps();

    for (Map<String, String> assignmentData : cucumberData) {
      var assignmentMember = (Member) User.getWithEmail(assignmentData.get("memberEmail"));
      var assignmentGuide = (Guide) User.getWithEmail(assignmentData.get("guideEmail"));
      var assignmentHotel = Hotel.getWithName(assignmentData.get("hotelName"));
      int startWeek = Integer.valueOf(assignmentData.get("startWeek"));
      int endWeek = Integer.valueOf(assignmentData.get("endWeek"));

      Assignment newAssignment = climbSafe.addAssignment(startWeek, endWeek, assignmentMember);
      newAssignment.setGuide(assignmentGuide);
      newAssignment.setHotel(assignmentHotel);
    }
  }

  @When("the administrator attempts to confirm payment for {string} using authorization code {string}")
  public void the_administrator_attempts_to_confirm_payment_for_using_authorization_code(
      String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the assignment for {string} shall record the authorization code {string}")
  public void the_assignment_for_shall_record_the_authorization_code(String string,
      String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the member account with the email {string} does not exist")
  public void the_member_account_with_the_email_does_not_exist(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  /**
   * Checks the number of members in the system.
   * 
   * @param expectedNumberOfMembers The number of members that should be in the system.
   * @author Harrison Wang
   */
  @Then("there are {string} members in the system")
  public void there_are_members_in_the_system(String expectedNumberOfMembers) {
    assertEquals(Integer.valueOf(expectedNumberOfMembers), climbSafe.numberOfMembers());
  }

  /**
   * Checks the error raised by the system.
   * 
   * @param expectedError The error that should be raised by the system.
   * @author Harrison Wang
   */
  @Then("the error {string} shall be raised")
  public void the_error_shall_be_raised(String expectedError) {
    assertEquals(expectedError, errorMessage);
  }

  @When("the administrator attempts to cancel the trip for {string}")
  public void the_administrator_attempts_to_cancel_the_trip_for(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Given("the member with {string} has paid for their trip")
  public void the_member_with_has_paid_for_their_trip(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the member with email address {string} shall receive a refund of {string} percent")
  public void the_member_with_email_address_shall_receive_a_refund_of_percent(String string,
      String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Given("the member with {string} has started their trip")
  public void the_member_with_has_started_their_trip(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the administrator attempts to finish the trip for the member with email {string}")
  public void the_administrator_attempts_to_finish_the_trip_for_the_member_with_email(
      String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Given("the member with {string} is banned")
  public void the_member_with_is_banned(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Then("the member with email {string} shall be {string}")
  public void the_member_with_email_shall_be(String string, String string2) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @When("the administrator attempts to start the trips for week {string}")
  public void the_administrator_attempts_to_start_the_trips_for_week(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Given("the member with {string} has cancelled their trip")
  public void the_member_with_has_cancelled_their_trip(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }

  @Given("the member with {string} has finished their trip")
  public void the_member_with_has_finished_their_trip(String string) {
    // Write code here that turns the phrase above into concrete actions
    throw new io.cucumber.java.PendingException();
  }
}
