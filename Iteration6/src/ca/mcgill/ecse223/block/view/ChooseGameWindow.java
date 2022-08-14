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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOPlayableGame;
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.PlayedGame;
import ca.mcgill.ecse223.block.model.User;
import ca.mcgill.ecse223.block.model.UserRole;

public class ChooseGameWindow extends JFrame {

	private static JPanel contentPane;
	private static JButton btnLogOut, chooseGameBtn;
	private static JComboBox<String> gameWheel;

	public ChooseGameWindow() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 300, 450);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(235, 235, 186));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		setResizable(false);
		setLocationRelativeTo(null);

		btnLogOut = new JButton("Logout");
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

		gameWheel = new JComboBox<String>();
		contentPane.add(gameWheel);
		gameWheel.setBounds(20, 200, 260, 40);
		contentPane.add(gameWheel);

		chooseGameBtn = new JButton("Play Game");
		chooseGameBtn.setBounds(20, 115, 260, 40);
		chooseGameBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(chooseGameBtn);

		chooseGameBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					
					// make sure a value is selected from the comboBox
					if (gameWheel.getSelectedIndex() != -1) {
						Game game = null;
						for (Game aGame : Block223Application.getBlock223().getGames()) {
							if (aGame.getName().equals(gameWheel.getSelectedItem()))
								game = aGame;
						}
						User currentUser = null;
						for (User user : Block223Application.getBlock223().getUsers()) {
							for (UserRole ur : user.getRoles()) {
								if (ur.equals(Block223Application.getCurrentUserRole())) {
									currentUser = user;
								}
							}
						}
						//TODO something needs to change here. s
						Block223Application.setCurrentPlayableGame(new PlayedGame(currentUser.getUsername(),
										game, Block223Application.getBlock223()));
						Block223Controller.startGame(Block223Application.getPlayWindow());
//						Block223Application.playGame();
					} else
						JOptionPane.showMessageDialog(null, "Please select a game.");
				} catch (InvalidInputException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}

			}

		});

	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 614046117144251380L;

	public void refresh() {
		gameWheel.removeAllItems();
		gameWheel.setSelectedIndex(-1);
		for (Game game : Block223Application.getBlock223().getGames()) {
			if (game.isPublished())
				gameWheel.addItem(game.getName());
		}

	}

}
