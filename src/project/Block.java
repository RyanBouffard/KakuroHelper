package project;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;


public class Block extends JButton{
	//properties
	private int blockType;
	private int storedValue;
	private int storedValueTwo;
	private static Color blockColor;
	ArrayList<Block> questionList = new ArrayList<Block>();	//which sum blocks does this answer to?
	private static int width = 100, height = 100;
	private int row;
	private int col;
	
	//constructor
	public Block(int bt, int sv, int sv2, Color bc, int r, int c) {
		this.blockType = bt;
		this.storedValue = sv;
		this.storedValueTwo = sv2;
		this.blockColor = bc;
		this.row = r;
		this.col = c;
		setPreferredSize(new Dimension(width, height));
		setBackground(blockColor);
		paintImmediately(0, 0, width, height);
		
	}
	
	//behaviors
	public void update(int choice){
		this.storedValue = choice;
	}
	
	public void clearBlock() {
		// TODO
		this.storedValue = 0;	//sets storedvalue to 0, essentially clearing the block's value
	}
	
	
	public void setType(int type) {
		this.blockType = type;				// sets the block type to the one indicated
	}
	
	public int getType() {
		return this.blockType;			// gets the block type when called
	}
	
	public int getStoredValue() {
		return this.storedValue;			// gets the block's stored value when called
	}
	
	public Integer getAnswer() {			// not implemented yet, not sure if redundant or on TODO
		// TODO
		return 0;
	}
	
	public Integer getQuestion(){			// not implemented yet, not sure if redundant or on TODO
		// TODO
		return 0;
	}
	
	public void setPosition(int posRow, int posCol){			
		this.row = posRow;
		this.col = posCol;
	}
	
	public Integer getRow(){			
		return this.row;
	}
	
	public Integer getCol(){			
		return this.col;
	}
	
	public String toString() {				// to-string method that allows block to be written to text file, string representation of block
		return (this.blockType+","+ this.storedValue+","+this.storedValueTwo);
	}
	
	public void paintComponent(Graphics g){
	    if (this.blockType==1 && this.storedValue != 0 && this.storedValueTwo != 0) {   
	    	super.paintComponent(g);
	    	g.drawLine(0, 0, width-1, height-1 );
	    } else {
	    	super.paintComponent(g);
	    }
}

//	public boolean isEmpty() {
//		// TODO
//		if (storedValue != null) {
//			return false;
//		}
//		return true;
//		
//	}
}


