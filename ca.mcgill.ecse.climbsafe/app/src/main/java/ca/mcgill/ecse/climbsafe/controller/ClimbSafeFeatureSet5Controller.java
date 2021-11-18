package ca.mcgill.ecse.climbsafe.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import ca.mcgill.ecse.climbsafe.persistence.ClimbSafePersistence;


public class ClimbSafeFeatureSet5Controller {

  /**
   * Method to add a instance of equipment bundle to the application.
   * 
   * @author Annie Kang October 26th, 2021
   * @param name
   * @param discount
   * @param equipmentNames
   * @param equipmentQuantities
   * @throws InvalidInputException
   */
  private static ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();

  public static void addEquipmentBundle(String name, int discount, List<String> equipmentNames,
      List<Integer> equipmentQuantities) throws InvalidInputException {


    /**
     * validation for quantity, discount, and name
     */
    validate_equipmentQuantities(equipmentQuantities);
    validate_discount(discount);
    validate_name(name);


    /**
     * validation check for List<String> equipmentNames
     */
    // List<Equipment> existing_equipment= climbSafe.getEquipment();

    // does not exist equipment name
    for (String itemName : equipmentNames) {
      if (Equipment.getWithName(itemName) == null) {
        throw new InvalidInputException("Equipment " + itemName + " does not exist");
      }
    }

    // at least 2 distinct equipment
    if (equipmentNames.size() <= 1) {
      throw new InvalidInputException(
          "Equipment bundle must contain at least two distinct types of equipment");
    }

    // repeating equipment
    List<String> temp = new ArrayList<String>();
    // temp.add(equipmentNames.get(0));
    for (String eq_name : equipmentNames) {
      if (temp.contains(eq_name)) {
        throw new InvalidInputException(
            "Equipment bundle must contain at least two distinct types of equipment");
      } else {
        temp.add(eq_name);
        // persistence save
        try {
          ClimbSafePersistence.save();
        } catch (RuntimeException e) {
          throw new InvalidInputException(e.getMessage());
        }
      }
    }

    /**
     * validate to add equipment bundle and bundle items
     */
    EquipmentBundle new_added_bundle = climbSafe.addBundle(name, discount);
    bundleItems(new_added_bundle, equipmentNames, equipmentQuantities);

  }

  /**
   * @author Annie Kang October 20th, 2021
   * 
   * @param oldName
   * @param newName
   * @param newDiscount
   * @param newEquipmentNames
   * @param newEquipmentQuantities
   * @throws InvalidInputException
   */
  public static void updateEquipmentBundle(String oldName, String newName, int newDiscount,
      List<String> newEquipmentNames, List<Integer> newEquipmentQuantities)
      throws InvalidInputException {

    /**
     * validation check for List<String> equipmentNames
     */

    // at least 2 distinct equipment
    if (newEquipmentNames.size() <= 1) {
      throw new InvalidInputException(
          "Equipment bundle must contain at least two distinct types of equipment");
    }

    // repeating equipment
    List<String> temp = new ArrayList<String>();
    for (String eq_name : newEquipmentNames) {
      if (temp.contains(eq_name)) {
        throw new InvalidInputException(
            "Equipment bundle must contain at least two distinct types of equipment");
      } else {
        temp.add(eq_name);
        // persistence save
        try {
          ClimbSafePersistence.save();
        } catch (RuntimeException e) {
          throw new InvalidInputException(e.getMessage());
        }
      }
    }

    // does not exist equipment name
    for (String itemName : newEquipmentNames) {
      if (BookableItem.getWithName(itemName) == null) {
        throw new InvalidInputException("Equipment " + itemName + " does not exist");
      }
    }


    /**
     * validation for discount and quantities
     */
    validate_equipmentQuantities(newEquipmentQuantities);
    validate_discount(newDiscount);


    /**
     * validation for new names
     */
    if (newName == null) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    } else if (newName.equals("")) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    } else if (newName.equals(" ")) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    }
    if (EquipmentBundle.getWithName(newName) != null && !newName.equals(oldName)) {
      throw new InvalidInputException("A bookable item called " + newName + " already exists");
    }


    /**
     * Existence of bundle with old name
     */

    var checker = BookableItem.getWithName(oldName);

    if (checker != null && checker instanceof EquipmentBundle) {
      var found_bundle = (EquipmentBundle) checker;

      found_bundle.setName(newName);
      found_bundle.setDiscount(newDiscount);


      // delete old
      while (found_bundle.hasBundleItems()) {
        found_bundle.getBundleItem(0).delete();
      }
      // update new

      bundleItems(found_bundle, newEquipmentNames, newEquipmentQuantities);
      // persistence save
      try {
        ClimbSafePersistence.save();
      } catch (RuntimeException e) {
        throw new InvalidInputException(e.getMessage());
      }
    }

    else {
      throw new InvalidInputException("Equipment bundle " + oldName + " does not exist");
    }
  }

  /**
   * Getter for a list of all available equipment bundles in the model
   * 
   * @return ArrayList
   * @author Jimmy Sheng
   */
  public static ArrayList<TOEquipmentBundle> getEquipmentBundles() {
    var equipmentBundles = new ArrayList<TOEquipmentBundle>();
    for (var bundle : climbSafe.getBundles()) {
      equipmentBundles.add(new TOEquipmentBundle(bundle.getName(), bundle.getDiscount()));
    }
    return equipmentBundles;
  }

  /**
   * Getter for a list of bundle items in the given bundle
   * 
   * @param bundleName
   * @return ArrayList
   * @throws InvalidInputException if bundleName does not link to a valid bundle
   */
  public static ArrayList<TOBundleItem> getBundleItems(String bundleName)
      throws InvalidInputException {
    // check if bundle exists
    var bookableItem = BookableItem.getWithName(bundleName);
    if (bookableItem == null) {
      throw new InvalidInputException("Bundle not found");
    } else if (!(bookableItem instanceof EquipmentBundle)) {
      throw new InvalidInputException("Bundle not found");
    }
    var bundle = (EquipmentBundle) bookableItem;
    var bundleItems = new ArrayList<TOBundleItem>();
    for (var item : bundle.getBundleItems()) {
      bundleItems.add(new TOBundleItem(item.getQuantity(), item.getBundle().getName(),
          item.getEquipment().getName()));
    }
    return bundleItems;
  }

  /**
   * Getter for a list of all bundle items in the model
   * 
   * @return ArrayList
   * @author Jimmy Sheng
   */
  public static ArrayList<TOBundleItem> getBundleItems() {
    var bundleItems = new ArrayList<TOBundleItem>();
    for (var item : climbSafe.getBundleItems()) {
      bundleItems.add(new TOBundleItem(item.getQuantity(), item.getBundle().getName(),
          item.getEquipment().getName()));
    }
    return bundleItems;
  }

  /**
   * Helper method for validating equipment quantities list
   * 
   * @param equipmentQuantities
   * @throws InvalidInputException
   */
  private static void validate_equipmentQuantities(List<Integer> equipmentQuantities)
      throws InvalidInputException {
    /**
     * validation check for List<Integer> equipmentQuantities
     */
    for (int quantity : equipmentQuantities) {
      if (quantity < 1) {
        throw new InvalidInputException(
            "Each bundle item must have quantity greater than or equal to 1");
      }
    }

  }

  /**
   * Helper method for validating input discount
   * 
   * @param discount
   * @throws InvalidInputException
   */
  private static void validate_discount(int discount) throws InvalidInputException {
    /**
     * validation check for discount
     */

    if (discount < 0) {
      throw new InvalidInputException("Discount must be at least 0");
    } else if (discount > 100) {
      throw new InvalidInputException("Discount must be no more than 100");
    }
  }

  private static void validate_name(String name) throws InvalidInputException {
    /**
     * validation check for name
     */

    if (name == null) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    } else if (name.equals("")) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    } else if (name.equals(" ")) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    }

    /**
     * validation check for duplicated name
     */
    if (EquipmentBundle.getWithName(name) != null) {
      throw new InvalidInputException("A bookable item called " + name + " already exists");
    }

  }

  /**
   * adding bundle items to equipment bundle
   * 
   * @param equipmentBundle
   * @param equipmentNames
   * @param equipmentQuantities
   * @throws InvalidInputException
   */

  private static void bundleItems(EquipmentBundle equipmentBundle, List<String> equipmentNames,
      List<Integer> equipmentQuantities) throws InvalidInputException {
    Iterator<String> equi_iterator = equipmentNames.iterator();
    Iterator<Integer> quanti_iterator = equipmentQuantities.iterator();
    while (equi_iterator.hasNext() && quanti_iterator.hasNext()) {
      String equipment = equi_iterator.next();
      Integer quantity = quanti_iterator.next();
      // only add item when quantity is larger than 0
      if (quantity > 0) {
        var bookable = BookableItem.getWithName(equipment);
        if (bookable instanceof Equipment) {
          Equipment found_eq = (Equipment) bookable;
          // check if item exists as a BookableItem
          if (bookable != null) {
            climbSafe.addBundleItem(quantity, equipmentBundle, found_eq);
          }
        }

        else {
          throw new InvalidInputException("Requested item not found");
        }
      }
    }

  }



}
