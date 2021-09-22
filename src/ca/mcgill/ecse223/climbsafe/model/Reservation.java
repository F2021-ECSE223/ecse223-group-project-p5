/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse223.climbsafe.model;

// line 65 "../../../../../domain_model.ump"
public class Reservation
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Reservation Attributes
  private int week;

  //Reservation Associations
  private Member member;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Reservation(int aWeek, Member aMember)
  {
    week = aWeek;
    boolean didAddMember = setMember(aMember);
    if (!didAddMember)
    {
      throw new RuntimeException("Unable to create reservation due to member. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setWeek(int aWeek)
  {
    boolean wasSet = false;
    week = aWeek;
    wasSet = true;
    return wasSet;
  }

  /**
   * Hired guide
   * Rented equipment
   * Hotel stay
   * Total cost = {
   */
  public int getWeek()
  {
    return week;
  }
  /* Code from template association_GetOne */
  public Member getMember()
  {
    return member;
  }
  /* Code from template association_SetOneToMany */
  public boolean setMember(Member aMember)
  {
    boolean wasSet = false;
    if (aMember == null)
    {
      return wasSet;
    }

    Member existingMember = member;
    member = aMember;
    if (existingMember != null && !existingMember.equals(aMember))
    {
      existingMember.removeReservation(this);
    }
    member.addReservation(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Member placeholderMember = member;
    this.member = null;
    if(placeholderMember != null)
    {
      placeholderMember.removeReservation(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "week" + ":" + getWeek()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "member = "+(getMember()!=null?Integer.toHexString(System.identityHashCode(getMember())):"null");
  }
}