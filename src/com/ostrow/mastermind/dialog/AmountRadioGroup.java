package com.ostrow.mastermind.dialog;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import com.ostrow.mastermind.R;

/**
 * @author ajostrow
 * 
 *  This class is the number of holes and colors selection in the dialog.
 *  Very little reusibility value.
 */
public class AmountRadioGroup extends LinearLayout {
	
	private int selected = -1;
	private ToggleButton[] buttons;
	private String[] labels;
	
	/**
	 * Constructor
	 * @param context - activity context
	 * @param start - integer to start at
	 * @param stop - integer to stop at
	 * @param isHoles - whether it is for the holes or colors
	 */
	public AmountRadioGroup(Context context, int start, int stop, boolean isHoles) {
		super(context);		
		
		buttons = new ToggleButton[ (stop - start) + 1];
        
        this.setLayoutParams(
        		new LinearLayout.LayoutParams(
        				LinearLayout.LayoutParams.FILL_PARENT, 
        				LinearLayout.LayoutParams.WRAP_CONTENT
        		)
        );
        this.setOrientation(HORIZONTAL);
	            
		int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40, getResources().getDisplayMetrics());			
		int margin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 2, getResources().getDisplayMetrics());

		if(isHoles) {
			labels = context.getResources().getStringArray(R.array.createDialog_numbers);
		}
		
		for(int i=start; i<=stop; i++) {
			final ToggleButton rb = new ToggleButton(context);
			final int position = i;
			rb.setId(i - start);
			
			LinearLayout.LayoutParams blp = new LinearLayout.LayoutParams(0, height, 1);
			blp.setMargins(margin, 0, margin, 0);
			
			rb.setButtonDrawable( getResources().getDrawable(R.drawable.togglebutton_src) );
			rb.setBackgroundDrawable( getResources().getDrawable(R.drawable.togglebutton_background) );
			
			rb.setOnClickListener(
				new OnClickListener() {
					public void onClick(View v) {
						selected = position;
						for(ToggleButton b : buttons) {
							b.setClickable(true);
							b.setChecked(false);
						}
						rb.setClickable(false);
						rb.setChecked(true);
					}
				}
			);
						
			if(isHoles) {
				String text = labels[i-start];
				rb.setText(text);
				rb.setTextOn(text);
				rb.setTextOff(text);
			} else {
				rb.setText(" " + i + " ");
				rb.setTextOn(" " + i + " ");
				rb.setTextOff(" " + i + " ");
			}
			
			rb.setGravity(Gravity.CENTER);
			this.addView(rb, blp);
			buttons[i-start] = rb;
		}
	
	}
	
	public int getSelectedValue() {
		if(selected < 0)
			return selected;
		return selected;
	}
	
}
