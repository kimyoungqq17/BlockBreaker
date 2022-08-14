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
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

public class GeneralSettingsPage extends JFrame {

	JLabel lblGame;

	public GeneralSettingsPage() {
		JPanel contentPane = new JPanel();
		contentPane.setBackground(new Color(235, 235, 186));
		setBounds(100, 100, 300, 550);
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
		btnDelete.setBounds(50, 440, 200, 40);
		contentPane.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int a = JOptionPane.showConfirmDialog(contentPane, "Are you sure?");
				if(a==JOptionPane.YES_OPTION) {
					try {
						Block223Controller.deleteGame(Block223Application.getCurrentGame().getName());
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
				int a = JOptionPane.showConfirmDialog(contentPane, "Do you want to save this game? Going back will delete it.");
				if(a==JOptionPane.YES_OPTION) {
					Block223Persistence.save();
					// goes to admin home page
					Block223Application.adminHome();
				}
				else if(a==JOptionPane.NO_OPTION) {
					Block223Application.getCurrentGame().delete();
					// goes to admin home page
					Block223Application.adminHome();
				}
			}
		});
		btnHome.setBounds(5, 5, 110, 30);
		contentPane.add(btnHome);

		setLocationRelativeTo(null);
		setResizable(false);
	}
	public void refresh() {
		lblGame.setText("Game: "+Block223Application.getCurrentGame().getName());
	}
}