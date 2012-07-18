package com.ostrow.mastermind.gameui;

import com.ostrow.mastermind.R;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * @author ajostrow
 *
 * Simply separating out UI methods from the activity. The Enter button. 
 */

public class EnterGuessButton extends ImageButton {

	public EnterGuessButton(Context context, OnClickListener listener) {
		super(context);
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams( 0, LinearLayout.LayoutParams.MATCH_PARENT, 2 );
											  // XML: layout_width="0", layout_height="match_parent", layout_weight="1"
		//lp.gravity = (Gravity.RIGHT | Gravity.CENTER_VERTICAL);
		this.setLayoutParams( lp );
		
		int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());
		int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
		this.setPadding(margin, marginTop, margin, margin);
		this.setScaleType(ImageView.ScaleType.FIT_CENTER);
		
		this.setBackgroundColor( Color.TRANSPARENT );
		this.setImageDrawable(context.getResources().getDrawable(R.drawable.icon));
		
		this.setOnClickListener(listener);
	}
	
}
