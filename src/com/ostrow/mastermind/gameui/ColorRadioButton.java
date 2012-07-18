package com.ostrow.mastermind.gameui;

import com.ostrow.mastermind.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.RadioButton;

/**
 * @author ajostrow
 *
 * This class is simply to separated the UI object from the loop in the activity.
 */
public class ColorRadioButton extends RadioButton {
	
	// Only formatting, no logic.

	public ColorRadioButton(Context context, Integer colorId) {
		super(context);
		
		this.setText("");
    	this.setBackgroundDrawable( (Drawable) ( getResources().getDrawable( colorId ) ));
    	this.setButtonDrawable((Drawable) getResources().getDrawable(R.drawable.nothing));
    	this.setPadding(0, 0, 0, 0);
	}

}
