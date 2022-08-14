package ca.mcgill.ecse223.block.controller;

import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Ball;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.BlockAssignment;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.Level;
import ca.mcgill.ecse223.block.model.Paddle;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

public class Block223Controller {

	/*
	 * Helper methods to: 1) check if a current user is an admin 2) check if a
	 * certain admin is the correct admin of a given game Helper method written by
	 * Sean, taken from Garrett's controller methods.
	 */

	public static boolean checkIfAdmin(UserRole currentUserRole) {
		if (currentUserRole instanceof Admin) {
			return true;
		}
		return false;
	}

	public static boolean checkIfCorrectAdmin(Admin admin, Game game) {
		boolean sameName = false;
		List<Game> adminsGames = admin.getGames();
		for (Game g : adminsGames) { // goes through the list of all the admin's games (g)
			if (g.getName().equals(game.getName())) { // compares admin's games to game passed as parameter
				sameName = true;
			}
		}
		return sameName;
	}

	// ****************************
	// Modifier methods
	// ****************************
	public static void createGame(String name) throws InvalidInputException {
		Block223 block223 = Block223Application.getBlock223();
		UserRole currentUserRole = Block223Application.getcurrentUserRole();
		List<Game> games = block223.getGames();
		String message;
		if (!(currentUserRole instanceof Admin)) {
			message = "Admin privileges are required to create a game.";
			throw new InvalidInputException(message);
		}

		if (name.equals(null) || name.contentEquals(" ")) {
			message = "The name of a game must be specified.";
			throw new InvalidInputException(message);
		}
		for (Game g : games) {
			if (g.getName().equals(name)) {
				message = "The name of a game must be unique.";
				throw new InvalidInputException(message);
			}
		}
		try {
			Game game = new Game(name, 1, (Admin) currentUserRole, 0, 0, 0, 0, 0, block223);
			block223.addGame(game);
			((Admin) Block223Application.getcurrentUserRole()).addGame(game);
			Block223Application.setCurrentGame(game);
			Block223Application.generalSettings();
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void setGameDetails(int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {

		Game game = Block223Application.getCurrentGame();
		UserRole currentUserRole = Block223Application.getcurrentUserRole();
		String message;

		if (!(currentUserRole instanceof Admin)) {
			message = "Admin privileges are required to define game settings.";
			throw new InvalidInputException(message);
		}

		if (game == null) {
			message = "A game must be selected to define game settings.";
			throw new InvalidInputException(message);
		}


			if (game.getAdmin()!=currentUserRole) {
				message = "Only the admin who created the game can define its game settings.";
				throw new InvalidInputException(message);
			}
		
		if (nrLevels < 1 || nrLevels > 99) {
			message = "The number of levels must be between 1 and 99.";
			throw new InvalidInputException(message);
		}
		int size = game.getLevels().size();
		for (int i = 0; i < nrLevels; i++) {
			game.addLevel();
			size++;
		}
		if (nrLevels > size) {
			int diff = nrLevels - size;
			for (int i = 0; i < diff; i++) {
				game.addLevel();
				size++;
			}
		} else if (nrLevels < size) {
			int diff = size - nrLevels;
			for (int i = 0; i < diff; i++) {
				Level level = game.getLevel(size - i);
				level.delete();
				size--;
			}
		}

		if (nrBlocksPerLevel <= 0) {
			message = "The number of blocks per Level must be greater than zero.";
			throw new InvalidInputException(message);
		}
		try {
			game.setNrBlocksPerLevel(nrBlocksPerLevel);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

		if (minBallSpeedX <= 0) {
			message = "The minimum speed of the ball must be greater than zero.";
			throw new InvalidInputException(message);
		}
		try {
			game.getBall().setMinBallSpeedX(minBallSpeedX);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

		if (minBallSpeedY <= 0) {
			message = "The minimum speed of the ball must be greater than zero.";
			throw new InvalidInputException(message);
		}
		try {
			game.getBall().setMinBallSpeedY(minBallSpeedY);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

		if (ballSpeedIncreaseFactor <= 0) {
			message = "The speed increase factor of the ball must be greater than zero.";
			throw new InvalidInputException(message);
		}
		try {
			game.getBall().setBallSpeedIncreaseFactor(ballSpeedIncreaseFactor);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

		if (maxPaddleLength <= 0 || maxPaddleLength >= 390) {
			message = "The maximum length of the paddle must be greater than zero and less than or equal to 390.";
			throw new InvalidInputException(message);
		}
		try {
			game.getPaddle().setMaxPaddleLength(maxPaddleLength);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

		if (minPaddleLength <= 0 || minPaddleLength>maxPaddleLength) {
			message = "The minimum length of the paddle must be greater than zero.";
			throw new InvalidInputException(message);
		}
		try {
			game.getPaddle().setMinPaddleLength(minPaddleLength);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}

	public static void deleteGame(String name) throws InvalidInputException {
		String error = "";
		// TODO: IMPLEMENT THE findGame(String name){} method in block223 class
		// otherwise we're screwed.
		Game game = null;
		for(Game aGame : Block223Application.getBlock223().getGames()) {
			if(aGame.getName().equals(name))
				game = aGame;
		}
		UserRole currentUserRole = Block223Application.getcurrentUserRole();
		Admin currentGameAdmin = Block223Application.getCurrentGame().getAdmin();

		if (!checkIfAdmin(currentUserRole)) {
			error += " Admin privileges are required to position a delete a game. ";
		}
		if (!checkIfCorrectAdmin(currentGameAdmin, game)) {
			error += "Only the admin who created the game can delete the game. ";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}

		if (game != null) {
			game.delete();
			Block223Application.setCurrentGame(null);
			Block223Persistence.save();
			Block223Application.adminHome();
		}

	}

	public static void selectGame(String name) throws InvalidInputException {
		String error = "";
		Game game = null;
		for(Game aGame : Block223Application.getBlock223().getGames()) {
			if(aGame.getName().equals(name))
				game = aGame;
		}
		UserRole currentUserRole = Block223Application.getcurrentUserRole();
		Admin currentAdmin = (Admin) currentUserRole;
		if (game == null) {
			error += " A game with name " + name + " does not exist. ";
		}
		if (!checkIfAdmin(currentUserRole)) {
			error += "Admin privileges are required to update the game. ";
		}
		if (!checkIfCorrectAdmin(currentAdmin, game)) {
			error += "Only the admin who created the game can select the game. "; // of the specific admin.
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		Block223Application.setCurrentGame(game);
		Block223Application.generalSettings();
	}

	@SuppressWarnings("unused")
	public static void updateGame(String name, int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {

		String error = "";
		Game game = Block223Application.getCurrentGame();
		UserRole currentUser = Block223Application.getcurrentUserRole();
		Admin currentAdmin = game.getAdmin();

		if (game == null) {
			error += " A game must be selected to define game setting. ";
		}
		if (!checkIfAdmin(currentUser)) {
			error += " Admin privileges are required to define game game settings. ";
		}
		if (!checkIfCorrectAdmin(currentAdmin, game)) {
			error += " Only the admin who created the game can select the game. "; // of the specific admin.
		}

		String currentName = game.getName();
		if (game.setName(name) == false) {
			error += " The name of a game must be unique. ";
		}
		if (currentName.equals(null)) {
			error += " The name of a game must be specified. ";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}

		if (currentName != name) {
			game.setName(name);
		}
		setGameDetails(nrLevels, nrBlocksPerLevel, minBallSpeedX, minBallSpeedY, ballSpeedIncreaseFactor,
				maxPaddleLength, minPaddleLength);

	}

	public static void addBlock(int red, int green, int blue, int points) throws InvalidInputException {
		String error = "";
		Game CurrentGame = Block223Application.getCurrentGame();
		UserRole currentUserRole = Block223Application.getcurrentUserRole();
		if (!(currentUserRole instanceof Admin)) {
			error = "Admin privileges are required to add a block.";
		}
		if (CurrentGame == null) {
			error = "A game must be selected to add a block.";
		}
		if (currentUserRole instanceof Admin) {  // if admin, checks if admin of selected game
			boolean sameName = false;
			List<Game> games = ((Admin) currentUserRole).getGames();
			for (Game g : games) {
				if (g.getName().equals(CurrentGame.getName())) {
					sameName = true;
				}
			}
			if (sameName == false) {
				error = "Only the admin who created the game can add a block.";
			}
		}
		if (CurrentGame != null) { 				// checks if block of same color already exists
			boolean sameColor = false;
			List<Block> blocks = CurrentGame.getBlocks();

			for (Block b : blocks) {
				if (b.getRed() == red && b.getGreen() == green && b.getBlue() == blue) {
					sameColor = true;
				}
			}
			if (sameColor) {
				error = "A block with the same color already exists for the game.";
			}
		}		
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		try {
			CurrentGame.addBlock(red, green, blue, points);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void deleteBlock(int id) throws InvalidInputException {
		String error = "";
		Game CurrentGame = Block223Application.getCurrentGame();
		UserRole currentUserRole = Block223Application.getcurrentUserRole();
		if (!(currentUserRole instanceof Admin)) {
			error = "Admin privileges are required to delete a block.";
		}
		if (CurrentGame == null) {
			error = "A game must be selected to delete a block.";
		}
		if (currentUserRole instanceof Admin) {  // if admin, checks if admin of selected game
			boolean sameName = false;
			List<Game> games = ((Admin) currentUserRole).getGames();
			for (Game g : games) {
				if (g.getName().equals(CurrentGame.getName())) {
					sameName = true;
				}
			}
			if (sameName == false) {
				error = "Only the admin who created the game can delete a block.";
			}
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		Block block = CurrentGame.findBlock(id);
		if (block != null) {
			block.delete();
		}
	}

	public static void updateBlock(int id, int red, int green, int blue, int points) throws InvalidInputException {
		String error = "";

		// TO DO: validation checks
		// checks if current user is an admin
		UserRole currentUserRole = Block223Application.getcurrentUserRole();
		if (!(currentUserRole instanceof Admin)) {
			error = "Admin privileges are required to update a block.";
		}

		// checks if game exists
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			error = "A game must be selected to update a block.";
		}

		// if admin, checks if admin of selected game
		if (currentUserRole instanceof Admin) {
			boolean sameName = false;
			List<Game> games = ((Admin) currentUserRole).getGames();

			for (Game g : games) {
				if (g.getName().equals(game.getName())) {
					sameName = true;
				}
			}

			if (sameName == false) {
				error = "Only the admin who created the game can update a block.";
			}
		}

		// checks if block of same color already exists
		if (game != null) {
			boolean sameColor = false;
			List<Block> blocks = game.getBlocks();

			for (Block b : blocks) {
				if (b.getRed() == red && b.getGreen() == green && b.getBlue() == blue && b.getId() != id) {
					sameColor = true;
				}
			}

			if (sameColor) {
				error = "A block with the same color already exists for the game.";
			}
		}

		// finds the block with matching id
		Block block = null;
		if (game != null) {
			List<Block> blocks = game.getBlocks();

			for (Block b : blocks) {
				if (b.getId() == id) {
					block = b;
				}
			}

			if (block == null) {
				error = "The block does not exist.";
			}
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		// updates the block
		// TO DO: validate red, green, blue
		try {
			block.setRed(red);
			block.setGreen(green);
			block.setBlue(blue);
			block.setPoints(points);
		}

		catch (RuntimeException e) {
			error = e.getMessage();
			// TO DO:
		}
	}

	// TO DO
	public static void positionBlock(int id, int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {
		String error = "";

		// TO DO: validation checks
		// checks if current user is an admin
		UserRole currentUserRole = Block223Application.getcurrentUserRole();
		if (!(currentUserRole instanceof Admin)) {
			error = "Admin privileges are required to position a block.";
		}

		// checks if game exists
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			error = "A game must be selected to position a block.";
		}

		// if admin, checks if admin of selected game
		if (currentUserRole instanceof Admin) {
			boolean sameName = false;
			List<Game> games = ((Admin) currentUserRole).getGames();

			for (Game g : games) {
				if (g.getName().equals(game.getName())) {
					sameName = true;
				}
			}

			if (sameName == false) {
				error = "Only the admin who created the game can position a block.";
			}
		}

		// TO DO: exceptions, currentLevel.getBlocks(), findBlock, create
		// BlockAssignment
		Level currentLevel;
		try {
			currentLevel = game.getLevel(level);
		}

		catch (IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level " + level + " does not exist for the game.");
		}

		// finds the block with matching id
		Block block = null;
		if (game != null) {
			List<Block> blocks = game.getBlocks();

			for (Block b : blocks) {
				if (b.getId() == id) {
					block = b;
				}
			}

			if (block == null) {
				error = "The block does not exist.";
			}
		}
		
		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		
		if(gridHorizontalPosition <= 26 && gridHorizontalPosition >= 1 && gridVerticalPosition <= 31 && gridVerticalPosition >= 1) {
			currentLevel.addBlockAssignment(gridHorizontalPosition, gridVerticalPosition, block, game);
		}
		
		else if(gridHorizontalPosition > 25 || gridHorizontalPosition < 1) {
			throw new InvalidInputException("The horizontal position must be between 1 and 25.");
		}
		
		else if(gridVerticalPosition > 31 || gridVerticalPosition < 1) {
			throw new InvalidInputException("The vertical position must be between 1 and 31.");
		}
	}

	public static void moveBlock(int level, int oldGridHorizontalPosition, int oldGridVerticalPosition,
			int newGridHorizontalPosition, int newGridVerticalPosition) throws InvalidInputException {
	//-----------------------------------verification start----------------------------------------------
		String error = "";
		
		// checks if current user is an admin
				UserRole currentUserRole = Block223Application.getcurrentUserRole();
				if(!(currentUserRole instanceof Admin)) {
					error = "Admin privileges are required to remove a block.";
				}
				
		// checks if game exists
				Game game = Block223Application.getCurrentGame();			//define Game game
				if(game == null) {
					error = "A game must be selected to remove a block.";
				}
				
		// if admin, check if admin of selected game
				if(currentUserRole instanceof Admin) {
					boolean sameName = false;
					List<Game> games = ((Admin) currentUserRole).getGames();
					
					for(Game g : games) {
						if(g.getName().equals(game.getName())) {
							sameName = true;
							break;				//possibly wrong?
						}
					}
					
					if(sameName == false) {
						error = "Only the admin who created the game can remove a block.";
					}
				}
				
				
				
				if(error.length()>0) {
					throw new InvalidInputException(error);
				}
				//-----------------------------------verification end----------------------------------------------				
		error="";		//reset error
				
		Level l = null;
		if (level<=game.getLevels().size() && level>=game.MIN_NR_LEVELS) {
			l = game.getLevel(level);		   	
		}else {
			error="Level" + level + "does not exist";
		}
		
				
		BlockAssignment assignment = l.findBlockAssignment(oldGridHorizontalPosition, oldGridVerticalPosition);
		if (assignment!=null) {
			assignment.delete();
		}else {
			error="A block does not exist at location " + oldGridHorizontalPosition +"/"+oldGridVerticalPosition+".";
		}
		if (l.findBlockAssignment(newGridHorizontalPosition, newGridVerticalPosition)!=null) {
			error="A block already exists at location " +newGridHorizontalPosition+"/"+newGridVerticalPosition+".";
		}
		
		else if(newGridVerticalPosition>0 && newGridVerticalPosition<(Game.PLAY_AREA_SIDE+Game.WALL_PADDING)/Block.SIZE) {
			error="The horizontal position must be between 1 and " + (Game.PLAY_AREA_SIDE+Game.WALL_PADDING)/Block.SIZE + ".";			
		}else {
			assignment.setGridVerticalPosition(newGridVerticalPosition);
			assignment.setGridHorizontalPosition(newGridHorizontalPosition);
		}
		
		if(error.length()>0) {
			throw new InvalidInputException(error);
		}
		
	}
	
	public static void removeBlock(int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {
		
		//-----------------------------------verification start----------------------------------------------		
		String error = "";
		
		// checks if current user is an admin
				UserRole currentUserRole = Block223Application.getcurrentUserRole();
				if(!(currentUserRole instanceof Admin)) {
					error = "Admin privileges are required to remove a block.";
				}
				
		// checks if game exists
				Game game = Block223Application.getCurrentGame();
				if(game == null) {
					error = "A game must be selected to remove a block.";
				}
				
		// if admin, check if admin of selected game
				if(currentUserRole instanceof Admin) {
					boolean sameName = false;
					List<Game> games = ((Admin) currentUserRole).getGames();
					
					for(Game g : games) {
						if(g.getName().equals(game.getName())) {
							sameName = true;
							break;				//possibly wrong???????????????????????????????
						}
					}
					
					if(sameName == false) {
						error = "Only the admin who created the game can remove a block.";
					}
				}
	//-----------------------------------verification end----------------------------------------------
		Level l;
		l = game.getLevel(level);
		BlockAssignment assignment = l.findBlockAssignment(gridHorizontalPosition, gridVerticalPosition);
		if (assignment!=null) {
			assignment.delete();
		}
	}

	/*
	 * saves the games, settings and users of the application
	 * Note: currently, this just saves an image of the entirety of the Block223
	 * application. This means that editing game's settings and NOT saving, but then
	 * saving on a different game's settings will save the first game's settings as well. 
	 */
	public static void saveGame() throws InvalidInputException {

		//check that an admin is making the request
		UserRole user = Block223Application.getcurrentUserRole();
		if (!getUserMode().getMode().equals(TOUserMode.Mode.Design))
			throw new InvalidInputException("Admin privileges are required to save a game.");

		if (Block223Application.getCurrentGame() == null)
			throw new InvalidInputException("A game must be selected to save it.");

		boolean ownsGame = false;
		for (Game game : ((Admin) user).getGames()) {
			if (game.equals(Block223Application.getCurrentGame()))
				ownsGame = true;
		}

		if (!ownsGame)
			throw new InvalidInputException("Only the admin who created the game can save it.");

		//save the game through persistence files
		try {
			Block223Persistence.save();
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	//create a new user account (admin password is optional)
	public static void register(String username, String playerPassword, String adminPassword)
			throws InvalidInputException {

		if (Block223Application.getcurrentUserRole() != null)
			throw new InvalidInputException("Cannot register a new user while a user is logged in.");

		if (playerPassword == null || playerPassword.isEmpty())
			throw new InvalidInputException("The player password needs to be specified.");

		if (username.isEmpty())
			throw new InvalidInputException("The username must be specified.");

		if (playerPassword.equals(adminPassword))
			throw new InvalidInputException("The passwords have to be different.");

		for (User user : Block223Application.getBlock223().getUsers()) {
			if (user.getUsername().equals(username))
				throw new InvalidInputException("The username has already been taken.");
		}

		UserRole[] roles = new UserRole[2];
		roles[0] = new Player(playerPassword, Block223Application.getBlock223());

		//check that a valid admin password is given: if so, create an admin role for this account
		//&& is to make sure NullPointer is thrown on second statement
		if (adminPassword != null && !adminPassword.isEmpty())
			roles[1] = new Admin(adminPassword, Block223Application.getBlock223());

		Block223Application.getBlock223().addUser(new User(username, Block223Application.getBlock223(), roles));

		//save the game through the persistence files
		try {
			Block223Persistence.save();
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	//log in using either an admin or player password (with its associated username)
	//NOTE: loging in as a player currently does nothing
	public static void login(String username, String password) throws InvalidInputException {

		if (!getUserMode().getMode().equals(TOUserMode.Mode.None))
			throw new InvalidInputException("Cannot perform login while a user is logged in.");

		boolean userFound = false;
		User loginUser = null;
		//check each user for a match
		for (User user : Block223Application.getBlock223().getUsers()) {
			if (user.getUsername().equals(username)) {
				userFound = true;
				loginUser = user;
			}
		}

		if (!userFound)
			throw new InvalidInputException("The username and password do not match.");

		if (loginUser.getRole(0).getPassword().equals(password))
			Block223Application.setCurrentUserRole(loginUser.getRole(0));

		//check first if there are 2 roles to avoid NullPointer
		else if (loginUser.numberOfRoles() == 2 && loginUser.getRole(1).getPassword().equals(password))
			Block223Application.setCurrentUserRole(loginUser.getRole(1));

		else
			throw new InvalidInputException("The username and password do not match.");
		
		//note: once playing the game is implemented, an else statement will lead the user there
		if(getUserMode().getMode().equals(TOUserMode.Mode.Design))
			Block223Application.adminHome();

	}

	//controller method to log out as current user, return to login page
	public static void logout() {

		Block223Application.setCurrentUserRole(null);
		Block223Application.login();
		
	}

	// ****************************
	// Query methods
	// ****************************
	public static List<TOGame> getDesignableGames() throws InvalidInputException {
		String error = "";

		Block223 block223 = Block223Application.getBlock223();
		UserRole currentUserRole = Block223Application.getcurrentUserRole();
		List<TOGame> result = create();

		if (!checkIfAdmin(currentUserRole)) {
			error = "Admin privileges are required to access game information.";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}

		List<Game> games = block223.getGames();
		for (Game game : games) { // iterate through list of games
			Admin gameAdmin = game.getAdmin(); // and get the admin of each game.
			if (gameAdmin.equals(currentUserRole)) {
				TOGame to = (TOGame) create(); // TODO: check if casting is correct way to implement this method.
				result.add(to);
			}
		}
		return result;
	}

	// helper method to return a list of toGames, used for getDesignableGames(){}
	public static List<TOGame> create() {
		List<TOGame> toGames = new ArrayList<TOGame>(); // create a list of TOGames
		for (Game game : Block223Application.getBlock223().getGames()) { // iterate through all the games in
																			// block223Application
			TOGame toGame = new TOGame( // TOGame constructor fields initialized with games from block223Application
					game.getName(), game.getLevels().size(), game.getNrBlocksPerLevel(),
					game.getBall().getMinBallSpeedX(), game.getBall().getMinBallSpeedY(),
					game.getBall().getBallSpeedIncreaseFactor(), game.getPaddle().getMaxPaddleLength(),
					game.getPaddle().getMinPaddleLength());
			toGames.add(toGame);
		}
		return toGames;
	}

	public static TOGame getCurrentDesignableGame() throws InvalidInputException {
		String error = "";

		Game game = Block223Application.getCurrentGame();
		TOGame to = (TOGame) create();
		UserRole currentUserRole = Block223Application.getcurrentUserRole();
		Admin currentAdmin = Block223Application.getCurrentGame().getAdmin();

		if (game == null) {
			error += " A game must be selected to access its information. ";
		}
		if (!checkIfAdmin(currentUserRole)) {
			error += "Admin privileges are required to access game information. ";
		}
		if (!checkIfCorrectAdmin(currentAdmin, game)) {
			error += " Only the admin who created the game can access game information. ";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		return to;
	}

//view must not know about the model so this transfer object allows the view to import this method and get the blocks saved in the system
	public static List<TOBlock> getBlocksOfCurrentDesignableGame() throws InvalidInputException {
		String error = "";
		Game CurrentGame = Block223Application.getCurrentGame();
		UserRole currentUserRole = Block223Application.getcurrentUserRole();
		Admin currentAdmin = Block223Application.getCurrentGame().getAdmin();

		if (CurrentGame == null) {
			error += " A game must be selected to access its information. ";
		}
		if (!checkIfAdmin(currentUserRole)) {
			error += "Admin privileges are required to access game information. ";
		}
		if (!checkIfCorrectAdmin(currentAdmin, CurrentGame)) {
			error += " Only the admin who created the game can access game information. ";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		ArrayList<TOBlock> blocks = new ArrayList<TOBlock>();
		for (Block block : CurrentGame.getBlocks()) {
			TOBlock toblock = new TOBlock(block.getId(), block.getRed(), block.getGreen(), block.getBlue(),
					block.getPoints());
			blocks.add(toblock);
		}
		return blocks;
	}

	public static TOBlock getBlockOfCurrentDesignableGame(int id) throws InvalidInputException {
		String error = "";
		Game CurrentGame = Block223Application.getCurrentGame();
		UserRole currentUserRole = Block223Application.getcurrentUserRole();
		
		Admin currentAdmin = Block223Application.getCurrentGame().getAdmin();

		if (CurrentGame == null) {
			error += " A game must be selected to access its information. ";
		}
		if (!checkIfAdmin(currentUserRole)) {
			error += "Admin privileges are required to access game information. ";
		}
		if (!checkIfCorrectAdmin(currentAdmin, CurrentGame)) {
			error += " Only the admin who created the game can access game information. ";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error); 
		}
		Block block = CurrentGame.findBlock(id);
		if (block == null) {
			error = "The block does not exist";
		}
		TOBlock toblock = new TOBlock(block.getId(),block.getRed(),block.getGreen(),block.getBlue(),block.getPoints());
			
		return toblock;
	}

	public static List<TOGridCell> getBlocksAtLevelOfCurrentDesignableGame(int level) throws InvalidInputException {
		String error = "";
		// checks if current user is an admin
		UserRole currentUserRole = Block223Application.getcurrentUserRole();
		if(!(currentUserRole instanceof Admin)) {
			error += "Admin privileges are required to access game information. ";
		}
		// checks if game exists
		Game game = Block223Application.getCurrentGame();
		if(game == null) {
			error += "A game must be selected to access its information. ";
		}
		// if admin, checks if admin of selected game
		if(currentUserRole instanceof Admin) {
			boolean sameName = false;
			List<Game> games = ((Admin) currentUserRole).getGames();
			
			for(Game g : games) {
				if(g.getName().equals(game.getName())) {
					sameName = true;
				}
			}
			if(sameName == false) {
				error += "Only the admin who created the game can access its information. ";
			}
		}
		
		if(error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		Level currentLevel;
		try {
			currentLevel = game.getLevel(level - 1);
		}
		catch(IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level " + level + " does not exist for the game. ");
		}
		
		List<TOGridCell> toassignments = new ArrayList<TOGridCell>();
		if(game != null) {
			List<BlockAssignment> assignments = currentLevel.getBlockAssignments();
			
			for(BlockAssignment ba : assignments) {
				toassignments.add(new TOGridCell(ba.getGridHorizontalPosition(), ba.getGridVerticalPosition(), 
						ba.getBlock().getId(), ba.getBlock().getRed(), ba.getBlock().getGreen(), 
						ba.getBlock().getBlue(), ba.getBlock().getPoints()));
			}
		}
		return toassignments;
	}

	//return the current user's mode
	public static TOUserMode getUserMode() {
		TOUserMode mode;

		//it was simpler to set the default mode to admin, then correct later if necessary
		mode = new TOUserMode(TOUserMode.Mode.Design);
		
		if (Block223Application.getcurrentUserRole() == null)
			mode = new TOUserMode(TOUserMode.Mode.None);

		else {
			try {
				if (((Player) Block223Application.getcurrentUserRole()).getPassword()
						.equals(Block223Application.getcurrentUserRole().getPassword()))
					mode = new TOUserMode(TOUserMode.Mode.Play);
			} 
			//catch does nothing, if an exception is thrown, it's because its not a player
			catch (ClassCastException e) {}
		}


		return mode;
	}

}

