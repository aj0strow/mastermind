package com.ostrow.mastermind.backend;

import java.util.ArrayList;

/**
 * @author ajostrow
 *
 * This class is for generating and keeping track of GameTypes, more
 * specifically the natural keys for the SharedPreferences (prefixes).
 */
public class GameTypesManager {

	public ArrayList<String> keys; // Breaking encapsulation makes sense here imo
	
	/**
	 * Constructor
	 */
	public GameTypesManager() {
		keys = new ArrayList<String>();
	}
	
	/**
	 * Constructor
	 * @param existingPrefixes - Must come from the toString() method.
	 */
	public GameTypesManager(String existingPrefixes) {
		String s = existingPrefixes;
		keys = new ArrayList<String>();
		while(s.length() > 3) {
			keys.add(s.substring(0, 3));
			s = s.substring(3);
		}
		if(s.length() == 3)
			keys.add(s);
	}
	
	/**
	 * This method returns a string that can be used in the second
	 * constructor to remake the same object as before. 
	 */
	public String toString() {
		String done = "";
		for(String key : keys) {
			done += key;
		}
		return done;
	}
	
	/**
	 * Create new key until it is unique (highly probable.)
	 * Then add it to the list of all keys and return it. 
	 */
	public String generateKey() {
		String key = makeKey();
		while(!checkKey(key))
			key = makeKey();
		keys.add(key);
		return key;
	}
	
	/**
	 * Makes a new random key (aka prefix)
	 * 
	 * Each key is 3 letters from a-z
	 * Choose a random number from 0 to 25 (26 letters in alphabet)
	 * and add 97, the value of lower case 'a'
	 */
	private String makeKey() {
		String key = "";
		for(int i=0; i<3; i++)
			key += (char) ( (int)(Math.random() * 26 + 97) );
		return key;
	}
	
	/**
	 * true -> key is unique
	 * false -> duplicate key already exists in the ArrayList<String> keys
	 */
	private boolean checkKey(String newKey) {
		for(String key : keys) { 	// Check the key against each other key
			if(key.equals(newKey))
				return false;
		}
		return true;
	}
	
	/**
	 * Used in conjunction with the ArrowButtons in the Main Menu
	 * to keep track of game type order.
	 * 
	 * @param key - The key corresponding to the gametype to be moved up.
	 */
	public void moveKeyUp(String key) {
		int index = keys.indexOf(key);
		if(index != 0) {     // Make sure index is valid.
			keys.add( index-1, keys.remove(index) );
		}
	}
	
	// Similar idea to moveKeyUp(key), except down in the list
	public void moveKeyDown(String key) {
		int index = keys.indexOf(key);
		if(index != keys.size()-1) {
			keys.add( index+1, keys.remove(index) );
		}
	}
	
	public void removeKey(String key) {
		keys.remove(key);
	}
	
}
