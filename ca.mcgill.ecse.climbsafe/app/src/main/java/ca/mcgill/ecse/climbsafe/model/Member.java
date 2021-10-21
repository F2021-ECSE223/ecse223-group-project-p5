/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.model;
import java.util.*;

// line 39 "../../../../../ClimbSafe.ump"
public class Member extends NamedUser
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Member Attributes
  private int nrWeeks;
  private boolean guideRequired;
  private boolean hotelRequired;

  //Member Associations
  private ClimbSafe climbSafe;
  private Assignment assignment;
  private List<BookedItem> bookedItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Member(String aEmail, String aPassword, String aName, String aEmergencyContact, int aNrWeeks, boolean aGuideRequired, boolean aHotelRequired, ClimbSafe aClimbSafe)
  {
    super(aEmail, aPassword, aName, aEmergencyContact);
    nrWeeks = aNrWeeks;
    guideRequired = aGuideRequired;
    hotelRequired = aHotelRequired;
    boolean didAddClimbSafe = setClimbSafe(aClimbSafe);
    if (!didAddClimbSafe)
    {
      throw new RuntimeException("Unable to create member due to climbSafe. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    bookedItems = new ArrayList<BookedItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setNrWeeks(int aNrWeeks)
  {
    boolean wasSet = false;
    nrWeeks = aNrWeeks;
    wasSet = true;
    return wasSet;
  }

  public boolean setGuideRequired(boolean aGuideRequired)
  {
    boolean wasSet = false;
    guideRequired = aGuideRequired;
    wasSet = true;
    return wasSet;
  }

  public boolean setHotelRequired(boolean aHotelRequired)
  {
    boolean wasSet = false;
    hotelRequired = aHotelRequired;
    wasSet = true;
    return wasSet;
  }

  public int getNrWeeks()
  {
    return nrWeeks;
  }

  public boolean getGuideRequired()
  {
    return guideRequired;
  }

  public boolean getHotelRequired()
  {
    return hotelRequired;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isGuideRequired()
  {
    return guideRequired;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isHotelRequired()
  {
    return hotelRequired;
  }
  /* Code from template association_GetOne */
  public ClimbSafe getClimbSafe()
  {
    return climbSafe;
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
  /* Code from template association_GetMany */
  public BookedItem getBookedItem(int index)
  {
    BookedItem aBookedItem = bookedItems.get(index);
    return aBookedItem;
  }

  public List<BookedItem> getBookedItems()
  {
    List<BookedItem> newBookedItems = Collections.unmodifiableList(bookedItems);
    return newBookedItems;
  }

  public int numberOfBookedItems()
  {
    int number = bookedItems.size();
    return number;
  }

  public boolean hasBookedItems()
  {
    boolean has = bookedItems.size() > 0;
    return has;
  }

  public int indexOfBookedItem(BookedItem aBookedItem)
  {
    int index = bookedItems.indexOf(aBookedItem);
    return index;
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
      existingClimbSafe.removeMember(this);
    }
    climbSafe.addMember(this);
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBookedItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BookedItem addBookedItem(int aQuantity, ClimbSafe aClimbSafe, BookableItem aItem)
  {
    return new BookedItem(aQuantity, aClimbSafe, this, aItem);
  }

  public boolean addBookedItem(BookedItem aBookedItem)
  {
    boolean wasAdded = false;
    if (bookedItems.contains(aBookedItem)) { return false; }
    Member existingMember = aBookedItem.getMember();
    boolean isNewMember = existingMember != null && !this.equals(existingMember);
    if (isNewMember)
    {
      aBookedItem.setMember(this);
    }
    else
    {
      bookedItems.add(aBookedItem);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBookedItem(BookedItem aBookedItem)
  {
    boolean wasRemoved = false;
    //Unable to remove aBookedItem, as it must always have a member
    if (!this.equals(aBookedItem.getMember()))
    {
      bookedItems.remove(aBookedItem);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBookedItemAt(BookedItem aBookedItem, int index)
  {  
    boolean wasAdded = false;
    if(addBookedItem(aBookedItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBookedItems()) { index = numberOfBookedItems() - 1; }
      bookedItems.remove(aBookedItem);
      bookedItems.add(index, aBookedItem);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBookedItemAt(BookedItem aBookedItem, int index)
  {
    boolean wasAdded = false;
    if(bookedItems.contains(aBookedItem))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBookedItems()) { index = numberOfBookedItems() - 1; }
      bookedItems.remove(aBookedItem);
      bookedItems.add(index, aBookedItem);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBookedItemAt(aBookedItem, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    ClimbSafe placeholderClimbSafe = climbSafe;
    this.climbSafe = null;
    if(placeholderClimbSafe != null)
    {
      placeholderClimbSafe.removeMember(this);
    }
    Assignment existingAssignment = assignment;
    assignment = null;
    if (existingAssignment != null)
    {
      existingAssignment.delete();
    }
    for(int i=bookedItems.size(); i > 0; i--)
    {
      BookedItem aBookedItem = bookedItems.get(i - 1);
      aBookedItem.delete();
    }
    super.delete();
  }


  public String toString()
  {
    return super.toString() + "["+
            "nrWeeks" + ":" + getNrWeeks()+ "," +
            "guideRequired" + ":" + getGuideRequired()+ "," +
            "hotelRequired" + ":" + getHotelRequired()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "climbSafe = "+(getClimbSafe()!=null?Integer.toHexString(System.identityHashCode(getClimbSafe())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "assignment = "+(getAssignment()!=null?Integer.toHexString(System.identityHashCode(getAssignment())):"null");
  }
}