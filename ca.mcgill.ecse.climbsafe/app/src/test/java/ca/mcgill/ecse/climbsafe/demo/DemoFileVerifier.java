package ca.mcgill.ecse.climbsafe.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import ca.mcgill.ecse.climbsafe.demo.persistence.ClimbSafePersistence;
import ca.mcgill.ecse.climbsafe.model.Administrator;
import ca.mcgill.ecse.climbsafe.model.BookableItem;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Hotel;
import ca.mcgill.ecse.climbsafe.model.Hotel.HotelRating;
import ca.mcgill.ecse.climbsafe.model.Member;
import ca.mcgill.ecse.climbsafe.model.User;

public class DemoFileVerifier {

  public static void verifyClimbSafe(ClimbSafe climbSafe) {
    // clear
    climbSafe.delete();
    // load
    climbSafe = ClimbSafePersistence.load();
    // test
    verifyLoadedClimbSafe(climbSafe);
    // output
    DemoFilePrinter.printClimbSafe(climbSafe);
  }

  private static void verifyLoadedClimbSafe(ClimbSafe climbSafe) {
    // root
    verifyClimbSafe(climbSafe, "2022-04-01", 5, 50);

    // admin
    verifyAdministrator("admin@nmc.nt", "admin");

    // equipment
    assertEquals(3, climbSafe.getEquipment().size());
    verifyEquipment("rope", 1000, 30);
    verifyEquipment("stove", 2500, 100);
    verifyEquipment("pickaxe", 2000, 50);

    // equipment bundle
    assertEquals(2, climbSafe.getBundles().size());
    verifyEquipmentBundle("standard", 20, new int[] {1, 2}, new String[] {"stove", "rope"});
    verifyEquipmentBundle("plus", 30, new int[] {2, 4, 3},
        new String[] {"stove", "rope", "pickaxe"});

    // hotel
    assertEquals(2, climbSafe.getHotels().size());
    verifyHotel("Climbers' Lodge", "123 Mountain View Road", HotelRating.ThreeStars);
    verifyHotel("High Peak", "455 Mountain View Road", HotelRating.FourStars);

    // guide
    assertEquals(2, climbSafe.getGuides().size());
    verifyGuide("bob@gmail.com", "password", "Bob Hill", "(222) 123-4567");
    verifyGuide("sarah@yahoo.ca", "pwd", "Sarah Hill", "(222) 123-7654");

    // member
    assertEquals(5, climbSafe.getMembers().size());
    verifyMember("joe@hotmail.com", "1234", "Joe Black", "(222) 987-6540", 2, true, false,
        new int[] {1, 2}, new String[] {"plus", "rope"});
    verifyMember("jane@hotmail.com", "1234", "Jane Black", "(222) 987-6541", 3, false, false,
        new int[] {1, 1, 1}, new String[] {"rope", "stove", "pickaxe"});
    verifyMember("jack@hotmail.com", "1234", "Jack Black", "(222) 987-6542", 4, true, true,
        new int[] {1, 1}, new String[] {"standard", "plus"});
    verifyMember("julie@hotmail.com", "1234", "Julie Black", "(222) 987-6543", 1, true, false,
        new int[] {}, new String[] {});
    verifyMember("jon@hotmail.com", "1234", "Jon Black", "(222) 987-6544", 3, true, false,
        new int[] {3, 1, 2}, new String[] {"rope", "stove", "pickaxe"});

    System.out.println("=======================");
    System.out.println("Verification successful");
    System.out.println("=======================");
  }

  private static void verifyAdministrator(String email, String password) {
    Administrator admin = (Administrator) User.getWithEmail(email);
    assertNotNull(admin);
    assertEquals(password, admin.getPassword());
  }

  private static void verifyClimbSafe(ClimbSafe climbSafe, String startDate, int nrWeeks,
      int priceOfGuidePerWeek) {
    assertEquals(startDate, climbSafe.getStartDate().toString());
    assertEquals(nrWeeks, climbSafe.getNrWeeks());
    assertEquals(priceOfGuidePerWeek, climbSafe.getPriceOfGuidePerWeek());
  }

  private static void verifyEquipment(String name, int weight, int pricePerWeek) {
    Equipment equipment = (Equipment) BookableItem.getWithName(name);
    assertNotNull(equipment);
    assertEquals(weight, equipment.getWeight());
    assertEquals(pricePerWeek, equipment.getPricePerWeek());
  }

  private static void verifyEquipmentBundle(String name, int discount, int[] quantities,
      String[] equipmentNames) {
    EquipmentBundle bundle = (EquipmentBundle) BookableItem.getWithName(name);
    assertNotNull(bundle);
    assertEquals(discount, bundle.getDiscount());
    assertEquals(quantities.length, bundle.getBundleItems().size());
    for (int i = 0; i < quantities.length; i++) {
      assertEquals(quantities[i], bundle.getBundleItem(i).getQuantity());
      assertEquals(equipmentNames[i], bundle.getBundleItem(i).getEquipment().getName());
    }
  }

  private static void verifyGuide(String email, String password, String name,
      String emergencyContact) {
    Guide guide = (Guide) User.getWithEmail(email);
    assertNotNull(guide);
    assertEquals(password, guide.getPassword());
    assertEquals(name, guide.getName());
    assertEquals(emergencyContact, guide.getEmergencyContact());
  }

  private static void verifyHotel(String name, String address, HotelRating rating) {
    Hotel hotel = Hotel.getWithName(name);
    assertNotNull(hotel);
    assertEquals(address, hotel.getAddress());
    assertEquals(rating, hotel.getRating());
  }

  private static void verifyMember(String email, String password, String name,
      String emergencyContact, int nrWeeks, boolean guideRequired, boolean hotelRequired,
      int[] quantities, String[] bookableItemsNames) {
    Member member = (Member) User.getWithEmail(email);
    assertNotNull(member);
    assertEquals(password, member.getPassword());
    assertEquals(name, member.getName());
    assertEquals(emergencyContact, member.getEmergencyContact());
    assertEquals(nrWeeks, member.getNrWeeks());
    assertEquals(guideRequired, member.getGuideRequired());
    assertEquals(hotelRequired, member.getHotelRequired());
    assertEquals(quantities.length, member.getBookedItems().size());
    for (int i = 0; i < quantities.length; i++) {
      assertEquals(quantities[i], member.getBookedItem(i).getQuantity());
      assertEquals(bookableItemsNames[i], member.getBookedItem(i).getItem().getName());
    }
  }

}
