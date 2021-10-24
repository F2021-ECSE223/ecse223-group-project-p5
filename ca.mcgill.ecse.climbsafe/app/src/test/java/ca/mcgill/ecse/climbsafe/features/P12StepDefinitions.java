package ca.mcgill.ecse.climbsafe.features;

import java.sql.Date;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet1Controller;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Member;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class P12StepDefinitions {

  private ClimbSafe climbSafe;

  /**
   * Method containing the steps to be executed when setting up the climbsafe system instance for
   * the guide deletion tests
   * 
   * @author Cédric Barré, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param dataTable Datatable specifying the information with which to setup the climbsafe system
   *        instance
   */
  @Given("the following ClimbSafe system exists: \\(p12)")
  public void the_following_climb_safe_system_exists_p12(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> exClimbSafe = dataTable.asMaps(String.class, String.class);
    climbSafe = ClimbSafeApplication.getClimbSafe();

    climbSafe.setStartDate(Date.valueOf(exClimbSafe.get(0).get("startDate")));
    climbSafe.setNrWeeks(Integer.parseInt(exClimbSafe.get(0).get("nrWeeks")));
    climbSafe
        .setPriceOfGuidePerWeek(Integer.parseInt(exClimbSafe.get(0).get("priceOfGuidePerWeek")));
  }

  /**
   * Method used to instantiate the guides in the climbsafe system used in the gherkin test for
   * guide deletion
   * 
   * @author Cédric Barré, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param dataTable Datatable specifying the guides to be added to the climbsafe system instance
   */
  @Given("the following guides exist in the system: \\(p12)")
  public void the_following_guides_exist_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> guideList = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> g : guideList) {
      climbSafe.addGuide(g.get("email"), g.get("password"), g.get("name"),
          g.get("emergencyContact"));
    }
  }

  /**
   * Method used to instantiate the members in the climbsafe system used in the gherkin test for
   * guide deletion
   * 
   * @author Cédric Barré, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param dataTable Datatable specifying the members to be added to the climbsafe system instance
   */
  @Given("the following members exist in the system: \\(p12)")
  public void the_following_members_exist_in_the_system_p12(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> memberList = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> m : memberList) {
      climbSafe.addMember(m.get("email"), m.get("password"), m.get("name"),
          m.get("emergencyContact"), Integer.parseInt(m.get("nrWeeks")),
          Boolean.parseBoolean(m.get("guideRequired")),
          Boolean.parseBoolean(m.get("hotelRequired")));
    }
  }

  /**
   * Method called by the gherkin guide deletion test to interact with the controller api under
   * test. The method deletes a specified guide through a specific controller method
   * 
   * @author Cédric Barré, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param string Email of the guide to be deleted
   */
  @When("the admin attempts to delete the guide account linked to the {string} \\(p12)")
  public void the_admin_attempts_to_delete_the_guide_account_linked_to_the_p12(String string) {
    ClimbSafeFeatureSet1Controller.deleteGuide(string);
  }

  /**
   * Method used to check that the state of the model is exactly what is expected from the call to
   * the controller method used in previous test steps. Here, we make sure the specified guide does
   * not exist in the system
   * 
   * @author Cédric Barré, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param string Email of the guide which should not exist in the climbsafe system instance
   */
  @Then("the guide account linked to the {string} shall not exist in the system \\(p12)")
  public void the_guide_account_linked_to_the_shall_not_exist_in_the_system_p12(String string) {
    Assert.assertNull(findGuideFromEmail(string));
  }

  /**
   * Method used to check that the state of the model is exactly what is expected from the call to
   * the controller method used in previous test steps. Here, we make sure the amount of guides in
   * the system is the same as the number specified
   * 
   * @author Cédric Barré, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param string Expected number of guides in the climbsafe system instance in the form of a
   *        string
   */
  @Then("the number of guides in the system is {string} \\(p12)")
  public void the_number_of_guides_in_the_system_is_p12(String string) {
    Assert.assertEquals(Integer.parseInt(string), climbSafe.numberOfGuides());
  }

  /**
   * Method used to check that the state of the model is exactly what is expected from the call to
   * the controller method used in previous test steps. Here, we make sure the specified member
   * exists in the system
   * 
   * @author Cédric Barré, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param string Email of the member which should exist in the system
   */
  @Then("the member account linked to the {string} shall exist in the system \\(p12)")
  public void the_member_account_linked_to_the_shall_exist_in_the_system_p12(String string) {
    Assert.assertNotNull(findMemberFromEmail(string));
  }

  /**
   * Method used to check that the state of the model is exactly what is expected from the call to
   * the controller method used in previous test steps. Here, we make sure the number of guides in
   * the system is equal to the Integer specified
   * 
   * @author Cédric Barré, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param int1 Expected number of guides in the climbsafe system instance in the form of an
   *        Integer
   */
  @Then("the number of guides in the system is {int} \\(p12)")
  public void the_number_of_guides_in_the_system_is_p12(Integer int1) {
    Assert.assertEquals(int1, Integer.valueOf(climbSafe.numberOfGuides()));
  }

  /**
   * Helper method used to find a guide of the climbsafe system from its email address
   * 
   * @author Cédric Barré, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param email Email of the guide to find in the climbsafe system instance
   * @return
   */
  private Guide findGuideFromEmail(String email) {
    List<Guide> guideList = ClimbSafeApplication.getClimbSafe().getGuides();
    for (Guide g : guideList) {
      if (g.getEmail().equals(email)) {
        return g;
      }
    }
    return null;
  }

  /**
   * Helper method used to find a member of the climbsafe system from its email address
   * 
   * @author Cédric Barré, Theo Ghanem, Zachary Godden, Chris Hatoum, Habib Jarweh, Philippe
   *         Sarouphim Hochar
   * @param email Email of the member to find in the climbsafe system instance
   * @return
   */
  private Member findMemberFromEmail(String email) {
    List<Member> memberList = ClimbSafeApplication.getClimbSafe().getMembers();
    for (Member m : memberList) {
      if (m.getEmail().equals(email)) {
        return m;
      }
    }
    return null;
  }

}
