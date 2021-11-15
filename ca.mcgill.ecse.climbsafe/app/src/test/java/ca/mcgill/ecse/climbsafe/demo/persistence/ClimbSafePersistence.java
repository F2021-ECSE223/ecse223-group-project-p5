package ca.mcgill.ecse.climbsafe.demo.persistence;

import java.sql.Date;

import ca.mcgill.ecse.climbsafe.model.ClimbSafe;

public class ClimbSafePersistence {

  public static void save(ClimbSafe climbSafe) {
    PersistenceObjectStream.serialize(climbSafe);
  }

  public static ClimbSafe load() {
    ClimbSafe climbSafe = (ClimbSafe) PersistenceObjectStream.deserialize();
    // model cannot be loaded - create empty ClimbSafe
    if (climbSafe == null) {
      climbSafe = new ClimbSafe(new Date(0L), 0, 0);
    } else {
      climbSafe.reinitialize();
    }
    return climbSafe;
  }

  public static void setFilename(String filename) {
    PersistenceObjectStream.setFilename(filename);
  }

}
