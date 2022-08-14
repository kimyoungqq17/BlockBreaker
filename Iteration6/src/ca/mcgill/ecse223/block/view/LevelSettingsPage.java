package ca.mcgill.ecse223.block.view;

import javax.swing.JFrame;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;
import ca.mcgill.ecse223.block.controller.TOGridCell;
import ca.mcgill.ecse223.block.model.Block;
import ca.mcgill.ecse223.block.model.BlockAssignment;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;


public class LevelSettingsPage extends JFrame {
	private JComboBox<String> comboBox_availableblocks;

	private JButton btnLogout;
	private JButton btnHome;
	private JButton btnSettings;
	//placeblock
	private JButton btnPlaceblock;


	private JButton btnMoveblock;
	private JButton btnSave;
	private JButton btnRemoveblock, btnChooseLevel;

	// private HashMap<Integer, String> availableblocks;
	private HashMap<Integer, TOBlock> blocksingame;

	private static JPanel contentPane;
	private JLabel label_xposition;
	private JLabel label_yposition;

	private JTextField textField_newyposition;
	private JTextField textField_newxposition;
	private JTextField textField_inputlevel;

	private int currentLevel = -1;

	private HashMap<Integer, TOGridCell> blocksinlevel;
	private JComboBox<String> comboBox_blocksinlvl;

	public LevelSettingsPage() {


		// Initialize the contents of the frame.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(235, 235, 186));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		comboBox_blocksinlvl = new JComboBox<String>();
		comboBox_blocksinlvl.setBounds(152, 176, 160, 22);
		contentPane.add(comboBox_blocksinlvl);

		comboBox_availableblocks = new JComboBox<String>();
		comboBox_availableblocks.setBounds(152, 134, 160, 22);
		contentPane.add(comboBox_availableblocks);

		btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Block223Controller.logout();
				Block223Application.login();
			}
		});
		btnLogout.setBounds(352, 0, 92, 23);
		contentPane.add(btnLogout);

		btnHome = new JButton("Home");
		btnHome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Block223Application.adminHome();
			}
		});
		btnHome.setBounds(0, 0, 89, 23);
		contentPane.add(btnHome);

		btnSettings = new JButton("Back");
		btnSettings.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Block223Application.generalSettings();
			}
		});
		btnSettings.setBounds(0, 28, 89, 22);
		contentPane.add(btnSettings);

		JLabel lblEnterLevel = new JLabel("Enter Level no:");
		lblEnterLevel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblEnterLevel.setBounds(25, 76, 174, 21);
		contentPane.add(lblEnterLevel);

		JLabel lblChooseBlock = new JLabel("Blocks in Game:");
		lblChooseBlock.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblChooseBlock.setBounds(25, 127, 160, 31);
		contentPane.add(lblChooseBlock);

		JLabel lblBlocksAtLevel = new JLabel("Blocks at Level#:");
		lblBlocksAtLevel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblBlocksAtLevel.setBounds(25, 174, 160, 24);
		contentPane.add(lblBlocksAtLevel);

		btnRemoveblock = new JButton("RemoveBlock");
		btnRemoveblock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRemoveblock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// TODO
				if (comboBox_blocksinlvl.getSelectedIndex() == -1)
					JOptionPane.showMessageDialog(null, "Please choose a block to remove.");

				else if (currentLevel == -1)
					JOptionPane.showMessageDialog(null, "Please choose a level to edit.");

				else {
					String blockInfo = (String) comboBox_blocksinlvl.getSelectedItem(), blockX = "", blockY = "";
					int i = 1;
					while (true) {
						if (blockInfo.charAt(i) != ',') {
							blockX += blockInfo.charAt(i);
							i++;
						} else
							break;
					}
					i += 2;
					while (true) {
						if (blockInfo.charAt(i) != ')') {
							blockY += blockInfo.charAt(i);
							i++;
						} else
							break;
					}
					try {
						Block223Controller.removeBlock(currentLevel, Integer.parseInt(blockX),
								Integer.parseInt(blockY));

						refresh();
					} catch (InvalidInputException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			}
		});

		btnRemoveblock.setBounds(316, 175, 128, 23);

		contentPane.add(btnRemoveblock);

		btnPlaceblock = new JButton("PlaceBlock");
		btnPlaceblock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPlaceblock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (comboBox_availableblocks.getSelectedIndex() == -1)
					JOptionPane.showMessageDialog(null, "Please choose a block to position.");

				else if (currentLevel == -1)
					JOptionPane.showMessageDialog(null, "Please choose a level to edit.");

				else {
					int selectedTOBlock = comboBox_availableblocks.getSelectedIndex();

					int newhorizposition = 0;
					try {
						newhorizposition = Integer.parseInt(textField_newxposition.getText());
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "PositionX needs to be a numerical value! ");
					}
					int newvertposition = 0;
					try {
						newvertposition = Integer.parseInt(textField_newyposition.getText());
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "PositionY needs to be a numerical value! ");
					}
					try {
						String blockInfo = (String) comboBox_availableblocks.getSelectedItem(), blockId = "";
						int i = 9;
						while (true) {
							if (blockInfo.charAt(i) != '.') {
								blockId += blockInfo.charAt(i);
								i++;
							} else
								break;
						}
						Block223Controller.positionBlock(Integer.parseInt(blockId), currentLevel, newhorizposition,
								newvertposition);
						;
						refresh();
						JOptionPane.showMessageDialog(null, "Blocked placed successfully!");

					} catch (InvalidInputException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			}
		});
		btnPlaceblock.setBounds(88, 368, 111, 23);
		contentPane.add(btnPlaceblock);

		btnMoveblock = new JButton("MoveBlock");
		btnMoveblock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnMoveblock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				if (comboBox_blocksinlvl.getSelectedIndex() == -1)
					JOptionPane.showMessageDialog(null, "Please choose a block to move.");
				else {
					int selectedTOGrid = comboBox_blocksinlvl.getSelectedIndex();

					int newhorizposition = 0;
					try {
						newhorizposition = Integer.parseInt(textField_newxposition.getText());
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "PositionX needs to be a numerical value! ");

					}
					int newvertposition = 0;
					try {
						newvertposition = Integer.parseInt(textField_newyposition.getText());

					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "PositionY needs to be a numerical value! ");
					}
					String blockInfo = (String) comboBox_blocksinlvl.getSelectedItem(), blockX = "", blockY = "";
					int i = 1;
					while (true) {
						if (blockInfo.charAt(i) != ',') {
							blockX += blockInfo.charAt(i);
							i++;
						} else
							break;
					}
					i += 2;
					while (true) {
						if (blockInfo.charAt(i) != ')') {
							blockY += blockInfo.charAt(i);
							i++;
						} else
							break;
					}
					try {
						Block223Controller.moveBlock(currentLevel, Integer.parseInt(blockX), Integer.parseInt(blockY),
								newhorizposition, newvertposition);
						refresh();
						JOptionPane.showMessageDialog(null, "Blocked moved successfully!");
					} catch (InvalidInputException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			}
		});
		btnMoveblock.setBounds(260, 368, 115, 23);
		contentPane.add(btnMoveblock);

		JLabel lblHorizontalposition = new JLabel("newHorizontalPosition:");
		lblHorizontalposition.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblHorizontalposition.setBounds(132, 209, 189, 21);
		contentPane.add(lblHorizontalposition);

		JLabel lblVerticalposition = new JLabel("newVerticalPosition:");
		lblVerticalposition.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblVerticalposition.setBounds(146, 279, 175, 21);
		contentPane.add(lblVerticalposition);

		btnSave = new JButton("Save");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Block223Persistence.save();
					refresh();
					JOptionPane.showMessageDialog(null, "Game saved successfully!");
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		btnSave.setBounds(0, 416, 89, 23);
		contentPane.add(btnSave);

		JLabel lblLevelSettings = new JLabel("          Level Settings");
		lblLevelSettings.setForeground(Color.RED);
		lblLevelSettings.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblLevelSettings.setBounds(118, 4, 185, 31);
		contentPane.add(lblLevelSettings);

		label_xposition = new JLabel("");
		label_xposition.setBounds(162, 167, 49, 14);
		contentPane.add(label_xposition);

		label_yposition = new JLabel("");
		label_yposition.setBounds(241, 167, 49, 14);
		contentPane.add(label_yposition);

		textField_inputlevel = new JTextField();
		textField_inputlevel.setBounds(175, 79, 96, 20);
		contentPane.add(textField_inputlevel);
		textField_inputlevel.setColumns(10);

		btnChooseLevel = new JButton("Edit Level");
		btnChooseLevel.setBounds(316, 77, 115, 23);
		btnChooseLevel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		contentPane.add(btnChooseLevel);
		btnChooseLevel.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				try {
					Integer.parseInt(textField_inputlevel.getText());
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(null, "Please input an integer value");
					return;
				}
				if (!textField_inputlevel.getText().isEmpty()
						&& !(Integer.parseInt(textField_inputlevel.getText()) > Block223Application.getCurrentGame()
								.numberOfLevels())
						&& Integer.parseInt(textField_inputlevel.getText()) > 0) {

					currentLevel = Integer.parseInt(textField_inputlevel.getText());

					refresh();
				} else
					JOptionPane.showMessageDialog(null, "Please choose a level between 1 and "
							+ Block223Application.getCurrentGame().numberOfLevels());
			}
		});

		textField_newyposition = new JTextField();
		textField_newyposition.setBounds(175, 241, 96, 20);
		contentPane.add(textField_newyposition);
		textField_newyposition.setColumns(10);

		textField_newxposition = new JTextField();
		textField_newxposition.setBounds(175, 311, 96, 20);
		contentPane.add(textField_newxposition);
		textField_newxposition.setColumns(10);

		setLocationRelativeTo(null);
		setResizable(false);

	}

	public void refresh() {
		// TODO

		// Chooseblockingame
		Integer index = 0;
		blocksingame = new HashMap<Integer, TOBlock>();
		comboBox_availableblocks.removeAllItems();
		comboBox_availableblocks.setSelectedIndex(-1);
		for (Block block : Block223Application.getCurrentGame().getBlocks()) {
			comboBox_availableblocks.addItem("Block #: " + block.getId() + ". Points: " + block.getPoints());
		}

		// Chooseblockinlevel

		comboBox_blocksinlvl.removeAllItems();
		comboBox_blocksinlvl.setSelectedIndex(-1);
		if (currentLevel > 0) {
			for (BlockAssignment block : Block223Application.getCurrentGame().getLevel(currentLevel - 1)
					.getBlockAssignments()) {
				comboBox_blocksinlvl.addItem("(" + block.getGridHorizontalPosition() + ", "
						+ block.getGridVerticalPosition() + ") : Block " + block.getBlock().getId());
			}
		}

	}

}