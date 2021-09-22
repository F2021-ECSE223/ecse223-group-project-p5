/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse223.climbsafe.model;

// line 50 "../../../../../domain_model.ump"
public abstract class RentableItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //RentableItem Attributes
  private int cost;
  private int discountPrice;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public RentableItem(int aCost, int aDiscountPrice)
  {
    cost = aCost;
    discountPrice = aDiscountPrice;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setCost(int aCost)
  {
    boolean wasSet = false;
    cost = aCost;
    wasSet = true;
    return wasSet;
  }

  public boolean setDiscountPrice(int aDiscountPrice)
  {
    boolean wasSet = false;
    discountPrice = aDiscountPrice;
    wasSet = true;
    return wasSet;
  }

  public int getCost()
  {
    return cost;
  }

  public int getDiscountPrice()
  {
    return discountPrice;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "cost" + ":" + getCost()+ "," +
            "discountPrice" + ":" + getDiscountPrice()+ "]";
  }
}