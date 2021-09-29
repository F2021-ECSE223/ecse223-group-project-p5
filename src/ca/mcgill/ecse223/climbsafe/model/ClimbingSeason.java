/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse223.climbsafe.model;
import java.sql.Date;
import java.util.*;

// line 57 "../../../../../domain_model.ump"
public class ClimbingSeason
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ClimbingSeason Attributes
  private Date start;
  private Date end;
  private int guideCost;

  //ClimbingSeason Associations
  private List<Member> members;
  private List<Guide> guides;
  private List<Assignment> assignments;
  private ClimbSafe climbSafe;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ClimbingSeason(Date aStart, Date aEnd, int aGuideCost, ClimbSafe aClimbSafe)
  {
    start = aStart;
    end = aEnd;
    guideCost = aGuideCost;
    members = new ArrayList<Member>();
    guides = new ArrayList<Guide>();
    assignments = new ArrayList<Assignment>();
    boolean didAddClimbSafe = setClimbSafe(aClimbSafe);
    if (!didAddClimbSafe)
    {
      throw new RuntimeException("Unable to create season due to climbSafe. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setStart(Date aStart)
  {
    boolean wasSet = false;
    start = aStart;
    wasSet = true;
    return wasSet;
  }

  public boolean setEnd(Date aEnd)
  {
    boolean wasSet = false;
    end = aEnd;
    wasSet = true;
    return wasSet;
  }

  public boolean setGuideCost(int aGuideCost)
  {
    boolean wasSet = false;
    guideCost = aGuideCost;
    wasSet = true;
    return wasSet;
  }

  /**
   * beginning week of climbing period
   */
  public Date getStart()
  {
    return start;
  }

  /**
   * end week of climbing period
   */
  public Date getEnd()
  {
    return end;
  }

  /**
   * constant for all guides
   */
  public int getGuideCost()
  {
    return guideCost;
  }
  /* Code from template association_GetMany */
  public Member getMember(int index)
  {
    Member aMember = members.get(index);
    return aMember;
  }

  /**
   * to access all users
   */
  public List<Member> getMembers()
  {
    List<Member> newMembers = Collections.unmodifiableList(members);
    return newMembers;
  }

  public int numberOfMembers()
  {
    int number = members.size();
    return number;
  }

  public boolean hasMembers()
  {
    boolean has = members.size() > 0;
    return has;
  }

  public int indexOfMember(Member aMember)
  {
    int index = members.indexOf(aMember);
    return index;
  }
  /* Code from template association_GetMany */
  public Guide getGuide(int index)
  {
    Guide aGuide = guides.get(index);
    return aGuide;
  }

  /**
   * to access all guides
   */
  public List<Guide> getGuides()
  {
    List<Guide> newGuides = Collections.unmodifiableList(guides);
    return newGuides;
  }

  public int numberOfGuides()
  {
    int number = guides.size();
    return number;
  }

  public boolean hasGuides()
  {
    boolean has = guides.size() > 0;
    return has;
  }

  public int indexOfGuide(Guide aGuide)
  {
    int index = guides.indexOf(aGuide);
    return index;
  }
  /* Code from template association_GetMany */
  public Assignment getAssignment(int index)
  {
    Assignment aAssignment = assignments.get(index);
    return aAssignment;
  }

  /**
   * all assignments that the admin makes
   */
  public List<Assignment> getAssignments()
  {
    List<Assignment> newAssignments = Collections.unmodifiableList(assignments);
    return newAssignments;
  }

  public int numberOfAssignments()
  {
    int number = assignments.size();
    return number;
  }

  public boolean hasAssignments()
  {
    boolean has = assignments.size() > 0;
    return has;
  }

  public int indexOfAssignment(Assignment aAssignment)
  {
    int index = assignments.indexOf(aAssignment);
    return index;
  }
  /* Code from template association_GetOne */
  public ClimbSafe getClimbSafe()
  {
    return climbSafe;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfMembers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Member addMember(String aUsername, String aPassword, ClimbSafe aClimbSafe, String aName, String aEmergencyContact, int aNoWeeks, boolean aHiredGuide, boolean aHotelStay)
  {
    return new Member(aUsername, aPassword, aClimbSafe, aName, aEmergencyContact, aNoWeeks, aHiredGuide, aHotelStay, this);
  }

  public boolean addMember(Member aMember)
  {
    boolean wasAdded = false;
    if (members.contains(aMember)) { return false; }
    ClimbingSeason existingSeason = aMember.getSeason();
    boolean isNewSeason = existingSeason != null && !this.equals(existingSeason);
    if (isNewSeason)
    {
      aMember.setSeason(this);
    }
    else
    {
      members.add(aMember);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeMember(Member aMember)
  {
    boolean wasRemoved = false;
    //Unable to remove aMember, as it must always have a season
    if (!this.equals(aMember.getSeason()))
    {
      members.remove(aMember);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addMemberAt(Member aMember, int index)
  {  
    boolean wasAdded = false;
    if(addMember(aMember))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMembers()) { index = numberOfMembers() - 1; }
      members.remove(aMember);
      members.add(index, aMember);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveMemberAt(Member aMember, int index)
  {
    boolean wasAdded = false;
    if(members.contains(aMember))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfMembers()) { index = numberOfMembers() - 1; }
      members.remove(aMember);
      members.add(index, aMember);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addMemberAt(aMember, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGuides()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Guide addGuide(String aUsername, String aPassword, ClimbSafe aClimbSafe, String aName, String aEmergencyContact)
  {
    return new Guide(aUsername, aPassword, aClimbSafe, aName, aEmergencyContact, this);
  }

  public boolean addGuide(Guide aGuide)
  {
    boolean wasAdded = false;
    if (guides.contains(aGuide)) { return false; }
    ClimbingSeason existingSeason = aGuide.getSeason();
    boolean isNewSeason = existingSeason != null && !this.equals(existingSeason);
    if (isNewSeason)
    {
      aGuide.setSeason(this);
    }
    else
    {
      guides.add(aGuide);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGuide(Guide aGuide)
  {
    boolean wasRemoved = false;
    //Unable to remove aGuide, as it must always have a season
    if (!this.equals(aGuide.getSeason()))
    {
      guides.remove(aGuide);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGuideAt(Guide aGuide, int index)
  {  
    boolean wasAdded = false;
    if(addGuide(aGuide))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGuides()) { index = numberOfGuides() - 1; }
      guides.remove(aGuide);
      guides.add(index, aGuide);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGuideAt(Guide aGuide, int index)
  {
    boolean wasAdded = false;
    if(guides.contains(aGuide))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGuides()) { index = numberOfGuides() - 1; }
      guides.remove(aGuide);
      guides.add(index, aGuide);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGuideAt(aGuide, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfAssignments()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Assignment addAssignment(int aStartWeek, int aEndWeek, Member aMember)
  {
    return new Assignment(aStartWeek, aEndWeek, aMember, this);
  }

  public boolean addAssignment(Assignment aAssignment)
  {
    boolean wasAdded = false;
    if (assignments.contains(aAssignment)) { return false; }
    ClimbingSeason existingSeason = aAssignment.getSeason();
    boolean isNewSeason = existingSeason != null && !this.equals(existingSeason);
    if (isNewSeason)
    {
      aAssignment.setSeason(this);
    }
    else
    {
      assignments.add(aAssignment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeAssignment(Assignment aAssignment)
  {
    boolean wasRemoved = false;
    //Unable to remove aAssignment, as it must always have a season
    if (!this.equals(aAssignment.getSeason()))
    {
      assignments.remove(aAssignment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addAssignmentAt(Assignment aAssignment, int index)
  {  
    boolean wasAdded = false;
    if(addAssignment(aAssignment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignments()) { index = numberOfAssignments() - 1; }
      assignments.remove(aAssignment);
      assignments.add(index, aAssignment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveAssignmentAt(Assignment aAssignment, int index)
  {
    boolean wasAdded = false;
    if(assignments.contains(aAssignment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfAssignments()) { index = numberOfAssignments() - 1; }
      assignments.remove(aAssignment);
      assignments.add(index, aAssignment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addAssignmentAt(aAssignment, index);
    }
    return wasAdded;
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
      existingClimbSafe.removeSeason(this);
    }
    climbSafe.addSeason(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (members.size() > 0)
    {
      Member aMember = members.get(members.size() - 1);
      aMember.delete();
      members.remove(aMember);
    }
    
    while (guides.size() > 0)
    {
      Guide aGuide = guides.get(guides.size() - 1);
      aGuide.delete();
      guides.remove(aGuide);
    }
    
    while (assignments.size() > 0)
    {
      Assignment aAssignment = assignments.get(assignments.size() - 1);
      aAssignment.delete();
      assignments.remove(aAssignment);
    }
    
    ClimbSafe placeholderClimbSafe = climbSafe;
    this.climbSafe = null;
    if(placeholderClimbSafe != null)
    {
      placeholderClimbSafe.removeSeason(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "guideCost" + ":" + getGuideCost()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "start" + "=" + (getStart() != null ? !getStart().equals(this)  ? getStart().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "end" + "=" + (getEnd() != null ? !getEnd().equals(this)  ? getEnd().toString().replaceAll("  ","    ") : "this" : "null") + System.getProperties().getProperty("line.separator") +
            "  " + "climbSafe = "+(getClimbSafe()!=null?Integer.toHexString(System.identityHashCode(getClimbSafe())):"null");
  }
}