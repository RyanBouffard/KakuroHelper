package project;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


//@SuppressWarnings("serial")
public class TestingMoreStuff{


	public static void findNums(int[] numbers, int currSum, int index, int target, int[] solution, ArrayList<String> resultList) {
		// currentSum is like partial sum, adding until it gets the target number, and when it does, it builds a string of those numbers
		//
        System.out.println("currSum: "+currSum+" index: "+index);

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
	

		public static void main(String[] args) {
			// main method uses scanner to get the target number and grouplength (how many answer blocks/cells are involved)
			//  instead of getting user input, calculate how many blocks are involved through 2D-array
			// numbers is an int array of possible answers, 1-9
			// solution is another int array that checks if element is a possible solution.
			// resultList are all the results, but not limited by block number
			// finalList are results that pass the constraint of blocks available.
			ArrayList<String> resultList = new ArrayList<String>();
			ArrayList<String> finalList = new ArrayList<String>();
			Scanner s;
			s = new Scanner(System.in);
		    System.out.println("What is the target value? ");
			int target = s.nextInt();

		    System.out.println("How many cells are involved? ");
		    int groupLength = s.nextInt();
		    
		    
		    System.out.println("target: "+target+" grouplength: "+groupLength);
		    s.close();
		    int[] numbers = {1,2,3,4,5,6,7,8,9};
	        int[] solution = new int[numbers.length];

		    findNums(numbers, 0, 0, target, solution, resultList);
			for (String result : resultList) {
				if (result.length() == groupLength) {
					finalList.add("("+result+")");
				}
			}
			System.out.println("-------------------final--------------------------");
			for (String finalResult : finalList) {
				System.out.println(finalResult);
			}


		}
	}
