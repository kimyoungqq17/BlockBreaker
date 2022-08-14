/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 3 "../../../../../Block223_statemachine.ump"
public class PlayableGame
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayableGame State Machines
  public enum GameStatus { notPublished, onStandBy, inPlay }
  public enum GameStatusOnStandBy { Null, gameOver, viewingHallOfFame }
  private GameStatus gameStatus;
  private GameStatusOnStandBy gameStatusOnStandBy;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayableGame()
  {
    setGameStatusOnStandBy(GameStatusOnStandBy.Null);
    setGameStatus(GameStatus.notPublished);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public String getGameStatusFullName()
  {
    String answer = gameStatus.toString();
    if (gameStatusOnStandBy != GameStatusOnStandBy.Null) { answer += "." + gameStatusOnStandBy.toString(); }
    return answer;
  }

  public GameStatus getGameStatus()
  {
    return gameStatus;
  }

  public GameStatusOnStandBy getGameStatusOnStandBy()
  {
    return gameStatusOnStandBy;
  }

  public boolean publish()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case notPublished:
        setGameStatus(GameStatus.onStandBy);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean playGame()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case onStandBy:
        exitGameStatus();
        setGameStatus(GameStatus.inPlay);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean continueGame()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case onStandBy:
        exitGameStatus();
        setGameStatus(GameStatus.inPlay);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean deleteGame()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case onStandBy:
        exitGameStatus();
        setGameStatus(GameStatus.notPublished);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean blockHit()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case inPlay:
        if (levelNotAlmostOver())
        {
          setGameStatus(GameStatus.inPlay);
          wasEventProcessed = true;
          break;
        }
        if (levelAlmostOver())
        {
          setGameStatus(GameStatus.onStandBy);
          wasEventProcessed = true;
          break;
        }
        if (gameAlmostOver())
        {
          setGameStatusOnStandBy(GameStatusOnStandBy.gameOver);
          wasEventProcessed = true;
          break;
        }
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean movePaddle()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case inPlay:
        setGameStatus(GameStatus.inPlay);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean paddleHit()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case inPlay:
        setGameStatus(GameStatus.inPlay);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean moveBall()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case inPlay:
        setGameStatus(GameStatus.inPlay);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean wallHit()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case inPlay:
        setGameStatus(GameStatus.inPlay);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean paddleMiss()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case inPlay:
        // line 41 "../../../../../Block223_statemachine.ump"
        lives <= 0 || lives >= 3
        setGameStatusOnStandBy(GameStatusOnStandBy.gameOver);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean pause()
  {
    boolean wasEventProcessed = false;
    
    GameStatus aGameStatus = gameStatus;
    switch (aGameStatus)
    {
      case inPlay:
        setGameStatus(GameStatus.onStandBy);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean viewHF()
  {
    boolean wasEventProcessed = false;
    
    GameStatusOnStandBy aGameStatusOnStandBy = gameStatusOnStandBy;
    switch (aGameStatusOnStandBy)
    {
      case gameOver:
        exitGameStatusOnStandBy();
        setGameStatusOnStandBy(GameStatusOnStandBy.viewingHallOfFame);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void exitGameStatus()
  {
    switch(gameStatus)
    {
      case onStandBy:
        exitGameStatusOnStandBy();
        break;
    }
  }

  private void setGameStatus(GameStatus aGameStatus)
  {
    gameStatus = aGameStatus;

    // entry actions and do activities
    switch(gameStatus)
    {
      case onStandBy:
        if (gameStatusOnStandBy == GameStatusOnStandBy.Null) { setGameStatusOnStandBy(GameStatusOnStandBy.gameOver); }
        break;
    }
  }

  private void exitGameStatusOnStandBy()
  {
    switch(gameStatusOnStandBy)
    {
      case gameOver:
        setGameStatusOnStandBy(GameStatusOnStandBy.Null);
        break;
      case viewingHallOfFame:
        setGameStatusOnStandBy(GameStatusOnStandBy.Null);
        break;
    }
  }

  private void setGameStatusOnStandBy(GameStatusOnStandBy aGameStatusOnStandBy)
  {
    gameStatusOnStandBy = aGameStatusOnStandBy;
    if (gameStatus != GameStatus.onStandBy && aGameStatusOnStandBy != GameStatusOnStandBy.Null) { setGameStatus(GameStatus.onStandBy); }
  }

  public void delete()
  {}

}