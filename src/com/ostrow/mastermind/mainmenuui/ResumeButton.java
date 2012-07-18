package com.ostrow.mastermind.mainmenuui;

import com.ostrow.mastermind.R;
import com.ostrow.mastermind.backend.Info;

import android.content.Context;
import android.util.TypedValue;
import android.widget.Button;
import android.widget.LinearLayout;

public class ResumeButton extends Button {

	public ResumeButton(Context context, OnClickListener listener, int imageIndex) {
		super(context);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT, 0, 1.2f
		);
		
		int marginTop = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics());		
		lp.setMargins(0, marginTop, 0, 0); // left, top, right, bottom
		
		this.setLayoutParams(lp);
		
		this.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);	
		this.setText( getResources().getString(R.string.resume) );
		
		this.setBackgroundDrawable( getResources().getDrawable(Info.allButtonIds[imageIndex]) );
	
		this.setOnClickListener(listener);
		
	}

	
	
	
	
}
