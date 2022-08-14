/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 38 "../../../../../Block223_play.ump"
public class PlayableBlock
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final int SIZE = 20;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayableBlock Attributes
  private int blockX;
  private int blockY;
  private int red;
  private int green;
  private int blue;
  private int points;

  //PlayableBlock Associations
  private PlayableLevel playableLevel;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayableBlock(int aBlockX, int aBlockY, int aRed, int aGreen, int aBlue, int aPoints, PlayableLevel aPlayableLevel)
  {
    blockX = aBlockX;
    blockY = aBlockY;
    red = aRed;
    green = aGreen;
    blue = aBlue;
    points = aPoints;
    boolean didAddPlayableLevel = setPlayableLevel(aPlayableLevel);
    if (!didAddPlayableLevel)
    {
      throw new RuntimeException("Unable to create playableblock due to playableLevel");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public int getBlockX()
  {
    return blockX;
  }

  public int getBlockY()
  {
    return blockY;
  }

  public int getRed()
  {
    return red;
  }

  public int getGreen()
  {
    return green;
  }

  public int getBlue()
  {
    return blue;
  }

  public int getPoints()
  {
    return points;
  }
  /* Code from template association_GetOne */
  public PlayableLevel getPlayableLevel()
  {
    return playableLevel;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPlayableLevel(PlayableLevel aPlayableLevel)
  {
    boolean wasSet = false;
    if (aPlayableLevel == null)
    {
      return wasSet;
    }

    PlayableLevel existingPlayableLevel = playableLevel;
    playableLevel = aPlayableLevel;
    if (existingPlayableLevel != null && !existingPlayableLevel.equals(aPlayableLevel))
    {
      existingPlayableLevel.removePlayableblock(this);
    }
    playableLevel.addPlayableblock(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    PlayableLevel placeholderPlayableLevel = playableLevel;
    this.playableLevel = null;
    if(placeholderPlayableLevel != null)
    {
      placeholderPlayableLevel.removePlayableblock(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "blockX" + ":" + getBlockX()+ "," +
            "blockY" + ":" + getBlockY()+ "," +
            "red" + ":" + getRed()+ "," +
            "green" + ":" + getGreen()+ "," +
            "blue" + ":" + getBlue()+ "," +
            "points" + ":" + getPoints()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "playableLevel = "+(getPlayableLevel()!=null?Integer.toHexString(System.identityHashCode(getPlayableLevel())):"null");
  }
}