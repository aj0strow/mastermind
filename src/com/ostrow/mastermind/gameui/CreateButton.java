package com.ostrow.mastermind.gameui;

import com.ostrow.mastermind.R;
import com.ostrow.mastermind.backend.Result;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author ajostrow
 *
 * This class represents a hole and then a peg in a game row. 
 */
public class CreateButton extends ImageButton {
	
	// For the onClickListener to capture the value easily
	private int index;
	// For handling analysis
	private boolean perfect = false;
	private boolean close = false;
	private final static int MARGINS = 4;

	/**
	 * Constructor - For createRow()
	 * @param context - Activity context
	 * @param index - the index in the loop when created
	 * @param listener - the shared listener
	 */
	public CreateButton(Context context, int index, OnClickListener listener) {
		super(context);
		
		this.index = index;
		 // Start with the image of the hole. 
		Drawable image = getResources().getDrawable(R.drawable.peg_hole);
		this.setImageDrawable(image);
		this.setScaleType(ImageView.ScaleType.FIT_CENTER);
		this.setBackgroundDrawable(getResources().getDrawable(R.drawable.nothing));
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
		// layout_width="0", layout_height="match_parent", layout_weight="1"
		this.setLayoutParams(lp);
		int margins = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGINS, getResources().getDisplayMetrics());
		this.setPadding(0, margins, 0, margins);
		
		this.setOnClickListener(listener);
	}
	
	/**
	 * Constructor - For generateRow()
	 * @param context
	 * @param index
	 * @param pegId - set the image to the previously recorded color
	 */
	public CreateButton(Context context, int index, Integer pegId) {
		super(context);
		
		this.index = index;
		
		this.setImageDrawable( 
				(Drawable) getResources().getDrawable(pegId)
		);
		this.setScaleType(ImageView.ScaleType.FIT_CENTER);
		this.setBackgroundDrawable(getResources().getDrawable(R.drawable.nothing));

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
		// layout_width="0", layout_height="match_parent", layout_weight="1"
		this.setLayoutParams(lp);
		int margins = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, MARGINS, getResources().getDisplayMetrics());
		this.setPadding(0, margins, 0, margins);
	}
	
	public int getIndex() {
		return index;
	}
	
	// Handle logic for analysis
	public void setResult(int result) {
		if(result == Result.CLOSE) {
			close = true;
		} else if(result == Result.PERFECT) {
			perfect = true;
		}
	}
	
	public boolean isPerfect() {
		return perfect;
	}
	
	public boolean isClose() {
		return close;
	}
	
	// Dependent on perfect or close to be set beforehand.
	public void setAnalysisBackground() {
		if(perfect) {
			this.setBackgroundDrawable( getResources().getDrawable(R.drawable.dust_gold) );	// Needs to be replaced by nice gold resource
		} else if(close) {
			this.setBackgroundDrawable( getResources().getDrawable(R.drawable.dust_silver) );	// Needs to be replaced by nice silver resource
		}
	}

}
