package ca.mcgill.ecse.climbsafe.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.BundleItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import ca.mcgill.ecse.climbsafe.model.Guide;


public class ClimbSafeFeatureSet5Controller {

  /**
   * @author Annie Kang October 20th, 2021
   * 
   * Method to add a instance of equipment bundle to the application.
   * @param name
   * @param discount
   * @param equipmentNames
   * @param equipmentQuantities
   * @throws InvalidInputException
   */
  private static ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();
  public static void addEquipmentBundle(String name, int discount, List<String> equipmentNames,
      List<Integer> equipmentQuantities) throws InvalidInputException {

    

    validate_equipmentQuantities(equipmentQuantities);
    validate_discount(discount);
    validate_name(name);

    
    /**
     * validation check for List<String> equipmentNames
     */
    //List<Equipment> existing_equipment= climbSafe.getEquipment();
       
    //does not exist equipment name
    for (String itemName : equipmentNames) {
      if( Equipment.getWithName(itemName) == null) {
        throw new InvalidInputException("Equipment "+itemName+" does not exist");
      }
    }
    
    
    // at least 2 distinct equipment
    if(equipmentNames.size()<=1) {
      throw new InvalidInputException("Equipment bundle must contain at least two distinct types of equipment");
    }
    
    //repeating equipment  
    List<String> temp=new ArrayList<String>();
    //temp.add(equipmentNames.get(0));
    for(String eq_name : equipmentNames) {
       if(temp.contains(eq_name)) {
         throw new InvalidInputException("Equipment bundle must contain at least two distinct types of equipment");
       }
       else {
         temp.add(eq_name);
       }
    }
    
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
   // List<Equipment> existing_equipment= climbSafe.getEquipment();
       
 
    
    // at least 2 distinct equipment
    if(newEquipmentNames.size()<=1) {
      throw new InvalidInputException("Equipment bundle must contain at least two distinct types of equipment");
    }
    
    //repeating equipment  
    List<String> temp=new ArrayList<String>();
    //temp.add(equipmentNames.get(0));
    for(String eq_name : newEquipmentNames) {
       if(temp.contains(eq_name)) {
         throw new InvalidInputException("Equipment bundle must contain at least two distinct types of equipment");
       }
       else {
         temp.add(eq_name);
       }
    }
    
    //does not exist equipment name
    for (String itemName : newEquipmentNames) {
      if( BookableItem.getWithName(itemName) == null) {
        throw new InvalidInputException("Equipment "+itemName+" does not exist");
      }
    }

    validate_equipmentQuantities(newEquipmentQuantities);
    validate_discount(newDiscount);
    validate_name(newName);
    
    
    /**
     * Existence of bundle with old name
     */

    var checker = BookableItem.getWithName(oldName);
    
    if(checker != null && checker instanceof EquipmentBundle) {
      var found_bundle = (EquipmentBundle) checker;
      
          if(oldName != newName) {
            found_bundle.setName(newName);
          }
          
          found_bundle.setDiscount(newDiscount);
          //delete old
          while (found_bundle.hasBundleItems()) {
            found_bundle.getBundleItem(0).delete();
          }
          //update new
          //---------------------------------
          bundleItems(found_bundle, newEquipmentNames, newEquipmentQuantities);

  
    }
    else {
      throw new InvalidInputException("Equipment bundle "+oldName+" does not exist");
    }
 }

  
/**
 * Helper method for validating equipment quantities list
 * 
 * @param equipmentQuantities
 * @throws InvalidInputException
 */
  private static void validate_equipmentQuantities(List<Integer> equipmentQuantities) throws InvalidInputException {
    /**
     * validation check for List<Integer> equipmentQuantities        
     */
    for (int quantity : equipmentQuantities) {
      if (quantity <1) {
        throw new InvalidInputException("Each bundle item must have quantity greater than or equal to 1");
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
    }
    else if (discount > 100) {
      throw new InvalidInputException("Discount must be no more than 100");
    }
  }
  private static void validate_name(String name) throws InvalidInputException {
    /**
     * validation check for name
     */

    if (name == null) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    }
    else if (name.equals("")) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    }
    else if (name.equals(" ")) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    }
    
    /**
     * validation check for duplicated name
     */
    if( EquipmentBundle.getWithName(name) != null) {
      throw new InvalidInputException("A bookable item called "+name+" already exists");
    }
    
  }
  
  private static void bundleItems (EquipmentBundle equipmentBundle, List<String> equipmentNames, List<Integer> equipmentQuantities) throws InvalidInputException {
    Iterator<String> equi_iterator = equipmentNames.iterator();
    Iterator<Integer> quanti_iterator = equipmentQuantities.iterator();
    while (equi_iterator.hasNext() && quanti_iterator.hasNext()) {
      String item = equi_iterator.next();
      Integer qty = quanti_iterator.next();
      // only add item when quantity is larger than 0
      if (qty > 0) {
        var bookable = BookableItem.getWithName(item);
        if (bookable instanceof Equipment) {
          Equipment found_eq = (Equipment) bookable;
       // check if item exists as a BookableItem
          if (bookable != null) {
            climbSafe.addBundleItem(qty, equipmentBundle, found_eq);
          } 
        }
        
        else {
          throw new InvalidInputException("Requested item not found");
        }
      }
    }
    
  }
  





}
