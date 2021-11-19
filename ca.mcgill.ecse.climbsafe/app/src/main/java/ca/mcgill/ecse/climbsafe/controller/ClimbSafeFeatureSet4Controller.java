package ca.mcgill.ecse.climbsafe.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import ca.mcgill.ecse.climbsafe.persistence.ClimbSafePersistence;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;

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

  /**
   * 
   * @param name
   * @param weight
   * @param pricePerWeek
   * @throws InvalidInputException
   */

  public static void addEquipment(String name, int weight, int pricePerWeek)
      throws InvalidInputException {

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
    // persistence save
    try {
      ClimbSafePersistence.save();
    } catch (RuntimeException e) {
      throw new InvalidInputException(e.getMessage());
    }
  }


  /**
   * 
   * @param oldName
   * @param newName
   * @param newWeight
   * @param newPricePerWeek
   * @throws InvalidInputException
   */

  public static void updateEquipment(String oldName, String newName, int newWeight,
      int newPricePerWeek) throws InvalidInputException {

    /*
     * Validate newWeight & newPricePerWeek & newName
     */

    if (newWeight <= 0) {
      throw new InvalidInputException("The weight must be greater than 0");
    } else if (newPricePerWeek <= 0) {
      throw new InvalidInputException("The price per week must be greater than or equal to 0");
    } else if (newName.equals("")) {
      throw new InvalidInputException("The name must not be empty");
    } else if (newName.equals(" ")) {
      throw new InvalidInputException("The name must not be empty");
    }

    /*
     * Validate existent of equipment and equipment bundle in the system
     */

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

    // update the equipment with new information

    var checker = BookableItem.getWithName(oldName);

    if (checker != null && checker instanceof Equipment) {
      var found_equip = (Equipment) checker;

      found_equip.setName(newName);
      found_equip.setWeight(newWeight);
      found_equip.setPricePerWeek(newPricePerWeek);
      // persistence save
      try {
        ClimbSafePersistence.save();
      } catch (RuntimeException e) {
        throw new InvalidInputException(e.getMessage());
      }
    }

  }

  /**
   * Getter for a list of all equipments in the model
   * 
   * @return ArrayList
   * @author Jimmy Sheng
   */
  public static ArrayList<TOEquipment> getEquipments() {
    var equipments = new ArrayList<TOEquipment>();
    for (var equipment : climbsafe.getEquipment()) {
      /*
       * Initialize helper variable for MembersPage
       */
      Spinner<Integer> mpQuantity = new Spinner<Integer>();
      mpQuantity.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999, 0));
      equipments.add(new TOEquipment(equipment.getName(), equipment.getWeight(),
          equipment.getPricePerWeek(), mpQuantity));
    }
    // by default, sort by name
    Comparator<TOEquipment> comp = Comparator.comparing(TOEquipment::getName);
    Collections.sort(equipments, comp);
    return equipments;
  }

}
