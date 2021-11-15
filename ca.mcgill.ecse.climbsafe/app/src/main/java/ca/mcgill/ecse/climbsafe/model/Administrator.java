/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.model;
import java.io.Serializable;
import java.util.*;

// line 34 "../../../../../ClimbSafePersistence.ump"
// line 29 "../../../../../ClimbSafe.ump"
public class Administrator extends User implements Serializable
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Administrator Associations
  private ClimbSafe climbSafe;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Administrator(String aEmail, String aPassword, ClimbSafe aClimbSafe)
  {
    super(aEmail, aPassword);
    boolean didAddClimbSafe = setClimbSafe(aClimbSafe);
    if (!didAddClimbSafe)
    {
      throw new RuntimeException("Unable to create administrator due to climbSafe. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public ClimbSafe getClimbSafe()
  {
    return climbSafe;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setClimbSafe(ClimbSafe aNewClimbSafe)
  {
    boolean wasSet = false;
    if (aNewClimbSafe == null)
    {
      //Unable to setClimbSafe to null, as administrator must always be associated to a climbSafe
      return wasSet;
    }
    
    Administrator existingAdministrator = aNewClimbSafe.getAdministrator();
    if (existingAdministrator != null && !equals(existingAdministrator))
    {
      //Unable to setClimbSafe, the current climbSafe already has a administrator, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    ClimbSafe anOldClimbSafe = climbSafe;
    climbSafe = aNewClimbSafe;
    climbSafe.setAdministrator(this);

    if (anOldClimbSafe != null)
    {
      anOldClimbSafe.setAdministrator(null);
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ClimbSafe existingClimbSafe = climbSafe;
    climbSafe = null;
    if (existingClimbSafe != null)
    {
      existingClimbSafe.setAdministrator(null);
    }
    super.delete();
  }
  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 37 "../../../../../ClimbSafePersistence.ump"
  private static final long serialVersionUID = 3L ;

  
}