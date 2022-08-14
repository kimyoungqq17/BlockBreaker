package ca.mcgill.ecse223.block.view;
import java.awt.EventQueue;

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
import ca.mcgill.ecse223.block.model.Game;
import ca.mcgill.ecse223.block.model.Level;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;

public class LevelSettingsPage extends JFrame{
	private JComboBox comboBox_block;
	private JButton btnLogout;
	private JButton btnHome;
	private JButton btnSettings;
	private JButton btnPlaceblock;
	private JButton btnMoveblock;
	private JButton btnSave; 
	private JButton btnRemoveblock;
	
	private HashMap<Integer, String> availableblocks;
	
	private static JPanel contentPane;
	private JLabel label_xposition;
	private JLabel label_yposition;
	private JTextField textField_inputlevel;
	private JTextField textField_newyposition;
	private JTextField textField_newxposition;
	private TOGridCell griddetails;
	private int levelnumber;
	
	public LevelSettingsPage() {
	
		
		// Initialize the contents of the frame.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 500);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(235, 235, 186));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		comboBox_block = new JComboBox<String>();
		comboBox_block.setBounds(162, 134, 128, 22);
		contentPane.add(comboBox_block);
		comboBox_block.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBox_block.getSelectedIndex() != -1) {
						griddetails = null;
					try {
						for (TOGridCell blockdetails : Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(levelnumber)) {
							if(blockdetails.getId()==((Integer) comboBox_block.getSelectedItem())) {
								griddetails = blockdetails;
								break;
							}
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					label_xposition.setText("X: "+String.valueOf(griddetails.getGridHorizontalPosition()));
					label_yposition.setText("Y: "+String.valueOf(griddetails.getGridVerticalPosition()));
					//currentBlockButton.setBackground(new Color(block.getRed(), block.getGreen(), block.getBlue()));
				}
			}
		});

		
		btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Block223Controller.logout();
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
		
		btnSettings = new JButton("Settings");
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
		
		JLabel lblChooseBlock = new JLabel("Choose block:");
		lblChooseBlock.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblChooseBlock.setBounds(25, 132, 160, 21);
		contentPane.add(lblChooseBlock);
		
		btnRemoveblock = new JButton("RemoveBlock");
		btnRemoveblock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRemoveblock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		btnRemoveblock.setBounds(319, 132, 128, 23);
		contentPane.add(btnRemoveblock);
		
		btnPlaceblock = new JButton("PlaceBlock");
		btnPlaceblock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnPlaceblock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox_block.getSelectedIndex() == -1)
					JOptionPane.showMessageDialog(null, "Please choose a block to position.");
				else {
					try {
						Block223Controller.positionBlock(griddetails.getId(),Integer.parseInt(textField_inputlevel.getText()), Integer.parseInt(textField_newxposition.getText()), Integer.parseInt(textField_newyposition.getText()));
						refresh();
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
				//TODO
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
		
		textField_newyposition = new JTextField();
		textField_newyposition.setBounds(175, 241, 96, 20);
		contentPane.add(textField_newyposition);
		textField_newyposition.setColumns(10);
		
		textField_newxposition = new JTextField();
		textField_newxposition.setBounds(175, 311, 96, 20);
		contentPane.add(textField_newxposition);
		textField_newxposition.setColumns(10);
	}
	

	  public void refresh() {
		// TODO
		  Integer index = 0;
		  availableblocks = new HashMap<Integer, String>();
		  comboBox_block.removeAllItems();
		  index = 0;
		  	levelnumber =Integer.parseInt(textField_inputlevel.getText());
			try {
				for (TOGridCell blockdetails : Block223Controller.getBlocksAtLevelOfCurrentDesignableGame(levelnumber)) {
					String details = "#"+blockdetails.getId()+ "red"+blockdetails.getRed()+" blue:"+blockdetails.getBlue()+" green:"+blockdetails.getGreen();
					availableblocks.put(index,details );
					comboBox_block.addItem(details);
					index++;
				}
			} catch (Exception e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			comboBox_block.setSelectedIndex(-1);
			
	  }
	 
}