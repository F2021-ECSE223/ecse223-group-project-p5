package ca.mcgill.ecse.climbsafe.features;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import java.sql.Date;
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
import ca.mcgill.ecse.climbsafe.model.Member;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;


public class P15StepDefinitions {

  private ClimbSafe climbSafe;
  private String error;

  /**
   * @param dataTable
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @Given("the following ClimbSafe system exists: \\(p15)")
  public void the_following_climb_safe_system_exists_p15(
      io.cucumber.datatable.DataTable dataTable) {
    this.error = null;

    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
    this.climbSafe = ClimbSafeApplication.getClimbSafe();
    for (Map<String, String> row : rows) {

      Date startDate = java.sql.Date.valueOf(row.get("startDate"));
      int nrWeeks = Integer.parseInt(row.get("nrWeeks"));
      int weeklyGuidePrice = Integer.parseInt(row.get("priceOfGuidePerWeek"));

      this.climbSafe.setStartDate(startDate);
      this.climbSafe.setNrWeeks(nrWeeks);
      this.climbSafe.setPriceOfGuidePerWeek(weeklyGuidePrice);
    }
  }

  /**
   * @param dataTable
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @Given("the following equipment exists in the system: \\(p15)")
  public void the_following_equipment_exists_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row : rows) {
      String name = row.get("name");
      int weight = Integer.parseInt(row.get("weight"));
      int weeklyPrice = Integer.parseInt(row.get("pricePerWeek"));

      new Equipment(name, weight, weeklyPrice, this.climbSafe);
    }
  }

  /**
   * @param dataTable
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @Given("the following equipment bundles exist in the system: \\(p15)")
  public void the_following_equipment_bundles_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row : rows) {
      String name = row.get("name");
      int discount = Integer.parseInt(row.get("discount"));
      List<Integer> quantity = Arrays.stream(row.get("quantity").split(",")).map(String::trim)
          .mapToInt(Integer::parseInt).boxed().toList();
      List<String> items = Arrays.asList(row.get("items").split(","));

      EquipmentBundle equipmentBundle = new EquipmentBundle(name, discount, this.climbSafe);

      for (int i = 0; i < items.size(); i++) {
        new BundleItem(quantity.get(i), this.climbSafe, equipmentBundle,
            (Equipment) Equipment.getWithName(items.get(i)));
      }
    }
  }

  /**
   * @param dataTable
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @Given("the following members exist in the system: \\(p15)")
  public void the_following_members_exist_in_the_system_p15(
      io.cucumber.datatable.DataTable dataTable) {
    List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

    for (Map<String, String> row : rows) {

      String email = row.get("email");
      String name = row.get("name");
      String password = row.get("password");
      String emergencyContact = row.get("emergencyContact");
      int nrWeeks = Integer.parseInt(row.get("nrWeeks"));
      boolean guideRequired = Boolean.parseBoolean(row.get("guideRequired"));
      boolean hotelRequired = Boolean.parseBoolean(row.get("hotelRequired"));
      List<Integer> requestedQuantities = Arrays.asList(row.get("requestedQuantities").split(","))
          .stream().map(String::trim).mapToInt(Integer::parseInt).boxed().toList();
      List<String> bookableItems = Arrays.asList(row.get("bookableItems").split(","));

      Member member = new Member(email, password, name, emergencyContact, nrWeeks, guideRequired,
          hotelRequired, this.climbSafe);

      for (int i = 0; i < bookableItems.size(); i++) {
        BookableItem bookableItem = BookableItem.getWithName(bookableItems.get(i));
        member.addBookedItem(requestedQuantities.get(i), this.climbSafe, bookableItem);
      }

    }

  }

  /**
   * @param email
   * @param newPassword
   * @param newName
   * @param newEmergencyContact
   * @param newNrWeeks
   * @param newBookableItems
   * @param newRequestedQuantities
   * @param newGuideRequired
   * @param newHotelRequired
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @When("the member with {string} attempts to update their account with {string}, {string}, {string}, {string}, {string}, {string}, {string}, and {string} \\(p15)")
  public void the_member_with_attempts_to_update_their_account_with_and_p15(String email,
      String newPassword, String newName, String newEmergencyContact, String newNrWeeks,
      String newBookableItems, String newRequestedQuantities, String newGuideRequired,
      String newHotelRequired) {
    List<String> itemNames = Arrays.asList(newBookableItems.split(","));
    List<Integer> itemQuantities = Arrays.stream(newRequestedQuantities.split(","))
        .map(String::trim).mapToInt(Integer::parseInt).boxed().toList();

    try {
      ClimbSafeFeatureSet2Controller.updateMember(email, newPassword, newName, newEmergencyContact,
          Integer.parseInt(newNrWeeks), Boolean.parseBoolean(newGuideRequired),
          Boolean.parseBoolean(newHotelRequired), itemNames, itemQuantities);
    } catch (InvalidInputException e) {
      error = e.getMessage();
    }
  }

  /**
   * @param email
   * @param newPassword
   * @param newName
   * @param newEmergencyContact
   * @param newNrWeeks
   * @param newBookableItems
   * @param newRequestedQuantities
   * @param newGuideRequired
   * @param newHotelRequired
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @Then("the member account with {string} shall be updated with {string}, {string}, {string}, {string}, {string}, {string}, {string}, and {string} \\(p15)")
  public void the_member_account_with_shall_be_updated_with_and_p15(String email,
      String newPassword, String newName, String newEmergencyContact, String newNrWeeks,
      String newBookableItems, String newRequestedQuantities, String newGuideRequired,
      String newHotelRequired) {
    Member member = (Member) Member.getWithEmail(email);
    assertNotNull(member);
    assertEquals(newPassword, member.getPassword());
    assertEquals(newName, member.getName());
    assertEquals(newEmergencyContact, member.getEmergencyContact());
    assertEquals(Integer.parseInt(newNrWeeks), member.getNrWeeks());
    assertEquals(Boolean.parseBoolean(newGuideRequired), member.getGuideRequired());
    assertEquals(Boolean.parseBoolean(newHotelRequired), member.getHotelRequired());


    List<Integer> actualQuantities = Arrays.stream(newRequestedQuantities.split(","))
        .map(String::trim).mapToInt(Integer::parseInt).boxed().toList();
    List<String> expectedItems = Arrays.asList(newBookableItems.split(","));

    // check member booked item size
    List<BookedItem> memberItems = member.getBookedItems();
    assertEquals(expectedItems.size(), memberItems.size());

    // Check if item assigned to the member exists with the right quantity as specified by the input
    // string
    for (BookedItem memberItem : memberItems) {
      assertTrue(expectedItems.contains(memberItem.getItem().getName()));
      int index = expectedItems.indexOf(memberItem.getItem().getName());
      assertEquals((Integer) memberItem.getQuantity(), actualQuantities.get(index));
    }
  }

  /**
   * @param expectedMemberNum expected number of members
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @Then("there are {int} members in the system. \\(p15)")
  public void there_are_members_in_the_system_p15(Integer expectedMemberNum) {
    assertEquals(expectedMemberNum, (Integer) this.climbSafe.numberOfMembers());
  }

  /**
   * @param expectedError expected error
   * @author Yazdan Zinati, Ze Yuan Fu, Michelle Lee, Enzo Calcagno, Magnus Gao, Elie Abdo
   */
  @Then("the following {string} shall be raised. \\(p15)")
  public void the_following_shall_be_raised_p15(String expectedError) {
    assertEquals(expectedError, error);
  }
}
