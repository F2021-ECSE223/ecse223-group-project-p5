package ca.mcgill.ecse.climbsafe.controller;

import java.util.Iterator;
import java.util.List;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.BundleItem;
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

  public static void addEquipment(String name, int weight, int pricePerWeek) throws InvalidInputException {
  
  ClimbSafe climbsafe = ClimbSafeApplication.getClimbSafe();
    
  if (weight <= 0) {
	  throw new InvalidInputException("The weight must be greater than 0.");
 // } else if (weight < 0) {
	 // throw new InvalidInputException("The weight must be greater than 0");
  } else if (pricePerWeek < 0) {
	  throw new InvalidInputException("The price per week must be greater than or equal to 0");
  } else if (name == null) {
	  throw new InvalidInputException("The name must not be empty");
  } 
  
  var equipbun = BookableItem.getWithName(name);
  if (equipbun != null) {
    if (equipbun instanceof EquipmentBundle) {
      throw new InvalidInputException("The equipment bundle already exists");
    }
  }
  
  var equip = BookableItem.getWithName(name);
  if (equip != null) {
    if (equip instanceof Equipment) {
      throw new InvalidInputException("The piece of equipment already exists");
	  }	  
  }
}


  public static void updateEquipment(String oldName, String newName, int newWeight,
      int newPricePerWeek) throws InvalidInputException {
	  
	  ClimbSafe climbsafe = ClimbSafeApplication.getClimbSafe();
	  

	if (newWeight == 0) {
		throw new InvalidInputException("The weight must be greater than 0");
	} else if (newWeight == 0) {
		throw new InvalidInputException("The weight must be greater than 0");
	} else if (newPricePerWeek <= 0) {
		throw new InvalidInputException("The price per week must be greater than or equal to 0");
	} else if (newName == null) {
		throw new InvalidInputException("The name must not be empty");
	} else {
	Equipment equipment = new Equipment(newName, newWeight, newPricePerWeek, climbsafe);
	Boolean check = climbsafe.addEquipment(equipment);
	if (!check) {
		throw new InvalidInputException("The piece of "+newName+" already exists");
	}
	
	
	
  }

 }
}
