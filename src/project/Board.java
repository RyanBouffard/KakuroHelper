package project;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;





public class Board extends JFrame {
	private JMenuBar menuBar;
	private JMenu subMenu;
	private JMenu fileMenu;
	private ArrayList<Block> blockList = new ArrayList<Block>();
	private Manager phil = new Manager();
	private Block[][] block2DArray;
	private JPanel mainPanel;
	private JPanel gamePanel;
	private JPanel watchPanel;
    private  Timer timer;
    private  JTextField tf = new JTextField(5);
    private  SimpleDateFormat date = new SimpleDateFormat("mm.ss.SSS");
    private long startTime;
	
	public Board() {				// creates an empty space with a menu that allows the user to choose certain files to read
		Container c = getContentPane();
		watchPanel = new JPanel();
		gamePanel= new JPanel();
		mainPanel = new JPanel();
		setTitle("Kakuro v1.0");
		setSize(400, 500);

		mainPanel.setLayout(new BorderLayout());
		c.add(mainPanel);
		
		buildMenu();
//		pack();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		}

	public void buildMenu() {			// builds game menu and submenu for read
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");

		JMenuItem menuItem = new JMenuItem("Reset");
		fileMenu.add(menuItem);
		menuItem.addActionListener(phil);
		
		menuItem = new JMenuItem("Save");
		fileMenu.add(menuItem);
		menuItem.addActionListener(phil);
		
		subMenu = new JMenu("Read");
		fileMenu.add(subMenu);
		menuItem.addActionListener(phil);
		
		JMenuItem subMenuItem = new JMenuItem("Easy Puzzle 1");
		subMenu.add(subMenuItem);
		subMenuItem.addActionListener(phil);
		
		subMenuItem = new JMenuItem("Easy Puzzle 2");
		subMenu.add(subMenuItem);
		subMenuItem.addActionListener(phil);
		
		subMenuItem = new JMenuItem("Easy Puzzle 3");
		subMenu.add(subMenuItem);
		subMenuItem.addActionListener(phil);
		
		subMenuItem = new JMenuItem("Saved Puzzle");
		subMenu.add(subMenuItem);
		subMenuItem.addActionListener(phil);
		
		
		menuItem = new JMenuItem("Exit");
		fileMenu.add(menuItem);
		menuItem.addActionListener(phil);

		menuBar.add(fileMenu);

		setJMenuBar(menuBar);
		
		
	}
	
	public void buildStopwatch() {
		
		timer = new Timer(0, new ActionListener() { 
            @Override
            public void actionPerformed(ActionEvent e) {
    			updateClock();
            }
		});
		watchPanel.setLayout(new BorderLayout());
        tf.setHorizontalAlignment(JTextField.CENTER);

        tf.setEditable(false);
        watchPanel.add(tf, BorderLayout.CENTER);
//        Dimension a = c.getPreferredSize();
//        watchPanel.setPreferredSize(a);
        mainPanel.add(watchPanel,BorderLayout.PAGE_START);
//        c.add(watchPanel);
        startTime = System.currentTimeMillis();
        timer.start();
        
		setVisible(true);

        //when the game is completed should have a timer.stop();
	}
	
    public void updateClock() {
        Date elapsed = new Date(System.currentTimeMillis() - startTime);
        tf.setText(date.format(elapsed));
    }
    
	public static void main(String[] args) {
		new Board();			// creates a new board
	}
	
	

	private class Manager implements ActionListener, MouseListener, MouseMotionListener, KeyListener {
		@Override
		/**
		 * Knows when action is performed, grabs the object associated with the action.
		 */
		public void actionPerformed(ActionEvent arg0) {
			System.out.println(arg0.getActionCommand());
			//certain commands happen here, if menus are clicked, then certain actions will follow
			if (arg0.getActionCommand().equals("Reset")) {
				System.out.println("Resetting Game");
				for (Block b : blockList) {
					if (b.getType()==2) {
						b.clearBlock();
						b.setText("0");
					}
				}
			}
			else if (arg0.getActionCommand().equals("Easy Puzzle 1")) {			// calls method to open/read main puzzle
				System.out.println("Opening puzzle");
				readGame("Resources/puzzle1.txt");
			}
			
			else if (arg0.getActionCommand().equals("Easy Puzzle 2")) {			// calls method to open/read main puzzle
				System.out.println("Opening puzzle");
				readGame("Resources/puzzle2.txt");
			}
			
			else if (arg0.getActionCommand().equals("Easy Puzzle 3")) {			// calls method to open/read main puzzle
				System.out.println("Opening puzzle");
				readGame("Resources/puzzle3.txt");
			}
			
			else if (arg0.getActionCommand().equals("Saved Puzzle")) {			// calls method to open/read saved puzzle
				System.out.println("Opening puzzle");
				readGame("Resources/savedpuzzle.txt");
			}
			else if (arg0.getActionCommand().equals("Save")) {					// calls method to save puzzle to text file
				System.out.println("Saving Game");
				saveGame("Resources/savedpuzzle.txt");
			}
			else if (arg0.getActionCommand().equals("Exit")) {					// exits the program with no time delay
				System.out.println("Exiting program");
				System.exit(0);
			}
		}
		
		private void readGame(String filename) {				// creates content pane and reads text file in order to create kakuro board
			gamePanel = new JPanel();
		    JLabel myButton1_Label_E;
		    JLabel myButton1_Label_S = new JLabel();
			gamePanel.setLayout(new GridLayout((0), (5)));
			try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	String[] values = line.split(",");
			    	int bt = Integer.parseInt(values[0]);
			    	int sv = Integer.parseInt(values[1]);
			    	int sv2 = Integer.parseInt(values[2]);
//			        System.out.println(Arrays.toString(values));
		        	int r = 0;
		        	int c = 0;
			        if (bt == 0) {
			        	Color bc = Color.black;
			        	Block b1 = new Block(bt,sv,sv2,bc,r,c);
						b1.setFocusable(false);
						blockList.add(b1);
			        } else if(bt ==1) {
			        	Color bc = Color.gray;
			        	Block b1 = new Block(bt,sv,sv2,bc,r,c);
						b1.addActionListener(phil);
						b1.addMouseListener(phil);
						b1.addMouseMotionListener(phil);
						if (Integer.toString(sv2).equals("0".trim())){
							b1.setText(Integer.toString(sv));
						} 
						else if (Integer.toString(sv).equals("0".trim())){
							b1.setText(Integer.toString(sv2));
						} 
						else {
					        myButton1_Label_E = new JLabel(""+sv);
					        myButton1_Label_E.setHorizontalAlignment(JLabel.CENTER);
					        myButton1_Label_E.setForeground(Color.BLACK);
					        myButton1_Label_S = new JLabel(""+sv2);
					        myButton1_Label_S.setHorizontalAlignment(JLabel.CENTER);
					        myButton1_Label_S.setForeground(Color.BLACK);
					        b1.setLayout(new BorderLayout());
					        b1.add(myButton1_Label_E, BorderLayout.EAST);
					        b1.add(myButton1_Label_S, BorderLayout.SOUTH);
//					        b1.paintComponent(getGraphics());


						}

						blockList.add(b1);
			        } else {
			        	Color bc = Color.white;
						Block b1 = new Block(bt,sv,sv2,bc,r,c);
						b1.addActionListener(phil);
						b1.addMouseListener(phil);
						b1.addMouseMotionListener(phil);
						b1.addKeyListener(phil);
						b1.setFocusable(true);
						b1.setText(Integer.toString(sv));
						blockList.add(b1);

			        }
			    }
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			block2DArray = new Block[5][5];						
			int k = 0;
			for (int i=0; i<5; i++) {
				for (int j=0; j<5; j++) {
					block2DArray[i][j] = blockList.get(k); 	
					block2DArray[i][j].setPosition(i, j);
					k+=1;
					System.out.println(block2DArray[i][j]);
//					System.out.println(block2DArray[i][j].getRow());
//					System.out.println(block2DArray[i][j].getCol());

				}
			}
			
			for (Block block : blockList) {						//adds blocks to the content pane
				gamePanel.add(block);
			}




			System.out.println(block2DArray.length);			//prints out length of 2D-array, good for testing, remove in final.

			mainPanel.add(gamePanel,BorderLayout.PAGE_END);

			buildStopwatch();
			buildMenu();
			pack();
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
			
		}

		private void saveGame(String filename) {			//saves game by writing each block in its string representation to a text file
			File drawingFile = new File(filename);
			PrintWriter pw = null;
			
			try {
				pw = new PrintWriter(drawingFile);
				for (int i=0; i < blockList.size()-1;i++) {
					Block b = blockList.get(i);
					pw.write(b.toString()+"\n");
				}
				int lastIndex = blockList.size()-1;
				if(blockList.size() > 0) {
					pw.write(blockList.get(lastIndex).toString());
				}
				pw.close();

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		private void findQuestionBlocks(Block b) {			
			int a = b.getType();
			System.out.println(a);
			
			int row = b.getRow();
			int column = b.getCol();
		     // column
			int counter = 0;
			for (int i = 0; i < 5; i++) {
				if (block2DArray[i][column].getType() == 1) {
					b.questionList.add(block2DArray[i][column]);
					counter +=1;
				}
			}
		     // row
			for (int i = 0; i < 5; i++) {
				if (block2DArray[row][i].getType() == 1 && counter < 2) {
					b.questionList.add(block2DArray[row][i]);
					counter +=1;
				}
			}
			System.out.println("COUNTER: "+counter);
			for (Block block : b.questionList) {
				System.out.println(block);
			}

		}
		
		public void mouseEntered(MouseEvent e) {			//when mouse enters block, object gets the source and knows which block it is,
																// requests focus in order to use keyboard
			Object mousedOver = e.getSource();
			Block mousedOverBlock = ((Block) mousedOver);

//				mousedOverBlock.requestFocus();
				if (mousedOverBlock.getType()==2) {
					mousedOverBlock.setToolTipText("TEST TOOLTIP CHANGE TO POSSIBLE ANSWERS");
					findQuestionBlocks(mousedOverBlock);
				}
				

		}

		@Override
		public void mouseClicked(MouseEvent e) {				//TODO mouse needs to be clicked for this project, not hovered
			// TODO Auto-generated method stub
			Object mousedOver = e.getSource();
			Block mousedOverBlock = ((Block) mousedOver);

				mousedOverBlock.requestFocus();
			
		}
		@Override
		public void mouseExited(MouseEvent e) {					//knows whem mouse exits a block
																	//commented out code was good for testing, may not be needed anymore.
			Object mousedOver = e.getSource();
			Block mousedOverBlock = ((Block) mousedOver);

			String a =mousedOverBlock.getText();
//			mousedOverBlock.setText(Integer.toString(mousedOverBlock.getStoredValue()));
//			System.out.println("Leaving "+a);
//				mousedOverBlock.update(finalInput);
//			}
			
		}
		
		@Override
		public void mousePressed(MouseEvent arg0) {				//mouse does not need to be pressed for this project
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseReleased(MouseEvent arg0) {			//mouse does not need to be released for this project
			// TODO Auto-generated method stub
			
		}
		/**
		 * Allows for game to end, game state is checked each time a card is clicked.
		 * 
		 * @return Returns whether the game should be over, or if it is still in
		 *         progress.
		 */
		public boolean gameState() {
			// TODO
//			if all blocks filled, and correct, return true
			return false;
		}
		
		
		@Override
		public void mouseDragged(MouseEvent arg0) {			//mouse does not need to know when it is dragged for this project
			// TODO Auto-generated method stub
			
		}
		@Override
		public void mouseMoved(MouseEvent arg0) {			//mouse does not need to know when it is moved for this project
			// TODO Auto-generated method stub
			
		}
		@Override
		public void keyPressed(KeyEvent e) {			//key does not need to know when it is pressed for this project
			// TODO Auto-generated method stub


		}
		@Override
		public void keyReleased(KeyEvent e) {			//when key is released (meaning backspace only in this case,)
															// the block is cleared of a value(value is set to 0) and the text on the block is set to 0
			// TODO Auto-generated method stub
			if(e.getKeyCode() == KeyEvent.VK_BACK_SPACE)
		    {  
				Object typedIn = e.getSource();
				Block typedInBlock = ((Block) typedIn);
				typedInBlock.clearBlock();
				typedInBlock.setText("0");
		    } 
		}
		@Override
		public void keyTyped(KeyEvent e) {		//when key is typed (meaning a single integer is fully typed in by releasing key,)
													// it updates the block's value and set's the block's text on the board.
													// currently throws errors when letter or symbol is entered		
		    	char inputChar = e.getKeyChar();
		    	Object typedIn = e.getSource();
		    	Block typedInBlock = ((Block) typedIn);
		    	String inputString = Character.toString(inputChar);
		    	if (inputString != null && inputString.trim().length() > 0) {
		    		int inputInt = Integer.parseInt(inputString);
		    	    typedInBlock.update(inputInt);
		    	    typedInBlock.setText(inputString);
		    }

		}

//		@Override
//		public void itemStateChanged(ItemEvent e) {
//			// TODO Auto-generated method stub
//                if (b.isSelected()) {
//                    startTime = System.currentTimeMillis();
//                    timer.start();
//                    b.setText(stop);
//                } 
//                else {
//                    updateClock();
//                    startTime = 0;
//
//                    timer.stop();
//                    b.setText(start);
//                }
//            }
		}
		

		
	
	
}

