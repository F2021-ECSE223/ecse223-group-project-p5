/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.controller;

// line 53 "../../../../../ClimbSafeTransferObjects.ump"
public class TOEquipmentBundle
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOEquipmentBundle Attributes
  private String name;
  private int discount;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOEquipmentBundle(String aName, int aDiscount)
  {
    name = aName;
    discount = aDiscount;
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

  public boolean setDiscount(int aDiscount)
  {
    boolean wasSet = false;
    discount = aDiscount;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public int getDiscount()
  {
    return discount;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "discount" + ":" + getDiscount()+ "]";
  }
}