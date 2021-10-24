package ca.mcgill.ecse.climbsafe.features;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet1Controller;
import ca.mcgill.ecse.climbsafe.model.*;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.After;
import org.junit.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class P14StepDefinitions {
  private ClimbSafe climbSafe;
  private String error = "";

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param dataTable the table containing the climbSafe nrWeeks, startDate, and priceOfGuidePerWeek
   *        attributes
   */
  @Given("the following ClimbSafe system exists: \\(p14)")
  public void the_following_climb_safe_system_exists_p14(
      io.cucumber.datatable.DataTable dataTable) {

    climbSafe = ClimbSafeApplication.getClimbSafe();

    error = "";
    List<Map<String, String>> theDataTable = dataTable.asMaps(String.class, String.class);

    climbSafe.setNrWeeks(Integer.parseInt(theDataTable.get(0).get("nrWeeks")));
    climbSafe.setStartDate(Date.valueOf(theDataTable.get(0).get("startDate")));
    climbSafe
        .setPriceOfGuidePerWeek(Integer.parseInt(theDataTable.get(0).get("priceOfGuidePerWeek")));

  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param dataTable table containing the equipment that we need to instantiate in our system
   */
  @Given("the following equipment exists in the system: \\(p14)")
  public void the_following_equipment_exists_in_the_system_p14(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> cur : rows) {
      climbSafe.addEquipment(cur.get("name"), Integer.parseInt(cur.get("weight")),
          Integer.parseInt(cur.get("pricePerWeek")));
    }

  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param dataTable the table containing the equipmentBundles that we need to instantiate in our
   *        system
   */
  @Given("the following equipment bundles exist in the system: \\(p14)")
  public void the_following_equipment_bundles_exist_in_the_system_p14(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> equipmentBundles = dataTable.asMaps();
    for (Map<String, String> e : equipmentBundles) {
      climbSafe.addBundle(e.get("name"), Integer.parseInt(e.get("discount")));

    }
  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param dataTable the table containing the Members that we need to instantiate in our system
   */
  @Given("the following members exist in the system: \\(p14)")
  public void the_following_members_exist_in_the_system_p14(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();

    for (Map<String, String> columns : rows) {

      Member m =
          climbSafe.addMember(columns.get("email"), columns.get("password"), columns.get("name"),
              columns.get("emergencyContact"), Integer.parseInt(columns.get("nrWeeks")),
              Boolean.parseBoolean(columns.get("guideRequired")),
              Boolean.parseBoolean(columns.get("hotelRequired")));
      String[] itemStrings = columns.get("bookableItems").split(",");
      String[] itemQuantity = columns.get("requestedQuantities").split(",");
      for (int i = 0; i < itemStrings.length; i++) {
        m.addBookedItem(Integer.parseInt(itemQuantity[i]), climbSafe,
            BookableItem.getWithName(itemStrings[i]));
      }
    }

  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param dataTable the table containing the Guides that we need to instantiate in our system
   */
  @Given("the following guides exist in the system: \\(p14)")
  public void the_following_guides_exist_in_the_system_p14(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> data = dataTable.asMaps();
    for (Map<String, String> r : data) {

      climbSafe.addGuide(r.get("email"), r.get("password"), r.get("name"),
          r.get("emergencyContact"));
    }
  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param string the email of the account that the member is trying to delete
   */
  @When("the member attempts to delete the account with email {string} \\(p14)")
  public void the_member_attempts_to_delete_the_account_with_email_p14(String string) {
    ClimbSafeFeatureSet1Controller.deleteMember(string);

  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param string the email of the Member that was hopefully deleted
   */
  @Then("the member account with the email {string} does not exist \\(p14)")
  public void the_member_account_with_the_email_does_not_exist_p14(String string) {
    User u = User.getWithEmail(string);
    Assert.assertTrue(u == null || !(u instanceof Member));
  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param string the number of members that should now be in the system
   */
  @Then("there are {string} members in the system. \\(p14)")
  public void there_are_members_in_the_system_p14(String string) {
    Assert.assertEquals(Integer.parseInt(string), climbSafe.getMembers().size());
  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param string the email of the Member account that the member is trying to delete
   */
  @When("the member attempts to delete the member account with email {string} \\(p14)")

  public void the_member_attempts_to_delete_the_member_account_with_email_p14(String string) {

    ClimbSafeFeatureSet1Controller.deleteMember(string);

  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param string the email of the guide account that should still exist in the system
   */
  @Then("the guide account linked to the {string} shall exist in the system \\(p14)")
  public void the_guide_account_linked_to_the_shall_exist_in_the_system_p14(String string) {
    User user = User.getWithEmail(string);
    Assert.assertNotNull(user);
    Assert.assertTrue(user instanceof Guide);
  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param numGuides the number of Guides that should remain in the system
   */
  @Then("the number of guides in the system is {int} \\(p14)")
  public void the_number_of_guides_in_the_system_is_p14(Integer numGuides) {
    Assert.assertEquals(numGuides, Integer.valueOf(climbSafe.numberOfGuides()));
  }

  /**
   * @author: Hongfei Liu, Zihan Zhang, Matt MacDonald, Ryan Reszetnik, Sabrina Mansour, Sehr
   *          Moosabhoy
   * @param numMembers the number of Members that should still be in the systme
   */
  @Then("there are {int} members in the system. \\(p14)")
  public void there_are_members_in_the_system_p14(Integer numMembers) {
    Assert.assertEquals(numMembers, Integer.valueOf(climbSafe.numberOfMembers()));
  }
}
