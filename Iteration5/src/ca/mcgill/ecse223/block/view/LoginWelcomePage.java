package ca.mcgill.ecse223.block.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;

public class LoginWelcomePage extends JFrame {
	
	private JPanel contentPane;
	private JTextField loginUser, createUser;
	private JPasswordField loginPassword, createPlayerPassword, createAdminPassword;
	
	/**
	 * Initialize the contents of the frame.
	 */
	public LoginWelcomePage() {
		contentPane = new JPanel();
		//off beige color
		contentPane.setBackground(new Color(235 ,235, 186));
		//still don't know what this does
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setBounds(100, 100, 801, 452);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		//Title label
		JLabel lblWelcomeToBlock = new JLabel("Welcome to Group22's Block223!");
		lblWelcomeToBlock.setBounds(0, 35, 788, 49);
		lblWelcomeToBlock.setHorizontalAlignment(SwingConstants.CENTER);
		lblWelcomeToBlock.setFont(new Font("Tahoma", Font.PLAIN, 40));
		contentPane.add(lblWelcomeToBlock);
		
		//Login label
		JLabel lblLoginAsUser = new JLabel("Login As User or Admin");
		lblLoginAsUser.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblLoginAsUser.setBounds(35, 94, 274, 49);
		contentPane.add(lblLoginAsUser);
		
		//User create label
		JLabel lblCreateUser = new JLabel("Create User");
		lblCreateUser.setFont(new Font("Tahoma", Font.PLAIN, 25));
		lblCreateUser.setBounds(547, 94, 159, 49);
		contentPane.add(lblCreateUser);
		
		//Username field for login
		loginUser = new JTextField();
		loginUser.setBounds(35, 153, 266, 45);
		contentPane.add(loginUser);
		loginUser.setColumns(10);

		loginPassword = new JPasswordField();
		loginPassword.setBounds(35, 208, 266, 41);
		contentPane.add(loginPassword);		
		
		//when the user clicks the login button
		JButton btnNewButton = new JButton("Login");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String username = "", password = "";
				try {
					username = loginUser.getText();
					password = String.valueOf(loginPassword.getPassword());
					
					//resets the password to an empty string (mimics most apps nowadays)
					loginPassword.setText("");
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				
				try {
					Block223Controller.login(username, password);
				} catch (InvalidInputException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
			}
		});
		btnNewButton.setBounds(35, 259, 266, 49);
		contentPane.add(btnNewButton);

		JLabel lblUsername = new JLabel("<-Username->");
		lblUsername.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblUsername.setBounds(330, 155, 138, 34);
		contentPane.add(lblUsername);
		
		JLabel lblPassword = new JLabel("<-Password->");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblPassword.setBounds(330, 205, 132, 34);
		contentPane.add(lblPassword);
		
		JLabel lblAdminPassword = new JLabel("AdminPW (optional)->");
		lblAdminPassword.setFont(new Font("Tahoma", Font.PLAIN, 18));
		lblAdminPassword.setBounds(310, 260, 200, 34);
		contentPane.add(lblAdminPassword);
		
		//Username field for registration
		createUser = new JTextField();
		createUser.setColumns(10);
		createUser.setBounds(494, 153, 259, 41);
		contentPane.add(createUser);
		
		createPlayerPassword = new JPasswordField();
		createPlayerPassword.setBounds(494, 208, 259, 41);
		contentPane.add(createPlayerPassword);
		
		createAdminPassword = new JPasswordField();
		createAdminPassword.setBounds(494, 259, 259, 41);
		contentPane.add(createAdminPassword);
		
		JButton createUserBtn = new JButton("Create User");
		createUserBtn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		createUserBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String username = "", playerPassword = "", adminPassword = "";
				try {
					username = createUser.getText();
					playerPassword = String.valueOf(createPlayerPassword.getPassword());
					adminPassword = String.copyValueOf(createAdminPassword.getPassword());
					
					createPlayerPassword.setText("");
					createAdminPassword.setText("");
				} catch (NullPointerException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				
				try {
					Block223Controller.register(username, playerPassword, adminPassword);
				} catch (InvalidInputException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				
			}
		});
		createUserBtn.setBounds(548, 310, 145, 49);
		contentPane.add(createUserBtn);
		setLocationRelativeTo(null);
		setResizable(false);
	}
	
	public void refresh() {
		createUser.setText("");
		loginUser.setText("");
	}
}