package com.ostrow.mastermind.gameui;

import com.ostrow.mastermind.R;
import com.ostrow.mastermind.backend.AutoResizeTextView;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

/**
 * @author ajostrow
 *
 * The message that says WIN! when you win the game. 
 */

public class WinTextView extends AutoResizeTextView {

	/**
	 * Constructor
	 * @param context - Activity context
	 */
	public WinTextView(Context context) {
		super(context, Color.rgb(253, 208, 23), 24);
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 2);
		
		this.setLayoutParams(lp);
		
		this.setText( context.getResources().getString( R.string.game_win ));

	}

}
