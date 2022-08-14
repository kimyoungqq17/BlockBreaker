package ca.mcgill.ecse223.block.persistence;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;

public class Block223Persistence {

	private static String filename = "data.block223";
	
	public static Block223 load() {
		PersistenceObjectStream.setFilename(filename);
		Block223 block223 = (Block223) PersistenceObjectStream.deserialize();
		
		if(block223==null) {
			block223 = new Block223();
		}
		else {
			 // First time block223 is called its loaded from persistence all other times it is reinitialized
			   //since some static HashMaps and id's don't get loaded properly.
			block223.reinitialize();
		}

		return block223;		
	}
		
	
	public static void save(Block223 block223) {
		
		PersistenceObjectStream.serialize(block223);
		
	}
	public static void save() {
		
		PersistenceObjectStream.serialize(Block223Application.getBlock223());
		
	}
	
}
