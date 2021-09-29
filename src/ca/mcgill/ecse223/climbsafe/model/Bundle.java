/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse223.climbsafe.model;
import java.util.*;

// line 80 "../../../../../domain_model.ump"
public class Bundle
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Bundle Attributes
  private String name;
  private double discountPercentage;

  //Bundle Associations
  private List<Equipment> inclusions;
  private ClimbSafe climbSafe;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Bundle(String aName, double aDiscountPercentage, ClimbSafe aClimbSafe, Equipment... allInclusions)
  {
    name = aName;
    discountPercentage = aDiscountPercentage;
    inclusions = new ArrayList<Equipment>();
    boolean didAddInclusions = setInclusions(allInclusions);
    if (!didAddInclusions)
    {
      throw new RuntimeException("Unable to create Bundle, must have at least 2 inclusions. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddClimbSafe = setClimbSafe(aClimbSafe);
    if (!didAddClimbSafe)
    {
      throw new RuntimeException("Unable to create bundle due to climbSafe. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
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

  public boolean setDiscountPercentage(double aDiscountPercentage)
  {
    boolean wasSet = false;
    discountPercentage = aDiscountPercentage;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public double getDiscountPercentage()
  {
    return discountPercentage;
  }
  /* Code from template association_GetMany */
  public Equipment getInclusion(int index)
  {
    Equipment aInclusion = inclusions.get(index);
    return aInclusion;
  }

  /**
   * must contain at least 2 pieces of equipment
   */
  public List<Equipment> getInclusions()
  {
    List<Equipment> newInclusions = Collections.unmodifiableList(inclusions);
    return newInclusions;
  }

  public int numberOfInclusions()
  {
    int number = inclusions.size();
    return number;
  }

  public boolean hasInclusions()
  {
    boolean has = inclusions.size() > 0;
    return has;
  }

  public int indexOfInclusion(Equipment aInclusion)
  {
    int index = inclusions.indexOf(aInclusion);
    return index;
  }
  /* Code from template association_GetOne */
  public ClimbSafe getClimbSafe()
  {
    return climbSafe;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfInclusions()
  {
    return 2;
  }
  /* Code from template association_AddUnidirectionalMStar */
  public boolean addInclusion(Equipment aInclusion)
  {
    boolean wasAdded = false;
    if (inclusions.contains(aInclusion)) { return false; }
    inclusions.add(aInclusion);
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeInclusion(Equipment aInclusion)
  {
    boolean wasRemoved = false;
    if (!inclusions.contains(aInclusion))
    {
      return wasRemoved;
    }

    if (numberOfInclusions() <= minimumNumberOfInclusions())
    {
      return wasRemoved;
    }

    inclusions.remove(aInclusion);
    wasRemoved = true;
    return wasRemoved;
  }
  /* Code from template association_SetUnidirectionalMStar */
  public boolean setInclusions(Equipment... newInclusions)
  {
    boolean wasSet = false;
    ArrayList<Equipment> verifiedInclusions = new ArrayList<Equipment>();
    for (Equipment aInclusion : newInclusions)
    {
      if (verifiedInclusions.contains(aInclusion))
      {
        continue;
      }
      verifiedInclusions.add(aInclusion);
    }

    if (verifiedInclusions.size() != newInclusions.length || verifiedInclusions.size() < minimumNumberOfInclusions())
    {
      return wasSet;
    }

    inclusions.clear();
    inclusions.addAll(verifiedInclusions);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addInclusionAt(Equipment aInclusion, int index)
  {  
    boolean wasAdded = false;
    if(addInclusion(aInclusion))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInclusions()) { index = numberOfInclusions() - 1; }
      inclusions.remove(aInclusion);
      inclusions.add(index, aInclusion);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveInclusionAt(Equipment aInclusion, int index)
  {
    boolean wasAdded = false;
    if(inclusions.contains(aInclusion))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfInclusions()) { index = numberOfInclusions() - 1; }
      inclusions.remove(aInclusion);
      inclusions.add(index, aInclusion);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addInclusionAt(aInclusion, index);
    }
    return wasAdded;
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
      existingClimbSafe.removeBundle(this);
    }
    climbSafe.addBundle(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    inclusions.clear();
    ClimbSafe placeholderClimbSafe = climbSafe;
    this.climbSafe = null;
    if(placeholderClimbSafe != null)
    {
      placeholderClimbSafe.removeBundle(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "discountPercentage" + ":" + getDiscountPercentage()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "climbSafe = "+(getClimbSafe()!=null?Integer.toHexString(System.identityHashCode(getClimbSafe())):"null");
  }
}