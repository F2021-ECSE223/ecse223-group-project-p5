package ca.mcgill.ecse.climbsafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Date;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet6Controller;
import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.BookedItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import ca.mcgill.ecse.climbsafe.model.Member;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class P7StepDefinitions {

  private ClimbSafe climbSafe;
  private String error;

  /*
   * this key variables is used to fetch the data from the map written by AbdelrahmanAli
   */
  private static final String hotelRequired = "hotelRequired";
  private static final String guideRequired = "guideRequired";
  private static final String emergencyContact = "emergencyContact";
  private static final String password = "password";
  private static final String email = "email";
  private static final String name = "name";
  private static final String startDate = "startDate";
  private static final String nrWeeks = "nrWeeks";
  private static final String weight = "weight";
  private static final String discount = "discount";
  private static final String items = "items";
  private static final String quantity = "quantity";
  private static final String priceOfGuidePerWeek = "priceOfGuidePerWeek";
  private static final String pricePerWeek = "pricePerWeek";

  /**
   * This method assumes that the given date is valid and does not perform any data validation
   * 
   * @author AbdelrahmanAli
   * @param dataTable
   */
  @Given("the following ClimbSafe system exists: \\(p7)")
  public void the_following_climb_safe_system_exists_p7(DataTable dataTable) {

    climbSafe = ClimbSafeApplication.getClimbSafe();
    error = "";

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    climbSafe.setStartDate(Date.valueOf(rows.get(0).get(startDate))); // Fetch and set startDate
    climbSafe.setNrWeeks(Integer.valueOf(rows.get(0).get(nrWeeks))); // Fetch and set number of
                                                                     // weeks
    climbSafe.setPriceOfGuidePerWeek(Integer.valueOf(rows.get(0).get(priceOfGuidePerWeek))); // Fetch
                                                                                             // and
                                                                                             // parse
                                                                                             // price
                                                                                             // of
                                                                                             // guide
                                                                                             // per
                                                                                             // week

  }

  /**
   * This method assumes that the given date is valid and does not perform any data validation
   * 
   * @author AbdelrahmanAli
   * @param dataTable
   */
  @Given("the following pieces of equipment exist in the system: \\(p7)")
  public void the_following_pieces_of_equipment_exist_in_the_system_p7(DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (int i = 0; i < rows.size(); i++) {
      Map<String, String> columns = rows.get(i); // working on a single row a time
      climbSafe.addEquipment(columns.get(name) // add new equipment
          , Integer.parseInt(columns.get(weight)), Integer.parseInt(columns.get(pricePerWeek)));
    }
  }

  /**
   * This method assumes that the given date is valid and does not perform any data validation
   * 
   * @author AbdelrahmanAli
   * @param dataTable
   */
  @Given("the following equipment bundles exist in the system: \\(p7)")
  public void the_following_equipment_bundles_exist_in_the_system_p7(DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (int i = 0; i < rows.size(); i++) {

      Map<String, String> columns = rows.get(i); // working on a single row a time

      // initialize the new EquipmentBundle
      EquipmentBundle newEquipmentBundle = new EquipmentBundle(columns.get(name),
          Integer.parseInt(columns.get(discount)), climbSafe);

      // Fetch the names of the equipments to be added to the bundle
      List<String> equipmentNamesInNewBundle = Arrays.asList(columns.get(items).split(","));
      // Fetch the quantity of each equipment
      List<String> equipmentsQuantity = Arrays.asList(columns.get(quantity).split(","));
      // get all equipments in the system
      List<Equipment> existingEquipments = climbSafe.getEquipment();

      for (int j = 0; j < equipmentNamesInNewBundle.size(); j++) {
        String equipmentName = equipmentNamesInNewBundle.get(j); // Fetch the equipment's name to be
                                                                 // added to the bundle

        // start looking for the equipment by its name
        Equipment equipment = null;
        for (Equipment temp : existingEquipments) {
          if (temp.getName().equals(equipmentName)) {
            equipment = temp;
            break; // break once found
          }
        }

        // add the equipment to the bundle
        newEquipmentBundle.addBundleItem(Integer.parseInt(equipmentsQuantity.get(j)), climbSafe,
            equipment);
      }
    }

  }


  /**
   * This method assumes that the given date is valid and does not perform any data validation
   *
   * @author yujieqin
   * @param dataTable
   */

  @Given("the following members exist in the system: \\(p7)")
  public void the_following_members_exist_in_the_system_p7(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (int i = 0; i < rows.size(); i++) {

      Map<String, String> columns = rows.get(i);

      Member newMember = new Member(columns.get(email), columns.get(password), columns.get(name),
          columns.get(emergencyContact), Integer.parseInt(columns.get(nrWeeks)),
          Boolean.parseBoolean(columns.get(guideRequired)),
          Boolean.parseBoolean(columns.get(hotelRequired)), climbSafe);

      List<String> bookedItem = Arrays.asList(columns.get("bookedItems").split(","));
      List<String> itemQuality = Arrays.asList(columns.get("quantity").split(","));

      for (int j = 0; j < bookedItem.size(); j++) {
        BookableItem.getWithName(bookedItem.get(j))
            .addBookedItem(Integer.parseInt(itemQuality.get(j)), climbSafe, newMember);

      }

    }

  }


  /**
   * This method test to attempts to delete the piece of equipment
   * 
   * @author yujieqin
   * @param string
   */

  @When("the administrator attempts to delete the piece of equipment in the system with name {string} \\(p7)")
  public void the_administrator_attempts_to_delete_the_piece_of_equipment_in_the_system_with_name_p7(
      String string) {

    try {

      ClimbSafeFeatureSet6Controller.deleteEquipment(string);

    } catch (InvalidInputException e) {
      error += e.getMessage();
    }

  }

  /**
   * @author Dawei Zhou
   * @param String numEquip
   */
  @Then("the number of pieces of equipment in the system shall be {string} \\(p7)")
  public void the_number_of_pieces_of_equipment_in_the_system_shall_be_p7(String numEquip) {
    assertEquals(Integer.parseInt(numEquip), climbSafe.getEquipment().size());
  }

  /**
   * @author Dawei Zhou
   * @param String name
   * @param String weight
   * @param String price
   */
  @Then("the piece of equipment with name {string}, weight {string}, and price per week {string} shall not exist in the system \\(p7)")
  public void the_piece_of_equipment_with_name_weight_and_price_per_week_shall_not_exist_in_the_system_p7(
      String name, String weight, String price) {

    BookableItem bookableItem = Equipment.getWithName(name);
    /// *
    if (bookableItem != null) { // the case when the result is not null

      if (Equipment.class.isInstance(bookableItem)) { // the case when the result is in type
                                                      // Equipment
        Equipment equipment = (Equipment) bookableItem;
        assertTrue( // then either or both of weight and price should be different
            equipment.getWeight() != Integer.parseInt(weight)
                || equipment.getPricePerWeek() != Integer.parseInt(price),
            String.format(
                "Expect weight not to be %d, but it is %d, OR/AND price not to be %d, but it is %d",
                Integer.parseInt(weight), equipment.getWeight(), Integer.parseInt(price),
                equipment.getPricePerWeek()));
      }
    }
  }

  /**
   * @author Karim Atoui
   * @param dataTable
   */

  @Then("the following pieces of equipment shall exist in the system: \\(p7)")
  public void the_following_pieces_of_equipment_shall_exist_in_the_system_p7(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    BookableItem e = null;

    for (int i = 0; i < rows.size(); i++) {

      e = Equipment.getWithName(rows.get(i).get(name));

      assertTrue(e instanceof Equipment);

      assertEquals(((Equipment) e).getWeight(), Integer.parseInt(rows.get(i).get(weight)));
      assertEquals(((Equipment) e).getPricePerWeek(),
          Integer.parseInt(rows.get(i).get(pricePerWeek)));

    }
  }

  /**
   * @author Karim Atoui
   * @param email
   * @param numberOfBookedItems
   */

  @Then("the number of booked items for the member with email {string} shall be {string} \\(p7)")
  public void the_number_of_booked_items_for_the_member_with_email_shall_be_p7(String email,
      String numberOfBookedItems) {
    // Write code here that turns the phrase above into concrete actions

    Member member = null;

    for (Member m : climbSafe.getMembers()) {
      if (m.getEmail().equals(email)) {
        member = m;
        break;
      }
    }

    assertNotNull(member);

    assertEquals(Integer.parseInt(numberOfBookedItems), member.getBookedItems().size());

  }

  /**
   * @author FrederikMartin
   * @param email
   * @param itemName
   * @param itemQuantity
   */
  @Then("the member with email {string} shall have a bookable item with name {string} and quantity {string} \\(p7)")
  public void the_member_with_email_shall_have_a_bookable_item_with_name_and_quantity_p7(
      String email, String itemName, String itemQuantity) {
    // Write code here that turns the phrase above into concrete actions

    Member member = null;
    for (Member m : climbSafe.getMembers()) {
      if (m.getEmail().equals(email)) {
        member = m;
        break;
      }
    }
    assertNotNull(member);

    BookedItem item = null;
    for (BookedItem bi : member.getBookedItems()) {
      if (bi.getItem().getName().equals(itemName)) {
        item = bi;
        break;
      }
    }
    assertNotNull(item);
    assertEquals(item.getQuantity(), Integer.parseInt(itemQuantity));
  }

  /**
   * @author FrederikMartin
   * @param equipmentBundleQuantity
   */
  @Then("the number of equipment bundles in the system shall be {string} \\(p7)")
  public void the_number_of_equipment_bundles_in_the_system_shall_be_p7(
      String equipmentBundleQuantity) {
    // Write code here that turns the phrase above into concrete actions

    assertEquals(climbSafe.getBundles().size(), Integer.parseInt(equipmentBundleQuantity));
  }

  /**
   * @author rambodazimi
   * @param dataTable
   */

  @Then("the following equipment bundles shall exist in the system: \\(p7)")
  public void the_following_equipment_bundles_shall_exist_in_the_system_p7(
      io.cucumber.datatable.DataTable dataTable) {

    // creating a list of lists from dataTable
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    // save all the bundles from climSafe application to the bundles list
    List<EquipmentBundle> bundles = climbSafe.getBundles();
    // iterate through each bundle in the system
    for (int i = 1; i < rows.size(); i++) {
      String bundleName = rows.get(i).get(name);
      String bundleDiscount = rows.get(i).get(discount);

      // Discount need to be saved in an integer data type instead of String
      int bundleDiscountInt = Integer.parseInt(bundleDiscount);

      // because items can be multiple, we save them in a list
      List<String> equipmentItems = Arrays.asList(rows.get(i).get(items).split(","));

      // because quantities can be multiple, we save them in a list
      List<String> equipmentQuantity = Arrays.asList(rows.get(i).get(quantity).split(","));

      EquipmentBundle bb = null;
      for (var x : climbSafe.getBundles()) {
        if (x.getName().equals(bundleName)) {
          bb = x;
          break;
        }
      }
      assertNotNull(bb);
      // The name has already been checked in the for loop. So, we check only discount, items, and
      // quantities
      // check each discount amount
      assertEquals(bundleDiscountInt, bb.getDiscount());
      // check the similarity of each item of an equipment bundle as well as the quantity
      for (int j = 0; j < bb.getBundleItems().size(); j++) {
        // check each item's name
        assertEquals(equipmentItems.get(j), bb.getBundleItem(j).getEquipment().getName());
        int equipmentQuantityInt = Integer.parseInt(equipmentQuantity.get(j));
        // check each quantity of each item
        assertEquals(equipmentQuantityInt, bb.getBundleItems().get(j).getQuantity());
      }
    }
  }


  /**
   * @author rambodazimi
   * @param string
   */


  @Then("the system shall raise the error {string} \\(p7)")
  public void the_system_shall_raise_the_error_p7(String string) {
    assertTrue(error.contains(string));
  }

}
