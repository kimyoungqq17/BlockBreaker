package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;
import ca.mcgill.ecse223.block.controller.TOHallOfFame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class HallOfFamePage extends JFrame {

	/**
	 * 
	 */
//	private static final long serialVersionUID = 8525949708196417668L;
	private JPanel contentPane;
	private JTextField textFieldPos1;
	private JTextField textFieldPos2;
	private JTextField textFieldPos3;
	private JTextField textFieldPos4;
	private JTextField textFieldPos5;
	private JTextField textFieldName1;
	private JTextField textFieldName2;
	private JTextField textFieldName3;
	private JTextField textFieldName4;
	private JTextField textFieldName5;
	private JTextField textFieldScore1;
	private JTextField textFieldScore2;
	private JTextField textFieldScore3;
	private JTextField textFieldScore4;
	private JTextField textFieldScore5;

	//variables which change depending on the index of hall of fame entries we want to view
	int pos1 = 1;
	int pos2 = pos1+1;
	int pos3 = pos1+2;
	int pos4 = pos1+3;
	int pos5 = pos1+4;		
	String spos1 = Integer.toString(pos1);
	String spos2 = Integer.toString(pos2);
	String spos3 = Integer.toString(pos3);
	String spos4 = Integer.toString(pos4);
	String spos5 = Integer.toString(pos5);
	
	TOHallOfFame hof = Block223Controller.getHallOfFame(pos1, pos5); //gets hall of fame with entries
	String name1 = hof.getEntry(pos1).getPlayername();
	String name2 = hof.getEntry(pos2).getPlayername();
	String name3 = hof.getEntry(pos3).getPlayername();
	String name4 = hof.getEntry(pos4).getPlayername();
	String name5 = hof.getEntry(pos5).getPlayername();
	int score1 = hof.getEntry(pos1).getScore();	
	int score2 = hof.getEntry(pos2).getScore();	
	int score3 = hof.getEntry(pos3).getScore();	
	int score4 = hof.getEntry(pos4).getScore();	
	int score5 = hof.getEntry(pos5).getScore();	
	String sscore1 = Integer.toString(score1);
	String sscore2 = Integer.toString(score2);
	String sscore3 = Integer.toString(score3);
	String sscore4 = Integer.toString(score4);
	String sscore5 = Integer.toString(score5);
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					HallOfFamePage frame = new HallOfFamePage();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public int increasePos1Index() {
		this.pos1 = (this.pos1+1);
		return this.pos1;
	}
	public int decreasePos1Index() {
		this.pos1 = (this.pos1-1);
		return this.pos1;
	}
	
	public void refresh() {
		this.textFieldPos1.setText("");
		this.textFieldPos2.setText("");
		this.textFieldPos3.setText("");
		this.textFieldPos4.setText("");
		this.textFieldPos5.setText("");
		this.textFieldName1.setText("");
		this.textFieldName2.setText("");
		this.textFieldName3.setText("");
		this.textFieldName4.setText("");
		this.textFieldName5.setText("");
		this.textFieldScore1.setText("");
		this.textFieldScore2.setText("");
		this.textFieldScore3.setText("");
		this.textFieldScore4.setText("");
		this.textFieldScore5.setText("");
		this.pos1 = 1;
	}
	
	/**
	 * Create the frame.
	 * @throws InvalidInputException 
	 */
	public HallOfFamePage() throws InvalidInputException {		
			
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(235, 235, 186));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel HallOfFamePageTitle = new JLabel("Hall of Fame for GAMENAME");
		HallOfFamePageTitle.setFont(new Font("Tahoma", Font.BOLD, 24));
		HallOfFamePageTitle.setHorizontalAlignment(SwingConstants.CENTER);
		HallOfFamePageTitle.setBounds(204, 10, 361, 65);
		contentPane.add(HallOfFamePageTitle);
		
		textFieldPos1 = new JTextField();
		textFieldPos1.setBounds(139, 85, 55, 43);
		textFieldPos1.setColumns(10);
		textFieldPos1.setText(spos1);
		contentPane.add(textFieldPos1);
		
		textFieldPos2 = new JTextField();
		textFieldPos2.setColumns(10);
		textFieldPos2.setBounds(139, 138, 55, 43);
		textFieldPos2.setText(spos2);
		contentPane.add(textFieldPos2);
		
		textFieldPos3 = new JTextField();
		textFieldPos3.setColumns(10);
		textFieldPos3.setBounds(139, 191, 55, 43);
		textFieldPos3.setText(spos3);
		contentPane.add(textFieldPos3);
		
		textFieldPos4 = new JTextField();
		textFieldPos4.setColumns(10);
		textFieldPos4.setBounds(139, 244, 55, 43);
		textFieldPos4.setText(spos4);
		contentPane.add(textFieldPos4);
		
		textFieldPos5 = new JTextField();
		textFieldPos5.setColumns(10);
		textFieldPos5.setBounds(139, 297, 55, 43);
		textFieldPos5.setText(spos5);
		contentPane.add(textFieldPos5);
		
		textFieldName1 = new JTextField();
		textFieldName1.setColumns(10);
		textFieldName1.setBounds(204, 85, 361, 43);
		textFieldName1.setText(name1);
		contentPane.add(textFieldName1);
		
		textFieldName2 = new JTextField();
		textFieldName2.setColumns(10);
		textFieldName2.setBounds(204, 138, 361, 43);
		textFieldName2.setText(name2);
		contentPane.add(textFieldName2);
		
		textFieldName3 = new JTextField();
		textFieldName3.setColumns(10);
		textFieldName3.setBounds(204, 191, 361, 43);
		textFieldName3.setText(name3);
		contentPane.add(textFieldName3);
		
		textFieldName4 = new JTextField();
		textFieldName4.setColumns(10);
		textFieldName4.setBounds(204, 244, 361, 43);
		textFieldName4.setText(name4);
		contentPane.add(textFieldName4);
		
		textFieldName5 = new JTextField();
		textFieldName5.setColumns(10);
		textFieldName5.setBounds(204, 297, 361, 43);
		textFieldName5.setText(name5);
		contentPane.add(textFieldName5);
		
		textFieldScore1 = new JTextField();
		textFieldScore1.setColumns(10);
		textFieldScore1.setBounds(575, 85, 176, 43);
		textFieldScore1.setText(sscore1);
		contentPane.add(textFieldScore1);
		
		textFieldScore2 = new JTextField();
		textFieldScore2.setColumns(10);
		textFieldScore2.setBounds(575, 138, 176, 43);
		textFieldScore2.setText(sscore2);
		contentPane.add(textFieldScore2);
		
		textFieldScore3 = new JTextField();
		textFieldScore3.setColumns(10);
		textFieldScore3.setBounds(575, 191, 176, 43);
		textFieldScore3.setText(sscore3);
		contentPane.add(textFieldScore3);
		
		textFieldScore4 = new JTextField();
		textFieldScore4.setColumns(10);
		textFieldScore4.setBounds(575, 244, 176, 43);
		textFieldScore4.setText(sscore4);
		contentPane.add(textFieldScore4);
		
		textFieldScore5 = new JTextField();
		textFieldScore5.setColumns(10);
		textFieldScore5.setBounds(575, 297, 176, 43);
		textFieldScore5.setText(sscore5);
		contentPane.add(textFieldScore5);
		
		JButton btnLowerScores = new JButton("Lower Scores");
		btnLowerScores.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnLowerScores.setBounds(390, 350, 175, 43);
		btnLowerScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					increasePos1Index();
					Block223Controller.getHallOfFame(pos1,pos5);
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "No lower scores available to view.");
				}				
			}
		});
		contentPane.add(btnLowerScores);

		
		JButton btnHigherScores = new JButton("Higher Scores");
		btnHigherScores.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnHigherScores.setBounds(204, 350, 176, 43);
		btnHigherScores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					decreasePos1Index();
					Block223Controller.getHallOfFame(pos1,pos5);
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "No higher scores available to view.");
				}				
			}
		});
		contentPane.add(btnHigherScores);
		
		JButton btnReturnToMenu = new JButton("Return to Menu");
		btnReturnToMenu.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnReturnToMenu.setBounds(575, 350, 176, 43);
		btnReturnToMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					Block223Application.chooseGame();
				}catch(Exception e1) {
					JOptionPane.showMessageDialog(null, "Error Returning to menu. Please Restart the app.");
				}
			}
		});
		contentPane.add(btnReturnToMenu);
		
	}
}