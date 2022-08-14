/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 35 "../../../../../DomainModel.ump"
// line 106 "../../../../../DomainModel.ump"
public class Playthrough
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Playthrough Attributes
  private int score;
  private int numLivesLeft;

  //Playthrough Associations
  private Player player;
  private Game game;
  private HallOfFame hallOfFame;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Playthrough(int aScore, int aNumLivesLeft, Player aPlayer, Game aGame, HallOfFame aHallOfFame)
  {
    score = aScore;
    numLivesLeft = aNumLivesLeft;
    boolean didAddPlayer = setPlayer(aPlayer);
    if (!didAddPlayer)
    {
      throw new RuntimeException("Unable to create playthrough due to player");
    }
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create playthrough due to game");
    }
    boolean didAddHallOfFame = setHallOfFame(aHallOfFame);
    if (!didAddHallOfFame)
    {
      throw new RuntimeException("Unable to create playthrough due to hallOfFame");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setScore(int aScore)
  {
    boolean wasSet = false;
    score = aScore;
    wasSet = true;
    return wasSet;
  }

  public boolean setNumLivesLeft(int aNumLivesLeft)
  {
    boolean wasSet = false;
    numLivesLeft = aNumLivesLeft;
    wasSet = true;
    return wasSet;
  }

  public int getScore()
  {
    return score;
  }

  public int getNumLivesLeft()
  {
    return numLivesLeft;
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetOne */
  public HallOfFame getHallOfFame()
  {
    return hallOfFame;
  }
  /* Code from template association_SetOneToMany */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    if (aPlayer == null)
    {
      return wasSet;
    }

    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      existingPlayer.removePlaythrough(this);
    }
    player.addPlaythrough(this);
    wasSet = true;
    return wasSet;
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
      existingGame.removePlaythrough(this);
    }
    game.addPlaythrough(this);
    wasSet = true;
    return wasSet;
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
      existingHallOfFame.removePlaythrough(this);
    }
    hallOfFame.addPlaythrough(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    Player placeholderPlayer = player;
    this.player = null;
    if(placeholderPlayer != null)
    {
      placeholderPlayer.removePlaythrough(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlaythrough(this);
    }
    HallOfFame placeholderHallOfFame = hallOfFame;
    this.hallOfFame = null;
    if(placeholderHallOfFame != null)
    {
      placeholderHallOfFame.removePlaythrough(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "score" + ":" + getScore()+ "," +
            "numLivesLeft" + ":" + getNumLivesLeft()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "hallOfFame = "+(getHallOfFame()!=null?Integer.toHexString(System.identityHashCode(getHallOfFame())):"null");
  }
}