package ca.mcgill.ecse.climbsafe.demo;

import ca.mcgill.ecse.climbsafe.model.Administrator;
import ca.mcgill.ecse.climbsafe.model.BookedItem;
import ca.mcgill.ecse.climbsafe.model.BundleItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Hotel;
import ca.mcgill.ecse.climbsafe.model.Member;

public class DemoFilePrinter {

  public static void printClimbSafe(ClimbSafe climbSafe) {
    System.out.println("ClimbSafe: startDate = " + climbSafe.getStartDate().toString()
        + "; nrOfWeeks = " + climbSafe.getNrWeeks() + "; priceOfGuidePerWeek = "
        + climbSafe.getPriceOfGuidePerWeek());
    Administrator admin = climbSafe.getAdministrator();
    System.out.println(
        "Administrator: email = " + admin.getEmail() + "; password = " + admin.getPassword());
    for (Equipment equipment : climbSafe.getEquipment()) {
      System.out.println("Equipment: name = " + equipment.getName() + "; weight = "
          + equipment.getWeight() + "; pricePerWeek = " + equipment.getPricePerWeek());
    }
    for (EquipmentBundle bundle : climbSafe.getBundles()) {
      System.out
          .println("Bundle: name = " + bundle.getName() + "; discount = " + bundle.getDiscount());
      for (BundleItem item : bundle.getBundleItems()) {
        System.out.println("   Item: name = " + item.getEquipment().getName() + "; quantity = "
            + item.getQuantity());
      }
    }
    for (Hotel hotel : climbSafe.getHotels()) {
      System.out.println("Hotel: name = " + hotel.getName() + "; address = " + hotel.getAddress()
          + "; rating = " + hotel.getRating());
    }
    for (Guide guide : climbSafe.getGuides()) {
      System.out.println(
          "Guide: email = " + guide.getEmail() + "; password = " + guide.getPassword() + "; name = "
              + guide.getName() + "; emergencyContact = " + guide.getEmergencyContact());
    }
    for (Member member : climbSafe.getMembers()) {
      System.out.println("Member: email = " + member.getEmail() + "; password = "
          + member.getPassword() + "; name = " + member.getName() + "; emergencyContact = "
          + member.getEmergencyContact() + "; nrWeeks = " + member.getNrWeeks()
          + "; guideRequired = " + member.getGuideRequired() + "; hotelRequired = "
          + member.getHotelRequired());
      for (BookedItem item : member.getBookedItems()) {
        System.out.println(
            "   Item: name = " + item.getItem().getName() + "; quantity = " + item.getQuantity());
      }
    }
  }

}
