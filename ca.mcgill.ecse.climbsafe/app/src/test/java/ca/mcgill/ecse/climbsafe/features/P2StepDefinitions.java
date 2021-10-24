package ca.mcgill.ecse.climbsafe.features;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet5Controller;
import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.BundleItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class P2StepDefinitions {

  private ClimbSafe climbSafe;
  private String error = "";

  // @author everyone
  @Given("the following ClimbSafe system exists: \\(p2)")
  public void the_following_climb_safe_system_exists_p2(io.cucumber.datatable.DataTable dataTable) {
    climbSafe = ClimbSafeApplication.getClimbSafe();
    error = "";
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      climbSafe.setStartDate(Date.valueOf(row.get("startDate")));
      climbSafe.setNrWeeks(Integer.parseInt(row.get("nrWeeks")));
      climbSafe.setPriceOfGuidePerWeek(Integer.parseInt(row.get("priceOfGuidePerWeek")));
    }
  }

  // @author Danny Tu
  @Given("the following equipment exists in the system: \\(p2)")
  public void the_following_equipment_exists_in_the_system_p2(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      String name = row.get("name");
      int weight = Integer.parseInt(row.get("weight"));
      int pricePerWeek = Integer.parseInt(row.get("pricePerWeek"));
      climbSafe.addEquipment(name, weight, pricePerWeek);
    }
  }

  // @author Salim Benchekroun
  @When("the administrator attempts to add an equipment bundle with name {string}, discount {string}, items {string}, and quantities {string} \\(p2)")
  public void the_administrator_attempts_to_add_an_equipment_bundle_with_name_discount_items_and_quantities_p2(
      String name, String discountString, String items, String quantities) {
    int discount = Integer.parseInt(discountString);
    List<Integer> quantityList = new ArrayList<Integer>();
    List<String> itemList = new ArrayList<String>();
    if (!items.isEmpty() && !quantities.isEmpty()) {
      itemList = Arrays.asList(items.split(","));
      for (String quantity : quantities.split(",")) {
        quantityList.add(Integer.parseInt(quantity));
      }
    }
    try {
      ClimbSafeFeatureSet5Controller.addEquipmentBundle(name, discount, itemList, quantityList);
    } catch (InvalidInputException e) {
      error = e.getMessage();
    }
  }

  // @author Arturo Mory Ramirez
  @Then("the number of equipment bundles in the system shall be {string} \\(p2)")
  public void the_number_of_equipment_bundles_in_the_system_shall_be_p2(String string) {
    assertEquals(Integer.parseInt(string), climbSafe.numberOfBundles());
  }

  // @author Jian Long (Noah) Ye
  @Then("the equipment bundle {string} shall exist in the system \\(p2)")
  public void the_equipment_bundle_shall_exist_in_the_system_p2(String string) {
    EquipmentBundle Bundle = (EquipmentBundle) EquipmentBundle.getWithName(string);
    assertNotNull(Bundle);
  }

  // @author Runge (Karen) Fu
  @Then("the equipment bundle {string} shall contain the items {string} with quantities {string} \\(p2)")
  public void the_equipment_bundle_shall_contain_the_items_with_quantities_p2(String name,
      String items, String quantities) {
    EquipmentBundle bundle = (EquipmentBundle) EquipmentBundle.getWithName(name);
    List<String> itemList = new ArrayList<String>();
    List<Integer> quantityList = new ArrayList<Integer>();
    if (!items.isEmpty() && !quantities.isEmpty()) {
      itemList = Arrays.asList(items.split(","));
      for (String quantity : quantities.split(",")) {
        quantityList.add(Integer.parseInt(quantity));
      }
    }
    int n = 0;
    for (BundleItem i : bundle.getBundleItems()) {
      Assert.assertEquals(itemList.get(n), i.getEquipment().getName());
      Assertions.assertEquals(quantityList.get(n), i.getQuantity());
      n += 1;
    }
  }

  // @author everyone
  @Then("the equipment bundle {string} shall have a discount of {string} \\(p2)")
  public void the_equipment_bundle_shall_have_a_discount_of_p2(String name, String discount) {
    EquipmentBundle bundle = (EquipmentBundle) BookableItem.getWithName(name);
    assertEquals(Integer.parseInt(discount), bundle.getDiscount());
  }

  // @author Peini Cheng
  @Then("the error {string} shall be raised \\(p2)")
  public void the_error_shall_be_raised_p2(String string) {
    assertTrue(error.startsWith(string));
  }

  // @author Peini Cheng
  @Given("the following equipment bundles exist in the system: \\(p2)")
  public void the_following_equipment_bundles_exist_in_the_system_p2(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps();
    for (var row : rows) {
      EquipmentBundle bundle =
          climbSafe.addBundle(row.get("name"), Integer.parseInt(row.get("discount")));
      String items = row.get("items");
      String quantities = row.get("quantities");
      List<String> itemList = Arrays.asList(items.split(","));
      List<String> quantityList = Arrays.asList(quantities.split(","));
      for (int i = 0; i < itemList.size(); i++) {
        if (Equipment.hasWithName(itemList.get(i))
            && Equipment.getWithName(itemList.get(i)) instanceof Equipment) {
          BundleItem bundleItem = bundle.addBundleItem(Integer.parseInt(quantityList.get(i)),
              climbSafe, (Equipment) Equipment.getWithName(itemList.get(i)));
          bundle.addBundleItem(bundleItem);
        }
      }
    }
  }
}
