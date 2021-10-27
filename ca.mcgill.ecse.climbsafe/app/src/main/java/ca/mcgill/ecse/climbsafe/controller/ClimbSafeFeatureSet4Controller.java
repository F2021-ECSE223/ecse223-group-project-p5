package ca.mcgill.ecse.climbsafe.controller;

import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;

/**
 * 
 * Controller for add equipment and update equipment
 * 
 * @author SiboHuang
 *
 */
public class ClimbSafeFeatureSet4Controller {
  
  // reference to ClimbSafe
  private static ClimbSafe climbsafe = ClimbSafeApplication.getClimbSafe();
  public static void addEquipment(String name, int weight, int pricePerWeek) throws InvalidInputException {
  
   /*
    * Validate weight & pricePerWeek & name
    */
   
  if (weight <= 0) {
	  throw new InvalidInputException("The weight must be greater than 0");
  } else if (pricePerWeek < 0) {
	  throw new InvalidInputException("The price per week must be greater than or equal to 0");
  } else if (name == null || name.equals("") || name.equals(" ")) {
    throw new InvalidInputException("The name must not be empty"); 
  }
  
  /*
   * Validate existent of equipment and equipment bundle in the system
   */
  
  var equip = BookableItem.getWithName(name);
    if (equip instanceof Equipment) {
      throw new InvalidInputException("The piece of equipment already exists");
    }   
  
  
  var equipbun = BookableItem.getWithName(name);
  if (equipbun != null) {
    if (equipbun instanceof EquipmentBundle) {
      throw new InvalidInputException("The equipment bundle already exists");
    }
  }
  
  climbsafe.addEquipment(name, weight, pricePerWeek);
}


  public static void updateEquipment(String oldName, String newName, int newWeight,
      int newPricePerWeek) throws InvalidInputException {
	  

	if (newWeight <= 0) {
		throw new InvalidInputException("The weight must be greater than 0");
	} else if (newPricePerWeek <= 0) {
		throw new InvalidInputException("The price per week must be greater than or equal to 0");
	} else if (newName.equals("")) {
		throw new InvalidInputException("The name must not be empty");
	} else if (newName.equals(" ")) {
	    throw new InvalidInputException("The name must not be empty");
	}
	
	var equip = BookableItem.getWithName(oldName);
	if (equip == null) {
	  throw new InvalidInputException("The piece of equipment does not exist");
	}  
	
	var newequip = BookableItem.getWithName(newName);
	if (!newName.equals(oldName)) {
	  if (newequip instanceof Equipment) {
	      throw new InvalidInputException("The piece of equipment already exists");
	    }
	    
	    if (newequip instanceof EquipmentBundle) {
	      throw new InvalidInputException("An equipment bundle with the same name already exists");
	    }
	  
	}
	

	var checker = BookableItem.getWithName(oldName);
	
	if (checker != null && checker instanceof Equipment) {
	  var found_equip = (Equipment) checker;
	  
	  found_equip.setName(newName);
	  found_equip.setWeight(newWeight);
	  found_equip.setPricePerWeek(newPricePerWeek);
	}
	
	} 

}
