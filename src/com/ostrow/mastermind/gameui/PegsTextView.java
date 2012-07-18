package com.ostrow.mastermind.gameui;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author ajostrow
 *
 * This TextView holds the perfect and close readings after the EnterGuess button is pressed. 
 */
public class PegsTextView extends TextView {

	public PegsTextView(Context context, int pegs, int colorRGB) {
		super(context);

		this.setText("" + pegs + " "); // Spaced properly
		this.setTextColor(colorRGB);
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
												// XML: layout_width="0", layout_height="match_parent", layout_weight="1"
		lp.setMargins(0, 0, 2, 0);
		this.setLayoutParams(lp);
		
		this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
		this.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);	
	
	}

}
