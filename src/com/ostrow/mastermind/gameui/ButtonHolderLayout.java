package com.ostrow.mastermind.gameui;

import android.content.Context;
import android.widget.LinearLayout;

/**
 * @author ajostrow
 *
 * This class is a LinearLayout UI object used for holding the buttons in the Game Row. 
 */
public class ButtonHolderLayout extends LinearLayout {
	
	private int amount; // amount of CreateButtons to hold

	/**
	 * Constructor for createRow()
	 * @param context - context of activity
	 * @param amount - the amount of buttons to create (numHoles)
	 * @param listener - the onClickListener for the buttons.
	 */
	public ButtonHolderLayout(Context context, int amount, OnClickListener listener) {
		super(context);
		
		this.amount = amount;
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 8);
		this.setLayoutParams( lp );
				
		for(int i=0; i<amount; i++) {
			final CreateButton hole = new CreateButton(context, i, listener);
			this.addView(hole);
		}
	}
	
	/**
	 * Constructor for generateRow()
	 * @param context
	 * @param guess - the data of the previous guess
	 * @param imageIds - the images to assign to the disabled buttons depending on the guess
	 */
	public ButtonHolderLayout(Context context, int[] guess, Integer[] imageIds) {
		super(context);
		
		this.amount = guess.length;
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 8);
		this.setLayoutParams( lp );	
		
		for(int i=0; i<amount; i++) {
			CreateButton hole = new CreateButton(context, i, imageIds[ guess[i] ] );
			hole.setClickable(false);
			hole.setFocusable(false);
			this.addView(hole);
		}
	}

	/**
	 * Disables all the CreateButtons it holds;
	 * called when EnterGuessButton is clicked. 
	 */
	public void disableAll() {
		for(int i=0; i<amount; i++) {
			this.getChildAt(i).setClickable(false);
			this.getChildAt(i).setFocusable(false);
		}
	}
	
	// Called when See Analysis button is clicked in the "You Won!" dialog
	public void handleAnalysis() {
		for(int i=0; i<this.getChildCount(); i++) {
			( (CreateButton) this.getChildAt(i) ).setAnalysisBackground();
		}
	}

}
