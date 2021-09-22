/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse223.climbsafe.model;
import java.util.*;

// line 8 "../../../../../domain_model.ump"
public class ClimbSafe
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ClimbSafe Associations
  private Admin administrator;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ClimbSafe(Admin aAdministrator)
  {
    if (aAdministrator == null || aAdministrator.getClimbSafe() != null)
    {
      throw new RuntimeException("Unable to create ClimbSafe due to aAdministrator. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    administrator = aAdministrator;
  }

  public ClimbSafe(String aUsernameForAdministrator, String aPasswordForAdministrator)
  {
    administrator = new Admin(aUsernameForAdministrator, aPasswordForAdministrator, this);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Admin getAdministrator()
  {
    return administrator;
  }

  public void delete()
  {
    Admin existingAdministrator = administrator;
    administrator = null;
    if (existingAdministrator != null)
    {
      existingAdministrator.delete();
    }
  }

}