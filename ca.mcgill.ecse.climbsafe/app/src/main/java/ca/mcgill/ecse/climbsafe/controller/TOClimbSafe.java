/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.controller;
import java.sql.Date;

// line 18 "../../../../../ClimbSafeTransferObjects.ump"
public class TOClimbSafe
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //TOClimbSafe Attributes
  private Date startDate;
  private int nrWeeks;
  private int priceOfGuidePerWeek;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public TOClimbSafe(Date aStartDate, int aNrWeeks, int aPriceOfGuidePerWeek)
  {
    startDate = aStartDate;
    nrWeeks = aNrWeeks;
    priceOfGuidePerWeek = aPriceOfGuidePerWeek;
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStartDate(Date aStartDate)
  {
    boolean wasSet = false;
    startDate = aStartDate;
    wasSet = true;
    return wasSet;
  }

  public boolean setNrWeeks(int aNrWeeks)
  {
    boolean wasSet = false;
    nrWeeks = aNrWeeks;
    wasSet = true;
    return wasSet;
  }

  public boolean setPriceOfGuidePerWeek(int aPriceOfGuidePerWeek)
  {
    boolean wasSet = false;
    priceOfGuidePerWeek = aPriceOfGuidePerWeek;
    wasSet = true;
    return wasSet;
  }

  public Date getStartDate()
  {
    return startDate;
  }

  public int getNrWeeks()
  {
    return nrWeeks;
  }

  public int getPriceOfGuidePerWeek()
  {
    return priceOfGuidePerWeek;
  }

  public void delete()
  {}


  public String toString()
  {
    return super.toString() + "["+
            "nrWeeks" + ":" + getNrWeeks()+ "," +
            "priceOfGuidePerWeek" + ":" + getPriceOfGuidePerWeek()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "startDate" + "=" + (getStartDate() != null ? !getStartDate().equals(this)  ? getStartDate().toString().replaceAll("  ","    ") : "this" : "null");
  }
}