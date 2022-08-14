/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 48 "../../../../../DomainModel.ump"
// line 120 "../../../../../DomainModel.ump"
public class HallOfFame
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //HallOfFame Associations
  private List<Playthrough> playthroughs;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public HallOfFame(Game aGame)
  {
    playthroughs = new ArrayList<Playthrough>();
    if (aGame == null || aGame.getHallOfFame() != null)
    {
      throw new RuntimeException("Unable to create HallOfFame due to aGame");
    }
    game = aGame;
  }

  public HallOfFame(String aNameForGame, GameSetting aGameSettingForGame, Block223 aBlock223ForGame, BlockSelection aBlockSelectionForGame)
  {
    playthroughs = new ArrayList<Playthrough>();
    game = new Game(aNameForGame, aGameSettingForGame, this, aBlock223ForGame, aBlockSelectionForGame);
  }

  //------------------------
  // INTERFACE
  //------------------------
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
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlaythroughs()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Playthrough addPlaythrough(int aScore, int aNumLivesLeft, Player aPlayer, Game aGame)
  {
    return new Playthrough(aScore, aNumLivesLeft, aPlayer, aGame, this);
  }

  public boolean addPlaythrough(Playthrough aPlaythrough)
  {
    boolean wasAdded = false;
    if (playthroughs.contains(aPlaythrough)) { return false; }
    HallOfFame existingHallOfFame = aPlaythrough.getHallOfFame();
    boolean isNewHallOfFame = existingHallOfFame != null && !this.equals(existingHallOfFame);
    if (isNewHallOfFame)
    {
      aPlaythrough.setHallOfFame(this);
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
    //Unable to remove aPlaythrough, as it must always have a hallOfFame
    if (!this.equals(aPlaythrough.getHallOfFame()))
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

  public void delete()
  {
    for(int i=playthroughs.size(); i > 0; i--)
    {
      Playthrough aPlaythrough = playthroughs.get(i - 1);
      aPlaythrough.delete();
    }
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }

}