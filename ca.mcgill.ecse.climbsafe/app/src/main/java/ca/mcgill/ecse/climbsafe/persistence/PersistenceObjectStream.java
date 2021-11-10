package ca.mcgill.ecse.climbsafe.persistence;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PersistenceObjectStream {

  private static String filename = "output.txt";

  public static void serialize(Object object) {
    try (var oos = new ObjectOutputStream(new FileOutputStream(new File(filename)))) {
      oos.writeObject(object);
    } catch (IOException e) {
      throw new RuntimeException("Could not save data to file '" + filename + "'.");
    }
  }

  public static Object deserialize() {
    try (var ois = new ObjectInputStream(new FileInputStream(new File(filename)))) {
      return ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void setFilename(String newFilename) {
    filename = newFilename;
  }

}
