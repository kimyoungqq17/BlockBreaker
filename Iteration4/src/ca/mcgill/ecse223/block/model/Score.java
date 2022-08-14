/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 58 "../../../../../Block223_play.ump"
public class Score
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Score Attributes
  private String userName;
  private int score;

  //Score Associations
  private HallOfFame hallOfFame;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Score(String aUserName, int aScore, HallOfFame aHallOfFame)
  {
    userName = aUserName;
    score = aScore;
    boolean didAddHallOfFame = setHallOfFame(aHallOfFame);
    if (!didAddHallOfFame)
    {
      throw new RuntimeException("Unable to create score due to hallOfFame");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setUserName(String aUserName)
  {
    boolean wasSet = false;
    userName = aUserName;
    wasSet = true;
    return wasSet;
  }

  public boolean setScore(int aScore)
  {
    boolean wasSet = false;
    score = aScore;
    wasSet = true;
    return wasSet;
  }

  public String getUserName()
  {
    return userName;
  }

  public int getScore()
  {
    return score;
  }
  /* Code from template association_GetOne */
  public HallOfFame getHallOfFame()
  {
    return hallOfFame;
  }
  /* Code from template association_SetOneToMany */
  public boolean setHallOfFame(HallOfFame aHallOfFame)
  {
    boolean wasSet = false;
    if (aHallOfFame == null)
    {
      return wasSet;
    }

    HallOfFame existingHallOfFame = hallOfFame;
    hallOfFame = aHallOfFame;
    if (existingHallOfFame != null && !existingHallOfFame.equals(aHallOfFame))
    {
      existingHallOfFame.removeScore(this);
    }
    hallOfFame.addScore(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    HallOfFame placeholderHallOfFame = hallOfFame;
    this.hallOfFame = null;
    if(placeholderHallOfFame != null)
    {
      placeholderHallOfFame.removeScore(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "userName" + ":" + getUserName()+ "," +
            "score" + ":" + getScore()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "hallOfFame = "+(getHallOfFame()!=null?Integer.toHexString(System.identityHashCode(getHallOfFame())):"null");
  }
}