/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.31.1.5860.78bb27cc6 modeling language!*/

package ca.mcgill.ecse223.climbsafe.model;
import java.util.*;
import java.sql.Date;

// line 8 "../../../../../domain_model.ump"
public class ClimbSafe
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //ClimbSafe Associations
  private List<User> users;
  private List<ClimbingSeason> seasons;
  private List<Hotel> hotels;
  private List<Equipment> equipment;
  private List<Bundle> bundles;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public ClimbSafe()
  {
    users = new ArrayList<User>();
    seasons = new ArrayList<ClimbingSeason>();
    hotels = new ArrayList<Hotel>();
    equipment = new ArrayList<Equipment>();
    bundles = new ArrayList<Bundle>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public User getUser(int index)
  {
    User aUser = users.get(index);
    return aUser;
  }

  /**
   * User class associations
   * all users
   */
  public List<User> getUsers()
  {
    List<User> newUsers = Collections.unmodifiableList(users);
    return newUsers;
  }

  public int numberOfUsers()
  {
    int number = users.size();
    return number;
  }

  public boolean hasUsers()
  {
    boolean has = users.size() > 0;
    return has;
  }

  public int indexOfUser(User aUser)
  {
    int index = users.indexOf(aUser);
    return index;
  }
  /* Code from template association_GetMany */
  public ClimbingSeason getSeason(int index)
  {
    ClimbingSeason aSeason = seasons.get(index);
    return aSeason;
  }

  public List<ClimbingSeason> getSeasons()
  {
    List<ClimbingSeason> newSeasons = Collections.unmodifiableList(seasons);
    return newSeasons;
  }

  public int numberOfSeasons()
  {
    int number = seasons.size();
    return number;
  }

  public boolean hasSeasons()
  {
    boolean has = seasons.size() > 0;
    return has;
  }

  public int indexOfSeason(ClimbingSeason aSeason)
  {
    int index = seasons.indexOf(aSeason);
    return index;
  }
  /* Code from template association_GetMany */
  public Hotel getHotel(int index)
  {
    Hotel aHotel = hotels.get(index);
    return aHotel;
  }

  public List<Hotel> getHotels()
  {
    List<Hotel> newHotels = Collections.unmodifiableList(hotels);
    return newHotels;
  }

  public int numberOfHotels()
  {
    int number = hotels.size();
    return number;
  }

  public boolean hasHotels()
  {
    boolean has = hotels.size() > 0;
    return has;
  }

  public int indexOfHotel(Hotel aHotel)
  {
    int index = hotels.indexOf(aHotel);
    return index;
  }
  /* Code from template association_GetMany */
  public Equipment getEquipment(int index)
  {
    Equipment aEquipment = equipment.get(index);
    return aEquipment;
  }

  public List<Equipment> getEquipment()
  {
    List<Equipment> newEquipment = Collections.unmodifiableList(equipment);
    return newEquipment;
  }

  public int numberOfEquipment()
  {
    int number = equipment.size();
    return number;
  }

  public boolean hasEquipment()
  {
    boolean has = equipment.size() > 0;
    return has;
  }

  public int indexOfEquipment(Equipment aEquipment)
  {
    int index = equipment.indexOf(aEquipment);
    return index;
  }
  /* Code from template association_GetMany */
  public Bundle getBundle(int index)
  {
    Bundle aBundle = bundles.get(index);
    return aBundle;
  }

  public List<Bundle> getBundles()
  {
    List<Bundle> newBundles = Collections.unmodifiableList(bundles);
    return newBundles;
  }

  public int numberOfBundles()
  {
    int number = bundles.size();
    return number;
  }

  public boolean hasBundles()
  {
    boolean has = bundles.size() > 0;
    return has;
  }

  public int indexOfBundle(Bundle aBundle)
  {
    int index = bundles.indexOf(aBundle);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfUsers()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */


  public boolean addUser(User aUser)
  {
    boolean wasAdded = false;
    if (users.contains(aUser)) { return false; }
    ClimbSafe existingClimbSafe = aUser.getClimbSafe();
    boolean isNewClimbSafe = existingClimbSafe != null && !this.equals(existingClimbSafe);
    if (isNewClimbSafe)
    {
      aUser.setClimbSafe(this);
    }
    else
    {
      users.add(aUser);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeUser(User aUser)
  {
    boolean wasRemoved = false;
    //Unable to remove aUser, as it must always have a climbSafe
    if (!this.equals(aUser.getClimbSafe()))
    {
      users.remove(aUser);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addUserAt(User aUser, int index)
  {  
    boolean wasAdded = false;
    if(addUser(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveUserAt(User aUser, int index)
  {
    boolean wasAdded = false;
    if(users.contains(aUser))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfUsers()) { index = numberOfUsers() - 1; }
      users.remove(aUser);
      users.add(index, aUser);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addUserAt(aUser, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfSeasons()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public ClimbingSeason addSeason(Date aStart, Date aEnd, int aGuideCost)
  {
    return new ClimbingSeason(aStart, aEnd, aGuideCost, this);
  }

  public boolean addSeason(ClimbingSeason aSeason)
  {
    boolean wasAdded = false;
    if (seasons.contains(aSeason)) { return false; }
    ClimbSafe existingClimbSafe = aSeason.getClimbSafe();
    boolean isNewClimbSafe = existingClimbSafe != null && !this.equals(existingClimbSafe);
    if (isNewClimbSafe)
    {
      aSeason.setClimbSafe(this);
    }
    else
    {
      seasons.add(aSeason);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeSeason(ClimbingSeason aSeason)
  {
    boolean wasRemoved = false;
    //Unable to remove aSeason, as it must always have a climbSafe
    if (!this.equals(aSeason.getClimbSafe()))
    {
      seasons.remove(aSeason);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addSeasonAt(ClimbingSeason aSeason, int index)
  {  
    boolean wasAdded = false;
    if(addSeason(aSeason))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSeasons()) { index = numberOfSeasons() - 1; }
      seasons.remove(aSeason);
      seasons.add(index, aSeason);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveSeasonAt(ClimbingSeason aSeason, int index)
  {
    boolean wasAdded = false;
    if(seasons.contains(aSeason))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfSeasons()) { index = numberOfSeasons() - 1; }
      seasons.remove(aSeason);
      seasons.add(index, aSeason);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addSeasonAt(aSeason, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfHotels()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Hotel addHotel(String aName, String aAddress, int aRating)
  {
    return new Hotel(aName, aAddress, aRating, this);
  }

  public boolean addHotel(Hotel aHotel)
  {
    boolean wasAdded = false;
    if (hotels.contains(aHotel)) { return false; }
    ClimbSafe existingClimbSafe = aHotel.getClimbSafe();
    boolean isNewClimbSafe = existingClimbSafe != null && !this.equals(existingClimbSafe);
    if (isNewClimbSafe)
    {
      aHotel.setClimbSafe(this);
    }
    else
    {
      hotels.add(aHotel);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeHotel(Hotel aHotel)
  {
    boolean wasRemoved = false;
    //Unable to remove aHotel, as it must always have a climbSafe
    if (!this.equals(aHotel.getClimbSafe()))
    {
      hotels.remove(aHotel);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addHotelAt(Hotel aHotel, int index)
  {  
    boolean wasAdded = false;
    if(addHotel(aHotel))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHotels()) { index = numberOfHotels() - 1; }
      hotels.remove(aHotel);
      hotels.add(index, aHotel);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveHotelAt(Hotel aHotel, int index)
  {
    boolean wasAdded = false;
    if(hotels.contains(aHotel))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfHotels()) { index = numberOfHotels() - 1; }
      hotels.remove(aHotel);
      hotels.add(index, aHotel);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addHotelAt(aHotel, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfEquipment()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Equipment addEquipment(String aName, int aWeight, int aPrice)
  {
    return new Equipment(aName, aWeight, aPrice, this);
  }

  public boolean addEquipment(Equipment aEquipment)
  {
    boolean wasAdded = false;
    if (equipment.contains(aEquipment)) { return false; }
    ClimbSafe existingClimbSafe = aEquipment.getClimbSafe();
    boolean isNewClimbSafe = existingClimbSafe != null && !this.equals(existingClimbSafe);
    if (isNewClimbSafe)
    {
      aEquipment.setClimbSafe(this);
    }
    else
    {
      equipment.add(aEquipment);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeEquipment(Equipment aEquipment)
  {
    boolean wasRemoved = false;
    //Unable to remove aEquipment, as it must always have a climbSafe
    if (!this.equals(aEquipment.getClimbSafe()))
    {
      equipment.remove(aEquipment);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addEquipmentAt(Equipment aEquipment, int index)
  {  
    boolean wasAdded = false;
    if(addEquipment(aEquipment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEquipment()) { index = numberOfEquipment() - 1; }
      equipment.remove(aEquipment);
      equipment.add(index, aEquipment);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveEquipmentAt(Equipment aEquipment, int index)
  {
    boolean wasAdded = false;
    if(equipment.contains(aEquipment))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfEquipment()) { index = numberOfEquipment() - 1; }
      equipment.remove(aEquipment);
      equipment.add(index, aEquipment);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addEquipmentAt(aEquipment, index);
    }
    return wasAdded;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBundles()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Bundle addBundle(String aName, double aDiscountPercentage, Equipment... allInclusions)
  {
    return new Bundle(aName, aDiscountPercentage, this, allInclusions);
  }

  public boolean addBundle(Bundle aBundle)
  {
    boolean wasAdded = false;
    if (bundles.contains(aBundle)) { return false; }
    ClimbSafe existingClimbSafe = aBundle.getClimbSafe();
    boolean isNewClimbSafe = existingClimbSafe != null && !this.equals(existingClimbSafe);
    if (isNewClimbSafe)
    {
      aBundle.setClimbSafe(this);
    }
    else
    {
      bundles.add(aBundle);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBundle(Bundle aBundle)
  {
    boolean wasRemoved = false;
    //Unable to remove aBundle, as it must always have a climbSafe
    if (!this.equals(aBundle.getClimbSafe()))
    {
      bundles.remove(aBundle);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBundleAt(Bundle aBundle, int index)
  {  
    boolean wasAdded = false;
    if(addBundle(aBundle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundles()) { index = numberOfBundles() - 1; }
      bundles.remove(aBundle);
      bundles.add(index, aBundle);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBundleAt(Bundle aBundle, int index)
  {
    boolean wasAdded = false;
    if(bundles.contains(aBundle))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBundles()) { index = numberOfBundles() - 1; }
      bundles.remove(aBundle);
      bundles.add(index, aBundle);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBundleAt(aBundle, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (users.size() > 0)
    {
      User aUser = users.get(users.size() - 1);
      aUser.delete();
      users.remove(aUser);
    }
    
    while (seasons.size() > 0)
    {
      ClimbingSeason aSeason = seasons.get(seasons.size() - 1);
      aSeason.delete();
      seasons.remove(aSeason);
    }
    
    while (hotels.size() > 0)
    {
      Hotel aHotel = hotels.get(hotels.size() - 1);
      aHotel.delete();
      hotels.remove(aHotel);
    }
    
    while (equipment.size() > 0)
    {
      Equipment aEquipment = equipment.get(equipment.size() - 1);
      aEquipment.delete();
      equipment.remove(aEquipment);
    }
    
    while (bundles.size() > 0)
    {
      Bundle aBundle = bundles.get(bundles.size() - 1);
      aBundle.delete();
      bundles.remove(aBundle);
    }
    
  }

}