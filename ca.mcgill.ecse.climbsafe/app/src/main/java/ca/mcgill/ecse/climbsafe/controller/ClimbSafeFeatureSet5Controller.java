package ca.mcgill.ecse.climbsafe.controller;

import java.util.List;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.BundleItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;


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

  public static void addEquipmentBundle(String name, int discount, List<String> equipmentNames,
      List<Integer> equipmentQuantities) throws InvalidInputException {

    ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();

    /**
     * validation check for List<String> equipmentNames
     */
    List<Equipment> elist= climbSafe.getEquipment();
    List<String> temp = null;
    List<Equipment> equipmentList = null;

    if(elist.size()<2) {
      throw new InvalidInputException("Equipment bundle must contain at least two distinct types of equipment");
    }

    boolean check = false;
    for (String ItemName : equipmentNames) {
      for (Equipment e : elist) {
        if(e.getName()== ItemName) {
          //turn list of name into list of equipments
          equipmentList.add(e);
          check = true;
        }
      }
      if (check == false) {
        throw new InvalidInputException("Equipment "+ItemName+" does not exist");
      }
      //------------------------
      if(temp.contains(ItemName)) {
        throw new InvalidInputException("Equipment bundle must contain at least two distinct types of equipment");
      }
      temp.add(ItemName);
      //--------------------------- 
    }


    /**
     * validation check for List<Integer> equipmentQuantities        
     */
    for (int quantity : equipmentQuantities) {
      if (quantity<=0) {
        throw new InvalidInputException("Each bundle item must have quantity greater than or equal to 1");
      }
    }


    /**
     * validation check for discount 
     */

    if (discount < 0) {
      throw new InvalidInputException("Discount must be at least 0");
    }
    else if (discount > 100) {
      throw new InvalidInputException("Discount must be no more than 100");
    }

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

    List<EquipmentBundle> ebundle= climbSafe.getBundles();
    for (EquipmentBundle b : ebundle) {
      if(b.getName() == name) {
        throw new InvalidInputException("A bookable item called "+name+" already exists");
      }
    }

    EquipmentBundle eq = new EquipmentBundle(name, discount, climbSafe);
    climbSafe.addBundle(eq);
    for(int i=0; i< equipmentNames.size(); i++) {
      eq.addBundleItem(equipmentQuantities.get(i), climbSafe, equipmentList.get(i));
    }

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


    ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();

    /**
     * validation check for List<String> equipmentNames
     */
    List<Equipment> elist= climbSafe.getEquipment();
    List<String> temp = null;
    List<Equipment> equipmentList = null;

    if(elist.size()<2) {
      throw new InvalidInputException("Equipment bundle must contain at least two distinct types of equipment");
    }

    boolean check = false;
    for (String ItemName : newEquipmentNames) {
      for (Equipment e : elist) {
        if(e.getName()== ItemName) {
          //turn list of name into list of equipments
          equipmentList.add(e);
          check = true;
        }
      }
      if (check == false) {
        throw new InvalidInputException("Equipment "+ItemName+" does not exist");
      }
      //------------------------
      if(temp.contains(ItemName)) {
        throw new InvalidInputException("Equipment bundle must contain at least two distinct types of equipment");
      }
      temp.add(ItemName);
      //--------------------------- 
    }
    /**
     * validation check for List<Integer> equipmentQuantities        
     */
    for (int quantity : newEquipmentQuantities) {
      if (quantity<=0) {
        throw new InvalidInputException("Each bundle item must have quantity greater than or equal to 1");
      }
    }


    /**
     * validation check for discount 
     */

    if (newDiscount < 0) {
      throw new InvalidInputException("Discount must be at least 0");
    }
    else if (newDiscount > 100) {
      throw new InvalidInputException("Discount must be no more than 100");
    }

    /**
     * validation check for name
     */

    if (oldName == null) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    }
    else if (oldName.equals("")) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    }
    else if (oldName.equals(" ")) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    }
    
    if (newName == null) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    }
    else if (newName.equals("")) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    }
    else if (newName.equals(" ")) {
      throw new InvalidInputException("Equipment bundle name cannot be empty");
    }

    /**
     * Existence of bundle with old name
     */
    List<EquipmentBundle> ebundle= climbSafe.getBundles();
    EquipmentBundle found_bundle = null;
    
    for (EquipmentBundle b : ebundle) {
      if(b.getName() == oldName) {
        
        found_bundle = b;
      }
    }
    //did not find the bundle with old name
    if (found_bundle == null) {
      throw new InvalidInputException("Equipment bundle "+oldName+"does not exist");
    }
    
    //update
    found_bundle.setName(newName);
    found_bundle.setDiscount(newDiscount);
   
    //delete old bundle items
    for (BundleItem bItem : found_bundle.getBundleItems()) {
      found_bundle.removeBundleItem(bItem);
    }
    
    //add new bundle items
    for(int i=0; i< newEquipmentNames.size(); i++) {
      found_bundle.addBundleItem(newEquipmentQuantities.get(i), climbSafe, equipmentList.get(i));
    }
    
    
    
    
    
    
    }

  
  }
