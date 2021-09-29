/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse223.climbsafe.model;

// line 27 "../../../../../domain_model.ump"
public class Admin extends User
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Admin Attributes
  private String username;
  private String password;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Admin(String aUsername, String aPassword, ClimbSafe aClimbSafe)
  {
    super(aUsername, aPassword, aClimbSafe);
    username = "admin@nmc.nt";
    password = "admin";
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUsername(String aUsername)
  {
    boolean wasSet = false;
    username = aUsername;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getUsername()
  {
    return username;
  }

  public String getPassword()
  {
    return password;
  }

  public void delete()
  {
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "username" + ":" + getUsername()+ "," +
            "password" + ":" + getPassword()+ "]";
  }
}