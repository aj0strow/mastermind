package com.ostrow.mastermind.dialog;

import com.ostrow.mastermind.R;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

/**
 * @author ajostrow
 *
 * Dialog called when New Type (+) button is clicked on the Main Menu
 */
public class NewTypeDialog extends Dialog {
	
	// Fields public as they should be accessed by the Activity
	public EditText enterName;
	public AmountRadioGroup colorsGroup;
	public AmountRadioGroup holesGroup;
	private Button submit;

	/**
	 * Constructor
	 * @param context
	 * @param displayWidth
	 */
	public NewTypeDialog(Context context, int displayWidth) {
		super(context);

		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.setCancelable(true);
	
		LayoutInflater factory = LayoutInflater.from(context);
		View dialogView = factory.inflate(R.layout.custom_dialog, null);
		this.setContentView(dialogView);
		
		// Initialize ui
		enterName = (EditText) dialogView.findViewById(R.id.dialog_name);
		
		LinearLayout numColors = (LinearLayout) dialogView.findViewById(R.id.dialog_colors);
		colorsGroup = new AmountRadioGroup(dialogView.getContext(), 3, 8, false);
		numColors.addView( colorsGroup );
		
		LinearLayout numHoles = (LinearLayout) dialogView.findViewById(R.id.dialog_holes);
		holesGroup = new AmountRadioGroup(dialogView.getContext(), 3, 5, true);
		numHoles.addView( holesGroup );
		
		submit = (Button) dialogView.findViewById(R.id.dialog_submit);
		
		// Set layout and show it
		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
	    lp.copyFrom( this.getWindow().getAttributes() );
	    lp.width = (int) ( displayWidth * .95 );
	    lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
	    this.show();
	    this.getWindow().setAttributes(lp);
	}
	
	// Set the onClickListener to the submit button through the dialog.
	public void setSubmitOnClickListener(android.view.View.OnClickListener buttonListener) {
		submit.setOnClickListener( (android.view.View.OnClickListener) buttonListener );
	}

}
