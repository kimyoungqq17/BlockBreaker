package ca.mcgill.ecse223.block.controller;

import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.BlockAssignment;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.HallOfFameEntry;
import ca.mcgill.ecse223.block.model.Level;
import ca.mcgill.ecse223.block.model.PlayedBlockAssignment;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.PlayedGame.PlayStatus;
import ca.mcgill.ecse223.block.model.Player;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;

public class Block223Controller {


	// ****************************
	// Modifier methods
	// ****************************
	
	// Younggue Kim
	public static void createGame(String name) throws InvalidInputException {
		Block223 block223 = Block223Application.getBlock223();
		UserRole currentUserRole = Block223Application.getCurrentUserRole();
		List<Game> games = block223.getGames();
		String message;
		try {
			
		
		if (!(currentUserRole instanceof Admin)) {
			message = "Admin privileges are required to create a game.";
			throw new InvalidInputException(message);
		}

		for (Game g : games) {
			if (g.getName().equals(name)) {
				message = "The name of a game must be unique.";
				throw new InvalidInputException(message);
			}
		}
		
		try {
			Game game = new Game(name, 1, (Admin) currentUserRole, 1, 1, 1, 10, 10, block223);

			Block223Application.setCurrentGame(game);

		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}
			
		}catch (NullPointerException e) {
			message = "The name of a game must be specified.";
		}
	}

	public static void setGameDetails(int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {

		if (minBallSpeedX == 0 && minBallSpeedY == 0) {
			
			throw new InvalidInputException("The minimum speed of the ball must be greater than zero.");
		}
		Game game = Block223Application.getCurrentGame();
		UserRole currentUserRole = Block223Application.getCurrentUserRole();
		String message;

		if (!(currentUserRole instanceof Admin)) {
			message = "Admin privileges are required to define game settings.";
			throw new InvalidInputException(message);
		}

		if (game == null) {
			message = "A game must be selected to define game settings.";
			throw new InvalidInputException(message);
		}

		if (game.getAdmin() != currentUserRole) {
			message = "Only the admin who created the game can define its game settings.";
			throw new InvalidInputException(message);
		}

		if (nrLevels < 1 || nrLevels > 99) {
			message = "The number of levels must be between 1 and 99.";
			throw new InvalidInputException(message);
		}
		List<Level> levels = game.getLevels();
		int size = levels.size();
	
		while (nrLevels>size) {
			game.addLevel();
			size = levels.size();
		}
		
		while(nrLevels < size) {
			Level level = game.getLevel(size -1);
			level.delete();
			size = levels.size();
		}

		try {
			game.setNrBlocksPerLevel(nrBlocksPerLevel);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}


		try {
			game.getBall().setMinBallSpeedX(minBallSpeedX);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
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

		if (minPaddleLength <= 0 || minPaddleLength > maxPaddleLength) {
			message = "The minimum length of the paddle must be greater than zero.";
			throw new InvalidInputException(message);
		}
		try {
			game.getPaddle().setMinPaddleLength(minPaddleLength);
		} catch (RuntimeException e) {
			throw new InvalidInputException(e.getMessage());
		}

	}

	public static void deleteGame(String name) throws InvalidInputException { //sean
				String message;
		String failure = "There was an exception while deleting a non-existing game.";	//very important failure, that's why it has its' own variable
		Game gameToDelete = null;
		Game compareGame = null;
		
		try {
			UserRole currentUserRole = Block223Application.getCurrentUserRole();
			if (!(currentUserRole instanceof Admin)) {
				message = "Admin privileges are required to delete a game.";
				throw new InvalidInputException(message);
			}

			List<Game> listOfGames = Block223Application.getBlock223().getGames();			
			for(int i=0; i<listOfGames.size(); i++) {
				compareGame = listOfGames.get(i);
				if(compareGame.getName().equals(name)) {
					gameToDelete = compareGame;
					
					if(gameToDelete.getAdmin() != currentUserRole) {
						message = "Only the admin who created the game can delete the game.";
						throw new InvalidInputException(message);
					}
					if(gameToDelete.getPublished()) {
						message = "A published game cannot be deleted.";
						throw new InvalidInputException(message);
					}
					
					try {
						Admin adminOfGameToDelete = gameToDelete.getAdmin();
						if (adminOfGameToDelete==null || gameToDelete==null) {
							message = failure;
						}
					} catch (NullPointerException e) {
						throw new InvalidInputException(e.getMessage());
					}
						
				}
				if(i==listOfGames.size()-1 && gameToDelete==null) {
					message = failure;
				}
			}
			
			if(gameToDelete != null) {
				Block223 block223 = gameToDelete.getBlock223();
				gameToDelete.delete();
				Block223Persistence.save(block223);
			}
			
		} catch (NullPointerException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}

	public static void selectGame(String name) throws InvalidInputException { //sean
		
		String message;
		String failure = "A game with name " + name + " does not exist.";		
		UserRole currentUserRole = Block223Application.getCurrentUserRole();
		if(!(currentUserRole instanceof Admin)) {
			message = "Admin privileges are required to select a game.";
			throw new InvalidInputException(message);
		}
		Game gameToSelect = Block223Application.getBlock223().findGame(name);
		if(gameToSelect==null) {
			message = failure;
			throw new InvalidInputException(message);
		}
		if(gameToSelect.getAdmin() != currentUserRole) {
			message = "Only the admin who created the game can select the game.";
			throw new InvalidInputException(message);
		}
			if(gameToSelect.getPublished()) {
				message = "A published game cannot be changed.";
				throw new InvalidInputException(message);
			}
		
		Block223Application.setCurrentGame(gameToSelect);
	//	Block223Application.generalSettings();  should be placed somewhere in UI
	
	}

	public static void updateGame(String name, int nrLevels, int nrBlocksPerLevel, int minBallSpeedX, int minBallSpeedY,
			Double ballSpeedIncreaseFactor, int maxPaddleLength, int minPaddleLength) throws InvalidInputException {
		
		String message;
		UserRole currentUserRole = Block223Application.getCurrentUserRole();
		
		try {
			Game game = Block223Application.getCurrentGame();
			String currentName = game.getName();
			if(currentName =="" || currentName.equals("")) {
				message = "The name of a game must be specified.";
				throw new InvalidInputException(message);
			}
			Admin gameAdmin = game.getAdmin();
			if(!(currentUserRole instanceof Admin)) {
				message = "Admin privileges are required to define game settings.";
				throw new InvalidInputException(message);
			}
			if(currentUserRole != gameAdmin) {
				message = "Only the admin who created the game can define its game settings.";
				throw new InvalidInputException(message);
			}
			if (currentName != name) {
				try {
					List<Game> games = Block223Application.getBlock223().getGames();
					for(Game g : games) {
					if(g.getName().equals(name)) {
						message = "The name of a game must be unique.";
						throw new InvalidInputException(message);
						}
					}
					game.setName(name);
			
				}catch (RuntimeException e) {
					throw new InvalidInputException (e.getMessage());
				} 
			}
		}catch (NullPointerException e) {
			message = "A game must be selected to define game settings.";
			throw new InvalidInputException(message);
		}
		
		try {
			setGameDetails(nrLevels, nrBlocksPerLevel, minBallSpeedX, minBallSpeedY, ballSpeedIncreaseFactor,
					maxPaddleLength, minPaddleLength);
			
		}catch(InvalidInputException e) {
			throw new InvalidInputException(e.getMessage());
		}
				
	}

	public static void addBlock(int red, int green, int blue, int points) throws InvalidInputException {
		String error = "";
		try {
			Game CurrentGame = Block223Application.getCurrentGame();
			UserRole currentUserRole = Block223Application.getCurrentUserRole();
			if (!(currentUserRole instanceof Admin)) {
				error = "Admin privileges are required to add a block.";
			}
			if (currentUserRole instanceof Admin) { // if admin, checks if admin of selected game
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
			if (CurrentGame != null) { // checks if block of same color already exists
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
		} catch (NullPointerException e) {
			error = "A game must be selected to add a block.";
			throw new InvalidInputException(error);
		}

	}

	public static void deleteBlock(int id) throws InvalidInputException {
		String error = "";
		try {
			Game CurrentGame = Block223Application.getCurrentGame();
			UserRole currentUserRole = Block223Application.getCurrentUserRole();
			if (!(currentUserRole instanceof Admin)) {
				error = "Admin privileges are required to delete a block.";
			}
			if (currentUserRole instanceof Admin) { // if admin, checks if admin of selected game
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
		} catch (NullPointerException e) {
			error = "A game must be selected to delete a block.";
			throw new InvalidInputException(error);
		}
	}

	public static void updateBlock(int id, int red, int green, int blue, int points) throws InvalidInputException {
		String error = "";
		// checks if current user is an admin
		UserRole currentUserRole = Block223Application.getCurrentUserRole();
		if (!(currentUserRole instanceof Admin)) {
			error = "Admin privileges are required to update a block.";
		}
		// checks if game exists
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			error = "A game must be selected to update a block.";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
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
			throw new InvalidInputException(error);
		}
	}

	public static void positionBlock(int id, int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {
		String error = "";
		// checks if current user is an admin
		UserRole currentUserRole = Block223Application.getCurrentUserRole();
		if (!(currentUserRole instanceof Admin)) {
			error = "Admin privileges are required to position a block.";
		}
		// checks if game exists
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			error = "A game must be selected to position a block.";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
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
		// BlockAssignment
		Level currentLevel;
		try {
			currentLevel = game.getLevel(level - 1);
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
		if (currentLevel != null) {
			List<BlockAssignment> blockassignments = currentLevel.getBlockAssignments();	
			for (BlockAssignment ba : blockassignments) {
				if (ba.getGridHorizontalPosition() == gridHorizontalPosition && ba.getGridVerticalPosition() == gridVerticalPosition) {
					throw new InvalidInputException("A block already exists at location " + gridHorizontalPosition + "/" + gridVerticalPosition + ".");
				}
			}
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		
		if (currentLevel.getBlockAssignments().size() == game.getNrBlocksPerLevel()) {
			throw new InvalidInputException("The number of blocks has reached the maximum number (" + game.getNrBlocksPerLevel() + ") allowed for this game.");
		}

		if (gridHorizontalPosition <= 26 && gridHorizontalPosition >= 1 && gridVerticalPosition <= 31
				&& gridVerticalPosition >= 1) {
			currentLevel.addBlockAssignment(gridHorizontalPosition, gridVerticalPosition, block, game);
		}

		else if (gridHorizontalPosition > 25 || gridHorizontalPosition < 1) {
			throw new InvalidInputException("The horizontal position must be between 1 and 25.");
		}

		else if (gridVerticalPosition > 31 || gridVerticalPosition < 1) {
			throw new InvalidInputException("The vertical position must be between 1 and 31.");
		}
	}

		public static void moveBlock(int level, int oldGridHorizontalPosition, int oldGridVerticalPosition,
			int newGridHorizontalPosition, int newGridVerticalPosition) throws InvalidInputException {
			
			String error = "";
			UserRole currentUserRole = Block223Application.getCurrentUserRole();
			if (!(currentUserRole instanceof Admin)) {
				throw new InvalidInputException("Admin privileges are required to move a block.");
			}
			// checks if game exists
			Game game = Block223Application.getCurrentGame(); // define Game game
			if (game == null) {
				throw new InvalidInputException("A game must be selected to move a block.");
			}
			
			if (currentUserRole instanceof Admin) { // if admin, checks if admin of selected game
				boolean sameName = false;
				List<Game> games = ((Admin) currentUserRole).getGames();
				for (Game g : games) {
					if (g.getName().equals(game.getName())) {
						sameName = true;
					}
				}
				if (sameName == false) {
					error = "Only the admin who created the game can move a block.";
				}
			}
			if (error.length() > 0) {
				throw new InvalidInputException(error.trim());
			}
			if (newGridHorizontalPosition > 15 || newGridHorizontalPosition < 1) {	//not sure yet why this had to be 15
				throw new InvalidInputException("The horizontal position must be between 1 and 15.");
			}
			if (newGridVerticalPosition > 15 || newGridVerticalPosition < 1) {
				throw new InvalidInputException("The vertical position must be between 1 and 15.");
			}
			Level getlevel;
			try {
				getlevel = game.getLevel(level - 1);
				}
			catch (IndexOutOfBoundsException e) {
				throw new InvalidInputException("Level " + level + " does not exist for the game.");
				}
			
			BlockAssignment assignment = getlevel.findBlockAssignment(oldGridHorizontalPosition, oldGridVerticalPosition);
			if (assignment == null) {
				throw new InvalidInputException("A block does not exist at location " + oldGridHorizontalPosition + "/" + oldGridVerticalPosition
						+ ".");
			}
			if (getlevel != null) {
				List<BlockAssignment> blockassignments = getlevel.getBlockAssignments();	
				for (BlockAssignment ba : blockassignments) {
					if (ba.getGridHorizontalPosition() == newGridHorizontalPosition && ba.getGridVerticalPosition() == newGridVerticalPosition) {
						throw new InvalidInputException("A block already exists at location " + newGridHorizontalPosition + "/" + newGridVerticalPosition + ".");
					}
				}
			}
			if (newGridHorizontalPosition <= 26 && newGridHorizontalPosition >= 1 && newGridVerticalPosition <= 31
					&& newGridVerticalPosition >= 1) {
				assignment.setGridHorizontalPosition(newGridHorizontalPosition);
				assignment.setGridVerticalPosition(newGridVerticalPosition);
			}
		}
	
	public static void removeBlock(int level, int gridHorizontalPosition, int gridVerticalPosition)
			throws InvalidInputException {

		if (!(Block223Application.getCurrentUserRole() instanceof Admin)) {
			throw new InvalidInputException("Admin privileges are required to remove a block.");
		}
		if (Block223Application.getCurrentGame()==null) {
			throw new InvalidInputException("A game must be selected to remove a block.");
		}

		Game game = Block223Application.getCurrentGame();
		Level aLevel = game.getLevel(level - 1);
		BlockAssignment blockAssignment = aLevel.findBlockAssignment(gridHorizontalPosition, gridVerticalPosition);
		if (blockAssignment != null) {
			blockAssignment.delete();
		}
		// checks if current user is an admin
		UserRole currentUserRole = Block223Application.getCurrentUserRole();

		// if admin, check if admin of selected game
		if (currentUserRole instanceof Admin) {
			boolean sameName = false;
			List<Game> games = ((Admin) currentUserRole).getGames();
			for (Game g : games) {
				if (g.getName().equals(game.getName())) {
					sameName = true;
					break; 
				}
			}
			if (sameName == false) {
				throw new InvalidInputException("Only the admin who created the game can remove a block.");
			}
		}
	}

	/*
	 * saves the games, settings and users of the application Note: currently, this
	 * just saves an image of the entirety of the Block223 application. This means
	 * that editing game's settings and NOT saving, but then saving on a different
	 * game's settings will save the first game's settings as well.
	 */
	public static void saveGame() throws InvalidInputException {

		// check that an admin is making the request
		UserRole user = Block223Application.getCurrentUserRole();
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

		// save the game through persistence files
		try {
			Block223Persistence.save();
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	// create a new user account (admin password is optional)
	public static void register(String username, String playerPassword, String adminPassword)
			throws InvalidInputException {

		if (Block223Application.getCurrentUserRole() != null)
			throw new InvalidInputException("Cannot register a new user while a user is logged in.");

		if (playerPassword == null || playerPassword.isEmpty())
			throw new InvalidInputException("The player password needs to be specified.");

		if (username == null || username.isEmpty())
			throw new InvalidInputException("The username must be specified.");

		if (playerPassword.equals(adminPassword))
			throw new InvalidInputException("The passwords have to be different.");

		for (User user : Block223Application.getBlock223().getUsers()) {
			if (user.getUsername().equals(username))
				throw new InvalidInputException("The username has already been taken.");
		}

		UserRole[] roles = new UserRole[2];
		roles[0] = new Player(playerPassword, Block223Application.getBlock223());

		// check that a valid admin password is given: if so, create an admin role for
		// this account
		// && is to make sure NullPointer is thrown on second statement
		if (adminPassword != null && !adminPassword.isEmpty())
			roles[1] = new Admin(adminPassword, Block223Application.getBlock223());

		Block223Application.getBlock223().addUser(new User(username, Block223Application.getBlock223(), roles));

		// save the game through the persistence files
		try {
			Block223Persistence.save();
		} catch (RuntimeException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	// log in using either an admin or player password (with its associated
	// username)
	// NOTE: loging in as a player currently does nothing
	public static void login(String username, String password) throws InvalidInputException {

		if (!getUserMode().getMode().equals(TOUserMode.Mode.None))
			throw new InvalidInputException("Cannot perform login while a user is logged in.");

		boolean userFound = false;
		User loginUser = null;
		// check each user for a match
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

		// check first if there are 2 roles to avoid NullPointer
		else if (loginUser.numberOfRoles() == 2 && loginUser.getRole(1).getPassword().equals(password))
			Block223Application.setCurrentUserRole(loginUser.getRole(1));

		else
			throw new InvalidInputException("The username and password do not match.");

		// note: once playing the game is implemented, an else statement will lead the
		// user there
		if (getUserMode().getMode().equals(TOUserMode.Mode.Design))
			Block223Application.adminHome();
		else
			Block223Application.chooseGame();

	}

	// controller method to log out as current user, return to login page
	public static void logout() {

		Block223Application.setCurrentUserRole(null);

	}

	// play mode

	public static void selectPlayableGame(String name, int id) throws InvalidInputException {
		String message = " ";
		UserRole userRole = Block223Application.getCurrentUserRole();
	
		Game game = Block223Application.getBlock223().findGame(name); //written by Sean iteration 2 
		Block223 block223 = Block223Application.getBlock223();
		PlayedGame pgame = null;//TODO im not sure setting null is  right, ask the group later
		try {
			if(userRole == null || userRole instanceof Admin) {
				message += "Player privileges are required to play a game.";
				throw new InvalidInputException(message);			
			}
			if(game != null) {
				UserRole player = Block223Application.getCurrentUserRole();
				String username = User.findUsername(player);
				if(username == null) {
					throw new InvalidInputException("The game does not exist.");
				}
				PlayedGame result = new PlayedGame(username, game, block223);
				result.setPlayer((Player) player);
				
			}
			else {
				 pgame= Block223Application.getBlock223().findPlayableGame(id);
				
			}
			if ((game == null) && (pgame == null))
				throw new InvalidInputException("The game does not exist.");
			if ((game == null) && (Block223Application.getCurrentUserRole() != pgame.getPlayer()))
				throw new InvalidInputException("Only the player that started a game can continue the game.");

			
			Block223Application.setCurrentPlayableGame(pgame);
		
		}catch(NullPointerException e) {
			throw new InvalidInputException(e.getMessage());
		}
	}
	public static void startGame(Block223PlayModeInterface ui) throws InvalidInputException {
		if (Block223Application.getCurrentUserRole() == null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		if ((Block223Application.getCurrentPlayableGame() == null)) {
			throw new InvalidInputException("A game must be selected to play it.");
		}
		if ((Block223Application.getCurrentUserRole() instanceof Admin) && (Block223Application.getCurrentPlayableGame().getPlayer() != null)) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		if ((Block223Application.getCurrentUserRole() instanceof Admin) && (Block223Application.getCurrentUserRole() != Block223Application.getCurrentGame().getAdmin())) {//Check for the admin of the function
			throw new InvalidInputException("Only the admin of a game can test the game.");
		}
		if ((Block223Application.getCurrentUserRole() instanceof Player) && (Block223Application.getCurrentPlayableGame().getPlayer() == null)) {
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}
		Block223Application.getCurrentPlayableGame().play();
		ui.takeInputs();

		while (Block223Application.getCurrentPlayableGame().getPlayStatus() == PlayStatus.Moving) {
			String userInputs = ui.takeInputs();
			updatePaddlePosition(userInputs);
			Block223Application.getCurrentPlayableGame().move();
			if (userInputs.contains(" ")) {
				Block223Application.getCurrentPlayableGame().pause();
			}try {
			Thread.sleep((long) Block223Application.getCurrentPlayableGame().getWaitTime());
			}catch (InterruptedException e) {
				
			}

			ui.refresh();
		}
		if (Block223Application.getCurrentPlayableGame().getPlayStatus() == PlayStatus.GameOver) {
//			Block223Application.HallOffame();
			Block223Application.setCurrentPlayableGame(null);
		} else if (Block223Application.getCurrentPlayableGame().getPlayer() != null) {
			Block223 block223 = Block223Application.getBlock223();
			Block223Persistence.save(block223);
		}
	}

	public static void updatePaddlePosition(String userinputs) {
		for (int i = 0; i < userinputs.length(); i++) {
			if (userinputs.charAt(i) == 'l') {
				left(Block223Application.getCurrentPlayableGame());
			}
			if (userinputs.charAt(i) == 'r') {
				right(Block223Application.getCurrentPlayableGame());
			}
			if (userinputs.charAt(i) == ' ') {
				break;
			}
		}
	}

	private static void left(PlayedGame pgame) {
		if (pgame.getCurrentPaddleX() > 0)
			pgame.setCurrentPaddleX(pgame.getCurrentPaddleX() + PlayedGame.PADDLE_MOVE_LEFT);
	}

	private static void right(PlayedGame pgame) {
		if (Game.PLAY_AREA_SIDE - pgame.getCurrentPaddleLength() > pgame.getCurrentPaddleX())
			pgame.setCurrentPaddleX(pgame.getCurrentPaddleX() + PlayedGame.PADDLE_MOVE_RIGHT);
	}


	public static void testGame(Block223PlayModeInterface ui) throws InvalidInputException {
		if (!(Block223Application.getCurrentUserRole() instanceof Admin))
			throw new InvalidInputException("Admin privileges are required to test a game.");
		if (Block223Application.getCurrentGame() == null)
			throw new InvalidInputException("A game must be selected to test it.");

		boolean ownsGame = false;
		for (Game game : ((Admin) Block223Application.getCurrentUserRole()).getGames()) {
			if (game.equals(Block223Application.getCurrentGame()))
				ownsGame = true;
		}
		if (!ownsGame)
			throw new InvalidInputException("Only the admin who created the game can test it.");

		PlayedGame pg = new PlayedGame(User.findUsername(Block223Application.getCurrentUserRole()),
				Block223Application.getCurrentGame(), Block223Application.getBlock223());
		pg.setPlayer(null);
		Block223Application.setCurrentPlayableGame(pg);
		Block223Application.playGame();//startGame(ui); replaced this since playGame starts a game.
	}

	public static void publishGame() throws InvalidInputException {
		if (!(Block223Application.getCurrentUserRole() instanceof Admin))
			throw new InvalidInputException("Admin privileges are required to publish a game.");
		if (Block223Application.getCurrentGame() == null)
			throw new InvalidInputException("A game must be selected to publish it.");

		boolean ownsGame = false;
		for (Game game : ((Admin) Block223Application.getCurrentUserRole()).getGames()) {
			if (game.equals(Block223Application.getCurrentGame()))
				ownsGame = true;
		}
		if (!ownsGame)
			throw new InvalidInputException("Only the admin who created the game can publish it.");
		
		if(Block223Application.getCurrentGame().getBlocks().size() < 1)
			throw new InvalidInputException("At least one block must be defined for a game to be published.");
		
		Block223Application.getCurrentGame().setPublished(true);
	}

	// ****************************
	// Query methods
	// ****************************
	public static List<TOGame> getDesignableGames() throws InvalidInputException {
		String error = "";
		Block223 block223 = Block223Application.getBlock223();
		UserRole currentUserRole = Block223Application.getCurrentUserRole();
		List<TOGame> result = new ArrayList<TOGame>();
		
		if (!(currentUserRole instanceof Admin)) {
			error = "Admin privileges are required to access game information.";
			throw new InvalidInputException(error);
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}

		List<Game> games = block223.getGames();
		for (Game game : games) { // iterate through list of games
			Admin gameAdmin = game.getAdmin(); // and get the admin of each game.
			if (gameAdmin.equals(currentUserRole)) {
				if(!game.getPublished()) {
					TOGame to = new TOGame(
							game.getName(),
							game.getLevels().size(),
							game.getNrBlocksPerLevel(),
							game.getBall().getMinBallSpeedX(),
							game.getBall().getMinBallSpeedY(),
							game.getBall().getBallSpeedIncreaseFactor(),
							game.getPaddle().getMaxPaddleLength(),
							game.getPaddle().getMinPaddleLength()
							);
					result.add(to);	
				}
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
		String message;

		Game game = Block223Application.getCurrentGame();
		UserRole currentUserRole = Block223Application.getCurrentUserRole();
		
		if (!(currentUserRole instanceof Admin)) {
			message = "Admin privileges are required to access game information.";
			throw new InvalidInputException(message);
		}
		if(game==null) {
			message = "A game must be selected to access its information.";
			throw new InvalidInputException(message);
		}
		if(game.getAdmin() != currentUserRole) {
			message = "Only the admin who created the game can access its information.";
			throw new InvalidInputException(message);
		}
		
		TOGame to = new TOGame(
				game.getName(),
				game.getLevels().size(),
				game.getNrBlocksPerLevel(),
				game.getBall().getMinBallSpeedX(),
				game.getBall().getMinBallSpeedY(),
				game.getBall().getBallSpeedIncreaseFactor(),
				game.getPaddle().getMaxPaddleLength(),
				game.getPaddle().getMinPaddleLength()
				);
		return to;
	}

//view must not know about the model so this transfer object allows the view to import this method and get the blocks saved in the system
	public static List<TOBlock> getBlocksOfCurrentDesignableGame() throws InvalidInputException {
		String error = "";
		try {
			Game CurrentGame = Block223Application.getCurrentGame();
			UserRole currentUserRole = Block223Application.getCurrentUserRole();
			if (!checkIfAdmin(currentUserRole)) {
				error += "Admin privileges are required to access game information.";
			}
			if (currentUserRole instanceof Admin) {
				boolean sameName = false;
				List<Game> games = ((Admin) currentUserRole).getGames();

				for (Game g : games) {
					if (g.getName().equals(CurrentGame.getName())) {
						sameName = true;
					}
				}
				if (sameName == false) {
					error += "Only the admin who created the game can access its information. ";
				}
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
		} catch (NullPointerException e) {
			error = "A game must be selected to access its information.";
			throw new InvalidInputException(error);
		}
	}

	public static TOBlock getBlockOfCurrentDesignableGame(int id) throws InvalidInputException {
		String error = "";
		Game CurrentGame = Block223Application.getCurrentGame();
		UserRole currentUserRole = Block223Application.getCurrentUserRole();
		
		if (CurrentGame == null) {
			error += " A game must be selected to access its information. ";
		}
		
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		//Admin currentAdmin = Block223Application.getCurrentGame().getAdmin();

		if (!checkIfAdmin(currentUserRole)) {
			error += "Admin privileges are required to access game information. ";
		}
		
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		if (!checkIfCorrectAdmin(((Admin) currentUserRole), CurrentGame)) {
			error += " Only the admin who created the game can access its information. ";
		}
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		Block block = CurrentGame.findBlock(id);
		if (block == null) {
			error = "The block does not exist.";
		}
		
		if (error.length() > 0) {
			throw new InvalidInputException(error);
		}
		
		TOBlock toblock = new TOBlock(block.getId(), block.getRed(), block.getGreen(), block.getBlue(),
				block.getPoints());

		return toblock;
	}

	public static List<TOGridCell> getBlocksAtLevelOfCurrentDesignableGame(int level) throws InvalidInputException {
		String error = "";
		// checks if current user is an admin
		UserRole currentUserRole = Block223Application.getCurrentUserRole();
		if (!(currentUserRole instanceof Admin)) {
			error += "Admin privileges are required to access game information. ";
		}
		// checks if game exists
		Game game = Block223Application.getCurrentGame();
		if (game == null) {
			error += "A game must be selected to access its information. ";
		}
		
		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
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
				error += "Only the admin who created the game can access its information. ";
			}
		}

		if (error.length() > 0) {
			throw new InvalidInputException(error.trim());
		}
		Level currentLevel;
		try {
			currentLevel = game.getLevel(level - 1);
		} catch (IndexOutOfBoundsException e) {
			throw new InvalidInputException("Level " + level + " does not exist for the game. ");
		}

		List<TOGridCell> toassignments = new ArrayList<TOGridCell>();
		if (game != null) {
			List<BlockAssignment> assignments = currentLevel.getBlockAssignments();

			for (BlockAssignment ba : assignments) {
				toassignments.add(new TOGridCell(ba.getGridHorizontalPosition(), ba.getGridVerticalPosition(),
						ba.getBlock().getId(), ba.getBlock().getRed(), ba.getBlock().getGreen(),
						ba.getBlock().getBlue(), ba.getBlock().getPoints()));
			}
		}
		return toassignments;
	}

	// return the current user's mode
	public static TOUserMode getUserMode() {
		TOUserMode mode;

		// it was simpler to set the default mode to admin, then correct later if
		// necessary
		mode = new TOUserMode(TOUserMode.Mode.Design);

		if (Block223Application.getCurrentUserRole() == null)
			mode = new TOUserMode(TOUserMode.Mode.None);

		else {
			try {
				if (((Player) Block223Application.getCurrentUserRole()).getPassword()
						.equals(Block223Application.getCurrentUserRole().getPassword()))
					mode = new TOUserMode(TOUserMode.Mode.Play);
			}
			// catch does nothing, if an exception is thrown, it's because its not a player
			catch (ClassCastException e) {
			}
		}

		return mode;
	}

	// play mode

	public static List<TOPlayableGame> getPlayableGames() throws InvalidInputException {
		String message;
		UserRole userRole = Block223Application.getCurrentUserRole();
	
		Block223 block223 = Block223Application.getBlock223();
		UserRole player = Block223Application.getCurrentUserRole();
		ArrayList<TOPlayableGame> result = new ArrayList<TOPlayableGame>();
		List<Game> games = block223.getGames();
		List<PlayedGame> played = player.getBlock223().getPlayedGames();
		
		try {
			if(userRole == null || userRole instanceof Admin) {
				message = "Player privileges are required to play a game.";
				throw new InvalidInputException(message);
			}
			
			for(Game game: games) {
				if(game.isPublished()) {
					if(game.isPublished()) {
						TOPlayableGame to = new TOPlayableGame(game.getName(), -1, 0);
						result.add(to);
					}
				}
			}
			for(PlayedGame game: played) {
				TOPlayableGame to = new TOPlayableGame(game.getGame().getName(), game.getId(), game.getCurrentLevel());
				result.add(to);
			}
			
			return result;
		}catch(NullPointerException e) {
			throw new InvalidInputException(e.getMessage());
		}
		
	}


public static TOCurrentlyPlayedGame getCurrentPlayableGame() throws InvalidInputException {
		if (Block223Application.getCurrentUserRole() == null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		if (Block223Application.getCurrentPlayableGame() == null) {
			throw new InvalidInputException("A game must be selected to play it.");
		}
		Player currentPlayer = Block223Application.getCurrentPlayableGame().getPlayer();
		if (Block223Application.getCurrentUserRole() instanceof Admin && currentPlayer != null) {
			throw new InvalidInputException("Player privileges are required to play a game.");
		}
		Admin gameAdmin = Block223Application.getCurrentPlayableGame().getGame().getAdmin();
		if (Block223Application.getCurrentUserRole() instanceof Admin && Block223Application.getCurrentUserRole() != gameAdmin) {
			throw new InvalidInputException("Only the admin of a game can test the game.");
		}
		if (Block223Application.getCurrentUserRole() instanceof Player && currentPlayer == null) {
			throw new InvalidInputException("Admin privileges are required to test a game.");
		}
		TOCurrentlyPlayedGame result = new TOCurrentlyPlayedGame(Block223Application.getCurrentPlayableGame().getGame().getName(), Block223Application.getCurrentPlayableGame().getPlayStatus() == PlayStatus.Ready || Block223Application.getCurrentPlayableGame().getPlayStatus() == PlayStatus.Paused, Block223Application.getCurrentPlayableGame().getScore(), Block223Application.getCurrentPlayableGame().getLives(),
				Block223Application.getCurrentPlayableGame().getCurrentLevel(), Block223Application.getCurrentPlayableGame().getPlayername(), (int) Block223Application.getCurrentPlayableGame().getCurrentBallX(), (int) Block223Application.getCurrentPlayableGame().getCurrentBallY(), (int) Block223Application.getCurrentPlayableGame().getCurrentPaddleLength(),
				(int) Block223Application.getCurrentPlayableGame().getCurrentPaddleX());
		List<PlayedBlockAssignment> blocks = Block223Application.getCurrentPlayableGame().getBlocks();
		for (PlayedBlockAssignment pblocks : blocks) {
			new TOCurrentBlock(pblocks.getBlock().getRed(), pblocks.getBlock().getGreen(), pblocks.getBlock().
					getBlue(), pblocks.getBlock().getPoints(), pblocks.getX(), pblocks.getY(), result);
		}
		return result;

	}

	public static TOHallOfFame getHallOfFame(int start, int end) throws InvalidInputException {
	String message;
	TOHallOfFame result;
	UserRole currentUserRole = Block223Application.getCurrentUserRole();
	try {
		if(!(currentUserRole instanceof Player) || currentUserRole instanceof Admin) {
			message = "Player privileges are required to access a game's hall of fame.";
			throw new InvalidInputException(message.trim());
		}
		PlayedGame pgame = Block223Application.getCurrentPlayableGame();
		if(pgame==null || pgame.equals(null)) {
			message = "A game must be selected to view its hall of fame.";
			throw new InvalidInputException(message.trim());
		}
		Game game = pgame.getGame();
		result = new TOHallOfFame(game.getName());
		if(start < 1) {
			start=1;
		}
		if(end >= game.numberOfHallOfFameEntries()) {
			end=game.numberOfHallOfFameEntries();

		}else{
			end=game.numberOfHallOfFameEntries()-end;
		}

		start = game.numberOfHallOfFameEntries() - start;
		end = game.numberOfHallOfFameEntries() - end;
		
		for(int i=start; i>=end; i--) {
			TOHallOfFameEntry to = new TOHallOfFameEntry(
					i+1,
					game.getHallOfFameEntry(i).getPlayername(),
					game.getHallOfFameEntry(i).getScore(),
					result
					);
			result.addEntry(to);
		}
	}catch(NullPointerException e) {
		throw new InvalidInputException(e.getMessage());
	}
	return result;
}

//a little bubble sort to sort hall of fame and return in correct order. This is NOT fully functioning and potentially useless.
public static TOHallOfFame sortHall(TOHallOfFame unsorted) {
	TOHallOfFame sorted = new TOHallOfFame(unsorted.getGamename());
	TOHallOfFameEntry left = null;
	TOHallOfFameEntry right = null;
	for(int i=0; i<unsorted.numberOfEntries() - 1; i++) {
		for(int j=0; j<unsorted.numberOfEntries() - 1 - i; j++) {
			left = unsorted.getEntry(i);
			right = unsorted.getEntry(i+1);
			if(left.getScore()<right.getScore()) {
				TOHallOfFameEntry temp = left;
				left = right;
				right = temp;
			}sorted.addEntry(left); 	//adds entries in 'correct' order
		}sorted.addEntry(right);		//add the last entry (this is causing a bug idk how to add the entries...)
	}
	return sorted;
}

public static TOHallOfFame getHallOfFameWithMostRecentEntry(int numberOfEntries) throws InvalidInputException {
	String message;
	TOHallOfFame result;
	UserRole currentUserRole = Block223Application.getCurrentUserRole();
	
	try {
		
		if(!(currentUserRole instanceof Player)) {
			message = "Player privileges are required to access a game's hall of fame.";
			throw new InvalidInputException(message);
		}
		
		PlayedGame pgame = Block223Application.getCurrentPlayableGame();
		if(pgame==null || pgame.equals(null)) {
			message = "A game must be selected to view its hall of fame.";
			throw new InvalidInputException(message);
		}
		Game game = pgame.getGame();
		result = new TOHallOfFame(game.getName());
		HallOfFameEntry mostRecent = game.getMostRecentEntry();
		int indexR = game.indexOfHallOfFameEntry(mostRecent);
		int start = indexR - numberOfEntries/2;
		if(start<1) {
			start = 1;
		}else {
			start = game.numberOfHallOfFameEntries() - start;
		}
		
		int end = start + numberOfEntries - 1;
		if(end>game.numberOfHallOfFameEntries()) {
			end = game.numberOfHallOfFameEntries();
		}else{
			end = game.numberOfHallOfFameEntries() - end;
		}
		
		start = game.numberOfHallOfFameEntries() - start;
//		end = game.numberOfHallOfFameEntries() - end;
		for(int i=start; i>=end; i--) {
			TOHallOfFameEntry to = new TOHallOfFameEntry(
					i+1,
					game.getHallOfFameEntry(i).getPlayername(),
					game.getHallOfFameEntry(i).getScore(),
					result
					);
			result.addEntry(to);
		}	
	}catch(NullPointerException e) {
		throw new InvalidInputException(e.getMessage());
	}
	return result;
}
	
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

}
