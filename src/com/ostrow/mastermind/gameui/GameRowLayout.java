package com.ostrow.mastermind.gameui;

import com.ostrow.mastermind.R;

import android.content.Context;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * @author ajostrow
 *
 * This class holds one complete row of the game. 
 */
public class GameRowLayout extends LinearLayout {

	// Measurements in density independent pixels (DIP)
	private final int MARGIN_TOP_BOTTOM = 5;
	private final int MARGIN_LEFT_RIGHT = 5;
	private final int HEIGHT = 60;
	
	/**
	 * Constructor
	 * @param context
	 */
	public GameRowLayout(Context context) {
		super(context);

		int dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, HEIGHT, getResources().getDisplayMetrics());
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( ViewGroup.LayoutParams.FILL_PARENT , dp  );
		
		int marginTopBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_TOP_BOTTOM, getResources().getDisplayMetrics());
		int marginLeftRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGIN_LEFT_RIGHT, getResources().getDisplayMetrics());
		lp.setMargins(marginLeftRight, marginTopBottom, marginLeftRight, marginTopBottom); 
		
		this.setLayoutParams(  lp ); 

		this.setBackgroundDrawable( getResources().getDrawable(R.drawable.gamerow_highlight) );
	}
	
}
