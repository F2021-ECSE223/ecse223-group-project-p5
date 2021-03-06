package ca.mcgill.ecse.climbsafe.controller;

import java.util.ArrayList;
import java.util.List;
import ca.mcgill.ecse.climbsafe.application.ClimbSafeApplication;
import ca.mcgill.ecse.climbsafe.model.Assignment;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.persistence.ClimbSafePersistence;

/**
 * Controller Methods for Feature Set 6.
 * <ul>
 * <li>Method to delete an instance of Equipment from the application.</li>
 * <li>Method to delete an instance of EquipmentBundle from the application.</li>
 * <li>Method to view all Assignment instances in the application.</li>
 * </ul>
 * 
 * @author Harrison Wang Oct 19, 2021
 *
 */
public class ClimbSafeFeatureSet6Controller {

  /**
   * Delete an instance of <code>Equipment</code> from the application.
   * 
   * @author Harrison Wang Oct 19, 2021
   * @param name - The name of the <code>Equipment</code> instance to delete
   * @throws InvalidInputException if the corresponding <code>Equipment</code> instance is not found
   *         or is in a bundle.
   */
  public static void deleteEquipment(String name) throws InvalidInputException {
    // Fetch the instance of BookableItem associated with <name>
    BookableItem equipmentToBeDeleted = BookableItem.getWithName(name);
    /*
     * Before casting equipmentToBeDeleted to Equipment and deleting, we need to verify that it is
     * both not null and an instance of Equipment to guarantee we are calling the correct version of
     * the method delete().
     */
    if (equipmentToBeDeleted != null && equipmentToBeDeleted instanceof Equipment) {
      /*
       * Equipment cannot be deleted if it is in a bundle, So check to see if it is in a bundle
       * before deleting
       */
      if (((Equipment) equipmentToBeDeleted).numberOfBundleItems() > 0) {
        throw new InvalidInputException(
            "The piece of equipment is in a bundle and cannot be deleted");
      }
      ((Equipment) equipmentToBeDeleted).delete();
      // persistence save
      try {
        ClimbSafePersistence.save();
      } catch (RuntimeException e) {
        throw new InvalidInputException(e.getMessage());
      }
    } else {
      throw new InvalidInputException("The piece of equipment " + name + " does not exist");
    }
  }

  /**
   * Delete an instance of <code>EquipmentBundle</code> from the application.
   * 
   * @author Harrison Wang Oct 19, 2021
   * @param name - The name of the <code>EquipmentBundle</code> instance to delete
   * @throws InvalidInputException if the corresponding <code>EquipmentBundle</code> instance does
   *         not exist.
   */
  public static void deleteEquipmentBundle(String name) throws InvalidInputException {
    // Fetch instance of BookableItem associated with <name>
    BookableItem equipmentBundleToBeDeleted = BookableItem.getWithName(name);
    /*
     * Before casting BookableItem to EquipmentBundle and deleting, we need to verify that it is
     * both not null and an instance of EquipmentBundle to guarantee we are calling the correct
     * version of the method delete().
     */
    if (equipmentBundleToBeDeleted != null
        && equipmentBundleToBeDeleted instanceof EquipmentBundle) {
      ((EquipmentBundle) equipmentBundleToBeDeleted).delete();
      // persistence save
      try {
        ClimbSafePersistence.save();
      } catch (RuntimeException e) {
        throw new InvalidInputException(e.getMessage());
      }
    }
  }

  /**
   * Return a list of all assignments currently in the application.
   * 
   * @author Harrison Wang Oct 19, 2021
   * @return A list of <code>TOAssignment</code> instances containing information regarding the
   *         <code>Assignment</code> instances in the application.
   */
  public static List<TOAssignment> getAssignments() {
    ClimbSafe climbSafe = ClimbSafeApplication.getClimbSafe();
    ArrayList<TOAssignment> TOAssignmentList = new ArrayList<TOAssignment>();

    for (var assignment : climbSafe.getAssignments()) {
      var newTOAssignment = wrapAssignment(assignment);
      TOAssignmentList.add(newTOAssignment);
    }

    return TOAssignmentList;
  }
  
  /**
   * Get assignment for single member
   * 
   * @param email the email of the target member
   * @return the TOAssignment instance containing information about the target member's assignment
   * @throws InvalidInputException if a member with the email does not exist
   * @author Michael Grieco
   */
  public static TOAssignment getAssignment(String email) throws InvalidInputException {
    var usr = Member.getWithEmail(email);
    if (usr == null) {
      throw new InvalidInputException("Member not found");
    } else if (!(usr instanceof Member)) {
      throw new InvalidInputException("Member not found");
    }
    
    var retAssignment = ((Member)usr).getAssignment();
    
    return wrapAssignment(retAssignment);
  }

  /**
   * Helper method used to wrap the information in an <code>Assignment</code> instance in an
   * instance of <code>TOAssignment</code>.
   * 
   * @author Harrison Wang Oct 19, 2021
   * @param assignment - The <code>Assignment</code> instance to transfer the information from.
   * @return A <code>TOAssignment</code> instance containing the information in the
   *         <code>Assignment</code> parameter.
   */
  private static TOAssignment wrapAssignment(Assignment assignment) {
    ClimbSafe assignmentClimbSafe = assignment.getClimbSafe();
    Member assignmentMember = assignment.getMember();

    // Initialize values for all necessary parameters.
    String memberEmail = assignmentMember.getEmail(), memberName = assignmentMember.getName(),
        guideEmail = assignment.hasGuide() ? assignment.getGuide().getEmail() : null,
        guideName = assignment.hasGuide() ? assignment.getGuide().getName() : null,
        hotelName = assignment.hasHotel() ? assignment.getHotel().getName() : null;
    int nrOfWeeks = assignmentMember.getNrWeeks(), startWeek = assignment.getStartWeek(),
        endWeek = assignment.getEndWeek(), totalCostForGuide =
            assignment.hasGuide() ? nrOfWeeks * assignmentClimbSafe.getPriceOfGuidePerWeek() : 0;
    /*
     * Calculate the totalCostForEquipment.
     * 
     * Sum the costs of all booked items depending on if they are an Equipment or EquipmentBundle
     * instance to get the equipmentCostPerWeek for this assignment.
     * 
     * Multiply equipmentCostPerWeek by nrOfWeeks to get totalCostForEquipment.
     */
    int equipmentCostPerWeek = 0;
    for (var bookedItem : assignmentMember.getBookedItems()) {
      BookableItem item = bookedItem.getItem();
      if (item instanceof Equipment) {
        equipmentCostPerWeek += ((Equipment) item).getPricePerWeek() * bookedItem.getQuantity();
      } else if (item instanceof EquipmentBundle) {
        EquipmentBundle bundle = ((EquipmentBundle) item);
        int bundleCost = 0;
        for (var bundledItem : bundle.getBundleItems()) {
          bundleCost += bundledItem.getEquipment().getPricePerWeek() * bundledItem.getQuantity();
        }
        /*
         * Discount is only applicable if the assignment includes a Guide, So check if a Guide is
         * required before applying the discount.
         */
        if (assignment.hasGuide()) {
          bundleCost = (int) (bundleCost * ((100.0 - bundle.getDiscount()) / 100.0));
        }
        equipmentCostPerWeek += bundleCost * bookedItem.getQuantity();
      }
    }
    int totalCostForEquipment = equipmentCostPerWeek * nrOfWeeks;

    /*
     * New fields of TOAssignment
     */
    String status = assignment.getAssignmentStatusFullName();
    String authorizationCode = assignment.getPaymentCode();
    int refundPercentAmount = assignment.getRefundPercentage();

    return new TOAssignment(memberEmail, memberName, guideEmail, guideName, hotelName, startWeek,
        endWeek, totalCostForGuide, totalCostForEquipment, status, authorizationCode,
        refundPercentAmount);
  }

}
