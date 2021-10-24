package ca.mcgill.ecse.climbsafe.features;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.function.Executable;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet4Controller;
import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class P1StepDefinitions {
  private ClimbSafe climbSafe;
  private String error;

  /**
   * @author Asma Gandour
   */
  @Given("the following ClimbSafe system exists: \\(p1)")
  public void the_following_climb_safe_system_exists_p1(io.cucumber.datatable.DataTable dataTable) {
    var rows = dataTable.asMaps();

    for (var row : rows) {
      climbSafe = new ClimbSafe(Date.valueOf(row.get("startDate")),
          Integer.parseInt(row.get("nrWeeks")), Integer.parseInt(row.get("priceOfGuidePerWeek")));
    }
    error = "";
  }

  /**
   * @author Alexandre Chiasera
   */
  @Given("the following pieces of equipment exist in the system: \\(p1)")
  public void the_following_pieces_of_equipment_exist_in_the_system_p1(
      io.cucumber.datatable.DataTable dataTable) {
    var rows = dataTable.asMaps();

    for (var row : rows) {
      climbSafe.addEquipment(row.get("name"), Integer.parseInt(row.get("weight")),
          Integer.parseInt(row.get("pricePerWeek")));
    }
  }

  /**
   *
   * @author Haroun Guessous
   */
  @Given("the following equipment bundles exist in the system: \\(p1)")
  public void the_following_equipment_bundles_exist_in_the_system_p1(
      io.cucumber.datatable.DataTable dataTable) {
    var rows = dataTable.asMaps();

    for (var row : rows) {
      String nameBundle = row.get("name");
      int discount = Integer.parseInt(row.get("discount"));
      String bundleItems = row.get("items");
      String bundleItemQuantities = row.get("quantity");
      // create empty Bundle
      var newBundle = new EquipmentBundle(nameBundle, discount, climbSafe);

      List<Integer> quantities = new ArrayList<Integer>();
      List<BookableItem> bookableItems = new ArrayList<BookableItem>();

      for (var item : Arrays.asList(bundleItems.split(","))) {
        var existingItem = Equipment.getWithName(item);
        bookableItems.add(existingItem);
      }

      for (var itemsQuantity : Arrays.asList(bundleItemQuantities.split(","))) {
        var itemQuantity = Integer.parseInt(itemsQuantity);
        quantities.add(itemQuantity);
      }

      for (var equipment : climbSafe.getEquipment()) {
        for (int i = 0; i < bookableItems.size(); i++) {
          var existingEquipmentName = equipment.getName();
          var addedItemName = bookableItems.get(i).getName();
          var itemQuantity = quantities.get(i);
          if (existingEquipmentName.equals(addedItemName)) {
            // add already existing pieces of equipment to empty bundle
            newBundle.addBundleItem(itemQuantity, climbSafe, equipment);
          }
        }
      }
    }
  }


  /**
   * @author Asma Gandour
   */
  @When("the administator attempts to update the piece of equipment in the system with name {string} to have name {string}, weight {string}, and price per week {string} \\(p1)")
  public void the_administator_attempts_to_update_the_piece_of_equipment_in_the_system_with_name_to_have_name_weight_and_price_per_week_p1(
      String oldName, String newName, String newWeight, String newPricePerWeek) {
    callController(() -> ClimbSafeFeatureSet4Controller.updateEquipment(oldName, newName,
        Integer.parseInt(newWeight), Integer.parseInt(newPricePerWeek)));
  }

  /**
   *
   * @author Haroun Guessous
   */
  @Then("the number of pieces of equipment in the system shall be {string} \\(p1)")
  public void the_number_of_pieces_of_equipment_in_the_system_shall_be_p1(String string) {
    assertEquals(Integer.parseInt(string), climbSafe.getEquipment().size());
  }

  /**
   *
   * @author Alexandre Chiasera
   */
  @Then("the piece of equipment with name {string}, weight {string}, and price per week {string} shall not exist in the system \\(p1)")
  public void the_piece_of_equipment_with_name_weight_and_price_per_week_shall_not_exist_in_the_system_p1(
      String oldName, String oldWeight, String oldPricePerWeek) {
    Integer weight = Integer.parseInt(oldWeight);
    Integer pricePerWeek = Integer.parseInt(oldPricePerWeek);
    // for each piece of equipment in the climbSafe system (for the admin)
    for (Equipment equipment : climbSafe.getEquipment()) {
      if (equipment.getName().equals(oldName) && equipment.getWeight() == weight
          && equipment.getPricePerWeek() == pricePerWeek) {
        // if everything matches, it means the object is not updated correctly
        fail("The piece of equipment <" + equipment.getName()
            + "> has not been correctly updated by the system");
      }
    }
  }

  /**
   *
   * @author Alexandre Chiasera
   */
  @Then("the piece of equipment with name {string}, weight {string}, and price per week {string} shall exist in the system \\(p1)")
  public void the_piece_of_equipment_with_name_weight_and_price_per_week_shall_exist_in_the_system_p1(
      String newName, String newWeight, String newPricePerWeek) {
    String name = newName;
    Integer weight = Integer.parseInt(newWeight);
    Integer pricePerWeek = Integer.parseInt(newPricePerWeek);

    var equipment = (Equipment) Equipment.getWithName(name);
    // for each piece of equipment in the climbSafe system (for the admin)
    assertTrue(equipment.getName().equals(name) && equipment.getWeight() == weight
        && equipment.getPricePerWeek() == pricePerWeek);
  }

  /**
   * @author Atreyi Srivastava
   */
  @Then("the following pieces of equipment shall exist in the system: \\(p1)")
  public void the_following_pieces_of_equipment_shall_exist_in_the_system_p1(
      io.cucumber.datatable.DataTable dataTable) {
    var rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name");
      int weight = Integer.parseInt(row.get("weight"));
      int pricePerWeek = Integer.parseInt(row.get("pricePerWeek"));

      var equipment = (Equipment) BookableItem.getWithName(name);
      assertTrue(equipment.getName().equals(name) && equipment.getWeight() == weight
          && equipment.getPricePerWeek() == pricePerWeek);
    }
  }

  /**
   * @author Mohammad Shaheer Bilal
   */
  @Then("the system shall raise the error {string} \\(p1)")
  public void the_system_shall_raise_the_error_p1(String string) {
    assertEquals(string, error);
  }

  /**
   * 
   * @author Haroun Guessous
   */
  @Then("the number of equipment bundles in the system shall be {string} \\(p1)")
  public void the_number_of_equipment_bundles_in_the_system_shall_be_p1(String string) {
    assertEquals(Integer.parseInt(string), climbSafe.getBundles().size());
  }

  /**
   * @author Atreyi Srivastava
   */
  @Then("the following equipment bundles shall exist in the system: \\(p1)")
  public void the_following_equipment_bundles_shall_exist_in_the_system_p1(
      io.cucumber.datatable.DataTable dataTable) {
    var rows = dataTable.asMaps();
    for (var row : rows) {
      String nameBundle = row.get("name");
      int discount = Integer.parseInt(row.get("discount"));

      var bundle = (EquipmentBundle) BookableItem.getWithName(nameBundle);
      assertTrue(bundle.getName().equals(nameBundle) && bundle.getDiscount() == discount);
    }
  }

  private void callController(Executable executable) {
    try {
      executable.execute();
    } catch (InvalidInputException e) {
      error += e.getMessage();
    } catch (Throwable t) {
      fail();
    }
  }
}
