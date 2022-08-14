package ca.mcgill.ecse223.block.application;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;

import ca.mcgill.ecse223.block.model.Block223;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;
import ca.mcgill.ecse223.block.view.*;

public class Block223Application {

	private static Block223 block223;
	private static Game currentGame;
	private static UserRole currentUserRole;
	
	//JFrames for each view (more will be added with player functions
	private static LoginWelcomePage login;
	private static AdminHomePage adminHome;
	private static BlockSettingsPage blockSettings;
	private static GameSettingsPage gameSettings;
	private static GeneralSettingsPage generalSettings;
	private static LevelSettingsPage levelSettings;
	
	//hold all the JFrames (used for collective state changes)
	private static ArrayList<JFrame> frames;
	
	//buttons to click in the backend only: will tell the windows to appear to user
	private static JButton adminHomeBtn, loginBtn, blocksSetBtn, gameSetBtn, generalSetBtn, levelSetBtn;

	public static void main(String[] args) {
		currentUserRole = null;
		currentGame = null;
		
		//get a block223 image (either from file or fresh)
		block223 = resetBlock223();
		
		login = new LoginWelcomePage();
		adminHome = new AdminHomePage();
		blockSettings = new BlockSettingsPage();
		gameSettings = new GameSettingsPage();
		generalSettings = new GeneralSettingsPage();
		levelSettings = new LevelSettingsPage();
		
		frames = new ArrayList<JFrame>();
		frames.add(login);
		frames.add(adminHome);
		frames.add(blockSettings);
		frames.add(gameSettings);
		frames.add(generalSettings);
		frames.add(levelSettings);
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				//open login upon execution
				try {					
					login.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//create buttons to open different windows
				adminHomeBtn = new JButton();
				adminHomeBtn.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						adminHome.refresh();
						adminHome.setVisible(true);
					}
				});
				
				loginBtn = new JButton();
				loginBtn.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						login.refresh();
						login.setVisible(true);
					}
				});
				
				blocksSetBtn = new JButton();
				blocksSetBtn.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						blockSettings.refresh();
						blockSettings.setVisible(true);
					}
				});
				
				gameSetBtn = new JButton();
				gameSetBtn.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						gameSettings.refresh();
						gameSettings.setVisible(true);
					}
				});
				
				generalSetBtn = new JButton();
				generalSetBtn.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						generalSettings.refresh();
						generalSettings.setVisible(true);
					}
				});
				
				levelSetBtn = new JButton();
				levelSetBtn.addActionListener(new ActionListener(){
					@Override
					public void actionPerformed(ActionEvent e) {
						levelSettings.setVisible(true);
					}
				});
			}
		});
	}

	public static Block223 getBlock223() {
		return block223;
	}

	// forces a load from file
	public static Block223 resetBlock223() {
		return Block223Persistence.load();
	}
	
	public static void setBlock223(Block223 aBlock) {
		block223 = aBlock;
	}

	// method to get current userRole
	public static UserRole getcurrentUserRole() {
		return currentUserRole;
	}

	// sets the current user role
	public static void setCurrentUserRole(UserRole aUserRole) {
		currentUserRole = aUserRole;
	}

	// sets the current game
	public static void setCurrentGame(Game aGame) {
		currentGame = aGame;
	}

	public static Game getCurrentGame() {
		return currentGame;
	}

	/*
	 * Public methods to be called from various locations
	 * they click the respective window button, triggering 
	 * setAllInvis before setting only the requested window
	 * visible.
	 */
	public static void login() {
		setAllInvis();
		loginBtn.doClick();
	}
	
	public static void adminHome() {
		setAllInvis();
		adminHomeBtn.doClick();
	}
	
	public static void levelSettings() {
		setAllInvis();
		levelSetBtn.doClick();
	}
	
	public static void blockSettings() {
		setAllInvis();
		blocksSetBtn.doClick();
	}
	
	public static void gameSettings() {
		setAllInvis();
		gameSetBtn.doClick();
	}
	
	public static void generalSettings() {
		setAllInvis();
		generalSetBtn.doClick();
	}
	
	private static void setAllInvis() {
		for(JFrame frame : frames) 
			frame.setVisible(false);
	}
	
}
