/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 91 "../../../../../DomainModel.ump"
// line 176 "../../../../../DomainModel.ump"
public class BlockColumn
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BlockColumn Associations
  private List<Block> blocks;
  private BlockGrid blockGrid;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BlockColumn(BlockGrid aBlockGrid)
  {
    blocks = new ArrayList<Block>();
    boolean didAddBlockGrid = setBlockGrid(aBlockGrid);
    if (!didAddBlockGrid)
    {
      throw new RuntimeException("Unable to create blockColumn due to blockGrid");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetMany */
  public Block getBlock(int index)
  {
    Block aBlock = blocks.get(index);
    return aBlock;
  }

  public List<Block> getBlocks()
  {
    List<Block> newBlocks = Collections.unmodifiableList(blocks);
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

  public int indexOfBlock(Block aBlock)
  {
    int index = blocks.indexOf(aBlock);
    return index;
  }
  /* Code from template association_GetOne */
  public BlockGrid getBlockGrid()
  {
    return blockGrid;
  }
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlocks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Block addBlock(String aColor, int aPointValue, BlockSelection aBlockSelection)
  {
    return new Block(aColor, aPointValue, aBlockSelection, this);
  }

  public boolean addBlock(Block aBlock)
  {
    boolean wasAdded = false;
    if (blocks.contains(aBlock)) { return false; }
    BlockColumn existingBlockColumn = aBlock.getBlockColumn();
    boolean isNewBlockColumn = existingBlockColumn != null && !this.equals(existingBlockColumn);
    if (isNewBlockColumn)
    {
      aBlock.setBlockColumn(this);
    }
    else
    {
      blocks.add(aBlock);
    }
    wasAdded = true;
    return wasAdded;
  }

  public boolean removeBlock(Block aBlock)
  {
    boolean wasRemoved = false;
    //Unable to remove aBlock, as it must always have a blockColumn
    if (!this.equals(aBlock.getBlockColumn()))
    {
      blocks.remove(aBlock);
      wasRemoved = true;
    }
    return wasRemoved;
  }
  /* Code from template association_AddIndexControlFunctions */
  public boolean addBlockAt(Block aBlock, int index)
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

  public boolean addOrMoveBlockAt(Block aBlock, int index)
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
  /* Code from template association_SetOneToMany */
  public boolean setBlockGrid(BlockGrid aBlockGrid)
  {
    boolean wasSet = false;
    if (aBlockGrid == null)
    {
      return wasSet;
    }

    BlockGrid existingBlockGrid = blockGrid;
    blockGrid = aBlockGrid;
    if (existingBlockGrid != null && !existingBlockGrid.equals(aBlockGrid))
    {
      existingBlockGrid.removeBlockColumn(this);
    }
    blockGrid.addBlockColumn(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    for(int i=blocks.size(); i > 0; i--)
    {
      Block aBlock = blocks.get(i - 1);
      aBlock.delete();
    }
    BlockGrid placeholderBlockGrid = blockGrid;
    this.blockGrid = null;
    if(placeholderBlockGrid != null)
    {
      placeholderBlockGrid.removeBlockColumn(this);
    }
  }

}