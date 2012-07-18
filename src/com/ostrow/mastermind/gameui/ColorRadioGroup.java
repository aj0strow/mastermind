package com.ostrow.mastermind.gameui;

import android.content.Context;
import android.widget.RadioGroup;

/**
 * @author ajostrow
 *
 * This class is the color selector at the bottom of the game activity screen.
 */
public class ColorRadioGroup extends RadioGroup {

	/**
	 * Constructor
	 * @param context - activity context
	 * @param colorIds - From the Info class depending on numColors. 
	 * @param listener - for handling changing the selected color
	 */
	public ColorRadioGroup(Context context, Integer[] colorIds, OnCheckedChangeListener listener) {
		super(context);
		
	    this.setOrientation(RadioGroup.HORIZONTAL);
	    this.setPadding(0, 0, 0, 0); // No padding
	    this.setWeightSum( (float) colorIds.length );
	    
        // For each color, a radio button is made and added to the group.  
	    for(int i=0; i<colorIds.length; i++) {
	    	ColorRadioButton button = new ColorRadioButton(context, colorIds[i] );
	    	this.addView( button, 
	    	    new RadioGroup.LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
	    	); 
	    }

	    this.setOnCheckedChangeListener(listener);
	}
}
