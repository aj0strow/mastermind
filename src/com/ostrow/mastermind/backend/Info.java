package com.ostrow.mastermind.backend;

import com.ostrow.mastermind.R;

/**
 * @author ajostrow
 *
 * This class if for keeping track of static info specific to 
 * the Java code and outside res/values. 
 * 
 * Order of colors: 
 *    1 - Red
 *    2 - Yellow
 *    3 - Green
 *    4 - Blue
 *    5 - Purple
 *    6 - Orange
 *    7 - Seafoam
 *    8 - Gray
 */
public class Info {
	
// Static suffixes for SharedPreferences strings
	
	public final static String GAME_RECORD_SUFFIX = "_game_record";
	public final static String AVG_SUFFIX = "_avg";
	public final static String GAMETYPE_SUFFIX = "_gametype";
	
// Static info of all button selectors
	
	public final static Integer[] allButtonIds = {
		R.drawable.appbutton_red_selector,
		R.drawable.appbutton_yellow_selector,
		R.drawable.appbutton_green_selector,
		R.drawable.appbutton_blue_selector,
		R.drawable.appbutton_purple_selector,
		R.drawable.appbutton_orange_selector,
		R.drawable.appbutton_seafoam_selector,
		R.drawable.appbutton_gray_selector
	};
	
// Static info for choosing the clicked peg color
	
	private final static Integer[] allPegIds = {
		R.drawable.red,
		R.drawable.yellow,
		R.drawable.green,
		R.drawable.blue,
		R.drawable.purple,
		R.drawable.orange,
		R.drawable.seafoam,
		R.drawable.gray
	};

	// PRECONDITION: 0 < numColors <= 8
	public static Integer[] newPegIdsArray(int numColors) {
		Integer[] pegIds = new Integer[ numColors ];
		for(int i=0; i<pegIds.length; i++) {
			pegIds[i] = allPegIds[i];
		}
		return pegIds;
	}
	
// Static info for building the color selector
	
	private final static Integer[] allColorIds = {
		R.drawable.red_selector,
		R.drawable.yellow_selector,
		R.drawable.green_selector,
		R.drawable.blue_selector,
		R.drawable.purple_selector,
		R.drawable.orange_selector,
		R.drawable.seafoam_selector,
		R.drawable.gray_selector
	};

	// PRECONDITION: 0 < numColors <= 8
	public static Integer[] newColorIdsArray(int numColors) {
		Integer[] colorIds = new Integer[ numColors ];
		for(int i=0; i<colorIds.length; i++) {
			colorIds[i] = allColorIds[i];
		}
		return colorIds;
	}

}
