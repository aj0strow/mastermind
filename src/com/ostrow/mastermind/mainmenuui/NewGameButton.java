package com.ostrow.mastermind.mainmenuui;

import com.ostrow.mastermind.backend.Info;

import android.content.Context;
import android.util.TypedValue;
import android.widget.LinearLayout;

public class NewGameButton extends TextResizeButton {

	public NewGameButton(Context context, String text, OnClickListener listener, OnLongClickListener olclistener, int imageIndex) {
		super(context, text);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				0, LinearLayout.LayoutParams.WRAP_CONTENT, 8
		);
		int marginLeft = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 8, getResources().getDisplayMetrics());
		int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());
		int marginBottom = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());		
		lp.setMargins(marginLeft, marginTop, 0, marginBottom); // left, top, right, bottom
		
		this.setLayoutParams(lp);

		int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 70, getResources().getDisplayMetrics());
		this.setMinimumHeight(height);
		
		this.setBackgroundDrawable( context.getResources().getDrawable(Info.allButtonIds[imageIndex]) );

		this.setOnClickListener(listener);
		this.setOnLongClickListener(olclistener);
	}

}
