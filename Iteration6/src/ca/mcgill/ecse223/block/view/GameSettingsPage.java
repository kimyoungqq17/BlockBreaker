package ca.mcgill.ecse223.block.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JTextField;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class GameSettingsPage extends JFrame{

	private JTextField noOfLevels;
	private JTextField minBallX;
	private JTextField minBallY;
	private JTextField ballInc;
	private JTextField maxPadL;
	private JTextField minPadL;
	private JTextField noOfBlocks;
	private JLabel lblMinBallSpeedX;
	private JLabel lblMinBallSpeedY;
	private JLabel lblBallSpeedInc;
	private JLabel lblMaxPaddleLength;
	private JLabel lblMinPaddleLength;
	private JLabel lblNumberOfBlocks;
	private JButton btnSave;
	private JLabel lblGameSetting;
	private JLabel lblInstructions;
	private JLabel lblInstructions2;
	private JLabel lblNumberOfLevels;
	
	public GameSettingsPage() {
		getContentPane().setBackground(new Color(235 ,235, 186));
		setBounds(100, 100, 360, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		
		lblGameSetting = new JLabel("Game Settings");
		lblGameSetting.setFont(new Font("Tahoma", Font.BOLD, 25));
		lblGameSetting.setBounds(85, 35, 338, 44);
		getContentPane().add(lblGameSetting);
		
		lblInstructions = new JLabel("Please input integers only :)");
		lblInstructions.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInstructions.setBounds(94, 42, 338, 80);
		getContentPane().add(lblInstructions);
		
		lblInstructions2 = new JLabel("(\"Ball Speed Increment\" can have decimals)");
		lblInstructions2.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInstructions2.setBounds(42, 58, 338, 80);
		getContentPane().add(lblInstructions2);
		
		
		lblNumberOfLevels = new JLabel("Number of Levels");
		lblNumberOfLevels.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNumberOfLevels.setBounds(40, 120, 251, 25);
		getContentPane().add(lblNumberOfLevels);
		
		noOfLevels = new JTextField();
		noOfLevels.setBounds(260, 120, 60, 25);
		getContentPane().add(noOfLevels);
		noOfLevels.setColumns(10);
		
		
		lblMinBallSpeedX = new JLabel("Min Ball Speed X");
		lblMinBallSpeedX.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblMinBallSpeedX.setBounds(40, 155, 201, 25);
		getContentPane().add(lblMinBallSpeedX);
		
		minBallX = new JTextField();
		minBallX.setBounds(260, 155, 60, 25);
		getContentPane().add(minBallX);
		minBallX.setColumns(10);
		
		
		lblMinBallSpeedY = new JLabel("Min Ball Speed Y");
		lblMinBallSpeedY.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblMinBallSpeedY.setBounds(40, 190, 163, 25);
		getContentPane().add(lblMinBallSpeedY);
		
		minBallY = new JTextField();
		minBallY.setBounds(260, 190, 60, 25);
		getContentPane().add(minBallY);
		minBallY.setColumns(10);
		
		
		lblBallSpeedInc = new JLabel("Ball Speed Increment");
		lblBallSpeedInc.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblBallSpeedInc.setBounds(40, 225, 251, 25);
		getContentPane().add(lblBallSpeedInc);
		
		ballInc = new JTextField();
		ballInc.setBounds(260, 225, 60, 25);
		getContentPane().add(ballInc);
		ballInc.setColumns(10);
		
		
		lblMinPaddleLength = new JLabel("Min Paddle Length");
		lblMinPaddleLength.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblMinPaddleLength.setBounds(40, 260, 163, 25);
		getContentPane().add(lblMinPaddleLength);
		
		minPadL = new JTextField();
		minPadL.setBounds(260, 260, 60, 25);
		getContentPane().add(minPadL);
		minPadL.setColumns(10);
		
		
		lblMaxPaddleLength = new JLabel("Max Paddle Length");
		lblMaxPaddleLength.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblMaxPaddleLength.setBounds(40, 295, 163, 25);
		getContentPane().add(lblMaxPaddleLength);
		
		maxPadL = new JTextField();
		maxPadL.setBounds(260, 295, 60, 25);
		getContentPane().add(maxPadL);
		maxPadL.setColumns(10);
		
		
		lblNumberOfBlocks = new JLabel("Blocks Per Level");
		lblNumberOfBlocks.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblNumberOfBlocks.setBounds(40, 330, 251, 25);
		getContentPane().add(lblNumberOfBlocks);
		
		noOfBlocks = new JTextField();
		noOfBlocks.setBounds(260, 330, 60, 25);
		getContentPane().add(noOfBlocks);
		noOfBlocks.setColumns(10);
		
		
		btnSave = new JButton("Save");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					Block223Controller.setGameDetails(Integer.parseInt(noOfLevels.getText()), 
							Integer.parseInt(noOfBlocks.getText()),
							Integer.parseInt(minBallX.getText()),
							Integer.parseInt(minBallY.getText()),
							Double.parseDouble(ballInc.getText()),
							Integer.parseInt(maxPadL.getText()),
							Integer.parseInt(minPadL.getText()));
					Block223Persistence.save();
					JOptionPane.showMessageDialog(null, "Game saved successfully!");
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Please input proper values");
				} catch (InvalidInputException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		btnSave.setFont(new Font("Tahoma", Font.BOLD, 20));
		btnSave.setBounds(130, 390, 100, 40);
		getContentPane().add(btnSave);
		
		JButton btnLogOut = new JButton("Logout");
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// logs out the user and goes to login welcome page
				try {
					Block223Controller.logout();
					Block223Application.login();
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		btnLogOut.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnLogOut.setBounds(240, 5, 110, 30);
		getContentPane().add(btnLogOut);
		
		JButton btnSettings = new JButton("Back");
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Block223Application.generalSettings();
			}
		});
		btnSettings.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnSettings.setBounds(5, 5, 110, 30);
		getContentPane().add(btnSettings);

		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public void refresh() {
		noOfLevels.setText(String.valueOf(Block223Application.getCurrentGame().numberOfLevels()));
		minBallX.setText(String.valueOf(Block223Application.getCurrentGame().getBall().getMinBallSpeedX()));
		minBallY.setText(String.valueOf(Block223Application.getCurrentGame().getBall().getMinBallSpeedY()));
		ballInc.setText(String.valueOf(Block223Application.getCurrentGame().getBall().getBallSpeedIncreaseFactor()));
		maxPadL.setText(String.valueOf(Block223Application.getCurrentGame().getPaddle().getMaxPaddleLength()));
		minPadL.setText(String.valueOf(Block223Application.getCurrentGame().getPaddle().getMinPaddleLength()));
		noOfBlocks.setText(String.valueOf(Block223Application.getCurrentGame().getNrBlocksPerLevel()));
	}
}