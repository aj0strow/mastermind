package com.ostrow.mastermind.backend;

/**
 * @author ajostrow
 *
 * This class takes in the code or answer, and the guess, and returns
 * how many pegs were the right color and right place, and how many
 * were the right color wrong place. 
 */
public class Result {
	
	public static final int PERFECT = 1;
	public static final int CLOSE = 2;
		
	public int[] result;
	private int perfect;
	private int close;
	
	/**
	 * Constructor
	 * 
	 * Logic must be done in two separate loops to properly count the holes. 
	 * 
	 * @param answer - The correct answer
	 * @param guess - The entered guess
	 */
	public Result(int[] answer, int[] guess) {
		int holes = answer.length;   // Also guess.length
		boolean[] claimed = new boolean[holes];	 // To avoid duplicates.
		result = new int[holes];

		// If the pegs are the same color and position, it is a perfect match.
		perfect = 0;
		for(int i=0; i<holes; i++) {
			if(guess[i] == answer[i]) {
				perfect++;
				result[i] = Result.PERFECT;
				claimed[i] = true;  // Avoid double-counting later on
			}
		}
		// If the pegs are the same color but different positions, it is a close match. 
		close = 0;
		for (int i=0; i<holes; i++) {
			
			// To avoid double-counting perfects as close
			if(result[i] == Result.PERFECT) continue;	
			
			for (int j=0; j<holes; j++) {
				
				// To avoid double-counting claimed close matches
				if (claimed[j]) continue;	
				
				if (guess[i] == answer[j]) {
					result[i] = Result.CLOSE;
					close++;
					claimed[j] = true;
					break;
				}
			}
		}
	}
	
	public int amountPerfect() {
		return perfect;
	}
	
	public int amountClose() {
		return close;
	}

}
