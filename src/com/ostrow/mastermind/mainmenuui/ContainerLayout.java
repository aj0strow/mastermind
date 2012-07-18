package com.ostrow.mastermind.mainmenuui;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;

public class ContainerLayout extends LinearLayout {
	
	private ArrowsLayout arrowsLayout;

	public ContainerLayout(Context context) {
		super(context);
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT );
		
		int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
		lp.setMargins(0, 0, 0, margin); //substitute parameters for left, top, right, bottom
		
		this.setLayoutParams(lp);
		
		this.setOrientation(LinearLayout.HORIZONTAL);
	}
	
	public void addArrowsLayout(ArrowsLayout al) {
		arrowsLayout = al;
		this.addView(al);
	}
	
	public void disableUpArrow() {
		( (ArrowButton) arrowsLayout.getChildAt(0) ).setEnabled(false);
	}
	
	public void disableDownArrow() {
		( (ArrowButton) arrowsLayout.getChildAt(1) ).setEnabled(false);
	}
	
	public void enableArrows() {
		( (ArrowButton) arrowsLayout.getChildAt(0) ).setEnabled(true);
		( (ArrowButton) arrowsLayout.getChildAt(1) ).setEnabled(true);
	}

}
