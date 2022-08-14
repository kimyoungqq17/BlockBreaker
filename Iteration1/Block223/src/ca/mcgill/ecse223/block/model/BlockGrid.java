/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 86 "../../../../../DomainModel.ump"
// line 156 "../../../../../DomainModel.ump"
public class BlockGrid
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BlockGrid Associations
  private List<BlockColumn> blockColumns;
  private Level level;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BlockGrid(Level aLevel)
  {
    blockColumns = new ArrayList<BlockColumn>();
    if (aLevel == null || aLevel.getBlockGrid() != null)
    {
      throw new RuntimeException("Unable to create BlockGrid due to aLevel");
    }
    level = aLevel;
  }

  public BlockGrid(int aLevelNumberForLevel, boolean aIsRandomGridForLevel, Paddle aPaddleForLevel, Game aGameForLevel, Ball aBallForLevel)
  {
    blockColumns = new ArrayList<BlockColumn>();
    level = new Level(aLevelNumberForLevel, aIsRandomGridForLevel, aPaddleForLevel, aGameForLevel, aBallForLevel, this);
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public BlockColumn getBlockColumn(int index)
  {
    BlockColumn aBlockColumn = blockColumns.get(index);
    return aBlockColumn;
  }

  public List<BlockColumn> getBlockColumns()
  {
    List<BlockColumn> newBlockColumns = Collections.unmodifiableList(blockColumns);
    return newBlockColumns;
  }

  public int numberOfBlockColumns()
  {
    int number = blockColumns.size();
    return number;
  }

  public boolean hasBlockColumns()
  {
    boolean has = blockColumns.size() > 0;
    return has;
  }

  public int indexOfBlockColumn(BlockColumn aBlockColumn)
  {
    int index = blockColumns.indexOf(aBlockColumn);
    return index;
  }
  /* Code from template association_GetOne */
  public Level getLevel()
  {
    return level;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlockColumns()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public BlockColumn addBlockColumn()
  {
    return new BlockColumn(this);
  }

  public boolean addBlockColumn(BlockColumn aBlockColumn)
  {
    boolean wasAdded = false;
    if (blockColumns.contains(aBlockColumn)) { return false; }
    BlockGrid existingBlockGrid = aBlockColumn.getBlockGrid();
    boolean isNewBlockGrid = existingBlockGrid != null && !this.equals(existingBlockGrid);
    if (isNewBlockGrid)
    {
      aBlockColumn.setBlockGrid(this);
    }
    else
    {
      blockColumns.add(aBlockColumn);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBlockColumn(BlockColumn aBlockColumn)
  {
    boolean wasRemoved = false;
    //Unable to remove aBlockColumn, as it must always have a blockGrid
    if (!this.equals(aBlockColumn.getBlockGrid()))
    {
      blockColumns.remove(aBlockColumn);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBlockColumnAt(BlockColumn aBlockColumn, int index)
  {  
    boolean wasAdded = false;
    if(addBlockColumn(aBlockColumn))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlockColumns()) { index = numberOfBlockColumns() - 1; }
      blockColumns.remove(aBlockColumn);
      blockColumns.add(index, aBlockColumn);
      wasAdded = true;
    }
    return wasAdded;
  }

  public boolean addOrMoveBlockColumnAt(BlockColumn aBlockColumn, int index)
  {
    boolean wasAdded = false;
    if(blockColumns.contains(aBlockColumn))
    {
      if(index < 0 ) { index = 0; }
      if(index > numberOfBlockColumns()) { index = numberOfBlockColumns() - 1; }
      blockColumns.remove(aBlockColumn);
      blockColumns.add(index, aBlockColumn);
      wasAdded = true;
    } 
    else 
    {
      wasAdded = addBlockColumnAt(aBlockColumn, index);
    }
    return wasAdded;
  }

  public void delete()
  {
    while (blockColumns.size() > 0)
    {
      BlockColumn aBlockColumn = blockColumns.get(blockColumns.size() - 1);
      aBlockColumn.delete();
      blockColumns.remove(aBlockColumn);
    }
    
    Level existingLevel = level;
    level = null;
    if (existingLevel != null)
    {
      existingLevel.delete();
    }
  }

}