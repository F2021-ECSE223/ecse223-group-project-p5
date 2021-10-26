package ca.mcgill.ecse.climbsafe.controller;

import java.util.Iterator;
import java.util.List;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.model.User;

/**
 * Controller for register member and update member.
 * 
 * @author Jimmy Sheng
 *
 */
public class ClimbSafeFeatureSet2Controller {

  // reference to ClimbSafe
  private static ClimbSafe cs = ClimbSafeApplication.getClimbSafe();

  // controller should not be instantiated
  private ClimbSafeFeatureSet2Controller() {}

  /**
   * Validate given parameters and register a member if valid.
   * 
   * @author Jimmy Sheng
   * @param email
   * @param password
   * @param name
   * @param emergencyContact
   * @param nrWeeks
   * @param guideRequired
   * @param hotelRequired
   * @param itemNames
   * @param itemQuantities
   * @throws InvalidInputException
   */
  public static void registerMember(String email, String password, String name,
      String emergencyContact, int nrWeeks, boolean guideRequired, boolean hotelRequired,
      List<String> itemNames, List<Integer> itemQuantities) throws InvalidInputException {
    /*
     * validate email
     */
    if (email == null) {
      throw new InvalidInputException("The email cannot be empty");
    } else if (email.contains(" ")) {
      throw new InvalidInputException("The email must not contain any spaces");
    } else if (!(email.indexOf("@") > 0)) {
      invalidEmailFormat();
    } else if (!(email.indexOf("@") == email.lastIndexOf("@"))) {
      invalidEmailFormat();
    } else if (!(email.indexOf("@") < email.lastIndexOf(".") - 1)) {
      invalidEmailFormat();
    } else if (!(email.lastIndexOf(".") < email.length() - 1)) {
      invalidEmailFormat();
    } else if (email.equals("admin@nmc.nt")) {
      throw new InvalidInputException("The email entered is not allowed for members");
    }
    /*
     * check if email doesn't belong to existing guide or member umple check this anyway but this
     * prints the expected error message
     */
    var usr = User.getWithEmail(email);
    if (usr != null) {
      if (usr instanceof Guide) {
        throw new InvalidInputException("A guide with this email already exists");
      } else if (usr instanceof Member) {
        throw new InvalidInputException("A member with this email already exists");
      }
    }
    /*
     * more validations
     */
    validatePassword(password);
    validateName(name);
    validateEmergencyContact(emergencyContact);
    validateNrWeeks(nrWeeks);
    validateItems(itemNames, itemQuantities);
    /*
     * create the member
     */
    try {
      var newMember = cs.addMember(email, password, name, emergencyContact, nrWeeks, guideRequired,
          hotelRequired);
      // book items for the member
      try {
        bookItems(newMember, itemNames, itemQuantities);
      } catch (InvalidInputException e) {
        // if exist invalid item, undo creation of the member
        newMember.delete();
        throw new InvalidInputException(e.getMessage());
      }
    } catch (RuntimeException e) {
      throw new InvalidInputException(e.getMessage());
    }
  }

  /**
   * Check if email correspond to a registered member. Validate parameters and update the member
   * accordingly.
   * 
   * @author Jimmy Sheng
   * @param email
   * @param newPassword
   * @param newName
   * @param newEmergencyContact
   * @param newNrWeeks
   * @param newGuideRequired
   * @param newHotelRequired
   * @param newItemNames
   * @param newItemQuantities
   * @throws InvalidInputException
   */
  public static void updateMember(String email, String newPassword, String newName,
      String newEmergencyContact, int newNrWeeks, boolean newGuideRequired,
      boolean newHotelRequired, List<String> newItemNames, List<Integer> newItemQuantities)
      throws InvalidInputException {
    /*
     * check email
     */
    if (email == null) {
      throw new InvalidInputException("The email cannot be empty");
    }
    // check if member exists
    var usr = User.getWithEmail(email);
    if (usr == null) {
      throw new InvalidInputException("Member not found");
    } else if (!(usr instanceof Member)) {
      throw new InvalidInputException("Member not found");
    }
    var member = (Member) usr;
    /*
     * validations
     */
    validatePassword(newPassword);
    validateName(newName);
    validateEmergencyContact(newEmergencyContact);
    validateNrWeeks(newNrWeeks);
    validateItems(newItemNames, newItemQuantities);
    /*
     * update password, name, emergencyContact, nrWeeks, guide, hotel
     */
    member.setPassword(newPassword);
    member.setName(newName);
    member.setEmergencyContact(newEmergencyContact);
    member.setNrWeeks(newNrWeeks);
    member.setGuideRequired(newGuideRequired);
    member.setHotelRequired(newHotelRequired);
    /*
     * remove old booked items
     */
    while (member.hasBookedItems()) {
      member.getBookedItem(0).delete();
    }
    /*
     * update items
     */
    try {
      bookItems(member, newItemNames, newItemQuantities);
    } catch (InvalidInputException e) {
      throw new InvalidInputException(e.getMessage());
    }
  }

  /**
   * Helper method for invalid email formats
   * 
   * @throws InvalidInputException
   */
  private static void invalidEmailFormat() throws InvalidInputException {
    throw new InvalidInputException("Invalid email");
  }

  /**
   * Helper method for validating password
   * 
   * @param psw
   * @throws InvalidInputException
   */
  private static void validatePassword(String psw) throws InvalidInputException {
    if (psw == null) {
      throw new InvalidInputException("The password cannot be empty");
    } else if (psw.contains(" ")) {
      throw new InvalidInputException("The password must not contain any spaces");
    } else if (psw.equals("")) {
      throw new InvalidInputException("The password cannot be empty");
    }
  }

  /**
   * Helper method for validating name
   * 
   * @param name
   * @throws InvalidInputException
   */
  private static void validateName(String name) throws InvalidInputException {
    if (name == null) {
      throw new InvalidInputException("The name cannot be empty");
    } else if (name.equals("")) {
      throw new InvalidInputException("The name cannot be empty");
    }
  }

  /**
   * Helper method for validating emergency contact
   * 
   * @param contact
   * @throws InvalidInputException
   */
  private static void validateEmergencyContact(String contact) throws InvalidInputException {
    if (contact == null) {
      throw new InvalidInputException("The emergency contact cannot be empty");
    } else if (contact.equals("")) {
      throw new InvalidInputException("The emergency contact cannot be empty");
    }
  }

  /**
   * Helper method for validating number of weeks
   * 
   * @param nrWeeks
   * @throws InvalidInputException
   */
  private static void validateNrWeeks(int nrWeeks) throws InvalidInputException {
    if (!(nrWeeks > 0 && nrWeeks <= cs.getNrWeeks())) {
      throw new InvalidInputException(
          "The number of weeks must be greater than zero and less than or equal to the number of climbing weeks in the climbing season");
    }
  }

  /**
   * Helper method for validating list of item names and list of quantities
   * 
   * @param itemNames
   * @param itemQuantities
   * @throws InvalidInputException
   */
  private static void validateItems(List<String> itemNames, List<Integer> itemQuantities)
      throws InvalidInputException {
    if (itemNames == null) {
      throw new InvalidInputException("A list of items must be specified.");
    }
    if (itemQuantities == null) {
      throw new InvalidInputException("A list of item quantities must be specified.");
    }
    if (itemNames.size() != itemQuantities.size()) {
      throw new InvalidInputException("Length of list of item and item quantities doesn't match.");
    }
  }

  /**
   * Book the items in the list for the member
   * 
   * @param member
   * @param itemNames
   * @param itemQuantities
   */
  private static void bookItems(Member member, List<String> itemNames, List<Integer> itemQuantities)
      throws InvalidInputException {
    Iterator<String> itemIter = itemNames.iterator();
    Iterator<Integer> qtyIter = itemQuantities.iterator();
    while (itemIter.hasNext() && qtyIter.hasNext()) {
      String item = itemIter.next();
      Integer qty = qtyIter.next();
      // only add item when quantity is larger than 0
      if (qty > 0) {
        var bookedItem = BookableItem.getWithName(item);
        // check if item exists as a BookableItem
        if (bookedItem != null) {
          cs.addBookedItem(qty, member, bookedItem);
        } else {
          throw new InvalidInputException("Requested item not found");
        }
      }
    }
  }

}
