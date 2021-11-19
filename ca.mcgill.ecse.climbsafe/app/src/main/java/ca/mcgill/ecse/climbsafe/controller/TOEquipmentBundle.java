/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.controller;
import javafx.scene.control.Spinner;

// line 57 "../../../../../ClimbSafeTransferObjects.ump"
public class TOEquipmentBundle
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOEquipmentBundle Attributes
  private String name;
  private int discount;
  private int noDiscountPrice;
  private Spinner mpQuantity;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOEquipmentBundle(String aName, int aDiscount, int aNoDiscountPrice, Spinner aMpQuantity)
  {
    name = aName;
    discount = aDiscount;
    noDiscountPrice = aNoDiscountPrice;
    mpQuantity = aMpQuantity;
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

  public boolean setNoDiscountPrice(int aNoDiscountPrice)
  {
    boolean wasSet = false;
    noDiscountPrice = aNoDiscountPrice;
    wasSet = true;
    return wasSet;
  }

  public boolean setMpQuantity(Spinner aMpQuantity)
  {
    boolean wasSet = false;
    mpQuantity = aMpQuantity;
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

  /**
   * helper stuff for MembersPage
   */
  public int getNoDiscountPrice()
  {
    return noDiscountPrice;
  }

  public Spinner getMpQuantity()
  {
    return mpQuantity;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "discount" + ":" + getDiscount()+ "," +
            "noDiscountPrice" + ":" + getNoDiscountPrice()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "mpQuantity" + "=" + (getMpQuantity() != null ? !getMpQuantity().equals(this)  ? getMpQuantity().toString().replaceAll("  ","    ") : "this" : "null");
  }
}