/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 21 "../../../../../DomainModel.ump"
// line 101 "../../../../../DomainModel.ump"
public class GameSetting
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final int MAXLEVELS = 99;
  public static final int MINFRAMESIZE = 200;
  public static final int MAXFRAMESIZE = 500;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //GameSetting Attributes
  private int minBallSpeed;
  private int ballSpeedIncreaseFactor;
  private int minPaddleLength;
  private int maxPaddleLength;
  private int gameFrameSizeX;
  private int gameFrameSizeY;
  private int numLevels;
  private int numBlocks;

  //GameSetting Associations
  private Admin admin;
  private Game game;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public GameSetting(int aMinBallSpeed, int aBallSpeedIncreaseFactor, int aMinPaddleLength, int aMaxPaddleLength, int aGameFrameSizeX, int aGameFrameSizeY, int aNumLevels, int aNumBlocks, Admin aAdmin, Game aGame)
  {
    minBallSpeed = aMinBallSpeed;
    ballSpeedIncreaseFactor = aBallSpeedIncreaseFactor;
    minPaddleLength = aMinPaddleLength;
    maxPaddleLength = aMaxPaddleLength;
    gameFrameSizeX = aGameFrameSizeX;
    gameFrameSizeY = aGameFrameSizeY;
    numLevels = aNumLevels;
    numBlocks = aNumBlocks;
    boolean didAddAdmin = setAdmin(aAdmin);
    if (!didAddAdmin)
    {
      throw new RuntimeException("Unable to create gameSetting due to admin");
    }
    if (aGame == null || aGame.getGameSetting() != null)
    {
      throw new RuntimeException("Unable to create GameSetting due to aGame");
    }
    game = aGame;
  }

  public GameSetting(int aMinBallSpeed, int aBallSpeedIncreaseFactor, int aMinPaddleLength, int aMaxPaddleLength, int aGameFrameSizeX, int aGameFrameSizeY, int aNumLevels, int aNumBlocks, Admin aAdmin, String aNameForGame, HallOfFame aHallOfFameForGame, Block223 aBlock223ForGame, BlockSelection aBlockSelectionForGame)
  {
    minBallSpeed = aMinBallSpeed;
    ballSpeedIncreaseFactor = aBallSpeedIncreaseFactor;
    minPaddleLength = aMinPaddleLength;
    maxPaddleLength = aMaxPaddleLength;
    gameFrameSizeX = aGameFrameSizeX;
    gameFrameSizeY = aGameFrameSizeY;
    numLevels = aNumLevels;
    numBlocks = aNumBlocks;
    boolean didAddAdmin = setAdmin(aAdmin);
    if (!didAddAdmin)
    {
      throw new RuntimeException("Unable to create gameSetting due to admin");
    }
    game = new Game(aNameForGame, this, aHallOfFameForGame, aBlock223ForGame, aBlockSelectionForGame);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setMinBallSpeed(int aMinBallSpeed)
  {
    boolean wasSet = false;
    minBallSpeed = aMinBallSpeed;
    wasSet = true;
    return wasSet;
  }

  public boolean setBallSpeedIncreaseFactor(int aBallSpeedIncreaseFactor)
  {
    boolean wasSet = false;
    ballSpeedIncreaseFactor = aBallSpeedIncreaseFactor;
    wasSet = true;
    return wasSet;
  }

  public boolean setMinPaddleLength(int aMinPaddleLength)
  {
    boolean wasSet = false;
    minPaddleLength = aMinPaddleLength;
    wasSet = true;
    return wasSet;
  }

  public boolean setMaxPaddleLength(int aMaxPaddleLength)
  {
    boolean wasSet = false;
    maxPaddleLength = aMaxPaddleLength;
    wasSet = true;
    return wasSet;
  }

  public boolean setGameFrameSizeX(int aGameFrameSizeX)
  {
    boolean wasSet = false;
    gameFrameSizeX = aGameFrameSizeX;
    wasSet = true;
    return wasSet;
  }

  public boolean setGameFrameSizeY(int aGameFrameSizeY)
  {
    boolean wasSet = false;
    gameFrameSizeY = aGameFrameSizeY;
    wasSet = true;
    return wasSet;
  }

  public int getMinBallSpeed()
  {
    return minBallSpeed;
  }

  public int getBallSpeedIncreaseFactor()
  {
    return ballSpeedIncreaseFactor;
  }

  public int getMinPaddleLength()
  {
    return minPaddleLength;
  }

  public int getMaxPaddleLength()
  {
    return maxPaddleLength;
  }

  public int getGameFrameSizeX()
  {
    return gameFrameSizeX;
  }

  public int getGameFrameSizeY()
  {
    return gameFrameSizeY;
  }

  public int getNumLevels()
  {
    return numLevels;
  }

  public int getNumBlocks()
  {
    return numBlocks;
  }
  /* Code from template association_GetOne */
  public Admin getAdmin()
  {
    return admin;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_SetOneToMany */
  public boolean setAdmin(Admin aAdmin)
  {
    boolean wasSet = false;
    if (aAdmin == null)
    {
      return wasSet;
    }

    Admin existingAdmin = admin;
    admin = aAdmin;
    if (existingAdmin != null && !existingAdmin.equals(aAdmin))
    {
      existingAdmin.removeGameSetting(this);
    }
    admin.addGameSetting(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Admin placeholderAdmin = admin;
    this.admin = null;
    if(placeholderAdmin != null)
    {
      placeholderAdmin.removeGameSetting(this);
    }
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "minBallSpeed" + ":" + getMinBallSpeed()+ "," +
            "ballSpeedIncreaseFactor" + ":" + getBallSpeedIncreaseFactor()+ "," +
            "minPaddleLength" + ":" + getMinPaddleLength()+ "," +
            "maxPaddleLength" + ":" + getMaxPaddleLength()+ "," +
            "gameFrameSizeX" + ":" + getGameFrameSizeX()+ "," +
            "gameFrameSizeY" + ":" + getGameFrameSizeY()+ "," +
            "numLevels" + ":" + getNumLevels()+ "," +
            "numBlocks" + ":" + getNumBlocks()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "admin = "+(getAdmin()!=null?Integer.toHexString(System.identityHashCode(getAdmin())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null");
  }
}