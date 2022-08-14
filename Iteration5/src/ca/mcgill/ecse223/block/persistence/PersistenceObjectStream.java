package ca.mcgill.ecse223.block.persistence;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import ca.mcgill.ecse223.block.model.Block223;

public class PersistenceObjectStream {

	private static String filename = "data.block223";

	public static void serialize(Block223 block223) {
		FileOutputStream fileOut;
		try {
			fileOut = new FileOutputStream(filename);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(block223);
			out.close();
			fileOut.close();
		} catch (Exception e) {
			throw new RuntimeException("Could not save data to file '" + filename + "'.");
		}

	}

	public static Object deserialize() {
		Block223 block223 = null;
		ObjectInputStream in;
		try {
			FileInputStream fileIn = new FileInputStream(filename);
			in = new ObjectInputStream(fileIn);
			block223 = (Block223) in.readObject();
			in.close();
			fileIn.close();
		} catch (Exception e) {
			block223 = null;
		}
		return block223;
	}
	
	public static void setFilename(String newFilename) {
		filename = newFilename;
	}
	
}
