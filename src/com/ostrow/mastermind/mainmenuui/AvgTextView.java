package com.ostrow.mastermind.mainmenuui;

import com.ostrow.mastermind.backend.AutoResizeTextView;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;

public class AvgTextView extends AutoResizeTextView {

	public AvgTextView(Context context, String text) {
		super(context, Color.LTGRAY, 14);
				
		this.setLayoutParams(
				new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 0, 1f)
		);
		
		this.setText(text);

	}

}
