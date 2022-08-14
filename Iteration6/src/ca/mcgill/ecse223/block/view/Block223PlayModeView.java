/**
 * Start, Pause, Resume Game play is in here 
 */

package ca.mcgill.ecse223.block.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


import ca.mcgill.ecse223.block.application.Block223Application;
import ca.mcgill.ecse223.block.controller.Block223Controller;
import ca.mcgill.ecse223.block.controller.InvalidInputException;

import ca.mcgill.ecse223.block.controller.TOHallOfFameEntry;
import ca.mcgill.ecse223.block.model.Ball;
import ca.mcgill.ecse223.block.model.Paddle;
import ca.mcgill.ecse223.block.model.PlayedGame;
//import ca.mcgill.ecse223.block.view.Block223PlayModeListener;
import ca.mcgill.ecse223.block.view.Block223PlayModeView;
import ca.mcgill.ecse223.block.view.Block223PlayModeInterface;

public class Block223PlayModeView extends JFrame implements Block223PlayModeInterface, ActionListener {

	private volatile String keyString = "";
	private static final long serialVersionUID = -613534727974834342L;
	JTextArea gameArea;
	Block223PlayModeListener2 bp;
	PlayedGame playedgame = Block223Application.getCurrentPlayableGame();

	// will change by navigating through controller
	private int ballposX = 195;
	private int ballposY = 195;
	private int ballXdir = 1;
	private int ballYdir = 1;
	public int paddleX = 185;

	private BlockMapGenerator map; // call generator or blockassignments

	public Block223PlayModeView() {
		createAndShowGUI();
	}

	/**
	 * Creating GUI
	 */
	private void createAndShowGUI() {
		// Create and set up the window.

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(400, 400, 400, 400); // may need to getgamearea side here from controller

		// Add components to window pane
		addComponentsToPane();

		// add number of rows and columns here from a specific level
		map = new BlockMapGenerator(4, 5); // for now 3 rows and 7 columns

		// Display the window.
		setLocationRelativeTo(null);
		setResizable(false);
	}

	private void addComponentsToPane() {

		JButton button = new JButton("Start Game");

		gameArea = new JTextArea();
		gameArea.setEditable(false);
		JScrollPane scrollPane = new JScrollPane(gameArea);
		scrollPane.setPreferredSize(new Dimension(400, 400));

		getContentPane().add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(button, BorderLayout.PAGE_END);

		button.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {

				button.setVisible(false);
				// initiating a thread to start listening to keyboard inputs
				bp = new Block223PlayModeListener2();

				Runnable r1 = new Runnable() {
					@Override
					public void run() {
						// in the actual game, add keyListener to the game window
						gameArea.addKeyListener(bp);
					}
				};
				Thread t1 = new Thread(r1);
				t1.start();

				// to be on the safe side use join to start executing thread t1 before executing
				// the next thread
				try {
					t1.join();
				} catch (InterruptedException e1) {
				}

				// initiating a thread to start the game loop
				Runnable r2 = new Runnable() {
					@Override
					public void run() {
						try {
							Block223Controller.startGame((Block223PlayModeInterface) Block223PlayModeView.this);
							button.setVisible(true);

						} catch (InvalidInputException e) {
						}
					}
				};
				Thread t2 = new Thread(r2);
				t2.start();
			}
		});
	}

	@Override
	public String takeInputs() {
		if (bp == null) {
			return "";
		}
		return bp.takeInputs();
	}

	@Override
	public void refresh() {

		ballposX = (int) Block223Application.getCurrentPlayableGame().getCurrentBallX();
		ballposY = (int) Block223Application.getCurrentPlayableGame().getCurrentBallY();

		repaint();
	}

	@Override
	public void endGame(int nrOfLives, TOHallOfFameEntry hof) {
		// TODO Auto-generated method stub

	}

	public void paint(Graphics g) {
		// background
		g.setColor(new Color(235, 235, 186));
		g.fillRect(0, 0, 390, 390);

		// drawing map
		map.draw((Graphics2D) g);

		// the paddle
		g.setColor(Color.BLACK);
		g.fillRect((int) Block223Application.getCurrentPlayableGame().getCurrentPaddleX(),
				(int) Block223Application.getCurrentPlayableGame().getCurrentPaddleY(),
				(int) Block223Application.getCurrentPlayableGame().getCurrentPaddleLength(), Paddle.PADDLE_WIDTH);

		// the ball
		g.setColor(Color.RED);
		g.fillOval((int) Block223Application.getCurrentPlayableGame().getCurrentBallX(),
				(int) Block223Application.getCurrentPlayableGame().getCurrentBallY(), Ball.BALL_DIAMETER,
				Ball.BALL_DIAMETER); 

		g.dispose();
	}

	// added the listener here so I can test with same variables
	class Block223PlayModeListener2 implements KeyListener {

		/**
		 * 'String input from keyboard - marked as volatile since it is shared by two
		 * threads
		 */
		private volatile String keyString = "";

		@Override
		public synchronized void keyPressed(KeyEvent e) {
			try {
				keyInputs(e);
			} catch (InvalidInputException e1) {
				System.out.print(e1);
			}
		}

		private synchronized String keyInputs(KeyEvent e) throws InvalidInputException {
			int location = e.getKeyCode();
			if (location == KeyEvent.VK_LEFT) {
				keyString += "l";
				if (Block223Application.getCurrentPlayableGame().getCurrentPaddleX() < 5) {
					Block223Application.getCurrentPlayableGame().setCurrentPaddleX(5); // keep in border
				} else {
					moveLeft(); // controller implements move, right now testing to see if works
				}

			} else if (location == KeyEvent.VK_RIGHT) {
				keyString += "r";
				if (Block223Application.getCurrentPlayableGame().getCurrentPaddleX() >= 390 - Block223Application.getCurrentPlayableGame().getCurrentPaddleLength()) {
					Block223Application.getCurrentPlayableGame().setCurrentPaddleX(390 - Block223Application.getCurrentPlayableGame().getCurrentPaddleLength()); // keep
																													// in
																													// border
				} else {
					moveRight();
				}
			} else if (location == KeyEvent.VK_SPACE) {
				keyString += " ";
			} else {
				// ignore all other keys
			}
			return keyString;
		}
//will be deleted, just testing

		/**
		 * Takes key inputs and clears the input string. marked as synchronized to make
		 * the key inputs as thread-safe
		 */
		public synchronized String takeInputs() {
			String passString = keyString;
			keyString = "";
			return passString;
		}

		@Override
		public void keyReleased(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

	}

	private void moveRight() {
		Block223Application.getCurrentPlayableGame();
		Block223Application.getCurrentPlayableGame().setCurrentPaddleX(
				Block223Application.getCurrentPlayableGame().getCurrentPaddleX() + PlayedGame.PADDLE_MOVE_RIGHT * 5);
		paddleX += PlayedGame.PADDLE_MOVE_RIGHT * 5;
	}

	private void moveLeft() {
		Block223Application.getCurrentPlayableGame().setCurrentPaddleX(
				Block223Application.getCurrentPlayableGame().getCurrentPaddleX() + PlayedGame.PADDLE_MOVE_LEFT * 5);
		paddleX += PlayedGame.PADDLE_MOVE_LEFT * 5;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
