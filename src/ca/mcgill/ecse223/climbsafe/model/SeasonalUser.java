/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse223.climbsafe.model;

// line 34 "../../../../../domain_model.ump"
public abstract class SeasonalUser extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //SeasonalUser Attributes
  private String name;
  private String emergencyContact;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public SeasonalUser(String aUsername, String aPassword, ClimbSafe aClimbSafe, String aName, String aEmergencyContact)
  {
    super(aUsername, aPassword, aClimbSafe);
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

  /**
   * need to add attributes to users for Members and Guides
   */
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