/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.controller;

// line 66 "../../../../../ClimbSafeTransferObjects.ump"
public class TOBundleItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOBundleItem Attributes
  private int quantity;
  private String equipmentBundleName;
  private String equipmentName;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOBundleItem(int aQuantity, String aEquipmentBundleName, String aEquipmentName)
  {
    quantity = aQuantity;
    equipmentBundleName = aEquipmentBundleName;
    equipmentName = aEquipmentName;
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

  public boolean setEquipmentBundleName(String aEquipmentBundleName)
  {
    boolean wasSet = false;
    equipmentBundleName = aEquipmentBundleName;
    wasSet = true;
    return wasSet;
  }

  public boolean setEquipmentName(String aEquipmentName)
  {
    boolean wasSet = false;
    equipmentName = aEquipmentName;
    wasSet = true;
    return wasSet;
  }

  public int getQuantity()
  {
    return quantity;
  }

  public String getEquipmentBundleName()
  {
    return equipmentBundleName;
  }

  public String getEquipmentName()
  {
    return equipmentName;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "," +
            "equipmentBundleName" + ":" + getEquipmentBundleName()+ "," +
            "equipmentName" + ":" + getEquipmentName()+ "]";
  }
}