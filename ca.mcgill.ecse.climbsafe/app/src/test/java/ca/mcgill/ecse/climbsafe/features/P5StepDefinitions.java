package ca.mcgill.ecse.climbsafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet6Controller;
import ca.mcgill.ecse.climbsafe.controller.TOAssignment;
import ca.mcgill.ecse.climbsafe.model.Assignment;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Hotel;
import ca.mcgill.ecse.climbsafe.model.Hotel.HotelRating;
import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

/**
 * Implementation of Step Definitions assigned to ECSE 223 Group 5
 * 
 * @author Harrison Wang, Jimmy Sheng, Michael Grieco, Yida Pan, Annie Kang, Sibo Huang
 *
 */
public class P5StepDefinitions {

  private ClimbSafe climbSafe;
  private List<TOAssignment> assignments;

  /**
   * Instantiates <code>ClimbSafe</code> instance for use with Gherkin Scenario.
   * 
   * @param dataTable Data provided in the Cucumber Feature file.
   * @author Harrison Wang
   */
  @Given("the following ClimbSafe system exists: \\(p5)")
  public void the_following_climb_safe_system_exists_p5(io.cucumber.datatable.DataTable dataTable) {
    /*
     * Get the data for the ClimbSafe instance from the Cucumber Feature file. Note: it is assumed
     * that there will only be one element in the list returned from dataTable.asMaps(). This is
     * because there can be only one instance of ClimbSafe in the system at any given time. For this
     * reason, we simply grab the first element in the list.
     */
    Map<String, String> climbSafeDataFromCucumber = dataTable.asMaps().get(0);

    Date startDate = Date.valueOf(climbSafeDataFromCucumber.get("startDate"));
    int nrWeeks = Integer.valueOf(climbSafeDataFromCucumber.get("nrWeeks"));
    int priceOfGuidePerWeek = Integer.valueOf(climbSafeDataFromCucumber.get("priceOfGuidePerWeek"));

    climbSafe = ClimbSafeApplication.getClimbSafe();
    climbSafe.setStartDate(startDate);
    climbSafe.setNrWeeks(nrWeeks);
    climbSafe.setPriceOfGuidePerWeek(priceOfGuidePerWeek);

    assignments = null;
  }

  /**
   * Step definition to add hotels to the ClimbSafe system
   * 
   * @param dataTable table containing hotel information
   * @author Michael Grieco
   */
  @Given("the following hotels exist in the system: \\(p5)")
  public void the_following_hotels_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    // convert data table to a map
    List<Map<String, String>> hotelDataList = dataTable.asMaps();

    for (Map<String, String> hotelData : hotelDataList) {
      // get data from entry
      String nameString = hotelData.get("name");
      String addressString = hotelData.get("address");
      int ratingValue = Integer.parseInt(hotelData.get("rating"));
      HotelRating rating = HotelRating.values()[ratingValue - 1];

      // add Hotel with parsed data
      climbSafe.addHotel(nameString, addressString, rating);
    }
  }

  /**
   * @param dataTable
   * @author YidaPan
   * 
   */
  @Given("the following guides exist in the system: \\(p5)")
  public void the_following_guides_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
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
   * 
   * @param dataTable
   * @author Annie Kang October 20th, 2021
   */
  @Given("the following equipment exists in the system: \\(p5)")
  public void the_following_equipment_exists_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> cucumberData = dataTable.asMaps();

    for (Map<String, String> row : cucumberData) {
      climbSafe.addEquipment(row.get("name"), Integer.parseInt(row.get("weight")),
          Integer.parseInt(row.get("pricePerWeek")));
    }
  }

  /**
   * @author SiboHuang
   * @param dataTable
   */
  @Given("the following equipment bundles exist in the system: \\(p5)")
  public void the_following_equipment_bundles_exist_in_the_system_p5(
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
   * Run code to simulate the administrator viewing assignments.
   * 
   * @author Harrison Wang
   */
  @When("the administrator attempts to view the assignments \\(p5)")
  public void the_administrator_attempts_to_view_the_assignments_p5() {
    assignments = ClimbSafeFeatureSet6Controller.getAssignments();
  }

  /**
   * Check if number of assignments is equal to the expected value.
   * 
   * @author Jimmy Sheng
   * @param string
   */
  @Then("the number of assignments displayed shall be {string} \\(p5)")
  public void the_number_of_assignments_displayed_shall_be_p5(String string) {
    assertEquals(Integer.valueOf(string), assignments.size());
  }

  /**
   * @author Jimmy Sheng
   * @param dataTable
   */
  @Given("the following members exist in the system: \\(p5)")
  public void the_following_members_exist_in_the_system_p5(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    // add each member
    for (var row : rows) {
      String email = row.get("email");
      String password = row.get("password");
      String name = row.get("name");
      String emergencyContact = row.get("emergencyContact");
      int nrWeeks = Integer.parseInt(row.get("nrWeeks"));
      boolean guideRequired = Boolean.parseBoolean(row.get("guideRequired"));
      boolean hotelRequired = Boolean.parseBoolean(row.get("hotelRequired"));
      // add the member to ClimbSafe
      var member = climbSafe.addMember(email, password, name, emergencyContact, nrWeeks,
          guideRequired, hotelRequired);
      /*
       * Parse items and quantities. The null check is necessary in case a member doesn't wish to
       * rent any equipment (e.g. Mohamed Farah in ViewAssignment.feature, line 53)
       */
      String bookedItemsList = row.get("bookedItems");
      String bookedQtyList = row.get("bookedItemQuantities");
      String[] itemNames = bookedItemsList != null ? bookedItemsList.split(",") : new String[] {};
      String[] itemQuantities = bookedQtyList != null ? bookedQtyList.split(",") : new String[] {};
      // add items and quantities (assume they're the same length)
      for (int i = 0; i < itemNames.length; i++) {
        var bookedItem = BookableItem.getWithName(itemNames[i]);
        int qty = Integer.parseInt(itemQuantities[i]);
        climbSafe.addBookedItem(qty, member, bookedItem);
      }
    }
  }

  /**
   * Instantiates <code>Assignment</code> instances for use with Gherkin Scenario.
   * 
   * @param dataTable Data provided in the Cucumber Feature file.
   * @author Harrison Wang
   */
  @Given("the following assignments exist in the system: \\(p5)")
  public void the_following_assignments_exist_in_the_system_p5(
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

  /**
   * 
   * @param dataTable
   * @author Michael Grieco
   */
  @Then("the following assignment information shall be displayed: \\(p5)")
  public void the_following_assignment_information_shall_be_displayed_p5(
      io.cucumber.datatable.DataTable dataTable) {
    // convert data table to a map
    List<Map<String, String>> outputDataList = dataTable.asMaps();

    for (int i = 0; i < outputDataList.size(); i++) {
      TOAssignment assignment = assignments.get(i);
      Map<String, String> assignmentData = outputDataList.get(i);

      assertEquals(assignmentData.get("memberEmail"), assignment.getMemberEmail());
      assertEquals(assignmentData.get("memberName"), assignment.getMemberName());
      assertEquals(assignmentData.get("guideEmail"), assignment.getGuideEmail());
      assertEquals(assignmentData.get("guideName"), assignment.getGuideName());
      assertEquals(assignmentData.get("hotelName"), assignment.getHotelName());
      assertEquals(Integer.parseInt(assignmentData.get("startWeek")), assignment.getStartWeek());
      assertEquals(Integer.parseInt(assignmentData.get("endWeek")), assignment.getEndWeek());
      assertEquals(Integer.parseInt(assignmentData.get("totalCostForEquipment")),
          assignment.getTotalCostForEquipment());
      assertEquals(Integer.parseInt(assignmentData.get("totalCostForGuide")),
          assignment.getTotalCostForGuide());
    }
  }
}

