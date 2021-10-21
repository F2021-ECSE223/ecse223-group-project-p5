/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.model;

// line 46 "../../../../../ClimbSafe.ump"
public class BookedItem
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BookedItem Attributes
  private int quantity;

  //BookedItem Associations
  private ClimbSafe climbSafe;
  private Member member;
  private BookableItem item;

  //Helper Variables
  private int cachedHashCode;
  private boolean canSetMember;
  private boolean canSetItem;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BookedItem(int aQuantity, ClimbSafe aClimbSafe, Member aMember, BookableItem aItem)
  {
    cachedHashCode = -1;
    canSetMember = true;
    canSetItem = true;
    quantity = aQuantity;
    boolean didAddClimbSafe = setClimbSafe(aClimbSafe);
    if (!didAddClimbSafe)
    {
      throw new RuntimeException("Unable to create bookedItem due to climbSafe. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddMember = setMember(aMember);
    if (!didAddMember)
    {
      throw new RuntimeException("Unable to create bookedItem due to member. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
    boolean didAddItem = setItem(aItem);
    if (!didAddItem)
    {
      throw new RuntimeException("Unable to create bookedItem due to item. See http://manual.umple.org?RE002ViolationofAssociationMultiplicity.html");
    }
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

  public int getQuantity()
  {
    return quantity;
  }
  /* Code from template association_GetOne */
  public ClimbSafe getClimbSafe()
  {
    return climbSafe;
  }
  /* Code from template association_GetOne */
  public Member getMember()
  {
    return member;
  }
  /* Code from template association_GetOne */
  public BookableItem getItem()
  {
    return item;
  }
  /* Code from template association_SetOneToManyAssociationClass */
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
      existingClimbSafe.removeBookedItem(this);
    }
    if (!climbSafe.addBookedItem(this))
    {
      climbSafe = existingClimbSafe;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setMember(Member aMember)
  {
    boolean wasSet = false;
    if (!canSetMember) { return false; }
    if (aMember == null)
    {
      return wasSet;
    }

    Member existingMember = member;
    member = aMember;
    if (existingMember != null && !existingMember.equals(aMember))
    {
      existingMember.removeBookedItem(this);
    }
    if (!member.addBookedItem(this))
    {
      member = existingMember;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }
  /* Code from template association_SetOneToManyAssociationClass */
  public boolean setItem(BookableItem aItem)
  {
    boolean wasSet = false;
    if (!canSetItem) { return false; }
    if (aItem == null)
    {
      return wasSet;
    }

    BookableItem existingItem = item;
    item = aItem;
    if (existingItem != null && !existingItem.equals(aItem))
    {
      existingItem.removeBookedItem(this);
    }
    if (!item.addBookedItem(this))
    {
      item = existingItem;
      wasSet = false;
    }
    else
    {
      wasSet = true;
    }
    return wasSet;
  }

  public boolean equals(Object obj)
  {
    if (obj == null) { return false; }
    if (!getClass().equals(obj.getClass())) { return false; }

    BookedItem compareTo = (BookedItem)obj;
  
    if (getMember() == null && compareTo.getMember() != null)
    {
      return false;
    }
    else if (getMember() != null && !getMember().equals(compareTo.getMember()))
    {
      return false;
    }

    if (getItem() == null && compareTo.getItem() != null)
    {
      return false;
    }
    else if (getItem() != null && !getItem().equals(compareTo.getItem()))
    {
      return false;
    }

    return true;
  }

  public int hashCode()
  {
    if (cachedHashCode != -1)
    {
      return cachedHashCode;
    }
    cachedHashCode = 17;
    if (getMember() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getMember().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }
    if (getItem() != null)
    {
      cachedHashCode = cachedHashCode * 23 + getItem().hashCode();
    }
    else
    {
      cachedHashCode = cachedHashCode * 23;
    }

    canSetMember = false;
    canSetItem = false;
    return cachedHashCode;
  }

  public void delete()
  {
    ClimbSafe placeholderClimbSafe = climbSafe;
    this.climbSafe = null;
    if(placeholderClimbSafe != null)
    {
      placeholderClimbSafe.removeBookedItem(this);
    }
    Member placeholderMember = member;
    this.member = null;
    if(placeholderMember != null)
    {
      placeholderMember.removeBookedItem(this);
    }
    BookableItem placeholderItem = item;
    this.item = null;
    if(placeholderItem != null)
    {
      placeholderItem.removeBookedItem(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "quantity" + ":" + getQuantity()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "climbSafe = "+(getClimbSafe()!=null?Integer.toHexString(System.identityHashCode(getClimbSafe())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "member = "+(getMember()!=null?Integer.toHexString(System.identityHashCode(getMember())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "item = "+(getItem()!=null?Integer.toHexString(System.identityHashCode(getItem())):"null");
  }
}