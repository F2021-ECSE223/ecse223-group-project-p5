/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.controller;
import javafx.scene.control.Spinner;

// line 48 "../../../../../ClimbSafeTransferObjects.ump"
public class TOEquipment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOEquipment Attributes
  private String name;
  private int weight;
  private int pricePerWeek;
  private Spinner mpQuantity;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOEquipment(String aName, int aWeight, int aPricePerWeek, Spinner aMpQuantity)
  {
    name = aName;
    weight = aWeight;
    pricePerWeek = aPricePerWeek;
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

  public int getWeight()
  {
    return weight;
  }

  public int getPricePerWeek()
  {
    return pricePerWeek;
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
            "weight" + ":" + getWeight()+ "," +
            "pricePerWeek" + ":" + getPricePerWeek()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "mpQuantity" + "=" + (getMpQuantity() != null ? !getMpQuantity().equals(this)  ? getMpQuantity().toString().replaceAll("  ","    ") : "this" : "null");
  }
}