/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;

// line 78 "../../../../../DomainModel.ump"
// line 149 "../../../../../DomainModel.ump"
public class Block
{

  //------------------------
  // STATIC VARIABLES
  //------------------------

  public static final int SIZE = 20;

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //Block Attributes
  private String color;
  private int pointValue;

  //Block Associations
  private BlockSelection blockSelection;
  private BlockColumn blockColumn;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public Block(String aColor, int aPointValue, BlockSelection aBlockSelection, BlockColumn aBlockColumn)
  {
    color = aColor;
    pointValue = aPointValue;
    boolean didAddBlockSelection = setBlockSelection(aBlockSelection);
    if (!didAddBlockSelection)
    {
      throw new RuntimeException("Unable to create block due to blockSelection");
    }
    boolean didAddBlockColumn = setBlockColumn(aBlockColumn);
    if (!didAddBlockColumn)
    {
      throw new RuntimeException("Unable to create block due to blockColumn");
    }
  }

  //------------------------
  // INTERFACE
  //------------------------

  public boolean setColor(String aColor)
  {
    boolean wasSet = false;
    color = aColor;
    wasSet = true;
    return wasSet;
  }

  public boolean setPointValue(int aPointValue)
  {
    boolean wasSet = false;
    pointValue = aPointValue;
    wasSet = true;
    return wasSet;
  }

  public String getColor()
  {
    return color;
  }

  public int getPointValue()
  {
    return pointValue;
  }
  /* Code from template association_GetOne */
  public BlockSelection getBlockSelection()
  {
    return blockSelection;
  }
  /* Code from template association_GetOne */
  public BlockColumn getBlockColumn()
  {
    return blockColumn;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBlockSelection(BlockSelection aBlockSelection)
  {
    boolean wasSet = false;
    if (aBlockSelection == null)
    {
      return wasSet;
    }

    BlockSelection existingBlockSelection = blockSelection;
    blockSelection = aBlockSelection;
    if (existingBlockSelection != null && !existingBlockSelection.equals(aBlockSelection))
    {
      existingBlockSelection.removeBlock(this);
    }
    blockSelection.addBlock(this);
    wasSet = true;
    return wasSet;
  }
  /* Code from template association_SetOneToMany */
  public boolean setBlockColumn(BlockColumn aBlockColumn)
  {
    boolean wasSet = false;
    if (aBlockColumn == null)
    {
      return wasSet;
    }

    BlockColumn existingBlockColumn = blockColumn;
    blockColumn = aBlockColumn;
    if (existingBlockColumn != null && !existingBlockColumn.equals(aBlockColumn))
    {
      existingBlockColumn.removeBlock(this);
    }
    blockColumn.addBlock(this);
    wasSet = true;
    return wasSet;
  }

  public void delete()
  {
    BlockSelection placeholderBlockSelection = blockSelection;
    this.blockSelection = null;
    if(placeholderBlockSelection != null)
    {
      placeholderBlockSelection.removeBlock(this);
    }
    BlockColumn placeholderBlockColumn = blockColumn;
    this.blockColumn = null;
    if(placeholderBlockColumn != null)
    {
      placeholderBlockColumn.removeBlock(this);
    }
  }


  public String toString()
  {
    return super.toString() + "["+
            "color" + ":" + getColor()+ "," +
            "pointValue" + ":" + getPointValue()+ "]" + System.getProperties().getProperty("line.separator") +
            "  " + "blockSelection = "+(getBlockSelection()!=null?Integer.toHexString(System.identityHashCode(getBlockSelection())):"null") + System.getProperties().getProperty("line.separator") +
            "  " + "blockColumn = "+(getBlockColumn()!=null?Integer.toHexString(System.identityHashCode(getBlockColumn())):"null");
  }
}