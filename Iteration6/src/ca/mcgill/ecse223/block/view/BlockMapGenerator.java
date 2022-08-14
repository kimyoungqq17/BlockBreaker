package ca.mcgill.ecse223.block.view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

//saw this from online tutorial   but when connected with game will just be block assignments
public class BlockMapGenerator {
	public int map[][]; //will contain all bricks
	public int brickWidth;
	public int brickHeight;
	public BlockMapGenerator (int row, int col) { //want to recieve number of rows and colums to be generated
		map = new int [row][col];  //instantiate 2d array with number of rows and colums
		for (int i = 0; i< map.length; i++) {
			for(int j=0 ; j<map[0].length;j++) {
				map[i][j]=1; //1 will detect that this particular brick has not been intersected with ball since it has to be shown on the panel 
			}
		}
		
		brickWidth = 100/col;
		brickHeight = 100/row; //can get the brick height from transfer functions
		
	}
	//for each block in blockassignment, get that x and draw, get that y and draw at position
	public void draw (Graphics2D g) {
		for (int i = 0; i< map.length; i++) {
			for (int j=0; j< map[0].length;j++) {
				if(map[i][j] > 0) { //create particular block in particular position
					g.setColor(Color.blue);
					g.fillRect(j * brickWidth +100 , i*brickHeight +50, brickWidth, brickHeight );
					
					g.setStroke(new BasicStroke(3));
					g.setColor(Color.gray);
					g.drawRect(j * brickWidth +100 , i*brickHeight +50, brickWidth, brickHeight);//draw according to rectangle sides 
				}
			}
		}
	}
	public void setBrickValue(int value, int row, int col) {
		map[row][col] = value;
	}
	
}
