/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 9 "../../../../../DomainModel.ump"
// line 170 "../../../../../DomainModel.ump"
public class Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Player Attributes
  private String name;
  private String password;

  //Player Associations
  private List<Playthrough> playthroughs;
  private Block223 block223;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Player(String aName, String aPassword, Block223 aBlock223)
  {
    name = aName;
    password = aPassword;
    playthroughs = new ArrayList<Playthrough>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create player due to block223");
    }
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

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public String getName()
  {
    return name;
  }

  public String getPassword()
  {
    return password;
  }
  /* Code from template association_GetMany */
  public Playthrough getPlaythrough(int index)
  {
    Playthrough aPlaythrough = playthroughs.get(index);
    return aPlaythrough;
  }

  public List<Playthrough> getPlaythroughs()
  {
    List<Playthrough> newPlaythroughs = Collections.unmodifiableList(playthroughs);
    return newPlaythroughs;
  }

  public int numberOfPlaythroughs()
  {
    int number = playthroughs.size();
    return number;
  }

  public boolean hasPlaythroughs()
  {
    boolean has = playthroughs.size() > 0;
    return has;
  }

  public int indexOfPlaythrough(Playthrough aPlaythrough)
  {
    int index = playthroughs.indexOf(aPlaythrough);
    return index;
  }
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_GetOne_clear */
  protected void clear_block223()
  {
    block223 = null;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlaythroughs()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Playthrough addPlaythrough(int aScore, int aNumLivesLeft, Game aGame, HallOfFame aHallOfFame)
  {
    return new Playthrough(aScore, aNumLivesLeft, this, aGame, aHallOfFame);
  }

  public boolean addPlaythrough(Playthrough aPlaythrough)
  {
    boolean wasAdded = false;
    if (playthroughs.contains(aPlaythrough)) { return false; }
    Player existingPlayer = aPlaythrough.getPlayer();
    boolean isNewPlayer = existingPlayer != null && !this.equals(existingPlayer);
    if (isNewPlayer)
    {
      aPlaythrough.setPlayer(this);
    }
    else
    {
      playthroughs.add(aPlaythrough);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlaythrough(Playthrough aPlaythrough)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlaythrough, as it must always have a player
    if (!this.equals(aPlaythrough.getPlayer()))
    {
      playthroughs.remove(aPlaythrough);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlaythroughAt(Playthrough aPlaythrough, int index)
  {  
    boolean wasAdded = false;
    if(addPlaythrough(aPlaythrough))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlaythroughs()) { index = numberOfPlaythroughs() - 1; }
      playthroughs.remove(aPlaythrough);
      playthroughs.add(index, aPlaythrough);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlaythroughAt(Playthrough aPlaythrough, int index)
  {
    boolean wasAdded = false;
    if(playthroughs.contains(aPlaythrough))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlaythroughs()) { index = numberOfPlaythroughs() - 1; }
      playthroughs.remove(aPlaythrough);
      playthroughs.add(index, aPlaythrough);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlaythroughAt(aPlaythrough, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBlock223(Block223 aBlock223)
  {
    boolean wasSet = false;
    if (aBlock223 == null)
    {
      return wasSet;
    }

    Block223 existingBlock223 = block223;
    block223 = aBlock223;
    if (existingBlock223 != null && !existingBlock223.equals(aBlock223))
    {
      existingBlock223.removePlayer(this);
    }
    block223.addPlayer(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=playthroughs.size(); i > 0; i--)
    {
      Playthrough aPlaythrough = playthroughs.get(i - 1);
      aPlaythrough.delete();
    }
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removePlayer(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "," +
            "password" + ":" + getPassword()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null");
  }
}