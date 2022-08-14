/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 59 "../../../../../DomainModel.ump"
// line 132 "../../../../../DomainModel.ump"
public class Paddle
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final int WIDTH = 5;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Paddle Attributes
  private int length;
  private int speed;

  //Paddle Associations
  private Level level;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Paddle(int aLength, int aSpeed, Level aLevel)
  {
    length = aLength;
    speed = aSpeed;
    if (aLevel == null || aLevel.getPaddle() != null)
    {
      throw new RuntimeException("Unable to create Paddle due to aLevel");
    }
    level = aLevel;
  }

  public Paddle(int aLength, int aSpeed, int aLevelNumberForLevel, boolean aIsRandomGridForLevel, Game aGameForLevel, Ball aBallForLevel, BlockGrid aBlockGridForLevel)
  {
    length = aLength;
    speed = aSpeed;
    level = new Level(aLevelNumberForLevel, aIsRandomGridForLevel, this, aGameForLevel, aBallForLevel, aBlockGridForLevel);
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setLength(int aLength)
  {
    boolean wasSet = false;
    length = aLength;
    wasSet = true;
    return wasSet;
  }

  public boolean setSpeed(int aSpeed)
  {
    boolean wasSet = false;
    speed = aSpeed;
    wasSet = true;
    return wasSet;
  }

  public int getLength()
  {
    return length;
  }

  public int getSpeed()
  {
    return speed;
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
            "length" + ":" + getLength()+ "," +
            "speed" + ":" + getSpeed()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "level = "+(getLevel()!=null?Integer.toHexString(System.identityHashCode(getLevel())):"null");
  }
}