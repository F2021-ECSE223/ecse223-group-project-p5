/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.model;
import ca.mcgill.ecse.climbsafe.model.Member.BanStatus;

// line 12 "../../../../../ClimbSafeStates.ump"
// line 83 "../../../../../ClimbSafe.ump"
public class Assignment
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Assignment Attributes
  private String paymentCode;
  private int refundPercentage;
  private int startWeek;
  private int endWeek;

  //Assignment State Machines
  public enum AssignmentStatus { Unassigned, Assigned, Paid, Started, Finished, Cancelled }
  private AssignmentStatus assignmentStatus;

  //Assignment Associations
  private Member member;
  private Guide guide;
  private Hotel hotel;
  private ClimbSafe climbSafe;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Assignment(int aStartWeek, int aEndWeek, Member aMember, ClimbSafe aClimbSafe)
  {
    paymentCode = null;
    refundPercentage = 0;
    startWeek = aStartWeek;
    endWeek = aEndWeek;
    boolean didAddMember = setMember(aMember);
    if (!didAddMember)
    {
      throw new RuntimeException("Unable to create assignment due to member. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddClimbSafe = setClimbSafe(aClimbSafe);
    if (!didAddClimbSafe)
    {
      throw new RuntimeException("Unable to create assignment due to climbSafe. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    setAssignmentStatus(AssignmentStatus.Unassigned);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setPaymentCode(String aPaymentCode)
  {
    boolean wasSet = false;
    paymentCode = aPaymentCode;
    wasSet = true;
    return wasSet;
  }

  public boolean setRefundPercentage(int aRefundPercentage)
  {
    boolean wasSet = false;
    refundPercentage = aRefundPercentage;
    wasSet = true;
    return wasSet;
  }

  public boolean setStartWeek(int aStartWeek)
  {
    boolean wasSet = false;
    startWeek = aStartWeek;
    wasSet = true;
    return wasSet;
  }

  public boolean setEndWeek(int aEndWeek)
  {
    boolean wasSet = false;
    endWeek = aEndWeek;
    wasSet = true;
    return wasSet;
  }

  public String getPaymentCode()
  {
    return paymentCode;
  }

  public int getRefundPercentage()
  {
    return refundPercentage;
  }

  public int getStartWeek()
  {
    return startWeek;
  }

  public int getEndWeek()
  {
    return endWeek;
  }

  public String getAssignmentStatusFullName()
  {
    String answer = assignmentStatus.toString();
    return answer;
  }

  public AssignmentStatus getAssignmentStatus()
  {
    return assignmentStatus;
  }

  public boolean assign(Integer startWeek,Integer endWeek,Guide guide,Hotel hotel)
  {
    boolean wasEventProcessed = false;
    
    AssignmentStatus aAssignmentStatus = assignmentStatus;
    switch (aAssignmentStatus)
    {
      case Unassigned:
        // line 19 "../../../../../ClimbSafeStates.ump"
        doAssign(startWeek, endWeek, guide, hotel);
        setAssignmentStatus(AssignmentStatus.Assigned);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean start()
  {
    boolean wasEventProcessed = false;
    
    AssignmentStatus aAssignmentStatus = assignmentStatus;
    switch (aAssignmentStatus)
    {
      case Assigned:
        // line 25 "../../../../../ClimbSafeStates.ump"
        member.ban();
        setAssignmentStatus(AssignmentStatus.Assigned);
        wasEventProcessed = true;
        break;
      case Paid:
        if (!(isBanned()))
        {
          setAssignmentStatus(AssignmentStatus.Started);
          wasEventProcessed = true;
          break;
        }
        if (isBanned())
        {
        // line 65 "../../../../../ClimbSafeStates.ump"
          rejectBanAction("start");
          setAssignmentStatus(AssignmentStatus.Paid);
          wasEventProcessed = true;
          break;
        }
        break;
      case Finished:
        // line 101 "../../../../../ClimbSafeStates.ump"
        rejectTripAction("start", "finished");
        setAssignmentStatus(AssignmentStatus.Finished);
        wasEventProcessed = true;
        break;
      case Cancelled:
        // line 115 "../../../../../ClimbSafeStates.ump"
        rejectTripAction("start", "been cancelled");
        setAssignmentStatus(AssignmentStatus.Cancelled);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean pay(String code)
  {
    boolean wasEventProcessed = false;
    
    AssignmentStatus aAssignmentStatus = assignmentStatus;
    switch (aAssignmentStatus)
    {
      case Assigned:
        if (!(isBanned()))
        {
        // line 29 "../../../../../ClimbSafeStates.ump"
          doPay(code);
          setAssignmentStatus(AssignmentStatus.Paid);
          wasEventProcessed = true;
          break;
        }
        if (isBanned())
        {
        // line 33 "../../../../../ClimbSafeStates.ump"
          rejectBanAction("pay for");
          setAssignmentStatus(AssignmentStatus.Assigned);
          wasEventProcessed = true;
          break;
        }
        break;
      case Paid:
        // line 51 "../../../../../ClimbSafeStates.ump"
        rejectRedundantPayment();
        setAssignmentStatus(AssignmentStatus.Paid);
        wasEventProcessed = true;
        break;
      case Started:
        // line 91 "../../../../../ClimbSafeStates.ump"
        rejectRedundantPayment();
        setAssignmentStatus(AssignmentStatus.Started);
        wasEventProcessed = true;
        break;
      case Finished:
        // line 97 "../../../../../ClimbSafeStates.ump"
        rejectTripAction("pay for", "finished");
        setAssignmentStatus(AssignmentStatus.Finished);
        wasEventProcessed = true;
        break;
      case Cancelled:
        // line 111 "../../../../../ClimbSafeStates.ump"
        rejectTripAction("pay for", "been cancelled");
        setAssignmentStatus(AssignmentStatus.Cancelled);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean cancel()
  {
    boolean wasEventProcessed = false;
    
    AssignmentStatus aAssignmentStatus = assignmentStatus;
    switch (aAssignmentStatus)
    {
      case Assigned:
        if (!(isBanned()))
        {
        // line 37 "../../../../../ClimbSafeStates.ump"
          setRefundPercentage(100);
          setAssignmentStatus(AssignmentStatus.Cancelled);
          wasEventProcessed = true;
          break;
        }
        if (isBanned())
        {
        // line 41 "../../../../../ClimbSafeStates.ump"
          rejectBanAction("cancel");
          setAssignmentStatus(AssignmentStatus.Assigned);
          wasEventProcessed = true;
          break;
        }
        break;
      case Paid:
        if (!(isBanned()))
        {
        // line 55 "../../../../../ClimbSafeStates.ump"
          setRefundPercentage(50);
          setAssignmentStatus(AssignmentStatus.Cancelled);
          wasEventProcessed = true;
          break;
        }
        if (isBanned())
        {
        // line 59 "../../../../../ClimbSafeStates.ump"
          rejectBanAction("cancel");
          setAssignmentStatus(AssignmentStatus.Paid);
          wasEventProcessed = true;
          break;
        }
        break;
      case Started:
        if (!(isBanned()))
        {
        // line 75 "../../../../../ClimbSafeStates.ump"
          setRefundPercentage(10);
          setAssignmentStatus(AssignmentStatus.Cancelled);
          wasEventProcessed = true;
          break;
        }
        if (isBanned())
        {
        // line 79 "../../../../../ClimbSafeStates.ump"
          rejectBanAction("cancel");
          setAssignmentStatus(AssignmentStatus.Started);
          wasEventProcessed = true;
          break;
        }
        break;
      case Finished:
        // line 105 "../../../../../ClimbSafeStates.ump"
        rejectTripAction("cancel", "finished");
        setAssignmentStatus(AssignmentStatus.Finished);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean finish()
  {
    boolean wasEventProcessed = false;
    
    AssignmentStatus aAssignmentStatus = assignmentStatus;
    switch (aAssignmentStatus)
    {
      case Assigned:
        // line 45 "../../../../../ClimbSafeStates.ump"
        rejectTripAction("finish", "not started");
        setAssignmentStatus(AssignmentStatus.Assigned);
        wasEventProcessed = true;
        break;
      case Paid:
        // line 69 "../../../../../ClimbSafeStates.ump"
        rejectTripAction("finish", "not started");
        setAssignmentStatus(AssignmentStatus.Paid);
        wasEventProcessed = true;
        break;
      case Started:
        if (!(isBanned()))
        {
        // line 83 "../../../../../ClimbSafeStates.ump"
          setRefundPercentage(0);
          setAssignmentStatus(AssignmentStatus.Finished);
          wasEventProcessed = true;
          break;
        }
        if (isBanned())
        {
        // line 87 "../../../../../ClimbSafeStates.ump"
          rejectBanAction("finish");
          setAssignmentStatus(AssignmentStatus.Started);
          wasEventProcessed = true;
          break;
        }
        break;
      case Cancelled:
        // line 119 "../../../../../ClimbSafeStates.ump"
        rejectTripAction("finish", "been cancelled");
        setAssignmentStatus(AssignmentStatus.Cancelled);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setAssignmentStatus(AssignmentStatus aAssignmentStatus)
  {
    assignmentStatus = aAssignmentStatus;
  }
  /* Code from template association_GetOne */
  public Member getMember()
  {
    return member;
  }
  /* Code from template association_GetOne */
  public Guide getGuide()
  {
    return guide;
  }

  public boolean hasGuide()
  {
    boolean has = guide != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Hotel getHotel()
  {
    return hotel;
  }

  public boolean hasHotel()
  {
    boolean has = hotel != null;
    return has;
  }
  /* Code from template association_GetOne */
  public ClimbSafe getClimbSafe()
  {
    return climbSafe;
  }
  /* Code from template association_SetOneToOptionalOne */
  public boolean setMember(Member aNewMember)
  {
    boolean wasSet = false;
    if (aNewMember == null)
    {
      //Unable to setMember to null, as assignment must always be associated to a member
      return wasSet;
    }
    
    Assignment existingAssignment = aNewMember.getAssignment();
    if (existingAssignment != null && !equals(existingAssignment))
    {
      //Unable to setMember, the current member already has a assignment, which would be orphaned if it were re-assigned
      return wasSet;
    }
    
    Member anOldMember = member;
    member = aNewMember;
    member.setAssignment(this);

    if (anOldMember != null)
    {
      anOldMember.setAssignment(null);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setGuide(Guide aGuide)
  {
    boolean wasSet = false;
    Guide existingGuide = guide;
    guide = aGuide;
    if (existingGuide != null && !existingGuide.equals(aGuide))
    {
      existingGuide.removeAssignment(this);
    }
    if (aGuide != null)
    {
      aGuide.addAssignment(this);
    }
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setHotel(Hotel aHotel)
  {
    boolean wasSet = false;
    Hotel existingHotel = hotel;
    hotel = aHotel;
    if (existingHotel != null && !existingHotel.equals(aHotel))
    {
      existingHotel.removeAssignment(this);
    }
    if (aHotel != null)
    {
      aHotel.addAssignment(this);
    }
    wasSet = true;
    return wasSet;
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
      existingClimbSafe.removeAssignment(this);
    }
    climbSafe.addAssignment(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Member existingMember = member;
    member = null;
    if (existingMember != null)
    {
      existingMember.setAssignment(null);
    }
    if (guide != null)
    {
      Guide placeholderGuide = guide;
      this.guide = null;
      placeholderGuide.removeAssignment(this);
    }
    if (hotel != null)
    {
      Hotel placeholderHotel = hotel;
      this.hotel = null;
      placeholderHotel.removeAssignment(this);
    }
    ClimbSafe placeholderClimbSafe = climbSafe;
    this.climbSafe = null;
    if(placeholderClimbSafe != null)
    {
      placeholderClimbSafe.removeAssignment(this);
    }
  }


  /**
   * 
   * Function to validate and store payment code for a member
   * 
   * @param code the payment code
   * @author Michael Grieco
   */
  // line 132 "../../../../../ClimbSafeStates.ump"
   private void doPay(String code){
    if (code.equals("")) {
      throw new RuntimeException("Invalid authorization code");
    }

    setPaymentCode(code);
  }


  /**
   * 
   * Function to set up assignment instance variables
   * 
   * @param startWeek the first week of the assignment
   * @param endWeek the last week of the assignment
   * @param guide the Guide if the member requested one, null otherwise
   * @param hotel the Hotel if the member requested one, null otherwise
   * @author Michael Grieco
   */
  // line 149 "../../../../../ClimbSafeStates.ump"
   private void doAssign(int startWeek, int endWeek, Guide guide, Hotel hotel){
    setStartWeek(startWeek);
    setEndWeek(endWeek);
    if (guide != null) {
      setGuide(guide);
    }
    if (hotel != null) {
      setHotel(hotel);
    }
  }


  /**
   * 
   * Function serving as a guard condition to determine if the member on
   * the assignment is banned
   * 
   * @return true if the member's current state is Banned, false otherwise
   * @author Micahel Grieco
   */
  // line 167 "../../../../../ClimbSafeStates.ump"
   private boolean isBanned(){
    return member.getBanStatus().equals(BanStatus.Banned);
  }


  /**
   * 
   * Function to be called to throw an error rejecting a requested transition
   * 
   * @param verb the requested action
   * @param reason the reason the action cannot be completed
   * @author Michael Grieco
   */
  // line 178 "../../../../../ClimbSafeStates.ump"
   private void rejectTripAction(String verb, String reason){
    throw new RuntimeException("Cannot " + verb + " a trip which has " + reason);
  }


  /**
   * 
   * Function to be called to throw an error rejecting a requested payment if
   * the trip has already been paid for
   * 
   * @author Michael Grieco
   */
  // line 188 "../../../../../ClimbSafeStates.ump"
   private void rejectRedundantPayment(){
    throw new RuntimeException("Trip has already been paid for");
  }


  /**
   * 
   * Function to be called to throw an error rejecting a requested action if
   * the attached member is banned
   * 
   * @param verb the requested action
   * @author Michael Grieco
   */
  // line 199 "../../../../../ClimbSafeStates.ump"
   private void rejectBanAction(String verb){
    throw new RuntimeException("Cannot " + verb + " the trip due to a ban");
  }


  public String toString()
  {
    return super.toString() + "["+
            "paymentCode" + ":" + getPaymentCode()+ "," +
            "refundPercentage" + ":" + getRefundPercentage()+ "," +
            "startWeek" + ":" + getStartWeek()+ "," +
            "endWeek" + ":" + getEndWeek()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "member = "+(getMember()!=null?Integer.toHexString(System.identityHashCode(getMember())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "guide = "+(getGuide()!=null?Integer.toHexString(System.identityHashCode(getGuide())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "hotel = "+(getHotel()!=null?Integer.toHexString(System.identityHashCode(getHotel())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "climbSafe = "+(getClimbSafe()!=null?Integer.toHexString(System.identityHashCode(getClimbSafe())):"null");
  }
}