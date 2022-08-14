package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;

import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.model.UserRole;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

public class GeneralSettingsPage extends JFrame {

	JLabel lblGame;

	public GeneralSettingsPage() {
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(235, 235, 186));
		setBounds(100, 100, 300, 700);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JButton btnGameSettings = new JButton("Game Settings");
		btnGameSettings.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnGameSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// goes to game settings page
				Block223Application.gameSettings();
			}
		});
		btnGameSettings.setBounds(50, 200, 200, 40);
		contentPane.add(btnGameSettings);

		JButton btnBlockSettings = new JButton("Block Settings");
		btnBlockSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// goes to block settings page
				Block223Application.blockSettings();
			}
		});
		btnBlockSettings.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnBlockSettings.setBounds(50, 265, 200, 40);
		contentPane.add(btnBlockSettings);

		JButton btnLevelSettings = new JButton("Level Settings");
		btnLevelSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// goes to level settings page
				Block223Application.levelSettings();
			}
		});
		btnLevelSettings.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLevelSettings.setBounds(50, 330, 200, 40);
		contentPane.add(btnLevelSettings);

		JButton btnDelete = new JButton("DELETE GAME");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnDelete.setBounds(50, 570, 200, 40);
		contentPane.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(contentPane, "Are you sure?");
				if (a == JOptionPane.YES_OPTION) {
					try {
						Block223Controller.deleteGame(Block223Application.getCurrentGame().getName());
						Block223Application.adminHome();
					} catch (InvalidInputException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			}

		});

		JLabel lblSettings = new JLabel("Choose an Option");
		lblSettings.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblSettings.setHorizontalAlignment(SwingConstants.CENTER);
		lblSettings.setBounds(50, 75, 200, 40);
		contentPane.add(lblSettings);

		lblGame = new JLabel("");
		lblGame.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGame.setHorizontalAlignment(SwingConstants.CENTER);
		lblGame.setBounds(20, 115, 260, 40);
		contentPane.add(lblGame);

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
		btnLogOut.setBounds(180, 5, 110, 30);
		contentPane.add(btnLogOut);

		JButton btnHome = new JButton("Back");
		btnHome.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int a = JOptionPane.showConfirmDialog(contentPane,
						"Do you want to save this game? Choosing no will delete it.");
				if (a == JOptionPane.YES_OPTION) {
					Block223Persistence.save();
					// goes to admin home page
					Block223Application.adminHome();
				} else if (a == JOptionPane.NO_OPTION) {

					Block223Application.getCurrentGame().delete();
					// goes to admin home page
					Block223Application.adminHome();
				}
			}
		});
		btnHome.setBounds(5, 5, 110, 30);
		contentPane.add(btnHome);

		JButton publishBtn = new JButton("Publish Game");
		publishBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		publishBtn.setBounds(50, 395, 200, 40);
		contentPane.add(publishBtn);

		publishBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Block223Controller.publishGame();
					JOptionPane.showMessageDialog(null, "Game published successfully!");
				} catch (InvalidInputException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});

		JButton testBtn = new JButton("Test Game");
		testBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		testBtn.setBounds(50, 460, 200, 40);
		contentPane.add(testBtn);

		testBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					User currentUser = null;
					for (User user : Block223Application.getBlock223().getUsers()) {
						for (UserRole ur : user.getRoles()) {
							if (ur.equals(Block223Application.getCurrentUserRole())) {
								currentUser = user;
							}
						}
					}
					Block223Application.setCurrentPlayableGame(new PlayedGame(currentUser.getUsername(),
										Block223Application.getCurrentGame(), Block223Application.getBlock223()));
					Block223Controller.testGame(Block223Application.getPlayWindow());
				} catch (InvalidInputException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});

		setLocationRelativeTo(null);
		setResizable(false);
	}

	public void refresh() {
		lblGame.setText("Game: " + Block223Application.getCurrentGame().getName());
	}
}