/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 40 "../../../../../DomainModel.ump"
// line 111 "../../../../../DomainModel.ump"
public class Game
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Game Attributes
  private String name;

  //Game Associations
  private GameSetting gameSetting;
  private HallOfFame hallOfFame;
  private List<Playthrough> playthroughs;
  private List<Level> levels;
  private Block223 block223;
  private BlockSelection blockSelection;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Game(String aName, GameSetting aGameSetting, HallOfFame aHallOfFame, Block223 aBlock223, BlockSelection aBlockSelection)
  {
    name = aName;
    if (aGameSetting == null || aGameSetting.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aGameSetting");
    }
    gameSetting = aGameSetting;
    if (aHallOfFame == null || aHallOfFame.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aHallOfFame");
    }
    hallOfFame = aHallOfFame;
    playthroughs = new ArrayList<Playthrough>();
    levels = new ArrayList<Level>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create game due to block223");
    }
    if (aBlockSelection == null || aBlockSelection.getGame() != null)
    {
      throw new RuntimeException("Unable to create Game due to aBlockSelection");
    }
    blockSelection = aBlockSelection;
  }

  public Game(String aName, int aMinBallSpeedForGameSetting, int aBallSpeedIncreaseFactorForGameSetting, int aMinPaddleLengthForGameSetting, int aMaxPaddleLengthForGameSetting, int aGameFrameSizeXForGameSetting, int aGameFrameSizeYForGameSetting, int aNumLevelsForGameSetting, int aNumBlocksForGameSetting, Admin aAdminForGameSetting, Block223 aBlock223)
  {
    name = aName;
    gameSetting = new GameSetting(aMinBallSpeedForGameSetting, aBallSpeedIncreaseFactorForGameSetting, aMinPaddleLengthForGameSetting, aMaxPaddleLengthForGameSetting, aGameFrameSizeXForGameSetting, aGameFrameSizeYForGameSetting, aNumLevelsForGameSetting, aNumBlocksForGameSetting, aAdminForGameSetting, this);
    hallOfFame = new HallOfFame(this);
    playthroughs = new ArrayList<Playthrough>();
    levels = new ArrayList<Level>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create game due to block223");
    }
    blockSelection = new BlockSelection(this);
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

  public String getName()
  {
    return name;
  }
  /* Code from template association_GetOne */
  public GameSetting getGameSetting()
  {
    return gameSetting;
  }
  /* Code from template association_GetOne */
  public HallOfFame getHallOfFame()
  {
    return hallOfFame;
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
  /* Code from template association_GetMany */
  public Level getLevel(int index)
  {
    Level aLevel = levels.get(index);
    return aLevel;
  }

  public List<Level> getLevels()
  {
    List<Level> newLevels = Collections.unmodifiableList(levels);
    return newLevels;
  }

  public int numberOfLevels()
  {
    int number = levels.size();
    return number;
  }

  public boolean hasLevels()
  {
    boolean has = levels.size() > 0;
    return has;
  }

  public int indexOfLevel(Level aLevel)
  {
    int index = levels.indexOf(aLevel);
    return index;
  }
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_GetOne */
  public BlockSelection getBlockSelection()
  {
    return blockSelection;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlaythroughs()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Playthrough addPlaythrough(int aScore, int aNumLivesLeft, Player aPlayer, HallOfFame aHallOfFame)
  {
    return new Playthrough(aScore, aNumLivesLeft, aPlayer, this, aHallOfFame);
  }

  public boolean addPlaythrough(Playthrough aPlaythrough)
  {
    boolean wasAdded = false;
    if (playthroughs.contains(aPlaythrough)) { return false; }
    Game existingGame = aPlaythrough.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aPlaythrough.setGame(this);
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
    //Unable to remove aPlaythrough, as it must always have a game
    if (!this.equals(aPlaythrough.getGame()))
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfLevels()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Level addLevel(int aLevelNumber, boolean aIsRandomGrid, Paddle aPaddle, Ball aBall, BlockGrid aBlockGrid)
  {
    return new Level(aLevelNumber, aIsRandomGrid, aPaddle, this, aBall, aBlockGrid);
  }

  public boolean addLevel(Level aLevel)
  {
    boolean wasAdded = false;
    if (levels.contains(aLevel)) { return false; }
    Game existingGame = aLevel.getGame();
    boolean isNewGame = existingGame != null && !this.equals(existingGame);
    if (isNewGame)
    {
      aLevel.setGame(this);
    }
    else
    {
      levels.add(aLevel);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeLevel(Level aLevel)
  {
    boolean wasRemoved = false;
    //Unable to remove aLevel, as it must always have a game
    if (!this.equals(aLevel.getGame()))
    {
      levels.remove(aLevel);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addLevelAt(Level aLevel, int index)
  {  
    boolean wasAdded = false;
    if(addLevel(aLevel))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLevels()) { index = numberOfLevels() - 1; }
      levels.remove(aLevel);
      levels.add(index, aLevel);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveLevelAt(Level aLevel, int index)
  {
    boolean wasAdded = false;
    if(levels.contains(aLevel))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfLevels()) { index = numberOfLevels() - 1; }
      levels.remove(aLevel);
      levels.add(index, aLevel);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addLevelAt(aLevel, index);
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
      existingBlock223.removeGame(this);
    }
    block223.addGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    GameSetting existingGameSetting = gameSetting;
    gameSetting = null;
    if (existingGameSetting != null)
    {
      existingGameSetting.delete();
    }
    HallOfFame existingHallOfFame = hallOfFame;
    hallOfFame = null;
    if (existingHallOfFame != null)
    {
      existingHallOfFame.delete();
    }
    while (playthroughs.size() > 0)
    {
      Playthrough aPlaythrough = playthroughs.get(playthroughs.size() - 1);
      aPlaythrough.delete();
      playthroughs.remove(aPlaythrough);
    }
    
    while (levels.size() > 0)
    {
      Level aLevel = levels.get(levels.size() - 1);
      aLevel.delete();
      levels.remove(aLevel);
    }
    
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removeGame(this);
    }
    BlockSelection existingBlockSelection = blockSelection;
    blockSelection = null;
    if (existingBlockSelection != null)
    {
      existingBlockSelection.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "name" + ":" + getName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "gameSetting = "+(getGameSetting()!=null?Integer.toHexString(System.identityHashCode(getGameSetting())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "hallOfFame = "+(getHallOfFame()!=null?Integer.toHexString(System.identityHashCode(getHallOfFame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "blockSelection = "+(getBlockSelection()!=null?Integer.toHexString(System.identityHashCode(getBlockSelection())):"null");
  }
}