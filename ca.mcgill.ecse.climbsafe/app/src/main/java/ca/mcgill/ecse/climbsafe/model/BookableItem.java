/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse.climbsafe.model;
import java.io.Serializable;
import java.util.*;

/**
 * persistence for BookedItem needs to be specified in ClimbSafe.ump
 * due to a bug in Umple (association classes cannot be defined in two files)
 */
// line 61 "../../../../../ClimbSafePersistence.ump"
// line 61 "../../../../../ClimbSafe.ump"
public abstract class BookableItem implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  private static Map<String, BookableItem> bookableitemsByName = new HashMap<String, BookableItem>();

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BookableItem Attributes
  private String name;

  //BookableItem Associations
  private List<BookedItem> bookedItems;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BookableItem(String aName)
  {
    if (!setName(aName))
    {
      throw new RuntimeException("Cannot create due to duplicate name. See http://manual.umple.org?RE003ViolationofUniqueness.html");
    }
    bookedItems = new ArrayList<BookedItem>();
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setName(String aName)
  {
    boolean wasSet = false;
    String anOldName = getName();
    if (anOldName != null && anOldName.equals(aName)) {
      return true;
    }
    if (hasWithName(aName)) {
      return wasSet;
    }
    name = aName;
    wasSet = true;
    if (anOldName != null) {
      bookableitemsByName.remove(anOldName);
    }
    bookableitemsByName.put(aName, this);
    return wasSet;
  }

  public String getName()
  {
    return name;
  }
  /* Code from template attribute_GetUnique */
  public static BookableItem getWithName(String aName)
  {
    return bookableitemsByName.get(aName);
  }
  /* Code from template attribute_HasUnique */
  public static boolean hasWithName(String aName)
  {
    return getWithName(aName) != null;
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBookedItems()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BookedItem addBookedItem(int aQuantity, ClimbSafe aClimbSafe, Member aMember)
  {
    return new BookedItem(aQuantity, aClimbSafe, aMember, this);
  }

  public boolean addBookedItem(BookedItem aBookedItem)
  {
    boolean wasAdded = false;
    if (bookedItems.contains(aBookedItem)) { return false; }
    BookableItem existingItem = aBookedItem.getItem();
    boolean isNewItem = existingItem != null && !this.equals(existingItem);
    if (isNewItem)
    {
      aBookedItem.setItem(this);
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
    //Unable to remove aBookedItem, as it must always have a item
    if (!this.equals(aBookedItem.getItem()))
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
    bookableitemsByName.remove(getName());
    for(int i=bookedItems.size(); i > 0; i--)
    {
      BookedItem aBookedItem = bookedItems.get(i - 1);
      aBookedItem.delete();
    }
  }

  // line 67 "../../../../../ClimbSafePersistence.ump"
   public static  void reinitializeUniqueName(List<Equipment> equipment, List<EquipmentBundle> bundles){
    bookableitemsByName = new HashMap<String, BookableItem>();
    for (Equipment e : equipment) {
      bookableitemsByName.put(e.getName(), e);
    }
    for (EquipmentBundle bundle : bundles) {
      bookableitemsByName.put(bundle.getName(), bundle);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]";
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 64 "../../../../../ClimbSafePersistence.ump"
  private static final long serialVersionUID = 7L ;

  
}