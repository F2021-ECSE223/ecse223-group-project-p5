/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.controller;

// line 42 "../../../../../ClimbSafeTransferObjects.ump"
public class TOBookedItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOBookedItem Attributes
  private int quantity;
  private String memberEmail;
  private String bookableItemName;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOBookedItem(int aQuantity, String aMemberEmail, String aBookableItemName)
  {
    quantity = aQuantity;
    memberEmail = aMemberEmail;
    bookableItemName = aBookableItemName;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setQuantity(int aQuantity)
  {
    boolean wasSet = false;
    quantity = aQuantity;
    wasSet = true;
    return wasSet;
  }

  public boolean setMemberEmail(String aMemberEmail)
  {
    boolean wasSet = false;
    memberEmail = aMemberEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setBookableItemName(String aBookableItemName)
  {
    boolean wasSet = false;
    bookableItemName = aBookableItemName;
    wasSet = true;
    return wasSet;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public String getMemberEmail()
  {
    return memberEmail;
  }

  public String getBookableItemName()
  {
    return bookableItemName;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "," +
            "memberEmail" + ":" + getMemberEmail()+ "," +
            "bookableItemName" + ":" + getBookableItemName()+ "]";
  }
}