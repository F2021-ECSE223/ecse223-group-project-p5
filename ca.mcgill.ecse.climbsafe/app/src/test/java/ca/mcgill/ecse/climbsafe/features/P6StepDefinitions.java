package ca.mcgill.ecse.climbsafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet6Controller;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.BundleItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.model.User;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class P6StepDefinitions {

  private ClimbSafe climbSafe;

  /**
   * @author Pierre Masselot
   */
  @Given("the following ClimbSafe system exists: \\(p6)")
  public void the_following_climb_safe_system_exists_p6(io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> data = dataTable.asMaps();
    // get values from table
    Date startDate = Date.valueOf(data.get(0).get("startDate"));
    int nrWeeks = Integer.valueOf(data.get(0).get("nrWeeks"));
    int priceOfGuidePerWeek = Integer.valueOf(data.get(0).get("priceOfGuidePerWeek"));
    // get instance of ClimbSafe
    climbSafe = ClimbSafeApplication.getClimbSafe();
    // set parameter of the ClimbSafe app
    climbSafe.setStartDate(startDate);
    climbSafe.setNrWeeks(nrWeeks);
    climbSafe.setPriceOfGuidePerWeek(priceOfGuidePerWeek);
  }

  /**
   * @author Pierre Masselot
   */
  @Given("the following equipment exists in the system: \\(p6)")
  public void the_following_equipment_exists_in_the_system_p6(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> data = dataTable.asMaps();
    // For each row
    for (var row : data) {
      // get values from table
      String name = row.get("name");
      int weight = Integer.valueOf(row.get("weight"));
      int pricePerWeek = Integer.valueOf(row.get("pricePerWeek"));
      // add equipment with specified values
      climbSafe.addEquipment(name, weight, pricePerWeek);
    }
  }

  /**
   * @author Theodore Glavas
   * 
   */
  @Given("the following equipment bundles exist in the system: \\(p6)")
  public void the_following_equipment_bundles_exist_in_the_system_p6(
      io.cucumber.datatable.DataTable dataTable) {
    // Convert dataTable to List of Maps
    List<Map<String, String>> data = dataTable.asMaps();

    // For each row
    for (var row : data) {
      int discount = Integer.parseInt(row.get("discount"));
      String[] equipmentNames = row.get("items").split(",");
      String[] equipmentQuantities = row.get("quantities").split(",");

      // create bundle and add all bundleItems
      var bundle = new EquipmentBundle(row.get("name"), discount, climbSafe);
      for (int i = 0; i < equipmentNames.length; i++) {
        bundle.addBundleItem(Integer.parseInt(equipmentQuantities[i]), climbSafe,
            (Equipment) BookableItem.getWithName(equipmentNames[i]));
      }
    }
  }

  /**
   * @author Theodore Glavas
   */
  @Given("the following members exist in the system: \\(p6)")
  public void the_following_members_exist_in_the_system_p6(
      io.cucumber.datatable.DataTable dataTable) {
    // Convert dataTable to List of Maps
    List<Map<String, String>> data = dataTable.asMaps();

    // For each row
    for (var row : data) {
      int nrWeeks = Integer.parseInt(row.get("nrWeeks"));
      boolean guideRequired = Boolean.parseBoolean(row.get("guideRequired"));
      boolean hotelRequired = Boolean.parseBoolean(row.get("hotelRequired"));
      String[] bookedItems = row.get("bookedItems").split(",");
      String[] bookedQuantities = row.get("quantity").split(",");

      // create member and add bookedItems
      var member = new Member(row.get("email"), row.get("password"), row.get("name"),
          row.get("emergencyContact"), nrWeeks, guideRequired, hotelRequired, climbSafe);

      for (int i = 0; i < bookedItems.length; i++) {
        member.addBookedItem(Integer.parseInt(bookedQuantities[i]), climbSafe,
            BookableItem.getWithName(bookedItems[i]));
      }
    }
  }

  /**
   * @author Ari Arabian
   */
  @When("the administrator attempts to delete the equipment bundle {string} \\(p6)")
  public void the_administrator_attempts_to_delete_the_equipment_bundle_p6(String string) {
    ClimbSafeFeatureSet6Controller.deleteEquipmentBundle(string);
  }

  /**
   * @author Ari Arabian
   */
  @Then("the number of equipment bundles in the system shall be {string} \\(p6)")
  public void the_number_of_equipment_bundles_in_the_system_shall_be_p6(String string) {
    // Write code here that turns the phrase above into concrete actions
    assertEquals(Integer.parseInt(string), climbSafe.numberOfBundles());
  }

  /**
   * @author Yash Khapre
   */
  @Then("the equipment bundle {string} shall not exist in the system \\(p6)")
  public void the_equipment_bundle_shall_not_exist_in_the_system_p6(String string) {
    // BookableItem is superclass of EquipmentBundle.

    // Should still pass if bookableItem is an equipment or null
    var item = BookableItem.getWithName(string);
    assertTrue(item == null || item instanceof Equipment);
  }

  /**
   * @author Yash Khapre
   */
  @Then("the equipment bundle {string} shall preserve the following properties: \\(p6)")
  public void the_equipment_bundle_shall_preserve_the_following_properties_p6(String string,
      io.cucumber.datatable.DataTable dataTable) {

    // import dataTable as List of Maps
    List<Map<String, String>> data = dataTable.asMaps();

    for (var row : data) {
      // Assume bundle with name string exists, if not exception will tell us why
      var bundle = (EquipmentBundle) BookableItem.getWithName(row.get("name"));

      int discount = Integer.parseInt(row.get("discount"));
      assertEquals(discount, bundle.getDiscount());

      // Adds all the bundle items and their quantities to empty arrays
      String[] expectedItemNames = row.get("items").split(",");
      String[] expectedItemQuantity = row.get("quantities").split(",");

      // Create a Map to contain all the desired (item,quantity) pairs
      Map<String, Integer> expectedItemsAndQuantities = new HashMap<>();

      // Adds integer,string pairs to the map
      for (int i = 0; i < expectedItemNames.length; i++) {
        expectedItemsAndQuantities.put(expectedItemNames[i],
            Integer.parseInt(expectedItemQuantity[i]));
      }

      List<BundleItem> items = bundle.getBundleItems();

      // Create a Map to contain all items in the bundle (item, quantity)
      Map<String, Integer> actualItemsAndQuantities = new HashMap<>();

      // Add bundle item,quantity pairs to the map
      for (var item : items) {
        actualItemsAndQuantities.put(item.getEquipment().getName(), item.getQuantity());
      }

      // Compare maps to ensure that the maps are exactly equal
      assertEquals(expectedItemsAndQuantities, actualItemsAndQuantities);
    }
  }

  /**
   * @author William Lin
   */
  @Then("the member {string} shall have the following bookable items: \\(p6)")
  public void the_member_shall_have_the_following_bookable_items_p6(String string,
      io.cucumber.datatable.DataTable dataTable) {

    // create instance of member by using their email
    var member = (Member) User.getWithEmail(string);
    List<Map<String, String>> data = dataTable.asMaps();
    Map<String, Integer> expectedMemberBookedItems = new HashMap<>();

    // Map the expected booked items for the data table
    for (var row : data) {
      expectedMemberBookedItems.put(row.get("bookedItems"), Integer.parseInt(row.get("quantity")));
    }

    var memberBookedItems = member.getBookedItems();
    Map<String, Integer> actualMemberBookedItems = new HashMap<>();

    // Map the actual member booked items
    for (var bItem : memberBookedItems) {
      actualMemberBookedItems.put(bItem.getItem().getName(), bItem.getQuantity());
    }

    // compare both maps making sure that data list booked items and member's booked items are the
    // same
    assertEquals(expectedMemberBookedItems, actualMemberBookedItems);
  }

  /**
   * @author William Lin
   */
  @Then("the number of pieces of equipment in the system shall be {string} \\(p6)")
  public void the_number_of_pieces_of_equipment_in_the_system_shall_be_p6(String string) {
    // verify that there are the correct number of pieces of equipment in the system after
    // attempting deleting.
    assertEquals(Integer.parseInt(string), climbSafe.numberOfEquipment());
  }
}
