package ca.mcgill.ecse223.block.controller;

public class InvalidInputException extends Exception {
	
	public InvalidInputException(String errorMessage) {
		super(errorMessage);
	}

}
