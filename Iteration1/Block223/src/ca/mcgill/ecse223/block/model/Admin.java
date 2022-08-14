/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 15 "../../../../../DomainModel.ump"
// line 95 "../../../../../DomainModel.ump"
public class Admin extends Player
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Admin Associations
  private List<GameSetting> gameSettings;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Admin(String aName, String aPassword, Block223 aBlock223)
  {
    super(aName, aPassword, aBlock223);
    gameSettings = new ArrayList<GameSetting>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public GameSetting getGameSetting(int index)
  {
    GameSetting aGameSetting = gameSettings.get(index);
    return aGameSetting;
  }

  public List<GameSetting> getGameSettings()
  {
    List<GameSetting> newGameSettings = Collections.unmodifiableList(gameSettings);
    return newGameSettings;
  }

  public int numberOfGameSettings()
  {
    int number = gameSettings.size();
    return number;
  }

  public boolean hasGameSettings()
  {
    boolean has = gameSettings.size() > 0;
    return has;
  }

  public int indexOfGameSetting(GameSetting aGameSetting)
  {
    int index = gameSettings.indexOf(aGameSetting);
    return index;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfGameSettings()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public GameSetting addGameSetting(int aMinBallSpeed, int aBallSpeedIncreaseFactor, int aMinPaddleLength, int aMaxPaddleLength, int aGameFrameSizeX, int aGameFrameSizeY, int aNumLevels, int aNumBlocks, Game aGame)
  {
    return new GameSetting(aMinBallSpeed, aBallSpeedIncreaseFactor, aMinPaddleLength, aMaxPaddleLength, aGameFrameSizeX, aGameFrameSizeY, aNumLevels, aNumBlocks, this, aGame);
  }

  public boolean addGameSetting(GameSetting aGameSetting)
  {
    boolean wasAdded = false;
    if (gameSettings.contains(aGameSetting)) { return false; }
    Admin existingAdmin = aGameSetting.getAdmin();
    boolean isNewAdmin = existingAdmin != null && !this.equals(existingAdmin);
    if (isNewAdmin)
    {
      aGameSetting.setAdmin(this);
    }
    else
    {
      gameSettings.add(aGameSetting);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeGameSetting(GameSetting aGameSetting)
  {
    boolean wasRemoved = false;
    //Unable to remove aGameSetting, as it must always have a admin
    if (!this.equals(aGameSetting.getAdmin()))
    {
      gameSettings.remove(aGameSetting);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addGameSettingAt(GameSetting aGameSetting, int index)
  {  
    boolean wasAdded = false;
    if(addGameSetting(aGameSetting))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameSettings()) { index = numberOfGameSettings() - 1; }
      gameSettings.remove(aGameSetting);
      gameSettings.add(index, aGameSetting);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveGameSettingAt(GameSetting aGameSetting, int index)
  {
    boolean wasAdded = false;
    if(gameSettings.contains(aGameSetting))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfGameSettings()) { index = numberOfGameSettings() - 1; }
      gameSettings.remove(aGameSetting);
      gameSettings.add(index, aGameSetting);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addGameSettingAt(aGameSetting, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    for(int i=gameSettings.size(); i > 0; i--)
    {
      GameSetting aGameSetting = gameSettings.get(i - 1);
      aGameSetting.delete();
    }
    super.delete();
  }

}