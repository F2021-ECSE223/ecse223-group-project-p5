package ca.mcgill.ecse.climbsafe.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet2Controller;
import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.BookedItem;
import ca.mcgill.ecse.climbsafe.model.BundleItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Member;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class P9StepDefinitions {
  private ClimbSafe climbSafe;
  private String error;


  /**
   * @param dataTable
   * @author KaraBest & JoeyKoay & VictorMicha
   */
  @Given("the following ClimbSafe system exists: \\(p9)")
  public void the_following_climb_safe_system_exists_p9(io.cucumber.datatable.DataTable dataTable) {
    error = "";
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    climbSafe = ClimbSafeApplication.getClimbSafe();
    for (Map<String, String> row : rows) {
      Date startDate = java.sql.Date.valueOf(row.get("startDate"));
      climbSafe.setStartDate(startDate);

      int nrWeeks = Integer.parseInt(row.get("nrWeeks"));
      climbSafe.setNrWeeks(nrWeeks);

      int priceOfGuidePerWeek = Integer.parseInt(row.get("priceOfGuidePerWeek"));
      climbSafe.setPriceOfGuidePerWeek(priceOfGuidePerWeek);
    }
  }


  /**
   * @param dataTable
   * @author EnzoBenoitJeannin & KaraBest & JoeyKoay & VictorMicha
   */
  @Given("the following equipment exists in the system: \\(p9)")
  public void the_following_equipment_exists_in_the_system_p9(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> r : rows) {
      String name = r.get("name");
      int weight = Integer.parseInt(r.get("weight"));
      int pricePerWeek = Integer.parseInt(r.get("pricePerWeek"));
      new Equipment(name, weight, pricePerWeek, this.climbSafe);
    }
  }

  /**
   * @param dataTable
   * @author EnzoBenoitJeannin & KaraBest & EunjunChang & JoeyKoay & VictorMicha & SejongYoon
   */
  @Given("the following equipment bundles exist in the system: \\(p9)")
  public void the_following_equipment_bundles_exist_in_the_system_p9(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    for (Map<String, String> r : rows) {
      String name = r.get("name");
      int discount = Integer.parseInt(r.get("discount"));
      EquipmentBundle bundle = new EquipmentBundle(name, discount, climbSafe);
      List<String> items = Arrays.asList(r.get("items").split(","));
      List<String> quantities = Arrays.asList(r.get("quantity").split(","));
      for (int i = 0; i < items.size(); i++) {
        new BundleItem(Integer.parseInt(quantities.get(i)), this.climbSafe, bundle,
            (Equipment) Equipment.getWithName(items.get(i)));
      }
    }
  }

  /**
   * @param dataTable
   * @author EnzoBenoitJeannin & KaraBest & JoeyKoay & VictorMicha
   */
  @Given("the following members exist in the system: \\(p9)")
  public void the_following_members_exist_in_the_system_p9(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> r : rows) {
      String email = r.get("email");
      String password = r.get("password");
      String name = r.get("name");
      String emergencyContact = r.get("emergencyContact");
      int nrWeeks = Integer.parseInt(r.get("nrWeeks"));
      List<String> bookableItems = Arrays.asList(r.get("bookableItems").split(","));
      List<String> requestedQuantities = Arrays.asList(r.get("requestedQuantities").split(","));
      boolean guideRequired = Boolean.parseBoolean(r.get("guideRequired"));
      boolean hotelRequired = Boolean.parseBoolean(r.get("hotelRequired"));
      Member m = new Member(email, password, name, emergencyContact, nrWeeks, guideRequired,
          hotelRequired, this.climbSafe);

      for (int i = 0; i < bookableItems.size(); i++) {
        BookableItem bookableItem = BookableItem.getWithName(bookableItems.get(i));
        m.addBookedItem(Integer.parseInt(requestedQuantities.get(i)), this.climbSafe, bookableItem);
      }
    }
  }

  /**
   * @param dataTable
   * @author EnzoBenoitJeannin & KaraBest & EunjunChang & JoeyKoay & VictorMicha & SejongYoon
   */
  @Given("the following guides exist in the system: \\(p9)")
  public void the_following_guides_exist_in_the_system_p9(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    List<Guide> guides = climbSafe.getGuides();
    List<String> emails = new ArrayList<String>();
    for (Guide g : guides)
      emails.add(g.getEmail());

    for (int i = 0; i < rows.size(); i++) {
      String email = rows.get(i).get("email");
      new Guide(email, rows.get(i).get("password"), rows.get(i).get("name"),
          rows.get(i).get("emergencyContact"), climbSafe);
    }
  }


  /**
   * @param email
   * @param password
   * @param name
   * @param emergencyContact
   * @param strnrWeeks
   * @param stritemNames
   * @param stritemQuantities
   * @param strguideRequired
   * @param strhotelRequired
   * @author KaraBest & JoeyKoay & VictorMicha
   */

  @When("a new member attempts to register with {string} , {string} , {string}, {string}, {string}, {string}, {string}, {string}, and {string} \\(p9)")
  public void a_new_member_attempts_to_register_with_and_p9(String email, String password,
      String name, String emergencyContact, String strNrWeeks, String strBookableItems,
      String strItemQuantities, String strGuideRequired, String strHotelRequired) {
    List<String> bookableItems = Arrays.asList(strBookableItems.split(","));
    List<Integer> itemQuantities = new ArrayList<Integer>();
    boolean guideRequired = Boolean.parseBoolean(strGuideRequired);
    boolean hotelRequired = Boolean.parseBoolean(strHotelRequired);
    int nrWeeks = Integer.parseInt(strNrWeeks);
    for (String s : strItemQuantities.split(","))
      itemQuantities.add(Integer.parseInt(s));

    try {
      ClimbSafeFeatureSet2Controller.registerMember(email, password, name, emergencyContact,
          nrWeeks, guideRequired, hotelRequired, bookableItems, itemQuantities);
    } catch (InvalidInputException e) {
      error = e.getMessage();
    }
  }

  /**
   * @param email
   * @param password
   * @param name
   * @param emergencyContact
   * @param nrWeeks
   * @param bookableItems
   * @param requestedQuantities
   * @param guideRequired
   * @param hotelRequired
   * @author KaraBest & JoeyKoay & VictorMicha
   */
  @Then("a new member account shall exist with {string}, {string}, {string}, {string}, {string}, {string}, {string}, {string}, and {string} \\(p9)")
  public void a_new_member_account_shall_exist_with_and_p9(String email, String password,
      String name, String emergencyContact, String nrWeeks, String bookableItems,
      String requestedQuantities, String guideRequired, String hotelRequired) {
    Member member = (Member) Member.getWithEmail(email);
    assertNotNull(member);
    assertEquals(password, member.getPassword());
    assertEquals(name, member.getName());
    assertEquals(emergencyContact, member.getEmergencyContact());
    assertEquals(Integer.parseInt(nrWeeks), member.getNrWeeks());
    assertEquals(Boolean.parseBoolean(guideRequired), member.getGuideRequired());
    assertEquals(Boolean.parseBoolean(hotelRequired), member.getHotelRequired());
    List<String> itemNames = Arrays.asList(bookableItems.split(","));
    List<BookedItem> bookableItemsList = member.getBookedItems();
    List<String> quantities = Arrays.asList(requestedQuantities.split(","));
    assertEquals(bookableItemsList.size(), itemNames.size());
    int index;
    for (BookedItem bItem : bookableItemsList) {
      String s = bItem.getItem().getName();
      assertTrue(itemNames.contains(s));
      index = itemNames.indexOf(s);
      assertEquals(Integer.parseInt(quantities.get(index)), bItem.getQuantity());
    }
  }

  /**
   * @param numMembers
   * @author KaraBest & JoeyKoay & VictorMicha
   */
  @Then("there are {int} members in the system. \\(p9)")
  public void there_are_members_in_the_system_p9(Integer numMembers) {
    assertEquals(numMembers, (Integer) climbSafe.numberOfMembers());
  }

  /**
   * @param errorString
   * @author KaraBest & JoeyKoay & VictorMicha
   */
  @Then("the following {string} shall be raised. \\(p9)")
  public void the_following_shall_be_raised_p9(String errorString) {
    assertTrue(error.contains(errorString));
  }

  /**
   * @param email
   * @author KaraBest & JoeyKoay & VictorMicha
   */
  @Then("there is no member account for {string} \\(p9)")
  public void there_is_no_member_account_for_p9(String email) {
    List<Member> members = climbSafe.getMembers();
    for (int i = 0; i < climbSafe.numberOfMembers(); i++) {
      assertNotEquals(email, members.get(i).getEmail());
    }
  }

}
