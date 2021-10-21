/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.model;
import java.util.*;

// line 28 "../../../../../ClimbSafe.ump"
public abstract class NamedUser extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //NamedUser Attributes
  private String name;
  private String emergencyContact;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public NamedUser(String aEmail, String aPassword, String aName, String aEmergencyContact)
  {
    super(aEmail, aPassword);
    name = aName;
    emergencyContact = aEmergencyContact;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    name = aName;
    wasSet = true;
    return wasSet;
  }

  public boolean setEmergencyContact(String aEmergencyContact)
  {
    boolean wasSet = false;
    emergencyContact = aEmergencyContact;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getEmergencyContact()
  {
    return emergencyContact;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "emergencyContact" + ":" + getEmergencyContact()+ "]";
  }
}