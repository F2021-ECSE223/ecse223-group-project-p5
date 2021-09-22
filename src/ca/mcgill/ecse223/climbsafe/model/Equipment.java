/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse223.climbsafe.model;

// line 56 "../../../../../domain_model.ump"
public class Equipment extends RentableItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Equipment Associations
  private Bundle bundle;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Equipment(int aCost, int aDiscountPrice, Bundle aBundle)
  {
    super(aCost, aDiscountPrice);
    boolean didAddBundle = setBundle(aBundle);
    if (!didAddBundle)
    {
      throw new RuntimeException("Unable to create equipment due to bundle. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Bundle getBundle()
  {
    return bundle;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBundle(Bundle aBundle)
  {
    boolean wasSet = false;
    if (aBundle == null)
    {
      return wasSet;
    }

    Bundle existingBundle = bundle;
    bundle = aBundle;
    if (existingBundle != null && !existingBundle.equals(aBundle))
    {
      existingBundle.removeEquipment(this);
    }
    bundle.addEquipment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Bundle placeholderBundle = bundle;
    this.bundle = null;
    if(placeholderBundle != null)
    {
      placeholderBundle.removeEquipment(this);
    }
    super.delete();
  }

}