/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse223.climbsafe.model;

// line 19 "../../../../../domain_model.ump"
public abstract class User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //User Attributes
  private String username;
  private String password;

  //User Associations
  private ClimbSafe climbSafe;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public User(String aUsername, String aPassword, ClimbSafe aClimbSafe)
  {
    username = aUsername;
    password = aPassword;
    boolean didAddClimbSafe = setClimbSafe(aClimbSafe);
    if (!didAddClimbSafe)
    {
      throw new RuntimeException("Unable to create user due to climbSafe. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  /**
   * this class should not be instantiated
   * umple cannot handle unique immutable attributes
   * username must be set uniquely and cannot be changed
   */
  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetOne */
  public ClimbSafe getClimbSafe()
  {
    return climbSafe;
  }
  /* Code from template association_SetOneToMany */
  public boolean setClimbSafe(ClimbSafe aClimbSafe)
  {
    boolean wasSet = false;
    if (aClimbSafe == null)
    {
      return wasSet;
    }

    ClimbSafe existingClimbSafe = climbSafe;
    climbSafe = aClimbSafe;
    if (existingClimbSafe != null && !existingClimbSafe.equals(aClimbSafe))
    {
      existingClimbSafe.removeUser(this);
    }
    climbSafe.addUser(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ClimbSafe placeholderClimbSafe = climbSafe;
    this.climbSafe = null;
    if(placeholderClimbSafe != null)
    {
      placeholderClimbSafe.removeUser(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "climbSafe = "+(getClimbSafe()!=null?Integer.toHexString(System.identityHashCode(getClimbSafe())):"null");
  }
}