package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.model.Admin;
import ca.mcgill.ecse223.block.model.Game;

public class AdminHomePage extends JFrame {

	private static JTextField gameName;
	private static JComboBox<String> comboBox;

	private static JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public AdminHomePage() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 851, 446);
		contentPane = new JPanel();
		//slightly off beige color
		contentPane.setBackground(new Color(235, 235, 186));
		//I honestly have no idea if this does anything
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel adminLoginHead = new JLabel("Welcome to the Admin Page!");
		adminLoginHead.setFont(new Font("Tahoma", Font.PLAIN, 35));
		adminLoginHead.setBounds(200, 25, 705, 50);
		contentPane.add(adminLoginHead);

		JButton adminLogoutButton = new JButton("Logout");
		adminLogoutButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		adminLogoutButton.setBounds(725, 10, 111, 50);
		contentPane.add(adminLogoutButton);
		adminLogoutButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Block223Controller.logout();
				Block223Application.login();
			}
		});

		JButton createGameBtn = new JButton("Create Game");
		createGameBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		createGameBtn.setBounds(105, 138, 173, 41);
		contentPane.add(createGameBtn);

		gameName = new JTextField();
		gameName.setBounds(288, 140, 438, 36);
		contentPane.add(gameName);
		gameName.setColumns(10);

		createGameBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//need to make sure name value isn't empty, creates a game otherwise
				if (!gameName.getText().isEmpty()) {
					try {
						Block223Controller.createGame(gameName.getText());
						Block223Application.generalSettings();
					} catch (InvalidInputException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
				else
					JOptionPane.showMessageDialog(null, "Please input a non empty name.");
			}
		});

		JButton updateGameBtn = new JButton("Update Game");
		updateGameBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		updateGameBtn.setBounds(105, 260, 173, 41);
		contentPane.add(updateGameBtn);

		comboBox = new JComboBox<String>();
		comboBox.setBounds(288, 262, 438, 37);
		contentPane.add(comboBox);

		updateGameBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					//make sure a value is selected from the comboBox
					if (comboBox.getSelectedIndex() != -1) {
						Game game = null;
						for (Game aGame : Block223Application.getBlock223().getGames()) {
							if (aGame.getName().equals(comboBox.getSelectedItem()))
								game = aGame;
						}
						Block223Controller.selectGame(game.getName());
						Block223Application.generalSettings();
					} else
						JOptionPane.showMessageDialog(null, "Please select a game.");
				} catch (InvalidInputException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		
		//these disallow resizability (since our window elements are fixed)
		//and place the windows in the middle of the screen on creation
		setLocationRelativeTo(null);
		setResizable(false);
	}

	//refreshes the comboBox (to make sure duplicate games aren't added and
	//added/deleted games do/don't show up
	public void refresh() {

		gameName.setText("");
		comboBox.removeAllItems();
		comboBox.setSelectedIndex(-1);
		for (Game game : ((Admin) Block223Application.getCurrentUserRole()).getGames()) {
			comboBox.addItem(game.getName());
		}
	}
}