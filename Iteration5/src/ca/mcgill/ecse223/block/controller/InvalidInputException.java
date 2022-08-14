package ca.mcgill.ecse223.block.controller;

public class InvalidInputException extends Exception {
	//TODO
	//not sure about the line below, seems to have a link with btms so i commented it out
	//private static final long serialVersionUID = -5633915762703837868L;
	
	public InvalidInputException(String errorMessage) {
		super(errorMessage);
	}

}
