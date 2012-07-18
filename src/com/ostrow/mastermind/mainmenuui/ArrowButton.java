package com.ostrow.mastermind.mainmenuui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * @author ajostrow
 *
 * A class for the arrow buttons on the Main Menu
 */
public class ArrowButton extends Button {

	public ArrowButton(Context context, Integer resource, OnClickListener listener) {
		super(context);

		this.setLayoutParams(
				new LinearLayout.LayoutParams( LinearLayout.LayoutParams.FILL_PARENT, 0, 1 )
		);

		this.setText("");

    	this.setBackgroundDrawable( 
    			(Drawable) ( getResources().getDrawable( resource ) )
    	);

    	this.setOnClickListener(listener);
	}

}
