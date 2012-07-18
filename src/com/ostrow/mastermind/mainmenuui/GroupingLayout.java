package com.ostrow.mastermind.mainmenuui;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;

public class GroupingLayout extends LinearLayout {

	public GroupingLayout(Context context) {
		super(context);
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				0, LinearLayout.LayoutParams.MATCH_PARENT, 5);

		int marginLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		int marginRight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
		int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());		
		lp.setMargins(marginLeft, 0, marginRight, marginBottom); //substitute parameters for left, top, right, bottom
		
		this.setLayoutParams(lp);
		
		this.setOrientation(LinearLayout.VERTICAL);

	}

}
