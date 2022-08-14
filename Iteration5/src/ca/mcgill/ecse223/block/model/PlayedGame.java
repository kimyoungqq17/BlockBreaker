/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.model.BouncePoint.BounceDirection;

// line 11 "../../../../../Block223PlayMode.ump"
// line 65 "../../../../../Block223Persistence.ump"
// line 1 "../../../../../Block223States.ump"
public class PlayedGame implements Serializable
{

  //------------------------
  // STATIC VARIABLES
  //------------------------


  /**
   * at design time, the initial wait time may be adjusted as seen fit
   */
  public static final int INITIAL_WAIT_TIME = 1000;
  private static int nextId = 1;
  public static final int NR_LIVES = 3;

  /**
   * the PlayedBall and PlayedPaddle are not in a separate class to avoid the bug in Umple that occurred for the second constructor of Game
   * no direct link to Ball, because the ball can be found by navigating to PlayedGame, Game, and then Ball
   */
  public static final int BALL_INITIAL_X = Game.PLAY_AREA_SIDE / 2;
  public static final int BALL_INITIAL_Y = Game.PLAY_AREA_SIDE / 2;

  /**
   * no direct link to Paddle, because the paddle can be found by navigating to PlayedGame, Game, and then Paddle
   * pixels moved when right arrow key is pressed
   */
  public static final int PADDLE_MOVE_RIGHT = 1;

  /**
   * pixels moved when left arrow key is pressed
   */
  public static final int PADDLE_MOVE_LEFT = -1;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //PlayedGame Attributes
  private int score;
  private int lives;
  private int currentLevel;
  private double waitTime;
  private String playername;
  private double ballDirectionX;
  private double ballDirectionY;
  private double currentBallX;
  private double currentBallY;
  private double currentPaddleLength;
  private double currentPaddleX;
  private double currentPaddleY;

  //Autounique Attributes
  private int id;

  //PlayedGame State Machines
  public enum PlayStatus { Ready, Moving, Paused, GameOver }
  private PlayStatus playStatus;

  //PlayedGame Associations
  private Player player;
  private Game game;
  private List<PlayedBlockAssignment> blocks;
  private BouncePoint bounce;
  private Block223 block223;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public PlayedGame(String aPlayername, Game aGame, Block223 aBlock223)
  {
    score = 0;
    lives = NR_LIVES;
    currentLevel = 1;
    waitTime = INITIAL_WAIT_TIME;
    playername = aPlayername;
    ballDirectionX = getGame().getBall().getMinBallSpeedX();
    ballDirectionY = getGame().getBall().getMinBallSpeedY();
    resetCurrentBallX();
    resetCurrentBallY();
    currentPaddleLength = getGame().getPaddle().getMaxPaddleLength();
    resetCurrentPaddleX();
    currentPaddleY = Game.PLAY_AREA_SIDE - Paddle.VERTICAL_DISTANCE - Paddle.PADDLE_WIDTH;
    id = nextId++;
    boolean didAddGame = setGame(aGame);
    if (!didAddGame)
    {
      throw new RuntimeException("Unable to create playedGame due to game");
    }
    blocks = new ArrayList<PlayedBlockAssignment>();
    boolean didAddBlock223 = setBlock223(aBlock223);
    if (!didAddBlock223)
    {
      throw new RuntimeException("Unable to create playedGame due to block223");
    }
    setPlayStatus(PlayStatus.Ready);
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

  public boolean setLives(int aLives)
  {
    boolean wasSet = false;
    lives = aLives;
    wasSet = true;
    return wasSet;
  }

  public boolean setCurrentLevel(int aCurrentLevel)
  {
    boolean wasSet = false;
    currentLevel = aCurrentLevel;
    wasSet = true;
    return wasSet;
  }

  public boolean setWaitTime(double aWaitTime)
  {
    boolean wasSet = false;
    waitTime = aWaitTime;
    wasSet = true;
    return wasSet;
  }

  public boolean setPlayername(String aPlayername)
  {
    boolean wasSet = false;
    playername = aPlayername;
    wasSet = true;
    return wasSet;
  }

  public boolean setBallDirectionX(double aBallDirectionX)
  {
    boolean wasSet = false;
    ballDirectionX = aBallDirectionX;
    wasSet = true;
    return wasSet;
  }

  public boolean setBallDirectionY(double aBallDirectionY)
  {
    boolean wasSet = false;
    ballDirectionY = aBallDirectionY;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentBallX(double aCurrentBallX)
  {
    boolean wasSet = false;
    currentBallX = aCurrentBallX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentBallX()
  {
    boolean wasReset = false;
    currentBallX = getDefaultCurrentBallX();
    wasReset = true;
    return wasReset;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentBallY(double aCurrentBallY)
  {
    boolean wasSet = false;
    currentBallY = aCurrentBallY;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentBallY()
  {
    boolean wasReset = false;
    currentBallY = getDefaultCurrentBallY();
    wasReset = true;
    return wasReset;
  }

  public boolean setCurrentPaddleLength(double aCurrentPaddleLength)
  {
    boolean wasSet = false;
    currentPaddleLength = aCurrentPaddleLength;
    wasSet = true;
    return wasSet;
  }
  /* Code from template attribute_SetDefaulted */
  public boolean setCurrentPaddleX(double aCurrentPaddleX)
  {
    boolean wasSet = false;
    currentPaddleX = aCurrentPaddleX;
    wasSet = true;
    return wasSet;
  }

  public boolean resetCurrentPaddleX()
  {
    boolean wasReset = false;
    currentPaddleX = getDefaultCurrentPaddleX();
    wasReset = true;
    return wasReset;
  }

  public int getScore()
  {
    return score;
  }

  public int getLives()
  {
    return lives;
  }

  public int getCurrentLevel()
  {
    return currentLevel;
  }

  public double getWaitTime()
  {
    return waitTime;
  }

  /**
   * added here so that it only needs to be determined once
   */
  public String getPlayername()
  {
    return playername;
  }

  /**
   * 0/0 is the top left corner of the play area, i.e., a directionX/Y of 0/1 moves the ball down in a straight line
   */
  public double getBallDirectionX()
  {
    return ballDirectionX;
  }

  public double getBallDirectionY()
  {
    return ballDirectionY;
  }

  /**
   * the position of the ball is at the center of the ball
   */
  public double getCurrentBallX()
  {
    return currentBallX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentBallX()
  {
    return BALL_INITIAL_X;
  }

  public double getCurrentBallY()
  {
    return currentBallY;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentBallY()
  {
    return BALL_INITIAL_Y;
  }

  public double getCurrentPaddleLength()
  {
    return currentPaddleLength;
  }

  /**
   * the position of the paddle is at its top right corner
   */
  public double getCurrentPaddleX()
  {
    return currentPaddleX;
  }
  /* Code from template attribute_GetDefaulted */
  public double getDefaultCurrentPaddleX()
  {
    return (Game.PLAY_AREA_SIDE - currentPaddleLength) / 2;
  }

  public double getCurrentPaddleY()
  {
    return currentPaddleY;
  }

  public int getId()
  {
    return id;
  }

  public String getPlayStatusFullName()
  {
    String answer = playStatus.toString();
    return answer;
  }

  public PlayStatus getPlayStatus()
  {
    return playStatus;
  }

  public boolean play()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Ready:
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      case Paused:
        setPlayStatus(PlayStatus.Moving);
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
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Moving:
        setPlayStatus(PlayStatus.Paused);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  public boolean move()
  {
    boolean wasEventProcessed = false;
    
    PlayStatus aPlayStatus = playStatus;
    switch (aPlayStatus)
    {
      case Moving:
        if (hitPaddle())
        {
        // line 12 "../../../../../Block223States.ump"
          doHitPaddleOrWall();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBoundsAndLastLife())
        {
        // line 13 "../../../../../Block223States.ump"
          doOutOfBounds();
          setPlayStatus(PlayStatus.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (isOutOfBounds())
        {
        // line 14 "../../../../../Block223States.ump"
          doOutOfBounds();
          setPlayStatus(PlayStatus.Paused);
          wasEventProcessed = true;
          break;
        }
        if (hitLastBlockAndLastLevel())
        {
        // line 15 "../../../../../Block223States.ump"
          doHitBlock();
          setPlayStatus(PlayStatus.GameOver);
          wasEventProcessed = true;
          break;
        }
        if (hitLastBlock())
        {
        // line 16 "../../../../../Block223States.ump"
          doHitBlockNextLevel();
          setPlayStatus(PlayStatus.Ready);
          wasEventProcessed = true;
          break;
        }
        if (hitBlock())
        {
        // line 17 "../../../../../Block223States.ump"
          doHitBlock();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        if (hitWall())
        {
        // line 18 "../../../../../Block223States.ump"
          doHitPaddleOrWall();
          setPlayStatus(PlayStatus.Moving);
          wasEventProcessed = true;
          break;
        }
        // line 19 "../../../../../Block223States.ump"
        doHitNothingAndNotOutOfBounds();
        setPlayStatus(PlayStatus.Moving);
        wasEventProcessed = true;
        break;
      default:
        // Other states do respond to this event
    }

    return wasEventProcessed;
  }

  private void setPlayStatus(PlayStatus aPlayStatus)
  {
    playStatus = aPlayStatus;

    // entry actions and do activities
    switch(playStatus)
    {
      case Ready:
        // line 7 "../../../../../Block223States.ump"
        doSetup();
        break;
      case GameOver:
        // line 25 "../../../../../Block223States.ump"
        doGameOver();
        break;
    }
  }
  /* Code from template association_GetOne */
  public Player getPlayer()
  {
    return player;
  }

  public boolean hasPlayer()
  {
    boolean has = player != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
  /* Code from template association_GetMany */
  public PlayedBlockAssignment getBlock(int index)
  {
    PlayedBlockAssignment aBlock = blocks.get(index);
    return aBlock;
  }

  public List<PlayedBlockAssignment> getBlocks()
  {
    List<PlayedBlockAssignment> newBlocks = Collections.unmodifiableList(blocks);
    return newBlocks;
  }

  public int numberOfBlocks()
  {
    int number = blocks.size();
    return number;
  }

  public boolean hasBlocks()
  {
    boolean has = blocks.size() > 0;
    return has;
  }

  public int indexOfBlock(PlayedBlockAssignment aBlock)
  {
    int index = blocks.indexOf(aBlock);
    return index;
  }
  /* Code from template association_GetOne */
  public BouncePoint getBounce()
  {
    return bounce;
  }

  public boolean hasBounce()
  {
    boolean has = bounce != null;
    return has;
  }
  /* Code from template association_GetOne */
  public Block223 getBlock223()
  {
    return block223;
  }
  /* Code from template association_SetOptionalOneToMany */
  public boolean setPlayer(Player aPlayer)
  {
    boolean wasSet = false;
    Player existingPlayer = player;
    player = aPlayer;
    if (existingPlayer != null && !existingPlayer.equals(aPlayer))
    {
      existingPlayer.removePlayedGame(this);
    }
    if (aPlayer != null)
    {
      aPlayer.addPlayedGame(this);
    }
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
      existingGame.removePlayedGame(this);
    }
    game.addPlayedGame(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlocks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public PlayedBlockAssignment addBlock(int aX, int aY, Block aBlock)
  {
    return new PlayedBlockAssignment(aX, aY, aBlock, this);
  }

  public boolean addBlock(PlayedBlockAssignment aBlock)
  {
    boolean wasAdded = false;
    if (blocks.contains(aBlock)) { return false; }
    PlayedGame existingPlayedGame = aBlock.getPlayedGame();
    boolean isNewPlayedGame = existingPlayedGame != null && !this.equals(existingPlayedGame);
    if (isNewPlayedGame)
    {
      aBlock.setPlayedGame(this);
    }
    else
    {
      blocks.add(aBlock);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBlock(PlayedBlockAssignment aBlock)
  {
    boolean wasRemoved = false;
    //Unable to remove aBlock, as it must always have a playedGame
    if (!this.equals(aBlock.getPlayedGame()))
    {
      blocks.remove(aBlock);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBlockAt(PlayedBlockAssignment aBlock, int index)
  {  
    boolean wasAdded = false;
    if(addBlock(aBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlocks()) { index = numberOfBlocks() - 1; }
      blocks.remove(aBlock);
      blocks.add(index, aBlock);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBlockAt(PlayedBlockAssignment aBlock, int index)
  {
    boolean wasAdded = false;
    if(blocks.contains(aBlock))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlocks()) { index = numberOfBlocks() - 1; }
      blocks.remove(aBlock);
      blocks.add(index, aBlock);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBlockAt(aBlock, index);
    }
    return wasAdded;
  }
  /* Code from template association_SetUnidirectionalOptionalOne */
  public boolean setBounce(BouncePoint aNewBounce)
  {
    boolean wasSet = false;
    bounce = aNewBounce;
    wasSet = true;
    return wasSet;
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
      existingBlock223.removePlayedGame(this);
    }
    block223.addPlayedGame(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    if (player != null)
    {
      Player placeholderPlayer = player;
      this.player = null;
      placeholderPlayer.removePlayedGame(this);
    }
    Game placeholderGame = game;
    this.game = null;
    if(placeholderGame != null)
    {
      placeholderGame.removePlayedGame(this);
    }
    while (blocks.size() > 0)
    {
      PlayedBlockAssignment aBlock = blocks.get(blocks.size() - 1);
      aBlock.delete();
      blocks.remove(aBlock);
    }
    
    bounce = null;
    Block223 placeholderBlock223 = block223;
    this.block223 = null;
    if(placeholderBlock223 != null)
    {
      placeholderBlock223.removePlayedGame(this);
    }
  }


  /**
   * Guards
   */
  // line 32 "../../../../../Block223States.ump"
   private boolean hitPaddle(){
    // TODO implement
	   //create 5 rectangle boundaries around paddle
	   Rectangle2D A = new Rectangle2D.Double(getCurrentPaddleX(), getCurrentPaddleY()-5, getCurrentPaddleLength(), 10);
		Rectangle2D B = new Rectangle2D.Double(getCurrentPaddleX()-5, getCurrentPaddleY(), 5, 5);
		Rectangle2D C = new Rectangle2D.Double(getCurrentPaddleX()+getCurrentPaddleLength(), getCurrentPaddleY(), 5, 5);
		Rectangle2D E = new Rectangle2D.Double(getCurrentPaddleX()-5, getCurrentPaddleY()-5, 5, 5);
		Rectangle2D F = new Rectangle2D.Double(getCurrentPaddleX()+getCurrentPaddleLength(), getCurrentPaddleY()-5, 5, 5);
		
		ArrayList<Rectangle2D> boundary = new ArrayList<Rectangle2D>();
		boundary.add(A);
		boundary.add(B);
		boundary.add(C);
		boundary.add(E);
		boundary.add(F);
		
		double dx = getBallDirectionX();
		double dy = getBallDirectionY();
		double  x1 = getCurrentBallX();
		double	y1 = getCurrentBallY();
		double x2 = x1+dx;
		double y2 = y1+dy;
		
		int whichboundary = -1;
		Boolean ballintersects = false;
		for (int i=0; i< boundary.size(); i++){
		 if(boundary.get(i).intersectsLine(x1,y1, x2, y2)==true){
			ballintersects = true;
			whichboundary = i;
		//TODO how to determine when more than one intersection is possible	
			// intersection points on curved segments of E and F needs revision
		}
		
		 double[] s = getgradientandconstant(x1,y1,x2,y2);
		 double intersectionx, intersectiony;
			 if(whichboundary == 0){//todo change arraylist to hashmap
			 	//then its A
			 	
			 	intersectionx = ((getCurrentPaddleY()-5)-s[1])/(s[0]);
			 	intersectiony=  getCurrentPaddleY()-5;
			 	if(intersectionx >= getCurrentPaddleX() && intersectiony <getCurrentPaddleX()+ getCurrentPaddleLength())
			 	{
			 	BouncePoint bp = new BouncePoint(intersectionx,intersectiony,BounceDirection.FLIP_Y);
			 	setBounce(bp);
			 	}
			 
			 		
			 }
			 if(whichboundary == 1){ //then its B
			 	
				intersectionx = getCurrentPaddleX()-5;
			 	intersectiony=  s[0]*(getCurrentPaddleX()-5) + s[1];
			 	if(intersectiony >= getCurrentPaddleY() && intersectiony <= getCurrentPaddleY()+ 5)
			 	{
			 	BouncePoint bp = new BouncePoint(intersectionx,intersectiony,BounceDirection.FLIP_X);
			 	setBounce(bp);
			 	}
			 }	
			 	
			 if(whichboundary == 2){ //then its C
			 	
				intersectionx = getCurrentPaddleX()+getCurrentPaddleLength() + 5;
			 	intersectiony=  s[0]*(getCurrentPaddleX()+getCurrentPaddleLength() + 5) + s[1];
			 	if(intersectiony >= getCurrentPaddleY() && intersectiony <= getCurrentPaddleY()+ 5)
			 	{
			 	BouncePoint bp = new BouncePoint(intersectionx,intersectiony,BounceDirection.FLIP_X);
			 	setBounce(bp);
			 	}
			 		
			 }
			 if(whichboundary == 3){ //then its E
				 //TODO for now treat curve as a straight line but must change somehow
				 //need to intersect a circle with a line segment 
				 //once I have the intersection with a circle, then 
				 //I have to check whether the intersection point is in the corner box.

			 	double[] bE = getgradientandconstant(getCurrentPaddleX()-5, getCurrentPaddleY(),
			 					 getCurrentPaddleX(), getCurrentPaddleY()-5);

			 	intersectionx =(bE[1]-s[1])/(s[0]-bE[0]);
			 	intersectiony= s[0]*x1 + s[1];
			 	
			 	if(x1 > getCurrentPaddleX()-5){
			 		BouncePoint bp = new BouncePoint(intersectionx,intersectiony,BounceDirection.FLIP_Y);
				 	setBounce(bp);
			 	}else{
			 		BouncePoint bp = new BouncePoint(intersectionx,intersectiony,BounceDirection.FLIP_X);
				 	setBounce(bp);
			 	}	
			 		
			 }
			 if(whichboundary == 4){ //then its F
				 //TODO for now treat curve as a straight line but must change somehow
			 	double[] bF = getgradientandconstant(getCurrentPaddleX()+ getCurrentPaddleLength(), getCurrentPaddleY()-5,
			 			getCurrentPaddleX()+ getCurrentPaddleLength()+ 5,getCurrentPaddleY());

			 	intersectionx =(bF[1]-s[1])/(s[0]-bF[0]);
			 	intersectiony= s[0]*x1 + s[1];
			 	
			 	if(x1 < getCurrentPaddleX()+ getCurrentPaddleLength()+5){
			 		BouncePoint bp = new BouncePoint(intersectionx,intersectiony,BounceDirection.FLIP_X);
				 	setBounce(bp);
			 	}else{
			 		BouncePoint bp = new BouncePoint(intersectionx,intersectiony,BounceDirection.FLIP_Y);
				 	setBounce(bp);
			 	}	
			 	
			 		
			 }
		}
    return ballintersects;
  }

  private double[] getgradientandconstant(double x1, double y1, double x2, double y2) {
	// TODO Auto-generated method stub
	  double[] a = new double[2];
	  double m; double b;
	  	m = (y2-y1)/(x2-x1);
	  	b = y1-m*(x1);
	  	a[0]=m;
	  	a[1]=b; 
	return a;
}

// line 37 "../../../../../Block223States.ump"
   private boolean isOutOfBoundsAndLastLife(){
	   boolean outOfBounds = false;
	   if (lives == 1) {
		   outOfBounds = isOutOfBounds();
	   }
    return outOfBounds;
  }

  // line 42 "../../../../../Block223States.ump"
   private boolean isOutOfBounds(){
	   //check if ball position is out of bounds
	   boolean outOfBounds = false;
	   if(currentBallY + Ball.BALL_DIAMETER >= Game.PLAY_AREA_SIDE) {
		
		   outOfBounds = true;
	   }else {
		   isOutOfBounds();				//depending on if looped somewhere else??????????????
		   								//maybe return false otherwise!!!!!!!!!!!!!!!!
	   }
	   
	   return outOfBounds;
  }

  // line 47 "../../../../../Block223States.ump"
   private boolean hitLastBlockAndLastLevel(){
    // TODO implement
	   
Game game = getGame();
	   int nrLevels = game.numberOfLevels();
	   int nrBlocks;
	   
	   setBounce(null);
	   
	   if(nrLevels == currentLevel) {
		   nrBlocks = numberOfBlocks();
		   
		   if(nrBlocks == 1) {
			   PlayedBlockAssignment block = getBlock(0);
			   
			   // gets ball position and velocity and slope
			   double x1 = getCurrentBallX();
			   double y1 = getCurrentBallY();
			   double dx = getBallDirectionX();
			   double dy = getBallDirectionY();
			   
			   // determines size and coordinates of rectangle
			   // adjusts from block.getX() because position of block is from top right corner
			   // but position of rectangle2D is from top left
			   double hitboxSize = (double) (Block.SIZE + Ball.BALL_DIAMETER);
			   double hitboxX = block.getX() - hitboxSize;
			   double hitboxY = block.getY();
			   
			   // creates the block's hitbox
			   Rectangle2D.Double hitbox = new Rectangle2D.Double(hitboxX, hitboxY, hitboxSize, hitboxSize);
			   
			   // detects if ball will hit hitbox
			   boolean intersects = hitbox.intersectsLine(x1, y1, x1 + dx, y1 + dy);
			   
			   // if ball will hit hitbox, calculates bounce point
			   if(intersects) {
				   double radius = ((double) Ball.BALL_DIAMETER) / 2;
				   
				   // defines regions A through H
				   Line2D.Double A = new Line2D.Double(hitboxX + radius, hitboxX + hitboxSize - radius, hitboxY, hitboxY);
				   Line2D.Double B = new Line2D.Double(hitboxX, hitboxX, hitboxY + radius, hitboxY + hitboxSize - radius);
				   Line2D.Double C = new Line2D.Double(hitboxX + hitboxSize, hitboxX + hitboxSize, hitboxY + radius, hitboxY + hitboxSize - radius);
				   Line2D.Double D = new Line2D.Double(hitboxX + radius, hitboxX + hitboxSize - radius, hitboxY + hitboxSize, hitboxY + hitboxSize);
				   Ellipse2D.Double E = new Ellipse2D.Double(hitboxX, hitboxY, radius * 2, radius * 2);
				   Ellipse2D.Double F = new Ellipse2D.Double(hitboxX + hitboxSize - radius, hitboxY, radius * 2, radius * 2);
				   Ellipse2D.Double G = new Ellipse2D.Double(hitboxX, hitboxY + hitboxSize - radius, radius * 2, radius * 2);
				   Ellipse2D.Double H = new Ellipse2D.Double(hitboxX + hitboxSize - radius, hitboxY + hitboxSize - radius, radius * 2, radius * 2);
				   
				   // initializes all the possible bounce points
				   BouncePoint bp = null;
				   //BouncePoint bpB = null;
				   //BouncePoint bpC = null;
				   //BouncePoint bpD = null;
				   //BouncePoint bpE = null;
				   //BouncePoint bpF = null;
				   //BouncePoint bpG = null;
				   //BouncePoint bpH = null;
				   
				   // checks if intersects with A, B, C, or D
				   // if so, calculates bounce point
				   if(A.intersectsLine(x1, y1, x1 + dx, y1 + dy)) {
					   double Ax1 = A.getX1();
					   double Ay1 = A.getY1();
					   double Ax2 = A.getX2();
					   double Ay2 = A.getY2();
					   
					   // if ball has vertical trajectory
					   // create a new bounce point easily
					   if(dx == 0) {
						   BouncePoint bpA = new BouncePoint(x1, Ay1, BounceDirection.FLIP_Y);
						   
						   if(bp == null) {
							   bp = bpA;
						   }
						   else {
							   if(Point2D.Double.distance(x1, y1, bpA.getX(), bpA.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
								   bp = bpA;
							   }
						   }

					   }
					   
					   // else, calculates the intersection point to create the bounce point
					   else {
						   // determines slope and y-intercept of ball's motion
						   double m = dy / dx;
						   double b = y1 - (m * x1);
						   
						   // calculates the intersection point
						   double intersectionX = (Ay1 - b) / m;
						   double intersectionY = (m * intersectionX) + b;
						   
						   BouncePoint bpA = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
						   if(bp == null) {
							   bp = bpA;
						   }
						   else {
							   if(Point2D.Double.distance(x1, y1, bpA.getX(), bpA.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
								   bp = bpA;
							   }
						   }
					   }
					   
				   }
				   if(B.intersectsLine(x1, y1, x1 + dx, y1 + dy)) {
					   double Bx1 = B.getX1();
					   double By1 = B.getY1();
					   double Bx2 = B.getX2();
					   double By2 = B.getY2();
					   
					   // if ball has horizontal trajectory
					   // create a new bounce point easily
					   if(dy == 0) {
						   BouncePoint bpB = new BouncePoint(Bx1, y1, BounceDirection.FLIP_X);
						   if(bp == null) {
							   bp = bpB;
						   }
						   else {
							   if(Point2D.Double.distance(x1, y1, bpB.getX(), bpB.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
								   bp = bpB;
							   }
						   }
					   }
					   
					   // else, calculates the intersection point to create the bounce point
					   else {
						   // determines slope and y-intercept of ball's motion
						   double m = dy / dx;
						   double b = y1 - (m * x1);
						   
						   // calculates the intersection point
						   double intersectionX = (By1 - b) / m;
						   double intersectionY = (m * intersectionX) + b;
						   
						   BouncePoint bpB = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
						   if(bp == null) {
							   bp = bpB;
						   }
						   else {
							   if(Point2D.Double.distance(x1, y1, bpB.getX(), bpB.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
								   bp = bpB;
							   }
						   }
					   }
				   }
				   if(C.intersectsLine(x1, y1, x1 + dx, y1 + dy)) {
					   double Cx1 = C.getX1();
					   double Cy1 = C.getY1();
					   double Cx2 = C.getX2();
					   double Cy2 = C.getY2();
					   
					   // if ball has horizontal trajectory
					   // create a new bounce point easily
					   if(dy == 0) {
						   BouncePoint bpC = new BouncePoint(Cx1, y1, BounceDirection.FLIP_X);
						   if(bp == null) {
							   bp = bpC;
						   }
						   else {
							   if(Point2D.Double.distance(x1, y1, bpC.getX(), bpC.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
								   bp = bpC;
							   }
						   }
					   }
					   
					   // else, calculates the intersection point to create the bounce point
					   else {
						   // determines slope and y-intercept of ball's motion
						   double m = dy / dx;
						   double b = y1 - (m * x1);
						   
						   // calculates the intersection point
						   double intersectionX = (Cy1 - b) / m;
						   double intersectionY = (m * intersectionX) + b;
						   
						   BouncePoint bpC = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
						   if(bp == null) {
							   bp = bpC;
						   }
						   else {
							   if(Point2D.Double.distance(x1, y1, bpC.getX(), bpC.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
								   bp = bpC;
							   }
						   }
					   }
				   }
				   if(D.intersectsLine(x1, y1, x1 + dx, y1 + dy)) {
					   double Dx1 = D.getX1();
					   double Dy1 = D.getY1();
					   double Dx2 = D.getX2();
					   double Dy2 = D.getY2();
					   
					   // if ball has vertical trajectory
					   // create a new bounce point easily
					   if(dx == 0) {
						   BouncePoint bpD = new BouncePoint(x1, Dy1, BounceDirection.FLIP_Y);
						   if(bp == null) {
							   bp = bpD;
						   }
						   else {
							   if(Point2D.Double.distance(x1, y1, bpD.getX(), bpD.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
								   bp = bpD;
							   }
						   }
					   }
					   
					   // else, calculates the intersection point to create the bounce point
					   else {
						   // determines slope and y-intercept of ball's motion
						   double m = dy / dx;
						   double b = y1 - (m * x1);
						   
						   // calculates the intersection point
						   double intersectionX = (Dy1 - b) / m;
						   double intersectionY = (m * intersectionX) + b;
						   
						   BouncePoint bpD = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
						   if(bp == null) {
							   bp = bpD;
						   }
						   else {
							   if(Point2D.Double.distance(x1, y1, bpD.getX(), bpD.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
								   bp = bpD;
							   }
						   }
					   }
				   }
				   
				   // checks if intersects with E, F, G, or H
				   		// if so, calculates if/where intersects with the curve
				   
				   // checks for E
				   if(E.contains(x1 + dx, y1 + dy)) {
				       // sets the coordinates of the center of the circle
					   double Ex = hitboxX + radius;
					   double Ey = hitboxY + radius;
				       
					   double EcenterToBallX = Ex - x1;
				       double EcenterToBallY = Ey - y1;
				       
				       double a = dx * dx + dy * dy;
				       double bBy2 = dx * EcenterToBallX + dy * EcenterToBallY;
				       double c = EcenterToBallX * EcenterToBallX + EcenterToBallY * EcenterToBallY - radius * radius;

				       double pBy2 = bBy2 / a;
				       double q = c / a;

				       double disc = pBy2 * pBy2 - q;
				      
				       double tmpSqrt = Math.sqrt(disc);
				       double abScalingFactor1 = -pBy2 + tmpSqrt;
				       double abScalingFactor2 = -pBy2 - tmpSqrt;
				       
				       // if disc > 0, two intersection points
				       if (disc > 0) {
					       // calculates intersection points
					       double intersection1X = x1 - dx * abScalingFactor1;
					       double intersection1Y = y1 - dy * abScalingFactor1;
					       
					       double intersection2X = x1 - dx * abScalingFactor2;
					       double intersection2Y = y1 - dy * abScalingFactor2;
					       
					       // determines which, if any, of the points is in the correct quadrant
					       boolean intersection1InQuadrant = (intersection1X >= Ex - radius && intersection1X <= Ex && 
					    		   								intersection1Y >= Ey - radius && intersection1Y <= Ey);
					       boolean intersection2InQuadrant = (intersection2X >= Ex - radius && intersection2X <= Ex && 
	   															intersection2Y >= Ey - radius && intersection2Y <= Ey);
					       
					       if(intersection1InQuadrant && intersection2InQuadrant) {
					    	   // determines which one is closer
					    	   double ballToP1 = Point2D.Double.distance(x1, y1, intersection1X, intersection1Y);
					    	   double ballToP2 = Point2D.Double.distance(x1, y1, intersection2X, intersection2Y);
					    	   
					    	   if(ballToP1 < ballToP2) {
					    		   // creates bounce point
					    		   if(dx > 0) {
					    			   BouncePoint bpE = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
									   if(bp == null) {
										   bp = bpE;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpE;
										   }
									   }
						    	   }
						    	   else {
						    		   BouncePoint bpE = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
									   if(bp == null) {
										   bp = bpE;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpE;
										   }
									   }
						    	   }
					    	   }
					    	   else if(ballToP2 < ballToP1) {
						    	   // creates bounce point
						    	   if(dx > 0) {
						    		   BouncePoint bpE = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
									   if(bp == null) {
										   bp = bpE;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpE;
										   }
									   }
						    	   }
						    	   else {
						    		   BouncePoint bpE = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
									   if(bp == null) {
										   bp = bpE;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpE;
										   }
									   }
						    	   }
					    	   }
					       }
					       else if(intersection1InQuadrant) {
					    	   // creates bounce point
					    	   if(dx > 0) {
					    		   BouncePoint bpE = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpE;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpE;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpE = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpE;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpE;
									   }
								   }
					    	   }
					       }
					       else if(intersection2InQuadrant) {
					    	   // creates bounce point
					    	   if(dx > 0) {
					    		   BouncePoint bpE = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpE;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpE;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpE = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpE;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpE;
									   }
								   }
					    	   }
					       }
				       }
				       
				       // if disc == 0, one intersection
				       if(disc == 0) {
					       double intersectionX = x1 - dx * abScalingFactor1;
					       double intersectionY = y1 - dy * abScalingFactor1;
					       
					       boolean intersectionInQuadrant = (intersectionX >= Ex - radius && intersectionX <= Ex &&
					    		   								intersectionY >= Ey - radius && intersectionY <= Ey);
					       
					       if(intersectionInQuadrant) {
					    	   // creates bounce point
					    	   if(dx > 0) {
					    		   BouncePoint bpE = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpE;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpE;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpE = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpE;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpE;
									   }
								   }
					    	   }
					       }
				       }
				   }
				   
				   // checks for F
				   if(F.contains(x1 + dx, y1 + dy)) {
				       // sets the coordinates of the center of the circle
					   double Fx = hitboxX + hitboxSize - radius;
					   double Fy = hitboxY + radius;
				       
					   double FcenterToBallX = Fx - x1;
				       double FcenterToBallY = Fy - y1;
				       
				       double a = dx * dx + dy * dy;
				       double bBy2 = dx * FcenterToBallX + dy * FcenterToBallY;
				       double c = FcenterToBallX * FcenterToBallX + FcenterToBallY * FcenterToBallY - radius * radius;

				       double pBy2 = bBy2 / a;
				       double q = c / a;

				       double disc = pBy2 * pBy2 - q;
				      
				       double tmpSqrt = Math.sqrt(disc);
				       double abScalingFactor1 = -pBy2 + tmpSqrt;
				       double abScalingFactor2 = -pBy2 - tmpSqrt;
				       
				       // if disc > 0, two intersection points
				       if (disc > 0) {
					       // calculates intersection points
					       double intersection1X = x1 - dx * abScalingFactor1;
					       double intersection1Y = y1 - dy * abScalingFactor1;
					       
					       double intersection2X = x1 - dx * abScalingFactor2;
					       double intersection2Y = y1 - dy * abScalingFactor2;
					       
					       // determines which, if any, of the points is in the correct quadrant
					       boolean intersection1InQuadrant = (intersection1X >= Fx && intersection1X <= Fx + radius && 
					    		   								intersection1Y >= Fy - radius && intersection1Y <= Fy);
					       boolean intersection2InQuadrant = (intersection2X >= Fx && intersection2X <= Fx + radius && 
	   															intersection2Y >= Fy - radius && intersection2Y <= Fy);
					       
					       if(intersection1InQuadrant && intersection2InQuadrant) {
					    	   // determines which one is closer
					    	   double ballToP1 = Point2D.Double.distance(x1, y1, intersection1X, intersection1Y);
					    	   double ballToP2 = Point2D.Double.distance(x1, y1, intersection2X, intersection2Y);
					    	   
					    	   if(ballToP1 < ballToP2) {
					    		   // creates bounce point
					    		   if(dx >= 0) {
					    			   BouncePoint bpF = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
									   if(bp == null) {
										   bp = bpF;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpF;
										   }
									   }
						    	   }
						    	   else {
						    		   BouncePoint bpF = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
									   if(bp == null) {
										   bp = bpF;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpF;
										   }
									   }
						    	   }
					    	   }
					    	   else if(ballToP2 < ballToP1) {
						    	   // creates bounce point
						    	   if(dx >= 0) {
						    		   BouncePoint bpF = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
									   if(bp == null) {
										   bp = bpF;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpF;
										   }
									   }
						    	   }
						    	   else {
						    		   BouncePoint bpF = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
									   if(bp == null) {
										   bp = bpF;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpF;
										   }
									   }
						    	   }
					    	   }
					       }
					       else if(intersection1InQuadrant) {
					    	   // creates bounce point
					    	   if(dx >= 0) {
					    		   BouncePoint bpF = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpF;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpF;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpF = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpF;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpF;
									   }
								   }
					    	   }
					       }
					       else if(intersection2InQuadrant) {
					    	   // creates bounce point
					    	   if(dx >= 0) {
					    		   BouncePoint bpF = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpF;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpF;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpF = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpF;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpF;
									   }
								   }
					    	   }
					       }
				       }
				       
				       // if disc == 0, one intersection
				       if(disc == 0) {
					       double intersectionX = x1 - dx * abScalingFactor1;
					       double intersectionY = y1 - dy * abScalingFactor1;
					       
					       boolean intersectionInQuadrant = (intersectionX >= Fx && intersectionX <= Fx + radius &&
					    		   								intersectionY >= Fy - radius && intersectionY <= Fy);
					       
					       if(intersectionInQuadrant) {
					    	   // creates bounce point
					    	   if(dx >= 0) {
					    		   BouncePoint bpF = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpF;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpF;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpF = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpF;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpF;
									   }
								   }
					    	   }
					       }
				       }
				   }
				   
				   // checks for G
				   if(G.contains(x1 + dx, y1 + dy)) {
				       // sets the coordinates of the center of the circle
					   double Gx = hitboxX + radius;
					   double Gy = hitboxY + hitboxSize - radius;
				       
					   double GcenterToBallX = Gx - x1;
				       double GcenterToBallY = Gy - y1;
				       
				       double a = dx * dx + dy * dy;
				       double bBy2 = dx * GcenterToBallX + dy * GcenterToBallY;
				       double c = GcenterToBallX * GcenterToBallX + GcenterToBallY * GcenterToBallY - radius * radius;

				       double pBy2 = bBy2 / a;
				       double q = c / a;

				       double disc = pBy2 * pBy2 - q;
				      
				       double tmpSqrt = Math.sqrt(disc);
				       double abScalingFactor1 = -pBy2 + tmpSqrt;
				       double abScalingFactor2 = -pBy2 - tmpSqrt;
				       
				       // if disc > 0, two intersection points
				       if (disc > 0) {
					       // calculates intersection points
					       double intersection1X = x1 - dx * abScalingFactor1;
					       double intersection1Y = y1 - dy * abScalingFactor1;
					       
					       double intersection2X = x1 - dx * abScalingFactor2;
					       double intersection2Y = y1 - dy * abScalingFactor2;
					       
					       // determines which, if any, of the points is in the correct quadrant
					       boolean intersection1InQuadrant = (intersection1X >= Gx - radius && intersection1X <= Gx && 
					    		   								intersection1Y >= Gy && intersection1Y <= Gy + radius);
					       boolean intersection2InQuadrant = (intersection2X >= Gx - radius && intersection2X <= Gx && 
	   															intersection2Y >= Gy && intersection2Y <= Gy + radius);
					       
					       if(intersection1InQuadrant && intersection2InQuadrant) {
					    	   // determines which one is closer
					    	   double ballToP1 = Point2D.Double.distance(x1, y1, intersection1X, intersection1Y);
					    	   double ballToP2 = Point2D.Double.distance(x1, y1, intersection2X, intersection2Y);
					    	   
					    	   if(ballToP1 < ballToP2) {
					    		   // creates bounce point
					    		   if(dx > 0) {
					    			   BouncePoint bpG = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
									   if(bp == null) {
										   bp = bpG;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpG;
										   }
									   }
						    	   }
						    	   else {
						    		   BouncePoint bpG = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
									   if(bp == null) {
										   bp = bpG;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpG;
										   }
									   }
						    	   }
					    	   }
					    	   else if(ballToP2 < ballToP1) {
						    	   // creates bounce point
						    	   if(dx > 0) {
						    		   BouncePoint bpG = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
									   if(bp == null) {
										   bp = bpG;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpG;
										   }
									   }
						    	   }
						    	   else {
						    		   BouncePoint bpG = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
									   if(bp == null) {
										   bp = bpG;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpG;
										   }
									   }
						    	   }
					    	   }
					       }
					       else if(intersection1InQuadrant) {
					    	   // creates bounce point
					    	   if(dx > 0) {
					    		   BouncePoint bpG = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpG;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpG;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpG = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpG;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpG;
									   }
								   }
					    	   }
					       }
					       else if(intersection2InQuadrant) {
					    	   // creates bounce point
					    	   if(dx > 0) {
					    		   BouncePoint bpG = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpG;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpG;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpG = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpG;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpG;
									   }
								   }
					    	   }
					       }
				       }
				       
				       // if disc == 0, one intersection
				       if(disc == 0) {
					       double intersectionX = x1 - dx * abScalingFactor1;
					       double intersectionY = y1 - dy * abScalingFactor1;
					       
					       boolean intersectionInQuadrant = (intersectionX >= Gx - radius && intersectionX <= Gx &&
					    		   								intersectionY >= Gy && intersectionY <= Gy + radius);
					       
					       if(intersectionInQuadrant) {
					    	   // creates bounce point
					    	   if(dx > 0) {
					    		   BouncePoint bpG = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpG;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpG;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpG = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpG;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpG;
									   }
								   }
					    	   }
					       }
				       }
				   }
				   
				   // checks for H
				   if(H.contains(x1 + dx, y1 + dy)) {
				       // sets the coordinates of the center of the circle
					   double Hx = hitboxX + hitboxSize - radius;
					   double Hy = hitboxY + hitboxSize - radius;
				       
					   double HcenterToBallX = Hx - x1;
				       double HcenterToBallY = Hy - y1;
				       
				       double a = dx * dx + dy * dy;
				       double bBy2 = dx * HcenterToBallX + dy * HcenterToBallY;
				       double c = HcenterToBallX * HcenterToBallX + HcenterToBallY * HcenterToBallY - radius * radius;

				       double pBy2 = bBy2 / a;
				       double q = c / a;

				       double disc = pBy2 * pBy2 - q;
				      
				       double tmpSqrt = Math.sqrt(disc);
				       double abScalingFactor1 = -pBy2 + tmpSqrt;
				       double abScalingFactor2 = -pBy2 - tmpSqrt;
				       
				       // if disc > 0, two intersection points
				       if (disc > 0) {
					       // calculates intersection points
					       double intersection1X = x1 - dx * abScalingFactor1;
					       double intersection1Y = y1 - dy * abScalingFactor1;
					       
					       double intersection2X = x1 - dx * abScalingFactor2;
					       double intersection2Y = y1 - dy * abScalingFactor2;
					       
					       // determines which, if any, of the points is in the correct quadrant
					       boolean intersection1InQuadrant = (intersection1X >= Hx && intersection1X <= Hx + radius && 
					    		   								intersection1Y >= Hy && intersection1Y <= Hy + radius);
					       boolean intersection2InQuadrant = (intersection2X >= Hx && intersection2X <= Hx + radius && 
	   															intersection2Y >= Hy && intersection2Y <= Hy + radius);
					       
					       if(intersection1InQuadrant && intersection2InQuadrant) {
					    	   // determines which one is closer
					    	   double ballToP1 = Point2D.Double.distance(x1, y1, intersection1X, intersection1Y);
					    	   double ballToP2 = Point2D.Double.distance(x1, y1, intersection2X, intersection2Y);
					    	   
					    	   if(ballToP1 < ballToP2) {
					    		   // creates bounce point
					    		   if(dx >= 0) {
					    			   BouncePoint bpH = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
									   if(bp == null) {
										   bp = bpH;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpH;
										   }
									   }
						    	   }
						    	   else {
						    		   BouncePoint bpH = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
									   if(bp == null) {
										   bp = bpH;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpH;
										   }
									   }
						    	   }
					    	   }
					    	   else if(ballToP2 < ballToP1) {
						    	   // creates bounce point
						    	   if(dx >= 0) {
						    		   BouncePoint bpH = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
									   if(bp == null) {
										   bp = bpH;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpH;
										   }
									   }
						    	   }
						    	   else {
						    		   BouncePoint bpH = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
									   if(bp == null) {
										   bp = bpH;
									   }
									   else {
										   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
											   bp = bpH;
										   }
									   }
						    	   }
					    	   }
					       }
					       else if(intersection1InQuadrant) {
					    	   // creates bounce point
					    	   if(dx >= 0) {
					    		   BouncePoint bpH = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpH;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpH;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpH = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpH;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpH;
									   }
								   }
					    	   }
					       }
					       else if(intersection2InQuadrant) {
					    	   // creates bounce point
					    	   if(dx >= 0) {
					    		   BouncePoint bpH = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpH;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpH;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpH = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpH;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpH;
									   }
								   }
					    	   }
					       }
				       }
				       
				       // if disc == 0, one intersection
				       if(disc == 0) {
					       double intersectionX = x1 - dx * abScalingFactor1;
					       double intersectionY = y1 - dy * abScalingFactor1;
					       
					       boolean intersectionInQuadrant = (intersectionX >= Hx && intersectionX <= Hx + radius &&
					    		   								intersectionY >= Hy && intersectionY <= Hy + radius);
					       
					       if(intersectionInQuadrant) {
					    	   // creates bounce point
					    	   if(dx >= 0) {
					    		   BouncePoint bpH = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpH;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpH;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpH = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpH;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpH;
									   }
								   }
					    	   }
					       }
				       }
				   }
				   setBounce(bp);
			   }
			   
			   if(getBounce() != null) {
				   return true;
			   }
			   else return false;
			   // set bounce point
			   // return true if bounce point != null
		   }
		   
		   else return false;
	   }
    return false;
  }

  // line 52 "../../../../../Block223States.ump"
   private boolean hitLastBlock(){
    // TODO implement
	   
	   int nrBlocks = numberOfBlocks();
	   
	   setBounce(null);
	   
	   if(nrBlocks == 1) {
		   PlayedBlockAssignment block = getBlock(0);
		   // calculate bounce point (will be null if no bounce point)
		   // set bounce point
		   // return true if bounce point != null
		   
		   // gets ball position and velocity and slope
		   double x1 = getCurrentBallX();
		   double y1 = getCurrentBallY();
		   double dx = getBallDirectionX();
		   double dy = getBallDirectionY();
		   
		   // determines size and coordinates of rectangle
		   // adjusts from block.getX() because position of block is from top right corner
		   // but position of rectangle2D is from top left
		   double hitboxSize = (double) (Block.SIZE + Ball.BALL_DIAMETER);
		   double hitboxX = block.getX() - hitboxSize;
		   double hitboxY = block.getY();
		   
		   // creates the block's hitbox
		   Rectangle2D.Double hitbox = new Rectangle2D.Double(hitboxX, hitboxY, hitboxSize, hitboxSize);
		   
		   // detects if ball will hit hitbox
		   boolean intersects = hitbox.intersectsLine(x1, y1, x1 + dx, y1 + dy);
		   
		   // if ball will hit hitbox, calculates bounce point
		   if(intersects) {
			   double radius = ((double) Ball.BALL_DIAMETER) / 2;
			   
			   // defines regions A through H
			   Line2D.Double A = new Line2D.Double(hitboxX + radius, hitboxX + hitboxSize - radius, hitboxY, hitboxY);
			   Line2D.Double B = new Line2D.Double(hitboxX, hitboxX, hitboxY + radius, hitboxY + hitboxSize - radius);
			   Line2D.Double C = new Line2D.Double(hitboxX + hitboxSize, hitboxX + hitboxSize, hitboxY + radius, hitboxY + hitboxSize - radius);
			   Line2D.Double D = new Line2D.Double(hitboxX + radius, hitboxX + hitboxSize - radius, hitboxY + hitboxSize, hitboxY + hitboxSize);
			   Ellipse2D.Double E = new Ellipse2D.Double(hitboxX, hitboxY, radius * 2, radius * 2);
			   Ellipse2D.Double F = new Ellipse2D.Double(hitboxX + hitboxSize - radius, hitboxY, radius * 2, radius * 2);
			   Ellipse2D.Double G = new Ellipse2D.Double(hitboxX, hitboxY + hitboxSize - radius, radius * 2, radius * 2);
			   Ellipse2D.Double H = new Ellipse2D.Double(hitboxX + hitboxSize - radius, hitboxY + hitboxSize - radius, radius * 2, radius * 2);
			   
			   // initializes all the possible bounce points
			   BouncePoint bp = null;
			   //BouncePoint bpB = null;
			   //BouncePoint bpC = null;
			   //BouncePoint bpD = null;
			   //BouncePoint bpE = null;
			   //BouncePoint bpF = null;
			   //BouncePoint bpG = null;
			   //BouncePoint bpH = null;
			   
			   // checks if intersects with A, B, C, or D
			   // if so, calculates bounce point
			   if(A.intersectsLine(x1, y1, x1 + dx, y1 + dy)) {
				   double Ax1 = A.getX1();
				   double Ay1 = A.getY1();
				   double Ax2 = A.getX2();
				   double Ay2 = A.getY2();
				   
				   // if ball has vertical trajectory
				   // create a new bounce point easily
				   if(dx == 0) {
					   BouncePoint bpA = new BouncePoint(x1, Ay1, BounceDirection.FLIP_Y);
					   
					   if(bp == null) {
						   bp = bpA;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpA.getX(), bpA.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpA;
						   }
					   }

				   }
				   
				   // else, calculates the intersection point to create the bounce point
				   else {
					   // determines slope and y-intercept of ball's motion
					   double m = dy / dx;
					   double b = y1 - (m * x1);
					   
					   // calculates the intersection point
					   double intersectionX = (Ay1 - b) / m;
					   double intersectionY = (m * intersectionX) + b;
					   
					   BouncePoint bpA = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
					   if(bp == null) {
						   bp = bpA;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpA.getX(), bpA.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpA;
						   }
					   }
				   }
				   
			   }
			   if(B.intersectsLine(x1, y1, x1 + dx, y1 + dy)) {
				   double Bx1 = B.getX1();
				   double By1 = B.getY1();
				   double Bx2 = B.getX2();
				   double By2 = B.getY2();
				   
				   // if ball has horizontal trajectory
				   // create a new bounce point easily
				   if(dy == 0) {
					   BouncePoint bpB = new BouncePoint(Bx1, y1, BounceDirection.FLIP_X);
					   if(bp == null) {
						   bp = bpB;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpB.getX(), bpB.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpB;
						   }
					   }
				   }
				   
				   // else, calculates the intersection point to create the bounce point
				   else {
					   // determines slope and y-intercept of ball's motion
					   double m = dy / dx;
					   double b = y1 - (m * x1);
					   
					   // calculates the intersection point
					   double intersectionX = (By1 - b) / m;
					   double intersectionY = (m * intersectionX) + b;
					   
					   BouncePoint bpB = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
					   if(bp == null) {
						   bp = bpB;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpB.getX(), bpB.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpB;
						   }
					   }
				   }
			   }
			   if(C.intersectsLine(x1, y1, x1 + dx, y1 + dy)) {
				   double Cx1 = C.getX1();
				   double Cy1 = C.getY1();
				   double Cx2 = C.getX2();
				   double Cy2 = C.getY2();
				   
				   // if ball has horizontal trajectory
				   // create a new bounce point easily
				   if(dy == 0) {
					   BouncePoint bpC = new BouncePoint(Cx1, y1, BounceDirection.FLIP_X);
					   if(bp == null) {
						   bp = bpC;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpC.getX(), bpC.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpC;
						   }
					   }
				   }
				   
				   // else, calculates the intersection point to create the bounce point
				   else {
					   // determines slope and y-intercept of ball's motion
					   double m = dy / dx;
					   double b = y1 - (m * x1);
					   
					   // calculates the intersection point
					   double intersectionX = (Cy1 - b) / m;
					   double intersectionY = (m * intersectionX) + b;
					   
					   BouncePoint bpC = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
					   if(bp == null) {
						   bp = bpC;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpC.getX(), bpC.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpC;
						   }
					   }
				   }
			   }
			   if(D.intersectsLine(x1, y1, x1 + dx, y1 + dy)) {
				   double Dx1 = D.getX1();
				   double Dy1 = D.getY1();
				   double Dx2 = D.getX2();
				   double Dy2 = D.getY2();
				   
				   // if ball has vertical trajectory
				   // create a new bounce point easily
				   if(dx == 0) {
					   BouncePoint bpD = new BouncePoint(x1, Dy1, BounceDirection.FLIP_Y);
					   if(bp == null) {
						   bp = bpD;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpD.getX(), bpD.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpD;
						   }
					   }
				   }
				   
				   // else, calculates the intersection point to create the bounce point
				   else {
					   // determines slope and y-intercept of ball's motion
					   double m = dy / dx;
					   double b = y1 - (m * x1);
					   
					   // calculates the intersection point
					   double intersectionX = (Dy1 - b) / m;
					   double intersectionY = (m * intersectionX) + b;
					   
					   BouncePoint bpD = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
					   if(bp == null) {
						   bp = bpD;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpD.getX(), bpD.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpD;
						   }
					   }
				   }
			   }
			   
			   // checks if intersects with E, F, G, or H
			   		// if so, calculates if/where intersects with the curve
			   
			   // checks for E
			   if(E.contains(x1 + dx, y1 + dy)) {
			       // sets the coordinates of the center of the circle
				   double Ex = hitboxX + radius;
				   double Ey = hitboxY + radius;
			       
				   double EcenterToBallX = Ex - x1;
			       double EcenterToBallY = Ey - y1;
			       
			       double a = dx * dx + dy * dy;
			       double bBy2 = dx * EcenterToBallX + dy * EcenterToBallY;
			       double c = EcenterToBallX * EcenterToBallX + EcenterToBallY * EcenterToBallY - radius * radius;

			       double pBy2 = bBy2 / a;
			       double q = c / a;

			       double disc = pBy2 * pBy2 - q;
			      
			       double tmpSqrt = Math.sqrt(disc);
			       double abScalingFactor1 = -pBy2 + tmpSqrt;
			       double abScalingFactor2 = -pBy2 - tmpSqrt;
			       
			       // if disc > 0, two intersection points
			       if (disc > 0) {
				       // calculates intersection points
				       double intersection1X = x1 - dx * abScalingFactor1;
				       double intersection1Y = y1 - dy * abScalingFactor1;
				       
				       double intersection2X = x1 - dx * abScalingFactor2;
				       double intersection2Y = y1 - dy * abScalingFactor2;
				       
				       // determines which, if any, of the points is in the correct quadrant
				       boolean intersection1InQuadrant = (intersection1X >= Ex - radius && intersection1X <= Ex && 
				    		   								intersection1Y >= Ey - radius && intersection1Y <= Ey);
				       boolean intersection2InQuadrant = (intersection2X >= Ex - radius && intersection2X <= Ex && 
   															intersection2Y >= Ey - radius && intersection2Y <= Ey);
				       
				       if(intersection1InQuadrant && intersection2InQuadrant) {
				    	   // determines which one is closer
				    	   double ballToP1 = Point2D.Double.distance(x1, y1, intersection1X, intersection1Y);
				    	   double ballToP2 = Point2D.Double.distance(x1, y1, intersection2X, intersection2Y);
				    	   
				    	   if(ballToP1 < ballToP2) {
				    		   // creates bounce point
				    		   if(dx > 0) {
				    			   BouncePoint bpE = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpE;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpE;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpE = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpE;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpE;
									   }
								   }
					    	   }
				    	   }
				    	   else if(ballToP2 < ballToP1) {
					    	   // creates bounce point
					    	   if(dx > 0) {
					    		   BouncePoint bpE = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpE;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpE;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpE = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpE;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpE;
									   }
								   }
					    	   }
				    	   }
				       }
				       else if(intersection1InQuadrant) {
				    	   // creates bounce point
				    	   if(dx > 0) {
				    		   BouncePoint bpE = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpE;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpE;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpE = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpE;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpE;
								   }
							   }
				    	   }
				       }
				       else if(intersection2InQuadrant) {
				    	   // creates bounce point
				    	   if(dx > 0) {
				    		   BouncePoint bpE = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpE;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpE;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpE = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpE;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpE;
								   }
							   }
				    	   }
				       }
			       }
			       
			       // if disc == 0, one intersection
			       if(disc == 0) {
				       double intersectionX = x1 - dx * abScalingFactor1;
				       double intersectionY = y1 - dy * abScalingFactor1;
				       
				       boolean intersectionInQuadrant = (intersectionX >= Ex - radius && intersectionX <= Ex &&
				    		   								intersectionY >= Ey - radius && intersectionY <= Ey);
				       
				       if(intersectionInQuadrant) {
				    	   // creates bounce point
				    	   if(dx > 0) {
				    		   BouncePoint bpE = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpE;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpE;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpE = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpE;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpE;
								   }
							   }
				    	   }
				       }
			       }
			   }
			   
			   // checks for F
			   if(F.contains(x1 + dx, y1 + dy)) {
			       // sets the coordinates of the center of the circle
				   double Fx = hitboxX + hitboxSize - radius;
				   double Fy = hitboxY + radius;
			       
				   double FcenterToBallX = Fx - x1;
			       double FcenterToBallY = Fy - y1;
			       
			       double a = dx * dx + dy * dy;
			       double bBy2 = dx * FcenterToBallX + dy * FcenterToBallY;
			       double c = FcenterToBallX * FcenterToBallX + FcenterToBallY * FcenterToBallY - radius * radius;

			       double pBy2 = bBy2 / a;
			       double q = c / a;

			       double disc = pBy2 * pBy2 - q;
			      
			       double tmpSqrt = Math.sqrt(disc);
			       double abScalingFactor1 = -pBy2 + tmpSqrt;
			       double abScalingFactor2 = -pBy2 - tmpSqrt;
			       
			       // if disc > 0, two intersection points
			       if (disc > 0) {
				       // calculates intersection points
				       double intersection1X = x1 - dx * abScalingFactor1;
				       double intersection1Y = y1 - dy * abScalingFactor1;
				       
				       double intersection2X = x1 - dx * abScalingFactor2;
				       double intersection2Y = y1 - dy * abScalingFactor2;
				       
				       // determines which, if any, of the points is in the correct quadrant
				       boolean intersection1InQuadrant = (intersection1X >= Fx && intersection1X <= Fx + radius && 
				    		   								intersection1Y >= Fy - radius && intersection1Y <= Fy);
				       boolean intersection2InQuadrant = (intersection2X >= Fx && intersection2X <= Fx + radius && 
   															intersection2Y >= Fy - radius && intersection2Y <= Fy);
				       
				       if(intersection1InQuadrant && intersection2InQuadrant) {
				    	   // determines which one is closer
				    	   double ballToP1 = Point2D.Double.distance(x1, y1, intersection1X, intersection1Y);
				    	   double ballToP2 = Point2D.Double.distance(x1, y1, intersection2X, intersection2Y);
				    	   
				    	   if(ballToP1 < ballToP2) {
				    		   // creates bounce point
				    		   if(dx >= 0) {
				    			   BouncePoint bpF = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpF;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpF;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpF = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpF;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpF;
									   }
								   }
					    	   }
				    	   }
				    	   else if(ballToP2 < ballToP1) {
					    	   // creates bounce point
					    	   if(dx >= 0) {
					    		   BouncePoint bpF = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpF;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpF;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpF = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpF;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpF;
									   }
								   }
					    	   }
				    	   }
				       }
				       else if(intersection1InQuadrant) {
				    	   // creates bounce point
				    	   if(dx >= 0) {
				    		   BouncePoint bpF = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpF;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpF;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpF = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpF;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpF;
								   }
							   }
				    	   }
				       }
				       else if(intersection2InQuadrant) {
				    	   // creates bounce point
				    	   if(dx >= 0) {
				    		   BouncePoint bpF = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpF;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpF;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpF = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpF;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpF;
								   }
							   }
				    	   }
				       }
			       }
			       
			       // if disc == 0, one intersection
			       if(disc == 0) {
				       double intersectionX = x1 - dx * abScalingFactor1;
				       double intersectionY = y1 - dy * abScalingFactor1;
				       
				       boolean intersectionInQuadrant = (intersectionX >= Fx && intersectionX <= Fx + radius &&
				    		   								intersectionY >= Fy - radius && intersectionY <= Fy);
				       
				       if(intersectionInQuadrant) {
				    	   // creates bounce point
				    	   if(dx >= 0) {
				    		   BouncePoint bpF = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpF;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpF;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpF = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpF;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpF;
								   }
							   }
				    	   }
				       }
			       }
			   }
			   
			   // checks for G
			   if(G.contains(x1 + dx, y1 + dy)) {
			       // sets the coordinates of the center of the circle
				   double Gx = hitboxX + radius;
				   double Gy = hitboxY + hitboxSize - radius;
			       
				   double GcenterToBallX = Gx - x1;
			       double GcenterToBallY = Gy - y1;
			       
			       double a = dx * dx + dy * dy;
			       double bBy2 = dx * GcenterToBallX + dy * GcenterToBallY;
			       double c = GcenterToBallX * GcenterToBallX + GcenterToBallY * GcenterToBallY - radius * radius;

			       double pBy2 = bBy2 / a;
			       double q = c / a;

			       double disc = pBy2 * pBy2 - q;
			      
			       double tmpSqrt = Math.sqrt(disc);
			       double abScalingFactor1 = -pBy2 + tmpSqrt;
			       double abScalingFactor2 = -pBy2 - tmpSqrt;
			       
			       // if disc > 0, two intersection points
			       if (disc > 0) {
				       // calculates intersection points
				       double intersection1X = x1 - dx * abScalingFactor1;
				       double intersection1Y = y1 - dy * abScalingFactor1;
				       
				       double intersection2X = x1 - dx * abScalingFactor2;
				       double intersection2Y = y1 - dy * abScalingFactor2;
				       
				       // determines which, if any, of the points is in the correct quadrant
				       boolean intersection1InQuadrant = (intersection1X >= Gx - radius && intersection1X <= Gx && 
				    		   								intersection1Y >= Gy && intersection1Y <= Gy + radius);
				       boolean intersection2InQuadrant = (intersection2X >= Gx - radius && intersection2X <= Gx && 
   															intersection2Y >= Gy && intersection2Y <= Gy + radius);
				       
				       if(intersection1InQuadrant && intersection2InQuadrant) {
				    	   // determines which one is closer
				    	   double ballToP1 = Point2D.Double.distance(x1, y1, intersection1X, intersection1Y);
				    	   double ballToP2 = Point2D.Double.distance(x1, y1, intersection2X, intersection2Y);
				    	   
				    	   if(ballToP1 < ballToP2) {
				    		   // creates bounce point
				    		   if(dx > 0) {
				    			   BouncePoint bpG = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpG;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpG;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpG = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpG;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpG;
									   }
								   }
					    	   }
				    	   }
				    	   else if(ballToP2 < ballToP1) {
					    	   // creates bounce point
					    	   if(dx > 0) {
					    		   BouncePoint bpG = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpG;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpG;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpG = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpG;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpG;
									   }
								   }
					    	   }
				    	   }
				       }
				       else if(intersection1InQuadrant) {
				    	   // creates bounce point
				    	   if(dx > 0) {
				    		   BouncePoint bpG = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpG;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpG;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpG = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpG;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpG;
								   }
							   }
				    	   }
				       }
				       else if(intersection2InQuadrant) {
				    	   // creates bounce point
				    	   if(dx > 0) {
				    		   BouncePoint bpG = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpG;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpG;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpG = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpG;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpG;
								   }
							   }
				    	   }
				       }
			       }
			       
			       // if disc == 0, one intersection
			       if(disc == 0) {
				       double intersectionX = x1 - dx * abScalingFactor1;
				       double intersectionY = y1 - dy * abScalingFactor1;
				       
				       boolean intersectionInQuadrant = (intersectionX >= Gx - radius && intersectionX <= Gx &&
				    		   								intersectionY >= Gy && intersectionY <= Gy + radius);
				       
				       if(intersectionInQuadrant) {
				    	   // creates bounce point
				    	   if(dx > 0) {
				    		   BouncePoint bpG = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpG;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpG;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpG = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpG;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpG;
								   }
							   }
				    	   }
				       }
			       }
			   }
			   
			   // checks for H
			   if(H.contains(x1 + dx, y1 + dy)) {
			       // sets the coordinates of the center of the circle
				   double Hx = hitboxX + hitboxSize - radius;
				   double Hy = hitboxY + hitboxSize - radius;
			       
				   double HcenterToBallX = Hx - x1;
			       double HcenterToBallY = Hy - y1;
			       
			       double a = dx * dx + dy * dy;
			       double bBy2 = dx * HcenterToBallX + dy * HcenterToBallY;
			       double c = HcenterToBallX * HcenterToBallX + HcenterToBallY * HcenterToBallY - radius * radius;

			       double pBy2 = bBy2 / a;
			       double q = c / a;

			       double disc = pBy2 * pBy2 - q;
			      
			       double tmpSqrt = Math.sqrt(disc);
			       double abScalingFactor1 = -pBy2 + tmpSqrt;
			       double abScalingFactor2 = -pBy2 - tmpSqrt;
			       
			       // if disc > 0, two intersection points
			       if (disc > 0) {
				       // calculates intersection points
				       double intersection1X = x1 - dx * abScalingFactor1;
				       double intersection1Y = y1 - dy * abScalingFactor1;
				       
				       double intersection2X = x1 - dx * abScalingFactor2;
				       double intersection2Y = y1 - dy * abScalingFactor2;
				       
				       // determines which, if any, of the points is in the correct quadrant
				       boolean intersection1InQuadrant = (intersection1X >= Hx && intersection1X <= Hx + radius && 
				    		   								intersection1Y >= Hy && intersection1Y <= Hy + radius);
				       boolean intersection2InQuadrant = (intersection2X >= Hx && intersection2X <= Hx + radius && 
   															intersection2Y >= Hy && intersection2Y <= Hy + radius);
				       
				       if(intersection1InQuadrant && intersection2InQuadrant) {
				    	   // determines which one is closer
				    	   double ballToP1 = Point2D.Double.distance(x1, y1, intersection1X, intersection1Y);
				    	   double ballToP2 = Point2D.Double.distance(x1, y1, intersection2X, intersection2Y);
				    	   
				    	   if(ballToP1 < ballToP2) {
				    		   // creates bounce point
				    		   if(dx >= 0) {
				    			   BouncePoint bpH = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpH;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpH;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpH = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpH;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpH;
									   }
								   }
					    	   }
				    	   }
				    	   else if(ballToP2 < ballToP1) {
					    	   // creates bounce point
					    	   if(dx >= 0) {
					    		   BouncePoint bpH = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpH;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpH;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpH = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpH;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpH;
									   }
								   }
					    	   }
				    	   }
				       }
				       else if(intersection1InQuadrant) {
				    	   // creates bounce point
				    	   if(dx >= 0) {
				    		   BouncePoint bpH = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpH;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpH;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpH = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpH;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpH;
								   }
							   }
				    	   }
				       }
				       else if(intersection2InQuadrant) {
				    	   // creates bounce point
				    	   if(dx >= 0) {
				    		   BouncePoint bpH = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpH;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpH;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpH = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpH;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpH;
								   }
							   }
				    	   }
				       }
			       }
			       
			       // if disc == 0, one intersection
			       if(disc == 0) {
				       double intersectionX = x1 - dx * abScalingFactor1;
				       double intersectionY = y1 - dy * abScalingFactor1;
				       
				       boolean intersectionInQuadrant = (intersectionX >= Hx && intersectionX <= Hx + radius &&
				    		   								intersectionY >= Hy && intersectionY <= Hy + radius);
				       
				       if(intersectionInQuadrant) {
				    	   // creates bounce point
				    	   if(dx >= 0) {
				    		   BouncePoint bpH = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpH;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpH;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpH = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpH;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpH;
								   }
							   }
				    	   }
				       }
			       }
			   }
			   setBounce(bp);
		   }
		   
		   if(getBounce() != null) {
			   return true;
		   }
		   else return false;
		   // set bounce point
		   // return true if bounce point != null
	   }
    return false;
  }

  // line 57 "../../../../../Block223States.ump"
   private boolean hitBlock(){
    // TODO implement
	   
	   int nrBlocks = numberOfBlocks();
	   
	   setBounce(null);
	   
	   for(int i = 0; i < nrBlocks; i++) {
		   PlayedBlockAssignment block = getBlock(i);
		   // calculate bounce point (will be null if no bounce point)
		   
		   // gets ball position and velocity and slope
		   double x1 = getCurrentBallX();
		   double y1 = getCurrentBallY();
		   double dx = getBallDirectionX();
		   double dy = getBallDirectionY();
		   
		   // determines size and coordinates of rectangle
		   // adjusts from block.getX() because position of block is from top right corner
		   // but position of rectangle2D is from top left
		   double hitboxSize = (double) (Block.SIZE + Ball.BALL_DIAMETER);
		   double hitboxX = block.getX() - hitboxSize;
		   double hitboxY = block.getY();
		   
		   // creates the block's hitbox
		   Rectangle2D.Double hitbox = new Rectangle2D.Double(hitboxX, hitboxY, hitboxSize, hitboxSize);
		   
		   // detects if ball will hit hitbox
		   boolean intersects = hitbox.intersectsLine(x1, y1, x1 + dx, y1 + dy);
		   
		   // if ball will hit hitbox, calculates bounce point
		   if(intersects) {
			   double radius = ((double) Ball.BALL_DIAMETER) / 2;
			   
			   // defines regions A through H
			   Line2D.Double A = new Line2D.Double(hitboxX + radius, hitboxX + hitboxSize - radius, hitboxY, hitboxY);
			   Line2D.Double B = new Line2D.Double(hitboxX, hitboxX, hitboxY + radius, hitboxY + hitboxSize - radius);
			   Line2D.Double C = new Line2D.Double(hitboxX + hitboxSize, hitboxX + hitboxSize, hitboxY + radius, hitboxY + hitboxSize - radius);
			   Line2D.Double D = new Line2D.Double(hitboxX + radius, hitboxX + hitboxSize - radius, hitboxY + hitboxSize, hitboxY + hitboxSize);
			   Ellipse2D.Double E = new Ellipse2D.Double(hitboxX, hitboxY, radius * 2, radius * 2);
			   Ellipse2D.Double F = new Ellipse2D.Double(hitboxX + hitboxSize - radius, hitboxY, radius * 2, radius * 2);
			   Ellipse2D.Double G = new Ellipse2D.Double(hitboxX, hitboxY + hitboxSize - radius, radius * 2, radius * 2);
			   Ellipse2D.Double H = new Ellipse2D.Double(hitboxX + hitboxSize - radius, hitboxY + hitboxSize - radius, radius * 2, radius * 2);
			   
			   // initializes all the possible bounce points
			   BouncePoint bp = null;
			   //BouncePoint bpB = null;
			   //BouncePoint bpC = null;
			   //BouncePoint bpD = null;
			   //BouncePoint bpE = null;
			   //BouncePoint bpF = null;
			   //BouncePoint bpG = null;
			   //BouncePoint bpH = null;
			   
			   // checks if intersects with A, B, C, or D
			   // if so, calculates bounce point
			   if(A.intersectsLine(x1, y1, x1 + dx, y1 + dy)) {
				   double Ax1 = A.getX1();
				   double Ay1 = A.getY1();
				   double Ax2 = A.getX2();
				   double Ay2 = A.getY2();
				   
				   // if ball has vertical trajectory
				   // create a new bounce point easily
				   if(dx == 0) {
					   BouncePoint bpA = new BouncePoint(x1, Ay1, BounceDirection.FLIP_Y);
					   
					   if(bp == null) {
						   bp = bpA;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpA.getX(), bpA.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpA;
						   }
					   }

				   }
				   
				   // else, calculates the intersection point to create the bounce point
				   else {
					   // determines slope and y-intercept of ball's motion
					   double m = dy / dx;
					   double b = y1 - (m * x1);
					   
					   // calculates the intersection point
					   double intersectionX = (Ay1 - b) / m;
					   double intersectionY = (m * intersectionX) + b;
					   
					   BouncePoint bpA = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
					   if(bp == null) {
						   bp = bpA;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpA.getX(), bpA.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpA;
						   }
					   }
				   }
				   
			   }
			   if(B.intersectsLine(x1, y1, x1 + dx, y1 + dy)) {
				   double Bx1 = B.getX1();
				   double By1 = B.getY1();
				   double Bx2 = B.getX2();
				   double By2 = B.getY2();
				   
				   // if ball has horizontal trajectory
				   // create a new bounce point easily
				   if(dy == 0) {
					   BouncePoint bpB = new BouncePoint(Bx1, y1, BounceDirection.FLIP_X);
					   if(bp == null) {
						   bp = bpB;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpB.getX(), bpB.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpB;
						   }
					   }
				   }
				   
				   // else, calculates the intersection point to create the bounce point
				   else {
					   // determines slope and y-intercept of ball's motion
					   double m = dy / dx;
					   double b = y1 - (m * x1);
					   
					   // calculates the intersection point
					   double intersectionX = (By1 - b) / m;
					   double intersectionY = (m * intersectionX) + b;
					   
					   BouncePoint bpB = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
					   if(bp == null) {
						   bp = bpB;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpB.getX(), bpB.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpB;
						   }
					   }
				   }
			   }
			   if(C.intersectsLine(x1, y1, x1 + dx, y1 + dy)) {
				   double Cx1 = C.getX1();
				   double Cy1 = C.getY1();
				   double Cx2 = C.getX2();
				   double Cy2 = C.getY2();
				   
				   // if ball has horizontal trajectory
				   // create a new bounce point easily
				   if(dy == 0) {
					   BouncePoint bpC = new BouncePoint(Cx1, y1, BounceDirection.FLIP_X);
					   if(bp == null) {
						   bp = bpC;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpC.getX(), bpC.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpC;
						   }
					   }
				   }
				   
				   // else, calculates the intersection point to create the bounce point
				   else {
					   // determines slope and y-intercept of ball's motion
					   double m = dy / dx;
					   double b = y1 - (m * x1);
					   
					   // calculates the intersection point
					   double intersectionX = (Cy1 - b) / m;
					   double intersectionY = (m * intersectionX) + b;
					   
					   BouncePoint bpC = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
					   if(bp == null) {
						   bp = bpC;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpC.getX(), bpC.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpC;
						   }
					   }
				   }
			   }
			   if(D.intersectsLine(x1, y1, x1 + dx, y1 + dy)) {
				   double Dx1 = D.getX1();
				   double Dy1 = D.getY1();
				   double Dx2 = D.getX2();
				   double Dy2 = D.getY2();
				   
				   // if ball has vertical trajectory
				   // create a new bounce point easily
				   if(dx == 0) {
					   BouncePoint bpD = new BouncePoint(x1, Dy1, BounceDirection.FLIP_Y);
					   if(bp == null) {
						   bp = bpD;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpD.getX(), bpD.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpD;
						   }
					   }
				   }
				   
				   // else, calculates the intersection point to create the bounce point
				   else {
					   // determines slope and y-intercept of ball's motion
					   double m = dy / dx;
					   double b = y1 - (m * x1);
					   
					   // calculates the intersection point
					   double intersectionX = (Dy1 - b) / m;
					   double intersectionY = (m * intersectionX) + b;
					   
					   BouncePoint bpD = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
					   if(bp == null) {
						   bp = bpD;
					   }
					   else {
						   if(Point2D.Double.distance(x1, y1, bpD.getX(), bpD.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
							   bp = bpD;
						   }
					   }
				   }
			   }
			   
			   // checks if intersects with E, F, G, or H
			   		// if so, calculates if/where intersects with the curve
			   
			   // checks for E
			   if(E.contains(x1 + dx, y1 + dy)) {
			       // sets the coordinates of the center of the circle
				   double Ex = hitboxX + radius;
				   double Ey = hitboxY + radius;
			       
				   double EcenterToBallX = Ex - x1;
			       double EcenterToBallY = Ey - y1;
			       
			       double a = dx * dx + dy * dy;
			       double bBy2 = dx * EcenterToBallX + dy * EcenterToBallY;
			       double c = EcenterToBallX * EcenterToBallX + EcenterToBallY * EcenterToBallY - radius * radius;

			       double pBy2 = bBy2 / a;
			       double q = c / a;

			       double disc = pBy2 * pBy2 - q;
			      
			       double tmpSqrt = Math.sqrt(disc);
			       double abScalingFactor1 = -pBy2 + tmpSqrt;
			       double abScalingFactor2 = -pBy2 - tmpSqrt;
			       
			       // if disc > 0, two intersection points
			       if (disc > 0) {
				       // calculates intersection points
				       double intersection1X = x1 - dx * abScalingFactor1;
				       double intersection1Y = y1 - dy * abScalingFactor1;
				       
				       double intersection2X = x1 - dx * abScalingFactor2;
				       double intersection2Y = y1 - dy * abScalingFactor2;
				       
				       // determines which, if any, of the points is in the correct quadrant
				       boolean intersection1InQuadrant = (intersection1X >= Ex - radius && intersection1X <= Ex && 
				    		   								intersection1Y >= Ey - radius && intersection1Y <= Ey);
				       boolean intersection2InQuadrant = (intersection2X >= Ex - radius && intersection2X <= Ex && 
   															intersection2Y >= Ey - radius && intersection2Y <= Ey);
				       
				       if(intersection1InQuadrant && intersection2InQuadrant) {
				    	   // determines which one is closer
				    	   double ballToP1 = Point2D.Double.distance(x1, y1, intersection1X, intersection1Y);
				    	   double ballToP2 = Point2D.Double.distance(x1, y1, intersection2X, intersection2Y);
				    	   
				    	   if(ballToP1 < ballToP2) {
				    		   // creates bounce point
				    		   if(dx > 0) {
				    			   BouncePoint bpE = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpE;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpE;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpE = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpE;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpE;
									   }
								   }
					    	   }
				    	   }
				    	   else if(ballToP2 < ballToP1) {
					    	   // creates bounce point
					    	   if(dx > 0) {
					    		   BouncePoint bpE = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpE;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpE;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpE = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpE;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpE;
									   }
								   }
					    	   }
				    	   }
				       }
				       else if(intersection1InQuadrant) {
				    	   // creates bounce point
				    	   if(dx > 0) {
				    		   BouncePoint bpE = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpE;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpE;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpE = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpE;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpE;
								   }
							   }
				    	   }
				       }
				       else if(intersection2InQuadrant) {
				    	   // creates bounce point
				    	   if(dx > 0) {
				    		   BouncePoint bpE = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpE;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpE;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpE = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpE;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpE;
								   }
							   }
				    	   }
				       }
			       }
			       
			       // if disc == 0, one intersection
			       if(disc == 0) {
				       double intersectionX = x1 - dx * abScalingFactor1;
				       double intersectionY = y1 - dy * abScalingFactor1;
				       
				       boolean intersectionInQuadrant = (intersectionX >= Ex - radius && intersectionX <= Ex &&
				    		   								intersectionY >= Ey - radius && intersectionY <= Ey);
				       
				       if(intersectionInQuadrant) {
				    	   // creates bounce point
				    	   if(dx > 0) {
				    		   BouncePoint bpE = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpE;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpE;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpE = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpE;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpE.getX(), bpE.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpE;
								   }
							   }
				    	   }
				       }
			       }
			   }
			   
			   // checks for F
			   if(F.contains(x1 + dx, y1 + dy)) {
			       // sets the coordinates of the center of the circle
				   double Fx = hitboxX + hitboxSize - radius;
				   double Fy = hitboxY + radius;
			       
				   double FcenterToBallX = Fx - x1;
			       double FcenterToBallY = Fy - y1;
			       
			       double a = dx * dx + dy * dy;
			       double bBy2 = dx * FcenterToBallX + dy * FcenterToBallY;
			       double c = FcenterToBallX * FcenterToBallX + FcenterToBallY * FcenterToBallY - radius * radius;

			       double pBy2 = bBy2 / a;
			       double q = c / a;

			       double disc = pBy2 * pBy2 - q;
			      
			       double tmpSqrt = Math.sqrt(disc);
			       double abScalingFactor1 = -pBy2 + tmpSqrt;
			       double abScalingFactor2 = -pBy2 - tmpSqrt;
			       
			       // if disc > 0, two intersection points
			       if (disc > 0) {
				       // calculates intersection points
				       double intersection1X = x1 - dx * abScalingFactor1;
				       double intersection1Y = y1 - dy * abScalingFactor1;
				       
				       double intersection2X = x1 - dx * abScalingFactor2;
				       double intersection2Y = y1 - dy * abScalingFactor2;
				       
				       // determines which, if any, of the points is in the correct quadrant
				       boolean intersection1InQuadrant = (intersection1X >= Fx && intersection1X <= Fx + radius && 
				    		   								intersection1Y >= Fy - radius && intersection1Y <= Fy);
				       boolean intersection2InQuadrant = (intersection2X >= Fx && intersection2X <= Fx + radius && 
   															intersection2Y >= Fy - radius && intersection2Y <= Fy);
				       
				       if(intersection1InQuadrant && intersection2InQuadrant) {
				    	   // determines which one is closer
				    	   double ballToP1 = Point2D.Double.distance(x1, y1, intersection1X, intersection1Y);
				    	   double ballToP2 = Point2D.Double.distance(x1, y1, intersection2X, intersection2Y);
				    	   
				    	   if(ballToP1 < ballToP2) {
				    		   // creates bounce point
				    		   if(dx >= 0) {
				    			   BouncePoint bpF = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpF;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpF;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpF = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpF;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpF;
									   }
								   }
					    	   }
				    	   }
				    	   else if(ballToP2 < ballToP1) {
					    	   // creates bounce point
					    	   if(dx >= 0) {
					    		   BouncePoint bpF = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpF;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpF;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpF = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpF;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpF;
									   }
								   }
					    	   }
				    	   }
				       }
				       else if(intersection1InQuadrant) {
				    	   // creates bounce point
				    	   if(dx >= 0) {
				    		   BouncePoint bpF = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpF;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpF;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpF = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpF;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpF;
								   }
							   }
				    	   }
				       }
				       else if(intersection2InQuadrant) {
				    	   // creates bounce point
				    	   if(dx >= 0) {
				    		   BouncePoint bpF = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpF;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpF;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpF = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpF;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpF;
								   }
							   }
				    	   }
				       }
			       }
			       
			       // if disc == 0, one intersection
			       if(disc == 0) {
				       double intersectionX = x1 - dx * abScalingFactor1;
				       double intersectionY = y1 - dy * abScalingFactor1;
				       
				       boolean intersectionInQuadrant = (intersectionX >= Fx && intersectionX <= Fx + radius &&
				    		   								intersectionY >= Fy - radius && intersectionY <= Fy);
				       
				       if(intersectionInQuadrant) {
				    	   // creates bounce point
				    	   if(dx >= 0) {
				    		   BouncePoint bpF = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpF;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpF;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpF = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpF;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpF.getX(), bpF.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpF;
								   }
							   }
				    	   }
				       }
			       }
			   }
			   
			   // checks for G
			   if(G.contains(x1 + dx, y1 + dy)) {
			       // sets the coordinates of the center of the circle
				   double Gx = hitboxX + radius;
				   double Gy = hitboxY + hitboxSize - radius;
			       
				   double GcenterToBallX = Gx - x1;
			       double GcenterToBallY = Gy - y1;
			       
			       double a = dx * dx + dy * dy;
			       double bBy2 = dx * GcenterToBallX + dy * GcenterToBallY;
			       double c = GcenterToBallX * GcenterToBallX + GcenterToBallY * GcenterToBallY - radius * radius;

			       double pBy2 = bBy2 / a;
			       double q = c / a;

			       double disc = pBy2 * pBy2 - q;
			      
			       double tmpSqrt = Math.sqrt(disc);
			       double abScalingFactor1 = -pBy2 + tmpSqrt;
			       double abScalingFactor2 = -pBy2 - tmpSqrt;
			       
			       // if disc > 0, two intersection points
			       if (disc > 0) {
				       // calculates intersection points
				       double intersection1X = x1 - dx * abScalingFactor1;
				       double intersection1Y = y1 - dy * abScalingFactor1;
				       
				       double intersection2X = x1 - dx * abScalingFactor2;
				       double intersection2Y = y1 - dy * abScalingFactor2;
				       
				       // determines which, if any, of the points is in the correct quadrant
				       boolean intersection1InQuadrant = (intersection1X >= Gx - radius && intersection1X <= Gx && 
				    		   								intersection1Y >= Gy && intersection1Y <= Gy + radius);
				       boolean intersection2InQuadrant = (intersection2X >= Gx - radius && intersection2X <= Gx && 
   															intersection2Y >= Gy && intersection2Y <= Gy + radius);
				       
				       if(intersection1InQuadrant && intersection2InQuadrant) {
				    	   // determines which one is closer
				    	   double ballToP1 = Point2D.Double.distance(x1, y1, intersection1X, intersection1Y);
				    	   double ballToP2 = Point2D.Double.distance(x1, y1, intersection2X, intersection2Y);
				    	   
				    	   if(ballToP1 < ballToP2) {
				    		   // creates bounce point
				    		   if(dx > 0) {
				    			   BouncePoint bpG = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpG;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpG;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpG = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpG;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpG;
									   }
								   }
					    	   }
				    	   }
				    	   else if(ballToP2 < ballToP1) {
					    	   // creates bounce point
					    	   if(dx > 0) {
					    		   BouncePoint bpG = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpG;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpG;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpG = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpG;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpG;
									   }
								   }
					    	   }
				    	   }
				       }
				       else if(intersection1InQuadrant) {
				    	   // creates bounce point
				    	   if(dx > 0) {
				    		   BouncePoint bpG = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpG;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpG;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpG = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpG;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpG;
								   }
							   }
				    	   }
				       }
				       else if(intersection2InQuadrant) {
				    	   // creates bounce point
				    	   if(dx > 0) {
				    		   BouncePoint bpG = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpG;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpG;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpG = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpG;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpG;
								   }
							   }
				    	   }
				       }
			       }
			       
			       // if disc == 0, one intersection
			       if(disc == 0) {
				       double intersectionX = x1 - dx * abScalingFactor1;
				       double intersectionY = y1 - dy * abScalingFactor1;
				       
				       boolean intersectionInQuadrant = (intersectionX >= Gx - radius && intersectionX <= Gx &&
				    		   								intersectionY >= Gy && intersectionY <= Gy + radius);
				       
				       if(intersectionInQuadrant) {
				    	   // creates bounce point
				    	   if(dx > 0) {
				    		   BouncePoint bpG = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpG;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpG;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpG = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpG;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpG.getX(), bpG.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpG;
								   }
							   }
				    	   }
				       }
			       }
			   }
			   
			   // checks for H
			   if(H.contains(x1 + dx, y1 + dy)) {
			       // sets the coordinates of the center of the circle
				   double Hx = hitboxX + hitboxSize - radius;
				   double Hy = hitboxY + hitboxSize - radius;
			       
				   double HcenterToBallX = Hx - x1;
			       double HcenterToBallY = Hy - y1;
			       
			       double a = dx * dx + dy * dy;
			       double bBy2 = dx * HcenterToBallX + dy * HcenterToBallY;
			       double c = HcenterToBallX * HcenterToBallX + HcenterToBallY * HcenterToBallY - radius * radius;

			       double pBy2 = bBy2 / a;
			       double q = c / a;

			       double disc = pBy2 * pBy2 - q;
			      
			       double tmpSqrt = Math.sqrt(disc);
			       double abScalingFactor1 = -pBy2 + tmpSqrt;
			       double abScalingFactor2 = -pBy2 - tmpSqrt;
			       
			       // if disc > 0, two intersection points
			       if (disc > 0) {
				       // calculates intersection points
				       double intersection1X = x1 - dx * abScalingFactor1;
				       double intersection1Y = y1 - dy * abScalingFactor1;
				       
				       double intersection2X = x1 - dx * abScalingFactor2;
				       double intersection2Y = y1 - dy * abScalingFactor2;
				       
				       // determines which, if any, of the points is in the correct quadrant
				       boolean intersection1InQuadrant = (intersection1X >= Hx && intersection1X <= Hx + radius && 
				    		   								intersection1Y >= Hy && intersection1Y <= Hy + radius);
				       boolean intersection2InQuadrant = (intersection2X >= Hx && intersection2X <= Hx + radius && 
   															intersection2Y >= Hy && intersection2Y <= Hy + radius);
				       
				       if(intersection1InQuadrant && intersection2InQuadrant) {
				    	   // determines which one is closer
				    	   double ballToP1 = Point2D.Double.distance(x1, y1, intersection1X, intersection1Y);
				    	   double ballToP2 = Point2D.Double.distance(x1, y1, intersection2X, intersection2Y);
				    	   
				    	   if(ballToP1 < ballToP2) {
				    		   // creates bounce point
				    		   if(dx >= 0) {
				    			   BouncePoint bpH = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpH;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpH;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpH = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpH;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpH;
									   }
								   }
					    	   }
				    	   }
				    	   else if(ballToP2 < ballToP1) {
					    	   // creates bounce point
					    	   if(dx >= 0) {
					    		   BouncePoint bpH = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
								   if(bp == null) {
									   bp = bpH;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpH;
									   }
								   }
					    	   }
					    	   else {
					    		   BouncePoint bpH = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
								   if(bp == null) {
									   bp = bpH;
								   }
								   else {
									   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
										   bp = bpH;
									   }
								   }
					    	   }
				    	   }
				       }
				       else if(intersection1InQuadrant) {
				    	   // creates bounce point
				    	   if(dx >= 0) {
				    		   BouncePoint bpH = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpH;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpH;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpH = new BouncePoint(intersection1X, intersection1Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpH;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpH;
								   }
							   }
				    	   }
				       }
				       else if(intersection2InQuadrant) {
				    	   // creates bounce point
				    	   if(dx >= 0) {
				    		   BouncePoint bpH = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpH;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpH;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpH = new BouncePoint(intersection2X, intersection2Y, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpH;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpH;
								   }
							   }
				    	   }
				       }
			       }
			       
			       // if disc == 0, one intersection
			       if(disc == 0) {
				       double intersectionX = x1 - dx * abScalingFactor1;
				       double intersectionY = y1 - dy * abScalingFactor1;
				       
				       boolean intersectionInQuadrant = (intersectionX >= Hx && intersectionX <= Hx + radius &&
				    		   								intersectionY >= Hy && intersectionY <= Hy + radius);
				       
				       if(intersectionInQuadrant) {
				    	   // creates bounce point
				    	   if(dx >= 0) {
				    		   BouncePoint bpH = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_Y);
							   if(bp == null) {
								   bp = bpH;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpH;
								   }
							   }
				    	   }
				    	   else {
				    		   BouncePoint bpH = new BouncePoint(intersectionX, intersectionY, BounceDirection.FLIP_X);
							   if(bp == null) {
								   bp = bpH;
							   }
							   else {
								   if(Point2D.Double.distance(x1, y1, bpH.getX(), bpH.getY()) < Point2D.Double.distance(x1, y1, bp.getX(), bp.getY())) {
									   bp = bpH;
								   }
							   }
				    	   }
				       }
			       }
			   }
			   
			   if(bp != null && getBounce() != null) {
				   if(Point2D.Double.distance(x1, y1, bp.getX(), bp.getY()) < Point2D.Double.distance(x1, y1, getBounce().getX(), getBounce().getY())) {
					 setBounce(bp);  
				   }
			   }
			   else if(getBounce() == null) {
				   setBounce(bp);
			   }
		   }
		   
		   
		   // compare new calculated bounce point to those from previous loops
		   // set the closest one to be the bounce point
	   }
	   
	   if(getBounce() != null) {
		   return true;
	   }
	   
	   // return true if bounce point != null
	  return false;
  }

  // line 62 "../../../../../Block223States.ump"
   private boolean hitWall(){
    // TODO implement
	   //create 3 rectangle boundaries for wall
	   //The Rectangle2D class describes a rectangle defined by a location (x,y) and width and height).
	   	Rectangle2D A = new Rectangle2D.Double(0, 0, (Ball.BALL_DIAMETER)/2, Game.PLAY_AREA_SIDE - (Ball.BALL_DIAMETER/2));
		Rectangle2D B = new Rectangle2D.Double(0, 0, Game.PLAY_AREA_SIDE, Ball.BALL_DIAMETER/2);
		Rectangle2D C = new Rectangle2D.Double(Game.PLAY_AREA_SIDE-(Ball.BALL_DIAMETER/2),0, Ball.BALL_DIAMETER ,Game.PLAY_AREA_SIDE);
		
		
		ArrayList<Rectangle2D> boundary = new ArrayList<Rectangle2D>();
		boundary.add(A);
		boundary.add(B);
		boundary.add(C);
		
		
		double dx = getBallDirectionX();
		double dy = getBallDirectionY();
		double  x1 = getCurrentBallX();
		double	y1 = getCurrentBallY();
		double x2 = x1+dx;
		double y2 = y1+dy;
		
		int whichboundary = -1;
		Boolean ballintersects = false;
		for (int i=0; i< boundary.size(); i++){
		 if(boundary.get(i).intersectsLine(x1,y1, x2, y2)==true){
			ballintersects = true;
			whichboundary = i;
		//TODO how to determine when more than one intersection is possible	
		}
		
		 double[] s = getgradientandconstant(x1,y1,x2,y2);
		 double intersectionx, intersectiony;
			 if(whichboundary == 0){//todo change arraylist to hashmap
			 	//then its A
						
			 	intersectionx = 5; 
			 	intersectiony= s[0]*intersectionx + s[1];
			 	if(intersectionx == 5 && intersectiony ==5 ) {
			 		BouncePoint bp = new BouncePoint(intersectionx,intersectiony,BounceDirection.FLIP_BOTH);
				 	setBounce(bp);
			 	}
			 	if(intersectiony > 5 && intersectiony < Game.PLAY_AREA_SIDE - (Ball.BALL_DIAMETER/2))
			 	{
			 	BouncePoint bp = new BouncePoint(intersectionx,intersectiony,BounceDirection.FLIP_X);
			 	setBounce(bp);
			 	}
			 }
			 if(whichboundary == 1){ //then its B

			 	intersectionx =((Ball.BALL_DIAMETER/2)-s[1])/(s[0]);
			 	intersectiony= Ball.BALL_DIAMETER/2;
			 	
				if(intersectionx == 5 && intersectiony ==5 || intersectionx == 385 && intersectiony == 5) {
			 		BouncePoint bp = new BouncePoint(intersectionx,intersectiony,BounceDirection.FLIP_BOTH);
				 	setBounce(bp);
			 	}
				
			 	if(intersectionx > 5 && intersectionx < Game.PLAY_AREA_SIDE - (Ball.BALL_DIAMETER/2))
			 	{
			 	BouncePoint bp = new BouncePoint(intersectionx,intersectiony,BounceDirection.FLIP_Y);
			 	setBounce(bp);
			 	}
			 }
			 if(whichboundary == 2){ //then its C
			 	
			 	intersectionx = Game.PLAY_AREA_SIDE -(Ball.BALL_DIAMETER/2);
			 	intersectiony= s[0]*intersectionx + s[1];
			 	
				if(intersectionx == 385 && intersectiony == 5 ) {
			 		BouncePoint bp = new BouncePoint(intersectionx,intersectiony,BounceDirection.FLIP_BOTH);
				 	setBounce(bp);
			 	}
			 	if(intersectiony > 5 && intersectiony < Game.PLAY_AREA_SIDE - (Ball.BALL_DIAMETER/2))
			 	{
			 	BouncePoint bp = new BouncePoint(intersectionx,intersectiony,BounceDirection.FLIP_X);
			 	setBounce(bp);
			 	}
			 		
			 }
			 		
		}
    return ballintersects;
  }


  /**
   * Actions
   */
  // line 69 "../../../../../Block223States.ump"
   private void doSetup(){
	   resetGame();
	   resetCurrentBallX(); resetCurrentBallY(); resetBallDirectionX(); resetBallDirectionY(); resetCurrentPaddleX(); getGame();
	   Level assignment = game.getLevel(currentLevel - 1);
	   List<BlockAssignment> assignments = assignment.getBlockAssignments();
	   for(BlockAssignment a: assignments) {
		   PlayedBlockAssignment pblock = new PlayedBlockAssignment(Game.WALL_PADDING + (Block.SIZE +
	               Game.COLUMNS_PADDING) * (a.getGridHorizontalPosition() - 1), Game.WALL_PADDING + (Block.SIZE + Game.ROW_PADDING) *
	               (a.getGridVerticalPosition() - 1), a.getBlock(), this);
	   }
			   
	   while (numberOfBlocks() < game.getNrBlocksPerLevel()) {
	       int x = ThreadLocalRandom.current().nextInt(1, 15);
	       int y = ThreadLocalRandom.current().nextInt(1, 15);
	       for (BlockAssignment ablockAssignment : assignments) {
	         if (ablockAssignment.getGridHorizontalPosition() == x && ablockAssignment.getGridVerticalPosition() == y) {
	           x = x++;
	           y = y++;
	         }
	         if (ablockAssignment.getGridHorizontalPosition() != x && ablockAssignment.getGridVerticalPosition() != y) {
	           PlayedBlockAssignment pblock = new PlayedBlockAssignment(x, y, game.getRandomBlock(), this);
	         }
	       }
	     }
	   
  }

  // line 73 "../../../../../Block223States.ump"
   private void doHitPaddleOrWall(){
    // TODO implement
	   bounceBall();
  }

  // line 77 "../../../../../Block223States.ump"
   private void doOutOfBounds(){
	   setLives(lives-1);
	   resetGame();
  }
   private void resetGame() {
	   resetCurrentBallX();
	   resetCurrentBallY();
	   resetBallDirectionX();
	   resetBallDirectionY();
	   resetCurrentPaddleX();
   }
   private boolean resetBallDirectionX() {
	    boolean wasReset = false;
	    getGame().getBall().setMinBallSpeedX(0);
	    wasReset = true;
	    return wasReset;
   }
   
   private boolean resetBallDirectionY() {
	    boolean wasReset = false;
	    getGame().getBall().setMinBallSpeedY(5);
	    wasReset = true;
	    return wasReset;   
   }
      

  // line 81 "../../../../../Block223States.ump"
   private void doHitBlock(){
    // TODO implement
	   
	   // gets the current score
	   // and the point value of the block to be hit
	   int score = getScore();
	   BouncePoint bounce = getBounce();
	   PlayedBlockAssignment pblock = bounce.getHitBlock();
	   Block block = pblock.getBlock();
	   int points = block.getPoints();
	   
	   // updates score
	   setScore(score + points);
	   
	   // deletes hit block from game
	   pblock.delete();
	   
	   // performs the bounce
	   bounceBall();
  }

  // line 85 "../../../../../Block223States.ump"
   private void doHitBlockNextLevel(){
    // TODO implement
	   
	   // hits block
	   doHitBlock();
	   
	   // updates to next level
	   int level = getCurrentLevel();
	   setCurrentLevel(level + 1);
	   
	   // updates paddle length
	   Game game = getGame();
	   int maxPaddle = game.getPaddle().getMaxPaddleLength();
	   int minPaddle = game.getPaddle().getMinPaddleLength();
	   int nrLevels = game.numberOfLevels();
	   setCurrentPaddleLength((double) maxPaddle - (maxPaddle - minPaddle) / (nrLevels - 1) * level);
	   
	   // calculates the speed multiplier by raising the ball speed increase factor
	   // to the number of the next level - 1
	   double ballSpeedMultiplier = game.getBall().getBallSpeedIncreaseFactor();
	   for(int i = 1; i < level; i++) {
		   ballSpeedMultiplier *= ballSpeedMultiplier;
	   }
	   setWaitTime(INITIAL_WAIT_TIME * ballSpeedMultiplier);
  }

  // line 89 "../../../../../Block223States.ump"
   private void doHitNothingAndNotOutOfBounds(){
	setCurrentBallX(getCurrentBallX() + getBallDirectionX());
	setCurrentBallY(getCurrentBallY() + getBallDirectionY());
  }

  // line 93 "../../../../../Block223States.ump"
   private void doGameOver(){
	   Block223 block223 = new Block223();
	   block223= Block223Application.getBlock223();
	   Player p = getPlayer();
	   if(p!=null) {
		   Game game = getGame();
		   HallOfFameEntry hof = new HallOfFameEntry(score, playername, p, game, block223);		//use create() method?
		   game.setMostRecentEntry(hof);
		   game.delete();
	   }
   }

   private void bounceBall(){
		//TODO
		double x = getBallDirectionX();
		double y = getBallDirectionY();
		
		 //calculate distance
		double incomingdistanceX = bounce.getX() - getCurrentBallX();
       double remainingdistanceX = getBallDirectionX() - incomingdistanceX;
       
       double incomingdistanceY = bounce.getY() - getCurrentBallY();
       double remainingdistanceY = getBallDirectionY() - incomingdistanceY;
       
		//update ball direction
	        if ((getBounce().getDirection()==BounceDirection.FLIP_X) && remainingdistanceX != 0 && remainingdistanceY !=0){// trying to make sure enum for boucepoint is set to Flip_X
	        	setBallDirectionX(x*(-1));
	        	if (y==0) {
	        		setBallDirectionY(y + 1*0.1*Math.abs(x)); 
	        	}else {
	        		setBallDirectionY(y + Math.signum(y) *0.1*Math.abs(x)); 
	        	}
	        	 	
	        }
	        if ((getBounce().getDirection()==BounceDirection.FLIP_Y)&& remainingdistanceX != 0 && remainingdistanceY !=0) {
	        	setBallDirectionY(y*(-1));
	        	if (x==0) {
	        		setBallDirectionY(x + 1*0.1*Math.abs(y)); 
	        	}else {
	        		setBallDirectionY(x + Math.signum(x) *0.1*Math.abs(y)); 
	        	}
	        }
	       
	       //update ball center/position
		 setCurrentBallX(remainingdistanceX + bounce.getX());
		 setCurrentBallY(remainingdistanceY + bounce.getY());
		}
   
  public String toString()
  {
    return super.toString() + "["+
            "id" + ":" + getId()+ "," +
            "score" + ":" + getScore()+ "," +
            "lives" + ":" + getLives()+ "," +
            "currentLevel" + ":" + getCurrentLevel()+ "," +
            "waitTime" + ":" + getWaitTime()+ "," +
            "playername" + ":" + getPlayername()+ "," +
            "ballDirectionX" + ":" + getBallDirectionX()+ "," +
            "ballDirectionY" + ":" + getBallDirectionY()+ "," +
            "currentBallX" + ":" + getCurrentBallX()+ "," +
            "currentBallY" + ":" + getCurrentBallY()+ "," +
            "currentPaddleLength" + ":" + getCurrentPaddleLength()+ "," +
            "currentPaddleX" + ":" + getCurrentPaddleX()+ "," +
            "currentPaddleY" + ":" + getCurrentPaddleY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "player = "+(getPlayer()!=null?Integer.toHexString(System.identityHashCode(getPlayer())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "game = "+(getGame()!=null?Integer.toHexString(System.identityHashCode(getGame())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "bounce = "+(getBounce()!=null?Integer.toHexString(System.identityHashCode(getBounce())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "block223 = "+(getBlock223()!=null?Integer.toHexString(System.identityHashCode(getBlock223())):"null");
  }  
  //------------------------
  // DEVELOPER CODE - PROVIDED AS-IS
  //------------------------
  
  // line 68 "../../../../../Block223Persistence.ump"
  private static final long serialVersionUID = 8597675110221231714L ;

  
}
