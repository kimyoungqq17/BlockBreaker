/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 65 "../../../../../DomainModel.ump"
// line 137 "../../../../../DomainModel.ump"
public class Ball
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final int DIAMETER = 10;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Ball Attributes
  private int speed;
  private double positionX;
  private double positionY;

  //Ball Associations
  private Level level;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Ball(int aSpeed, double aPositionX, double aPositionY, Level aLevel)
  {
    speed = aSpeed;
    positionX = aPositionX;
    positionY = aPositionY;
    if (aLevel == null || aLevel.getBall() != null)
    {
      throw new RuntimeException("Unable to create Ball due to aLevel");
    }
    level = aLevel;
  }

  public Ball(int aSpeed, double aPositionX, double aPositionY, int aLevelNumberForLevel, boolean aIsRandomGridForLevel, Paddle aPaddleForLevel, Game aGameForLevel, BlockGrid aBlockGridForLevel)
  {
    speed = aSpeed;
    positionX = aPositionX;
    positionY = aPositionY;
    level = new Level(aLevelNumberForLevel, aIsRandomGridForLevel, aPaddleForLevel, aGameForLevel, this, aBlockGridForLevel);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setSpeed(int aSpeed)
  {
    boolean wasSet = false;
    speed = aSpeed;
    wasSet = true;
    return wasSet;
  }

  public boolean setPositionX(double aPositionX)
  {
    boolean wasSet = false;
    positionX = aPositionX;
    wasSet = true;
    return wasSet;
  }

  public boolean setPositionY(double aPositionY)
  {
    boolean wasSet = false;
    positionY = aPositionY;
    wasSet = true;
    return wasSet;
  }

  public int getSpeed()
  {
    return speed;
  }

  public double getPositionX()
  {
    return positionX;
  }

  public double getPositionY()
  {
    return positionY;
  }
  /* Code from template association_GetOne */
  public Level getLevel()
  {
    return level;
  }

  public void delete()
  {
    Level existingLevel = level;
    level = null;
    if (existingLevel != null)
    {
      existingLevel.delete();
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "speed" + ":" + getSpeed()+ "," +
            "positionX" + ":" + getPositionX()+ "," +
            "positionY" + ":" + getPositionY()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "level = "+(getLevel()!=null?Integer.toHexString(System.identityHashCode(getLevel())):"null");
  }
}