/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse223.climbsafe.model;

// line 74 "../../../../../domain_model.ump"
public class Equipment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Equipment Attributes
  private String name;
  private int weight;
  private int price;

  //Equipment Associations
  private ClimbSafe climbSafe;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Equipment(String aName, int aWeight, int aPrice, ClimbSafe aClimbSafe)
  {
    name = aName;
    weight = aWeight;
    price = aPrice;
    boolean didAddClimbSafe = setClimbSafe(aClimbSafe);
    if (!didAddClimbSafe)
    {
      throw new RuntimeException("Unable to create equipment due to climbSafe. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public boolean setWeight(int aWeight)
  {
    boolean wasSet = false;
    weight = aWeight;
    wasSet = true;
    return wasSet;
  }

  public boolean setPrice(int aPrice)
  {
    boolean wasSet = false;
    price = aPrice;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getWeight()
  {
    return weight;
  }

  public int getPrice()
  {
    return price;
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
      existingClimbSafe.removeEquipment(this);
    }
    climbSafe.addEquipment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ClimbSafe placeholderClimbSafe = climbSafe;
    this.climbSafe = null;
    if(placeholderClimbSafe != null)
    {
      placeholderClimbSafe.removeEquipment(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "weight" + ":" + getWeight()+ "," +
            "price" + ":" + getPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "climbSafe = "+(getClimbSafe()!=null?Integer.toHexString(System.identityHashCode(getClimbSafe())):"null");
  }
}