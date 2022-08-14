/*PLEASE DO NOT EDIT THIS CODE*/
/*This code was generated using the UMPLE 1.29.0.4181.a593105a9 modeling language!*/

package ca.mcgill.ecse223.block.model;
import java.util.*;

// line 73 "../../../../../DomainModel.ump"
// line 143 "../../../../../DomainModel.ump"
public class BlockSelection
{

  //------------------------
  // MEMBER VARIABLES
  //------------------------

  //BlockSelection Associations
  private Game game;
  private List<Block> blocks;

  //------------------------
  // CONSTRUCTOR
  //------------------------

  public BlockSelection(Game aGame)
  {
    if (aGame == null || aGame.getBlockSelection() != null)
    {
      throw new RuntimeException("Unable to create BlockSelection due to aGame");
    }
    game = aGame;
    blocks = new ArrayList<Block>();
  }

  public BlockSelection(String aNameForGame, GameSetting aGameSettingForGame, HallOfFame aHallOfFameForGame, Block223 aBlock223ForGame)
  {
    game = new Game(aNameForGame, aGameSettingForGame, aHallOfFameForGame, aBlock223ForGame, this);
    blocks = new ArrayList<Block>();
  }

  //------------------------
  // INTERFACE
  //------------------------
  /* Code from template association_GetOne */
  public Game getGame()
  {
    return game;
  }
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
  /* Code from template association_MinimumNumberOfMethod */
  public static int minimumNumberOfBlocks()
  {
    return 0;
  }
  /* Code from template association_AddManyToOne */
  public Block addBlock(String aColor, int aPointValue, BlockColumn aBlockColumn)
  {
    return new Block(aColor, aPointValue, this, aBlockColumn);
  }

  public boolean addBlock(Block aBlock)
  {
    boolean wasAdded = false;
    if (blocks.contains(aBlock)) { return false; }
    BlockSelection existingBlockSelection = aBlock.getBlockSelection();
    boolean isNewBlockSelection = existingBlockSelection != null && !this.equals(existingBlockSelection);
    if (isNewBlockSelection)
    {
      aBlock.setBlockSelection(this);
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
    //Unable to remove aBlock, as it must always have a blockSelection
    if (!this.equals(aBlock.getBlockSelection()))
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

  public void delete()
  {
    Game existingGame = game;
    game = null;
    if (existingGame != null)
    {
      existingGame.delete();
    }
    while (blocks.size() > 0)
    {
      Block aBlock = blocks.get(blocks.size() - 1);
      aBlock.delete();
      blocks.remove(aBlock);
    }
    
  }

}