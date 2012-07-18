package com.ostrow.mastermind.backend;

/**
 * @author ajostrow
 *
 * This class is to keep track of the average amount of guesses
 * for a game type. 
 */
public class GuessAvg {

	private double avg;
	private int games;
	
	/**
	 * Constructor - no params
	 */
	public GuessAvg() {
		avg = 0.0;
		games = 0;
	}
	
	/**
	 * Constructor
	 * @param str - Must be from toString() method of GuessAvg class
	 */
	public GuessAvg(String str) {
		String _avg = str.substring( 0 , str.indexOf('|') );
		String _games = str.substring(str.indexOf('|') + 1);
		avg = Double.parseDouble(_avg);
		games = Integer.parseInt(_games);
	}
	
	/**
	 * Add a new guess count to the average:
	 * - Find the total amount of guesses by multiplying the average amount
	 *   by the total amount of guesses. 
	 * - Add the amount of guesses from the game.
	 * - Up the count of games and divide out a new average amount of guesses. 
	 * @param guesses
	 */
	public void addGame(int guesses) {
		double total = avg * games + guesses;
		games++;
		avg = total / games;
	}
	
	public void reset() {
		avg = 0.0;
		games = 0;
	}
	
	public double getAvg() {
		return avg;
	}
	
	// Returns a string that can be used for the second constructor.
	public String toString() {
		return "" + avg + "|" + games;
	}
	
}
