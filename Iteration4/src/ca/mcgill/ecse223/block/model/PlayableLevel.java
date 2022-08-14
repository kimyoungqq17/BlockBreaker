/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 30 "../../../../../Block223_play.ump"
public class PlayableLevel
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayableLevel Attributes
  private int initialBallSpeedX;
  private int initialBallSpeedY;
  private int paddleLength;

  //PlayableLevel Associations
  private List<PlayableBlock> playableblocks;
  private PlayableGame playableGame;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayableLevel(int aInitialBallSpeedX, int aInitialBallSpeedY, int aPaddleLength, PlayableGame aPlayableGame)
  {
    initialBallSpeedX = aInitialBallSpeedX;
    initialBallSpeedY = aInitialBallSpeedY;
    paddleLength = aPaddleLength;
    playableblocks = new ArrayList<PlayableBlock>();
    boolean didAddPlayableGame = setPlayableGame(aPlayableGame);
    if (!didAddPlayableGame)
    {
      throw new RuntimeException("Unable to create playablelevel due to playableGame");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public int getInitialBallSpeedX()
  {
    return initialBallSpeedX;
  }

  public int getInitialBallSpeedY()
  {
    return initialBallSpeedY;
  }

  public int getPaddleLength()
  {
    return paddleLength;
  }
  /* Code from template association_GetMany */
  public PlayableBlock getPlayableblock(int index)
  {
    PlayableBlock aPlayableblock = playableblocks.get(index);
    return aPlayableblock;
  }

  public List<PlayableBlock> getPlayableblocks()
  {
    List<PlayableBlock> newPlayableblocks = Collections.unmodifiableList(playableblocks);
    return newPlayableblocks;
  }

  public int numberOfPlayableblocks()
  {
    int number = playableblocks.size();
    return number;
  }

  public boolean hasPlayableblocks()
  {
    boolean has = playableblocks.size() > 0;
    return has;
  }

  public int indexOfPlayableblock(PlayableBlock aPlayableblock)
  {
    int index = playableblocks.indexOf(aPlayableblock);
    return index;
  }
  /* Code from template association_GetOne */
  public PlayableGame getPlayableGame()
  {
    return playableGame;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfPlayableblocks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PlayableBlock addPlayableblock(int aBlockX, int aBlockY, int aRed, int aGreen, int aBlue, int aPoints)
  {
    return new PlayableBlock(aBlockX, aBlockY, aRed, aGreen, aBlue, aPoints, this);
  }

  public boolean addPlayableblock(PlayableBlock aPlayableblock)
  {
    boolean wasAdded = false;
    if (playableblocks.contains(aPlayableblock)) { return false; }
    PlayableLevel existingPlayableLevel = aPlayableblock.getPlayableLevel();
    boolean isNewPlayableLevel = existingPlayableLevel != null && !this.equals(existingPlayableLevel);
    if (isNewPlayableLevel)
    {
      aPlayableblock.setPlayableLevel(this);
    }
    else
    {
      playableblocks.add(aPlayableblock);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removePlayableblock(PlayableBlock aPlayableblock)
  {
    boolean wasRemoved = false;
    //Unable to remove aPlayableblock, as it must always have a playableLevel
    if (!this.equals(aPlayableblock.getPlayableLevel()))
    {
      playableblocks.remove(aPlayableblock);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addPlayableblockAt(PlayableBlock aPlayableblock, int index)
  {  
    boolean wasAdded = false;
    if(addPlayableblock(aPlayableblock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayableblocks()) { index = numberOfPlayableblocks() - 1; }
      playableblocks.remove(aPlayableblock);
      playableblocks.add(index, aPlayableblock);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMovePlayableblockAt(PlayableBlock aPlayableblock, int index)
  {
    boolean wasAdded = false;
    if(playableblocks.contains(aPlayableblock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfPlayableblocks()) { index = numberOfPlayableblocks() - 1; }
      playableblocks.remove(aPlayableblock);
      playableblocks.add(index, aPlayableblock);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addPlayableblockAt(aPlayableblock, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPlayableGame(PlayableGame aPlayableGame)
  {
    boolean wasSet = false;
    if (aPlayableGame == null)
    {
      return wasSet;
    }

    PlayableGame existingPlayableGame = playableGame;
    playableGame = aPlayableGame;
    if (existingPlayableGame != null && !existingPlayableGame.equals(aPlayableGame))
    {
      existingPlayableGame.removePlayablelevel(this);
    }
    playableGame.addPlayablelevel(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (playableblocks.size() > 0)
    {
      PlayableBlock aPlayableblock = playableblocks.get(playableblocks.size() - 1);
      aPlayableblock.delete();
      playableblocks.remove(aPlayableblock);
    }
    
    PlayableGame placeholderPlayableGame = playableGame;
    this.playableGame = null;
    if(placeholderPlayableGame != null)
    {
      placeholderPlayableGame.removePlayablelevel(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "initialBallSpeedX" + ":" + getInitialBallSpeedX()+ "," +
            "initialBallSpeedY" + ":" + getInitialBallSpeedY()+ "," +
            "paddleLength" + ":" + getPaddleLength()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "playableGame = "+(getPlayableGame()!=null?Integer.toHexString(System.identityHashCode(getPlayableGame())):"null");
  }
}