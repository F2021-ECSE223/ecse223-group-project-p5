package ca.mcgill.ecse.climbsafe.demo;

import java.sql.Date;
import java.util.Calendar;

import ca.mcgill.ecse.climbsafe.demo.persistence.ClimbSafePersistence;
import ca.mcgill.ecse.climbsafe.model.Administrator;
import ca.mcgill.ecse.climbsafe.model.ClimbSafe;
import ca.mcgill.ecse.climbsafe.model.Equipment;
import ca.mcgill.ecse.climbsafe.model.EquipmentBundle;
import ca.mcgill.ecse.climbsafe.model.Guide;
import ca.mcgill.ecse.climbsafe.model.Hotel;
import ca.mcgill.ecse.climbsafe.model.Hotel.HotelRating;
import ca.mcgill.ecse.climbsafe.model.Member;

public class DemoFileCreator {

  public static void main(String[] args) {
    ClimbSafe climbSafe = createDemoData();
    // save
    ClimbSafePersistence.setFilename("ClimbSafeDemo.data");
    ClimbSafePersistence.save(climbSafe);
    // test
    DemoFileVerifier.verifyClimbSafe(climbSafe);
  }

  private static ClimbSafe createDemoData() {
    // root (NMP program info)
    Calendar calendar = Calendar.getInstance();
    calendar.set(2022, Calendar.APRIL, 1, 0, 0, 0);
    ClimbSafe climbSafe = new ClimbSafe(new Date(calendar.getTimeInMillis()), 5, 50);

    // admin
    new Administrator("admin@nmc.nt", "admin", climbSafe);

    // equipment
    Equipment rope = new Equipment("rope", 1000, 30, climbSafe);
    Equipment stove = new Equipment("stove", 2500, 100, climbSafe);
    Equipment pickaxe = new Equipment("pickaxe", 2000, 50, climbSafe);

    // equipment bundle
    EquipmentBundle standard = new EquipmentBundle("standard", 20, climbSafe);
    standard.addBundleItem(1, climbSafe, stove);
    standard.addBundleItem(2, climbSafe, rope);
    EquipmentBundle plus = new EquipmentBundle("plus", 30, climbSafe);
    plus.addBundleItem(2, climbSafe, stove);
    plus.addBundleItem(4, climbSafe, rope);
    plus.addBundleItem(3, climbSafe, pickaxe);

    // hotel
    new Hotel("Climbers' Lodge", "123 Mountain View Road", HotelRating.ThreeStars, climbSafe);
    new Hotel("High Peak", "455 Mountain View Road", HotelRating.FourStars, climbSafe);

    // guide
    new Guide("bob@gmail.com", "password", "Bob Hill", "(222) 123-4567", climbSafe);
    new Guide("sarah@yahoo.ca", "pwd", "Sarah Hill", "(222) 123-7654", climbSafe);

    // member
    Member joe = new Member("joe@hotmail.com", "1234", "Joe Black", "(222) 987-6540", 2, true,
        false, climbSafe);
    joe.addBookedItem(1, climbSafe, plus);
    joe.addBookedItem(2, climbSafe, rope);
    Member jane = new Member("jane@hotmail.com", "1234", "Jane Black", "(222) 987-6541", 3, false,
        false, climbSafe);
    jane.addBookedItem(1, climbSafe, rope);
    jane.addBookedItem(1, climbSafe, stove);
    jane.addBookedItem(1, climbSafe, pickaxe);
    Member jack = new Member("jack@hotmail.com", "1234", "Jack Black", "(222) 987-6542", 4, true,
        true, climbSafe);
    jack.addBookedItem(1, climbSafe, standard);
    jack.addBookedItem(1, climbSafe, plus);
    new Member("julie@hotmail.com", "1234", "Julie Black", "(222) 987-6543", 1, true, false,
        climbSafe);
    Member jon = new Member("jon@hotmail.com", "1234", "Jon Black", "(222) 987-6544", 3, true,
        false, climbSafe);
    jon.addBookedItem(3, climbSafe, rope);
    jon.addBookedItem(1, climbSafe, stove);
    jon.addBookedItem(2, climbSafe, pickaxe);

    return climbSafe;
  }

}
