package com.ostrow.mastermind.mainmenuui;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;

public class ArrowsLayout extends LinearLayout {

	public ArrowsLayout(Context context) {
		super(context);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
		
		int marginRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics());
		lp.setMargins(0, 0, marginRight, 0); //substitute parameters for left, top, right, bottom
		
		this.setLayoutParams(lp);
		
		this.setOrientation(LinearLayout.VERTICAL);

	}

}
