/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 53 "../../../../../DomainModel.ump"
// line 126 "../../../../../DomainModel.ump"
public class Level
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Level Attributes
  private int levelNumber;
  private boolean isRandomGrid;

  //Level Associations
  private Paddle paddle;
  private Game game;
  private Ball ball;
  private BlockGrid blockGrid;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Level(int aLevelNumber, boolean aIsRandomGrid, Paddle aPaddle, Game aGame, Ball aBall, BlockGrid aBlockGrid)
  {
    levelNumber = aLevelNumber;
    isRandomGrid = aIsRandomGrid;
    if (aPaddle == null || aPaddle.getLevel() != null)
    {
      throw new RuntimeException("Unable to create Level due to aPaddle");
    }
    paddle = aPaddle;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create level due to game");
    }
    if (aBall == null || aBall.getLevel() != null)
    {
      throw new RuntimeException("Unable to create Level due to aBall");
    }
    ball = aBall;
    if (aBlockGrid == null || aBlockGrid.getLevel() != null)
    {
      throw new RuntimeException("Unable to create Level due to aBlockGrid");
    }
    blockGrid = aBlockGrid;
  }

  public Level(int aLevelNumber, boolean aIsRandomGrid, int aLengthForPaddle, int aSpeedForPaddle, Game aGame, int aSpeedForBall, double aPositionXForBall, double aPositionYForBall)
  {
    levelNumber = aLevelNumber;
    isRandomGrid = aIsRandomGrid;
    paddle = new Paddle(aLengthForPaddle, aSpeedForPaddle, this);
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create level due to game");
    }
    ball = new Ball(aSpeedForBall, aPositionXForBall, aPositionYForBall, this);
    blockGrid = new BlockGrid(this);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLevelNumber(int aLevelNumber)
  {
    boolean wasSet = false;
    levelNumber = aLevelNumber;
    wasSet = true;
    return wasSet;
  }

  public boolean setIsRandomGrid(boolean aIsRandomGrid)
  {
    boolean wasSet = false;
    isRandomGrid = aIsRandomGrid;
    wasSet = true;
    return wasSet;
  }

  public int getLevelNumber()
  {
    return levelNumber;
  }

  public boolean getIsRandomGrid()
  {
    return isRandomGrid;
  }
  /* Code from template attribute_IsBoolean */
  public boolean isIsRandomGrid()
  {
    return isRandomGrid;
  }
  /* Code from template association_GetOne */
  public Paddle getPaddle()
  {
    return paddle;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public Ball getBall()
  {
    return ball;
  }
  /* Code from template association_GetOne */
  public BlockGrid getBlockGrid()
  {
    return blockGrid;
  }
  /* Code from template association_SetOneToMany */
  public boolean setGame(Game aGame)
  {
    boolean wasSet = false;
    if (aGame == null)
    {
      return wasSet;
    }

    Game existingGame = game;
    game = aGame;
    if (existingGame != null && !existingGame.equals(aGame))
    {
      existingGame.removeLevel(this);
    }
    game.addLevel(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Paddle existingPaddle = paddle;
    paddle = null;
    if (existingPaddle != null)
    {
      existingPaddle.delete();
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removeLevel(this);
    }
    Ball existingBall = ball;
    ball = null;
    if (existingBall != null)
    {
      existingBall.delete();
    }
    BlockGrid existingBlockGrid = blockGrid;
    blockGrid = null;
    if (existingBlockGrid != null)
    {
      existingBlockGrid.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "levelNumber" + ":" + getLevelNumber()+ "," +
            "isRandomGrid" + ":" + getIsRandomGrid()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "paddle = "+(getPaddle()!=null?Integer.toHexString(System.identityHashCode(getPaddle())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "ball = "+(getBall()!=null?Integer.toHexString(System.identityHashCode(getBall())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "blockGrid = "+(getBlockGrid()!=null?Integer.toHexString(System.identityHashCode(getBlockGrid())):"null");
  }
}