/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 52 "../../../../../Block223_play.ump"
public class HallOfFame
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //HallOfFame Attributes
  private String gameName;

  //HallOfFame Associations
  private List<Score> scores;
  private Block223 block223;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public HallOfFame(String aGameName, Block223 aBlock223)
  {
    gameName = aGameName;
    scores = new ArrayList<Score>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create hallsoffame due to block223");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setGameName(String aGameName)
  {
    boolean wasSet = false;
    gameName = aGameName;
    wasSet = true;
    return wasSet;
  }

  public String getGameName()
  {
    return gameName;
  }
  /* Code from template association_GetMany */
  public Score getScore(int index)
  {
    Score aScore = scores.get(index);
    return aScore;
  }

  public List<Score> getScores()
  {
    List<Score> newScores = Collections.unmodifiableList(scores);
    return newScores;
  }

  public int numberOfScores()
  {
    int number = scores.size();
    return number;
  }

  public boolean hasScores()
  {
    boolean has = scores.size() > 0;
    return has;
  }

  public int indexOfScore(Score aScore)
  {
    int index = scores.indexOf(aScore);
    return index;
  }
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfScores()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Score addScore(String aUserName, int aScore)
  {
    return new Score(aUserName, aScore, this);
  }

  public boolean addScore(Score aScore)
  {
    boolean wasAdded = false;
    if (scores.contains(aScore)) { return false; }
    HallOfFame existingHallOfFame = aScore.getHallOfFame();
    boolean isNewHallOfFame = existingHallOfFame != null && !this.equals(existingHallOfFame);
    if (isNewHallOfFame)
    {
      aScore.setHallOfFame(this);
    }
    else
    {
      scores.add(aScore);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeScore(Score aScore)
  {
    boolean wasRemoved = false;
    //Unable to remove aScore, as it must always have a hallOfFame
    if (!this.equals(aScore.getHallOfFame()))
    {
      scores.remove(aScore);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addScoreAt(Score aScore, int index)
  {  
    boolean wasAdded = false;
    if(addScore(aScore))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfScores()) { index = numberOfScores() - 1; }
      scores.remove(aScore);
      scores.add(index, aScore);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveScoreAt(Score aScore, int index)
  {
    boolean wasAdded = false;
    if(scores.contains(aScore))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfScores()) { index = numberOfScores() - 1; }
      scores.remove(aScore);
      scores.add(index, aScore);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addScoreAt(aScore, index);
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
      existingBlock223.removeHallsoffame(this);
    }
    block223.addHallsoffame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    while (scores.size() > 0)
    {
      Score aScore = scores.get(scores.size() - 1);
      aScore.delete();
      scores.remove(aScore);
    }
    
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removeHallsoffame(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "gameName" + ":" + getGameName()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null");
  }
}