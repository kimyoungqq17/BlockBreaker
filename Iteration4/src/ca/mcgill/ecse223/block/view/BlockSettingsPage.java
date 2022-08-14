package ca.mcgill.ecse223.block.view;

import javax.swing.JFrame;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSlider;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOBlock;
import ca.mcgill.ecse223.block.persistence.Block223Persistence;

public class BlockSettingsPage extends JFrame {
	// UI elements
	private JLabel errorMessage;
	private JLabel label_green;
	private JLabel label_red;
	private JLabel label_blue;
	private JLabel label_points;
	private JComboBox<Integer> comboBox;
	private JButton btnAddblock;
	private JButton btnDeleteBlock;
	private JSlider slider_red;
	private JSlider slider_points;
	private JSlider slider_blue;
	private JSlider slider_green;

	int r = 0, g = 0, b = 0;
	private JButton colorButton, currentBlockButton;
	private JLabel currentBlockPoints;

	private static JPanel contentPane;

	public BlockSettingsPage() {

		// Initialize the contents of the frame.

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 851, 446);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(235, 235, 186));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		colorButton = new JButton("");
		colorButton.setBounds(615, 140, 50, 50);
		colorButton.setBackground(new Color(r, g, b));
		contentPane.add(colorButton);

		// elements for error message
		errorMessage = new JLabel();
		errorMessage.setForeground(Color.RED);// TODO set bounds for error JLabel on UI
		errorMessage.setBounds(172, 34, 467, 23);
		contentPane.add(errorMessage);

		// addBlock button initialized
		btnAddblock = new JButton("AddBlock");
		btnAddblock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAddblock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Block223Controller.addBlock(r, g, b, slider_points.getValue());
					refresh();
				} catch (InvalidInputException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		btnAddblock.setBounds(715, 74, 121, 47);
		contentPane.add(btnAddblock);

		JButton btnUpdateBlock = new JButton("UpdateBlock");
		btnUpdateBlock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnUpdateBlock.setBounds(715, 149, 121, 47);
		btnUpdateBlock.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() == -1)
					JOptionPane.showMessageDialog(null, "Please choose a block to update.");
				else {
					try {
						Block223Controller.updateBlock((Integer) comboBox.getSelectedItem(), r, g, b,
								slider_points.getValue());
						refresh();
					} catch (InvalidInputException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			}

		});
		contentPane.add(btnUpdateBlock);

		// Deleteblock inititalized
		btnDeleteBlock = new JButton("Delete Block");
		btnDeleteBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() == -1)
					JOptionPane.showMessageDialog(null, "Please choose a block to delete.");
				else {
					try {
						Block223Controller.deleteBlock((Integer) comboBox.getSelectedItem());
						refresh();
					} catch (InvalidInputException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
				}
			}
		});
		btnDeleteBlock.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnDeleteBlock.setBounds(715, 295, 121, 38);
		contentPane.add(btnDeleteBlock);

		JButton btnLogout = new JButton("Logout");
		btnLogout.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnLogout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Block223Controller.logout();
			}
		});
		btnLogout.setBounds(715, 0, 121, 47);
		contentPane.add(btnLogout);

		// red slider to set value for input
		slider_red = new JSlider();
		slider_red.setPaintTicks(true);
		slider_red.setPaintLabels(true);
		slider_red.setMinorTickSpacing(50);
		slider_red.setMaximum(255);
		slider_red.setValue(0);
		slider_red.setFocusable(true);
		slider_red.setVerifyInputWhenFocusTarget(true);
		slider_red.setMajorTickSpacing(128);
		slider_red.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				label_red.setText(Integer.toString(slider_red.getValue()));
				r = slider_red.getValue();
				colorButton.setBackground(new Color(r, g, b));
			}
		});

		slider_red.setBackground(new Color(235, 235, 186));
		slider_red.setBounds(168, 65, 420, 42);
		contentPane.add(slider_red);

		// green slider to set value for input
		slider_green = new JSlider();
		slider_green.setPaintTicks(true);
		slider_green.setPaintLabels(true);
		slider_green.setMinorTickSpacing(50);
		slider_green.setMaximum(255);
		slider_green.setValue(0);
		slider_green.setFocusable(true);
		slider_green.setVerifyInputWhenFocusTarget(true);
		slider_green.setMajorTickSpacing(128);
		slider_green.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				label_green.setText(Integer.toString(slider_green.getValue()));
				g = slider_green.getValue();
				colorButton.setBackground(new Color(r, g, b));
			}
		});
		slider_green.setBackground(new Color(235, 235, 186));
		slider_green.setBounds(168, 127, 420, 38);
		contentPane.add(slider_green);

		// blue slider to set value for input
		slider_blue = new JSlider();
		slider_blue.setPaintTicks(true);
		slider_blue.setPaintLabels(true);
		slider_blue.setMinorTickSpacing(50);
		slider_blue.setMaximum(255);
		slider_blue.setValue(0);
		slider_blue.setFocusable(true);
		slider_blue.setVerifyInputWhenFocusTarget(true);
		slider_blue.setMajorTickSpacing(128);
		slider_blue.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				label_blue.setText(Integer.toString(slider_blue.getValue()));
				b = slider_blue.getValue();
				colorButton.setBackground(new Color(r, g, b));
			}
		});
		slider_blue.setBackground(new Color(235, 235, 186));
		slider_blue.setBounds(168, 181, 420, 47);
		contentPane.add(slider_blue);

		currentBlockButton = new JButton("");
		currentBlockButton.setBounds(560, 315, 50, 50);
		currentBlockButton.setBackground(new Color(0, 0, 0));
		contentPane.add(currentBlockButton);
		
		currentBlockPoints = new JLabel("");
		currentBlockPoints.setBounds(530, 315, 50, 50);
		currentBlockPoints.setBackground(new Color(0, 0, 0));
		contentPane.add(currentBlockPoints);

		comboBox = new JComboBox<Integer>();
		comboBox.setBackground(Color.WHITE);
		comboBox.setBounds(252, 326, 252, 30);
		contentPane.add(comboBox);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() != -1) {
					TOBlock block = null;
					try {
						for(TOBlock gameBlock: Block223Controller.getBlocksOfCurrentDesignableGame()) {
							if(gameBlock.getId()==((Integer) comboBox.getSelectedItem())) {
								block = gameBlock;
								break;
							}
						}
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					}
					currentBlockPoints.setText(String.valueOf(block.getPoints()));
					currentBlockButton.setBackground(new Color(block.getRed(), block.getGreen(), block.getBlue()));
				}
			}
		});

		JLabel lblRed = new JLabel("Red:");
		lblRed.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblRed.setBounds(10, 74, 49, 23);
		contentPane.add(lblRed);

		// slider points to set value of points input
		slider_points = new JSlider(0, 1000, 0);
		slider_points.setPaintTicks(true);
		slider_points.setPaintLabels(true);
		slider_points.setMinorTickSpacing(50);
		slider_points.setFocusable(true);
		slider_points.setVerifyInputWhenFocusTarget(true);
		slider_points.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				label_points.setText(Integer.toString(slider_points.getValue()));
			}
		});
		slider_points.setBackground(new Color(235, 235, 186));
		slider_points.setBounds(168, 239, 420, 47);
		contentPane.add(slider_points);
		slider_points.setMajorTickSpacing(500);

		JLabel lblGreen = new JLabel("Green:");
		lblGreen.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblGreen.setBounds(10, 131, 49, 14);
		contentPane.add(lblGreen);

		JLabel lblBlue = new JLabel("Blue:");
		lblBlue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBlue.setBounds(10, 180, 49, 14);
		contentPane.add(lblBlue);

		JLabel lblPoints = new JLabel("Points:");
		lblPoints.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPoints.setBounds(10, 233, 49, 23);
		contentPane.add(lblPoints);

		JLabel lblBlock = new JLabel("Block:");
		lblBlock.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblBlock.setBounds(151, 334, 49, 14);
		contentPane.add(lblBlock);

		JButton btnSave = new JButton("Save");
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
		btnSave.setBounds(10, 359, 100, 38);
		contentPane.add(btnSave);

		JButton btnHome = new JButton("Home");
		btnHome.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnHome.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Block223Application.adminHome();
			}
		});
		btnHome.setBounds(0, 0, 110, 30);
		contentPane.add(btnHome);

		JButton btnSettings = new JButton("Settings");
		btnSettings.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Block223Application.generalSettings();
			}
		});
		btnSettings.setBounds(0, 30, 110, 33);
		contentPane.add(btnSettings);

		JLabel lblBlockSettings = new JLabel("       Block Settings");
		lblBlockSettings.setForeground(Color.RED);
		lblBlockSettings.setFont(new Font("Tahoma", Font.PLAIN, 17));
		lblBlockSettings.setBounds(254, 0, 296, 23);
		contentPane.add(lblBlockSettings);

		label_points = new JLabel("0");
		label_points.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_points.setBounds(69, 233, 41, 23);
		contentPane.add(label_points);

		label_blue = new JLabel("0");
		label_blue.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_blue.setBounds(67, 180, 41, 19);
		contentPane.add(label_blue);

		label_green = new JLabel("0");
		label_green.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_green.setBounds(69, 127, 46, 22);
		contentPane.add(label_green);

		label_red = new JLabel("0");
		label_red.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_red.setBounds(69, 74, 41, 23);
		contentPane.add(label_red);

		setResizable(false);
		setLocationRelativeTo(null);
	}

	public void refresh() {
		comboBox.removeAllItems();
		comboBox.setSelectedIndex(-1);
		try {
			for(TOBlock block: Block223Controller.getBlocksOfCurrentDesignableGame()) {//for (Block block : Block223Application.getCurrentGame().getBlocks()) {
				comboBox.addItem(block.getId());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(null, e1.getMessage());
		}
	}
}