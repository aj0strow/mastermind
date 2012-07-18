package com.ostrow.mastermind.backend;

/**
 * @author ajostrow
 *
 * This class keeps track of each entered guess so that the game
 * can be saved and resumed at a later time. 
 */
public class GameRecord {

	private String record;
	private String current;
	private int holes;
	
	/**
	 * Constructor
	 * @param numHoles
	 */
	public GameRecord(int numHoles) {
		record = "|";
		holes = numHoles;
	}
	
	/**
	 * @param numHoles
	 * @param previousRecord - created from GameRecord toString() method passed in here. 
	 */
	public GameRecord(int numHoles, String previousRecord) {
		record = previousRecord;
		holes = numHoles;
	}
	
	/**
	 * PRECONDITION: guess.length == holes
	 * @param guess - each int represents a chosen color entered by the user
	 */
	public void append(int[] guess) {
		String s = "";
		for(int i=0; i<holes; i++) {
			s += guess[i];
		}
		s += "|";
		record += s;
	}
	
	/**
	 * The current string can be modified, record is never modified.
	 */
	public void refresh() {
		current = record.toString();
	}
	
	/**
	 * Method reads one guess array out of the current record and returns it. 
	 * @return
	 */
	public int[] readArray() {
		current = current.substring(1);
		String data = current.substring( 0, current.indexOf("|") );
		current = current.substring(current.indexOf("|"));
		int[] arr = new int[holes];
		for(int i=0; i<holes; i++) {
			arr[i] = Integer.parseInt("" + data.charAt(i));
		}
		return arr;	
	}
	
	public boolean hasNext() {
		return current.length() > holes;
	}
	
	public String toString() {
		return "Record: " + record + ", Current: " + current;
	}
	
	public String getRecord() {
		return record;
	}
	
}
