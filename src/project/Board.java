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
import java.util.List;
import java.util.Scanner;

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
		setSize(500, 500);

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
//			System.out.println(arg0.getActionCommand());
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
		    int lineCount = 0;
			try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			    String line;
			    while ((line = br.readLine()) != null) {
			    	lineCount +=1;
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
							myButton1_Label_S = new JLabel(""+sv);
					        myButton1_Label_S.setHorizontalAlignment(JLabel.CENTER);
					        myButton1_Label_S.setForeground(Color.BLACK);
							b1.setLayout(new BorderLayout());
							b1.add(myButton1_Label_S, BorderLayout.SOUTH);
						} 
						else if (Integer.toString(sv).equals("0".trim())){
					        myButton1_Label_E = new JLabel(""+sv2);
					        myButton1_Label_E.setHorizontalAlignment(JLabel.CENTER);
					        myButton1_Label_E.setForeground(Color.BLACK);
					        b1.setLayout(new BorderLayout());
					        b1.add(myButton1_Label_E, BorderLayout.EAST);
						} 
						else {
					        myButton1_Label_E = new JLabel(""+sv2);
					        myButton1_Label_E.setHorizontalAlignment(JLabel.CENTER);
					        myButton1_Label_E.setForeground(Color.BLACK);
					        myButton1_Label_S = new JLabel(""+sv);
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
			int sqrtLineCount = (int) Math.sqrt(lineCount);
			gamePanel.setLayout(new GridLayout((0), (sqrtLineCount)));

			block2DArray = new Block[sqrtLineCount][sqrtLineCount];						
			int k = 0;
			for (int i=0; i<block2DArray.length; i++) {
				for (int j=0; j<block2DArray.length; j++) {
					block2DArray[i][j] = blockList.get(k); 	
					block2DArray[i][j].setPosition(i, j);
					k+=1;
//					System.out.println(block2DArray[i][j]);
//					System.out.println(block2DArray[i][j].getRow());
//					System.out.println(block2DArray[i][j].getCol());

				}
			}
			
			for (Block block : blockList) {						//adds blocks to the content pane
				gamePanel.add(block);
			}




//			System.out.println(block2DArray.length);			//prints out length of 2D-array, good for testing, remove in final.

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
			
			int row = b.getRow();
			int column = b.getCol();
		     // column
//			int counter = 0;
			for (int i = 0; i <= column; i++) {
				if (block2DArray[i][column].getType() == 1 && b.questionList.size() < 2) {
					b.questionList.add(block2DArray[i][column]);
//					counter +=1;
				}
			}
		     // row
			for (int i = 0; i <= row; i++) {
				if (block2DArray[row][i].getType() == 1 && b.questionList.size() < 2) {
					b.questionList.add(block2DArray[row][i]);
//					counter +=1;
				}
			}
//			System.out.println("COUNTER: "+counter);


		}
		
	    private <T> List<T> intersection(List<T> list1, List<T> list2) {
	        List<T> list = new ArrayList<T>();

	        for (T t : list1) {
	            if(list2.contains(t) && list.contains(t) == false) {
	                list.add(t);
	            }
	        }

	        return list;
	    }
		
	    private ArrayList<Integer> checkRowCol(Block b) {
			int row = b.getRow();
			int column = b.getCol();
			ArrayList<Integer> nonValid = new ArrayList<Integer>();
			for (int i = 0; i < block2DArray.length; i++) {
				if (block2DArray[i][column].getType()==2) {
					nonValid.add(block2DArray[i][column].getStoredValue());
				}
			}
		     // row
			for (int i = 0; i < block2DArray.length; i++) {
				if (block2DArray[i][row].getType()==2){
					nonValid.add(block2DArray[row][i].getStoredValue());
				}
			}

			return nonValid;
	    }
	    
		private String findAnswer(Block b) {			
			
//			for (Block block : b.questionList) {
//				System.out.println(block);
//			}
//			System.out.println("");


			
			int firstAnswer = b.questionList.get(0).getStoredValue();
			int secondAnswer = b.questionList.get(1).getStoredValueTwo();
//			System.out.println("FIRSTANSWER: "+firstAnswer+" SECOND ANSWER: "+secondAnswer);
			int row = b.getRow();
			int column = b.getCol();
		     // column
			int distanceOne = 0;
			int distanceTwo = 0;
			for (int i = 0; i < block2DArray.length; i++) {
				if (block2DArray[i][column].getType()==2) {
					distanceOne +=1;
				}
			}
		     // row
			for (int i = 0; i < block2DArray.length; i++) {
				if (block2DArray[row][i].getType()==2) {
					distanceTwo +=1;
				}
			}
			
//			System.out.println("Distance One: "+distanceOne+" Distance Two: "+distanceTwo);
			
			ArrayList<String> resultList = new ArrayList<String>();
			ArrayList<String> wipList = new ArrayList<String>();
			int target = firstAnswer;
		    int groupLength = distanceOne;
		    int[] numbers = {1,2,3,4,5,6,7,8,9};
	        int[] solution = new int[numbers.length];

		    findNums(numbers, 0, 0, target, solution, resultList);
			for (String result : resultList) {
				if (result.length() == groupLength) {
					wipList.add(result);
				}
			}
//			System.out.println("-------------------WIP LIST ONE RESULTS--------------------------");
//			for (String finalResult : wipList) {
//				System.out.println(finalResult);
//			}
			
			ArrayList<String> resultList2 = new ArrayList<String>();
			ArrayList<String> wipList2 = new ArrayList<String>();
			int target2 = secondAnswer;
		    int groupLength2 = distanceTwo;
		    int[] numbers2 = {1,2,3,4,5,6,7,8,9};
	        int[] solution2 = new int[numbers2.length];

		    findNums(numbers2, 0, 0, target2, solution2, resultList2);
			for (String result : resultList2) {
				if (result.length() == groupLength2) {
					wipList2.add(result);
				}
			}

//			System.out.println("-------------------COMBINED RESULTS--------------------------");

			ArrayList<Character> wipAnswer = new ArrayList<Character>();
			ArrayList<Character> wipTwoAnswer = new ArrayList<Character>();
			
			for (int i = 0; i < wipList.size(); i++) {
				char[] charArray = wipList.get(i).toCharArray();
					for (int j = 0; j < charArray.length; j++) {
						wipAnswer.add(charArray[j]);
						}
					}
			
			for (int i = 0; i < wipList2.size(); i++) {
				char[] charArray = wipList2.get(i).toCharArray();
					for (int j = 0; j < charArray.length; j++) {
						wipTwoAnswer.add(charArray[j]);
						}
					}
			
			ArrayList<Character> finalAnswer = (ArrayList<Character>) intersection(wipAnswer,wipTwoAnswer);
			ArrayList<Integer> nonValid = checkRowCol(b);

//			System.out.println("------------------");

			for (Integer elem : nonValid) {
				char removedElem = (char)(((int)'0')+elem);
				if (finalAnswer.contains(removedElem)){
					finalAnswer.set(finalAnswer.indexOf(removedElem), '-');
				}
			}
			
//			System.out.println("------------------");


			
			String toolTipString = "";
			for (Character answer : finalAnswer) {
//				System.out.println(answer);
				if (answer != '-') {
					toolTipString += answer;
				}
			}
//			System.out.println(toolTipString);
			return toolTipString;

			
//			List<String> finalList = new ArrayList<String>(wipList);
//			finalList.addAll(wipList2);
//			for (String str : finalList) {
//				System.out.println(str);
//			}
			
		}
		
		private void findNums(int[] numbers, int currSum, int index, int target, int[] solution, ArrayList<String> resultList) {
			// currentSum is like partial sum, adding until it gets the target number, and when it does, it builds a string of those numbers
			//
//	        System.out.println("currSum: "+currSum+" index: "+index);

		        if (currSum == target) {
	        	  String resultString = "";

		          for (int i = 0; i < solution.length; i++) {		//if the currentsum is equal to the target sum we want to find then:
		        	  if (solution[i] == 1) {						//  checks the solution array for each index, if index is 1, 
		        		  resultString += (""+numbers[i]);			//  then it is considered part of a subsetsum solution, and added to a string,
		            }												//  and then an arraylist.
		          }													//  Does this for each index in numbers.length
	        	  resultList.add(resultString);

		        } else if (index == numbers.length) {				//else if the variable named index, which adds 1 per recursion
		          return;											// == the length of the numbers array, ends recursion
		        } else {											
		          solution[index] = 1;								//if target not found and not at end of array, then
		          													//sets the current number in solution array to 1
		          currSum += numbers[index];						//the current sum adds the next number in the number array

		          findNums(numbers, currSum, index + 1, target, solution,resultList);	//recurses with the next index
		          currSum -= numbers[index];											//subtracts the number from current sum if it's not a fit
		          solution[index] = 0;													//sets the current number in solution array to 0,
		          																		// indicating to solution array that it is not a fit
		          findNums(numbers, currSum, index + 1, target, solution,resultList);	//continues recursion with the next number in the number array
		        }
		        return;
		      }
		
		public void mouseEntered(MouseEvent e) {			//when mouse enters block, object gets the source and knows which block it is,
																// requests focus in order to use keyboard
			Object mousedOver = e.getSource();
			Block mousedOverBlock = ((Block) mousedOver);

//				mousedOverBlock.requestFocus();
				if (mousedOverBlock.getType()==2) {
					findQuestionBlocks(mousedOverBlock);
					String tooltip = findAnswer(mousedOverBlock);
					mousedOverBlock.setToolTipText(tooltip);

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
			int questionNum = 0;
			int solvedQuestions = 0;
			for (int i=0; i<block2DArray.length; i++) {
				for (int j=0; j<block2DArray.length; j++) {
					if (block2DArray[i][j].getType()==1) {
						int row = block2DArray[i][j].getRow();
						int column = block2DArray[i][j].getCol();

						int target = block2DArray[i][j].getStoredValue();
						int target2 = block2DArray[i][j].getStoredValueTwo();
						int sum = 0;
						int sum2 = 0;
						if (target > 0) {
							questionNum +=1;
							for (int k = 0; k < block2DArray.length; k++) {
								if (block2DArray[k][column].getType()==2) {
									sum += block2DArray[k][column].getStoredValue();
								}
							}
							if (target == sum) {
								solvedQuestions +=1;
							}
						}
						if (target2 > 0) {
							questionNum +=1;
							for (int k = 0; k < block2DArray.length; k++) {
								if (block2DArray[row][k].getType()==2) {
									sum2 += block2DArray[row][k].getStoredValue();
								}
							}
							if (target2 == sum2) {
								solvedQuestions +=1;
							}
						}
//						System.out.println("QUESTION ONE: "+target+" QUESTION TWO: "+target2);
//						System.out.println("SUM ONE: "+sum+" SUM TWO: "+sum2);
					}
				}
			}
//			System.out.println("QUESTIONS: "+questionNum+" SOLVED QUESTIONS: "+solvedQuestions);
			if (solvedQuestions == questionNum) {
				return true;
			}
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

		    	boolean gameOver = gameState();
		    	if (gameOver == true) {
		    		System.out.println("YOU WON!");
		    		timer.stop();
		    	} else {
//		    		System.out.println("keep going");
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

