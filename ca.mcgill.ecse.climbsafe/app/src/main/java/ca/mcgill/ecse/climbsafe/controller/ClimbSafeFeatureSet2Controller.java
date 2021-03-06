package ca.mcgill.ecse.climbsafe.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.model.User;
import ca.mcgill.ecse.climbsafe.persistence.ClimbSafePersistence;

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
     * check if email doesn't belong to existing guide or member and print the expected error
     * messages
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
     * create the member, handle umple's exception if somehow member still fails to create after all
     * checks
     */
    try {
      var newMember = cs.addMember(email, password, name, emergencyContact, nrWeeks, guideRequired,
          hotelRequired);
      // book items for the member
      bookItems(newMember, itemNames, itemQuantities);
      // persistence save
      ClimbSafePersistence.save();
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
    // persistence save
    try {
      ClimbSafePersistence.save();
    } catch (RuntimeException e) {
      throw new InvalidInputException(e.getMessage());
    }
    /*
     * remove old booked items
     */
    while (member.hasBookedItems()) {
      member.getBookedItem(0).delete();
    }
    /*
     * update items
     */
    bookItems(member, newItemNames, newItemQuantities);
    // persistence save
    try {
      ClimbSafePersistence.save();
    } catch (RuntimeException e) {
      throw new InvalidInputException(e.getMessage());
    }
  }

  /**
   * Getter for a list of members
   * 
   * @return ArrayList containing all TO members in the model
   * @author Jimmy Sheng
   */
  public static ArrayList<TOMember> getMembers() {
    var members = new ArrayList<TOMember>();
    for (var member : cs.getMembers()) {
      members.add(new TOMember(member.getEmail(), member.getPassword(), member.getName(),
          member.getEmergencyContact(), member.getNrWeeks(), member.getGuideRequired(),
          member.getHotelRequired(), member.getBanStatusFullName()));
    }
    // by default, sort by name
    Collections.sort(members,
        Comparator.comparing((TOMember member) -> member.getName(), String.CASE_INSENSITIVE_ORDER));
    return members;
  }
  
  /**
   * Getter for a single member
   * 
   * @param email identifying the target member
   * @return null if email does not exist, TOMember with data otherwise
   * @author Michael Grieco
   */
  public static TOMember getMember(String email) throws InvalidInputException {
    var usr = Member.getWithEmail(email);
    if (usr == null) {
      throw new InvalidInputException("Member not found");
    } else if (!(usr instanceof Member)) {
      throw new InvalidInputException("Member not found");
    }
    
    var retMember = (Member)usr;
    
    return new TOMember(email, retMember.getPassword(),
        retMember.getName(), retMember.getEmergencyContact(),
        retMember.getNrWeeks(),
        retMember.getGuideRequired(), retMember.getHotelRequired(),
        retMember.getBanStatusFullName());
  }

  /**
   * Getter for a list of booked items for the member with the given email
   * 
   * @param memberEmail
   * @return ArrayList
   * @throws InvalidInputException if email doesn't link to a valid member
   * @author Jimmy Sheng
   */
  public static ArrayList<TOBookedItem> getBookedItems(String memberEmail)
      throws InvalidInputException {
    // check if member exists
    var usr = User.getWithEmail(memberEmail);
    if (usr == null) {
      throw new InvalidInputException("Member not found");
    } else if (!(usr instanceof Member)) {
      throw new InvalidInputException("Member not found");
    }
    var member = (Member) usr;
    var bookedItems = new ArrayList<TOBookedItem>();
    for (var item : member.getBookedItems()) {
      bookedItems.add(new TOBookedItem(item.getQuantity(), memberEmail, item.getItem().getName()));
    }
    // by default, sort by item name
    Collections.sort(bookedItems, Comparator.comparing(
        (TOBookedItem item) -> item.getBookableItemName(), String.CASE_INSENSITIVE_ORDER));
    return bookedItems;
  }

  /**
   * Getter for a list of all booked items in the model
   * 
   * @return ArrayList
   * @author Jimmy Sheng
   */
  public static ArrayList<TOBookedItem> getBookedItems() {
    var bookedItems = new ArrayList<TOBookedItem>();
    for (var item : cs.getBookedItems()) {
      bookedItems.add(new TOBookedItem(item.getQuantity(), item.getMember().getEmail(),
          item.getItem().getName()));
    }
    // by default, sort by item name
    Collections.sort(bookedItems, Comparator.comparing(
        (TOBookedItem item) -> item.getBookableItemName(), String.CASE_INSENSITIVE_ORDER));
    return bookedItems;
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
    } else if (name.trim().equals("")) {
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
    } else if (contact.trim().equals("")) {
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
      throw new InvalidInputException("A list of items must be specified");
    }
    if (itemQuantities == null) {
      throw new InvalidInputException("A list of item quantities must be specified");
    }
    if (itemNames.size() != itemQuantities.size()) {
      throw new InvalidInputException("Length of list of item and item quantities doesn't match");
    }
    for (var item : itemNames) {
      if (BookableItem.getWithName(item) == null) {
        throw new InvalidInputException("Requested item not found");
      }
    }
  }

  /**
   * Book the items in the list for the member. Assuming the inputs have already been validated.
   * 
   * @param member
   * @param itemNames
   * @param itemQuantities
   */
  private static void bookItems(Member member, List<String> itemNames,
      List<Integer> itemQuantities) {
    Iterator<String> itemIter = itemNames.iterator();
    Iterator<Integer> qtyIter = itemQuantities.iterator();
    while (itemIter.hasNext() && qtyIter.hasNext()) {
      String item = itemIter.next();
      Integer qty = qtyIter.next();
      cs.addBookedItem(qty, member, BookableItem.getWithName(item));
    }
  }

}
