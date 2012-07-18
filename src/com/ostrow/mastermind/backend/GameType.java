package com.ostrow.mastermind.backend;

/**
 * @author ajostrow
 *
 * This class is for storing the information that makes a game type unique. 
 * Each game type has a name, amount of holes, amount of colors, and a
 * natural key which serves as its SharedPreferences key prefix. 
 */
public class GameType {
		
	private String name;
	private int holes;
	private int colors;
	private String prefix; // For shared preferences. Natural key, 3 letters
	
	/**
	 * Constructor: 
	 * @param _name - name of game type
	 * @param _holes - amount of holes
	 * @param _colors - amount of colors
	 * @param gtm - Must be passed in so that it can generate a unique natural key.
	 */
	public GameType(String _name, int _holes, int _colors, GameTypesManager gtm) {
		name = _name;
		holes = _holes;
		colors = _colors;
		prefix = gtm.generateKey();
	}
	
	/**
	 * Constructor:
	 * @param info - Must be from the GameType toString() method. 
	 */
	public GameType(String info) {
		String s = info;
		prefix = s.substring(0, 3);
		s = s.substring(3);
		holes = Integer.parseInt("" + s.charAt(0));
		colors = Integer.parseInt("" + s.charAt(1));
		name = s.substring(2);
	}

	public String getPrefix() {
		return prefix;
	}

	public String getName() {
		return name;
	}
	
	public void changeName(String newName) {
		name = newName;
	}

	public int getHoles() {
		return holes;
	}
	
	public int getColors() {
		return colors;
	}
	
	/**
	 * The precision of this method is very important, and allows the 
	 * GameType to be stored as a string and remade with the 2nd constructor. 
	 */
	public String toString() {
		return prefix + holes + colors + name;
	}
	
}
