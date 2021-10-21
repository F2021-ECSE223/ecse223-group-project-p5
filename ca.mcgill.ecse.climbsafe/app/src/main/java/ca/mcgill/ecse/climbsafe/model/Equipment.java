/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.model;
import java.util.*;

// line 57 "../../../../../ClimbSafe.ump"
public class Equipment extends BookableItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Equipment Attributes
  private int weight;
  private int pricePerWeek;

  //Equipment Associations
  private ClimbSafe climbSafe;
  private List<BundleItem> bundleItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Equipment(String aName, int aWeight, int aPricePerWeek, ClimbSafe aClimbSafe)
  {
    super(aName);
    weight = aWeight;
    pricePerWeek = aPricePerWeek;
    boolean didAddClimbSafe = setClimbSafe(aClimbSafe);
    if (!didAddClimbSafe)
    {
      throw new RuntimeException("Unable to create equipment due to climbSafe. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    bundleItems = new ArrayList<BundleItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWeight(int aWeight)
  {
    boolean wasSet = false;
    weight = aWeight;
    wasSet = true;
    return wasSet;
  }

  public boolean setPricePerWeek(int aPricePerWeek)
  {
    boolean wasSet = false;
    pricePerWeek = aPricePerWeek;
    wasSet = true;
    return wasSet;
  }

  public int getWeight()
  {
    return weight;
  }

  public int getPricePerWeek()
  {
    return pricePerWeek;
  }
  /* Code from template association_GetOne */
  public ClimbSafe getClimbSafe()
  {
    return climbSafe;
  }
  /* Code from template association_GetMany */
  public BundleItem getBundleItem(int index)
  {
    BundleItem aBundleItem = bundleItems.get(index);
    return aBundleItem;
  }

  public List<BundleItem> getBundleItems()
  {
    List<BundleItem> newBundleItems = Collections.unmodifiableList(bundleItems);
    return newBundleItems;
  }

  public int numberOfBundleItems()
  {
    int number = bundleItems.size();
    return number;
  }

  public boolean hasBundleItems()
  {
    boolean has = bundleItems.size() > 0;
    return has;
  }

  public int indexOfBundleItem(BundleItem aBundleItem)
  {
    int index = bundleItems.indexOf(aBundleItem);
    return index;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBundleItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BundleItem addBundleItem(int aQuantity, ClimbSafe aClimbSafe, EquipmentBundle aBundle)
  {
    return new BundleItem(aQuantity, aClimbSafe, aBundle, this);
  }

  public boolean addBundleItem(BundleItem aBundleItem)
  {
    boolean wasAdded = false;
    if (bundleItems.contains(aBundleItem)) { return false; }
    Equipment existingEquipment = aBundleItem.getEquipment();
    boolean isNewEquipment = existingEquipment != null && !this.equals(existingEquipment);
    if (isNewEquipment)
    {
      aBundleItem.setEquipment(this);
    }
    else
    {
      bundleItems.add(aBundleItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBundleItem(BundleItem aBundleItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aBundleItem, as it must always have a equipment
    if (!this.equals(aBundleItem.getEquipment()))
    {
      bundleItems.remove(aBundleItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBundleItemAt(BundleItem aBundleItem, int index)
  {  
    boolean wasAdded = false;
    if(addBundleItem(aBundleItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundleItems()) { index = numberOfBundleItems() - 1; }
      bundleItems.remove(aBundleItem);
      bundleItems.add(index, aBundleItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBundleItemAt(BundleItem aBundleItem, int index)
  {
    boolean wasAdded = false;
    if(bundleItems.contains(aBundleItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundleItems()) { index = numberOfBundleItems() - 1; }
      bundleItems.remove(aBundleItem);
      bundleItems.add(index, aBundleItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBundleItemAt(aBundleItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ClimbSafe placeholderClimbSafe = climbSafe;
    this.climbSafe = null;
    if(placeholderClimbSafe != null)
    {
      placeholderClimbSafe.removeEquipment(this);
    }
    for(int i=bundleItems.size(); i > 0; i--)
    {
      BundleItem aBundleItem = bundleItems.get(i - 1);
      aBundleItem.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "weight" + ":" + getWeight()+ "," +
            "pricePerWeek" + ":" + getPricePerWeek()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "climbSafe = "+(getClimbSafe()!=null?Integer.toHexString(System.identityHashCode(getClimbSafe())):"null");
  }
}