/*
 * written by:Ziyue Wang, Norman Kong, Alexander Koran, Saviru Perera, Farah Mehzabeen, Menad
 * Kessaci, Edwin You Zhou
 */

package ca.mcgill.ecse.climbsafe.features;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.controller.ClimbSafeFeatureSet7Controller;
import ca.mcgill.ecse.climbsafe.controller.InvalidInputException;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Hotel;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class P13StepDefinitions {

  private ClimbSafe climbSafe;
  private String error;

  /**
   * This method creates climbSafe instance.
   *
   * @author Ziyue Wang
   */

  @Given("the following ClimbSafe system exists: \\(p13)")
  public void the_following_climb_safe_system_exists_p13(
      io.cucumber.datatable.DataTable dataTable) {
    climbSafe = ClimbSafeApplication.getClimbSafe();

    String error = ""; // Initial error message set as empty


    List<Map<String, String>> rows = dataTable.asMaps();
    for (Map<String, String> columns : rows) {
      climbSafe.setStartDate(Date.valueOf(columns.get("startDate")));
      climbSafe.setPriceOfGuidePerWeek(Integer.parseInt(columns.get("priceOfGuidePerWeek")));
      climbSafe.setNrWeeks(Integer.parseInt(columns.get("nrWeeks")));
    }
  }

  /**
   * This method create Hotel instances in climbSafe to construct environment for testing.
   *
   * @author Ziyue Wang
   */

  @Given("the following hotels exist in the system: \\(p13)")
  public void the_following_hotels_exist_in_the_system_p13(
      io.cucumber.datatable.DataTable dataTable) {

    List<Map<String, String>> rows = dataTable.asMaps();
    // get data and create Hotel
    for (Map<String, String> columns : rows) {
      if (Hotel.getWithName(columns.get("name")) == null) {
        climbSafe.addHotel(columns.get("name"), columns.get("address"),
            toRating(columns.get("nrStars")));
      }
    }
  }

  /**
   * This method attemps to create new Hotel instances by calling controller.
   *
   * @param name, address, numStars
   * @author Norman Kong, Alexander Koran
   */

  @When("the admin attempts to add a new hotel to the system with {string}, {string}, and {string} \\(p13)")
  public void the_admin_attempts_to_add_a_new_hotel_to_the_system_with_and_p13(String name,
      String address, String numStars) {
    callController(
        () -> ClimbSafeFeatureSet7Controller.addHotel(name, address, Integer.parseInt(numStars)));
  }

  /**
   * This method asserts new hotel has been succesfully added to climbSafe and find corresponding.
   * hotel instance by its name, validate with its name, address rating params
   *
   * @author Ziyue Wang, Norman Kong, Alexander Koran, Farah Mehzabeen
   * @params name, address, numStars
   */

  @Then("the hotel with {string}, {string}, and {string} shall now exist in the system \\(p13)")
  public void the_hotel_with_and_shall_now_exist_in_the_system_p13(String name, String address,
      String numStars) {
    Hotel current = Hotel.getWithName(name);
    assertNotNull(current);
    assertEquals(current.getAddress(), address);
    assertEquals(current.getRating(), toRating(numStars));// Get the hotel using hotel by name
                                                          // method
                                                          // Next test if its null and whether the
                                                          // address and number of stars is correct
  }

  /**
   * This method make sure the hotel is linked to climbsafe by validating hotel numbers in
   * climbsafe.
   *
   * @author Ziyue Wang
   */

  @Then("the number of hotels in the system is {int} \\(p13)")
  public void the_number_of_hotels_in_the_system_is_p13(Integer int1) {
    // use.getHotels()to get list of Hotel and it's size
    assertTrue(climbSafe.getHotels().size() == int1);
  }

  /**
   * This method asserts tthe correct error message is raised.
   *
   * @param string supposed error message
   * @author Ziyue Wang, Edwin
   */

  @Then("the following {string} shall be raised \\(p13)")
  public void the_following_shall_be_raised_p13(String string) {
    assertEquals(string, error);
  }

  /**
   * This method asserts that new hotel not successfully added are not in climbsafe and old hotel
   * still exists.
   * 
   * @param string,string2
   * @author Ziyue Wang, Edwin, Farah Mehzabeen
   */

  @Then("the hotel with {string} shall {string} in the system \\(p13)")
  public void the_hotel_with_shall_in_the_system_p13(String hotelName, String target) {
    if (Hotel.getWithName(hotelName) != null) {
      assertEquals(target, "exist");
    } else {
      assertEquals(target, "not exist");
    }
  }

  /**
   * This methods attemps to update an existing hotel.
   * 
   * @param newRating
   * @param newName
   * @param newAddress
   * @param oldName
   * @author Ziyue Wang, Edwin
   */

  @When("the admin attempts to update hotel with {string} in the system to have a {string}, {string}, and {string} \\(p13)")
  public void the_admin_attempts_to_update_hotel_with_in_the_system_to_have_a_and_p13(
      String oldName, String newName, String newAddress, String newRating) {

    callController(() -> ClimbSafeFeatureSet7Controller.updateHotel(oldName, newName, newAddress,
        Integer.parseInt(newRating))); // Executable, calls outer method first
  }

  /**
   * This methods asserts updated hotel have corresbonding name, address, rating.
   *
   * @param newAddress
   * @param newName
   * @param newRating
   * @author Ziyue Wang
   */

  @Then("the hotel will be updated to have a {string}, {string}, and {string} \\(p13)")
  public void the_hotel_will_be_updated_to_have_a_and_p13(String newName, String newAddress,
      String newRating) {
    Hotel hotel = Hotel.getWithName(newName);
    assertEquals(hotel.getAddress(), newAddress);
    assertEquals(toRating(newRating), hotel.getRating());
  }

  /**
   * This method asserts hotel info are not changed after unsuccessful update.
   *
   * @param oldAddress
   * @param oldName
   * @param oldRating
   * @author Ziyue Wang
   */

  @Then("the hotel will keep its {string}, {string}, and {string} \\(p13)")
  public void the_hotel_will_keep_its_and_p13(String oldName, String oldAddress, String oldRating) {
    Hotel hotel = Hotel.getWithName(oldName);
    assertEquals(hotel.getName(), oldName);
    assertEquals(hotel.getAddress(), oldAddress);
    assertEquals(hotel.getRating(), toRating(oldRating));
  }

  /**
   * This method validates raised error.
   *
   * @param string supposed error
   * @author Ziyue Wang
   */

  @Then("the following error {string} shall be raised \\(p13)")
  public void the_following_error_shall_be_raised_p13(String string) {
    Assertions.assertEquals(error, string);
  }

  /**
   * This method provide trying calling controller and catching error.
   *
   * @param executable
   * @Author Ziyue Wang, Farah Mehzabeen
   */
  public void callController(Executable executable) {
    try { // try executing the function
      executable.execute();
    } catch (InvalidInputException e) {
      error = e.getMessage(); // in case an error occurs, store the message and increment
    } catch (Throwable t) {
      fail();
    }

  }

  /**
   * This method provides quick conversion from int to rating type.
   *
   * @param numStars
   * @return
   * @Author Given by TAYounesB
   */
  private static Hotel.HotelRating toRating(String numStars) {
    return toRating(Integer.parseInt(numStars));
  }

  private static Hotel.HotelRating toRating(int numStars) {
    return Hotel.HotelRating.values()[numStars - 1];
  }
}
