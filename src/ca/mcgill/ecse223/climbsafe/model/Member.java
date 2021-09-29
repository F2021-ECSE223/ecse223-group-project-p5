/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse223.climbsafe.model;

// line 44 "../../../../../domain_model.ump"
public class Member extends SeasonalUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Member Attributes
  private int noWeeks;
  private boolean hiredGuide;
  private boolean hotelStay;

  //Member Associations
  private ClimbingSeason season;
  private Assignment assignment;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Member(String aUsername, String aPassword, ClimbSafe aClimbSafe, String aName, String aEmergencyContact, int aNoWeeks, boolean aHiredGuide, boolean aHotelStay, ClimbingSeason aSeason)
  {
    super(aUsername, aPassword, aClimbSafe, aName, aEmergencyContact);
    noWeeks = aNoWeeks;
    hiredGuide = aHiredGuide;
    hotelStay = aHotelStay;
    boolean didAddSeason = setSeason(aSeason);
    if (!didAddSeason)
    {
      throw new RuntimeException("Unable to create member due to season. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNoWeeks(int aNoWeeks)
  {
    boolean wasSet = false;
    noWeeks = aNoWeeks;
    wasSet = true;
    return wasSet;
  }

  public boolean setHiredGuide(boolean aHiredGuide)
  {
    boolean wasSet = false;
    hiredGuide = aHiredGuide;
    wasSet = true;
    return wasSet;
  }

  public boolean setHotelStay(boolean aHotelStay)
  {
    boolean wasSet = false;
    hotelStay = aHotelStay;
    wasSet = true;
    return wasSet;
  }

  /**
   * initialize preferences for a reservation before the admin finalizes details
   * number of weeks the member wants to climb for
   */
  public int getNoWeeks()
  {
    return noWeeks;
  }

  /**
   * if the member wants to hire a guide
   */
  public boolean getHiredGuide()
  {
    return hiredGuide;
  }

  /**
   * if the member wants to stay at a hotel before and after the climb
   */
  public boolean getHotelStay()
  {
    return hotelStay;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isHiredGuide()
  {
    return hiredGuide;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isHotelStay()
  {
    return hotelStay;
  }
  /* Code from template association_GetOne */
  public ClimbingSeason getSeason()
  {
    return season;
  }
  /* Code from template association_GetOne */
  public Assignment getAssignment()
  {
    return assignment;
  }

  public boolean hasAssignment()
  {
    boolean has = assignment != null;
    return has;
  }
  /* Code from template association_SetOneToMany */
  public boolean setSeason(ClimbingSeason aSeason)
  {
    boolean wasSet = false;
    if (aSeason == null)
    {
      return wasSet;
    }

    ClimbingSeason existingSeason = season;
    season = aSeason;
    if (existingSeason != null && !existingSeason.equals(aSeason))
    {
      existingSeason.removeMember(this);
    }
    season.addMember(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToOne */
  public boolean setAssignment(Assignment aNewAssignment)
  {
    boolean wasSet = false;
    if (assignment != null && !assignment.equals(aNewAssignment) && equals(assignment.getMember()))
    {
      //Unable to setAssignment, as existing assignment would become an orphan
      return wasSet;
    }

    assignment = aNewAssignment;
    Member anOldMember = aNewAssignment != null ? aNewAssignment.getMember() : null;

    if (!this.equals(anOldMember))
    {
      if (anOldMember != null)
      {
        anOldMember.assignment = null;
      }
      if (assignment != null)
      {
        assignment.setMember(this);
      }
    }
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    ClimbingSeason placeholderSeason = season;
    this.season = null;
    if(placeholderSeason != null)
    {
      placeholderSeason.removeMember(this);
    }
    Assignment existingAssignment = assignment;
    assignment = null;
    if (existingAssignment != null)
    {
      existingAssignment.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "noWeeks" + ":" + getNoWeeks()+ "," +
            "hiredGuide" + ":" + getHiredGuide()+ "," +
            "hotelStay" + ":" + getHotelStay()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "season = "+(getSeason()!=null?Integer.toHexString(System.identityHashCode(getSeason())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "assignment = "+(getAssignment()!=null?Integer.toHexString(System.identityHashCode(getAssignment())):"null");
  }
}